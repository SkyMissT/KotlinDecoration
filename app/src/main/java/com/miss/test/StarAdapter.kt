package com.miss.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *   Created by Vola on 2020/7/21.
 */
class StarAdapter() : RecyclerView.Adapter<StarAdapter.StarHolder>() {

    lateinit var mContext: Context
    lateinit var mData: MutableList<Star>

    constructor(context: Context,data:MutableList<Star>) : this() {
        this.mContext = context
        this.mData = data
    }


    inner class StarHolder(view: View) : RecyclerView.ViewHolder(view) {
        var testView: TextView = view.findViewById(R.id.tv_star)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarHolder {
        var layoutInflater = LayoutInflater.from(mContext)
        var view = layoutInflater.inflate(R.layout.rv_item_star, null)
        return StarHolder(view)
    }

    override fun getItemCount(): Int = mData.size


    override fun onBindViewHolder(holder: StarHolder, position: Int) {
        val bean: Star = mData[position]
        holder.testView.text = bean.name
    }

    public fun getGroupName(position: Int): String = mData[position].groupName


    /**
     *      是否是此组第一个
     */
    public fun isGroupHead(position: Int): Boolean {
        if (position == 0) {
            return true
        }

        var groupName: String = mData[position].groupName
        var preName: String = mData[position - 1].groupName
        if (groupName == preName) {
            return false
        }
        return true
    }


}