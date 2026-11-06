# Evidencias: equipo Valencia, Valencia, Padilla

**Integrantes:** José David Valencia Martínez, Juan Camilo Valencia Estrada, Santiago Padilla Valencia

## 1. Errores de sintaxis corregidos

| #   | Archivo                 | Línea | Mensaje del compilador                                                        | Corrección                                                                    |
| --- | ----------------------- | ----- | ----------------------------------------------------------------------------- | ----------------------------------------------------------------------------- |
| 1   | `modelo/Adicion.java`   | 4     | `'{' expected`                                                                | `implement` → `implements Cobrable`                                           |
| 2   | `modelo/Bebida.java`    | 23    | `')' expected`                                                                | Se cerró el paréntesis del `if (mililitros ... )`                             |
| 3   | `modelo/Panaderia.java` | 31    | `unclosed string literal`                                                     | Se cerró la comilla: `"Panadería"`                                            |
| 4   | `modelo/Pedido.java`    | 84    | `';' expected`                                                                | El `for` separaba sus partes con `,`; se usan `;`                             |
| 5   | `modelo/Bebida.java`    | 31    | `minutosPreparacion() in Bebida cannot override ...` (return type)            | El tipo de retorno pasó de `double` a `int`, como en `ProductoMenu`           |
| 6   | `modelo/LineaPedido.java` | 31  | `calcularPrecio() in LineaPedido cannot implement ...` (weaker access)        | Se agregó `public` al método que implementa `Cobrable`                        |
| 7   | `modelo/LineaPedido.java` | 12  | `incompatible types: ProductoMenu cannot be converted to boolean`             | `producto = null` → `producto == null`                                        |
| 8   | `modelo/Panaderia.java` | 20    | `precioBase has private access in ProductoMenu`                               | Se usa el getter `getPrecioBase()`                                            |
| 9   | `modelo/Pedido.java`    | 42    | `cannot assign a value to final variable pagado`                              | `pagado` deja de ser `final`: debe cambiar al cerrar la cuenta               |
| 10  | `modelo/PlatoFuerte.java` | 11  | `constructor ProductoMenu in class ProductoMenu cannot be applied to given types` | `super(codigo, nombre, precioBase, stock)`: faltaba el `stock`           |
| 11  | `Main.java`             | 23    | `cannot find symbol` (class `Bebida`)                                         | Faltaba `import modelo.Bebida;`                                               |
| 12  | `Main.java`             | 44    | `incompatible types: String cannot be converted to int`                       | `new Mesa("1", 2)` → `new Mesa(1, 2)`                                         |
| 13  | `modelo/Pedido.java`    | 47    | `variable total might not have been initialized`                              | `double total = 0;`                                                           |
| 14  | `servicio/Brunch.java`  | 58    | `missing return statement`                                                    | `buscarMesa` retorna `null` si no encuentra la mesa (como dice su comentario) |
| 15  | `servicio/Brunch.java`  | 75    | `unreachable statement`                                                       | En `abrirPedido`, `pedidos.add(pedido)` va **antes** del `return`             |

## 2. Errores de lógica corregidos

| #   | Archivo y método                         | Qué hacía mal                                                                                       | Pista (fila de la tabla)                                         | Cómo lo corregimos                                       |
| --- | ---------------------------------------- | --------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | -------------------------------------------------------- |
| 1   | `ProductoMenu` (atributo `stock`)        | `stock` era `static`: todos los productos compartían el mismo stock (el último creado, 5)          | Sección 1, stock de la carta (todos salían en 5)                 | Se quitó `static`: cada producto tiene su propio stock   |
| 2   | `ProductoMenu.setDisponible`             | `disponible = disponible` se asignaba el parámetro a sí mismo; el atributo nunca cambiaba          | Sección 2, arepa después de acabarse el chócolo (`true`)         | `this.disponible = disponible`                           |
| 3   | `Bebida.calcularPrecio`                  | Usaba `>` 500 ml; una bebida de exactamente 500 ml no pagaba el recargo                            | Sección 1, jugo de mandarina `$9000` en vez de `$11500`          | `mililitros >= MILILITROS_TAMANO_GRANDE`                 |
| 4   | `LineaPedido.calcularPrecio`             | `sumaAdiciones = ...` reemplazaba en vez de acumular: solo contaba la última adición               | Sección 7, `LineaPedido $71000` en vez de `$79000`               | `sumaAdiciones += a.calcularPrecio()`                    |
| 5   | `Pedido.calcularDescuento`               | Usaba `\|\|`: daba el plan familiar con solo 4 personas o solo con el subtotal                      | Sección 5, descuento de amigos (4 personas, $108000) no era $0   | `personas >= 4 && subtotal >= 150000`                    |
| 6   | `Pedido.calcularImpoconsumo`             | Calculaba el 8 % sobre el subtotal, sin restar el descuento                                        | Sección 5, impoconsumo de la familia (`$14760` en vez de `$13284`) | 8 % sobre `subtotal - descuento`                       |
| 7   | `Pedido.calcularPropina`                 | Incluía el impoconsumo en la base de la propina                                                    | Sección 5, propina de familia/pareja                             | 10 % sobre `subtotal - descuento`, sin impoconsumo       |
| 8   | `Pedido.esVegetariano`                   | El `else return true` dentro del `for` decidía solo con el primer producto                         | Sección 4, ¿vegetariano? amigos (`true` en vez de `false`)       | Se quitó el `else`; solo retorna `true` al final del ciclo |
| 9   | `Brunch.buscarProducto`                  | Comparaba `String` con `==` (referencias) y distinguía mayúsculas                                  | Sección 2, `Buscar pan-001` → `no encontrado`                    | `p.getCodigo().equalsIgnoreCase(codigo)`                 |
| 10  | `Brunch.cerrarPedido`                    | Marcaba el pedido como pagado pero no liberaba la mesa                                             | Sección 6, ¿mesa 3 libre? `false` / ¿abrió el siguiente grupo? `false` | Se agregó `pedido.getMesa().liberar()`             |

