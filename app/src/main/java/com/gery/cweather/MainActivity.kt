package com.gery.cweather

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gery.cweather.api.RetrofitClient
import com.gery.cweather.api.WeatherAPI
import com.gery.cweather.api.WeatherResponse
import com.gery.cweather.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val weatherApi: WeatherAPI = RetrofitClient.retrofit.create(WeatherAPI::class.java)

        binding.btnCitySearch.setOnClickListener {
            val requestThread = Thread {
                val response = weatherApi.getWeatherDetails(
                    binding.etCityValue.text.toString(),
                    getString(R.string.owm_units),
                    getString(R.string.owm_api_key)
                )
                response.enqueue(object : Callback<WeatherResponse> {
                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<WeatherResponse>,
                        response: retrofit2.Response<WeatherResponse>
                    ) {
                        Log.d("RETROFIT", response.body().toString())
                        val coord = response.body()?.coord
                        val coordString = coord?.lat.toString() + ", " + coord?.lon.toString()
                        binding.tvValueCoordinates.text = coordString

                        binding.tvValueDescription.text =
                            response.body()?.weather?.get(0)?.description

                        val weather = response.body()?.weather?.get(0)?.main
                        binding.tvValueWeather.text = weather

                        binding.tvValueTemperature.text = response.body()?.main?.temp.toString()

                        binding.tvValuePressure.text = response.body()?.main?.pressure.toString()

                        binding.tvValueHumidity.text = response.body()?.main?.humidity.toString()

                        binding.tvValueMinimum.text = response.body()?.main?.temp_min.toString()
                        binding.tvValueMaximum.text = response.body()?.main?.temp_max.toString()
                    }
                }
                )
            }
            requestThread.start()
        }
    }
}