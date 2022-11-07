    package com.iggy.catchthedemon

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.lose_pop.*
import kotlinx.android.synthetic.main.win_pop.*

class GameActivity : AppCompatActivity() {
    var people1:Int = 1
    var people2:Int = 2
    var people3:Int = 4
    var demon:Int = 11
    var touch:Int = 0
    var turn:Int = 0
    var start:Int = 0
    lateinit var mp0: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        human1.setImageResource(R.drawable.human1_side)
        human2.setImageResource(R.drawable.human2_side)
        human3.setImageResource(R.drawable.human3_side)
        demon_body.setImageResource(R.drawable.demon_left)
        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        adViewGame.loadAd(adRequest)


        //BGM
        mp0= MediaPlayer.create(this,R.raw.bgm)
        mp0.isLooping=true
        mp0.start()
        sound_button.setOnClickListener {
            if(sound_button.getTag() == 1){
                mp0.start()
                sound_button.setImageResource(R.drawable.sound_active)
                sound_button.setTag(0)
            }else{
                mp0.pause()
                sound_button.setImageResource(R.drawable.sound_inactive)
                sound_button.setTag(1)
            }
        }

        back.setOnClickListener {
            startActivity(Intent(this, TopActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }
        back_lose.setOnClickListener {
            startActivity(Intent(this, TopActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }
        back_win.setOnClickListener {
            startActivity(Intent(this, TopActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }

        //人に触れた場合
        human1.setOnClickListener {
            if(touch == 0){
                RangeOfMovement(people1)
                touch = 1
            }else{
                circleInvisible()
                if(turn == 0){
                    touch = 0
                }
            }
        }
        human2.setOnClickListener {
            if(touch == 0){
                RangeOfMovement(people2)
                touch = 2
            }else{
                circleInvisible()
                if(turn == 0){
                    touch = 0
                }
            }
        }
        human3.setOnClickListener {
            if(touch == 0){
                RangeOfMovement(people3)
                touch = 3
            }else{
                circleInvisible()
                if(turn == 0){
                    touch = 0
                }
            }
        }

        //サークルに移動
        circle_2.setOnClickListener {
            humanMove(circle_2,2)
        }
        circle_3.setOnClickListener {
            humanMove(circle_3,3)
        }
        circle_4.setOnClickListener {
            humanMove(circle_4,4)
        }
        circle_5.setOnClickListener {
            humanMove(circle_5,5)
        }
        circle_6.setOnClickListener {
            humanMove(circle_6,6)
        }
        circle_7.setOnClickListener {
            humanMove(circle_7,7)
        }
        circle_8.setOnClickListener {
            humanMove(circle_8,8)
        }
        circle_9.setOnClickListener {
            humanMove(circle_9,9)
        }
        circle_10.setOnClickListener {
            humanMove(circle_10,10)
        }
        circle_11.setOnClickListener {
            humanMove(circle_11,11)
        }

    }
    //勝利
    fun win() {
        win_pop.visibility = View.VISIBLE
        saveInt()
    }
    //敗北
    fun lose() {
        lose_pop.visibility = View.VISIBLE 
    }
    //    鬼の初期位置
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(start == 0){
            start = 1
            val demonFirstPsition =(5..10).random()
            setFirstDemon(circlePosition(demonFirstPsition))
            demon = demonFirstPsition
        }
    }
    override fun onStart() {
        super.onStart()
    }

//    鬼の初期位置
    fun setFirstDemon(circle: View){
        val demonPsition = circlePosition(11)
        val hrect = Rect()
        val crect = Rect()
        circle.getGlobalVisibleRect(crect)
        demonPsition.getGlobalVisibleRect(hrect)
        //実際に移動する
        val scaleX = ObjectAnimator.ofFloat(demon_body, "translationX", (crect.left - hrect.left).toFloat())
        val scaleY = ObjectAnimator.ofFloat(demon_body, "translationY",(crect.bottom - hrect.bottom).toFloat())
        val animatorSet = AnimatorSet()
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 0
        animatorSet.start()
    }
    //人の位置を取得
    fun setHumanPositon(position:Int){
        when(touch) {
            1 -> {
                people1 = position
            }
            2 -> {
                people2 = position
            }
            3 -> {
                people3 = position
            }
        }
    }
    //人の移動
    fun humanMove(circle:View,position: Int){
        turn = 1
        //人の場所
        val human = setHuman(touch)
        var people = circle_1
        when(touch){
            1->{
                people = circle_1
            }
            2->{
                people = circle_2
            }
            3->{
                people = circle_4
            }
        }
//        val humanPlace = circlePosition(people)

        val hrect = Rect()
        val crect = Rect()
        val o = Rect()
//        humanPlace.getGlobalVisibleRect(hrect)
//        human.getGlobalVisibleRect(hrect)
        circle.getGlobalVisibleRect(crect)
        people.getGlobalVisibleRect(hrect)


        //実際に移動する
        val scaleX = ObjectAnimator.ofFloat(human, "translationX", (crect.left - hrect.left).toFloat())
        val scaleY = ObjectAnimator.ofFloat(human, "translationY",(crect.bottom - hrect.bottom).toFloat())
        val animatorSet = AnimatorSet()
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 1000
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(p0: Animator?) {
                humanTurnEnd()
            }
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
        })
        animatorSet.start()


        setHumanPositon(position)
        circleInvisible()
    }

    //鬼の移動
    fun demionMove(circle:View,newPosition:Int){
        //人の場所
        val demonPsition = circlePosition(11)

        val hrect = Rect()
        val crect = Rect()
        circle.getGlobalVisibleRect(crect)
        demonPsition.getGlobalVisibleRect(hrect)

        //実際に移動する
        val scaleX = ObjectAnimator.ofFloat(demon_body, "translationX", (crect.left - hrect.left).toFloat())
        val scaleY = ObjectAnimator.ofFloat(demon_body, "translationY",(crect.bottom - hrect.bottom).toFloat())
        val animatorSet = AnimatorSet()
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 1000
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(p0: Animator?) {
                demonTurnEnd()
                touch = 0
                turn = 0
            }
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
        })
        animatorSet.start()

        demon = newPosition
    }
    //鬼のターン
    fun demonTurn(){
        var demonCanMove = mutableListOf<Int>()
        demonCanMove = demonMovrPosition()
        val min = calcMin(demonCanMove)
        val demonMovePsition = circlePosition(min)
        demionMove(demonMovePsition,min)
    }

