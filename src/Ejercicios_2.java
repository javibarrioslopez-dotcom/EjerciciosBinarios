import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicios_2 {

    private static final String RUTA_FICHERO = "src/";

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ejercicio1();
        ejercicio2();
        ejercicio3();
        ejercicio4();
        ejercicio5();
        ejercicio6();
        ejercicio7();
        ejercicio8();
        ejercicio9();
        ejercicio10();
    }

    private static int leerEntero(String mensaje, int minimo, int maximo) {
        int valor;
        do {
            System.out.print(mensaje);
            valor = sc.nextInt();
            sc.nextLine();
            if (valor < minimo || valor > maximo) {
                System.out.println("El valor debe ser mayor o igual que " + minimo + (maximo == Integer.MAX_VALUE ? "." : " y menor o igual que " + maximo + "."));
            }
        } while (valor < minimo || valor > maximo);
        return valor;
    }

    private static double leerDecimal(String mensaje, double minimo) {
        double valor;
        do {
            System.out.print(mensaje);
            valor = Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
            if (valor < minimo) {
                System.out.println("El valor debe ser mayor o igual que " + minimo + ".");
            }
        } while (valor < minimo);
        return valor;
    }

    private static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();
        } while (texto.isEmpty());
        return texto;
    }

    private static boolean leerSiNo(String mensaje) {
        String respuesta;
        do {
            System.out.print(mensaje);
            respuesta = sc.nextLine().trim();
        } while (!respuesta.equalsIgnoreCase("SI") && !respuesta.equalsIgnoreCase("NO"));
        return respuesta.equalsIgnoreCase("SI");
    }

    private static char leerSexo() {
        String respuesta;
        do {
            System.out.print("Sexo (H-M): ");
            respuesta = sc.nextLine().trim();
        } while (!respuesta.equalsIgnoreCase("H") && !respuesta.equalsIgnoreCase("M"));
        return Character.toUpperCase(respuesta.charAt(0));
    }

    public static void ejercicio1() {
        System.out.println("Ejercicio 1: números aleatorios");
        int cantidad = leerEntero("Cantidad de números aleatorios a guardar: ", 1, Integer.MAX_VALUE);
        int minimo = leerEntero("Límite inferior del rango (positivo): ", 1, Integer.MAX_VALUE);
        int maximo = leerEntero("Límite superior del rango: ", minimo, Integer.MAX_VALUE);

        try (DataOutputStream escribirFichero = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + "num_aleat.bin", true))) {
            for (int i = 0; i < cantidad; i++) {
                int numero = (int) (minimo + Math.random() * ((long) maximo - minimo + 1));
                escribirFichero.writeInt(numero);
            }
            System.out.println("Se han añadido " + cantidad + " números al fichero num_aleat.bin.");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero.");
        }

        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(RUTA_FICHERO + "num_aleat.bin"))) {
            System.out.println("Contenido del fichero:");
            int total = 0;
            while (true) {
                int numero = leerFichero.readInt();
                System.out.print(numero + " ");
                total++;
                if (total % 10 == 0) {
                    System.out.println();
                }
            }
        } catch (EOFException e) {
            System.out.println();
            System.out.println("Fin del fichero.");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
        }
    }

    public static void ejercicio2() {
        System.out.println("\nEjercicio 2: vehículos");
        int cantidad = leerEntero("Número de vehículos a introducir: ", 1, Integer.MAX_VALUE);

        try (DataOutputStream escribirFichero = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + "vehiculos.bin", true))) {
            for (int i = 1; i <= cantidad; i++) {
                System.out.println("Vehículo " + i + ":");
                String matricula = leerTexto("  Matrícula: ");
                String marca = leerTexto("  Marca: ");
                double deposito = leerDecimal("  Tamaño del depósito (litros): ", 0.1);
                String modelo = leerTexto("  Modelo: ");
                escribirFichero.writeUTF(matricula);
                escribirFichero.writeUTF(marca);
                escribirFichero.writeDouble(deposito);
                escribirFichero.writeUTF(modelo);
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero.");
        }

        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(RUTA_FICHERO + "vehiculos.bin"))) {
            System.out.println("Vehículos almacenados:");
            while (true) {
                String matricula = leerFichero.readUTF();
                String marca = leerFichero.readUTF();
                double deposito = leerFichero.readDouble();
                String modelo = leerFichero.readUTF();
                System.out.println("Matrícula: " + matricula + " | Marca: " + marca + " | Depósito: " + deposito + " litros | Modelo: " + modelo);
            }
        } catch (EOFException e) {
            System.out.println("Fin del fichero.");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
        }
    }

    private static void guardarBecario(DataOutputStream escribirFichero) throws IOException {
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        char sexo = leerSexo();
        int edad = leerEntero("Edad (20-60): ", 20, 60);
        int suspensos = leerEntero("Número de suspensos del curso anterior (0-4): ", 0, 4);
        boolean residenciaFamiliar = leerSiNo("Residencia familiar (SI o NO): ");
        double ingresos = leerDecimal("Ingresos anuales de la familia: ", 0);
        boolean tieneBeca = leerSiNo("Tiene beca (SI o NO): ");
        escribirFichero.writeUTF(nombre);
        escribirFichero.writeUTF(apellido);
        escribirFichero.writeChar(sexo);
        escribirFichero.writeInt(edad);
        escribirFichero.writeInt(suspensos);
        escribirFichero.writeBoolean(residenciaFamiliar);
        escribirFichero.writeDouble(ingresos);
        escribirFichero.writeBoolean(tieneBeca);
    }

    public static void ejercicio3() {
        System.out.println("\nEjercicio 3: un becario");
        try (DataOutputStream escribirFichero = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + "datosbeca.bin"))) {
            guardarBecario(escribirFichero);
            System.out.println("Datos guardados en datosbeca.bin (" + new File(RUTA_FICHERO + "datosbeca.bin").length() + " bytes).");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero.");
        }
    }

    public static void ejercicio4() {
        System.out.println("\nEjercicio 4: varios becarios");
        int cantidad = leerEntero("Número de becarios a introducir: ", 1, Integer.MAX_VALUE);

        try (DataOutputStream escribirFichero = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + "datosbeca.bin", true))) {
            for (int i = 1; i <= cantidad; i++) {
                System.out.println("Becario " + i + ":");
                guardarBecario(escribirFichero);
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero.");
        }

        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(RUTA_FICHERO + "datosbeca.bin"))) {
            System.out.println("Becarios almacenados:");
            while (true) {
                String nombre = leerFichero.readUTF();
                String apellido = leerFichero.readUTF();
                char sexo = leerFichero.readChar();
                int edad = leerFichero.readInt();
                int suspensos = leerFichero.readInt();
                boolean residenciaFamiliar = leerFichero.readBoolean();
                double ingresos = leerFichero.readDouble();
                boolean tieneBeca = leerFichero.readBoolean();
                System.out.println(nombre + " " + apellido + " | Sexo: " + sexo + " | Edad: " + edad + " | Suspensos: " + suspensos
                        + " | Residencia familiar: " + (residenciaFamiliar ? "SI" : "NO") + " | Ingresos: " + ingresos
                        + " € | Beca: " + (tieneBeca ? "SI" : "NO"));
            }
        } catch (EOFException e) {
            System.out.println("Fin del fichero.");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
        }
    }

    public static void ejercicio5() {
        System.out.println("\nEjercicio 5: cuantía de las becas");
        if (!new File(RUTA_FICHERO + "datosbeca.bin").exists()) {
            System.out.println("El fichero datosbeca.bin no existe. Ejecuta antes el ejercicio 4.");
            return;
        }

        int becasConcedidas = 0;
        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(RUTA_FICHERO + "datosbeca.bin"))) {
            while (true) {
                String nombre = leerFichero.readUTF();
                String apellido = leerFichero.readUTF();
                leerFichero.readChar();
                int edad = leerFichero.readInt();
                int suspensos = leerFichero.readInt();
                boolean residenciaFamiliar = leerFichero.readBoolean();
                double ingresos = leerFichero.readDouble();
                boolean tieneBeca = leerFichero.readBoolean();

                if (tieneBeca && suspensos < 2) {
                    double cuantia = 1500;
                    if (ingresos <= 12000) {
                        cuantia += 500;
                    }
                    if (edad < 23) {
                        cuantia += 200;
                    }
                    if (suspensos == 0) {
                        cuantia += 500;
                    } else {
                        cuantia += 200;
                    }
                    if (!residenciaFamiliar) {
                        cuantia += 1000;
                    }
                    System.out.printf("%s %s -> %.2f €%n", nombre, apellido, cuantia);
                    becasConcedidas++;
                }
            }
        } catch (EOFException e) {
            System.out.println("Becas concedidas: " + becasConcedidas);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
        }
    }

    private static void mostrarPersonas(String nombreFichero) {
        int total = 0;
        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(RUTA_FICHERO + nombreFichero))) {
            System.out.println("Contenido de " + nombreFichero + ":");
            while (true) {
                String nombre = leerFichero.readUTF();
                String apellidos = leerFichero.readUTF();
                int edad = leerFichero.readInt();
                String telefono = leerFichero.readUTF();
                String email = leerFichero.readUTF();
                String ciudad = leerFichero.readUTF();
                String nacionalidad = leerFichero.readUTF();
                String profesion = leerFichero.readUTF();
                System.out.println(nombre.trim() + " " + apellidos + " | " + edad + " años | Tel: " + telefono + " | " + email
                        + " | " + ciudad + " | " + nacionalidad.trim() + " | " + profesion);
                total++;
            }
        } catch (EOFException e) {
            System.out.println("Total de personas: " + total);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero " + nombreFichero + ".");
        }
    }

    public static void ejercicio6() {
        System.out.println("\nEjercicio 6: personas");
        int cantidad = leerEntero("Número de personas a introducir: ", 1, Integer.MAX_VALUE);

        try (DataOutputStream escribirFichero = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + "datospersonas.dat"))) {
            for (int i = 1; i <= cantidad; i++) {
                System.out.println("Persona " + i + ":");
                escribirFichero.writeUTF(leerTexto("  Nombre: "));
                escribirFichero.writeUTF(leerTexto("  Apellidos: "));
                escribirFichero.writeInt(leerEntero("  Edad: ", 0, 120));
                escribirFichero.writeUTF(leerTexto("  Teléfono: "));
                escribirFichero.writeUTF(leerTexto("  Email: "));
                escribirFichero.writeUTF(leerTexto("  Ciudad de residencia: "));
                escribirFichero.writeUTF(leerTexto("  Nacionalidad: "));
                escribirFichero.writeUTF(leerTexto("  Profesión: "));
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero.");
        }

        mostrarPersonas("datospersonas.dat");
    }

    public static void ejercicio7() {
        System.out.println("\nEjercicio 7: menores, adultos y mayores");
        if (!new File(RUTA_FICHERO + "muchosdatos.bin").exists()) {
            System.out.println("El fichero no existe.");
            return;
        }

        int menores = 0;
        int adultos = 0;
        int mayores = 0;
        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(RUTA_FICHERO + "muchosdatos.bin"));
             DataOutputStream escribirMenores = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + "menores.dat"));
             DataOutputStream escribirAdultos = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + "adultos.dat"));
             DataOutputStream escribirMayores = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + "mayores.dat"))) {
            while (true) {
                String nombre = leerFichero.readUTF();
                String apellidos = leerFichero.readUTF();
                int edad = leerFichero.readInt();
                String telefono = leerFichero.readUTF();
                String email = leerFichero.readUTF();
                String ciudad = leerFichero.readUTF();
                String nacionalidad = leerFichero.readUTF();
                String profesion = leerFichero.readUTF();

                DataOutputStream destino;
                if (edad < 18) {
                    destino = escribirMenores;
                    menores++;
                } else if (edad > 65) {
                    destino = escribirMayores;
                    mayores++;
                } else {
                    destino = escribirAdultos;
                    adultos++;
                }
                destino.writeUTF(nombre);
                destino.writeUTF(apellidos);
                destino.writeInt(edad);
                destino.writeUTF(telefono);
                destino.writeUTF(email);
                destino.writeUTF(ciudad);
                destino.writeUTF(nacionalidad);
                destino.writeUTF(profesion);
            }
        } catch (EOFException e) {
            System.out.println("Copia finalizada: " + menores + " menores, " + adultos + " adultos y " + mayores + " mayores.");
        } catch (IOException e) {
            System.out.println("Error al copiar los datos.");
            return;
        }

        mostrarPersonas(RUTA_FICHERO + "menores.dat");
        mostrarPersonas(RUTA_FICHERO + "adultos.dat");
        mostrarPersonas(RUTA_FICHERO + "mayores.dat");
    }

    public static void ejercicio8() {
        System.out.println("\nEjercicio 8: temperaturas de un día");
        if (!new File(RUTA_FICHERO + "temperaturas.txt").exists()) {
            System.out.println("El fichero no existe.");
            return;
        }
        int dia = leerEntero("Día de septiembre (1-30): ", 1, 30);

        int registros = 0;
        try (BufferedReader leerFichero = new BufferedReader(new FileReader(RUTA_FICHERO + "temperaturas.txt"));
             DataOutputStream escribirFichero = new DataOutputStream(new FileOutputStream(RUTA_FICHERO + "Septemp.dat"))) {
            String linea;
            while ((linea = leerFichero.readLine()) != null) {
                String[] partes = linea.split(",");
                int diaLinea = Integer.parseInt(partes[0].trim().split(" ")[1]);
                if (diaLinea == dia) {
                    int hora = Integer.parseInt(partes[1].trim().split(" ")[1].substring(0, 2));
                    int temperatura = Integer.parseInt(partes[2].trim().split(" ")[1].replaceAll("[^0-9-]", ""));
                    escribirFichero.writeInt(diaLinea);
                    escribirFichero.writeInt(hora);
                    escribirFichero.writeInt(temperatura);
                    registros++;
                }
            }
            System.out.println("Se han guardado " + registros + " registros del día " + dia + " en Septemp.dat.");
        } catch (IOException e) {
            System.out.println("Error al procesar los ficheros.");
        }
    }

    public static void ejercicio9() {
        System.out.println("\nEjercicio 9: estadísticas de temperaturas");
        if (!new File(RUTA_FICHERO + "Septemp.dat").exists()) {
            System.out.println("El fichero Septemp.dat no existe. Ejecuta antes el ejercicio 8.");
            return;
        }

        int dia = 0;
        int maxima = Integer.MIN_VALUE;
        int minima = Integer.MAX_VALUE;
        int horaMaxima = 0;
        int horaMinima = 0;
        int suma = 0;
        int total = 0;
        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(RUTA_FICHERO + "Septemp.dat"))) {
            while (true) {
                dia = leerFichero.readInt();
                int hora = leerFichero.readInt();
                int temperatura = leerFichero.readInt();
                if (temperatura > maxima) {
                    maxima = temperatura;
                    horaMaxima = hora;
                }
                if (temperatura < minima) {
                    minima = temperatura;
                    horaMinima = hora;
                }
                suma += temperatura;
                total++;
            }
        } catch (EOFException e) {
            if (total == 0) {
                System.out.println("El fichero no contiene registros.");
                return;
            }
            System.out.println("Resultados del día " + dia + " de septiembre (" + total + " registros):");
            System.out.println("Temperatura máxima: " + maxima + " °C");
            System.out.println("Temperatura mínima: " + minima + " °C");
            System.out.printf("Temperatura media: %.2f °C%n", (double) suma / total);
            System.out.printf("Hora más calurosa: %02d:00%n", horaMaxima);
            System.out.printf("Hora más fría: %02d:00%n", horaMinima);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
        }
    }

    public static void ejercicio10() {
        System.out.println("\nEjercicio 10: actualizar nóminas");
        File original = new File(RUTA_FICHERO + "nominas.bin");
        File temporal = new File(RUTA_FICHERO + "nominas_temporal.bin");
        if (!original.exists()) {
            System.out.println("El fichero nominas.bin no existe en la carpeta " + RUTA_FICHERO + ".");
            return;
        }

        int eliminados = 0;
        boolean procesado = false;
        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(original));
             DataOutputStream escribirFichero = new DataOutputStream(new FileOutputStream(temporal))) {
            while (true) {
                String nombre = leerFichero.readUTF();
                int diasBaja = leerFichero.readInt();
                double nomina = leerFichero.readDouble();
                if (diasBaja > 10) {
                    eliminados++;
                } else {
                    if (diasBaja == 0) {
                        nomina = nomina * 1.05;
                    } else if (diasBaja >= 4) {
                        nomina = nomina * 0.90;
                    }
                    escribirFichero.writeUTF(nombre);
                    escribirFichero.writeInt(diasBaja);
                    escribirFichero.writeDouble(nomina);
                }
            }
        } catch (EOFException e) {
            procesado = true;
        } catch (IOException e) {
            System.out.println("Error al actualizar las nóminas.");
        }

        if (!procesado) {
            return;
        }
        if (!original.delete() || !temporal.renameTo(original)) {
            System.out.println("No se ha podido sustituir el fichero original.");
            return;
        }

        try (DataInputStream leerFichero = new DataInputStream(new FileInputStream(original))) {
            System.out.println("Contenido actualizado de nominas.bin:");
            while (true) {
                String nombre = leerFichero.readUTF();
                int diasBaja = leerFichero.readInt();
                double nomina = leerFichero.readDouble();
                System.out.printf("%s | Días de baja: %d | Nómina: %.2f €%n", nombre, diasBaja, nomina);
            }
        } catch (EOFException e) {
            System.out.println("Empleados dados de baja (eliminados del fichero): " + eliminados);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
        }
    }
}