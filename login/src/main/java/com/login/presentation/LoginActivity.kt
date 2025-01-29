package com.login.presentation

import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.core.utils.Router
import com.login.R
import com.login.data.DataState
import com.login.databinding.ActivityLoginBinding
import com.login.di.LoginDepsProvider
import com.login.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDI()
        setUi()

        binding.loginButton.setOnClickListener {
            loginUser()
        }

        lifecycleScope.launch {
            onResultMessage()
        }
    }

    private fun loginUser() {
        viewModel.loginUser(getLogin(), getPassword())
    }

    private suspend fun onResultMessage() {
        viewModel.resultMessage.collect { result ->
            when (result) {
                is DataState.ErrorState -> {
                    showToast(result.errorMessage)
                }

                DataState.NormalState -> {
                    router.navigateToMainActivity(this@LoginActivity)
                    finish()
                }
            }
        }
    }

    private fun initDI() {
        val loginComponent = (applicationContext as LoginDepsProvider).getLoginComponent()
        loginComponent.inject(this)
        loginComponent.inject(viewModel)
    }

    private fun setUi() {
        setLoginEditTextSettings()
        setPasswordEditTextSettings()
    }

    private fun setPasswordEditTextSettings() {
        with(binding.passwordEditTextWithIcon) {
            setImageResource(R.drawable.password_icon)
            setHint(resources.getString(R.string.password_edit_text_hint))
            setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
        }
    }

    private fun setLoginEditTextSettings() {
        with(binding.loginEditTextWithIcon) {
            setImageResource(R.drawable.username_icon)
            setHint(resources.getString(R.string.email_edit_text_hint))
            setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        }
    }

    private fun getLogin(): String = binding.loginEditTextWithIcon.getText()
    private fun getPassword(): String = binding.passwordEditTextWithIcon.getText()
    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}