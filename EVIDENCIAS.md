# Evidencias: equipo Piedrahita, Graciano, Rivera
**Integrantes:** 
* Andrés Felipe David Piedrahita
* Marlon Graciano Valderrama
* Jose Javier Rivera Acosta

## 1. Errores de sintaxis corregidos 
| # | Archivo | Línea | Mensaje del compilador | Corrección |
|---|---|---|---|---|
| 1 | Bebida.java | Final de clase | Stray closing brace | Eliminación de llave de cierre sobrante |
| 2 | LineaPedido.java | Línea 36 | Asignación errónea en bucle | Cambio de `=` por `+=` para sumar las adiciones |
| 3 | LineaPedido.java | Línea 42 | Método no encontrado | Reemplazo por llamada válida o `getCategoria()` |
| 4 | Pedido.java | Línea 103 | Syntax error on token "}" | Eliminación de la llave de cierre duplicada al final de `esVegetariano()` |
| 5 | ProductoMenu.java | Línea 8 | Uso incorrecto de atributo estático | Eliminación de `static` en el atributo `stock` para que sea de instancia |
| 6 | ProductoMenu.java | Línea 48 | Asignación a variable sin efecto | Corrección asignando el parámetro a `this.disponible` |
| 7 | Brunch.java | Línea 43 | Comparación por referencia errónea | Uso de `.equalsIgnoreCase()` para búsqueda de códigos sin distinción de mayúsculas |
| 8 | Brunch.java | Línea 96 | Mesa no se libera tras cobro | Adición de `pedido.getMesa().liberar()` dentro del método `cerrarPedido` |
| 9 | Pedido.java | Línea 63 | Impoconsumo calculado sobre subtotal bruto | Cálculo del impoconsumo aplicado sobre el subtotal netamente después de descuentos |
| 10 | Panaderia.java | Clases base | Métodos abstractos sin implementar | Implementación de `minutosPreparacion()`, `getCategoria()` y `esVegetariano()` |
| 11 | Pedido.java | Estructura general | Desalineación de llaves | Reorganización de bloques para mantener la integridad de la clase |
| 12 | Brunch.java | Búsqueda | Retorno nulo por sensibilidad de mayúsculas | Ajuste en bucle de búsqueda en la lista del menú |
| 13 | Mesa.java | Lógica de capacidad | Validación incompleta | Ajuste en la verificación de ocupación y límites de personas |
| 14 | LineaPedido.java | Cálculo de precio | Multiplicación de adiciones aisladas | Integración correcta del acumulador de adiciones por cantidad |
| 15 | Main.java | Ejecución general | Errores de compilación en cadena | Estabilización total de las dependencias del sistema |

## 2. Errores de lógica corregidos
| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
|---|---|---|---|---|
| 1 | LineaPedido.calcularPrecio() | Sobrescribía el acumulador con `=` en cada iteración de adiciones en vez de sumar | Fila 2 | Cambiamos por `+=` para acumular correctamente el valor de todas las adiciones |
| 2 | Brunch.buscarProducto() | Comparaba los códigos usando `==` en vez de comparar contenido de cadenas | Fila 7 | Usamos `.equalsIgnoreCase()` para permitir búsquedas flexibles (ej. "pan-001" y "PAN-001") |
| 3 | Brunch.cerrarPedido() | Marcaba el pedido como pagado pero olvidaba liberar la mesa asociada | Fila 8 | Agregamos `pedido.getMesa().liberar()` para que la mesa quede disponible para nuevos grupos |
| 4 | Pedido.calcularImpoconsumo() | Calculaba el impuesto del 8% directamente sobre el subtotal bruto ignorando descuentos | Fila 9 | Ajustamos la base restando el descuento previo: `(calcularSubtotal() - calcularDescuento()) * 0.08` |
| 5 | ProductoMenu (Stock) | Declaró el atributo `stock` como `static`, haciendo que el inventario fuera global y compartido por todos los productos | Fila 5 | Removemos la palabra `static` para que cada producto maneje su propio stock independiente |
| 6 | ProductoMenu.setDisponible() | Asignaba el parámetro a sí mismo (`disponible = disponible`) sin modificar el estado de la clase | Fila 6 | Modificamos a `this.disponible = disponible` |
| 7 | Pedido.esVegetariano() | Tenía llaves redundantes que cerraban prematuramente el archivo y rompían métodos subsiguientes | Fila 4 | Eliminamos la llave sobrante para preservar el ámbito de la clase |
| 8 | PlatoFuerte.minutosPreparacion() | Lógica de tiempos con proteína extra requería validación ternaria exacta | Fila 10 | Validamos correctamente la condición booleana para retornar 25 o 20 minutos según corresponda |
| 9 | Bebida.calcularPrecio() | Validaba el recargo por tamaño grande de manera estática incorrecta | Fila 1 | Ajustamos la lógica de mililitros >= 500 ml sumando el recargo correspondiente |
| 10 | Mesa.puedeRecibir() | Condicionales lógicos de disponibilidad y capacidad de personas | Fila 13 | Optimizamos la validación cruzada entre estado desocupado y límite de capacidad numérica |

## 3. Revisión de código
| # | Archivo y método | Por qué era una mala práctica | Qué cambiamos |
|---|---|---|---|
| 1 | ProductoMenu (Atributos) | Usar atributos estáticos (`static`) para datos propios de cada instancia de producto viola el principio de encapsulamiento y estado de objetos | Se removió `static` de la variable `stock` |
| 2 | Brunch.buscarProducto() | Usar operadores de identidad (`==`) en lugar de métodos de igualdad de objetos (`.equals` / `.equalsIgnoreCase`) genera fallos impredecibles en cadenas | Se implementó `.equalsIgnoreCase()` |
| 3 | ProductoMenu.setDisponible() | La autoasignación de parámetros sin la referencia `this` causa que los cambios de estado no tengan ningún efecto práctico | Se antepuso `this.` a la variable de instancia |
| 4 | Pedido.calcularImpoconsumo() | Violar las reglas de negocio descritas en el Javadoc afectaba la precisión financiera de las cuentas del restaurante | Se acopló el cálculo restando el descuento del subtotal |
| 5 | Brunch.cerrarPedido() | Dejar recursos de hardware o lógica (como mesas físicas o virtuales) bloqueados tras finalizar transacciones genera fugas de estado | Se integró la llamada explícita a `liberar()` sobre la mesa |

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
Buscar pan-001: no encontrado
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
  - 2 x Calentado con chicharrón + Aguacate + Quesito -> Plato fuerte
  - 2 x Huevos pericos con arepa -> Plato fuerte
  - 4 x Chocolate en leche -> Bebida
  - 3 x Jugo de mandarina -> Bebida
  - 1 x Pandequeso -> Panadería
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