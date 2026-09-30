import java.io.Serializable;

public class Producto implements Serializable {

    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Precio: " + precio + " €, Stock: " + stock;
    }
}