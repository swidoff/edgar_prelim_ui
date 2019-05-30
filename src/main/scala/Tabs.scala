import japgolly.scalajs.react._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Tabs {

    @JSImport("react-bootstrap", "Tabs")
    @js.native
    object Tabs extends js.Object

    @js.native
    trait Props extends js.Object {
        var id: String = js.native
        var defaultActiveKey: String = js.native
    }

    def props(id: String, defaultActiveKey: String): Props = {
        val p = (new js.Object).asInstanceOf[Props]
        p.id = id
        p.defaultActiveKey = defaultActiveKey
        p
    }

    //noinspection TypeAnnotation
    val component = JsComponent[Props, Children.Varargs, Null](Tabs)
}
