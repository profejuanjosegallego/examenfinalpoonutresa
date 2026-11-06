# Evidencias: equipo <Ciro 1>, <Ocampo 2>, <Misas 3>

**Integrantes:** <los tres nombres completos><Veronica 1>, <Santiago 2> <Deerly 3>

## 1. Errores de sintaxis corregidos

| # | Archivo | Línea | Mensaje del compilador | Corrección |
|---|---------|-------|------------------------|------------|
| 1 | `Main.java` | 38 | `cannot find symbol: class Mesa` (error por tipo incalculable/no coincidentes) | `new Mesa("1", 2)` pasa a `new Mesa(1, 2)` (el parámetro debe ser un entero). |
| 2 | `Main.java` | 2 | `cannot find symbol: class Bebida` | Agregar `import modelo.Bebida;` en los imports iniciales. |
| 3 | `Brunch.java` | 49 | `missing return statement` | En `buscarMesa`: agregar `return null;` al final del método fuera del bucle. |
| 4 | `Brunch.java` | 64-67 | `unreachable statement` | En `abrirPedido`: poner `pedidos.add(pedido);` antes de `return pedido;`. |
| 5 | `Adicion.java` | 4 | `<identifier> expected / syntax error` | `implement` pasa a `implements` en la declaración de la clase. |
| 6 | `Bebida.java` | 23 | `')' expected` | Cerrar el paréntesis: `if (mililitros > MILILITROS_TAMANO_GRANDE) {` |
| 7 | `Bebida.java` | 31 | `minutosPreparacion() in Bebida cannot override minutosPreparacion() in ProductoMenu; attempting to use incompatible return type` | `minutosPreparacion()` pasa de retornar `double` a `int`. |
| 8 | `LineaPedido.java` | 11 | `incompatible types: ProductoMenu cannot be converted to boolean` | `if (producto = null)` pasa a `if (producto == null)`. |
| 9 | `LineaPedido.java` | 26 | `calcularPrecio() in LineaPedido cannot reduce the visibility of the method in Cobrable` | `calcularPrecio()` pasa de visibilidad de paquete a `public`. |
| 10 | `Panaderia.java` | 31 | `unclosed string literal` | `"Panadería;` pasa a `"Panadería";`. |
| 11 | `Panaderia.java` | 20 | `precioBase has private access in ProductoMenu` | `precioBase * unidades` pasa a `getPrecioBase() * unidades`. |
| 12 | `Pedido.java` | 12 | `cannot assign a value to final variable pagado` | El atributo `pagado` deja de ser `final` para permitir su modificación en `marcarPagado()`. |
| 13 | `Pedido.java` | 46 | `variable total might not have been initialized` | `double total;` pasa a `double total = 0;`. |
| 14 | `Pedido.java` | 80 | `';' expected / syntax error` | El bucle `for` usa `;` en vez de `,` (cambiar `int i = 0, i < lineas.size(), i++` por `int i = 0; i < lineas.size(); i++`). |
| 15 | `PlatoFuerte.java` | 10 | `constructor ProductoMenu in class ProductoMenu cannot be applied to given types` | `super(codigo, nombre, precioBase);` pasa a `super(codigo, nombre, precioBase, stock);` (faltaba el parámetro `stock`). |

---

## 2. Errores de lógica corregidos

| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
|---|------------------|---------------|--------------------------|--------------------|
| 1 | `ProductoMenu` | El atributo `stock` al ser `static` compartía la misma cantidad para todos los productos del menú. | `private static int stock` pasa a `private int stock`, para que cada producto tenga su propio stock | Se quitó el modificador `static` de la variable `stock` en `ProductoMenu`. |
| 2 | `ProductoMenu.setDisponible` | Asignaba el parámetro `disponible` a sí mismo en lugar de actualizar el atributo de la instancia. | `setDisponible: disponible = disponible;` pasa a `this.disponible = disponible;` | Se usó la referencia `this.disponible = disponible;`. |
| 3 | `Bebida.calcularPrecio` | Excluía las bebidas con tamaño exacto de 500 ml del recargo por tamaño grande. | `mililitros > 500` pasa a `>=` , para que 500 ml sí pague el recargo | Se cambió la condición a `mililitros >= MILILITROS_TAMANO_GRANDE`. |
| 4 | `LineaPedido.calcularPrecio` | Reemplazaba el valor acumulado de adiciones en cada iteración del bucle en lugar de sumarlo. | `sumaAdiciones = ...` pasa a `+=` , para sumar todas las adiciones | Se cambió `sumaAdiciones = a.calcularPrecio();` por `sumaAdiciones += a.calcularPrecio();`. |
| 5 | `Pedido.calcularDescuento` | Otorgaba el descuento si se cumplía *cualquiera* de las condiciones en lugar de ambas conjuntamente. | `descuento: personas >= 4 \|\| subtotal >= 150000` pasa a `&&` | Se reemplazó el operador lógico `||` por `&&` (`personas >= 4 && calcularSubtotal() >= 150000`). |
| 6 | `Pedido.calcularImpoconsumo` | Calculaba el impoconsumo directamente sobre el subtotal sin restar el descuento previo. | `impoconsumo: (subtotal - descuento) * 0.08` | Se modificó el cálculo a `(calcularSubtotal() - calcularDescuento()) * 0.08`. |
| 7 | `Pedido.calcularPropina` | Incluía erróneamente el impuesto impoconsumo en la base para el cálculo de la propina voluntaria. | `propina: (subtotal - descuento) * 0.10` , sin el impoconsumo | Se ajustó a `(calcularSubtotal() - calcularDescuento()) * 0.10`. |
| 8 | `Pedido.esVegetariano` | Retornaba `true` en la primera iteración del bucle interrumpiendo la evaluación completa del pedido. | `esVegetariano` : se quitó el `else return true` , que cortaba el ciclo en el primer producto | Se eliminó el bloque `else { return true; }` dentro del ciclo `for`. |
| 9 | `Brunch.buscarProducto` | Comparaba cadenas utilizando `==` (referencia) en lugar de comparación textual insensible a mayúsculas/minúsculas. | `buscarProducto: ==` pasa a `equalsIgnoreCase` , por eso `pan-001` ahora encuentra `PAN-001` | Se cambió `p.getCodigo() == codigo` por `p.getCodigo().equalsIgnoreCase(codigo)`. |
| 10 | `Brunch.cerrarPedido` | Marcaba el pedido como pagado pero no liberaba la mesa asociada para el siguiente uso. | `cerrarPedido: ahora llama pedido.getMesa().liberar()` | Se añadió la llamada `pedido.getMesa().liberar();` dentro del método `cerrarPedido`. |

---

## 3. Revisión de código

| # | Archivo y método | Por qué era una mala práctica | Qué cambiamos |
|---|------------------|---------------|---------------|
| R1 | `LineaPedido.obtenerEstacion` | Hacía uso excesivo e ineficiente de comprobación manual de tipos con `instanceof`, rompiendo la encapsulación y polimorfismo. | Se delegó la responsabilidad a los productos implementando un método abstracto/polimórfico `public abstract String getEstacion()` en `ProductoMenu` y sobrescribiéndolo en cada subclase (`Cocina`, `Barra`, `Horno`). |
| R2 | `Brunch.v` | Utilizaba un nombre de método críptico de una sola letra (`v`), variables intermedias innecesarias y comparación redundante `== true`. | Se refactorizó cambiando el nombre a `puedeVenderProducto` con un retorno directo: `return producto != null && producto.puedeVender(cantidad);`. |
| R3 | `Pedido.calcularTiempoEstimado` | Utilizaba un bucle tradicional indexado `for (int i = 0; ...)` en lugar de un bucle for-each idiomático de Java para colecciones. | Se simplificó utilizando un bucle for-each sobre la lista de líneas: `for (LineaPedido linea : lineas)`. |
| R4 | `Pedido` (cálculos repetidos) | Recalculaba múltiples veces `calcularSubtotal()` y `calcularDescuento()` dentro de los métodos de impoconsumo, propina y total, generando trabajo redundante. | Se refactorizaron los métodos para invocar y reutilizar los valores de los cálculos base limpios y coherentes. |
| R5 | `Brunch.abrirPedido` | Tenía múltiples puntos de salida desorganizados y mantenía impresiones de consola (`System.out.println`) mezclas con lógica del dominio. | Se extrajeron las validaciones limpiamente hacia arriba y se separó la responsabilidad de la lógica de negocio del flujo de la interfaz/consola. |

