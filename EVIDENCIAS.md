# Evidencias: equipo Correa, Botero

**Integrantes:** Jose David Correa Builes, Jose Manuel Botero

## 1. Errores de sintaxis corregidos
| # | Archivo | Línea | Mensaje del compilador | Corrección |
|---|---------|-------|------------------------|------------|
| 1 | Adicion.java | 4 | `'{' expected` | `implement` pasó a `implements Cobrable` |
| 2 | Bebida.java | 23 | `')' expected` | Se cerró el paréntesis del `if` |
| 3 | Panaderia.java | 31 | `unclosed string literal` | Se cerró la comilla: `"Panadería"` |
| 4 | Pedido.java | 84 | `';' expected` (junto con `> or ',' expected` y `-> expected`) | Las tres partes del `for` se separan con `;` en vez de `,` |
| 5 | Bebida.java | 31 | `minutosPreparacion() in Bebida cannot override minutosPreparacion() in ProductoMenu` (return type double no compatible con int) | El retorno pasó de `double` a `int` |
| 6 | LineaPedido.java | 12 | `incompatible types: ProductoMenu cannot be converted to boolean` | `producto = null` pasó a `producto == null` |
| 7 | LineaPedido.java | 31 | `calcularPrecio() in LineaPedido cannot implement calcularPrecio() in Cobrable; attempting to assign weaker access privileges` | El método pasó a `public` |
| 8 | Panaderia.java | 20 | `precioBase has private access in ProductoMenu` | Se usa el getter `getPrecioBase()` |
| 9 | Pedido.java | 42 (declaración en 12) | `cannot assign a value to final variable pagado` | `pagado` ya no es `final`, porque `marcarPagado()` lo cambia |
| 10 | PlatoFuerte.java | 11 | `constructor ProductoMenu in class ProductoMenu cannot be applied to given types` | `super(codigo, nombre, precioBase, stock)`: faltaba el stock |
| 11 | Main.java | 23 a 26 (cuatro mensajes, un solo error) | `cannot find symbol` (class Bebida) | Se agregó `import modelo.Bebida;` |
| 12 | Main.java | 44 | `incompatible types: String cannot be converted to int` | `new Mesa("1", 2)` pasó a `new Mesa(1, 2)` (solo sintaxis, el dato es el mismo) |
| 13 | Pedido.java | 49 | `variable total might not have been initialized` | `double total = 0;` |
| 14 | Brunch.java | 58 | `missing return statement` (en `buscarMesa`) | Se agregó `return null;` al final, como dice el comentario |
| 15 | Brunch.java | 75 | `unreachable statement` (`pedidos.add` tras `return`) | `pedidos.add(pedido)` va antes del `return pedido` |

Nota: los números 13, 14 y 15 aparecen en la última oleada de javac (el compilador los muestra solo cuando los errores anteriores ya no existen); el 11 produce 4 mensajes pero es una sola causa. En total son 15.

## 2. Errores de lógica corregidos
| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
|---|------------------|---------------|--------------------------|--------------------|
| 1 | ProductoMenu, atributo `stock` | Era `static`: todos los productos compartían el mismo stock | Sección 1, stock de la carta (todos 5 en vez de `8, 6, 10, 5...`) | Se quitó `static` |
| 2 | ProductoMenu, `setDisponible` | `disponible = disponible` se asignaba a sí mismo | Sección 2, "después de acabarse el chócolo" daba `true` en vez de `false` | `this.disponible = disponible` |
| 3 | Bebida, `calcularPrecio` | Usaba `>` 500 ml; la regla dice 500 ml o más | Sección 1, precios: el jugo de 500 ml daba `$9000` en vez de `$11500` | `>=` |
| 4 | LineaPedido, `calcularPrecio` | `sumaAdiciones = ...` reemplazaba en vez de acumular | Sección 7, `LineaPedido $79000` y sección 5, subtotal familia | `sumaAdiciones += ...` |
| 5 | Pedido, `calcularDescuento` | Usaba `\|\|` en vez de `&&` (4 o más personas Y subtotal de $150.000) | Sección 5, Amigos: descuento `$0` | Se cambió a `&&` |
| 6 | Pedido, `calcularImpoconsumo` | Calculaba el 8 % sobre el subtotal, sin restar el descuento | Sección 5, Familia: impoconsumo `$13284` | Base = `subtotal - descuento` |
| 7 | Pedido, `calcularPropina` | La base incluía el impoconsumo | Sección 5, Familia: propina `$16605` y Pareja `$7400` | Base = `subtotal - descuento` |
| 8 | Pedido, `esVegetariano` | El `else { return true; }` terminaba tras revisar solo la primera línea | Sección 4, vegetariano amigos `false` | Se eliminó el `else`; solo se devuelve `true` al final del ciclo |
| 9 | Brunch, `buscarProducto` | Comparaba con `==` (referencias) y distinguía mayúsculas | Sección 2, `Buscar pan-001` = `Pandequeso` | `equalsIgnoreCase` |
| 10 | Brunch, `cerrarPedido` | No liberaba la mesa | Sección 6, "¿Mesa 3 libre?" y "¿Abrió el siguiente grupo?" | Se agregó `pedido.getMesa().liberar()` |

