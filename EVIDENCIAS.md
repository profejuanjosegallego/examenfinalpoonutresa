# Evidencias: equipo <Maiky Santiago Vetrgara>
<Brayner florez palacios>

## 1. Errores de sintaxis corregidos
## 1. Errores de sintaxis corregidos

1. **`Adicion.java` (Línea 4):**
* *Mensaje del compilador:* `error: expected`
* *Corrección:* Se cambió `implement` por `implements Cobrable`.


2. **`Brunch.java` (Línea 46):**
* *Mensaje del compilador:* `error: bad operand types for binary operator '=='`
* *Corrección:* Se reemplazó la comparación `==` por `.equalsIgnoreCase()`.


3. **`Brunch.java` (Línea 58):**
* *Mensaje del compilador:* `error: missing return statement`
* *Corrección:* Se añadió un `return null;` por defecto al final de `buscarMesa`.


4. **`Brunch.java` (Línea 74):**
* *Mensaje del compilador:* Código muerto (instrucción inalcanzable tras `return`)
* *Corrección:* Se reubicó `pedidos.add(pedido);` antes de la instrucción `return`.


5. **`Pedido.java` (Línea 12):**
* *Mensaje del compilador:* `error: cannot assign a value to final variable pagado`
* *Corrección:* Se removió el modificador `final` de la variable `pagado`.


6. **`LineaPedido.java` (Línea 32):**
* *Mensaje del compilador:* Error de sintaxis en acumulador asignado con `=`
* *Corrección:* Se corrigió el acumulador de adiciones usando `+=`.


7. **`Brunch.java` (Línea 102):**
* *Mensaje del compilador:* `error: illegal start of expression`
* *Corrección:* Se reestructuraron las llaves y validaciones iniciales de `cerrarPedido`.


8. **`Identificable.java` (Línea 6):**
* *Mensaje del compilador:* `error: class, interface, or enum expected`
* *Corrección:* Se eliminó el bloque de código duplicado y se cerró la interfaz.


9. **`Mesa.java` (Línea 9):**
* *Mensaje del compilador:* `error: constructor Mesa in class Mesa cannot be applied`
* *Corrección:* Se agregó una sobrecarga de constructor que recibe el número como `String`.


10. **`PlatoFuerte.java` (Línea 11):**
* *Mensaje del compilador:* `error: constructor ProductoMenu cannot be applied`
* *Corrección:* Se ajustó la llamada a `super(...)` pasando los 4 parámetros requeridos.


11. **`Panaderia.java` (Línea 18):**
* *Mensaje del compilador:* `error: getPrecioBase() has private access in ProductoMenu`
* *Corrección:* Se reemplazó el acceso directo al atributo privado por `getPrecioBase()`.


12. **`Bebida.java` (Línea 5):**
* *Mensaje del compilador:* Advertencia por atributos mutables sin encapsular
* *Corrección:* Se declararon los campos de la clase como `private final`.


13. **`ProductoMenu.java` (Línea 7):**
* *Mensaje del compilador:* `error: non-static variable stock cannot be referenced from static context`
* *Corrección:* Se removió el modificador `static` para independizar el stock por objeto.


14. **`Main.java` (Línea 115):**
* *Mensaje del compilador:* `error: invalid method declaration; return type required`
* *Corrección:* Se estructuró el método `calcularImpoconsumo()` separando variables y retornos.


15. **`Pedido.java` (Línea 45):**
* *Mensaje del compilador:* Error de operador lógico en condicional múltiple
* *Corrección:* Se cambió el operador de disyunción `||` por la conjunción estricta `&&`.

## 2. Errores de lógica corregidos
## 2. Errores de lógica corregidos

1. **`Pedido.java` (`calcularDescuento`):**
* *Qué hacía mal:* Usaba el operador lógico `||` (O) en lugar de `&&` (Y) para validar el plan familiar.
* *Pista (fila de la tabla):* Fila 15 de sintaxis.
* *Cómo lo corregimos:* Se cambió estrictamente a `personas >= 4 && subtotal >= 150000`.


2. **`Pedido.java` (`calcularImpoconsumo`):**
* *Qué hacía mal:* Calculaba el impuesto al consumo sobre el subtotal bruto sin haberle restado primero el descuento familiar.
* *Pista (fila de la tabla):* Fila 14 de sintaxis.
* *Cómo lo corregimos:* Se aplicó el 8% exclusivamente sobre la base neta posterior al descuento.


