package modelo;

public class Mesa {
    private final String numero;
    private final int capacidad;
    private boolean ocupada;

    public Mesa(String numero, int capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.ocupada = false;
    }

    public String getNumero() { return numero; }
    public int getCapacidad() { return capacidad; }
    public boolean isOcupada() { return ocupada; }

    /** Recibe al grupo si la mesa está libre y las personas caben. */
    public boolean puedeRecibir(int personas) {
        return !ocupada && personas > 0 && personas <= capacidad;
    }

    public void ocupar() {
        ocupada = true;
    }

    public void liberar() {
        ocupada = false;
    }
}
