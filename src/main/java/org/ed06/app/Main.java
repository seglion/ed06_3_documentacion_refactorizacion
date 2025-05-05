package org.ed06.app;

import org.ed06.model.Cliente;
import org.ed06.model.Habitacion;
import org.ed06.model.Hotel;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

/**
 * Clase principal que gestiona la interacción con el usuario para un sistema de gestión de hoteles.
 * Permite registrar habitaciones, listar habitaciones disponibles, realizar reservas,
 * listar reservas, registrar clientes y listar clientes.
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);

    // Constantes para las opciones del menú
    private static final int REGISTRAR_HABITACION = 1;
    private static final int LISTAR_HABITACIONES_DISPONIBLES = 2;
    private static final int RESERVAR_HABITACION = 11;
    private static final int LISTAR_RESERVAS = 12;
    private static final int LISTAR_CLIENTES = 21;
    private static final int REGISTRAR_CLIENTE = 22;
    private static final int SALIR = 0;

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Hotel hotel = new Hotel("El mirador", "Calle Entornos de Desarrollo 6", "123456789");

        // Registramos algunas habitaciones
        hotel.registrarHabitacion(Habitacion.RoomType.SIMPLE, 50);
        hotel.registrarHabitacion(Habitacion.RoomType.DOBLE, 80);
        hotel.registrarHabitacion(Habitacion.RoomType.SUITE, 120);
        hotel.registrarHabitacion(Habitacion.RoomType.LITERAS, 200);

        // Registramos algunos clientes
        hotel.registrarCliente("Daniel", "daniel@daniel.com", "12345678A", true);
        hotel.registrarCliente("Adrián", "adrian@adrian.es", "87654321B", false);

        // Bucle principal del menú
        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case REGISTRAR_HABITACION:
                    registrarHabitacion(hotel);
                    break;
                case LISTAR_HABITACIONES_DISPONIBLES:
                    hotel.listarHabitacionesDisponibles();
                    break;
                case RESERVAR_HABITACION:
                    reservarHabitacion(hotel);
                    break;
                case LISTAR_RESERVAS:
                    hotel.listarReservas();
                    break;
                case LISTAR_CLIENTES:
                    hotel.listarClientes();
                    break;
                case REGISTRAR_CLIENTE:
                    registrarCliente(hotel);
                    break;
                case SALIR:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    /**
     * Registra una nueva habitación en el hotel.
     *
     * @param hotel Instancia del hotel donde se registrará la habitación.
     */
    private static void registrarHabitacion(Hotel hotel) {
        System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE, LITERAS): ");
        String tipo = scanner.nextLine().toUpperCase();
        try {
            Habitacion.RoomType roomType = Habitacion.RoomType.valueOf(tipo);
            System.out.println("Introduce el precio base de la habitación: ");
            double precioBase = scanner.nextDouble();
            scanner.nextLine();
            hotel.registrarHabitacion(roomType, precioBase);
            System.out.println("Habitación registrada: " + roomType + " - Precio base: " + precioBase);
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de habitación no válido.");
        }
    }

    /**
     * Realiza una reserva de habitación en el hotel.
     *
     * @param hotel Instancia del hotel donde se realizará la reserva.
     */
    private static void reservarHabitacion(Hotel hotel) {
        System.out.println("Introduce el id del cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE, LITERAS): ");
        String tipo = scanner.nextLine().toUpperCase();
        try {
            Habitacion.RoomType roomType = Habitacion.RoomType.valueOf(tipo);
            System.out.println("Introduce la fecha de entrada (año, mes, día): ");
            LocalDate fechaEntrada = leerFecha();
            System.out.println("Introduce la fecha de salida (año, mes, día): ");
            LocalDate fechaSalida = leerFecha();
            int numeroHabitacion = hotel.reservarHabitacion(clienteId, roomType, fechaEntrada, fechaSalida);
            Optional<Habitacion> habitacion = hotel.getHabitacion(numeroHabitacion);
            habitacion.ifPresent(h -> System.out.println(
                    "Habitación #" + h.getNumero() + " - Tipo: " + h.getTipo() + " - Precio base: " + h.getPrecioBase()));
            System.out.println("Número de habitación reservada: " + numeroHabitacion);
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de habitación no válido.");
        }
    }

    /**
     * Registra un nuevo cliente en el hotel.
     *
     * @param hotel Instancia del hotel donde se registrará el cliente.
     */
    private static void registrarCliente(Hotel hotel) {
        System.out.println("Introduce el nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.println("Introduce el email del cliente: ");
        String email = scanner.nextLine();
        System.out.println("Introduce el DNI del cliente: ");
        String dni = scanner.nextLine();
        System.out.println("¿Es VIP? (true/false): ");
        boolean esVip = scanner.nextBoolean();
        scanner.nextLine();
        hotel.registrarCliente(nombre, email, dni, esVip);
        System.out.println("Cliente registrado: " + nombre);
    }

    /**
     * Lee una fecha desde la entrada del usuario.
     *
     * @return La fecha introducida por el usuario.
     */
    private static LocalDate leerFecha() {
        System.out.println("Introduce el año: ");
        int anio = scanner.nextInt();
        System.out.println("Introduce el mes: ");
        int mes = scanner.nextInt();
        System.out.println("Introduce el día: ");
        int dia = scanner.nextInt();
        scanner.nextLine();
        return LocalDate.of(anio, mes, dia);
    }

    /**
     * Muestra el menú principal de opciones al usuario.
     */
    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Registrar habitación");
        System.out.println("2. Listar habitaciones disponibles");
        System.out.println("11. Reservar habitación");
        System.out.println("12. Listar reservas");
        System.out.println("21. Listar clientes");
        System.out.println("22. Registrar cliente");
        System.out.println("0. Salir");
    }
}