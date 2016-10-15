package ru.composter.driver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        findViewById(R.id.register)!!.setOnClickListener {
            //TODO
        }
    }

}