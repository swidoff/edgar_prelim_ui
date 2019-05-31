import japgolly.scalajs.react.component.builder.Lifecycle
import japgolly.scalajs.react.{Callback, CallbackTo, ScalaComponent}
import org.scalajs.dom.ext.Ajax

import scala.scalajs.js
import scala.scalajs.js.JSConverters.JSRichGenTraversableOnce
import scala.util.{Failure, Success}

//noinspection TypeAnnotation
object CompanyTable {
    import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

    val columns = IndexedSeq(
        Table.column("CIK", "cik"),
        Table.column("Ticker", "ticker"),
        Table.column("Name", "company_name"),
        Table.column("SIC", "sic"),
        Table.column("SIC Description", "sic_description"),
        Table.column("Last Filing Date", "last_filing_date"),
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
        .render_S(s => Table.component(Table.props(s.data, columns)))
        .componentWillMount(s => Callback.future(loadCompanies(s)))
        .build

    def apply() = Component(Props())
}
