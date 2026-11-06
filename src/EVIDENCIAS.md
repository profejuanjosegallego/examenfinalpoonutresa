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

## 2. Errores de lógica corregidos

| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
|---|------------------|---------------|--------------------------|--------------------|
| 1 | `ProductoMenu` · atributo `stock` | Era `static`: un solo stock compartido por **todos** los productos, así que cada `new` lo sobrescribía y todos mostraban `stock 5` (el último creado). Por eso casi nada se podía vender | Sección 1, stock (`8, 6, 10…`); sección 4, `Quedan de chocolate 16` | `private static int stock;` → `private int stock;` (cada objeto tiene el suyo) |
| 2 | `ProductoMenu.setDisponible()` | `disponible = disponible;` asigna el parámetro a sí mismo: el atributo nunca cambiaba | Sección 2, arepa después del chócolo debe dar `false` | `this.disponible = disponible;` |
| 3 | `Bebida.calcularPrecio()` | Usaba `>` y el comentario dice «500 ml **o más**»: el jugo de 500 ml no cobraba el recargo | Sección 1, jugo de mandarina `$11500` | `mililitros > …` → `mililitros >= MILILITROS_TAMANO_GRANDE` |
| 4 | `LineaPedido.calcularPrecio()` | `sumaAdiciones = …` reemplazaba en vez de acumular: solo contaba la última adición | Sección 7, `LineaPedido $79000`; subtotal familia `$184500` | `sumaAdiciones += a.calcularPrecio();` |
| 5 | `Pedido.calcularDescuento()` | Usaba `||` y la regla dice «4 o más personas **Y** subtotal ≥ $150.000»: los amigos (4 personas, $108.000) recibían descuento | Sección 5, amigos descuento `$0` | `||` → `&&` |
| 6 | `Pedido.calcularImpoconsumo()` | Calculaba el 8 % sobre el subtotal, no sobre lo que queda después del descuento | Sección 5, familia impoconsumo `$13284` | `(calcularSubtotal() - calcularDescuento()) * 0.08` |
| 7 | `Pedido.calcularPropina()` | Sumaba el impoconsumo a la base, y la regla dice «SIN incluir el impoconsumo» | Sección 5, familia propina `$16605`, pareja `$7400` | `(calcularSubtotal() - calcularDescuento()) * 0.10` |
| 8 | `Pedido.esVegetariano()` | El `else { return true; }` salía del ciclo en la **primera** línea: solo revisaba el primer producto | Sección 4, amigos `false` (el primer producto, huevos pericos, sí es vegetariano) | Se quitó el `else`; el `return true` queda solo después del `for` |
| 9 | `Brunch.buscarProducto()` | Comparaba `String` con `==` (compara referencias, no contenido) y además debía ignorar mayúsculas | Sección 2, `Buscar pan-001: Pandequeso` | `p.getCodigo().equalsIgnoreCase(codigo)` |
| 10 | `Brunch.cerrarPedido()` | Marcaba el pedido como pagado pero no liberaba la mesa, como pide el comentario | Sección 6, `¿Mesa 3 libre? true` y `¿Abrió el siguiente grupo? true` | Se agregó `pedido.getMesa().liberar();` |

## 3. Revisión de código

| # | Archivo y método | Por qué era una mala práctica | Qué cambiamos |
|---|------------------|-------------------------------|---------------|
| R1 | `LineaPedido.obtenerEstacion()` | Polimorfismo desaprovechado: una cadena de `instanceof` decidía la estación. Si mañana llega `Postre`, habría que abrir `LineaPedido` y agregar otro `else if`; si se olvida, sale «Sin estación» sin ningún aviso. La estación es un dato del producto, no de la línea | Se agregó `public abstract String getEstacion();` en `ProductoMenu`, implementado en `PlatoFuerte` («Cocina»), `Bebida` («Barra») y `Panaderia` («Horno»). `obtenerEstacion()` sigue existiendo y ahora solo hace `return producto.getEstacion();`. Una clase nueva no compila si no dice su estación |
| R2 | `Pedido.calcularDescuento()`, `calcularImpoconsumo()`, `calcularPropina()` | Números mágicos: `4`, `150000`, `0.10`, `0.08` y otro `0.10` no dicen qué significan, y los dos `0.10` (descuento y propina) parecen lo mismo sin serlo | Constantes con nombre, como en `Bebida` y `PlatoFuerte`: `PERSONAS_PLAN_FAMILIAR`, `SUBTOTAL_MINIMO_PLAN_FAMILIAR`, `PORCENTAJE_DESCUENTO_FAMILIAR`, `PORCENTAJE_IMPOCONSUMO`, `PORCENTAJE_PROPINA` |
| R3 | `Pedido`: impoconsumo, propina, total y descuento | Código duplicado: `calcularSubtotal() - calcularDescuento()` se repetía en tres métodos, y `calcularDescuento()` llamaba dos veces a `calcularSubtotal()`. Si la regla de la base cambia, hay que acordarse de cambiarla en tres sitios | Método privado `calcularBaseConDescuento()`, usado por impoconsumo, propina y total. En el descuento, el subtotal se calcula una vez y se guarda en una variable local |
| R4 | `Brunch.getMenu()` | Lista interna expuesta: retornaba la misma `ArrayList`, así que desde afuera se podía hacer `getMenu().clear()` o agregar productos sin pasar por `agregarAlMenu` (que valida `null`) | Retorna `new ArrayList<>(menu)`, igual que `Pedido.getLineas()`. La firma pública no cambió |
| R5 | `Brunch.v(ProductoMenu p, int c)` | Código difícil de leer: el nombre `v` y los parámetros `p`, `c` no dicen nada; además, `== true` y la variable `r` con `if/else` sobran para retornar un `boolean` | Se renombró a `sePuedeVender(ProductoMenu producto, int cantidad)` con cuerpo de una línea: `return producto != null && producto.puedeVender(cantidad);` |

