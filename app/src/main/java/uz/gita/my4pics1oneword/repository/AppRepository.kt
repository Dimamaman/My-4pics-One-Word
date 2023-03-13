package uz.gita.my4pics1oneword.repository

import android.util.Log
import uz.gita.my4pics1oneword.R
import uz.gita.my4pics1oneword.data.sharedpref.SharedPrefer
import uz.gita.my4pics1oneword.model.QuestionData

class AppRepository private constructor() {

    private var sharedPref = SharedPrefer.getInstance()

    companion object {
        private lateinit var instance: AppRepository

        fun getInstance(): AppRepository {
            if (!(::instance.isInitialized)) {
                instance = AppRepository()
            }
            return instance
        }
    }

    val list = ArrayList<QuestionData>()

    init {
        load();
    }

    private fun load() {
        list.add(
            QuestionData(
                1,
                R.drawable.color_1_1_black,
                R.drawable.color_1_2_black,
                R.drawable.color_1_3_black,
                R.drawable.color_1_4_black,
                "AFBKFDGLHIOPITCK",
                "BLACK"
            )
        )

        list.add(
            QuestionData(
                2,
                R.drawable.color_2_1_white,
                R.drawable.color_2_2_white,
                R.drawable.color_2_3_white,
                R.drawable.color_2_4_white,
                "EAFBTWKFDHPIITCK",
                "WHITE"
            )
        )

        list.add(
            QuestionData(
                3,
                R.drawable.color_3_1_green,
                R.drawable.color_3_2_green,
                R.drawable.color_3_3_green,
                R.drawable.color_3_4_green,
                "NGAFRBTWKEIETNCK",
                "GREEN"
            )
        )

        list.add(
            QuestionData(
                4,
                R.drawable.color_4_1_blue,
                R.drawable.color_4_2_blue,
                R.drawable.color_4_3_blue,
                R.drawable.color_4_4_blue,
                "NBGALFRBEIEUTNCK",
                "BLUE"
            )
        )

        list.add(
            QuestionData(
                5,
                R.drawable.color_5_1_yellow,
                R.drawable.color_5_2_yellow,
                R.drawable.color_5_3_yellow,
                R.drawable.color_5_4_yellow,
                "YNBGEOAEULTNLCKW",
                "YELLOW"
            )
        )

        list.add(
            QuestionData(
                6,
                R.drawable.color_6_1_pink,
                R.drawable.color_6_2_pink,
                R.drawable.color_6_3_pink,
                R.drawable.color_6_4_pink,
                "YNBIGENOPAEULKTN",
                "PINK"
            )
        )

        list.add(
            QuestionData(
                7,
                R.drawable.color_7_1_purple,
                R.drawable.color_7_2_purple,
                R.drawable.color_7_3_purple,
                R.drawable.color_7_4_purple,
                "YNUBIRPEULPKLTEN",
                "PURPLE"
            )
        )

        list.add(
            QuestionData(
                8,
                R.drawable.color_8_1_orange,
                R.drawable.color_8_2_orange,
                R.drawable.color_8_3_orange,
                R.drawable.color_8_4_orange,
                "EBIRNPEGAULRPOEN",
                "ORANGE"
            )
        )

        list.add(
            QuestionData(
                9,
                R.drawable.color_9_1_grey,
                R.drawable.color_9_2_grey,
                R.drawable.color_9_3_grey,
                R.drawable.color_9_4_grey,
                "RNRPEGAULREPOYEN",
                "GREY"
            )
        )

        list.add(
            QuestionData(
                10,
                R.drawable.color_10_1_brown,
                R.drawable.color_10_2_brown,
                R.drawable.color_10_3_brown,
                R.drawable.color_10_4_brown,
                "WRPENGOAUBLOYERN",
                "BROWN"
            )
        )
    }

    fun setCurrentPosition(currentPos: Int) {
        Log.d("RRRR","setCurrentPosition -> $currentPos")
        sharedPref.currentPosition = currentPos
    }

    fun getCurrentPosition(): Int {
        return sharedPref.currentPosition
    }

    fun addCash() {
        sharedPref.cash += 50
    }

    fun getCash(): Int {
        return sharedPref.cash
    }

    fun cutCash() {
        sharedPref.cash -= 60
    }

    fun setFinishCurrentPosition(position: Int) {
        sharedPref.currentPosition = position
    }

    fun setCoin() {
        sharedPref.cash = 100
    }
}