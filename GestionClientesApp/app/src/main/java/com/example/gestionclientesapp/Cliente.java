package com.example.gestionclientesapp;

/**
 * Clase modelo: representa un cliente tal como se guarda en Firebase.
 * Firebase convierte automáticamente este objeto a JSON (y viceversa)
 * usando el constructor vacío y los getters/setters.
 */
public class Cliente {

    private String codigo;
    private String nombre;
    private String apellido;
    private String telefono;
    private String cedula;
    private String direccion;

    // Constructor vacío OBLIGATORIO para que Firebase pueda crear el objeto al leer datos
    public Cliente() {
    }

    // Constructor con todos los parámetros
    public Cliente(String codigo, String nombre, String apellido,
                   String telefono, String cedula, String direccion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.cedula = cedula;
        this.direccion = direccion;
    }

    // Getters y setters (Firebase los usa para leer/escribir cada campo)
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
