package com.example.gestionclientesapp;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Funciones de ayuda para conectarse a Firebase sin que la app se cierre
 * si algo está mal configurado.
 */
public class FirebaseHelper {

    private static final Handler handler = new Handler(Looper.getMainLooper());
    private static String ultimoError = null;

    /** Devuelve la referencia al nodo "Clientes", o null si Firebase no está bien configurado. */
    public static DatabaseReference obtenerClientes(Activity activity) {
        try {
            return FirebaseDatabase.getInstance().getReference("Clientes");
        } catch (Exception e) {
            ultimoError = e.getMessage();
            new AlertDialog.Builder(activity)
                    .setTitle("Firebase no está configurado")
                    .setMessage("La app no pudo conectarse a Firebase.\n\nDetalle: " + e.getMessage()
                            + "\n\nRevisa el archivo google-services.json y que la Realtime Database esté creada.")
                    .setPositiveButton("Entendido", null)
                    .show();
            return null;
        }
    }

    public static void avisarSinConexion(Activity activity) {
        Toast.makeText(activity, "Sin conexión a Firebase: " + ultimoError, Toast.LENGTH_LONG).show();
    }

    /**
     * Si Firebase no responde en 10 segundos (URL de base de datos incorrecta,
     * base no creada o sin internet) muestra un aviso en lugar de quedarse callado.
     */
    public static Runnable avisoSiTarda(Activity activity) {
        Runnable r = () -> Toast.makeText(activity,
                "Firebase no responde. Revisa tu internet y que la Realtime Database esté creada.",
                Toast.LENGTH_LONG).show();
        handler.postDelayed(r, 10_000);
        return r;
    }

    public static void cancelarAviso(Runnable r) {
        handler.removeCallbacks(r);
    }
}
