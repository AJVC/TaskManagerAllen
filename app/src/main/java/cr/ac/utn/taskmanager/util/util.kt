package cr.ac.utn.taskmanager.util

import android.content.Context
import android.content.Intent

class util {
    companion object {
        fun OpenActivity(context: Context, classActivity: Class<*>) {
            val objIntent = Intent(context, classActivity)
            context.startActivity(objIntent)
        }
    }
}