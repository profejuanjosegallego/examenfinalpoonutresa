# Evidencias: equipo <acevedo yepes 1>, <isaza vasquez 2>

**Integrantes:** Daniel Acevedo Yepes - Juan Felipe Isaza Vasquez
## 1. Errores de sintaxis corregidos
| # | Archivo | Línea | Mensaje del compilador | Corrección |
|---|---------|-------|------------------------|------------|
| 1 | `Adicion.java` | 4 | `'{' expected` | Se corrigió `implement` por `implements` en la declaración de la clase. |
| 2 | `Bebida.java` | 23 | `')' expected` | Se agregó el paréntesis de cierre en la condición del `if`: `if (mililitros > MILILITROS_TAMANO_GRANDE) {`. |
| 3 | `Panaderia.java` | 31 | `unclosed string literal` | Se cerraron las comillas en el texto retornado: `return "Panadería";`. |
| 4 | `Bebida.java` | (línea de `minutosPreparacion`) | `return type double is not compatible with int` | Se cambió el tipo de retorno de `double` a `int` en `minutosPreparacion()`. |
| 5 | `Panaderia.java` | (línea de `calcularPrecio`) | `precioBase has private access in ProductoMenu` | Se reemplazó el acceso directo a `precioBase` por el getter `getPrecioBase()`. |
| 6 | `Pedido.java` | 84 | `';' expected` | Se cambiaron las comas por punto y coma en el ciclo `for`: `for (int i = 0; i < lineas.size(); i++)`. |
| 7 | `Pedido.java` | (atributo `pagado` / `marcarPagado`) | `cannot assign a value to final variable pagado` | Se quitó el modificador `final` del atributo `private boolean pagado;` para permitir cambiar su valor en `marcarPagado()`. |
| 8 | `Pedido.java` | 47 | `variable total might not have been initialized` | Se inicializó la variable local en cero: `double total = 0;`. |
| 9 | `LineaPedido.java` | 12 | `incompatible types: ProductoMenu cannot be converted to boolean` | Se cambió la asignación `=` por comparación `==`: `if (producto == null) {`. |
| 10 | `LineaPedido.java` | 31 | `attempting to assign weaker access privileges; was public` | Se agregó el modificador `public` a `public double calcularPrecio()`. |
| 11 | `Pedido.java` | 58 | `missing return statement` | Se agregó `return 0;` al final de `calcularDescuento()`. |
| 12 | `PlatoFuerte.java` | (constructor) | `constructor ProductoMenu in class ProductoMenu cannot be applied to given types` | Se agregó el argumento `stock` a la llamada `super(codigo, nombre, precioBase, stock);`. |
| 13 | `Brunch.java` | 58 | `missing return statement` | Se agregó `return null;` al final del método `buscarMesa(int numero)`. |
| 14 | `Brunch.java` | 76 | `unreachable statement` | Se movió `pedidos.add(pedido);` antes de `return pedido;` en `abrirPedido()`. |
| 15 | `Main.java` | 2 / 44 | `cannot find symbol: class Bebida` / `incompatible types: String cannot be converted to int` | Se agregó `import modelo.Bebida;` y se quitaron las comillas en `new Mesa(1, 2)`. |

## 2. Errores de lógica corregidos
| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
|---|------------------|---------------|--------------------------|--------------------|
| 1 | `Bebida.java` — `calcularPrecio()` | Usaba `>` en vez de `>=`, por lo que una bebida de exactamente 500 ml no sumaba el recargo de $2.500. | Sección 1: Precios de la carta (`$11500` para el jugo de mandarina). | Se cambió la condición a `if (mililitros >= MILILITROS_TAMANO_GRANDE)`. |
| 2 | `LineaPedido.java` — `calcularPrecio()` | Sobrescribía `sumaAdiciones` con `=` en lugar de acumular con `+=` en el ciclo `for`. | Sección 5 (totales de pedidos) y Sección 7 (`LineaPedido $79000`). | Se cambió por `sumaAdiciones += a.calcularPrecio();`. |
| 3 | `Pedido.java` — `calcularDescuento()` | Usaba `\|\|` en lugar de `&&`, aplicando el 10 % de descuento con solo cumplir una de las dos condiciones. | Sección 5: Amigos (4 personas y subtotal `$108000`, descuento `$0`). | Se cambió la condición a `if (personas >= 4 && calcularSubtotal() >= 150000)`. |
| 4 | `Brunch.java` — `buscarProducto(String)` | Comparaba los códigos con `==` y distinguía entre mayúsculas y minúsculas. | Sección 2: `Buscar pan-001` debe retornar `Pandequeso`. | Se reemplazó `p.getCodigo() == codigo` por `p.getCodigo().equalsIgnoreCase(codigo)`. |
| 5 | `ProductoMenu.java` — atributo `stock` | El atributo era `static`, haciendo que todos los productos compartieran el mismo stock. | Sección 1: Stock de la carta (`8, 6, 10, 5, 30, 20, 15, 15, 12, 5`). | Se eliminó `static` de la declaración: `private int stock;`. |
| 6 | `ProductoMenu.java` — `setDisponible(boolean)` | Asignaba el parámetro a sí mismo (`disponible = disponible;`) sin modificar el atributo. | Sección 2 (`¿Se puede vender 1 arepa? true / false`) y Sección 4 (`false`). | Se usó `this.disponible = disponible;`. |
| 7 | `Pedido.java` — `calcularImpoconsumo()` | Calculaba el 8 % sobre el subtotal sin restar antes el descuento. | Sección 5: Familia (impoconsumo `$13284`). | Se cambió a `return (calcularSubtotal() - calcularDescuento()) * 0.08;`. |
| 8 | `Pedido.java` — `calcularPropina()` | Sumaba el impoconsumo a la base sobre la que se calcula el 10 % de propina. | Sección 5: Familia (propina `$16605`) y Pareja (propina `$7400`). | Se quitó `+ calcularImpoconsumo()`, dejando `(calcularSubtotal() - calcularDescuento()) * 0.10;`. |
| 9 | `Pedido.java` — `esVegetariano()` | Tenía un `else { return true; }` dentro del ciclo, deteniéndose en el primer producto vegetariano. | Sección 4: `¿Vegetariano? amigos / pareja: false / true`. | Se eliminó el `else` para que solo retorne `true` al terminar todo el ciclo `for`. |
| 10 | `Brunch.java` — `cerrarPedido(Pedido)` | Marcaba el pedido como pagado pero no liberaba la mesa ocupada. | Sección 6: `¿Mesa 3 libre? / ¿Abrió el siguiente grupo?: true / true` y ventas. | Se agregó `pedido.getMesa().liberar();` después de `pedido.marcarPagado();`. |

