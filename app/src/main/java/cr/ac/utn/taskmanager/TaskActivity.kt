package cr.ac.utn.taskmanager

import android.os.Bundle
import android.service.quicksettings.Tile
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cr.ac.utn.taskmanager.domain.model.TaskStatus
import cr.ac.utn.taskmanager.util.util

class TaskActivity : AppCompatActivity() {

    private lateinit var txtID: EditText
    private lateinit var txtTile: EditText
    private lateinit var txtDescription: EditText
    private lateinit var txtStatus: EditText
    private lateinit var txtPriority: EditText
    private lateinit var txtAssignTo: EditText
    private lateinit var txtObservation: EditText
    private lateinit var tvDueDate: TextView
    private var dueDay: Int =0
    private var dueMonth: Int =0
    private var dueYear: Int =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtID = findViewById<EditText>(R.id.txtId_Task)
        txtTile = findViewById<EditText>(R.id.txtTitle_Task)
        txtDescription = findViewById<EditText>(R.id.txtDescription)
        txtStatus = findViewById<EditText>(R.id.txtStatus_Task)
        txtPriority = findViewById<EditText>(R.id.txtPriority_Task)
        txtStatus = findViewById<EditText>(R.id.txtStatus_Task)
        txtAssignTo = findViewById<EditText>(R.id.txtAssignTo_Task)
        txtObservation = findViewById<EditText>(R.id.txtObservation_Task)
        tvDueDate = findViewById<EditText>(R.id.tvDueDate_Task)

        val btnSearch = findViewById<ImageButton>(R.id.btnSearch_Task)
        val btnDueDate = findViewById<ImageButton>(R.id.btnDueDate_Task)
        val btnSave = findViewById<Button>(R.id.btnSave_Task)
        val btnRemove = findViewById<Button>(R.id.btnRemove_Task)
        val btnCancel = findViewById<Button>(R.id.btnCancel_Task)






        val btnOpenMain: Button = findViewById<Button>(R.id.btnTA)
        btnOpenMain.setOnClickListener(View.OnClickListener { view ->
            util.OpenActivity(
                this,
                classActivity = MainActivity::class.java
            )
        })
    }
}