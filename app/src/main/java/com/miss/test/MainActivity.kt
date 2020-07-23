package com.miss.test

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var mData : MutableList<Star>
    lateinit var mContext: Context

    lateinit var recyclerView: RecyclerView
    lateinit var starAdapter: StarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        mContext = this
        recyclerView = findViewById(R.id.rv_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        starAdapter = StarAdapter(mContext, mData)

        recyclerView.addItemDecoration(MyDecoration(mContext))
//        recyclerView.addItemDecoration(StarDecoration(mContext))
        recyclerView.adapter = starAdapter
    }


    private fun init(){
        mData = ArrayList()
        for (i in 0 until 4){
            for (j in 0 until 10) {
                if (i % 2 == 0) {
                    mData.add(Star("何炅$j", "快乐家族$i"))
                } else {
                    mData.add(Star("汪涵$j", "天天兄弟$i"))
                }
            }
        }
    }














}