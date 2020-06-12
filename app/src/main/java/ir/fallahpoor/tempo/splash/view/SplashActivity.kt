package ir.fallahpoor.tempo.splash.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.MainActivity
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.common.*
import ir.fallahpoor.tempo.splash.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private val splashViewModel: SplashViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {

        injectViewModelFactory()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        observeViewModel()
        splashViewModel.getAccessToken()

    }

    private fun injectViewModelFactory() {
        (application as TempoApplication).appComponent.inject(this)
    }

    private fun observeViewModel() {
        splashViewModel.getViewState().observe(this,
            Observer { viewState ->
                when (viewState) {
                    is LoadingState -> showLoading()
                    is DataLoadedState -> {
                        hideLoading()
                        launchMainActivity()
                    }
                    is ErrorState -> {
                        hideLoading()
                        showErrorSnackbar(viewState.errorMessage)
                    }
                }
            })
    }

    private fun showLoading() {
        splashProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        splashProgressBar.visibility = View.GONE
    }

    private fun launchMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(appNameTextView, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) {
                splashViewModel.getAccessToken()
            }
            .show()
    }

}
