package com.github.felipehjcosta.marvelclient.androidapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.github.felipehjcosta.marvelclient.createApplicationApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = createApplicationApi(BuildConfig.MARVEL_PRIVATE_KEY, BuildConfig.MARVEL_PUBLIC_KEY)

        api.fetchCharacters {
            GlobalScope.apply {
                launch(Dispatchers.Main) {
                    findViewById<TextView>(R.id.about).text = it
                }
            }
        }

        setContentView(R.layout.activity_main)
    }
}
