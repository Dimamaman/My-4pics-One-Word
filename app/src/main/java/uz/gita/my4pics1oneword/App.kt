package uz.gita.my4pics1oneword

import android.app.Application
import uz.gita.my4pics1oneword.data.sharedpref.SharedPrefer

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPrefer.init(this)
    }
}