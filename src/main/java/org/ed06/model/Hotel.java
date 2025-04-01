package org.ed06.model;

import java.time.LocalDate;
import java.util.*;

public class Hotel {
    private String nombre;
    private String direccion;
    private String telefono;

    private Map<Integer,Cliente> clientes = new HashMap<>();
    private List<Habitacion> habitaciones = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

    // Método para agregar una nueva habitación al hotel
    public void registrarHabitacion(String tipo, double precioBase) {
        Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipo, precioBase);
        habitaciones.add(habitacion);
    }

    public void listarHabitacionesDisponibles() {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.disponible) {
                System.out.println("Habitación #" + habitacion.numero + " - Tipo: " + habitacion.tipo + " - Precio base: " + habitacion.precioBase);
            }
        }
    }

    /**
     * Método para realizar una reserva. Comprueba si hay habitaciones disponibles, si existe el cliente y si las fechas son coherentes. Si encuentra una habitación disponible del tipo solicitado, crea una nueva reserva y la añade a la lista de reservas y devuelve el número de la habitación reservada.
     *
     * @param clienteId id del cliente
     * @param tipo tipo de habitación
     * @param fechaEntrada LocalDate con la fecha de entrada
     * @param fechaSalida LocalDate con la fecha de salida
     *
     * @return número de la habitación reservada. Si no encuentra una habitación disponible del tipo solicitado, devuelve -1. Si no existe el cliente, devuelve -2. Si la fecha de entrada es posterior a la fecha de salida, devuelve -3. Si no hay habitaciones en el hotel, devuelve -4.
     */
    public int reservarHabitacion(int clienteId, String tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // Comprobamos si hay habitaciones en el hotel
        if(!habitaciones.isEmpty()) {
            //comprobamos si existe el cliente
            if(this.clientes.get(clienteId) != null) {
                Cliente cliente = this.clientes.get(clienteId);
                // comprobamos si las fechas son coherentes
                if(fechaEntrada.isAfter(fechaSalida)) {
                    //buscamos una habitación disponible
                    for(Habitacion habitacion : habitaciones) {
                        if(habitacion.getTipo().equals(tipo) && habitacion.isDisponible()) {
                            // Comprobamos si el cliente pasa a ser vip tras la nueva reserva
                            int numReservas = 0;
                            for(Reserva reservaCliente : reservas) {
                                if(reservaCliente.getCliente().equals(cliente)) {
                                    if(reservaCliente.getFechaInicio().isAfter(LocalDate.now().minusYears(1))) {
                                        numReservas++;
                                    }
                                }
                            }
                            if(numReservas > 3 && !cliente.esVip) {
                                cliente.esVip = true;
                                System.out.println("El cliente " + cliente.nombre + " ha pasado a ser VIP");
                            }

                            Reserva reserva = new Reserva(reservas.size() + 1, habitacion, cliente, fechaEntrada, fechaSalida);
                            reservas.add(reserva);
                            // Marcamos la habitación como no disponible
                            habitacion.reservar();

                            System.out.println("Reserva realizada con éxito");
                            return habitacion.getNumero();
                        }
                    }
                    // si no hay habitaciones disponibles del tipo solicitado, mostramos un mensaje
                    System.out.println("No hay habitaciones disponibles del tipo " + tipo);
                    return -1;
                } else {
                    System.out.println("La fecha de entrada es posterior a la fecha de salida");
                    return -2;
                }
            } else {
                System.out.println("No existe el cliente con id " + clienteId);
                return -3;
            }
        } else {
            System.out.println("No hay habitaciones en el hotel");
            return -4;
        }

        return 0;
    }

    public void listarReservas() {
        for(Reserva reserva : reservas) {
            reserva.mostrarReserva();
        }
    }

    public void listarClientes() {
        for(Cliente cliente : clientes.values()) {
            System.out.println("Cliente #" + cliente.id + " - Nombre: " + cliente.nombre + " - DNI: " + cliente.dni + " - VIP: " + cliente.esVip);
        }
    }

    public void registrarCliente(String nombre, String email, String dni, boolean esVip) {
        Cliente cliente = new Cliente(clientes.size() + 1, nombre, dni, email, esVip);
        clientes.put(cliente.id, cliente);
    }


}
