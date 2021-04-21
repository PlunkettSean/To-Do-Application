package tk.GhostFIlms.ToDo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter (
    // declare a MutableList of ToDos
    var todos: MutableList<ToDo>
) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>(){
    // declare a ViewHolder that will hold the layout of an item in the RecyclerView
    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // declare TextView mutable field using null safety
    var todoTextView: TextView? = null

    // declare CheckBox mutable field using null safety
    var todoCheckBox: CheckBox? = null

    /**
     * Called when RecyclerView needs a new [ToDoViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.li_main,
            parent,
            false
        ))
    }

    /**
     * Returns the number of items in the MutableList of ToDos
     *
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return todos.size
    }

    /**
     * Initializes each of the items in the RecyclerView and maps the data in the MutableList of
     * ToDos to it.
     *
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ToDoViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ToDoViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        // get the current item in the MutableList of ToDos and store it in immutable variable
        val currentToDo = todos[position]

        holder.itemView.apply {
            // make TextView refer to TextView in li_main layout reference
            todoTextView = findViewById<View>(R.id.toDoTextView) as TextView
            // tell koltin that TextView isn't null. assign the name value in the current item to
            // text atribute of TextView
            todoTextView!!.text = currentToDo.name

            // make CheckBox refer to the CheckBox in li_main layout resource
            todoCheckBox = findViewById<View>(R.id.toDoCheckBox) as CheckBox

            // tell koltin that CheckBox isn't null. assign the name value in the current item to
            // text atribute of CheckBox
            todoCheckBox!!.isChecked = currentToDo.isChecked
            
            // Tell Kotlin that CheckBox isn't null
            // set onCheckedChangeListener to it
            todoCheckBox!!.setOnCheckedChangeListener { _, _ ->
                // invert current value of isChecked field of the currentToDo
                currentToDo.isChecked = !currentToDo.isChecked
            }
        }
    }

    /**
     * This method gests called by the addToDo method in the MainActivity when the add button is
     * clicked. It will call the DBHandler method that adds a ToDo into the database.
     */
    fun addToDo(dbHandler: DBHandler, name: String) {
        // ask ktolin to check if the dbHandler is null. If it isn't, call its addToDo method
        // passing the specified ToDo Name
        dbHandler?.addToDo(name)
    }
}