package org.ed06.model;

import java.time.LocalDate;
import java.util.Date;

public class Reserva {
    private int id;
    private Habitacion habitacion;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precioTotal;

    public Reserva(int id, Habitacion habitacion, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = calcularPrecioFinal();
    }

    public int getId() {
        return id;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    /**
     * Calcula el precio total de la reserva. Para calcular el precio total, se debe calcular el precio base de la habitación por el número de noches de la reserva. En el caso de que el cliente sea vip, se aplicará un descuento del 10%. Además, si el intervalo de fechas es mayor a 7 días, se aplicará un descuento adicional del 5%.
     *
     *
     * @return precio total de la reserva
     */
    public double calcularPrecioFinal() {
        int n = fechaFin.getDayOfYear() - fechaInicio.getDayOfYear();
        double pb = habitacion.getPrecioBase() * n;
        double pf = pb;

        if (cliente.esVip) {
            pf *= 0.9;
        }

        if (n > 7) {
            pf *= 0.95;
        }

        return pf;
    }

    public void mostrarReserva() {
        System.out.println("Reserva #" + id);
        System.out.println("Habitación: " + habitacion.getNumero());
        System.out.println("Cliente: " + cliente.nombre);
        System.out.println("Fecha de inicio: " + fechaInicio.toString());
        System.out.println("Fecha de fin: " + fechaFin.toString());
        System.out.printf("Precio total: %.2f €\n", precioTotal);
    }
}
