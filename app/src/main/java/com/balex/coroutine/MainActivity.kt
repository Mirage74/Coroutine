package com.balex.coroutine

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.balex.coroutine.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"


    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        Log.d(TAG, "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        loadCity { it ->
            binding.tvLocation.text = it
            loadTemperature(it) {
                binding.tvTemperature.text = it.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
                Log.d(TAG, "Load finished: $this")
            }
        }
    }


    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(3000)
            //Handler(Looper.getMainLooper()).post {
            runOnUiThread {
                callback.invoke("Bern")
            }
        }

    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            //Handler(Looper.getMainLooper()).post {
            runOnUiThread {
                Toast.makeText(
                    this,
                    getString(R.string.loading_temperature_toast, city),
                    Toast.LENGTH_SHORT
                ).show()
            }

            Thread.sleep(3000)
            //Handler(Looper.getMainLooper()).post {
            runOnUiThread {
                callback.invoke(17)
            }

        }
    }

}

