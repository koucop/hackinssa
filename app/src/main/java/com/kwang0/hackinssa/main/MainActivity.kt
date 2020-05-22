package com.kwang0.hackinssa.main;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kwang0.hackinssa.R


class MainActivity: AppCompatActivity() {

    lateinit var container: ViewPager
    lateinit var tl: TabLayout
    lateinit var toolbar: Toolbar

    fun mainActContentsInit() {
        container = findViewById<ViewPager>(R.id.main_container)
        tl = findViewById<TabLayout>(R.id.main_tl)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActContentsInit()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val pagerAdapter = MainPagerAdapter(supportFragmentManager)
        container.adapter = pagerAdapter


        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tl))
        tl.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    override fun onBackPressed() {
        if (container.getCurrentItem() == 0) {
            super.onBackPressed();
        }else {
            container.setCurrentItem(0);
        }
    }
}