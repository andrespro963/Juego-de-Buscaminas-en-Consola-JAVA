package Controlador;

import Modelo.GestorGuardado;
import Modelo.Tablero;
import Modelo.EstadoJuego;
import java.util.Scanner;
import Vista.Vista;
import java.io.IOException;

import excepciones.AccionInvalidaException;
import excepciones.BuscaminasException;
import excepciones.CeldaMarcadaException;
import excepciones.CeldaYaDescubiertaException;
import excepciones.CoordenadaInvalidaException;

public class Juego {
    private Tablero tablero;
    private Vista vista;
    private boolean juegoTerminado;
    private Scanner scanner; 

    public Juego() {
        this.scanner = new Scanner(System.in);  // Inicialización del Scanner
        this.vista = new Vista();
        this.juegoTerminado = false;
    }
    
    public void iniciar() {
        System.out.println("==== BUSCAMINAS ====");
        System.out.println("1. Nueva partida");
        System.out.println("2. Cargar partida");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
        
        String opcion = scanner.nextLine();
        
        switch (opcion) {
            case "1":
                this.tablero = new Tablero(10); // 10 minas por defecto
                jugar();
                break;
            case "2":
                cargarPartidaExistente();
                break;
            case "3":
                System.out.println("¡Hasta pronto!");
                break;
            default:
                System.out.println("Opción no válida");
                iniciar();
        }
    }
    
    private void cargarPartidaExistente() {
        try {
            System.out.print("Ingrese el nombre de la partida a cargar: ");
            String nombre = scanner.nextLine();
            
            EstadoJuego estado = GestorGuardado.cargarPartida(nombre);
            this.tablero = new Tablero(estado);
            System.out.println("Partida cargada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al cargar partida: " + e.getMessage());
            System.out.println("Iniciando nueva partida...");
            this.tablero = new Tablero(10);
        }
    }
    
    private void agregarOpcionGuardado() {
        // Durante el juego:        
        
        while (!juegoTerminado && !tablero.verificarVictoria()) {
            try {
                vista.mostrarTablero(tablero);
                
                String accion = vista.obtenerAccion();
                int[] coordenadas = vista.obtenerCoordenadas();
                int x = coordenadas[0];
                int y = coordenadas[1];

                switch (accion) {
                    case "D":
                        try {
                            if (tablero.descubrirCelda(x, y)) { //manejo de excpeciones personalziadas
                                vista.mostrarMensaje("¡DERROTA! pisaste una mina");
                                juegoTerminado = true;
                            }
                        } catch (CeldaYaDescubiertaException e) {
                            vista.mostrarMensaje("Error: " + e.getMessage());
                        } catch (CeldaMarcadaException e) {
                            vista.mostrarMensaje("Error: " + e.getMessage());
                        }
                        break;
                        
                    case "M":
                        try {
                            tablero.marcarCelda(x, y);
                        } catch (CeldaYaDescubiertaException e) {
                            vista.mostrarMensaje("Error: " + e.getMessage());
                        }
                        break;
                }
            } catch (AccionInvalidaException | CoordenadaInvalidaException e) {
                vista.mostrarMensaje("Error: " + e.getMessage());
            } catch (BuscaminasException e) {
                vista.mostrarMensaje("Error " + e.getMessage());
            }
        }

        if (!juegoTerminado) {
            vista.mostrarMensaje("¡Ganaste! Has descubierto todas las casillas");
        }

        vista.mostrarTablero(tablero);
        vista.cerrar();
    }

    private void guardarPartidaActual() {
        try {
            String nombre = vista.obtenerNombreGuardado();
            GestorGuardado.guardarPartida(tablero, nombre);
            vista.mostrarMensaje("Partida guardada correctamente");
        } catch (IOException e) {
            vista.mostrarMensaje("Error al guardar: " + e.getMessage());
        }
    }
    
    public void jugar() {
        System.out.println("==== Juego de Buscaminas en Consola ====");
        System.out.println("1. Nueva partida");
        System.out.println("2. Cargar partida");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        
        if (opcion == 2) {
            cargarPartidaExistente();
        } else {
            this.tablero = new Tablero(10); // 10 minas por defecto
        }

        // Bucle principal del juego
        while (!juegoTerminado && !tablero.verificarVictoria()) {
            vista.mostrarTablero(tablero);
            
            System.out.println("\nOpciones:");
            System.out.println("D - Descubrir casilla");
            System.out.println("M - Marcar/desmarcar casilla");
            System.out.println("G - Guardar partida");
            System.out.println("S - Salir");
            System.out.print("Seleccione una opción: ");
            
            String accion = scanner.nextLine().toUpperCase();
            
            switch (accion) {
                case "D":
                    descubrirCasilla();  // Método renombrado
                    break;
                case "M":
                    marcarCasilla();     // Método renombrado
                    break;
                case "G":
                    guardarPartida();
                    break;
                case "S":
                    if (confirmarSalida()) {
                        return;
                    }
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        
        if (tablero.verificarVictoria()) {
            System.out.println("¡Felicidades! Has ganado el juego.");
        }
    }

    private void descubrirCasilla() {
        System.out.print("Ingrese coordenada a descubrir (ej. A5): ");
        String input = scanner.nextLine().toUpperCase();
        
        try {
            int x = input.charAt(0) - 'A';
            int y = Integer.parseInt(input.substring(1)) - 1;
            
            if (tablero.descubrirCelda(x, y)) {
                System.out.println("¡BOOM! Has pisado una mina. Juego terminado.");
                juegoTerminado = true;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void marcarCasilla() {
        System.out.print("Ingrese coordenada a marcar (ej. A5): ");
        String input = scanner.nextLine().toUpperCase();
        
        try {
            int x = input.charAt(0) - 'A';
            int y = Integer.parseInt(input.substring(1)) - 1;
            
            tablero.marcarCelda(x, y);
            System.out.println("Casilla marcada/desmarcada correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private boolean confirmarSalida() {
        System.out.print("¿Deseas guardar la partida antes de salir? (S/N): ");
        String respuesta = scanner.nextLine().toUpperCase();
        
        if (respuesta.equals("S")) {
            guardarPartida();
        }
        
        System.out.print("¿Estás seguro que quieres salir? (S/N): ");
        respuesta = scanner.nextLine().toUpperCase();
        return respuesta.equals("S");
    }

    private void guardarPartida() {
        try {
            System.out.print("Ingrese un nombre para la partida: ");
            String nombre = scanner.nextLine();
            
            GestorGuardado.guardarPartida(tablero, nombre);
            System.out.println("Partida guardada correctamente como: " + nombre);
        } catch (Exception e) {
            System.out.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.jugar();
    }
}