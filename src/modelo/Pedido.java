package modelo;

import java.util.ArrayList;

public class Pedido implements Identificable, Cobrable {
    private final String codigo;
    private final Mesa mesa;
    private final int personas;
    private final boolean aceptaPropina;
    private final ArrayList<LineaPedido> lineas;
    private boolean pagado;

    public Pedido(String codigo, Mesa mesa, int personas, boolean aceptaPropina) {
        this.codigo = codigo;
        this.mesa = mesa;
        this.personas = personas;
        this.aceptaPropina = aceptaPropina;
        this.lineas = new ArrayList<>();
        this.pagado = false;
    }

    @Override
    public String getCodigo() { return codigo; }
    public Mesa getMesa() { return mesa; }
    public int getPersonas() { return personas; }
    public boolean isPagado() { return pagado; }
    public int getCantidadLineas() { return lineas.size(); }

    public ArrayList<LineaPedido> getLineas() {
        return new ArrayList<>(lineas);
    }

    public void agregarLinea(LineaPedido linea) {
        if (linea != null) {
            lineas.add(linea);
        }
    }

    public void marcarPagado() {
        pagado = true;
    }

    public double calcularSubtotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            if (linea != null) {
                total += linea.calcularPrecio();
            }
        }
        return total;
    }
    public double calcularDescuento() {
        if (personas >= 4 && calcularSubtotal() >= 150000) {
            return (calcularSubtotal() - calcularDescuento()) * 0.10;
        }
        return 0;
    }
    
    public double calcularImpoconsumo() {
        double subtotalConDescuento = calcularSubtotal() - calcularDescuento(); 
        return subtotalConDescuento * 0.08;
    }
    public double calcularPropina() {
        if (!aceptaPropina) {
            return 0;
        }
        return (calcularSubtotal() - calcularDescuento() + calcularImpoconsumo()) * 0.10;
    }
    @Override
    public double calcularPrecio() {
        return calcularSubtotal() - calcularDescuento() + calcularImpoconsumo() + calcularPropina();
    }
    public int calcularTiempoEstimado() {
        int mayor = 0;
        for (int i = 0;  i < lineas.size(); i++) {
            int minutos = lineas.get(i).getProducto().minutosPreparacion();
            if (minutos > mayor) {
                mayor = minutos;
            }
        }
        return mayor;
    }
    public boolean esVegetariano() {
        for (LineaPedido linea : lineas) {
            if (!linea.getProducto().esVegetariano()) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
    @Override
    public String obtenerFicha() {
        return "Pedido [" + codigo + "] mesa " + mesa.getNumero() + ", " + personas + " personas, "
                + calcularTiempoEstimado() + " min, total $" + String.format("%.0f", calcularPrecio());
    }
    public String obtenerCuenta() {
        return "Subtotal $" + String.format("%.0f", calcularSubtotal())
                + " | Descuento $" + String.format("%.0f", calcularDescuento())
                + " | Impoconsumo $" + String.format("%.0f", calcularImpoconsumo())
                + " | Propina $" + String.format("%.0f", calcularPropina())
                + " | Total $" + String.format("%.0f", calcularPrecio());
    }
}
