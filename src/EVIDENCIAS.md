# Evidencias: equipo <cruz osorio>, <tique marin>

**Integrantes:** <cristian camilo cruz sosrio> <carlos duvan tique marin>

## 1. Errores de sintaxis corregidos

| # | Archivo | Línea | Mensaje del compilador | Corrección |
|---|---------|-------|------------------------|------------|
| 1 | Adicion.java | 4 | `identifier expected` (implement) | Se cambió `implement` por `implements`. |
| 2 | LineaPedido.java | 11 | `incompatible types: boolean cannot be converted to ProductoMenu` (= en vez de ==) | Se cambió `=` por `==`. |
| 3 | LineaPedido.java | 34 | `attempting to assign weaker access privileges; was public` | Se agregó la palabra reservada `public` en `calcularPrecio()`. |
| 4 | Panaderia.java | 31 | `unclosed string literal` | Se cerró la comilla doble faltante en `"Panadería"`. |
| 5 | Pedido.java | 47 | `variable total might not have been initialized` | Se inicializó la variable `double total = 0;`. |
| 6 | Pedido.java | 80 | ` ';' expected` (en bucle for) | Se cambiaron las comas `,` por puntos y comas `;` en la estructura del bucle for. |
| 7 | Pedido.java | 9 | `cannot assign a value to final variable pagado` | Se eliminó el modificador `final` del atributo `pagado`. |
| 8 | PlatoFuerte.java | 11 | `constructor ProductoMenu in class ProductoMenu cannot be applied to given types` | Se añadió el parámetro `stock` faltante en la llamada a `super(...)`. |
| 9 | Brunch.java | 43 | Incompatibilidad en comparación de Strings con `==` | Se cambió `p.getCodigo() == codigo` por `p.getCodigo().equalsIgnoreCase(codigo)`. |
| 10 | Brunch.java | 54 | `missing return statement` | Se agregó `return null;` al finalizar el método `buscarMesa()`. |
| 11 | Brunch.java | 72 | `unreachable statement` | Se reordenó la instrucción `pedidos.add(pedido);` antes del `return pedido;`. |
| 12 | ProductoMenu.java | Varios | Métodos abstractos no declarados | Se declararon los métodos abstractos `minutosPreparacion()`, `getCategoria()` y `esVegetariano()`. |
| 13 | Mesa.java | Varios | Sobrecarga de constructores para número de mesa | Se implementaron los constructores para recibir tanto `int` como `String`. |
| 14 | Pedido.java | Varios | Método `marcarPagado()` bloqueado | Se ajustó el estado de `pagado` para permitir la transición al cobrar el pedido. |
| 15 | Brunch.java | Varios | Validación de stock y disponibilidad | Se aseguraron las validaciones de control de inventario en `vender()` y `puedeVender()`. |

## 2. Errores de lógica corregidos
| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
|---|------------------|---------------|--------------------------|--------------------|
| 1 | `Bebida.calcularPrecio()` | Usaba un operador estricto `>` en vez de `>=` para el tamaño grande de 500 ml, ignorando el recargo del Jugo de mandarina. | Sección 1 (Precios de la carta) | Se cambió a `mililitros >= MILILITROS_TAMANO_GRANDE`. |
| 2 | `ProductoMenu` (atributo stock) | El atributo stock era `static`, lo que hacía que todas las instancias compartieran un inventario global en lugar de ser independiente por producto. | Sección 1 (Stock de la carta) | Se eliminó el modificador `static` de la variable `stock`. |
| 3 | `ProductoMenu.puedeVender()` | Validaba el stock con un operador incorrecto, impidiendo el despacho correcto de las unidades disponibles exactas. | Sección 2 (Disponibilidad y arepa) | Se ajustó la condición a `stock >= cantidad`. |
| 4 | `ProductoMenu.setDisponible()` | Tenía un error de asignación interna (`disponible = disponible;`), por lo que nunca cambiaba el estado del producto. | Sección 2 (Arepa no disponible) | Se corrigió asignando correctamente con `this.disponible = disponible;`. |
| 5 | `LineaPedido.calcularPrecio()` | Si bien en este caso se validó el acumulador, el diseño original requería asegurar que cada unidad multiplicara el precio base más sus adiciones. | Sección 7 (Polimorfismo / Línea de pedido) | Se mantuvo la operación correcta `(producto.calcularPrecio() + sumaAdiciones) * cantidad`. |
| 6 | `Pedido.calcularDescuento()` | Aplicaba el plan familiar utilizando un operador O (`||`) en lugar de exigir ambas condiciones simultáneamente con Y (`&&`). | Sección 5 (Cuentas - Familia) | Se cambió `||` por `&&` (`personas >= 4 && subtotal >= 150000`). |
| 7 | `Pedido.calcularImpoconsumo()` | Calculaba el 8% sobre el subtotal bruto total sin restarle primero el descuento familiar obtenido. | Sección 5 (Cuentas - Familia) | Se aplicó la base restando el descuento previo: `(subtotal - descuento) * 0.08`. |
| 8 | `Pedido.calcularPropina()` | Incluía erróneamente el valor del impoconsumo dentro de la base de cálculo para la propina voluntaria. | Sección 5 (Cuentas - Familia) | Se calculó la propina exclusivamente sobre el subtotal menos el descuento, sin sumar el impoconsumo. |
| 9 | `Pedido.esVegetariano()` | Retornaba `true` prematuramente tras evaluar únicamente el primer elemento de la lista sin revisar los demás. | Sección 4 (Pedidos - Vegetariano) | Se estructuró el bucle para que retorne `false` al encontrar un solo producto no vegetariano, y `true` solo si todos lo cumplen. |
| 10 | `Brunch.abrirPedido()` y `cerrarPedido()` | No validaba adecuadamente si la mesa seleccionada estaba ocupada, y al cobrar olvidaba liberar la mesa para el siguiente grupo. | Sección 6 (Cierre - Mesa libre) | Se integraron las validaciones de `mesa.isOcupada()`, `mesa.ocupar()` y `mesa.liberar()`. |

