package uz.gita.my4pics1oneword.ui.game

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialDialogs
import uz.gita.my4pics1oneword.R
import uz.gita.my4pics1oneword.model.QuestionData
import uz.gita.my4pics1oneword.ui.main.MainActivity

class GameActivity : AppCompatActivity(), GameContract.View {
    private val imagesList = ArrayList<AppCompatImageView>(4)
    private val answerButtons = ArrayList<AppCompatTextView>(8)
    private val variantButtons = ArrayList<AppCompatTextView>(16)
    private lateinit var coin: AppCompatTextView
    private var counter = 0

    private lateinit var answerLine: LinearLayoutCompat
    private lateinit var animation: Animation

    private val presenter: GameContract.Presenter = GamePresenter(this)
    private var currentPosition: Int = presenter.getCurrentPosition()

    private lateinit var tvLevel: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.navigationBarColor = resources.getColor(R.color.navigationBarColor)

        answerLine = findViewById(R.id.answerLine)

        loadViews()

        clickVariantButtons()

        clickAnswerButtons()

        presenter.loadNextQuestion()

        findViewById<AppCompatImageView>(R.id.buttonBack).setOnClickListener {
            finish()
        }

        findViewById<AppCompatImageView>(R.id.lamp).setOnClickListener {

            var answer = ""
            answerButtons.forEach {
                answer += it.text.toString()
            }

            if (presenter.getCash() >= 60 && answer.length != presenter.getCurrentAnswer().answer.length) {
                showEnoughMoneyDialog()
            } else {
                showLessMoneyDialog()
            }
        }
    }

    private fun loadViews() {
        coin = findViewById(R.id.dollar)
        tvLevel = findViewById(R.id.tv_level)
        imagesList.add(findViewById(R.id.imageOne))
        imagesList.add(findViewById(R.id.imageTwo))
        imagesList.add(findViewById(R.id.imageThree))
        imagesList.add(findViewById(R.id.imageFour))

        val answerLine = findViewById<LinearLayoutCompat>(R.id.answerLine)

        for (i in 0 until answerLine.childCount) {
            answerButtons.add(answerLine.getChildAt(i) as AppCompatTextView)
        }

        val variantLine1 = findViewById<ConstraintLayout>(R.id.variantLine1)

        for (i in 0 until variantLine1.childCount) {
            variantButtons.add(variantLine1.getChildAt(i) as AppCompatTextView)
        }

        val variantLine2 = findViewById<ConstraintLayout>(R.id.variantLine2)

        for (i in 0 until variantLine2.childCount) {
            variantButtons.add(variantLine2.getChildAt(i) as AppCompatTextView)
        }
    }


    private fun clickVariantButtons() {
        for (i in variantButtons.indices) {
            variantButtons[i].setOnClickListener {
                counter++
                presenter.btnVariantClicked(variantButtons[i].text.toString(), i)
                if (counter == presenter.getCurrentAnswer().answer.length) {

                    variantButtons.forEach {
                        it.isClickable = false
                    }

                    var text = ""
                    answerButtons.forEach { txt ->
                        text += txt.text.toString()
                    }
                    checkAnswer(text)
                }
            }
        }
    }

    private fun checkAnswer(text: String) {
        if (text == presenter.getCurrentAnswer().answer) {
            presenter.addCash()
            presenter.showCash()
            MediaPlayer.create(this, R.raw.popolnen_balance).start()
            currentPosition++
            presenter.setCurrentPosition(currentPosition)
            if (presenter.getCurrentPosition() < 10) {
                clearData()
                presenter.loadNextQuestion()
            } else {
                showFinishDialog()
            }
        } else {
            animation = AnimationUtils.loadAnimation(this, R.anim.animation_shake)
            answerLine.startAnimation(animation)
        }
    }

    private fun clickAnswerButtons() {
        for (i in answerButtons.indices) {
            answerButtons[i].setOnClickListener {
                counter--
                variantButtons.forEach {
                    it.isClickable = true
                }

                val tagPos = answerButtons[i].tag as Int
                presenter.btnAnswerClicked(answerButtons[i].text.toString(), i, tagPos)

            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun describeQuestion(question: QuestionData) {

        coin.text = presenter.getCash().toString()
        tvLevel.text = presenter.getCurrentAnswer().id.toString()
        imagesList[0].setImageResource(question.image1)
        imagesList[1].setImageResource(question.image2)
        imagesList[2].setImageResource(question.image3)
        imagesList[3].setImageResource(question.image4)

        for (i in variantButtons.indices) {
            variantButtons[i].text = question.variant[i].toString()
            variantButtons[i].isEnabled = true
        }
        for (i in answerButtons.indices) {
            answerButtons[i].text = ""
            answerButtons[i].isEnabled = false
        }


        resizeAnswerButton(question.answer)
        describeVariantButtons(question.variant)
    }

    private fun clearData() {
        answerButtons.forEach {
            if (it.isVisible) {
                it.text = ""
            }
        }

        variantButtons.forEach {
            it.text = ""
            it.isClickable = true
        }
        counter = 0
    }


    override fun getFirstEmptyPosition(): Int {
        for (i in answerButtons.indices) {
            if (answerButtons[i].text.isEmpty() && answerButtons[i].visibility == View.VISIBLE) {
                return i
            }
        }
        return -1
    }

    override fun showCash(cash: Int) {
        coin.text = cash.toString()
    }

    override fun showValueToAnswer(text: String, position: Int) {
        val index = getFirstEmptyPosition()
        if (index != -1) {
            answerButtons[index].text = text
            answerButtons[index].tag = position
            variantButtons[position].text = ""
            variantButtons[position].isEnabled = false
            answerButtons[index].isEnabled = true
        }
    }

    override fun showValueToVariant(text: String, index: Int, tagPos: Int) {
        variantButtons[tagPos].text = text
        variantButtons[tagPos].isEnabled = true
        answerButtons[index].text = ""
        answerButtons[index].isEnabled = false
    }

    private fun resizeAnswerButton(answer: String) {
        for (i in answer.indices) {
            answerButtons[i].visibility = View.VISIBLE
        }

        for (i in answer.length until answerButtons.size) {
            answerButtons[i].visibility = View.GONE
        }
    }

    private fun describeVariantButtons(variant: String) {
        for (i in variant.indices) {
            variantButtons[i].text = variant[i].toString()
        }
    }


    private fun showEnoughMoneyDialog() {

        val dialogEnough = Dialog(this)
        dialogEnough.setContentView(R.layout.item_dialog_enough)
        dialogEnough.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogEnough.setCancelable(true)
        dialogEnough.show()

        dialogEnough.findViewById<AppCompatButton>(R.id.yes).setOnClickListener {
            presenter.cutCash()
            presenter.showCash()
            MediaPlayer.create(this, R.raw.cash_register).start()
            dialogEnough.dismiss()

            var answer = ""
            answerButtons.forEach {
                answer += it.text.toString()
            }

            val currentAnswer = presenter.getCurrentAnswer().answer
            val helperLetter = findViewById<AppCompatTextView>(R.id.helper_text)

            helperLetter.text = currentAnswer.substring(answer.length, answer.length + 1)

            val hugeSmallAnim = AnimationUtils.loadAnimation(this, R.anim.hugesmall)

            hugeSmallAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    helperLetter.isVisible = true
                }

                override fun onAnimationEnd(p0: Animation?) {
                    helperLetter.isVisible = false
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })

            helperLetter.startAnimation(hugeSmallAnim)

        }


        dialogEnough.findViewById<AppCompatButton>(R.id.notNow).setOnClickListener {
            dialogEnough.dismiss()
        }
    }

    private fun showLessMoneyDialog() {
        val dialogLess = Dialog(this)
        dialogLess.setContentView(R.layout.item_dialog_less)
        dialogLess.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogLess.setCancelable(true)
        dialogLess.show()

        dialogLess.findViewById<AppCompatButton>(R.id.ok).setOnClickListener {
            dialogLess.dismiss()
        }
    }

    private fun showFinishDialog() {
        val dialogFinish = Dialog(this)
        dialogFinish.setContentView(R.layout.item_dialog_finish)
        dialogFinish.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogFinish.setCancelable(false)
        dialogFinish.show()

        val restartButton = dialogFinish.findViewById<AppCompatButton>(R.id.restart)
        val quitButton = dialogFinish.findViewById<AppCompatButton>(R.id.quit)
        val levelTextView = dialogFinish.findViewById<AppCompatTextView>(R.id.level)
        val coinTextView = dialogFinish.findViewById<AppCompatTextView>(R.id.coin)

        levelTextView.text = presenter.getCurrentPosition().toString()
        coinTextView.text = presenter.getCash().toString()

        restartButton.setOnClickListener {
            presenter.setCurrentPosition(0)
            dialogFinish.dismiss()
            presenter.setCoin()
            finish()
        }

        quitButton.setOnClickListener {
            dialogFinish.dismiss()
            presenter.setCurrentPosition(0)
            presenter.setCoin()
            finishAffinity()
        }
    }
}