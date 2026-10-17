package cr.ac.utn.taskmanager

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cr.ac.utn.taskmanager.data.memory.TaskMemoryManager
import cr.ac.utn.taskmanager.domain.model.Task
import cr.ac.utn.taskmanager.domain.model.TaskPriority
import cr.ac.utn.taskmanager.domain.model.TaskStatus
import cr.ac.utn.taskmanager.util.util
import cr.ac.utn.taskmanager.viewmodel.TaskViewModel
import java.time.LocalDate

class TaskActivity : AppCompatActivity() {

    private lateinit var txtID: EditText
    private lateinit var txtTile: EditText
    private lateinit var txtDescription: EditText
    private lateinit var txtStatus: EditText
    private lateinit var txtPriority: EditText
    private lateinit var txtAssignTo: EditText
    private lateinit var txtObservation: EditText
    private lateinit var tvDueDate: TextView
    private lateinit var tvCreationDate: TextView
    private var dueDate: LocalDate? = null

    companion object {
        // Un solo repositorio en memoria para que los datos no se pierdan al cerrar la pantalla
        private val taskViewModel = TaskViewModel(TaskMemoryManager())
    }

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
        txtAssignTo = findViewById<EditText>(R.id.txtAssignTo_Task)
        txtObservation = findViewById<EditText>(R.id.txtObservation_Task)
        tvDueDate = findViewById<TextView>(R.id.tvDueDate_Task)
        tvCreationDate = findViewById<TextView>(R.id.tvCreationDate_Task)
        tvCreationDate.text = LocalDate.now().toString()

        val btnSearch = findViewById<ImageButton>(R.id.btnSearch_Task)
        val btnDueDate = findViewById<ImageButton>(R.id.btnDueDate_Task)
        val btnSave = findViewById<Button>(R.id.btnSave_Task)
        val btnRemove = findViewById<Button>(R.id.btnRemove_Task)
        val btnCancel = findViewById<Button>(R.id.btnCancel_Task)

        btnSearch.setOnClickListener { searchTask() }
        btnDueDate.setOnClickListener { pickDueDate() }
        btnSave.setOnClickListener { confirm(R.string.msgConfirmSave) { saveTask() } }
        btnRemove.setOnClickListener { removeTask() }
        btnCancel.setOnClickListener { clearForm() }

        val btnOpenMain: Button = findViewById<Button>(R.id.btnTA)
        btnOpenMain.setOnClickListener {
            util.OpenActivity(
                this,
                classActivity = MainActivity::class.java
            )
        }
    }

    private fun toast(messageId: Int) {
        Toast.makeText(this, messageId, Toast.LENGTH_LONG).show()
    }

    private fun confirm(messageId: Int, onYes: () -> Unit) {
        AlertDialog.Builder(this)
            .setMessage(messageId)
            .setPositiveButton(R.string.btnTextYes) { _, _ -> onYes() }
            .setNegativeButton(R.string.btnTextNo, null)
            .show()
    }

    private fun pickDueDate() {
        val today = LocalDate.now()
        DatePickerDialog(this, { _, year, month, day ->
            dueDate = LocalDate.of(year, month + 1, day)
            tvDueDate.text = dueDate.toString()
        }, today.year, today.monthValue - 1, today.dayOfMonth).show()
    }

    private fun searchTask() {
        val task = taskViewModel.getTaskById(txtID.text.toString())
        if (task == null) {
            toast(R.string.msgNoDataFound)
            return
        }
        txtID.setText(task.id)
        txtTile.setText(task.title)
        txtDescription.setText(task.description)
        dueDate = task.dueDate
        tvDueDate.text = task.dueDate.toString()
        tvCreationDate.text = task.creationDate.toString()
        txtStatus.setText(task.status.name)
        txtPriority.setText(task.priority.name)
        txtAssignTo.setText(task.assigned)
        txtObservation.setText(task.notes)
    }

    private fun saveTask() {
        try {
            val id = txtID.text.toString().trim()
            val due = dueDate
            if (id.isEmpty() || txtTile.text.toString().trim().isEmpty() || due == null) {
                toast(R.string.msgSaveError)
                return
            }
            if (taskViewModel.getTaskById(id) != null) {
                toast(R.string.msgDuplicateData)
                return
            }
            val task = Task(
                id = id,
                title = txtTile.text.toString().trim(),
                description = txtDescription.text.toString(),
                dueDate = due,
                creationDate = LocalDate.now(),
                status = TaskStatus.valueOf(txtStatus.text.toString().trim().uppercase()),
                assigned = txtAssignTo.text.toString(),
                priority = TaskPriority.valueOf(txtPriority.text.toString().trim().uppercase()),
                notes = txtObservation.text.toString()
            )
            taskViewModel.saveTask(task)
            toast(R.string.msgSaveSuccess)
            clearForm()
        } catch (e: Exception) {
            toast(R.string.msgSaveError)
        }
    }

    private fun removeTask() {
        val task = taskViewModel.getTaskById(txtID.text.toString())
        if (task == null) {
            toast(R.string.msgNoDataFound)
            return
        }
        if (task.status == TaskStatus.COMPLETED) {
            toast(R.string.msgTaskCompleted)
            return
        }
        confirm(R.string.msgConfirmDelete) {
            try {
                taskViewModel.removeTask(task.id)
                toast(R.string.msgDeleteSuccess)
                clearForm()
            } catch (e: Exception) {
                toast(R.string.msgDeleteError)
            }
        }
    }

    private fun clearForm() {
        txtID.setText("")
        txtTile.setText("")
        txtDescription.setText("")
        txtStatus.setText("")
        txtPriority.setText("")
        txtAssignTo.setText("")
        txtObservation.setText("")
        dueDate = null
        tvDueDate.text = ""
        tvCreationDate.text = LocalDate.now().toString()
    }
}