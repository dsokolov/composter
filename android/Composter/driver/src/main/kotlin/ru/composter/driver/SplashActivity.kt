package ru.composter.driver

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.composter.rsa.KeysStorage

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        findViewById(R.id.root)!!.setOnClickListener {
            if (KeysStorage.hasKeys(this)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, RegistrationActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}