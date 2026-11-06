package modelo;

import java.util.ArrayList;

/** La cuenta de una mesa. */
public class Pedido implements Identificable, Cobrable {
    private static final int PERSONAS_PLAN_FAMILIAR = 4;
    private static final double SUBTOTAL_MINIMO_PLAN_FAMILIAR = 150000;
    private static final double PORCENTAJE_PLAN_FAMILIAR = 0.10;
    private static final double PORCENTAJE_IMPOCONSUMO = 0.08;
    private static final double PORCENTAJE_PROPINA = 0.10;

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

    /** Retorna una copia para que nadie modifique la lista interna desde afuera. */
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

    /** Suma lo que vale cada línea, antes de descuentos e impuestos. */
    public double calcularSubtotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.calcularPrecio();
        }
        return total;
    }

    /** Plan familiar: 10 % si son 4 o más personas Y el subtotal llega a $150.000. */
    public double calcularDescuento() {
        double subtotal = calcularSubtotal();
        if (personas >= PERSONAS_PLAN_FAMILIAR && subtotal >= SUBTOTAL_MINIMO_PLAN_FAMILIAR) {
            return subtotal * PORCENTAJE_PLAN_FAMILIAR;
        }
        return 0;
    }

    /** Lo que queda después del descuento: base del impoconsumo y de la propina. */
    private double calcularSubtotalConDescuento() {
        return calcularSubtotal() - calcularDescuento();
    }

    /** Impoconsumo del 8 % sobre lo que queda después del descuento. */
    public double calcularImpoconsumo() {
        return calcularSubtotalConDescuento() * PORCENTAJE_IMPOCONSUMO;
    }

    /** Propina voluntaria del 10 %, después del descuento y SIN incluir el impoconsumo. */
    public double calcularPropina() {
        if (!aceptaPropina) {
            return 0;
        }
        return calcularSubtotalConDescuento() * PORCENTAJE_PROPINA;
    }

    /** Total a pagar: subtotal - descuento + impoconsumo + propina. */
    @Override
    public double calcularPrecio() {
        return calcularSubtotalConDescuento() + calcularImpoconsumo() + calcularPropina();
    }

    /** El pedido sale cuando termina el producto MÁS demorado (la cocina trabaja en paralelo). */
    public int calcularTiempoEstimado() {
        int mayor = 0;
        for (int i = 0; i < lineas.size(); i++) {
            int minutos = lineas.get(i).getProducto().minutosPreparacion();
            if (minutos > mayor) {
                mayor = minutos;
            }
        }
        return mayor;
    }

    /** Un pedido es vegetariano solo si TODOS sus productos lo son. */
    public boolean esVegetariano() {
        for (LineaPedido linea : lineas) {
            if (!linea.getProducto().esVegetariano()) {
                return false;
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