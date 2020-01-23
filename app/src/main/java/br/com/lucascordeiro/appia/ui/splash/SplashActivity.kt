package br.com.lucascordeiro.appia.ui.splash

import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.core.view.ViewCompat
import br.com.lucascordeiro.appia.R
import br.com.lucascordeiro.appia.ui.base.BaseActivity
import br.com.lucascordeiro.appia.ui.login.LoginActivity
import br.com.lucascordeiro.appia.ui.main.MainActivity
import br.com.lucascordeiro.core.ui.util.CommonsUtil.Companion.convertPxToDp
import br.com.lucascordeiro.core.ui.util.HAS_USER
import br.com.lucascordeiro.core.ui.util.TOKEN_ACCESS
import br.com.lucascordeiro.core.ui.util.showItemsWithMove
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import kotlin.concurrent.schedule

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

        ViewCompat.animate(txtLogo)
            .translationY(-convertPxToDp(this, 70f))
            .setStartDelay(300L)
            .setDuration(600L).setInterpolator(
                DecelerateInterpolator(1.5f)
            ).start()

        container.showItemsWithMove()

        Timer("delay").schedule(1000L){
            runOnUiThread {
                if(Prefs.getBoolean(HAS_USER, false)){
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }else{
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
                finish()
            }
        }
    }

    override fun hideLoading() {

    }

    override fun showLoading() {

    }
}
