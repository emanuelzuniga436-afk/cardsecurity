package com.example.gestionclientesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Cajas de texto del formulario
    private EditText etCodigo, etNombre, etApellido, etTelefono, etCedula, etDireccion;

    // Botones de acción
    private Button btnGuardar, btnActualizar, btnEliminar, btnIrConsulta, btnLimpiar;

    // Referencia al nodo "Clientes" en Firebase Realtime Database
    private DatabaseReference refClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Conexión con Firebase: obtenemos (o creamos) el nodo "Clientes"
        refClientes = FirebaseDatabase.getInstance().getReference("Clientes");

        // 2. Enlazamos los elementos del XML con las variables Java
        etCodigo    = findViewById(R.id.etCodigo);
        etNombre    = findViewById(R.id.etNombre);
        etApellido  = findViewById(R.id.etApellido);
        etTelefono  = findViewById(R.id.etTelefono);
        etCedula    = findViewById(R.id.etCedula);
        etDireccion = findViewById(R.id.etDireccion);

        btnGuardar    = findViewById(R.id.btnGuardar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar   = findViewById(R.id.btnEliminar);
        btnIrConsulta = findViewById(R.id.btnIrConsulta);
        btnLimpiar    = findViewById(R.id.btnLimpiar);   // Reto opcional

        // 3. Asignamos qué hace cada botón al hacer clic
        btnGuardar.setOnClickListener(v -> guardarCliente());
        btnActualizar.setOnClickListener(v -> actualizarCliente());
        btnEliminar.setOnClickListener(v -> eliminarCliente());
        btnIrConsulta.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConsultaActivity.class);
            startActivity(intent);
        });
        btnLimpiar.setOnClickListener(v -> limpiarCampos());
    }

    // ---------------------------------------------------------------- GUARDAR
    private void guardarCliente() {
        String codigo    = etCodigo.getText().toString().trim();
        String nombre    = etNombre.getText().toString().trim();
        String apellido  = etApellido.getText().toString().trim();
        String telefono  = etTelefono.getText().toString().trim();
        String cedula    = etCedula.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();

        // Validación: los campos principales no pueden estar vacíos
        if (codigo.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty()) {
            Toast.makeText(this, "Código, nombre, apellido y cédula son obligatorios",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Cliente cliente = new Cliente(codigo, nombre, apellido, telefono, cedula, direccion);

        // El código del cliente es la "llave" del nodo: Clientes/C01, Clientes/C02...
        refClientes.child(codigo).setValue(cliente)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Cliente guardado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al guardar: " + e.getMessage(),
                                Toast.LENGTH_LONG).show());
    }

    // ------------------------------------------------------------- ACTUALIZAR
    private void actualizarCliente() {
        String codigo = etCodigo.getText().toString().trim();
        if (codigo.isEmpty()) {
            Toast.makeText(this, "Escribe el código del cliente a actualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Solo enviamos los campos que el usuario escribió (los vacíos no se tocan)
        Map<String, Object> cambios = new HashMap<>();
        agregarSiNoVacio(cambios, "nombre", etNombre);
        agregarSiNoVacio(cambios, "apellido", etApellido);
        agregarSiNoVacio(cambios, "telefono", etTelefono);
        agregarSiNoVacio(cambios, "cedula", etCedula);
        agregarSiNoVacio(cambios, "direccion", etDireccion);

        if (cambios.isEmpty()) {
            Toast.makeText(this, "Escribe al menos un dato para actualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Primero comprobamos que el cliente exista, para no crear uno "a medias"
        refClientes.child(codigo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(MainActivity.this, "No existe un cliente con el código " + codigo,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                refClientes.child(codigo).updateChildren(cambios)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(MainActivity.this, "Datos actualizados",
                                    Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        })
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this,
                                "Error al actualizar: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void agregarSiNoVacio(Map<String, Object> mapa, String campo, EditText caja) {
        String valor = caja.getText().toString().trim();
        if (!valor.isEmpty()) {
            mapa.put(campo, valor);
        }
    }

    // --------------------------------------------------------------- ELIMINAR
    private void eliminarCliente() {
        String codigo = etCodigo.getText().toString().trim();
        if (codigo.isEmpty()) {
            Toast.makeText(this, "Escribe el código del cliente a eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        refClientes.child(codigo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(MainActivity.this, "No existe un cliente con el código " + codigo,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                refClientes.child(codigo).removeValue()
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(MainActivity.this, "Cliente eliminado",
                                    Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        })
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this,
                                "Error al eliminar: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // ---------------------------------------------------------------- LIMPIAR
    private void limpiarCampos() {
        etCodigo.setText("");
        etNombre.setText("");
        etApellido.setText("");
        etTelefono.setText("");
        etCedula.setText("");
        etDireccion.setText("");
        etCodigo.requestFocus();
    }
}
