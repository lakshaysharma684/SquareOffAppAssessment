package com.lakshay.squareoffassesment

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: ExampleAdapter
    private lateinit var recycleView: RecyclerView
    private var orientValue : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleView = findViewById(R.id.recycleView)
        fetchData()
        mAdapter = ExampleAdapter(this)
        recycleView.adapter = mAdapter
    }

    fun orientation(): Int? {
        return orientValue
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        orientValue = if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            0
        }else{
            1
        }
    }

    private fun fetchData(){
        val requestQueue = Volley.newRequestQueue(this)
        val url = "https://followchess.com/config.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
        null,
            {
                val newJsonArray = it.getJSONArray("trns")
                val data = ArrayList<dataList>()
                for(i in 0 until newJsonArray.length()){
                    val newJsonObject = newJsonArray.getJSONObject(i)
                    val name = newJsonObject.getString("name")
                    val slug = newJsonObject.getString("slug")
                    val image = try {
                        newJsonObject.getString("img")
                    }catch (e :Exception){
                        ""
                    }
                    val status = newJsonObject.getString("status")
                    val Data = dataList(
                        name,
                        slug,
                        image,
                        status
                    )
                    data.add(Data)
                }
                mAdapter.updateData(data)
            },
            {

            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}
