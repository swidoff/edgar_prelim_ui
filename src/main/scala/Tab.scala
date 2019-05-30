import japgolly.scalajs.react.{Children, JsComponent}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Tab {

    @JSImport("react-bootstrap", "Tab")
    @js.native
    object Tab extends js.Object

    @js.native
    trait Props extends js.Object {
        var eventKey: String = js.native
        var title: String = js.native
    }

    def props(eventKey: String, title: String): Props = {
        val p = (new js.Object).asInstanceOf[Props]
        p.title = title
        p.eventKey = eventKey
        p
    }

    //noinspection TypeAnnotation
    val component = JsComponent[Props, Children.Varargs, Null](Tab)
}
