package cr.ac.utn.taskmanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import cr.ac.utn.taskmanager.util.util

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnMessage1: Button = findViewById<Button>(R.id.btnMessage1_main)
        btnMessage1.setOnClickListener(View.OnClickListener{view ->
        Toast.makeText(this,
            getString(R.string.TextMessage1),
            Toast.LENGTH_LONG).show()
        })
        val clmain = findViewById<ConstraintLayout>(R.id.main)
        class snackbarClick: View.OnClickListener{
            override fun onClick(v: View?) {
                Snackbar.make(clmain,"Cancelled action",
                    Snackbar.LENGTH_LONG).show()
            }

        }

        val btnMessage2: Button = findViewById<Button>(R.id.btnMessage2_main)
        btnMessage2.setOnClickListener(View.OnClickListener{view ->
            val mySnackbar = Snackbar.make(clmain,
                                           R.string.TextMessage2,
                                        Snackbar.LENGTH_LONG)
            mySnackbar.setAction(R.string.TextSnackbar,
                                snackbarClick())
            mySnackbar.show()
        })

        val btnOpenScreen: Button = findViewById<Button>(R.id.btnOpenScreen_main)
        btnOpenScreen.setOnClickListener(View.OnClickListener{view ->
            util.OpenActivity(this,
                                    classActivity = SecondActivity::class.java)


        })

        val btnOpenTask: Button = findViewById<Button>(R.id.btnTask_main)
        btnOpenTask.setOnClickListener(View.OnClickListener{view ->
            util.OpenActivity(this,
                classActivity = TaskActivity::class.java)

        })


        val btnOpenTaskList: Button = findViewById<Button>(R.id.btnTaskList_main)
        btnOpenTaskList.setOnClickListener(View.OnClickListener{view ->
            util.OpenActivity(this,
                classActivity = TaskListActivity::class.java)


        })
        }
}