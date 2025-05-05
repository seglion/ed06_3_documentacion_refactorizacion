package org.ed06.model;

import java.time.LocalDate;
import java.util.*;

/**
 * Clase que representa un hotel.
 * <p>
 * Gestiona habitaciones, clientes y reservas en un hotel.
 * </p>
 */
public class Hotel {
    private final String nombre;
    private final String direccion;
    private final String telefono;
    private final GestorHabitaciones gestorHabitaciones;
    private final GestorClientes gestorClientes;
    private final GestorReservas gestorReservas;

    /**
     * Constructor de la clase Hotel.
     *
     * @param nombre Nombre del hotel.
     * @param direccion Dirección del hotel.
     * @param telefono Teléfono de contacto del hotel.
     */
    public Hotel(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.gestorHabitaciones = new GestorHabitaciones();
        this.gestorClientes = new GestorClientes();
        this.gestorReservas = new GestorReservas(gestorHabitaciones);
    }

    /**
     * Registra una habitación en el hotel.
     *
     * @param tipo El tipo de habitación.
     * @param precioBase El precio base de la habitación.
     */
    public void registrarHabitacion(Habitacion.RoomType tipo, double precioBase) {
        gestorHabitaciones.registrarHabitacion(tipo, precioBase);
    }

    /**
     * Registra varias habitaciones en el hotel.
     *
     * @param tipos Lista de tipos de habitaciones.
     * @param preciosBase Lista de precios base para las habitaciones.
     */
    public void registrarHabitaciones(List<Habitacion.RoomType> tipos, List<Double> preciosBase) {
        gestorHabitaciones.registrarHabitaciones(tipos, preciosBase);
    }

    /**
     * Muestra las habitaciones disponibles en el hotel.
     */
    public void listarHabitacionesDisponibles() {
        gestorHabitaciones.listarHabitacionesDisponibles();
    }

    /**
     * Obtiene una habitación según su número.
     *
     * @param numero El número de la habitación.
     * @return Una opción que contiene la habitación si existe, o vacía si no.
     */
    public Optional<Habitacion> getHabitacion(int numero) {
        return gestorHabitaciones.getHabitacion(numero);
    }

    /**
     * Realiza una reserva de habitación para un cliente.
     *
     * @param clienteId El ID del cliente que realiza la reserva.
     * @param tipo El tipo de habitación que se desea reservar.
     * @param fechaEntrada La fecha de entrada a la habitación.
     * @param fechaSalida La fecha de salida de la habitación.
     * @return El número de la habitación reservada, o un código de error negativo.
     */
    public int reservarHabitacion(int clienteId, Habitacion.RoomType tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (gestorHabitaciones.estaVacio()) {
            System.out.println("No hay habitaciones en el hotel");
            return -4;
        }

        Cliente cliente = gestorClientes.getCliente(clienteId);
        if (cliente == null) {
            System.out.println("No existe el cliente con id " + clienteId);
            return -3;
        }

        if (!fechaEntrada.isBefore(fechaSalida)) {
            System.out.println("La fecha de entrada es posterior a la fecha de salida");
            return -2;
        }

        return gestorReservas.realizarReserva(cliente, tipo, fechaEntrada, fechaSalida);
    }

    /**
     * Muestra todas las reservas realizadas en el hotel.
     */
    public void listarReservas() {
        gestorReservas.listarReservas();
    }

    /**
     * Muestra todos los clientes registrados en el hotel.
     */
    public void listarClientes() {
        gestorClientes.listarClientes();
    }

    /**
     * Registra un cliente en el hotel.
     *
     * @param nombre El nombre del cliente.
     * @param email El correo electrónico del cliente.
     * @param dni El DNI del cliente.
     * @param esVip Indica si el cliente es VIP o no.
     */
    public void registrarCliente(String nombre, String email, String dni, boolean esVip) {
        gestorClientes.registrarCliente(nombre, email, dni, esVip);
    }

    // Clases internas para gestionar responsabilidades específicas