    //勝利判定
    fun humanTurnEnd(){
        if(demon == 11 && (people1 + people2 + people3) == 27){
            win()
        }else{
            demonTurn()
        }
    }
    //敗北判定
    fun demonTurnEnd() {
        if (people1 > demon && people2 > demon && people3 > demon) {
            lose()
        }
    }


    //配列の最小値を返す
    fun calcMin(array: MutableList<Int>): Int {
        var intMin =0
        try {
            intMin = array[0]
        }catch (e: Exception){
            intMin = demon
        }
        if(array.size == 1){
            intMin = array[0]
        }else{
            for (i in 1 until array.size) {
                if (intMin > array[i]) {
                    intMin = array[i]
                }
            }
        }

        return intMin
    }
    //その他画面に触れた場合
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {

        if(turn == 0){
            circleInvisible()
            touch = 0
        }
        return false
    }

    //サークルを消す
    fun circleInvisible(){

        circle_1.visibility = View.INVISIBLE
        circle_2.visibility = View.INVISIBLE
        circle_3.visibility = View.INVISIBLE
        circle_4.visibility = View.INVISIBLE
        circle_5.visibility = View.INVISIBLE
        circle_6.visibility = View.INVISIBLE
        circle_7.visibility = View.INVISIBLE
        circle_8.visibility = View.INVISIBLE
        circle_9.visibility = View.INVISIBLE
        circle_10.visibility = View.INVISIBLE
        circle_11.visibility = View.INVISIBLE
    }

