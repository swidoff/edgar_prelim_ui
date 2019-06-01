import org.scalajs.dom

object Main {
    def main(args: Array[String]): Unit = {
        CompanyTable().renderIntoDOM(dom.document.getElementById("root"))
    }
}
