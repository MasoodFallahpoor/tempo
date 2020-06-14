package ir.fallahpoor.tempo.splash.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.fallahpoor.tempo.main.MainActivity
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.common.DataLoadedState
import ir.fallahpoor.tempo.common.ErrorState
import ir.fallahpoor.tempo.common.LoadingState
import ir.fallahpoor.tempo.splash.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        observeViewModel()
        splashViewModel.getAccessToken()

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
