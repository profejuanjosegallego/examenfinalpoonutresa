# Evidencias: equipo <Loaiza García>, <Urrego Escobar>, <Lombana Osorio>

**Integrantes:** <nombre completo 1>, <nombre completo 2>, <nombre completo 3>

## 1. Errores de sintaxis corregidos
| # | Archivo | Línea | Mensaje del compilador | Corrección |
|---|---------|-------|------------------------|------------|
| 1 | modelo/Adicion.java | 4 | `'{' expected` | `implement` estaba mal escrito; se cambió por `implements Cobrable`. |
| 2 | modelo/Bebida.java | 23 | `')' expected` | Faltaba cerrar el paréntesis del `if`: `if (mililitros > MILILITROS_TAMANO_GRANDE) {`. |
| 3 | modelo/Panaderia.java | 31 | `unclosed string literal` | Faltaba cerrar las comillas: `return "Panadería";`. |
| 4 | modelo/Pedido.java | 84 | `';' expected` | El `for` usaba comas en vez de punto y coma: `for (int i = 0; i < lineas.size(); i++)`. |
| 5 | modelo/Bebida.java | 31 | `minutosPreparacion() in Bebida cannot override minutosPreparacion() in ProductoMenu` / `return type double is not compatible with int` | Al sobrescribir hay que respetar el tipo de retorno del padre: `public int minutosPreparacion()`. |
| 6 | modelo/LineaPedido.java | 31 | `calcularPrecio() in LineaPedido cannot implement calcularPrecio() in Cobrable` / `attempting to assign weaker access privileges; was public` | Los métodos de una interfaz son públicos: `public double calcularPrecio()`. |
| 7 | modelo/LineaPedido.java | 12 | `incompatible types: ProductoMenu cannot be converted to boolean` | Se usaba `=` (asignar) en vez de `==` (comparar): `if (producto == null)`. |
| 8 | modelo/Panaderia.java | 20 | `precioBase has private access in ProductoMenu` | La hija no puede leer un atributo `private` del padre; se usó el getter: `getPrecioBase() * unidades`. |
| 9 | modelo/Pedido.java | 42 (declarado en 12) | `cannot assign a value to final variable pagado` | `pagado` cambia de `false` a `true` en `marcarPagado()`, así que no puede ser `final`. Quedó `private boolean pagado;`. |
| 10 | modelo/PlatoFuerte.java | 11 | `constructor ProductoMenu in class ProductoMenu cannot be applied to given types;` | Al `super` le faltaba el stock: `super(codigo, nombre, precioBase, stock)`. |
| 11 | Main.java | 23 (y 24, 25, 26) | `cannot find symbol` (symbol: class Bebida) | Faltaba importar la clase; se agregó `import modelo.Bebida;`. Las 4 líneas tenían la misma causa. |
| 12 | Main.java | 44 | `incompatible types: String cannot be converted to int` | El número de mesa es `int`: `new Mesa(1, 2)` en vez de `new Mesa("1", 2)`. |
| 13 | modelo/Pedido.java | 47 | `variable total might not have been initialized` | La suma debe arrancar en cero: `double total = 0;`. |
| 14 | servicio/Brunch.java | 58 | `missing return statement` | `buscarMesa` no retornaba nada si no encontraba la mesa; se agregó `return null;` al final, como dice su comentario. |
| 15 | servicio/Brunch.java | 75 | `unreachable statement` | `pedidos.add(pedido)` estaba después del `return`; se pasó antes: primero se guarda el pedido y luego se retorna. |

