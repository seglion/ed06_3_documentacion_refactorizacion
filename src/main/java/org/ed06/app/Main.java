package org.ed06.app;

import org.ed06.model.Habitacion;
import org.ed06.model.Hotel;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    // Definimos constantes para las diferentes opciones del menú
    private static final int REGISTRAR_HABITACION = 1;
    private static final int LISTAR_HABITACIONES_DISPONIBLES = 2;
    private static final int RESERVAR_HABITACION = 11;
    private static final int LISTAR_RESERVAS = 12;
    private static final int LISTAR_CLIENTES = 21;
    private static final int REGISTRAR_CLIENTE = 22;

    public static void main(String[] args) {
        // Variales locales
        String tipo;

        // Creamos un menú para el administrador con las diferentes opciones proporcionadas
        Hotel hotel = new Hotel();

        // Mostramos el menú
        mostrarMenu();
        int opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {

            case REGISTRAR_HABITACION:
                System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
                tipo = scanner.nextLine();
                System.out.println("Introduce el precio base de la habitación: ");
                double precioBase = scanner.nextDouble();
                scanner.nextLine();
                hotel.registrarHabitacion(tipo, precioBase);
                break;
            case LISTAR_HABITACIONES_DISPONIBLES:
                hotel.listarHabitacionesDisponibles();
                break;
            case RESERVAR_HABITACION:
                System.out.println("Introduce el id del cliente: ");
                int clienteId = scanner.nextInt();
                System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
                tipo = scanner.next();
                System.out.println("Introduce la fecha de entrada (yyyy-mm-dd): ");
                int anioEntrada = scanner.nextInt();
                scanner.nextLine();
                int mesEntrada = scanner.nextInt();
                scanner.nextLine();
                int diaEntrada = scanner.nextInt();
                scanner.nextLine();
                LocalDate fechaEntrada = LocalDate.of(anioEntrada, mesEntrada, diaEntrada);
                System.out.println("Introduce la fecha de salida (yyyy-mm-dd): ");
                int anioSalida = scanner.nextInt();
                scanner.nextLine();
                int mesSalida = scanner.nextInt();
                scanner.nextLine();
                int diaSalida = scanner.nextInt();
                scanner.nextLine();
                LocalDate fechaSalida = LocalDate.of(anioSalida, mesSalida, diaSalida);
                int numeroHabitacion = hotel.reservarHabitacion(clienteId, tipo, fechaEntrada, fechaSalida);
                System.out.println("Datos de la habitacion");
                Habitacion habitacion = hotel.getHabitacion(numeroHabitacion);
                System.out.println("Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo() + " - Precio base: " + habitacion.getPrecioBase());
                System.out.println("Número de habitación reservada: " + numeroHabitacion);
                break;
            case LISTAR_RESERVAS:
                hotel.listarReservas();
                break;
            case LISTAR_CLIENTES:
                hotel.listarClientes();
                break;
            case REGISTRAR_CLIENTE:
                System.out.println("Introduce el nombre del cliente: ");
                String nombre = scanner.next();
                System.out.println("Introduce el DNI del cliente: ");
                String dni = scanner.next();
                System.out.println("Introduce el email del cliente: ");
                String email = scanner.next();
                System.out.println("¿Es VIP? (true/false): ");
                boolean esVip = scanner.nextBoolean();
                hotel.registrarCliente(nombre, email, dni, esVip);
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }


    }

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