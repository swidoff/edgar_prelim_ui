import japgolly.scalajs.react._
import org.scalajs.dom.html

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Button {

    @JSImport("react-bootstrap", "Button")
    @js.native
    object Button extends js.Object

//    @js.native
//    trait Props extends js.Object {
//    }

//    def props(active: Boolean = true): Props = {
//        val p = (new js.Object).asInstanceOf[Props]
//        p
//    }

    //noinspection TypeAnnotation
    val component = JsForwardRefComponent[Null, Children.Varargs, html.Button](Button)
}
