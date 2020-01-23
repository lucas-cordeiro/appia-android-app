package br.com.lucascordeiro.appia.ui.splash

import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.lucascordeiro.appia.R
import br.com.lucascordeiro.appia.ui.base.BaseActivity
import br.com.lucascordeiro.appia.ui.main.MainActivity
import br.com.lucascordeiro.core.ui.util.HAS_USER
import br.com.lucascordeiro.core.ui.util.TOKEN_ACCESS
import com.pixplicity.easyprefs.library.Prefs

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()


        setUp()
    }

    override fun setUp() {
        Prefs.putString(TOKEN_ACCESS, "appia-token")
        if(Prefs.getBoolean(HAS_USER, false)){

        }else{

        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun hideLoading() {

    }

    override fun showLoading() {

    }
}
