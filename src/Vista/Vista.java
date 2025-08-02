package Vista;

import java.util.Scanner;
import java.util.List;

import Modelo.Celda;
import Modelo.Tablero;
import excepciones.AccionInvalidaException;
import excepciones.CoordenadaInvalidaException;

public class Vista {
    private Scanner scanner;
    private final char[] LETRAS_FILAS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    public Vista() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarTablero(Tablero tablero) { // Encabezado de columnas (1-10)

        System.out.print("     ");
        for (int i = 1; i <= tablero.getColumnas(); i++) {
            System.out.print(i + (i < 10 ? "   " : "  "));
        }
        System.out.println();

        System.out.print("   +");
        for (int i = 0; i < tablero.getColumnas(); i++) { // Línea horizontal superior
            System.out.print("---+");
        }
        System.out.println();

        Celda[][] celdas = tablero.getCeldas();
        for (int i = 0; i < tablero.getFilas(); i++) {
            System.out.print(LETRAS_FILAS[i] + "  |");
            for (int j = 0; j < tablero.getColumnas(); j++) {
                System.out.print(" " + celdas[i][j].getRepresentacion() + " |");
            }
            System.out.println();

            System.out.print("   +");
            for (int k = 0; k < tablero.getColumnas(); k++) {
                System.out.print("---+");
            }
            System.out.println();
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    public void mostrarPartidasGuardadas(List<String> partidas) {
        System.out.println("\nPartidas guardadas:");
        for (int i = 0; i < partidas.size(); i++) {
            System.out.println((i+1) + ") " + partidas.get(i));
        }
    }
    
    public String obtenerNombrePartida() {
        System.out.print("Ingresa el nombre de la partida a cargar: ");
        return scanner.nextLine();
    }
    
    public String obtenerNombreGuardado() {
        System.out.print("Nombre para guardar la partida: ");
        return scanner.nextLine();
    }
    
    public int obtenerOpcion(int min, int max) {
        while (true) {
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= min && opcion <= max) {
                    return opcion;
                }
                System.out.println("Opción inválida. Introduce un número entre " + min + " y " + max);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
            }
        }
    }

    public String obtenerAccion() throws AccionInvalidaException {
        System.out.print("\nSeleccione acción (D)escubrir o (M)arcar: ");
        String accion = scanner.nextLine().toUpperCase();
        
        if (!accion.equals("D") && !accion.equals("M")) {
            throw new AccionInvalidaException("Acción no valida. Use (D) descubrir o (M) marcar.");
        }
        
        return accion;
    }

    public int[] obtenerCoordenadas() throws CoordenadaInvalidaException {
        System.out.print("Ingrese coordenada (Ejem. F6): ");
        String input = scanner.nextLine().toUpperCase();
        
        if (input.length() < 2) {
            throw new CoordenadaInvalidaException("Formato incorrecto. Use letra (A-H) y número (1-10)");
        }
        
        char letraFila = input.charAt(0);
        String parteNumero = input.substring(1);
        
        int fila = letraFila - 'A';
        int columna;
        
        try {
            columna = Integer.parseInt(parteNumero) - 1;
        } catch (NumberFormatException e) {
            throw new CoordenadaInvalidaException("Error número columna. Use 1-10");
        }
        
        if (fila < 0 || fila >= LETRAS_FILAS.length) {
            throw new CoordenadaInvalidaException("Error letra fila. Use A-H");
        }
        
        if (columna < 0 || columna >= 10) {
            throw new CoordenadaInvalidaException("Número de columna inválido. Use 1-10");
        }
        
        return new int[]{fila, columna};
    }

    public void cerrar() {
        scanner.close();
    }   
    
}