package com.iggy.catchthedemon

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        back.setOnClickListener {
            startActivity(Intent(this, TopActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }


        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        adView_des.loadAd(adRequest)
    }
}