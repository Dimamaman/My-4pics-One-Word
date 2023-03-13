package uz.gita.my4pics1oneword.ui.main

import uz.gita.my4pics1oneword.model.QuestionData

class MainPresenter(
    private val view: MainContract.View
) : MainContract.Presenter {
    private val model = MainModel()

    override fun loadQuestion() {
        view.describeQuestion()
    }

    override fun getCurrentAnswer(currentPosition: Int): QuestionData {
        return model.getCurrentAnswer(currentPosition)
    }

    override fun getCurrentPosition(): Int {
        return model.getCurrentPosition()
    }
}