## 3. Revisión de código
| # | Archivo y método | Por qué era una mala práctica | Qué cambiamos |
|---|------------------|-------------------------------|---------------|
| R1 | LineaPedido, `obtenerEstacion` | Cadena de `instanceof`: un `Postre` nuevo obligaba a editar `LineaPedido`; la línea sabía cosas que son del producto | Método abstracto `obtenerEstacion()` en ProductoMenu, implementado en PlatoFuerte (Cocina), Bebida (Barra) y Panaderia (Horno); `LineaPedido.obtenerEstacion()` delega |
| R2 | Pedido, `calcularDescuento`, `calcularImpoconsumo`, `calcularPropina` | Números mágicos (4, 150000, 0.10, 0.08) sin significado | Constantes `PERSONAS_PLAN_FAMILIAR`, `SUBTOTAL_PLAN_FAMILIAR`, `PORCENTAJE_DESCUENTO_FAMILIAR`, `PORCENTAJE_IMPOCONSUMO`, `PORCENTAJE_PROPINA` |
| R3 | Pedido, `calcularImpoconsumo` y `calcularPropina` | `subtotal - descuento` estaba repetido: si la regla cambia hay que cambiarla en dos sitios | Método privado `calcularBaseGravable()` |
| R4 | Brunch, `getMenu` | Devolvía la lista interna: desde afuera se podía vaciar o alterar la carta | Devuelve `new ArrayList<>(menu)`, igual que `Pedido.getLineas()` |
| R5 | Brunch, método privado `v` | Nombre sin significado, parámetros `p` y `c`, y `if/else` con variable `r` innecesarios | Renombrado `hayProductoVendible(producto, cantidad)` con un solo `return` |

## 4. Salida final del programa
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
- **Encapsulamiento:** los atributos son `private` (por ejemplo `stock` y `disponible` en ProductoMenu, `lineas` en Pedido) y solo se tocan con métodos como `vender()` o `agregarLinea()`, que validan. `Pedido.getLineas()` y `Brunch.getMenu()` devuelven copias para no exponer las listas internas.
- **Herencia:** PlatoFuerte, Bebida y Panaderia extienden ProductoMenu y reutilizan código, como código, nombre, stock y `vender()`. Llaman a `super(...)` y sobrescriben `calcularPrecio()` para ajustar el precio (proteína extra, tamaño grande, porción).
- **Abstracción:** ProductoMenu es abstracta: no se puede crear un "producto" genérico y obliga a las hijas a definir `minutosPreparacion()`, `getCategoria()`, `esVegetariano()` y `obtenerEstacion()`.
- **Interfaz:** `Identificable` (`getCodigo`, `obtenerFicha`) la implementan ProductoMenu (y por herencia sus 3 hijas) y Pedido. `Cobrable` (`calcularPrecio`) la implementan ProductoMenu (y sus hijas), Adicion, LineaPedido y Pedido.
- **Polimorfismo:** en la sección 7 de Main una lista de `Cobrable` contiene Panaderia, Adicion, LineaPedido y Pedido, y cada una calcula su precio a su manera. En R1, `LineaPedido.obtenerEstacion()` llama a `producto.obtenerEstacion()` y cada hija responde distinto, sin `instanceof`.
