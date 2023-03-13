package uz.gita.my4pics1oneword.ui.main

import uz.gita.my4pics1oneword.model.QuestionData
import java.text.FieldPosition

interface MainContract {
    interface Model {
        fun getCurrentAnswer(currentPosition: Int): QuestionData
        fun getCurrentPosition(): Int
    }

    interface View {
        fun describeQuestion()
    }

    interface Presenter {
        fun loadQuestion()
        fun getCurrentAnswer(currentPosition: Int): QuestionData
        fun getCurrentPosition(): Int
    }
}