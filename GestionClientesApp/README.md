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

## Cómo usarlo

1. Crea el proyecto en Android Studio: **Empty Views Activity**, nombre `GestionClientesApp`, lenguaje **Java**, paquete `com.example.gestionclientesapp`.
2. **Tools > Firebase > Realtime Database > Connect to Firebase** y luego **Add the Realtime Database SDK to your app**.
3. En la consola de Firebase crea la Realtime Database y pon estas reglas (solo para la práctica):
   ```json
   { "rules": { ".read": true, ".write": true } }
   ```
4. Crea `ConsultaActivity` (New > Activity > Empty Views Activity).
5. Reemplaza el contenido de los archivos generados por los de esta carpeta.
6. En el `AndroidManifest.xml`, si tu tema tiene otro nombre, deja el `android:theme` que Android Studio generó.