    //移動できるサークルを表示
    fun RangeOfMovement(position:Int){
        when (position) {
            1 -> {
                circle_2.visibility = View.VISIBLE
                circle_3.visibility = View.VISIBLE
                circle_4.visibility = View.VISIBLE
            }
            2 -> {
                circle_3.visibility = View.VISIBLE
                circle_5.visibility = View.VISIBLE
                circle_6.visibility = View.VISIBLE
            }
            3 -> {
                circle_2.visibility = View.VISIBLE
                circle_4.visibility = View.VISIBLE
                circle_6.visibility = View.VISIBLE
            }
            4 -> {
                circle_3.visibility = View.VISIBLE
                circle_6.visibility = View.VISIBLE
                circle_7.visibility = View.VISIBLE
            }
            5 -> {
                circle_6.visibility = View.VISIBLE
                circle_8.visibility = View.VISIBLE
            }
            6 -> {
                circle_5.visibility = View.VISIBLE
                circle_7.visibility = View.VISIBLE
                circle_8.visibility = View.VISIBLE
                circle_9.visibility = View.VISIBLE
                circle_10.visibility = View.VISIBLE
            }
            7 -> {
                circle_6.visibility = View.VISIBLE
                circle_10.visibility = View.VISIBLE
            }
            8 -> {
                circle_9.visibility = View.VISIBLE
                circle_11.visibility = View.VISIBLE
            }
            9 -> {
                circle_8.visibility = View.VISIBLE
                circle_10.visibility = View.VISIBLE
                circle_11.visibility = View.VISIBLE
            }
            10 -> {
                circle_9.visibility = View.VISIBLE
                circle_11.visibility = View.VISIBLE
            }
        }

        circlePosition(demon).visibility = View.INVISIBLE
    }

    //鬼の移動できるところ
    fun demonMovrPosition(): MutableList<Int> {
        var demonX =mutableListOf<Int>()
        when(demon){
            2 -> {
                demonX = mutableListOf<Int>(1,3,5,6)

            }
            3 -> {
                demonX = mutableListOf<Int>(1,2,4,6)

            }
            4 -> {
                demonX = mutableListOf<Int>(1,3,6,7)

            }
            5 -> {
                demonX = mutableListOf<Int>(2,6,8)

            }
            6 -> {
                demonX = mutableListOf<Int>(2,3,4,5,7,8,9,10)

            }
            7 -> {
                demonX = mutableListOf<Int>(4,6,10)

            }
            8 -> {
                demonX = mutableListOf<Int>(5,6,9,11)

            }
            9 -> {
                demonX = mutableListOf<Int>(6,8,10,11)

            }
            10 -> {
                demonX = mutableListOf<Int>(6,7,9,11)

            }
            11 -> {
                demonX = mutableListOf<Int>(8,9,10)

            }
            else->{
                demonX = mutableListOf<Int>(demon)
            }
        }

        demonX.remove(people1)
        demonX.remove(people2)
        demonX.remove(people3)
        return demonX
    }

    //位置を返す
    fun circlePosition(position:Int): View {
        var circle:View = circle_6
         when (position){
             1 -> {
                 circle = circle_1
             }
             2 -> {
                 circle = circle_2
             }
             3 -> {
                 circle = circle_3
             }
             4 -> {
                 circle = circle_4
             }
             5 -> {
                 circle = circle_5
             }
             6 -> {
                 circle = circle_6
             }
             7 -> {
                 circle = circle_7
             }
             8 -> {
                 circle = circle_8
             }
             9 -> {
                 circle = circle_9
             }
             10 -> {
                 circle = circle_10
             }
             11 -> {
                 circle = circle_11
             }
        }
        return circle
    }
    //人を返す
    fun setHuman(position:Int): View {
        var human:View = human2
        when (position){
            1 -> {
                human = human1
            }
            2 -> {
                human = human2
            }
            3 -> {
                human = human3
            }
        }
        return human
    }
    fun saveInt(){
            val pref = getSharedPreferences("my_settings", Context.MODE_PRIVATE)
            val saveInt = pref.getInt("trofy", 0)
            val newInt = saveInt + 1
            getSharedPreferences("my_settings", Context.MODE_PRIVATE).edit().apply {
                putInt("trofy", newInt)
                apply()
            }
    }



    override fun onDestroy  () {
        super.onDestroy()
        mp0.release()
    }
}