    /**
     * Clase encargada de gestionar las habitaciones del hotel.
     */
    private static class GestorHabitaciones {
        private final List<Habitacion> habitaciones = new ArrayList<>();
        private final Map<Integer, List<Reserva>> reservasPorHabitacion = new HashMap<>();

        /**
         * Registra una nueva habitación en el hotel.
         *
         * @param tipo El tipo de habitación.
         * @param precioBase El precio base de la habitación.
         */
        public void registrarHabitacion(Habitacion.RoomType tipo, double precioBase) {
            Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipo, precioBase);
            habitaciones.add(habitacion);
            reservasPorHabitacion.put(habitacion.getNumero(), new ArrayList<>());
        }

        /**
         * Registra varias habitaciones en el hotel.
         *
         * @param tipos Lista de tipos de habitaciones.
         * @param preciosBase Lista de precios base para las habitaciones.
         */
        public void registrarHabitaciones(List<Habitacion.RoomType> tipos, List<Double> preciosBase) {
            for(int i = 0; i < tipos.size(); i++) {
                registrarHabitacion(tipos.get(i), preciosBase.get(i));
            }
        }

        /**
         * Muestra las habitaciones disponibles en el hotel.
         */
        public void listarHabitacionesDisponibles() {
            habitaciones.stream()
                    .filter(Habitacion::isDisponible)
                    .forEach(habitacion -> System.out.printf("Habitación #%d - Tipo: %s - Precio base: %.2f%n",
                            habitacion.getNumero(), habitacion.getTipo(), habitacion.getPrecioBase()));
        }

        /**
         * Obtiene una habitación por su número.
         *
         * @param numero El número de la habitación.
         * @return Una opción que contiene la habitación si existe, o vacía si no.
         */
        public Optional<Habitacion> getHabitacion(int numero) {
            return habitaciones.stream()
                    .filter(h -> h.getNumero() == numero)
                    .findFirst();
        }

        /**
         * Verifica si el hotel tiene habitaciones registradas.
         *
         * @return true si el hotel no tiene habitaciones, false si tiene.
         */
        public boolean estaVacio() {
            return habitaciones.isEmpty();
        }

        /**
         * Busca una habitación disponible del tipo solicitado.
         *
         * @param tipo El tipo de habitación.
         * @return Una opción que contiene la habitación disponible si existe, o vacía si no.
         */
        public Optional<Habitacion> encontrarHabitacionDisponible(Habitacion.RoomType tipo) {
            return habitaciones.stream()
                    .filter(h -> h.getTipo() == tipo && h.isDisponible())
                    .findFirst();
        }

        /**
         * Añade una reserva a una habitación.
         *
         * @param habitacion La habitación que será reservada.
         * @param reserva La reserva que se añadirá a la habitación.
         */
        public void agregarReserva(Habitacion habitacion, Reserva reserva) {
            reservasPorHabitacion.get(habitacion.getNumero()).add(reserva);
            habitacion.reservar();
        }

        /**
         * Obtiene las reservas de una habitación específica.
         *
         * @param numeroHabitacion El número de la habitación.
         * @return Lista de reservas asociadas a la habitación.
         */
        public List<Reserva> getReservasHabitacion(int numeroHabitacion) {
            return reservasPorHabitacion.getOrDefault(numeroHabitacion, new ArrayList<>());
        }