---

## 4. Salida final del programa

```text
=== 1. Carta del domingo ===
  - [PLA-001] Plato fuerte: Calentado paisa - $26000 (Stock: 8)
  - [PLA-002] Plato fuerte: Calentado con chicharrón - $32000 (Stock: 6)
  - [PLA-003] Plato fuerte: Huevos pericos con arepa - $18000 (Stock: 10)
  - [PLA-004] Plato fuerte: Arepa de chócolo con quesito - $16000 (Stock: 5)
  - [BEB-001] Bebida: Tinto de Jardín - $5000 (Stock: 30)
  - [BEB-002] Bebida: Chocolate en leche - $7000 (Stock: 20)
  - [BEB-003] Bebida: Jugo de mandarina - $11500 (Stock: 15)
  - [BEB-004] Bebida: Limonada de coco - $10000 (Stock: 15)
  - [PAN-001] Panadería: Pandequeso - $7000 (Stock: 12)
  - [PAN-002] Panadería: Buñuelo - $9000 (Stock: 5)

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
No se puede vender 6 de PAN-002.
¿Se vendió la arepa no disponible? false
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
Pedido [PED-001] mesa 3, 4 personas, 25 min, total $208169
Subtotal $173500 | Descuento $17350 | Impoconsumo $12492 | Propina $15615 | Total $208169
Pedido [PED-002] mesa 2, 4 personas, 20 min, total $125280
Subtotal $108000 | Descuento $0 | Impoconsumo $8640 | Propina $10800 | Total $125280
Pedido [PED-003] mesa 1, 2 personas, 20 min, total $62640
Subtotal $54000 | Descuento $0 | Impoconsumo $4320 | Propina $5400 | Total $62640

=== 6. Cierre del día ===
¿Se cerró la familia? true
¿Se cerró dos veces? false
¿Se cerró la pareja? true
¿La mesa 3 quedó libre? true
¿Se abrió el siguiente grupo en la mesa 3? true
Pedidos abiertos en el día: 4
Ventas del día: $270809

=== 7. Polimorfismo ===
Como Identificable: PED-001
Panaderia vale $7000
Adicion vale $3500
LineaPedido vale $79000
Pedido vale $62640
```

---

## 5. Conceptos de POO en este proyecto

- **Encapsulamiento:** 
  Se evidencia mediante la protección de los atributos de las clases declarándolos como `private` o `private final` (ej. `stock`, `disponible` en `ProductoMenu` y `lineas` en `Pedido`), controlando el acceso y modificación únicamente a través de métodos públicos como `getters`, `vender()` o `agregarLinea()`, retornando además copias de las listas internas.

- **Herencia:** 
  Se utiliza en la jerarquía de productos del menú donde `PlatoFuerte`, `Bebida` y `Panaderia` extienden de la clase base `ProductoMenu`. Estas subclases heredan los atributos comunes (`codigo`, `nombre`, `precioBase`, `stock`, `disponible`) y métodos compartidos, evitando la duplicación de código.

- **Abstracción:** 
  Se representa a través de la clase abstracta `ProductoMenu`, la cual define contratos generales como `minutosPreparacion()`, `getCategoria()` y `esVegetariano()`, abstrayendo la complejidad específica de cada tipo de producto para que el sistema opere con ellos de forma uniforme sin importar su tipo concreto.

- **Interfaz:** 
  Existen dos interfaces: `Cobrable` (implementada por `ProductoMenu`, `Adicion`, `LineaPedido` y `Pedido`) que define la capacidad de calcular un precio en pesos colombianos, e `Identificable` (implementada por `ProductoMenu` y `Pedido`) que exige la presencia de un código de rastreo y una ficha descriptiva.

- **Polimorfismo:** 
  Se aplica al tratar objetos de distintas clases de manera uniforme mediante una referencia común. En la sección 7 de `Main`, una lista `ArrayList<Cobrable>` almacena objetos de distintas clases (`Panaderia`, `Adicion`, `LineaPedido`, `Pedido`) y al invocar `c.calcularPrecio()`, cada objeto ejecuta su propia versión del cálculo. Igualmente, tras el refactor R1, `linea.obtenerEstacion()` invoca a `producto.getEstacion()`, ejecutando polimórficamente la estación correspondiente según el tipo real de producto.