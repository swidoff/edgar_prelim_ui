import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.component.builder.Lifecycle
import japgolly.scalajs.react.vdom.HtmlTagOf
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, ScalaComponent, raw}
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html

import scala.scalajs.js
import scala.scalajs.js.JSConverters.JSRichGenTraversableOnce
import scala.scalajs.js.annotation.JSExportTopLevel

//noinspection TypeAnnotation
object CompanyTable {
    import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

    val columns = IndexedSeq(
        Table.column("CIK", "cik", Option(CompanyLink.renderFn)),
        Table.column("Ticker", "ticker"),
        Table.column("Name", "company_name"),
        Table.column("SIC", "sic"),
        Table.column("SIC Description", "sic_description"),
        Table.column("Last Filing Date", "last_filing_date"),
        Table.column("", "cik", Option(ExportButtons.renderFn), filterable = false, sortable = false),
    )

    case class Props()
    case class State(data: js.Array[js.Object] = Seq.empty.toJSArray)

    def loadCompanies(s: Lifecycle.ComponentWillMount[Props, State, Unit]) = {
        Ajax.get("http://127.0.0.1:5000/companies").map { res =>
            if (res.status == 200) {
                val json = js.JSON.parse(res.responseText).asInstanceOf[js.Array[js.Object]]
                s.setState(State(json))
            } else {
                println(s"Status = ${res.status}")
                s.setState(State())
            }
        }
    }

    val Component = ScalaComponent.builder[Props]("CompanyTable")
        .initialState(State())
        .render_S(s => Table.component(Table.props(s.data, columns, filterable = true)))
        .componentWillMount(s => Callback.future(loadCompanies(s)))
        .build

    def apply(): Unmounted[Props, State, Unit] = Component(Props())
}

object CompanyLink {
    val component = ScalaComponent.builder[js.Object]("CompanyLink")
        .render_P(p => Button.component(Button.props(
            variant = "link",
            href = Some(s"http://127.0.0.01:5000/static/quality/${Table.valueOf(p)}.html")))(Table.valueOf(p)))
        .build

    @JSExportTopLevel("CompanyLink")
    def render(props: js.Object): raw.React.Element = component(props).rawElement

    val renderFn: js.Function1[js.Object, js.Object] = render
}

object ExportButtons {
    val component = ScalaComponent.builder[js.Object]("ExportButtons")
        .render_P(p => HtmlTagOf[html.Element]("ButtonToolbar")(
            Button.component(Button.props(
                href = Some(s"http://127.0.0.01:5000/${Table.valueOf(p)}?format=csv")))("CSV"),
            " ",
            Button.component(Button.props(
                href = Some(s"http://127.0.0.01:5000/${Table.valueOf(p)}?format=json")))("JSON"))
        ).build

    @JSExportTopLevel("ExportButtons")
    def render(props: js.Object): raw.React.Element = component(props).rawElement

    val renderFn: js.Function1[js.Object, js.Object] = render
}
