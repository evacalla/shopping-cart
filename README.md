# Shopping Cart

Un market place que permita genera carritos, agregar, quitar, productos, cambiar el estado de carrito y un proceso que permita de manera
simultanea descontar el stock con respecto a los productos asociados al carrito.


### Pre-requisitos 📋

```
Apache Maven 3.6.3
Java 1.8
```
### Instalación 🔧

_Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de pruebas._

```
mvn install
```

_Y_

```
mvn spring-boot:run
```


## API Endpoint ⚙️

- **<code>POST</code> /carts**
- **<code>POST</code> /carts/:id/products**
- **<code>DELETE</code> /carts/:id/products/:productId**
- **<code>GET</code> /carts/:id/products**
- **<code>GET</code> /carts/:id**
- **<code>POST</code> /carts/:id/checkout**



## Construido con 🛠️

_Herramientas utilizadas_

* [Spring boot](https://spring.io/guides) - El framework web usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Akka](https://akka.io/docs/) - Sistema de actores



## Autor ✒️

* **Enzo Vacalla Gismondi** - [evacalla](https://github.com/evacalla)