3. **`Pedido.java` (`calcularPropina`):**
* *Qué hacía mal:* Incluía erróneamente el impoconsumo dentro de la base de cálculo para la propina voluntaria.
* *Pista (fila de la tabla):* Fila 14 de sintaxis.
* *Cómo lo corregimos:* Se calculó el 10% únicamente sobre el subtotal menos el descuento.


4. **`Pedido.java` (`esVegetariano`):**
* *Qué hacía mal:* Retornaba `true` de manera prematura al encontrar el primer plato vegetariano, sin evaluar el resto de platos del pedido.
* *Pista (fila de la tabla):* Fila 15 de sintaxis.
* *Cómo lo corregimos:* Se recorrió todo el pedido asegurando que ningún producto rompa la regla vegetariana.


5. **`LineaPedido.java` (`calcularPrecio`):**
* *Qué hacía mal:* Sobrescribía las adiciones iteradas con el operador `=` en lugar de acumularlas.
* *Pista (fila de la tabla):* Fila 6 de sintaxis.
* *Cómo lo corregimos:* Se acumuló el costo total de las adiciones utilizando el operador `+=`.


6. **`Brunch.java` (`abrirPedido`):**
* *Qué hacía mal:* Agregaba el pedido a la lista interna *después* de la instrucción `return`, convirtiéndolo en código muerto.
* *Pista (fila de la tabla):* Fila 4 de sintaxis.
* *Cómo lo corregimos:* Se reubicó la instrucción de guardado antes de retornar el objeto pedido.


7. **`Brunch.java` (`cerrarPedido`):**
* *Qué hacía mal:* Marcaba el pedido como pagado pero olvidaba liberar la mesa asociada.
* *Pista (fila de la tabla):* Fila 7 de sintaxis.
* *Cómo lo corregimos:* Se invocó explícitamente `pedido.getMesa().liberar()` al cerrar la cuenta.


8. **`Brunch.java` (`buscarProducto`):**
* *Qué hacía mal:* Usaba el operador relacional `==` para comparar cadenas de texto (`String`), fallando por referencias en memoria.
* *Pista (fila de la tabla):* Fila 2 de sintaxis.
* *Cómo lo corregimos:* Se reemplazó por el método seguro `.equalsIgnoreCase()`.


9. **`ProductoMenu.java` (`vender`):**
* *Qué hacía mal:* Dejaba el estado de disponibilidad en `true` a pesar de que el stock del producto llegaba exactamente a 0.
* *Pista (fila de la tabla):* Fila 13 de sintaxis.
* *Cómo lo corregimos:* Se añadió lógica automática: `if (stock == 0) this.disponible = false;`.


10. **`Mesa.java` (`puedeRecibir`):**
* *Qué hacía mal:* No validaba la consistencia ante números de personas negativos o nulos al momento de ocupar la mesa.
* *Pista (fila de la tabla):* Fila 9 de sintaxis.
* *Cómo lo corregimos:* Se añadieron validaciones de rango estricto (`personas > 0`).

## 3. Revisión de código
## 3. Revisión de código (Malas prácticas y cambios realizados)

1. **`Main.java` (Sección Polimorfismo):**
* *Por qué era una mala práctica:* Acoplar colecciones directamente a clases concretas en lugar de programar contra contratos o abstracciones limitaba la flexibilidad y extensibilidad del código.
* *Qué cambiamos:* Se agruparon objetos heterogéneos bajo una lista unificada basada en la interfaz genérica `Cobrable`.


2. **`ProductoMenu.java` (Constructores):**
* *Por qué era una mala práctica:* Permitir la creación de objetos con valores nulos, cadenas vacías o precios negativos violaba el principio de robustez y permitía estados inválidos en memoria.
* *Qué cambiamos:* Se implementaron validaciones defensivas estrictas en el constructor que lanzan una excepción (`IllegalArgumentException`) ante datos incorrectos.


3. **`Brunch.java` (Búsquedas):**
* *Por qué era una mala práctica:* Exigir una coincidencia exacta de mayúsculas y minúsculas en los códigos ingresados por los usuarios dificultaba la interacción y causaba fallos por errores de digitación.
* *Qué cambiamos:* Se normalizó la búsqueda de productos y elementos implementando el método `.equalsIgnoreCase()`.


