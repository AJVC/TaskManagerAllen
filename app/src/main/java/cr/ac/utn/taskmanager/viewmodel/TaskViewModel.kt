package cr.ac.utn.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import cr.ac.utn.taskmanager.domain.model.Task
import cr.ac.utn.taskmanager.domain.repository.ITaskRepository

class TaskViewModel (
    private val repository: ITaskRepository

    ): ViewModel() {

    fun getTasks(): List<Task>{
        return repository.getTask()
    }

    fun getTaskById (id: String): Task?{
        return repository.getById(id)
    }

    fun saveTask(task: Task){
        repository.addTask(task)
    }

    fun updateTask(task: Task){
        repository.updateTask(task)
    }

    fun removeTask(id: String){
        repository.deleteTask(id)
    }
}
