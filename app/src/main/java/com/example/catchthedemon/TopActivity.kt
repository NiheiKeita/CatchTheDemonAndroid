package com.iggy.catchthedemon

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_top.*

class TopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        start_button.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        description.setOnClickListener {
            startActivity(Intent(this, DescriptionActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
        val pref = getSharedPreferences("my_settings", Context.MODE_PRIVATE)
        val saveInt = pref.getInt("trofy", 0)
        trophy_number.text = "×$saveInt"


        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}