package com.kwang0.hackinssa.presentation.ui.adapters

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kwang0.hackinssa.R
import com.kwang0.hackinssa.data.models.Country
import com.kwang0.hackinssa.helper.GlideHelper
import com.kwang0.hackinssa.presentation.ui.activities.countryinfo.CountryInfoActivity
import com.kwang0.hackinssa.presentation.ui.activities.countryselect.CountrySelectActivity
import com.kwang0.hackinssa.presentation.ui.activities.main.MainActivity


class CountryAdapter(val mContext: Context, var mData: MutableList<Country>?) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    fun addManyToList(countries: MutableList<Country>?) {
        this.mData = countries
        this.notifyDataSetChanged()
    }

    fun clearList() {
        with(this.mData) {
            this?.clear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView: View
        rootView = LayoutInflater.from(mContext).inflate(R.layout.reuse_country_rv_con, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Country? = mData?.get(position)
        GlideHelper.loadImg(mContext,BASE_IMG_URL_250_PX.toString() + item?.getAlpha2Code()!!.toLowerCase() + ".png?raw=true", holder.iv)

        holder.tv.text = item.getNativeName()
        holder.layout.setOnClickListener {v ->
            if(mContext is MainActivity) {
                val intent = Intent(mContext, CountryInfoActivity::class.java)
                intent.putExtra("country", mData?.get(position))
                mContext.startActivity(intent)
            } else if(mContext is CountrySelectActivity) {
                val intent = Intent();
                intent.putExtra("country", item.getAlpha2Code());
                mContext.setResult(RESULT_OK, intent);
                mContext.finish();
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: RelativeLayout
        var iv: ImageView
        var tv: TextView
        init {
            layout = itemView.findViewById<RelativeLayout>(R.id.country_rv_layout)
            iv = itemView.findViewById<ImageView>(R.id.country_rv_iv)
            tv = itemView.findViewById<TextView>(R.id.country_rv_tv)
        }
    }

    companion object {
        const val BASE_IMG_URL_250_PX = "https://github.com/hjnilsson/country-flags/blob/master/png250px/"
    }
}