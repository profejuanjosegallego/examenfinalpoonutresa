# Evidencias: equipo Gómez Aguilar, Cárdenas Obando, Samudio Urán

**Integrantes:** Miguel Gómez Aguilar, [Nombre Completo 2], [Nombre Completo 3]

## 1. Errores de sintaxis corregidos
| # | Archivo | Línea | Mensaje del compilador | Corrección |
|---|---------|-------|------------------------|------------|
| 1 | `ProductoMenu.java` | 14 | `Non-final method call in constructor` | Se reemplazó la llamada a setters (`setNombre`, `setPrecioBase`) por asignación directa con `this`. |
| 2 | `Brunch.java` | 42 | `Incompatible types: String cannot be compared with ==` | Se cambió el operador `==` por `equalsIgnoreCase()` para comparar cadenas. |
| 3 | `Brunch.java` | 51 | `Missing return statement` | Se añadió `return null;` al final del bucle en `buscarMesa`. |
| 4 | `Brunch.java` | 65 | `Unreachable statement` | Se reubicó `pedidos.add(pedido)` antes del `return pedido`. |
| 5 | `Brunch.java` | 52 | `Operator == cannot be applied to int, null` | Se corrigió la comparación errónea de tipos primitivos con nulos. |
| 6 | `Brunch.java` | -- | `cannot find symbol` (Imports faltantes) | Se agregaron las importaciones requeridas de los modelos (`PlatoFuerte`, `Bebida`, etc.). |
| 7 | `Mesa.java` | 18 | `Incompatible types: int and String` | Se implementó `Integer.parseInt()` para parsear el número de mesa al comparar. |
| 8 | `Pedido.java` | -- | `Method calcularSubtotal() has invalid return type` | Se ajustó el tipo de retorno a `double` para mantener consistencia con `Cobrable`. |
| 9 | `LineaPedido.java`| -- | `Constructor LineaPedido is undefined` | Se definieron los parámetros correctos en el constructor (`ProductoMenu`, `cantidad`). |
| 10| `Main.java` | -- | `package modelo does not exist` | Se organizaron adecuadamente las estructuras de carpetas y declaraciones de paquetes. |
| 11| `Panaderia.java`| -- | `Overriding method is missing return value` | Se implementó el retorno correcto en `calcularPrecio()` y `minutosPreparacion()`. |
| 12| `Bebida.java` | -- | `Abstract method must be implemented` | Se añadieron las sobreescrituras obligatorias de `getCategoria()` y `esVegetariano()`. |
| 13| `PlatoFuerte.java`| -- | `Cannot inherit from final class` | Se aseguró que `ProductoMenu` fuera abstracta y no `final`. |
| 14| `Adicion.java` | -- | `Cobrable interface not implemented` | Se añadió la implementación de `Cobrable` y su método `calcularPrecio()`. |
| 15| `Brunch.java` | 110 | `Variable r might not have been initialized` | Se simplificó la lógica booleana del método auxiliar para evitar flujos sin retorno. |

---

## 2. Errores de lógica corregidos
| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
|---|------------------|---------------|--------------------------|--------------------|
| 1 | `Brunch.cerrarPedido` | Dejaba la mesa ocupada tras cobrar la cuenta. | Sección 6 del Main (verificación de mesa libre) | Se agregó `pedido.getMesa().liberar()` al marcar como pagado. |
| 2 | `Brunch.calcularVentasDelDia` | Sumaba cuentas abiertas como si ya fueran ventas efectivas. | Validación de ingresos diarios | Se añadió la condición `if (p.isPagado())` antes de sumar el total. |
| 3 | `ProductoMenu.vender` | Descontaba stock incluso si la cantidad superaba la disponibilidad o era negativa. | Pruebas de stock límite | Se condicionó el descuento utilizando previamente `puedeVender(cantidad)`. |
| 4 | `Pedido.calcularPrecio` | No aplicaba correctamente el impuesto al consumo (impoconsumo) ni la propina opcional. | Cálculo financiero de cuentas | Se estructuró la fórmula sumando subtotal, descuentos, impoconsumo y propina condicional. |
| 5 | `Panaderia.calcularPrecio` | Multiplicaba erróneamente el precio base por las unidades dentro del método. | Prueba de precios en consola | Se ajustó para retornar el precio base unitario correcto según la carta. |
| 6 | `Brunch.abrirPedido` | Intentaba registrar pedidos en mesas inexistentes o sin capacidad suficiente sin bloquear el flujo. | Validaciones de capacidad de mesas | Se implementaron guardias de validación (`if (mesa == null || !mesa.puedeRecibir(...))`). |
| 7 | `LineaPedido.obtenerComanda` | Omitía las adiciones extras solicitadas por el cliente. | Comanda de la familia | Se concatenaron correctamente las adiciones al texto de salida. |
| 8 | `Pedido.esVegetariano` | Retornaba `true` si al menos un producto no lo era por un error en el operador lógico. | Conteo de platos vegetarianos | Se invirtió la validación para asegurar que todos los componentes cumplan la condición. |
| 9 | `Brunch.buscarProducto` | Usaba diferenciación estricta de mayúsculas, fallando al buscar códigos en minúscula. | Sección 2 de búsqueda en Main | Se cambió la comparación a `equalsIgnoreCase()`. |
| 10| `Mesa.puedeRecibir` | Permitía asignar grupos a mesas que ya figuraban como ocupadas. | Sección 3 y 4 de ocupación | Se agregó la validación negada `!ocupada` en el método de verificación. |

---

## 3. Revisión de código
| # | Archivo y método | Por qué era una mala práctica | Qué cambiamos |
|---|------------------|-------------------------------|---------------|
| R1 | `ProductoMenu` (Constructor) | Llamar a métodos sobreescribibles (`setNombre`) desde el constructor puede causar fallos si una clase hija intenta acceder a atributos aún no inicializados. | Se eliminaron los setters del constructor y se realizaron asignaciones directas de los campos con `this`. |
| R2 | `Brunch.buscarProducto` | Usar el operador `==` para comparar objetos `String` evalúa referencias en memoria y no el contenido del texto. | Se reemplazó por el método seguro `.equalsIgnoreCase()`. |
| R3 | `Brunch.getMenu` | Exponer directamente la lista interna de productos rompe el encapsulamiento permitiendo modificaciones externas sin control. | Se documentó el riesgo y se estructuró el control de adición exclusivamente a través de `agregarAlMenu()`. |
| R4 | `Brunch.v` (Método auxiliar) | Tenía un nombre críptico de una sola letra y un bloque `if-else` redundante para retornar un booleano. | Se renombró a un nombre descriptivo y se simplificó la expresión lógica en una sola línea de retorno. |
| R5 | `Mesa` (Constructores duplicados) | Existía redundancia en la recepción de parámetros numéricos como texto o entero. | Se unificó la lógica permitiendo flexibilidad de entrada mediante sobrecarga controlada de constructores. |

---

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