        /**
         * Obtiene todas las habitaciones registradas en el hotel.
         *
         * @return Lista de todas las habitaciones.
         */
        public List<Habitacion> getHabitaciones() {
            return new ArrayList<>(habitaciones);
        }
    }

    /**
     * Clase encargada de gestionar los clientes del hotel.
     */
    private static class GestorClientes {
        private static final int VIP_RESERVATION_THRESHOLD = 3;
        private static final int VIP_LOOKBACK_YEARS = 1;
        private final Map<Integer, Cliente> clientes = new HashMap<>();

        /**
         * Registra un nuevo cliente en el hotel.
         *
         * @param nombre El nombre del cliente.
         * @param email El correo electrónico del cliente.
         * @param dni El DNI del cliente.
         * @param esVip Indica si el cliente es VIP o no.
         */
        public void registrarCliente(String nombre, String email, String dni, boolean esVip) {
            Cliente cliente = new Cliente(clientes.size() + 1, nombre, dni, email, esVip);
            clientes.put(cliente.getId(), cliente);
        }

        /**
         * Obtiene un cliente por su ID.
         *
         * @param id El ID del cliente.
         * @return El cliente correspondiente al ID, o null si no existe.
         */
        public Cliente getCliente(int id) {
            return clientes.get(id);
        }

        /**
         * Muestra todos los clientes registrados en el hotel.
         */
        public void listarClientes() {
            clientes.values().forEach(cliente -> System.out.printf(
                    "Cliente #%d - Nombre: %s - DNI: %s - VIP: %b%n",
                    cliente.getId(), cliente.getNombre(), cliente.getDni(), cliente.isVip()));
        }

        /**
         * Actualiza el estado VIP de un cliente si ha superado el umbral de reservas recientes.
         *
         * @param cliente El cliente a actualizar.
         * @param numReservasRecientes El número de reservas realizadas recientemente.
         */
        public void actualizarEstadoVIP(Cliente cliente, int numReservasRecientes) {
            if (!cliente.isVip() && numReservasRecientes > VIP_RESERVATION_THRESHOLD) {
                cliente.setVip(true);
                System.out.println("El cliente " + cliente.getNombre() + " ha pasado a ser VIP");
            }
        }
    }

    /**
     * Clase encargada de gestionar las reservas del hotel.
     */
    private static class GestorReservas {
        private final GestorHabitaciones gestorHabitaciones;

        /**
         * Constructor de la clase GestorReservas.
         *
         * @param gestorHabitaciones El gestor de habitaciones del hotel.
         */
        public GestorReservas(GestorHabitaciones gestorHabitaciones) {
            this.gestorHabitaciones = gestorHabitaciones;
        }

        /**
         * Realiza una reserva de habitación para un cliente.
         *
         * @param cliente El cliente que realiza la reserva.
         * @param tipo El tipo de habitación a reservar.
         * @param fechaEntrada La fecha de entrada.
         * @param fechaSalida La fecha de salida.
         * @return El número de la habitación reservada, o un código de error negativo.
         */
        public int realizarReserva(Cliente cliente, Habitacion.RoomType tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {
            Optional<Habitacion> habitacionDisponible = gestorHabitaciones.encontrarHabitacionDisponible(tipo);
            if (habitacionDisponible.isEmpty()) {
                System.out.println("No hay habitaciones disponibles del tipo " + tipo);
                return -1;
            }

            Habitacion habitacion = habitacionDisponible.get();
            int idReserva = gestorHabitaciones.getReservasHabitacion(habitacion.getNumero()).size() + 1;
            Reserva reserva = new Reserva(
                    idReserva,
                    cliente.getId(),
                    habitacion.getNumero(),
                    fechaEntrada,
                    fechaSalida,
                    habitacion,
                    cliente
            );

            gestorHabitaciones.agregarReserva(habitacion, reserva);
            System.out.println("Reserva realizada con éxito");
            return habitacion.getNumero();
        }

        /**
         * Muestra todas las reservas del hotel.
         */
        public void listarReservas() {
            gestorHabitaciones.getHabitaciones().forEach(habitacion -> {
                List<Reserva> reservas = gestorHabitaciones.getReservasHabitacion(habitacion.getNumero());
                if (!reservas.isEmpty()) {
                    System.out.println("Habitación #" + habitacion.getNumero());
                    reservas.forEach(reserva -> System.out.printf(
                            "Reserva #%d - Cliente: %d - Fecha de entrada: %s - Fecha de salida: %s%n",
                            reserva.getId(), reserva.getClienteId(),
                            reserva.getFechaInicio(), reserva.getFechaFin()));
                }
            });
        }
    }
}

