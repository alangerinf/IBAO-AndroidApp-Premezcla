package com.ibao.premescla.ui.mod3.pushone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ibao.premescla.R
import com.ibao.premescla.models.Muestra
import kotlinx.android.synthetic.main.activity_selector.*

class SelectorActivity : AppCompatActivity() {


    private val BUNDLE: Bundle by lazy { intent.extras!! }
    private val ID_TANCADA: Int by lazy { BUNDLE.getInt("extra_id_tancada") }
    private val  presenter: SelectorPresenter by lazy { SelectorPresenter(this@SelectorActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selector)

        btnSave.setOnClickListener {

            if(eTextLines.text.toString().isEmpty()){
                eTextLines.setText("0");
            }
            if(eTextDistance.text.toString().isEmpty()){
                eTextDistance.setText("0");
            }
            if(eTextTime.text.toString().isEmpty()){
                eTextTime.setText("0");
            }
            val str_lines = eTextLines.text.toString()
            val str_distance = eTextDistance.text.toString()
            val str_time = eTextTime.text.toString()


            val lines = str_lines.toInt()
            val distance = str_distance.toFloat()


            var flag1= true
            if(lines<=0){
                eTextLines.setError("Valor no valido")
                flag1 = false
            }

            var flag2= true
            if(distance<=0){
                eTextDistance.setError("Valor no valido")
                flag2 = false
            }
            var flag3= true
            if(str_time.isEmpty() || str_time.toInt()==0){
                eTextTime.setError("Valor no valido")
                flag3 = false
            }

            if(flag1 && flag2 && flag3){
                val m = Muestra()
                m.idTancada = ID_TANCADA
                m.distancy = distance.toDouble()
                m.duration = str_time
                m.lines = lines
                presenter.requestPushMuestra(m)
            }

        }

        }

    val TAG = SelectorActivity::class.java.simpleName
    fun showError(fail: String) {
        Log.d(TAG,fail)
        Toast.makeText(this@SelectorActivity,fail,Toast.LENGTH_SHORT).show()
    }

    fun showSuccess() {

        Toast.makeText(this@SelectorActivity,"Muestra Guardada",Toast.LENGTH_SHORT).show()
        finish()
    }
}
