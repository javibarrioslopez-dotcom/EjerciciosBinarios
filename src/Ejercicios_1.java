import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Ejercicios_1 {

    private static final String RUTA_FICHERO = "datos/";

    public static void main(String[] args) {
        new File(RUTA_FICHERO).mkdirs();
        ejercicio1();
        ejercicio2();
        ejercicio3();
        ejercicio4();
    }

    public static void ejercicio1() {
        System.out.println("Ejercicio 1: enteros del 1 al 50");
        String nombreFichero = "numeros.dat";

        try (DataOutputStream escribirFichero = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + nombreFichero))) {
            for (int i = 1; i <= 50; i++) {
                escribirFichero.writeInt(i);
            }
            System.out.println("Fichero " + nombreFichero + " escrito correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero.");
        }

        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(RUTA_FICHERO + nombreFichero))) {
            System.out.println("Contenido del fichero:");
            while (true) {
                int numero = leerFichero.readInt();
                System.out.print(numero + " ");
            }
        } catch (EOFException e) {
            System.out.println();
            System.out.println("Fin del fichero.");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
        }
    }

    public static void ejercicio2() {
        System.out.println("\nEjercicio 2: cinco alumnos");
        String nombreFichero = "alumnos.dat";
        String[] nombres = {"Ana", "Luis", "Marta", "Pedro", "Lucía"};
        double[] notas = {8.75, 6.5, 9.2, 5.0, 7.85};

        try (DataOutputStream escribirFichero = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + nombreFichero))) {
            for (int i = 0; i < nombres.length; i++) {
                escribirFichero.writeUTF(nombres[i]);
                escribirFichero.writeDouble(notas[i]);
            }
            System.out.println("Fichero " + nombreFichero + " escrito correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero.");
        }

        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(RUTA_FICHERO + nombreFichero))) {
            System.out.println("Alumnos almacenados:");
            while (true) {
                String nombre = leerFichero.readUTF();
                double nota = leerFichero.readDouble();
                System.out.println("Nombre: " + nombre + " | Nota media: " + nota);
            }
        } catch (EOFException e) {
            System.out.println("Fin del fichero.");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
        }
    }

    public static void ejercicio3() {
        System.out.println("\nEjercicio 3: un objeto Producto");
        String nombreFichero = "producto.dat";
        Producto producto = new Producto("Teclado mecánico", 49.99, 15);

        try (ObjectOutputStream escribirFichero = new ObjectOutputStream(new FileOutputStream(RUTA_FICHERO + nombreFichero))) {
            escribirFichero.writeObject(producto);
            System.out.println("Fichero " + nombreFichero + " escrito correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero.");
        }

        try (ObjectInputStream leerFichero = new ObjectInputStream(new FileInputStream(RUTA_FICHERO + nombreFichero))) {
            Producto productoLeido = (Producto) leerFichero.readObject();
            System.out.println("Producto recuperado:");
            System.out.println(productoLeido);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el fichero.");
        }
    }

    public static void ejercicio4() {
        System.out.println("\nEjercicio 4: tres objetos Producto");
        String nombreFichero = "productos.dat";
        Producto[] productos = {
                new Producto("Teclado mecánico", 49.99, 15),
                new Producto("Ratón inalámbrico", 19.5, 40),
                new Producto("Monitor 24 pulgadas", 129.9, 8)
        };

        try (ObjectOutputStream escribirFichero = new ObjectOutputStream(new FileOutputStream(RUTA_FICHERO + nombreFichero))) {
            for (Producto producto : productos) {
                escribirFichero.writeObject(producto);
            }
            System.out.println("Fichero " + nombreFichero + " escrito correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero.");
        }

        try (ObjectInputStream leerFichero = new ObjectInputStream(new FileInputStream(RUTA_FICHERO + nombreFichero))) {
            System.out.println("Productos recuperados:");
            while (true) {
                Producto producto = (Producto) leerFichero.readObject();
                System.out.println(producto);
            }
        } catch (EOFException e) {
            System.out.println("Fin del fichero.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el fichero.");
        }
    }
}
