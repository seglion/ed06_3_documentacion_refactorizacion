package org.ed06.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Clase que representa una reserva en el hotel.
 * <p>
 * Una reserva está asociada a un cliente, una habitación y tiene un precio calculado en función de la duración de la estancia y posibles descuentos.
 * </p>
 */
public class Reserva {
    private static final double DESCUENTO_VIP = 0.9; // 10% de descuento para clientes VIP
    private static final double DESCUENTO_ESTANCIA_LARGA = 0.95; // 5% de descuento para estancias largas
    private static final int DIAS_ESTANCIA_LARGA = 7; // Número de días mínimo para una estancia larga

    private final int id;
    private final int clienteId;
    private final int habitacionId;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final double precioTotal;

    /**
     * Constructor de la clase Reserva.
     *
     * @param id El ID de la reserva.
     * @param clienteId El ID del cliente que realiza la reserva.
     * @param habitacionId El ID de la habitación reservada.
     * @param fechaInicio La fecha de inicio de la reserva.
     * @param fechaFin La fecha de fin de la reserva.
     * @param habitacion La habitación asociada a la reserva.
     * @param cliente El cliente que realiza la reserva.
     */
    public Reserva(int id, int clienteId, int habitacionId, LocalDate fechaInicio, LocalDate fechaFin,
                   Habitacion habitacion, Cliente cliente) {
        this.id = id;
        this.clienteId = clienteId;
        this.habitacionId = habitacionId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = calcularPrecioFinal(habitacion, cliente, fechaInicio, fechaFin);
    }

    /**
     * Calcula el precio final de la reserva.
     * <p>
     * El precio final depende de la duración de la estancia y los descuentos aplicables (VIP y estancia larga).
     * </p>
     *
     * @param habitacion La habitación asociada a la reserva.
     * @param cliente El cliente que realiza la reserva.
     * @param fechaInicio La fecha de inicio de la reserva.
     * @param fechaFin La fecha de fin de la reserva.
     * @return El precio total de la reserva con los descuentos aplicados.
     */
    private double calcularPrecioFinal(Habitacion habitacion, Cliente cliente,
                                       LocalDate fechaInicio, LocalDate fechaFin) {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        double precioBase = habitacion.getPrecioBase() * dias;
        return aplicarDescuentos(precioBase, cliente, dias);
    }

    /**
     * Aplica los descuentos correspondientes sobre el precio base.
     * <p>
     * Se aplican dos tipos de descuento: uno por ser cliente VIP y otro por una estancia larga.
     * </p>
     *
     * @param precioBase El precio base de la reserva (sin descuentos).
     * @param cliente El cliente que realiza la reserva.
     * @param dias La cantidad de días de la estancia.
     * @return El precio final con los descuentos aplicados.
     */
    private double aplicarDescuentos(double precioBase, Cliente cliente, long dias) {
        double precioFinal = precioBase;

        // Descuento por ser VIP
        if (cliente.isVip()) {
            precioFinal *= DESCUENTO_VIP;
        }

        // Descuento por estancia larga
        if (dias >= DIAS_ESTANCIA_LARGA) {
            precioFinal *= DESCUENTO_ESTANCIA_LARGA;
        }

        return precioFinal;
    }

    // Getters

    /**
     * Obtiene el ID de la reserva.
     *
     * @return El ID de la reserva.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el ID del cliente asociado a la reserva.
     *
     * @return El ID del cliente.
     */
    public int getClienteId() {
        return clienteId;
    }

    /**
     * Obtiene el ID de la habitación reservada.
     *
     * @return El ID de la habitación.
     */
    public int getHabitacionId() {
        return habitacionId;
    }

    /**
     * Obtiene la fecha de inicio de la reserva.
     *
     * @return La fecha de inicio de la reserva.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Obtiene la fecha de fin de la reserva.
     *
     * @return La fecha de fin de la reserva.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Obtiene el precio total de la reserva.
     *
     * @return El precio total de la reserva con descuentos aplicados.
     */
    public double getPrecioTotal() {
        return precioTotal;
    }

    /**
     * Representación en formato de texto de la reserva.
     *
     * @return Una cadena que describe la reserva.
     */
    @Override
    public String toString() {
        return String.format("Reserva #%d - Cliente: %d - Habitación: %d - Fechas: %s a %s - Precio: %.2f",
                id, clienteId, habitacionId, fechaInicio, fechaFin, precioTotal);
    }
}
