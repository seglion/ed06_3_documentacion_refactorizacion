package org.ed06.model;

/**
 * Clase que representa un cliente del hotel.
 *
 * <p>Un cliente tiene un identificador único, nombre, DNI, email y un estado
 * que indica si es VIP. Esta clase también proporciona validación de los datos
 * introducidos para garantizar la integridad de la información.</p>
 *
 * <p>La validación se realiza para garantizar que el nombre tenga al menos 3 caracteres,
 * el DNI siga el formato adecuado (8 dígitos seguidos de una letra) y que el email
 * tenga un formato válido.</p>
 *
 * @author Miguel Vigo
 */
public class Cliente {

    /** Identificador único del cliente */
    private final int id;

    /** Nombre del cliente */
    private String nombre;

    /** DNI del cliente */
    private String dni;

    /** Email del cliente */
    private String email;

    /** Indica si el cliente es VIP */
    private boolean esVip;

    /**
     * Constructor que inicializa un cliente con los datos proporcionados.
     *
     * @param id Identificador único del cliente.
     * @param nombre Nombre del cliente.
     * @param dni DNI del cliente.
     * @param email Email del cliente.
     * @param esVip Indica si el cliente es VIP.
     */
    public Cliente(int id, String nombre, String dni, String email, boolean esVip) {
        this.id = id;
        setNombre(nombre);
        setDni(dni);
        setEmail(email);
        this.esVip = esVip;
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return El identificador del cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el estado VIP del cliente.
     *
     * @return {@code true} si el cliente es VIP, {@code false} en caso contrario.
     */
    public boolean isVip() {
        return esVip;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente después de validar que sea válido.
     *
     * @param nombre Nombre del cliente.
     * @throws IllegalArgumentException Si el nombre es inválido (menos de 3 caracteres).
     */
    public void setNombre(String nombre) {
        validarNombre(nombre);
        this.nombre = nombre;
    }

    /**
     * Obtiene el DNI del cliente.
     *
     * @return El DNI del cliente.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del cliente después de validarlo.
     *
     * @param dni DNI del cliente.
     * @throws IllegalArgumentException Si el DNI no sigue el formato válido (8 números seguidos de una letra).
     */
    public void setDni(String dni) {
        validarDni(dni);
        this.dni = dni;
    }

    /**
     * Obtiene el email del cliente.
     *
     * @return El email del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del cliente después de validarlo.
     *
     * @param email Email del cliente.
     * @throws IllegalArgumentException Si el email no es válido.
     */
    public void setEmail(String email) {
        validarEmail(email);
        this.email = email;
    }

    /**
     * Establece el estado VIP del cliente.
     *
     * @param esVip Estado VIP del cliente.
     */
    public void setVip(boolean esVip) {
        this.esVip = esVip;
    }

    /**
     * Valida que el nombre sea correcto, con al menos 3 caracteres.
     *
     * @param nombre Nombre a validar.
     * @throws IllegalArgumentException Si el nombre es inválido.
     */
    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().length() < 3) {
            throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres");
        }
    }

    /**
     * Valida que el email siga un formato correcto.
     *
     * @param email Email a validar.
     * @throws IllegalArgumentException Si el email no sigue el formato adecuado.
     */
    private void validarEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El email debe tener un formato válido (ejemplo@dominio.com)");
        }
    }

    /**
     * Valida que el DNI siga el formato adecuado (8 dígitos seguidos de una letra).
     *
     * @param dni DNI a validar.
     * @throws IllegalArgumentException Si el DNI no sigue el formato válido.
     */
    private void validarDni(String dni) {
        if (dni == null || !dni.matches("^[0-9]{8}[A-Z]$")) {
            throw new IllegalArgumentException("El DNI debe tener 8 números seguidos de una letra mayúscula");
        }
    }

    @Override
    public String toString() {
        return String.format("Cliente #%d - %s (DNI: %s, Email: %s, VIP: %s)",
                id, nombre, dni, email, esVip ? "Sí" : "No");
    }
}
