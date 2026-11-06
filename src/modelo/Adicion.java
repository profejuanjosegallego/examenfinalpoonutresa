package modelo;

public class Adicion implements Cobrable {
    private final String nombre;
    private final double precio;

    public Adicion(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }

    @Override
    public double calcularPrecio() {
        return precio;
    }
}   
