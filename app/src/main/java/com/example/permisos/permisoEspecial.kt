package com.example.myapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MostrarSobreOtrasApps(private val context: Context) {

    private val REQUEST_CODE_OVERLAY_PERMISSION = 3

    // Verificar si el permiso para mostrar sobre otras aplicaciones ha sido otorgado
    fun verificarPermisoOverlay(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else {
            true
        }
    }

    // Solicitar al usuario que otorgue el permiso
    fun solicitarPermisoOverlay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {

                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${context.packageName}")
                )
                (context as AppCompatActivity).startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION)
            } else {
                // El permiso ya ha sido otorgado
                Toast.makeText(context, "Permiso ya concedido para mostrar sobre otras aplicaciones", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Manejar la respuesta después de que el usuario haya ido a la configuración
    fun manejarResultadoPermiso(requestCode: Int) {
        if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION) {
            if (verificarPermisoOverlay()) {

                Toast.makeText(context, "Permiso otorgado para mostrar sobre otras aplicaciones", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
