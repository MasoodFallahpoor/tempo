package ir.fallahpoor.tempo.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.MainActivity
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.common.DataErrorViewState
import ir.fallahpoor.tempo.common.DataLoadedViewState
import ir.fallahpoor.tempo.splash.viewmodel.SplashViewModel
import ir.fallahpoor.tempo.splash.viewmodel.SplashViewModelFactory
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    internal lateinit var splashViewModelFactory: SplashViewModelFactory

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        injectViewModelFactory()
        setupViewModel()
        observeViewModel()

    }

    private fun injectViewModelFactory() {
        (application as TempoApplication).appComponent.inject(this)
    }

    private fun setupViewModel() {
        splashViewModel = ViewModelProviders.of(this, splashViewModelFactory)
            .get(SplashViewModel::class.java)
    }

    private fun observeViewModel() {
        splashViewModel.getAccessToken().observe(this,
            Observer { viewState ->
                run {
                    when (viewState) {
                        is DataLoadedViewState<*> -> launchMainActivity()
                        is DataErrorViewState -> showErrorSnackbar(viewState.getMessage())
                    }
                }
            })
    }

    private fun launchMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(appNameTextView, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) {
                observeViewModel()
            }
            .show()
    }

}
