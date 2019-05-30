import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js

object Main {
    def company(cik: String, ticker: String, name: String, sic: String, sicDescription: String): js.Object = {
        js.Dynamic.literal("cik" -> cik, "ticker" -> ticker, "name" -> name, "sic" -> sic, "sicDescription" -> sicDescription)
    }

    def main(args: Array[String]): Unit = {
        val companyTable = Table.component(Table.props(
            IndexedSeq(company("0000004962", "AXP", "AMERICAN EXPRESS CO", "6199", "FINANCE SERVICES")),
            IndexedSeq(
                Table.column("CIK", "cik"),
                Table.column("Ticker", "ticker"),
                Table.column("Name", "name"),
                Table.column("SIC", "sic"),
                Table.column("SIC Description", "sicDescription"),
            )
        ))

        val tabs = Tabs.component(Tabs.props("edgar-prelim-tabs", "companies"))(
            Tab.component(Tab.props("companies", "Companies"))(companyTable),
            Tab.component(Tab.props("query", "Query"))(<.span("Query")),
        )
        tabs.renderIntoDOM(dom.document.getElementById("root"))
    }
}