## 3. Revisión de código
| # | Archivo y método | Por qué era una mala práctica | Qué cambiamos |
|---|------------------|-------------------------------|---------------|
| R1 | `LineaPedido.java` — `obtenerEstacion()` | Polimorfismo desaprovechado: usaba una cadena de `instanceof` para preguntar el tipo de producto. | Se definirá un método abstracto `obtenerEstacion()` en `ProductoMenu` y se implementará en cada subclase. |
| R2 | `Pedido.java` | Números mágicos en los cálculos de descuento (`0.10`), impoconsumo (`0.08`) y propina (`0.10`). | Se reemplazarán por constantes con nombres descriptivos (ej. `PORCENTAJE_DESCUENTO`). |
| R3 | `Pedido.java` | Código duplicado al repetir operaciones como `calcularSubtotal() - calcularDescuento()`. | Se extraerá en un método privado auxiliar (ej. `calcularBaseGravable()`). |
| R4 | `Brunch.java` — `getMenu()` | Lista interna expuesta directamente sin retornar copia defensiva. | Se retornará una nueva lista copiada: `return new ArrayList<>(menu);`. |
| R5 | `Brunch.java` — método privado `v` | Nombre poco descriptivo y estructura `if/else` redundante para retornar un booleano. | Se renombrará a un nombre claro como `puedeAgregarAlPedido` y se simplificará el retorno. |

## 4. Salida final del programa
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
Pedido [PED-001] mesa 3, 4 personas, 25 min, total $365679
Subtotal $184500 | Descuento $18450 | Impoconsumo $183024 | Propina $16605 | Total $365679
Pedido [PED-002] mesa 2, 4 personas, 20 min, total $216000
Subtotal $108000 | Descuento $0 | Impoconsumo $108000 | Propina $0 | Total $216000
Pedido [PED-003] mesa 1, 2 personas, 20 min, total $155400
Subtotal $74000 | Descuento $0 | Impoconsumo $74000 | Propina $7400 | Total $155400

=== 6. Cierre del día ===
¿Se cerró la familia? true
¿Se cerró dos veces? false
¿Se cerró la pareja? true
¿La mesa 3 quedó libre? true
¿Se abrió el siguiente grupo en la mesa 3? true
Pedidos abiertos en el día: 4
Ventas del día: $521079

=== 7. Polimorfismo ===
Como Identificable: PED-001
Panaderia vale $7000
Adicion vale $3500
LineaPedido vale $79000
Pedido vale $155400

## 5. Conceptos de POO en este proyecto
En 2 o 3 líneas cada uno, expliquen DÓNDE se usa en este código:
- **Encapsulamiento:** Ocultamiento de los atributos protegidos o privados (como `precioBase` o `stock` en `ProductoMenu`), permitiendo su acceso y modificación controlada exclusivamente a través de métodos públicos (*getters* y *setters*).
- **Herencia:** Mecanismo mediante el cual las clases `PlatoFuerte`, `Bebida` y `Panadería` extienden de la clase abstracta `ProductoMenu`, heredando sus atributos y comportamientos comunes.
- **Abstracción:** Uso de la clase `ProductoMenu` y métodos abstractos como `minutosPreparacion()` o `getCategoria()`, definiendo una estructura común sin implementar los detalles específicos hasta llegar a las subclases.
- **Interfaz:** Las interfaces `Identificable` y `Cobrable` definen contratos de comportamiento que deben cumplir clases como `ProductoMenu`, `Adicion`, `LineaPedido` y `Pedido` sin importar su jerarquía de herencia.
- **Polimorfismo:** Capacidad de tratar de forma uniforme a objetos de diferentes clases que comparten una interfaz o clase padre (por ejemplo, al calcular el total de una lista de elementos `Cobrable` o al procesar productos polimórficamente).