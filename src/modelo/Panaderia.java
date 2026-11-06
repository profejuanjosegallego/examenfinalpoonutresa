package modelo;

public class Panaderia extends ProductoMenu {
    private final int unidades;
    private final boolean recienHorneado;

    public Panaderia(String codigo, String nombre, double precioBase, int stock,
                     int unidades, boolean recienHorneado) {
        super(codigo, nombre, precioBase, stock);
        
        if (unidades <= 0) {
            throw new IllegalArgumentException("La porción de panadería debe tener al menos 1 unidad.");
        }
        
        this.unidades = unidades;
        this.recienHorneado = recienHorneado;
    }
 
    public int getUnidades() { return unidades; }

    @Override
    public double calcularPrecio() {
        return getPrecioBase() * unidades;
    }

    @Override
    public int minutosPreparacion() {
        return recienHorneado ? 12 : 3;
    }

    @Override
    public String getCategoria() {
        return "Panadería";
    }

    @Override
    public boolean esVegetariano() {
        return true;
    }
}