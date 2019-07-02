import os
from functools import wraps
from pathlib import Path

import pandas as pd
from flask import Flask, make_response, url_for
from sqlalchemy import text, create_engine
from werkzeug.contrib.cache import SimpleCache
from flask import request
from werkzeug.utils import redirect


def db_file():
    return (Path(__file__).parent / 'edgar_prelim.db').absolute()


def create_app(test_config=None):
    connection_string = os.environ.get("DB_URL", f'sqlite:///{db_file()}')
    prelim_engine = create_engine(connection_string, echo=False)

    # create and configure the app
    app = Flask(__name__, instance_relative_config=True)
    app.config.from_mapping(
        SECRET_KEY='dev',
    )

    if test_config is None:
        # load the instance config, if it exists, when not testing
        app.config.from_pyfile('config.py', silent=True)
    else:
        # load the test config if passed in
        app.config.from_mapping(test_config)

    # ensure the instance folder exists
    try:
        os.makedirs(app.instance_path)
    except OSError:
        pass

    cache = SimpleCache()

    def cached(timeout):
        def decorate(func):
            @wraps(func)
            def wrapper():
                key = func.__name__
                rv = cache.get(key)
                if rv is None:
                    rv = func()
                    cache.set(key, rv, timeout=timeout)
                return rv

            return wrapper

        return decorate

    @app.route('/')
    def index():
        return redirect(url_for('static', filename='index.html'))

    @app.route('/<cik>')
    def cik_data(cik):
        data_df = pd.read_sql(text(
            "select cik, filing_date, fiscal_period, item, item_value from prelim_statement where cik = :cik"
        ).bindparams(cik=cik), prelim_engine)

        unstacked_df = data_df.set_index(['cik', 'filing_date', 'fiscal_period', 'item']).unstack()
        unstacked_df.columns = unstacked_df.columns.droplevel(0)
        unstacked_df.reset_index(inplace=True)
        fmt = request.args.get('format', 'json')

        json = unstacked_df.to_json(orient='records') if fmt == 'json' else unstacked_df.to_csv()
        resp = make_response(json, 200)
        resp.headers['Content-Type'] = 'text/json' if fmt == 'json' else 'text/csv'
        return resp

    @app.route('/companies')
    @cached(timeout=0)
    def companies():
        cik_df = pd.read_sql(
            """
            select c.cik, c.sic, c.ticker, c.sic_description, c.company_name, max(s.filing_date) as last_filing_date 
            from cik c 
            join prelim_statement s on (
                c.cik = s.cik
            )
            group by c.cik, c.sic, c.sic_description, c.company_name
            order by last_filing_date desc, c.cik asc
            """, prelim_engine, parse_dates=['last_filing_date'])
        json = cik_df.assign(
            last_filing_date=cik_df.last_filing_date.map(lambda x: x.strftime('%Y-%m-%d'))
        ).to_json(orient='records')

        resp = make_response(json, 200)
        resp.headers['Content-Type'] = 'text/json'
        return resp

    def serve_scalajs_file(name: str, type='text/javascript'):
        file = Path(f'edgar_prelim_server/static/{name}')
        resp = make_response(file.read_text(), 200)
        resp.headers['Content-Type'] = type
        resp.headers['X-Content-Type-Options'] = 'nosniff'
        return resp

    @app.route('/static/edgar_prelim_ui-fastopt-bundle.js')
    def js():
        return serve_scalajs_file("edgar_prelim_ui-fastopt-bundle.js")

    @app.route('/edgar_prelim_ui-fastopt-bundle.js.map')
    def js_map():
        return serve_scalajs_file("edgar_prelim_ui-fastopt-bundle.js.map", type='text/json')

    return app
