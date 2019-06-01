import japgolly.scalajs.react._
import org.scalajs.dom.html

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Button {

    @JSImport("react-bootstrap", "Button")
    @js.native
    object Button extends js.Object

    @js.native
    trait Props extends js.Object {
        var variant: String = js.native
        var href: String = js.native
    }

    def props(variant: String = "primary", href: Option[String] = None): Props = {
        val p = (new js.Object).asInstanceOf[Props]
        p.variant = variant
        for (h <- href) p.href = h
        p
    }

    //noinspection TypeAnnotation
    val component = JsForwardRefComponent[Props, Children.Varargs, html.Button](Button)
}
