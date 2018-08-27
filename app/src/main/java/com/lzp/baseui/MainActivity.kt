package com.lzp.baseui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.baseactivity).setOnClickListener {
            startActivity(Intent(this@MainActivity, ExtendBaseActivity::class.java))
        }

        findViewById<View>(R.id.fragment).setOnClickListener {
            startActivity(Intent(this@MainActivity, ViewPagerActivity::class.java))
        }

    }
}
