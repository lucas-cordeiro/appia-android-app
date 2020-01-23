package br.com.lucascordeiro.core.ui.util

import android.content.Context
import android.graphics.*
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import androidx.core.content.ContextCompat
import br.com.lucascordeiro.core.R
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.jetbrains.anko.layoutInflater
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


class CommonsUtil {
    companion object{
        fun isOnline(): Boolean {
            try {
                val timeoutMs = 1500
                val sock = Socket()
                val sockaddr = InetSocketAddress("8.8.8.8", 53)

                sock.connect(sockaddr, timeoutMs)
                sock.close()

                return true
            } catch (e: IOException) {
                return false
            }

        }

        suspend fun isOnlineAsync() = withContext(IO){
            async {
                isOnline()
            }
        }

        fun getPx(context: Context, dip: Float) : Float{
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                context.resources.displayMetrics
            )
        }

        fun isValidPassword(password: String): Boolean {

            val pattern: Pattern
            val matcher: Matcher

            val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

            pattern = Pattern.compile(PASSWORD_PATTERN)
            matcher = pattern.matcher(password)

            return matcher.matches()

        }


        // Return true if the card number is valid
        fun isValidCreditCard(number: Long): Boolean {
            return getSize(number) >= 13 && getSize(
                number
            ) <= 16 &&
                    ( prefixMatched(number, 4) ||
                            prefixMatched(number, 5) ||
                            prefixMatched(number, 37) ||
                            prefixMatched(number, 6)) &&
                    (sumOfDoubleEvenPlace(number) + sumOfOddPlace(
                        number
                    )) % 10 == 0
        }

        // Get the result from Step 2
        fun sumOfDoubleEvenPlace(number: Long): Int {
            var sum = 0
            val num = number.toString() + ""
            var i = getSize(number) - 2
            while (i >= 0) {
                sum += getDigit(Integer.parseInt(num[i] + "") * 2)
                i -= 2
            }

            return sum
        }

        // Return this number if it is a single digit, otherwise,
        // return the sum of the two digits
        fun getDigit(number: Int): Int {
            return if (number < 9) number else number / 10 + number % 10
        }

        // Return sum of odd-place digits in number
        fun sumOfOddPlace(number: Long): Int {
            var sum = 0
            val num = number.toString() + ""
            var i = getSize(number) - 1
            while (i >= 0) {
                sum += Integer.parseInt(num[i] + "")
                i -= 2
            }
            return sum
        }

        // Return true if the digit d is a prefix for number
        fun prefixMatched(number: Long, d: Int): Boolean {
            return getPrefix(
                number,
                getSize(d.toLong())
            ) == d.toLong()
        }

        // Return the number of digits in d
        fun getSize(d: Long): Int {
            val num = d.toString() + ""
            return num.length
        }

        // Return the first k number of digits from
        // number. If the number of digits in number
        // is less than k, return number.
        fun getPrefix(number: Long, k: Int): Long {
            if (getSize(number) > k) {
                val num = number.toString() + ""
                return java.lang.Long.parseLong(num.substring(0, k))
            }
            return number
        }

        private val pesoCPF = intArrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2)
        private val pesoCNPJ = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

        private fun calcularDigito(str: String, peso: IntArray): Int {
            var soma = 0
            var indice = str.length - 1
            var digito: Int
            while (indice >= 0) {
                digito = Integer.parseInt(str.substring(indice, indice + 1))
                soma += digito * peso[peso.size - str.length + indice]
                indice--
            }
            soma = 11 - soma % 11
            return if (soma > 9) 0 else soma
        }
        fun convertPxToDp(context: Context, dip: Float): Float {
            val r = context.getResources()
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
            )
            return px
        }

        fun isValidCPF(_cpf: String): Boolean {
            val cpf = _cpf.unMaskOnlyNumbers()
            if (cpf.length != 11 || cpf == "00000000000" || cpf == "11111111111" ||
                cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" || cpf == "55555555555" ||
                cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf == "99999999999") {
                return false
            }

            val digito1 = calcularDigito(
                cpf.substring(0, 9),
                pesoCPF
            )
            val digito2 = calcularDigito(
                cpf.substring(0, 9) + digito1,
                pesoCPF
            )
            return cpf == cpf.substring(0, 9) + digito1.toString() + digito2.toString()
        }

        fun isValidCNPJ(cnpj: String): Boolean {
            if (cnpj.length != 14 || cnpj == "00000000000000" || cnpj == "11111111111111" ||
                cnpj == "22222222222222" || cnpj == "33333333333333" || cnpj == "44444444444444" || cnpj == "55555555555555" ||
                cnpj == "66666666666666" || cnpj == "77777777777777" || cnpj == "88888888888888" || cnpj == "99999999999999") {
                return false
            }

            val digito1 = calcularDigito(
                cnpj.substring(0, 12),
                pesoCNPJ
            )
            val digito2 = calcularDigito(
                cnpj.substring(0, 12) + digito1,
                pesoCNPJ
            )
            return cnpj == cnpj.substring(0, 12) + digito1.toString() + digito2.toString()
        }

        fun getAndroidVersion(sdkInt: Int) : String {
            return when(sdkInt){
                17 -> "4.2"
                18 -> "4.3"
                19 -> "4.4"
                20 -> "4.4"
                21 -> "5.0"
                22 -> "5.1"
                23 -> "6.0"
                24 -> "7.0"
                25 -> "7.1"
                26 -> "8.0"
                27 -> "8.1"
                28 -> "9.0"
                else -> sdkInt.toString()
            }
        }

        fun showCustomTost(context: Context, message: String) {
            val inflater = context.layoutInflater
            val layout = inflater.inflate(R.layout.custom_toast_result, null)

            val image = layout.findViewById(R.id.toastCustomImage) as ImageView
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_error)!!
//            val wrappedDrawable = DrawableCompat.wrap(drawable)
//            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, if (success) R.color.gray else R.color.blue))
            image.setImageDrawable(drawable)

            val cardView = layout.findViewById(R.id.toastCustomLayout) as CardView
//            cardView.setCardBackgroundColor(ContextCompat.getColor(context, if (success) R.color.gray else R.color.blue))

            val text = layout.findViewById(R.id.toastCustomText) as TextView
            text.text = message
//            text.setTextColor(ContextCompat.getColor(context, if (success) R.color.gray else R.color.blue))

            val toast = Toast(context)
            /*toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)*/
            toast.duration = Toast.LENGTH_LONG
            toast.view = layout
            toast.show()
        }
    }

}