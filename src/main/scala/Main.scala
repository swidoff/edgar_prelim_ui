import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

object Main {
    def main(args: Array[String]): Unit = {
        val tabs = Tabs.component(Tabs.props("edgar-prelim-tabs", "companies"))(
            Tab.component(Tab.props("companies", "Companies"))(CompanyTable()),
            Tab.component(Tab.props("query", "Query"))(<.span("Query")),
        )
        tabs.renderIntoDOM(dom.document.getElementById("root"))
    }
}
