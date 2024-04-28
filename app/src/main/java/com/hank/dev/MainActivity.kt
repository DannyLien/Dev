package com.hank.dev

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.hank.dev.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val TAG = MainActivity::class.java.simpleName
    val job = Job() + Dispatchers.IO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        //JSON
        launch {
            val json = URL("https://api.jsonserve.com/pcLzBT").readText()
            Log.d(TAG, "onCreate: $json")
//            parseJSON(json)
            val words = Gson().fromJson(json, Words::class.java)
            for (w in words.words) {
                Log.d(TAG, "onGson: ${w.name} : ${w.means}")
            }
        }

    }

    private fun parseJSON(json: String) {
        val jsonObject = JSONObject(json)
        val array = jsonObject.getJSONArray("words")
        for (i in 0..array.length() - 1) {
            val w = array.getJSONObject(i)
            val name = w.getString("name")
            val means = w.getString("means")
            Log.d(TAG, "onCreate: $name : $means")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job

}