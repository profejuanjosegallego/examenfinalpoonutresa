package modelo;

/** Todo lo que se vende en la carta del brunch. */
public abstract class ProductoMenu implements Identificable, Cobrable {
    private final String codigo;
    private String nombre;
    private double precioBase;
    private int stock;
    private boolean disponible;

    public ProductoMenu(String codigo, String nombre, double precioBase, int stock) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.codigo = codigo;
        setNombre(nombre);
        setPrecioBase(precioBase);
        this.stock = stock;
        this.disponible = true;
    }

    @Override
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecioBase() { return precioBase; }
    public int getStock() { return stock; }
    public boolean isDisponible() { return disponible; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public void setPrecioBase(double precioBase) {
        if (precioBase <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0");
        }
        this.precioBase = precioBase;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean puedeVender(int cantidad) {
        return disponible && cantidad > 0 && cantidad <= stock;
    }

public boolean vender(int cantidad) {
    if (!puedeVender(cantidad)) {
        return false;
    }
    stock -= cantidad;    
    if (stock == 0) {
        this.disponible = false;
    }
    return true;
}
    @Override
    public double calcularPrecio() {
        return precioBase;
    }

    public abstract int minutosPreparacion();

    public abstract String getCategoria();

    public abstract boolean esVegetariano();

    @Override
    public String obtenerFicha() {
        return getCategoria() + " [" + codigo + "] " + nombre + " - $"
                + String.format("%.0f", calcularPrecio()) + ", " + minutosPreparacion()
                + " min, stock " + stock;
    }
}

