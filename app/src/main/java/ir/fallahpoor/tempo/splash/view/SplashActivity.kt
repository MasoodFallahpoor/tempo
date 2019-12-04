package ir.fallahpoor.tempo.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ir.fallahpoor.tempo.MainActivity
import ir.fallahpoor.tempo.R
import ir.fallahpoor.tempo.app.TempoApplication
import ir.fallahpoor.tempo.common.ViewModelFactory
import ir.fallahpoor.tempo.common.ViewState
import ir.fallahpoor.tempo.splash.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

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
        splashViewModel = ViewModelProvider(this, viewModelFactory)
            .get(SplashViewModel::class.java)
    }

    private fun observeViewModel() {
        splashViewModel.accessToken.observe(this,
            Observer { viewState ->
                when (viewState) {
                    is ViewState.DataLoaded<*> -> launchMainActivity()
                    is ViewState.Error -> showErrorSnackbar(viewState.errorMessage)
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