## 2. Errores de lógica corregidos
| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
|---|------------------|---------------|--------------------------|--------------------|
| 1 | ProductoMenu, atributo `stock` (línea 8) | Era `static`: todos los productos compartían un solo stock, y quedaba el del último producto creado (5). | Sección 1, stock de la carta: salían diez veces 5 en vez de 8, 6, 10, 5, 30... | Se quitó `static`: `private int stock;`. Ahora cada producto tiene su propio stock. |
| 2 | ProductoMenu.setDisponible (línea 48) | `disponible = disponible;` se asignaba el parámetro a sí mismo y el atributo nunca cambiaba. | Sección 2, ¿vender 1 arepa después del chócolo?: daba `true` en vez de `false`. | `this.disponible = disponible;`. |
| 3 | Bebida.calcularPrecio (línea 23) | Usaba `>` y la regla dice 500 ml **o más**: el jugo de 500 ml no pagaba el recargo. | Sección 1, precio del jugo de mandarina: $9000 en vez de $11500. | `mililitros >= MILILITROS_TAMANO_GRANDE`. |
| 4 | LineaPedido.calcularPrecio (línea 34) | `sumaAdiciones = ...` reemplazaba la suma en cada vuelta, así que solo contaba la última adición. | Sección 7, LineaPedido: $71000 en vez de $79000 (faltaba el aguacate). | `sumaAdiciones += a.calcularPrecio();`. |
| 5 | Pedido.calcularDescuento (línea 56) | Usaba `\|\|` (o) y la regla pide las dos condiciones (y): los amigos, 4 personas con $108000, recibían descuento. | Sección 5, amigos: descuento $0. | `personas >= 4 && calcularSubtotal() >= 150000`. |
| 6 | Pedido.calcularImpoconsumo (línea 64) | Calculaba el 8 % sobre el subtotal completo, sin restar el descuento. | Sección 5, familia: impoconsumo $13284 (= 8 % de 166050), no $14760. | `(calcularSubtotal() - calcularDescuento()) * 0.08`. |
| 7 | Pedido.calcularPropina (línea 72) | Sumaba el impoconsumo a la base de la propina, y la regla dice que va sin impoconsumo. | Sección 5, familia: propina $16605 (= 10 % de 166050); pareja: $7400. | `(calcularSubtotal() - calcularDescuento()) * 0.10`. |
| 8 | Pedido.esVegetariano (líneas 98-100) | El `else return true` hacía que solo revisara la primera línea: si el primer producto era vegetariano, todo el pedido lo era. | Sección 4, ¿amigos vegetariano?: daba `true` (empiezan con huevos pericos y luego calentado). | Se quitó el `else`: retorna `false` al primer producto no vegetariano y `true` solo al final del ciclo. |
| 9 | Brunch.buscarProducto (línea 44) | Comparaba textos con `==` (compara referencias, no contenido) y además distinguía mayúsculas. | Sección 2, buscar pan-001: decía "no encontrado" en vez de Pandequeso. | `p.getCodigo().equalsIgnoreCase(codigo)`. |
| 10 | Brunch.cerrarPedido (línea 106) | Marcaba el pedido como pagado pero no liberaba la mesa. | Sección 6, ¿mesa 3 libre? y ¿abrió el siguiente grupo?: daban `false`. | Después de `marcarPagado()` se agregó `pedido.getMesa().liberar();`. |

## 3. Revisión de código
| # | Archivo y método | Por qué era una mala práctica | Qué cambiamos |
|---|------------------|-------------------------------|---------------|
| R1 | LineaPedido.obtenerEstacion | Preguntaba el tipo con una cadena de `instanceof`. Cada producto nuevo obligaba a tocar este método, y así se desaprovecha el polimorfismo. | Se agregó el método abstracto `getEstacion()` en ProductoMenu, implementado en PlatoFuerte ("Cocina"), Bebida ("Barra") y Panaderia ("Horno"). `obtenerEstacion()` sigue existiendo y solo delega: `return producto.getEstacion();`. |
| R2 | Pedido.calcularDescuento, calcularImpoconsumo, calcularPropina | Números mágicos (4, 150000, 0.10, 0.08, 0.10): no se sabe qué significan y, si cambia un porcentaje, hay que buscarlo en varias partes. | Constantes `private static final`: `PERSONAS_PLAN_FAMILIAR`, `SUBTOTAL_MINIMO_PLAN_FAMILIAR`, `PORCENTAJE_PLAN_FAMILIAR`, `PORCENTAJE_IMPOCONSUMO`, `PORCENTAJE_PROPINA`. |
| R3 | Pedido.calcularImpoconsumo, calcularPropina, calcularPrecio | El cálculo `calcularSubtotal() - calcularDescuento()` estaba repetido en tres métodos. | Se extrajo al método privado `calcularSubtotalConDescuento()`, que usan los tres. En `calcularDescuento()` también se guarda el subtotal en una variable para no calcularlo dos veces. |
| R4 | Brunch.getMenu | Devolvía la lista interna `menu`, y desde afuera se podían agregar o borrar productos sin pasar por `agregarAlMenu`. Eso rompe el encapsulamiento. | Retorna una copia, `new ArrayList<>(menu)`, igual que `Pedido.getLineas()`. La firma pública no cambia. |
| R5 | Brunch.v (privado) | El nombre `v` y los parámetros `p`, `c`, `r` no dicen nada. Además usaba `== true` y una variable con if/else para algo que es una sola expresión. | Se renombró a `sePuedeVender(ProductoMenu producto, int cantidad)` con cuerpo `return producto != null && producto.puedeVender(cantidad);` y se actualizó su uso en `agregarAlPedido`. |

Verificación: `diff salida_fase2.txt salida_fase3.txt` no imprime nada, así que la salida es idéntica antes y después de la revisión.

## 4. Salida final del programa