## 4. Salida final del programa

Salida final (`java -cp out Main`). Es la misma en la fase 2 y en la fase 3, y coincide fila por fila con la tabla de resultados esperados:

```text
=== 1. Carta del domingo ===
  - Plato fuerte [PLA-001] Calentado paisa - $26000, 20 min, stock 8
  - Plato fuerte [PLA-002] Calentado con chicharrón - $32000, 25 min, stock 6
  - Plato fuerte [PLA-003] Huevos pericos con arepa - $18000, 20 min, stock 10
  - Plato fuerte [PLA-004] Arepa de chócolo con quesito - $16000, 20 min, stock 5
  - Bebida [BEB-001] Tinto de Jardín - $5000, 4 min, stock 30
  - Bebida [BEB-002] Chocolate en leche - $7000, 4 min, stock 20
  - Bebida [BEB-003] Jugo de mandarina - $11500, 6 min, stock 15
  - Bebida [BEB-004] Limonada de coco - $10000, 6 min, stock 15
  - Panadería [PAN-001] Pandequeso - $7000, 12 min, stock 12
  - Panadería [PAN-002] Buñuelo - $9000, 3 min, stock 5

=== 2. Búsquedas y disponibilidad ===
Buscar pan-001: Pandequeso
Buscar XXX-999: no encontrado
¿Se puede vender 1 arepa? true
Se acabó el chócolo. ¿Se puede vender 1 arepa? false

=== 3. Mesas ===
¿La mesa 3 recibe a 6 personas? true
¿La mesa 3 recibe a 7 personas? false

=== 4. Pedidos ===
No se puede vender 1 de PLA-004.
¿Se vendió la arepa no disponible? false
No se puede vender 6 de PAN-002.
¿Se vendieron 6 buñuelos? false
¿Se vendieron 2 buñuelos? true
La mesa 9 no existe.
¿Se abrió el pedido de la mesa 9? false
La mesa 3 no puede recibir a 2 personas.
¿Se abrió otro pedido en la mesa 3? false
Comanda de la familia:
  - 2 x Calentado con chicharrón + Aguacate + Quesito -> Cocina
  - 2 x Huevos pericos con arepa -> Cocina
  - 4 x Chocolate en leche -> Barra
  - 3 x Jugo de mandarina -> Barra
  - 1 x Pandequeso -> Horno
Quedan de chocolate: 16
Quedan de buñuelo: 3
Pedido de amigos, ¿vegetariano? false
Pedido de pareja, ¿vegetariano? true

=== 5. Cuentas ===
Pedido [PED-001] mesa 3, 4 personas, 25 min, total $195939
Subtotal $184500 | Descuento $18450 | Impoconsumo $13284 | Propina $16605 | Total $195939
Pedido [PED-002] mesa 2, 4 personas, 20 min, total $116640
Subtotal $108000 | Descuento $0 | Impoconsumo $8640 | Propina $0 | Total $116640
Pedido [PED-003] mesa 1, 2 personas, 20 min, total $87320
Subtotal $74000 | Descuento $0 | Impoconsumo $5920 | Propina $7400 | Total $87320

=== 6. Cierre del día ===
¿Se cerró la familia? true
¿Se cerró dos veces? false
¿Se cerró la pareja? true
¿La mesa 3 quedó libre? true
¿Se abrió el siguiente grupo en la mesa 3? true
Pedidos abiertos en el día: 4
Ventas del día: $283259

=== 7. Polimorfismo ===
Como Identificable: PED-001
Panaderia vale $7000
Adicion vale $3500
LineaPedido vale $79000
Pedido vale $87320
```

## 5. Conceptos de POO en este proyecto

- **Encapsulamiento:**
- **Herencia:**
- **Abstracción:**
- **Interfaz:**
- **Polimorfismo:**