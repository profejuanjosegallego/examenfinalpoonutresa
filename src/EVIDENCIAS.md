# Evidencias: 
 
**Integrantes:** Hincapié Usuga, Pérez Higuita, Zambrano Colón
 
## 1. Errores de sintaxis corregidos
 
| # | Archivo | Línea | Mensaje del compilador | Corrección |
|---|---------|-------|------------------------|------------|
| 1 | `Main.java` | 23 | `cannot find symbol: class Bebida` | Se agregó `import modelo.Bebida;` |
| 2 | `Main.java` | 44 | `incompatible types: String cannot be converted to int` | `new Mesa("1", 2)` → `new Mesa(1, 2)` |
| 3 | `servicio/Brunch.java` | 58 | `missing return statement` | Se agregó `return null;` al final de `buscarMesa`, como dice su comentario |
| 4 | `servicio/Brunch.java` | 75 | `unreachable statement` | Se movió `pedidos.add(pedido);` antes de `return pedido;` |
| 5 | `modelo/Adicion.java` | 4 | `'{' expected` | `implement Cobrable` → `implements Cobrable` |
| 6 | `modelo/Bebida.java` | 23 | `')' expected` | Se cerró el paréntesis del `if`: `if (mililitros > MILILITROS_TAMANO_GRANDE) {` |
| 7 | `modelo/Bebida.java` | 31 | `minutosPreparacion() in Bebida cannot override minutosPreparacion() in ProductoMenu; return type double is not compatible with int` | `public double minutosPreparacion()` → `public int minutosPreparacion()` |
| 8 | `modelo/LineaPedido.java` | 12 | `incompatible types: ProductoMenu cannot be converted to boolean` | `if (producto = null)` → `if (producto == null)` |
| 9 | `modelo/LineaPedido.java` | 31 | `calcularPrecio() in LineaPedido cannot implement calcularPrecio() in Cobrable; attempting to assign weaker access privileges; was public` | `double calcularPrecio()` → `public double calcularPrecio()` |
| 10 | `modelo/Panaderia.java` | 31 | `unclosed string literal` | `"Panadería;` → `"Panadería";` |
| 11 | `modelo/Panaderia.java` | 20 | `precioBase has private access in ProductoMenu` | `precioBase * unidades` → `getPrecioBase() * unidades` (sin volver público el atributo) |
| 12 | `modelo/Pedido.java` | 42 | `cannot assign a value to final variable pagado` | `private final boolean pagado;` → `private boolean pagado;` |
| 13 | `modelo/Pedido.java` | 49 | `variable total might not have been initialized` | `double total;` → `double total = 0;` |
| 14 | `modelo/Pedido.java` | 84 | `';' expected` | `for (int i = 0, i < lineas.size(), i++)` → `for (int i = 0; i < lineas.size(); i++)` |
| 15 | `modelo/PlatoFuerte.java` | 11 | `constructor ProductoMenu in class ProductoMenu cannot be applied to given types` | `super(codigo, nombre, precioBase);` → `super(codigo, nombre, precioBase, stock);` |