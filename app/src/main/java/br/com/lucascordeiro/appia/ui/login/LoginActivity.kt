package br.com.lucascordeiro.appia.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.lucascordeiro.appia.R
import br.com.lucascordeiro.appia.ui.base.BaseActivity
import br.com.lucascordeiro.appia.ui.main.MainActivity
import br.com.lucascordeiro.core.ui.util.CommonsUtil
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginMvpView {

    @Inject
    lateinit var presenter: LoginMvpPresenter<LoginMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        activityComponent.inject(this)
        presenter.onAttach(this)
        setUp()
    }

    override fun setUp() {
        txtPasswordForget.setOnClickListener {
            showSnackError("Função ainda não implementada :(")
        }

        btnSend.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if(CommonsUtil.isValidEmail(email)){
                if(CommonsUtil.isValidPassword(password)){
                    showLoading()
                    presenter.doSingIn(email, password)
                }else{
                    showSnackError("Senha inválida")
                }
            }else{
                showSnackError("E-mail inválido")
            }
        }
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        btnSend.isEnabled = true
        edtEmail.isEnabled = true
        edtPassword.isEnabled = true
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        btnSend.isEnabled = false
        edtEmail.isEnabled = false
        edtPassword.isEnabled = false
    }

    override fun onSingIn() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onSingInFail(message: String?) {
        showSnackError(message?:"Falha ao realizar login")
        hideLoading()
    }

    override fun onSingInNetworkError() {
        showSnackError("Não foi possível se conectar, verifique sua conexão e tente novamente")
        hideLoading()
    }
}