```
=== 1. Carta del domingo ===
  - Plato fuerte [PLA-001] Calentado paisa - $26000, 20 min, stock 8
  - Plato fuerte [PLA-002] Calentado con chicharr?n - $32000, 25 min, stock 6
  - Plato fuerte [PLA-003] Huevos pericos con arepa - $18000, 20 min, stock 10
  - Plato fuerte [PLA-004] Arepa de ch?colo con quesito - $16000, 20 min, stock 5
  - Bebida [BEB-001] Tinto de Jard?n - $5000, 4 min, stock 30
  - Bebida [BEB-002] Chocolate en leche - $7000, 4 min, stock 20
  - Bebida [BEB-003] Jugo de mandarina - $11500, 6 min, stock 15
  - Bebida [BEB-004] Limonada de coco - $10000, 6 min, stock 15
  - Panader?a [PAN-001] Pandequeso - $7000, 12 min, stock 12
  - Panader?a [PAN-002] Bu?uelo - $9000, 3 min, stock 5

=== 2. B?squedas y disponibilidad ===
Buscar pan-001: Pandequeso
Buscar XXX-999: no encontrado
?Se puede vender 1 arepa? true
Se acab? el ch?colo. ?Se puede vender 1 arepa? false

=== 3. Mesas ===
?La mesa 3 recibe a 6 personas? true
?La mesa 3 recibe a 7 personas? false

=== 4. Pedidos ===
No se puede vender 1 de PLA-004.
?Se vendi? la arepa no disponible? false
No se puede vender 6 de PAN-002.
?Se vendieron 6 bu?uelos? false
?Se vendieron 2 bu?uelos? true
La mesa 9 no existe.
?Se abri? el pedido de la mesa 9? false
La mesa 3 no puede recibir a 2 personas.
?Se abri? otro pedido en la mesa 3? false
Comanda de la familia:
  - 2 x Calentado con chicharr?n + Aguacate + Quesito -> Cocina
  - 2 x Huevos pericos con arepa -> Cocina
  - 4 x Chocolate en leche -> Barra
  - 3 x Jugo de mandarina -> Barra
  - 1 x Pandequeso -> Horno
Quedan de chocolate: 16
Quedan de bu?uelo: 3
Pedido de amigos, ?vegetariano? false
Pedido de pareja, ?vegetariano? true

=== 5. Cuentas ===
Pedido [PED-001] mesa 3, 4 personas, 25 min, total $195939
Subtotal $184500 | Descuento $18450 | Impoconsumo $13284 | Propina $16605 | Total $195939
Pedido [PED-002] mesa 2, 4 personas, 20 min, total $116640
Subtotal $108000 | Descuento $0 | Impoconsumo $8640 | Propina $0 | Total $116640
Pedido [PED-003] mesa 1, 2 personas, 20 min, total $87320
Subtotal $74000 | Descuento $0 | Impoconsumo $5920 | Propina $7400 | Total $87320

=== 6. Cierre del d?a ===
?Se cerr? la familia? true
?Se cerr? dos veces? false
?Se cerr? la pareja? true
?La mesa 3 qued? libre? true
?Se abri? el siguiente grupo en la mesa 3? true
Pedidos abiertos en el d?a: 4
Ventas del d?a: $283259

=== 7. Polimorfismo ===
Como Identificable: PED-001
Panaderia vale $7000
Adicion vale $3500
LineaPedido vale $79000
Pedido vale $87320
```

(Los `?` en lugar de tildes vienen de la codificación de la consola, no del programa.)

## 5. Conceptos de POO en este proyecto
- **Encapsulamiento:** Los atributos de `ProductoMenu` (`codigo`, `nombre`, `precioBase`, `stock`, `disponible`) son `private` y solo se cambian con métodos que validan, como `setPrecioBase()` (rechaza precios ≤ 0) y `vender()` (solo descuenta si `puedeVender()`). `Pedido.getLineas()` y `Brunch.getMenu()` devuelven copias para proteger las listas internas.
- **Herencia:** `PlatoFuerte`, `Bebida` y `Panaderia` extienden `ProductoMenu` y reciben su código, nombre, precio y stock por medio de `super(...)`. `PlatoFuerte` y `Bebida` reutilizan el precio del padre con `super.calcularPrecio()` y le suman su recargo.
- **Abstracción:** `ProductoMenu` es `abstract`: no se puede crear un "producto" genérico. Define qué debe saber cada producto (`minutosPreparacion()`, `getCategoria()`, `getEstacion()`, `esVegetariano()`) sin decir cómo; eso lo decide cada hija.
- **Interfaz:** `Identificable` (`getCodigo()`, `obtenerFicha()`) la implementan `ProductoMenu` y `Pedido`. `Cobrable` (`calcularPrecio()`) la implementan `ProductoMenu`, `Adicion`, `LineaPedido` y `Pedido`, clases que no tienen relación de herencia entre sí.
- **Polimorfismo:** En la sección 7 de `Main` se guardan una panadería, una adición, una línea y un pedido en un `ArrayList<Cobrable>`, y `c.calcularPrecio()` ejecuta la versión de cada clase. También `ProductoMenu.obtenerFicha()` llama `calcularPrecio()` y `minutosPreparacion()`, que dan resultados distintos según sea plato, bebida o panadería. Lo mismo hace `LineaPedido.obtenerEstacion()` con `getEstacion()`.

## 6. Uso de IA

Con autorización del profesor, usamos una skill de Claude (`auditar-brunch-poo`) como apoyo para auditar y corregir el proyecto. El equipo revisó y entiende cada corrección registrada en este documento.