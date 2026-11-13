package com.example.gestionclientesapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ConsultaActivity extends AppCompatActivity {

    private EditText etBuscarDato;
    private Button btnBuscar, btnRegresar;
    private TextView tvResultado;

    private DatabaseReference refClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        refClientes = FirebaseHelper.obtenerClientes(this);

        etBuscarDato = findViewById(R.id.etBuscarDato);
        btnBuscar    = findViewById(R.id.btnBuscar);
        btnRegresar  = findViewById(R.id.btnRegresar);
        tvResultado  = findViewById(R.id.tvResultado);

        btnBuscar.setOnClickListener(v -> buscarCliente());

        // finish() cierra esta ventana y Android vuelve solo a MainActivity
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void buscarCliente() {
        String dato = etBuscarDato.getText().toString().trim();
        if (dato.isEmpty()) {
            tvResultado.setText("Escribe un código o una cédula para buscar.");
            return;
        }

        if (refClientes == null) { FirebaseHelper.avisarSinConexion(this); return; }

        tvResultado.setText("Buscando...");
        Runnable aviso = FirebaseHelper.avisoSiTarda(this);

        // Leemos UNA sola vez todo el nodo "Clientes"
        refClientes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseHelper.cancelarAviso(aviso);
                // Recorremos cada cliente guardado (cada hijo del nodo)
                for (DataSnapshot hijo : snapshot.getChildren()) {
                    Cliente c = hijo.getValue(Cliente.class);
                    if (c == null) continue;

                    // ¿Coincide con el código o con la cédula?
                    if (dato.equalsIgnoreCase(c.getCodigo()) || dato.equals(c.getCedula())) {
                        mostrarCliente(c);
                        return; // ya lo encontramos, dejamos de buscar
                    }
                }
                tvResultado.setText("No se encontró ningún cliente con el código o cédula: " + dato);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FirebaseHelper.cancelarAviso(aviso);
                tvResultado.setText("Error al consultar: " + error.getMessage());
            }
        });
    }

    private void mostrarCliente(Cliente c) {
        String ficha = "FICHA DEL CLIENTE\n\n"
                + "Código:     " + c.getCodigo() + "\n"
                + "Nombre:     " + c.getNombre() + "\n"
                + "Apellido:   " + c.getApellido() + "\n"
                + "Teléfono:   " + c.getTelefono() + "\n"
                + "Cédula:     " + c.getCedula() + "\n"
                + "Dirección:  " + c.getDireccion();
        tvResultado.setText(ficha);
    }
}
