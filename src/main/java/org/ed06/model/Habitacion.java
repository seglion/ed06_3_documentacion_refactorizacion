package org.ed06.model;

/**
 * Clase que representa una habitación en el hotel.
 *
 * <p>Una habitación tiene un número, un tipo, un precio base y un estado de disponibilidad.
 * Los tipos de habitación están definidos en el enum {@link RoomType}, y la disponibilidad
 * de la habitación puede cambiar a medida que se realizan reservas.</p>
 *
 * @author Miguel Vigo
 */
public class Habitacion {

    /** Número único que identifica la habitación */
    private final int numero;

    /** Tipo de la habitación (SIMPLE, DOBLE, SUITE, LITERAS) */
    private RoomType tipo;

    /** Precio base de la habitación */
    private double precioBase;

    /** Indica si la habitación está disponible o no */
    private boolean disponible;

    /**
     * Enum que representa los diferentes tipos de habitaciones disponibles.
     * Cada tipo de habitación tiene un número máximo de huéspedes que puede acomodar.
     */
    public enum RoomType {
        /** Habitación simple para 1 huésped */
        SIMPLE(1),

        /** Habitación doble para 3 huéspedes */
        DOBLE(3),

        /** Habitación suite para 4 huéspedes */
        SUITE(4),

        /** Habitación con literas para 8 huéspedes */
        LITERAS(8);

        /** Número máximo de huéspedes para cada tipo de habitación */
        private final int maxGuests;

        /**
         * Constructor que asigna el número máximo de huéspedes a cada tipo de habitación.
         *
         * @param maxGuests Número máximo de huéspedes para este tipo de habitación.
         */
        RoomType(int maxGuests) {
            this.maxGuests = maxGuests;
        }

        /**
         * Obtiene el número máximo de huéspedes para este tipo de habitación.
         *
         * @return El número máximo de huéspedes.
         */
        public int getMaxGuests() {
            return maxGuests;
        }
    }

    /**
     * Constructor que inicializa una habitación con los datos proporcionados.
     *
     * @param numero Número único de la habitación.
     * @param tipo Tipo de la habitación.
     * @param precioBase Precio base de la habitación.
     */
    public Habitacion(int numero, RoomType tipo, double precioBase) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioBase = precioBase;
        this.disponible = true;  // La habitación está disponible al principio
    }

    /**
     * Obtiene el número de la habitación.
     *
     * @return El número de la habitación.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Obtiene el tipo de la habitación.
     *
     * @return El tipo de la habitación.
     */
    public RoomType getTipo() {
        return tipo;
    }

    /**
     * Obtiene el precio base de la habitación.
     *
     * @return El precio base de la habitación.
     */
    public double getPrecioBase() {
        return precioBase;
    }

    /**
     * Verifica si la habitación está disponible para reserva.
     *
     * @return {@code true} si la habitación está disponible, {@code false} en caso contrario.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Obtiene el número máximo de huéspedes que puede alojar la habitación, según su tipo.
     *
     * @return El número máximo de huéspedes.
     */
    public int obtenerNumMaxHuespedes() {
        return tipo.getMaxGuests();
    }

    /**
     * Realiza una reserva de la habitación, marcándola como no disponible.
     * Si la habitación ya está reservada, se muestra un mensaje de error.
     */
    public void reservar() {
        if (!disponible) {
            System.out.println("Habitación #" + numero + " ya reservada");
            return;
        }
        disponible = false;  // La habitación ya no está disponible
    }
}
