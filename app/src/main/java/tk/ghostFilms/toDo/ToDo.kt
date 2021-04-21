package tk.ghostfilms.toDo

/**
 * This ToDo data class has three fields that map to the coulumns in the
 * todo table in the database. It will be used to exchange data between
 * the database and the RecylerView
 */
data class ToDo (
        // declare a mutable Int to store the todo id
        var id: Int,
        // declare an inmutable String to declare the todo name
        var name: String,
        // delcare a mutable Boolean to store the todo is
        var isChecked: Boolean = false
)