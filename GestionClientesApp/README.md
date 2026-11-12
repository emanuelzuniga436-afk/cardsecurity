# GestionClientesApp — Taller Android Studio + Firebase (Grado 11°)

App CRUD de clientes con dos ventanas (MainActivity y ConsultaActivity) conectada a Firebase Realtime Database.

## Archivos de este taller

| Archivo | Qué hace |
|---|---|
| `app/src/main/java/com/example/gestionclientesapp/Cliente.java` | Clase modelo con los 6 datos del cliente |
| `app/src/main/java/com/example/gestionclientesapp/MainActivity.java` | Guardar, Actualizar, Eliminar, Limpiar e ir a Consulta |
| `app/src/main/java/com/example/gestionclientesapp/ConsultaActivity.java` | Buscar por código o cédula y regresar |
| `app/src/main/res/layout/activity_main.xml` | Formulario de registro |
| `app/src/main/res/layout/activity_consulta.xml` | Pantalla de consulta |
| `app/src/main/AndroidManifest.xml` | Registra las dos Activities y el permiso de internet |

## Cómo abrirlo (proyecto completo)

1. Descomprime `GestionClientesApp.zip` y en Android Studio usa **File > Open** sobre la carpeta `GestionClientesApp`. Espera a que termine el *Gradle Sync* (necesita internet la primera vez).
2. Conecta Firebase: **Tools > Firebase > Realtime Database > Connect to Firebase**. Eso crea (o elige) tu proyecto en Firebase y descarga `app/google-services.json`.
   - Alternativa manual: en console.firebase.google.com agrega una app Android con el paquete `com.example.gestionclientesapp`, descarga `google-services.json` y ponlo dentro de la carpeta `app/`.
3. En la consola de Firebase: **Compilación > Realtime Database > Crear base de datos**. En **Reglas** pon (solo para la práctica):
   ```json
   { "rules": { ".read": true, ".write": true } }
   ```
   y pulsa **Publicar**.
4. Crea la base de datos **antes** de descargar `google-services.json` (así el archivo incluye la URL de la base). Si ya lo descargaste antes, vuelve a descargarlo.
5. Pulsa **File > Sync Project with Gradle Files** y luego **Run ▶** en el emulador o en tu celular.

Sin `google-services.json` el proyecto abre y compila, pero la app se cierra al usar Firebase.
