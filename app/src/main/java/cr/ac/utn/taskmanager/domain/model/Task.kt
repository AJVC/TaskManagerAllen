package cr.ac.utn.taskmanager.domain.model

import java.time.LocalDate

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: LocalDate,
    val creationDate: LocalDate,
    val status: TaskStatus,
    val assigned: String,
    val priority: TaskPriority,
    val notes: String
)
