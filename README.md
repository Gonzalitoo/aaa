# DifficultApp Backend (Rest Api)


### Como ejecutarlo sin IDE
*Antes de ejecutar los siguientes comandos se deberán tener encendidos servicios de 'mysql', 'mongod','redis','neo4j' (para ajustar contraseñas de las respectivas bases de datos se deberá ingresar al archivo aplication.yml)*
\
En un bash navegar hasta el path correspondiente al proyecto y luego ejecutar los siguientes comandos\
**./gradlew build** \
**./gradlew run**

## EndPoints disponibles

### Para el usuario

`POST /usuario/registrar`\
*Envia por body un usuario a registrar*\
**requestBody**:
>{\
&ensp;"apellido": "asd"\
&ensp;"contrasenia": "asd123"\
&ensp;"edad": 23\
&ensp;"imagen": " "\
&ensp;"nombre": "jorge"\
&ensp;"saldo": 1\
&ensp;"usuarioNombre": "Zeferino"\
>}

`PUT /usuario/ingresar`\
*Recibe usuario y contraseña por body y devuelve el id del usuario si si matchea de lo contrario, un error*\
**requestBody**:
>{\
&ensp; "usuarioNombre": "Zeferino"\
&ensp; "contrasenia": "asd123"\
>}

`GET /usuario/perfil/{uid}`\
*Recibe el id del usuario por path y devuelve un usuario*\

`PUT /usuario/perfil/{uid}/editar`\
*Recibe un usuario nuevo , el id de un usuario existente y pisa al usuario existente con los datos del nuevo usuario*\
**requestBody**:
>{\
&ensp;"apellido": "Chávez"\
&ensp;"nombre": "Zeferinas"\
&ensp;"saldo": 2300000\
>}

### Para los productos

`GET /producto/{id}`\
*Trae un producto que matchee con el id enviado por path*\

`GET /producto/traer`\
*Recibe 3 parametros opcionales por query params para realizar un filtrado, en el caso de que todos sean null devuelve todos los productos*\
**Parametros posibles:**

    /producto/traer?puntaje=5&paisDeOrigen=""&nombre=""
    donde puntaje es un numero entre 0 y 5,
    paisDeOrigen String con un pais y
    nombre es una String con el nombre correspondiente
     



### Para el carrito
`POST /carrito/{uid}/agregar`\
*Recibe un id del usuario por path y un producto (con lote y cantidad) por body*
**requestBody**:
>{\
&ensp;"cantidad": 1,\
&ensp;"loteId": 1,\
&ensp;"productoId": "62b752d67eb74678a83229a5"\
>}

`DELETE /carrito/{uid}/quitar/{itemId}`\
*Elimina el producto del carrito con el id del usuario y el id del producto por path*

`DELETE /carrito/{uid}/limpiar`\
*Borra todo el carrito recibe un id de usuario por path*

`GET /carrito/{uid}/items`\
*Trae todos los items del carrito de un usuario recibe su id por path*

`POST /carrito/{uid}/comprar`\
*Ejecuta la compra del carrito del usuario recibe su id por path*

### Para el clickLogger
`GET /logs/{id}`\
*Trae el producto mas clickeado por un usuario especifico recibe su id por path*
`POST /logs`\
*crea logs de clickeado*\
**requestBody**:
>{\
&ensp;"idProducto": "62b752d67eb74678a83229a5",\
&ensp;"idUsuario": "5",\
&ensp;"productoDescripcion": "Cerámica de interior",\
&ensp;"productoImagen": "i0QUlrl",\
&ensp;"productoNombre": "Cañuelas",\
&ensp;"productoValoracion": 0\
>}

`GET /logs/productosMasClickeados`\
*Trae el producto mas clickeado en general*
### Para las recomendaciones
`GET/producto/{pid}/usuario/{uid}"`\
*trae las recomendaciones de productos(Quienes vieron este producto también compraron)*\
*para un usuario al entrar a un producto pide id de producto y id de usuario por path*

## DER
![](https://i.imgur.com/AZF5yKi.png)

## MYSQL DER
![](https://i.imgur.com/qPlrocx.png)
