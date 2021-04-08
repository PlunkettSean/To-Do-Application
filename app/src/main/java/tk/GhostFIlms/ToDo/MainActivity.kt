package tk.GhostFIlms.ToDo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * This function gets called when the addButton is clicked. It adds a toDo into the database.
     * @param view MainActivity view
     */
    fun addToDO(view: View?) {

    }

    /**
     * This function gets called when the deleteButton is clicked. It deletes selected toDo elements
     * from the database.
     * @param view MainActivity view
     */
    fun deleteToDo(view: View?) {

    }
}