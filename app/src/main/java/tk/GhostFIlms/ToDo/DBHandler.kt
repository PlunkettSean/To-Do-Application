package tk.GhostFIlms.ToDo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context?, cursorFactory: SQLiteDatabase.CursorFactory?) :
SQLiteOpenHelper(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION){
    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    override fun onCreate(db: SQLiteDatabase) {
        // define create statement for todo table
        val query = "CREATE TABLE " + TABLE_TODO_LIST + "(" +
                COLUMN_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TODO_ISCHECKED + " TEXT, " +
                COLUMN_TODO_NAME + " TEXT);"

        // execute create statement
        db.execSQL(query)
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     *
     *
     * The SQLite ALTER TABLE documentation can be found
     * [here](http://sqlite.org/lang_altertable.html). If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     *
     *
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     *
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // define drop statement for the todo table
        val query = "DROP TABLE IF EXISTS " + TABLE_TODO_LIST

        // execute drop statement
        db.execSQL(query)

        // call method that creates the table
        onCreate(db)
    }

    /**
     * This method gets called when the add button of the MainActivity gets clicked.
     * it inserts a new row into the todo table
     * @param todoName
     */
    fun addToDo(todoName: String?) {
        // Get reference to todoapp database
        val db = writableDatabase

        // initialize a ContentValues object
        val values = ContentValues()

        // put data into the ContentValues object
        values.put(COLUMN_TODO_NAME, todoName)
        values.put(COLUMN_TODO_ISCHECKED, "false")

        // insert data in ContentValues object into todo table
        db.insert(TABLE_TODO_LIST, null, values)

        //
        db.close()
    }

    /**
     * This method gets called when the MainActivity is created and when the add and delete buttons
     * get clicked
     * @return MutableList
     */
    val todos: MutableList<ToDo>
        @SuppressLint("Recycle")
        get() {
            // get a reference to the todoapp database
            val db = writableDatabase

            // define select statement
            val query = "SELECT * FROM " + TABLE_TODO_LIST

            // execute the select statement and store its return in an immutable Cursor
            val c = db.rawQuery(query, null)

            // create MutableList of ToDos that will be returned by the method
            val list: MutableList<ToDo> = ArrayList()

            // loop through the rows in the Cursor
            while(c.moveToNext()) {
                // create an immutable ToDo using the data in the current row in the cursor
                val todo = ToDo(c.getInt(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("is_checked"))!!.toBoolean())
                // add the ToDo that was just created into the MutableList of ToDos
                list.add(todo)
            }

            db.close()

            return list
        }

    companion object {
        // initialize constants for the DB name and version
        private const val DATABASE_NAME = "todoapp.db"
        private const val DATABASE_VERSION = 1

        // initialize constants for the todo table
        private const val TABLE_TODO_LIST = "todo"
        private const val COLUMN_TODO_ID = "_id"
        private const val COLUMN_TODO_NAME = "name"
        private const val COLUMN_TODO_ISCHECKED = "is_checked"
    }

}
