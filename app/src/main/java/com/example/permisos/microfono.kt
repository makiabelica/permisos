package com.example.myapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException

class Microfono(private val activity: AppCompatActivity) {

    private val REQUEST_MICROPHONE_PERMISSION = 2
    private var mediaRecorder: MediaRecorder? = null

    // Verificar si el permiso de micrófono ha sido concedido
    fun verificarPermisoMicrofono() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {

            solicitarPermisoMicrofono()
        } else {
            // El permiso ya fue concedido, podemos acceder al micrófono
            accederMicrofono()
        }
    }

    // Solicitar el permiso de micrófono
    private fun solicitarPermisoMicrofono() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_MICROPHONE_PERMISSION
        )
    }

    // Manejar la respuesta del usuario al solicitar el permiso
    fun manejarResultadoPermiso(requestCode: Int, grantResults: IntArray) {
        if (requestCode == REQUEST_MICROPHONE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso fue concedido
                accederMicrofono()
            } else {
                // El permiso fue denegado
                Toast.makeText(activity, "Permiso de micrófono denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Acceder al micrófono para grabar audio
    private fun accederMicrofono() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile("/sdcard/audio.3gp")

            try {
                prepare()
                start()
                Toast.makeText(activity, "Grabación iniciada", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(activity, "Error al iniciar la grabación", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Detener la grabación (opcional)
    fun detenerGrabacion() {
        mediaRecorder?.apply {
            stop()
            release()
            Toast.makeText(activity, "Grabación detenida", Toast.LENGTH_SHORT).show()
        }
        mediaRecorder = null
    }
}
