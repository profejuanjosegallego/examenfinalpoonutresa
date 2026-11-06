# Evidencias: equipo <apellido 1>, <apellido 2>, <apellido 3>

**Integrantes:** <los tres nombres completos>

## 1. Errores de sintaxis corregidos
| # | Archivo | Línea | Mensaje del compilador | Corrección |
|1| Adicion.java  | 4 | implement cannot be resolved to a type|public class Adicion implements Cobrable|
|2| Bebida.java| 23 | Syntax error, insert ") Statement" to complete BlockStatements | if (mililitros > MILILITROS_TAMANO_GRANDE) { |   
|3| Bebida.java | 31 | The return type is incompatible with ProductoMenu.minutosPreparacion() |public int minutosPreparacion() {  |     
|4|LineaPedido.java  |12 |ProductoMenu producto - modelo.LineaPedido.LineaPedido(ProductoMenu, int) Source: examenfinalpoonutresa_feb9ba99  | if (producto == null) { |
 
|5| LineaPedido.java | 31 | Cannot reduce the visibility of the inherited method from Cobrable | public double calcularPrecio() {  |
       
|6| Panaderia.java | 31 | String literal is not properly closed by a double-quote |  return "Panadería"; |
      
|7| Panaderia.java | 20 | The field ProductoMenu.precioBase is not visible |  return getPrecioBase() * unidades;  |
       
|8| PlatoFuerte.java |11  | String codigo - modelo.PlatoFuerte.PlatoFuerte(String, String, double, int, boolean, boolean) Source: examenfinalpoonutresa_feb9ba99 |  super(codigo, nombre, precioBase, stock); |
    
|9| Pedido.java|42|The final field Pedido.pagado cannot be assigned  |public void marcarPagado(boolean pagado) {  |
        
|10|Pedido.java  | 47 | The local variable total may not have been initialized | double total = 0.0; |
       
|11|Pedido.java  | 84 |Syntax error on token ",", ; expected  |  for (int i = 0; i < lineas.size(); i++) { |
       
|12|ProductoMenu.java  | 48 |The assignment to variable disponible has no effect  |this.disponible = disponible;  |
       
|13|ProductoMenu.java  | 21 | The static field ProductoMenu.stock should be accessed in a static way | this.stock = stock;  |
      
|14| Brunch.java |52  | This method must return a result of type Mesa |  return null;  |
        
|15| Brunch.java |76  | ArrayList<Pedido> pedidos Source: examenfinalpoonutresa_feb9ba99 |pedidos.add(pedido);  |
      
|16| Brunch.java |107  | The method marcarPagado(boolean) in the type Pedido is not applicable for the arguments ()|  pedido.isPagado();  |
        
|17| Main.java |45 |The constructor Mesa(String, int) is undefined| brunch.agregarMesa(new Mesa(1, 2));  |

|18| Main.java |23 |Bebida cannot be resolved to a type| import modelo.Bebida; |

(las 15 filas)

## 2. Errores de lógica corregidos
| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
| 1 | | | | |
| 2 | | | | |
|  | | | | |
|  | | | | |
|  | | | | |
|  | | | | |
|  | | | | |
|  | | | | |
|  | | | | |
|  | | | | |
|  | | | | |

(las 10 filas)

## 3. Revisión de código
| # | Archivo y método | Por qué era una mala práctica | Qué cambiamos |
|---|------------------|-------------------------------|---------------|
(las 5 filas, de R1 a R5)

## 4. Salida final del programa
(pega aquí la salida completa de `java -cp out Main`, o una captura)

## 5. Conceptos de POO en este proyecto
En 2 o 3 líneas cada uno, expliquen DÓNDE se usa en este código:
- **Encapsulamiento:**
- **Herencia:**
- **Abstracción:**
- **Interfaz:** (hay dos, ¿qué clases implementa cada una?)
- **Polimorfismo:** (miren la sección 7 de Main y lo que hicieron en R1)