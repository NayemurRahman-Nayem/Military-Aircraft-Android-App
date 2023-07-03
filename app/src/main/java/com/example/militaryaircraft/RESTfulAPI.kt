package com.example.militaryaircraft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.nio.file.attribute.AclEntry.Builder

class RESTfulAPI : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restful_api)
        recyclerView = findViewById(R.id.recycler_view)

        val retrofitBuilder = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getQuotes()
        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                // If Api call is success then use the data of API and show in my APP
                var responseBody = response.body()
                val quoteList = responseBody?.quotes!!

                myAdapter = MyAdapter(this@RESTfulAPI,quoteList)
                recyclerView.adapter = myAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@RESTfulAPI)

            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                // If API call fails
                Log.d("RESTful API " , "onFailure" + t.message) ;
            }
        })



    }
}

