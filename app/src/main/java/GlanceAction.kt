import android.content.Context
import android.widget.Toast
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback

class GlanceAction : ActionCallback {

    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        Toast.makeText(context, "Glance id: $glanceId", Toast.LENGTH_LONG).show()
    }
}