## 3. Revisión de código

| #   | Archivo y método                                         | Por qué era una mala práctica                                                                                                               | Qué cambiamos                                                                                                                                                         |
| --- | -------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| R1  | `LineaPedido.obtenerEstacion`                            | Cadena de `instanceof`: para agregar `Postre` habría que tocar `LineaPedido`. La estación es un dato que le corresponde a cada producto. | Nuevo método abstracto `getEstacion()` en `ProductoMenu`, implementado en `PlatoFuerte` ("Cocina"), `Bebida` ("Barra") y `Panaderia` ("Horno"). `obtenerEstacion()` delega en `producto.getEstacion()`. |
| R2  | `Pedido.calcularDescuento / calcularImpoconsumo / calcularPropina` | Números mágicos (`4`, `150000`, `0.10`, `0.08`): no se sabe qué significan y hay que buscarlos para cambiar una regla.            | Constantes `PERSONAS_PLAN_FAMILIAR`, `SUBTOTAL_MINIMO_PLAN_FAMILIAR`, `PORCENTAJE_PLAN_FAMILIAR`, `PORCENTAJE_IMPOCONSUMO`, `PORCENTAJE_PROPINA`, como en `Bebida` y `PlatoFuerte`. |
| R3  | `Pedido.calcularImpoconsumo / calcularPropina / calcularPrecio` | `calcularSubtotal() - calcularDescuento()` se repetía en tres métodos; si cambia la base hay que acordarse de cambiarla en todos.    | Método privado `calcularSubtotalConDescuento()` usado por los tres. Además `calcularDescuento` calcula el subtotal una sola vez.                                        |
| R4  | `Brunch.getMenu`                                         | Retornaba la lista interna: desde afuera se podía hacer `getMenu().clear()` o agregar productos sin pasar por `agregarAlMenu`.           | Retorna una copia `new ArrayList<>(menu)`, igual que `Pedido.getLineas()`.                                                                                             |
| R5  | `Brunch.v`                                               | Nombre y parámetros de una letra (`v`, `p`, `c`, `r`), `== true` innecesario y un `if/else` para asignar un booleano.                    | Se renombró a `sePuedeVender(ProductoMenu producto, int cantidad)` y queda en una línea: `return producto != null && producto.puedeVender(cantidad);`                  |

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

- **Encapsulamiento:** Todos los atributos son `private` y se acceden por getters; los setters de `ProductoMenu` validan nombre y precio, y el stock solo cambia por `vender()`, que primero revisa `puedeVender()`. `Pedido.getLineas()` y `Brunch.getMenu()` retornan copias para proteger las listas internas.
- **Herencia:** `PlatoFuerte`, `Bebida` y `Panaderia` heredan de `ProductoMenu` código, nombre, precio base, stock y la lógica de venta; llaman a `super(...)` en el constructor y reutilizan `super.calcularPrecio()` para sumar sus recargos.
- **Abstracción:** `ProductoMenu` es abstracta: define qué debe saber todo producto (`minutosPreparacion()`, `getCategoria()`, `esVegetariano()`, `getEstacion()`) sin decir cómo, y no se puede instanciar directamente.
- **Interfaz:** `Identificable` (`getCodigo`, `obtenerFicha`) la implementan `ProductoMenu` (y por herencia sus tres hijas) y `Pedido`. `Cobrable` (`calcularPrecio`) la implementan `ProductoMenu` (y sus hijas), `Adicion`, `LineaPedido` y `Pedido`.
- **Polimorfismo:** En la sección 7 de `Main` una `ArrayList<Cobrable>` guarda objetos de clases distintas y cada uno responde `calcularPrecio()` a su manera. En R1, `LineaPedido.obtenerEstacion()` llama a `producto.getEstacion()` y Java ejecuta la versión de la clase real del producto, sin `instanceof`.
