package com.example.olegr.helloworld

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity;
import android.text.Selection.selectAll
import android.util.Log
import com.example.olegr.helloworld.R.id.myTextView
import kotlinx.android.synthetic.main.activity_fullscreen.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        btnGo.setOnClickListener { _ ->
            onGoClick()
        }
    }

    private fun onGoClick() {
        myTextView.text = "What a text!"

        database.insert("Vasya", "Pupkin", 21)

        showFullScreenActivity()
    }

    private fun showFullScreenActivity() {
        // Create an Intent to start the second activity
        val randomIntent = Intent(this, FullscreenActivity::class.java)

        // Start the new activity.
        startActivity(randomIntent)
    }
}
