package com.hank.dev

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var viewModel: MyViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val TAG = MainActivity::class.java.simpleName
    val job = Job() + Dispatchers.IO

    val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { success ->
            if (success) {
                takePhoto()
            } else {
                Snackbar.make(binding.root, "Denied", Snackbar.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //MVVM + Coroutines
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        //JSON
        viewModel.readJSON()
        //names
        val names = listOf(
            "Aaren", "Abbe", "Adele", "Carlyn", "Carol", "Cassy", "Claudia",
            "Dale", "Debra", "Ellen", "Gilberta", "Hallie", "Harlene", "Iaabelle", "Jacklyn",
            "Jaimie", "Jenifer", "Kaitlin", "Kaja"
        )
        val recyler = binding.contentView.recycler
        recyler.setHasFixedSize(true)
        recyler.layoutManager = LinearLayoutManager(this)
//        recyler.adapter = NameAdapter(names)
        viewModel.words.observe(this) { words ->
            recyler.adapter = WordAdapter(words)
        }
        //Spinner
        val nameAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names)
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = binding.contentView.spinner
        spinner.adapter = nameAdapter
        spinner.prompt = "Select name"
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
            R.id.action_camera -> {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                } else {
                    requestPermission.launch(Manifest.permission.CAMERA)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    override val coroutineContext: CoroutineContext
        get() = job

}