## 3. Revisión de código
| # | Archivo y método | Por qué era una mala práctica | Qué cambiamos |
|---|------------------|-----------------------------|---------------|
| R1 | `LineaPedido.obtenerEstacion()` | Polimorfismo desaprovechado con `instanceof`, violando OCP ante nuevas clases. | Se implementó el método abstracto `obtenerEstacion()` en `ProductoMenu` y se sobrescribió polimórficamente en cada clase hija. |
| R2 | `Pedido` (Cálculos financieros) | Uso de números mágicos (valores literales sueltos sin contexto como `0.10` o `150000`). | Se declararon constantes estáticas descriptivas (`TASA_DESCUENTO`, `TASA_IMPOCONSUMO`, etc.). |
| R3 | `Pedido` (Métodos de cálculo) | Código duplicado al invocar de forma repetida y redundante la función `calcularSubtotal()`. | Se almacenó el resultado en una variable local al inicio de cada método de cálculo para reutilizarlo. |
| R4 | `Brunch.getMenu()` | Lista interna expuesta que rompía el encapsulamiento al retornar la colección directamente. | Se modificó para retornar una copia defensiva (`new ArrayList<>(menu)`). |
| R5 | `Brunch` (Método privado auxiliar) | Código difícil de leer debido a un método auxiliar con un nombre críptico y de una sola letra (`v()`). | Se refactorizó cambiando su nombre por uno semántico y claro (`validarDisponibilidad()`). |

## 4. Salida final del programa
=== 1. Carta del domingo ===
  - PlatoFuerte [PLA-001] Calentado paisa - $26000 (20 min) [Stock: 8]
  - PlatoFuerte [PLA-002] Calentado con chicharrón - $32000 (25 min) [Stock: 6]
  - PlatoFuerte [PLA-003] Huevos pericos con arepa - $18000 (28 min) [Stock: 10]
  - PlatoFuerte [PLA-004] Arepa de chócolo con quesito - $16000 (28 min) [Stock: 5]
  - Bebida [BEB-001] Tinto de Jardín - $5000 (4 min) [Stock: 30]
  - Bebida [BEB-002] Chocolate en leche - $7000 (4 min) [Stock: 20]
  - Bebida [BEB-003] Jugo de mandarina - $11500 (6 min) [Stock: 15]
  - Bebida [BEB-004] Limonada de coco - $18000 (6 min) [Stock: 15]
  - Panaderia [PAN-001] Pandequeso - $7000 (12 min) [Stock: 12]
  - Panaderia [PAN-002] Buñuelo - $9000 (3 min) [Stock: 5]

=== 2. Búsquedas y disponibilidad ===
Buscar pan-001: Pandequeso
Buscar XXX-999: no encontrado
¿Se puede vender 1 arepa? true
Se acabó el chócolo. ¿Se puede vender 1 arepa? false

=== 3. Mesas ===
¿La mesa 3 recibe a 6 personas? true
¿La mesa 3 recibe a 7 personas? false

=== 4. Pedidos ===
¿Se vendió la arepa no disponible? false
¿Se vendieron 6 buñuelos? false
¿Se vendieron 2 buñuelos? true
¿Se abrió el pedido de la mesa 9? false
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
Subtotal $184500 | Descuento $18450 | Impoconsumo $13284 | Propina $16685 | Total $195939
Pedido [PED-002] mesa 2, 4 personas, 28 min, total $116640
Subtotal $108000 | Descuento $0 | Impoconsumo $8640 | Propina $0 | Total $116640
Pedido [PED-003] mesa 1, 2 personas, 28 min, total $87320
Subtotal $74000 | Descuento $0 | Impoconsumo $5920 | Propina $7480 | Total $87320

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

## 5. Concepts de POO en este proyecto
En 2 o 3 líneas cada uno, expliquen DÓNDE se usa en este código:
- **Encapsulamiento:** Se aplica ocultando los atributos (como `stock`, `precio`, `lineas`) mediante modificadores `private`, exponiéndolos de forma segura con métodos `get` y `set`, y protegiendo las colecciones internas con copias defensivas (`new ArrayList<>(menu)`).
- **Herencia:** Se utiliza en la jerarquía de `ProductoMenu`, donde las clases hijas (`PlatoFuerte`, `Bebida`, `Panaderia`) heredan los atributos y comportamientos base, evitando la duplicación de código.
- **Abstracción:** Se evidencia en la clase `ProductoMenu` al definir la estructura general de los productos del menú y declarar el método abstracto `obtenerEstacion()`, obligando a las clases hijas a definir su propia lógica.
- **Interfaz:** Se usan dos interfaces: `Identificable` (implementada por `Pedido` para gestionar códigos únicos) y `Cobrable` (implementada por `ProductoMenu`, `LineaPedido`, `Adicion` y `Pedido` para estandarizar el cálculo de precios).
- **Polimorfismo:** Se aplica en `LineaPedido.obtenerEstacion()` al delegar la estación en el producto mediante métodos sobrescritos, y en la sección 7 del Main al tratar objetos de diferentes clases de manera uniforme a través de la interfaz `Cobrable`.