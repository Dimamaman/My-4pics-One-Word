package uz.gita.my4pics1oneword.ui.main

import uz.gita.my4pics1oneword.model.QuestionData
import uz.gita.my4pics1oneword.repository.AppRepository

class MainModel : MainContract.Model {
    private val repository = AppRepository.getInstance()

    private val list = ArrayList<QuestionData>()

    init {
        list.addAll(repository.list)
    }

    override fun getCurrentAnswer(currentPosition: Int): QuestionData {
        return if (currentPosition < list.size) {
            list[currentPosition]
        } else {
            list[0]
        }
    }

    override fun getCurrentPosition(): Int {
        return repository.getCurrentPosition()
    }
}
