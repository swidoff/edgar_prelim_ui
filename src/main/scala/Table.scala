import japgolly.scalajs.react.{Children, ComponentDom, JsFnComponent}

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
        var Cell: js.Object = js.native
        var accessor: String = js.native
        var filterable: Boolean  = js.native
        var sortable: Boolean  = js.native
    }

    @js.native
    trait Props extends js.Object {
        var data: js.Object = js.native
        var columns: js.Array[Column] = js.native
        var filterable: Boolean = js.native
        var defaultPageSize: Int = js.native
    }

    def column(header: String,
               accessor: String,
               cell: Option[js.Object] = None,
               filterable: Boolean = true,
               sortable: Boolean = true): Column = {
        val p = (new js.Object).asInstanceOf[Column]
        p.accessor = accessor
        p.Header = header
        for (c <- cell) {
            p.Cell = c
        }
        p.filterable = filterable
        p.sortable = sortable
        p
    }

    def props(data: js.Array[js.Object], columns: Seq[Column],
              filterable: Boolean = false,
              defaultPageSize: Int = 20): Props = {
        val p = (new js.Object).asInstanceOf[Props]
        p.data = data
        p.columns = columns.toJSArray
        p.filterable = filterable
        p.defaultPageSize = defaultPageSize
        p
    }

    def valueOf(row: js.Object): String = row.asInstanceOf[js.Dictionary[String]]("value")

    //noinspection TypeAnnotation
    val component = JsFnComponent[Props, Children.None](ReactTable)
}
