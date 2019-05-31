import japgolly.scalajs.react.{Children, JsFnComponent}

import scala.scalajs.js
import scala.scalajs.js.JSConverters.JSRichGenTraversableOnce
import scala.scalajs.js.annotation.JSImport

object Table {
    @JSImport("react-table", "default")
    @js.native
    object ReactTable extends js.Object

    @js.native
    trait Column extends js.Object {
        var Header: String = js.native
        var accessor: String = js.native
    }

    @js.native
    trait Props extends js.Object {
        var data: js.Object = js.native
        var columns: js.Array[Column] = js.native
    }

    def column(header: String, accessor: String): Column = {
        val p = (new js.Object).asInstanceOf[Column]
        p.accessor = accessor
        p.Header = header
        p
    }

    def props(data: Seq[js.Object], columns: Seq[Column]): Props = {
        val p = (new js.Object).asInstanceOf[Props]
        p.data = data.toJSArray
        p.columns = columns.toJSArray
        p
    }

    def props(data: js.Array[js.Object], columns: Seq[Column]): Props = {
        val p = (new js.Object).asInstanceOf[Props]
        p.data = data
        p.columns = columns.toJSArray
        p
    }

    //noinspection TypeAnnotation
    val component = JsFnComponent[Props, Children.None](ReactTable)
}