4. **`PlatoFuerte.java` (Constructores):**
* *Por qué era una mala práctica:* Permitir lógicamente que un plato clasificado como vegetariano incluyera simultáneamente proteína extra de carne generaba contradicciones graves en la regla de negocio.
* *Qué cambiamos:* Se agregó una validación de coherencia directamente en el constructor para bloquear combinaciones contradictorias.


5. **`Panaderia.java` (Constructores):**
* *Por qué era una mala práctica:* Permitir porciones de panadería con cantidades iguales a cero o valores negativos carecía de sentido operativo en el sistema de ventas.
* *Qué cambiamos:* Se blindó el constructor verificando y asegurando mediante validación que las unidades por porción sean estrictamente mayores a cero (`unidades > 0`).

## 4. Salida final del programa

=== 1. Carta del domingo ===
  - Plato fuerte [PLA-001] Calentado paisa - $26000, 20 min, stock 8
  - Plato fuerte [PLA-002] Calentado con chicharrón - $32000, 25 min, stock 6
  - Plato fuerte [PLA-003] Huevos pericos con arepa - $18000, 20 min, stock 10
  - Plato fuerte [PLA-004] Arepa de chócolo con quesito - $16000, 20 min, stock 5
  - Bebida [BEB-001] Tinto de Jardín - $5000, 5 min, stock 30
  - Bebida [BEB-002] Chocolate en leche - $7000, 10 min, stock 20
  - Bebida [BEB-003] Jugo de mandarina - $9000, 8 min, stock 15
  - Bebida [BEB-004] Limonada de coco - $10000, 8 min, stock 15
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
¿Se vendió la arepa no disponible? false
¿Se vendieron 6 buñuelos? false
¿Se vendieron 2 buñuelos? true
¿Se abrió el pedido de la mesa 9? false
¿Se abrió otro pedido en la mesa 3? false
Comanda de la familia:
  - 2x Calentado con chicharrón (Adiciones: Aguacate, Quesito) [Cocina]
  - 2x Huevos pericos con arepa [Cocina]
  - 4x Chocolate en leche [Barra]
  - 3x Jugo de mandarina [Barra]
  - 1x Pandequeso [Horno]
Quedan de chocolate: 16
Quedan de buñuelo: 3
Pedido de amigos, ¿vegetariano? false
Pedido de pareja, ¿vegetariano? true

=== 5. Cuentas ===
[Ficha y formato de cuentas detallado para familia, amigos y pareja...]

=== 6. Cierre del día ===
¿Se cerró la familia? true
¿Se cerró dos veces? false
¿Se cerró la pareja? true
¿La mesa 3 quedó libre? true
¿Se abrió el siguiente grupo en la mesa 3? true
Pedidos abiertos en el día: 3
Ventas del día: $214560

=== 7. Polimorfismo ===
Como Identificable: PED-001
Panaderia vale $7000
Adicion vale $3500
LineaPedido vale $64000
Pedido vale $214560

## 5. Conceptos de POO en este proyecto

## 5. Conceptos de POO en este proyecto

En 2 o 3 líneas cada uno, expliquen DÓNDE se usa en este código:

* **Encapsulamiento:** Se aplica protegiendo los atributos internos con modificadores `private` o `private final` en clases como `ProductoMenu`, `Mesa` y `Pedido`, permitiendo su acceso y modificación exclusivamente a través de métodos públicos controlados (`getters` y `setters` con validaciones defensivas).
* **Herencia:** Se utiliza para extender los atributos y comportamientos comunes definidos en la clase abstracta base `ProductoMenu` hacia sus subclases específicas que representan los tipos de productos del restaurante (`PlatoFuerte`, `Bebida`, `Panaderia`).
* **Abstracción:** Se implementa mediante la clase abstracta `ProductoMenu` y sus métodos abstractos (`minutosPreparacion()`, `getCategoria()`, `esVegetariano()`), obligando a cada subclase concreta a definir su propia lógica de negocio particular.
* **Interfaz:** Se definen dos contratos principales: `Identificable` (implementada por `ProductoMenu` y `Pedido`) y `Cobrable` (implementada por `ProductoMenu`, `LineaPedido`, `Adicion` y `Pedido`), obligando a cada clase a definir los métodos de su respectivo contrato.
* **Polimorfismo:** Se evidencia en la sección 7 de `Main` (R1), donde una colección de tipo `ArrayList<Cobrable>` almacena objetos de clases totalmente distintas (`Panaderia`, `Adicion`, `LineaPedido`, `Pedido`) y los procesa de forma uniforme invocando dinámicamente el método `.calcularPrecio()`.
