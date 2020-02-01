package pl.szymonmazanik.applover_recruitment_task.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.szymonmazanik.applover_recruitment_task.BuildConfig
import pl.szymonmazanik.applover_recruitment_task.R
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree()) // Plant Tiber only for DEBUG
    }
}
