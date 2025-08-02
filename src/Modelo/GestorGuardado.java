package Modelo;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class GestorGuardado {
    private static final String DIRECTORIO = "partidas_guardadas";
    private static final String EXTENSION = ".buscaminas";
    
    public static void guardarPartida(Tablero tablero, String nombreArchivo) throws IOException {
        
        Files.createDirectories(Paths.get(DIRECTORIO));// Crear directorio si no existe
        
        EstadoJuego estado = new EstadoJuego( // Crear estado del juego
            tablero.getCeldas(),
            tablero.getCeldasDescubiertas(),
            tablero.getCeldasMarcadas(),
            tablero.getMinas() - tablero.getCeldasMarcadas()
        );
        
        try (ObjectOutputStream oos = new ObjectOutputStream(// Serializar y guardar
             new FileOutputStream(DIRECTORIO + File.separator + nombreArchivo + EXTENSION))) {
            oos.writeObject(estado);
        }
    }
    
    public static EstadoJuego cargarPartida(String nombreArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
             new FileInputStream(DIRECTORIO + File.separator + nombreArchivo + EXTENSION))) {
            return (EstadoJuego) ois.readObject();
        }
    }
    
    public static List<String> listarPartidas() throws IOException {
        List<String> partidas = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(DIRECTORIO), "*" + EXTENSION)) {
            for (Path entry : stream) {
                partidas.add(entry.getFileName().toString().replace(EXTENSION, ""));
            }
        }
        return partidas;
    }
}