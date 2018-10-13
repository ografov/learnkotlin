package com.example.olegr.helloworld

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.*

class MySqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "mydb") {

    companion object {
        private var instance: MySqlHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MySqlHelper {
            if (instance == null) {
                instance = MySqlHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("Person", true,
                "_id" to INTEGER + PRIMARY_KEY,
                "name" to TEXT,
                "surname" to TEXT,
                "age" to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun insert(name: String, surname: String, age: Number) {
        instance?.use {
            insert("Person",
                    "_id" to 2,
                    "name" to name,
                    "surname" to surname,
                    "age" to age)
        }
    }

    fun selectByName(name: String,  action: (map: Map<String, Any?>)->Any) {
        instance?.use {
            select("Person", "_id", "name", "surname", "age")
                    .whereArgs("(name = {name})",
                            "name" to name)
                    .exec {
                        parseList(object : MapRowParser<Map<String, Any?>> {
                            override fun parseRow(columns: Map<String, Any?>): Map<String, Any?> {
                                Log.e("Your result", columns.toString())
                                action(columns)
                                return columns
                            }
                        }
                        )
                    }
        }
    }
    fun selectAll(action: (columns: Map<String, Any?>)->Any) {
        instance?.use {
            select("Person", "_id", "name", "surname").exec {
                parseList(object : MapRowParser<Map<String, Any?>> {
                    override fun parseRow(columns: Map<String, Any?>): Map<String, Any?> {
                        Log.e("Your result", columns.toString())
                        action(columns)
                        return columns
                    }
                }
                )
            }
        }
    }
}

// Access property for Context
val Context.database: MySqlHelper
    get() = MySqlHelper.getInstance(applicationContext)