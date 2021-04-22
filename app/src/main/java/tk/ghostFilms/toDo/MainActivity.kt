package tk.ghostFilms.toDo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // Declare EditText as mutable field using null safety
    var todoEditText: EditText? = null

    // declare RecyclerView as a mutable field using null safety
    var todoRecyclerView: RecyclerView? = null

    // declare DBHandler as mutable field using null saftey
    var dbHandler: DBHandler? = null

    // declare a ToDoAdapter ad mutable field. Specifying that it will be initialized later
    lateinit var toDoAdapter: ToDoAdapter

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // make EditText refer to actual EditText in activity_main Layout resource
        todoEditText = findViewById<View>(R.id.toDoEditText) as EditText

        // make RecyclerView refer to actual RecyclerView in activity_main Layout resource
        todoRecyclerView = findViewById<View>(R.id.toDoRecyclerView) as RecyclerView

        // fully initialize DBHandler
        dbHandler = DBHandler(this, null)

        // initialize the toDoAdapter
        toDoAdapter = ToDoAdapter(dbHandler!!.todos)

        // Tell Kotlin that the RecyclerView isn't null and set the toDoadapter to it
        todoRecyclerView!!.adapter = toDoAdapter

        // Tell Kotlin that the RecyclerView isn't null and apply a LinearLayout to it
        todoRecyclerView!!.layoutManager = LinearLayoutManager(this)
    }

    /**
     * This function gets called when the addButton is clicked. It adds a toDo into the database.
     * @param view MainActivity view
     */
    fun addToDO(view: View?) {
        // Tell Kotlin that EditText isn't null. Get text input in EditText as String.
        // Store it in Variable
        val todoName = todoEditText!!.text.toString()

        // trim variable and check if it's equal to an empty string
        if(todoName.trim()=="") {
            // display "please enter a ToDo!" Toast
            Toast.makeText(this, "Please enter a ToDo!", Toast.LENGTH_LONG).show()
        } else {
            // Ask Kotlin to check if the dbHandler is null. If it's not, apply all code in let
            // block to it. The let block, may refer to the dbHandler as it
            dbHandler?.let {
                // call method in toDoAdapter that will add ToDo into the database.
                toDoAdapter.addToDo(it, todoName)
            }

            // display "ToDo Added!" Toast
            Toast.makeText(this, "ToDo Added!", Toast.LENGTH_LONG).show()

            // clear EditText
            todoEditText!!.text.clear()

            // call notifyAdapter method
            notifyAdapter()
        }
    }

    /**
     * This method updates the MutableLists of ToDos in the toDoAdapter with the most current data
     * in the database.
     */
    private fun notifyAdapter() {
        // Tell Kotlin that dbHandler isn't null. Call its getter method that returns the data in
        // the database as a MutableList. Assign MutableList to toDoAdapter's MutableList of ToDos
        toDoAdapter.todos = dbHandler!!.todos
    }

    /**
     * This function gets called when the deleteButton is clicked. It deletes selected toDo elements
     * from the database.
     * @param view MainActivity view
     */
    fun deleteToDo(view: View?) {
        // Ask Kotlin to check if the dbHandler is null. If it is not null, pass it to the
        // deleteToDos method in the toDoAdapter
        dbHandler?.let {
            toDoAdapter.deleteToDos(it)
        }

        // Call the notifyAdapter method
        notifyAdapter()
    }
}