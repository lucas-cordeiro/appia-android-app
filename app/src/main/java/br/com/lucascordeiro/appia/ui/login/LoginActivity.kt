package br.com.lucascordeiro.appia.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.lucascordeiro.appia.R
import br.com.lucascordeiro.appia.ui.base.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUp()
    }

    override fun setUp() {

    }

    override fun hideLoading() {

    }

    override fun showLoading() {

    }
}
