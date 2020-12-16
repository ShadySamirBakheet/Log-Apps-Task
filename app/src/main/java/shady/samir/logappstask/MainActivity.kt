package shady.samir.logappstask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import shady.samir.logappstask.DesingTask.DesignTaskActivity

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = Handler();
        handler.postDelayed(Runnable {
            next()
        },2000)
    }

    fun next(){

            startActivity( Intent(this,
                    DesignTaskActivity::class.java))
            finish()

    }
}