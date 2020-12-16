package shady.samir.logappstask.DesingTask

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import shady.samir.logappstask.R

class DesignTaskActivity : AppCompatActivity() {

    lateinit var toolbar:Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design_task)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_courses, R.id.navigation_search, R.id.navigation_home, R.id.navigation_chat, R.id.navigation_account
            )
        )
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}