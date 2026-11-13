package com.example.gestionclientesapp;

import android.app.Application;
import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Clase que arranca antes que cualquier pantalla.
 * Si la app llega a cerrarse por un error, guarda el detalle del error
 * para mostrarlo la próxima vez que se abra (así sabemos qué falló).
 */
public class App extends Application {

    static final String PREFS = "errores";
    static final String CLAVE = "ultimo_error";

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.UncaughtExceptionHandler original = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((hilo, error) -> {
            StringWriter sw = new StringWriter();
            error.printStackTrace(new PrintWriter(sw));
            getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                    .edit().putString(CLAVE, sw.toString()).commit();
            if (original != null) original.uncaughtException(hilo, error);
        });
    }
}
