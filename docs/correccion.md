# Informe de correccion

Fundamentos de Programacion Funcional y Concurrente.

## 1. Funcion cesar

La funcion `cesar(m, k)` cifra un mensaje cambiando cada letra minuscula segun el desplazamiento indicado. Los demas caracteres se dejan iguales.

### Caso base

Cuando el mensaje esta vacio, la funcion devuelve una cadena vacia:

\[
C("",k) = ""
\]

Esto es correcto porque no hay caracteres que cifrar.

### Paso de induccion

Supongamos que la funcion cifra correctamente cualquier mensaje de longitud menor que \(n\). Ahora debemos comprobar que tambien funciona para un mensaje de longitud \(n\).

Sea el mensaje \(m = c + r\), donde \(c\) es el primer caracter y \(r\) es el resto del mensaje.

Si \(c\) es una letra minuscula, se calcula la nueva letra usando el desplazamiento \(k\):

\[
c' = \text{a} + ((c-\text{a}+k)\bmod 26)
\]

Si el caracter no es una letra minuscula, se conserva sin cambios.

La funcion despues llama a `cesar` con el resto \(r\). Por la suposicion de induccion, esa llamada cifra correctamente el resto del mensaje. Al unir el primer caracter cifrado con el resultado del resto, se obtiene el mensaje completo cifrado correctamente.

Por lo tanto, `cesar` funciona correctamente para mensajes de cualquier longitud.

## 2. Funcion cesarCola

La funcion `cesarCola(m, k, acc)` cifra el mensaje igual que `cesar`, pero guarda los caracteres procesados en el acumulador `acc`.

### Caso base

Cuando el mensaje esta vacio, devuelve el acumulador:

\[
CC("",k,acc)=acc
\]

El acumulador contiene el resultado de procesar todos los caracteres anteriores.

### Paso de induccion

Supongamos que el acumulador contiene correctamente el cifrado de los caracteres ya procesados.

La funcion toma el primer caracter del mensaje. Si es una letra minuscula, lo cifra; en caso contrario, lo conserva. Luego agrega ese caracter al acumulador y llama a `cesarCola` con el resto del mensaje.

En cada paso, el acumulador conserva el resultado parcial correcto. Cuando el mensaje queda vacio, devuelve el resultado completo.

Por lo tanto, `cesarCola` produce el mismo resultado que `cesar` cuando se utiliza el mismo mensaje y desplazamiento.

La diferencia es que `cesarCola` utiliza recursion de cola, porque la llamada recursiva es la ultima operacion. La anotacion `@tailrec` permite comprobar esta condicion en Scala.

## 3. Funcion frecuencias

La funcion `frecuencias(m)` cuenta cuantas veces aparece cada letra minuscula en el mensaje. Al final ordena la lista desde la mayor frecuencia hasta la menor y, si hay un empate, utiliza el orden alfabetico.

### Caso base

Cuando no quedan caracteres por revisar, la funcion interna `contar` devuelve la lista acumulada:

\[
F("",L)=L
\]

La lista contiene las frecuencias de los caracteres que ya se procesaron.

### Paso de induccion

Supongamos que la lista \(L\) contiene las frecuencias correctas de los caracteres que ya se revisaron.

Al tomar el siguiente caracter, pueden ocurrir tres situaciones:

- Si no es una letra minuscula, la lista no cambia.
- Si es una letra que ya esta en la lista, su frecuencia aumenta en uno.
- Si es una letra que no esta en la lista, se agrega con frecuencia uno.

En cada caso, la lista queda con las frecuencias correctas para los caracteres procesados. La funcion continua con el resto del mensaje hasta terminar.

Finalmente, ordena la lista por frecuencia descendente y por caracter ascendente cuando hay empates.

Por lo tanto, `frecuencias` devuelve las cantidades correctas de las letras minusculas del mensaje, ordenadas segun las condiciones indicadas.

## 4. Funcion desplazamientoProbable

La funcion `desplazamientoProbable(m)` utiliza la letra mas frecuente del mensaje para calcular un desplazamiento probable, suponiendo que esa letra corresponde a la `e` del mensaje original.

### Caso base

Si la lista de frecuencias esta vacia, la funcion devuelve cero:

\[
D(m)=0
\]

Esto sucede cuando no hay letras minusculas para analizar.

### Caso general

Si existe al menos una letra, se toma la primera de la lista ordenada de frecuencias y se calcula:

\[
D(m)=(c-\text{e}+26)\bmod 26
\]

donde \(c\) es la letra mas frecuente.

La funcion realiza correctamente este calculo segun la regla que utiliza. Sin embargo, el desplazamiento obtenido no siempre es el que se uso para cifrar el mensaje, porque la letra mas frecuente no siempre corresponde a la `e` original.

Por lo tanto, la funcion calcula un desplazamiento probable, pero no garantiza que sea el correcto.

## 5. Funcion romperCesar

La funcion `romperCesar(m)` utiliza el desplazamiento obtenido por `desplazamientoProbable` y lo aplica en sentido contrario con la funcion `cesar`.

Su procedimiento se puede expresar asi:

\[
R(m)=C(m,-D(m))
\]

Primero calcula el desplazamiento probable. Despues cifra el mensaje con el desplazamiento negativo para intentar recuperar el texto original.

Si el desplazamiento calculado es el correcto, la funcion recupera las letras originales. Si el desplazamiento es incorrecto, el resultado tambien puede ser incorrecto.

Por ejemplo, si el mensaje original es `aaaa` y se cifra con desplazamiento 3, se obtiene `dddd`. La funcion puede suponer que la `d` corresponde a la `e` original y calcular un desplazamiento de 25. Al aplicar el desplazamiento contrario, obtiene `eeee`, no `aaaa`.

Por lo tanto, `romperCesar` sigue el procedimiento definido en el codigo, pero no garantiza recuperar cualquier mensaje original.

## 6. Funcion combinaciones

La funcion `combinaciones(n, a)` calcula la cantidad de cadenas de longitud \(n\) que se pueden formar con \(a\) letras sin repetir la misma letra dos veces seguidas.

### Casos base

Para \(n=0\), devuelve uno:

\[
C(0,a)=1
\]

Esto representa la cadena vacia.

Para \(n=1\), devuelve \(a\):

\[
C(1,a)=a
\]

Esto se debe a que cualquiera de las \(a\) letras puede ocupar la unica posicion.

### Paso de induccion

Para \(n\geq 2\), la funcion utiliza la formula:

\[
C(n,a)=(a-1)C(n-1,a)
\]

Supongamos que \(C(k,a)\) calcula correctamente la cantidad de cadenas de longitud \(k\), donde \(k\geq 1\).

Para una cadena de longitud \(k+1\), se puede elegir cualquiera de las \(a\) letras para la primera posicion. Para cada posicion siguiente hay \(a-1\) opciones, porque no se puede repetir la letra anterior.

Por eso, el numero de cadenas de longitud \(k+1\) es:

\[
C(k+1,a)=(a-1)C(k,a)
\]

Como la funcion aplica esta formula y reduce el valor de \(n\) en cada llamada, llega a uno de los casos base y calcula el resultado siguiendo la recurrencia.

Por lo tanto, la funcion calcula correctamente las cantidades para los valores definidos en el codigo, con \(a\geq 1\) y \(n\geq 0\).

Ademas, utiliza `BigInt` para trabajar con resultados enteros grandes.

## 7. Funcion vigenere

La funcion `vigenere(m, clave)` cifra cada letra minuscula utilizando la letra correspondiente de la clave. El acumulador guarda el resultado y el indice indica que posicion de la clave se debe utilizar.

### Caso especial

Si la clave esta vacia, la funcion devuelve el mensaje original sin cambios.

### Caso base

Cuando el resto del mensaje esta vacio, devuelve el acumulador:

\[
V("",clave,i,acc)=acc
\]

Esto significa que todos los caracteres ya fueron procesados.

### Paso de induccion

Supongamos que el acumulador contiene correctamente el resultado de los caracteres procesados y que el resto del mensaje se puede cifrar correctamente.

Si el primer caracter es una letra minuscula, la funcion obtiene el desplazamiento a partir de la letra correspondiente de la clave y lo cifra. Luego agrega el resultado al acumulador y aumenta el indice de la clave.

Si el caracter no es una letra minuscula, lo agrega sin cambios y mantiene el mismo indice. De esta forma, los espacios y otros caracteres no consumen letras de la clave.

Cuando el indice llega al final de la clave, se utiliza el modulo para volver a su primera posicion.

Por la suposicion de induccion, el resto del mensaje se procesa correctamente. Al agregar el caracter actual al acumulador, se mantiene correcto el resultado parcial.

Por lo tanto, `vigenere` cifra las letras minusculas segun la clave y conserva los demas caracteres, como indica el codigo.

## 8. Conclusion

Las funciones del taller utilizan recursion para cifrar mensajes, contar frecuencias y calcular cantidades.

La correccion de `cesar` se justifica porque cifra el primer caracter y luego cifra correctamente el resto del mensaje. `cesarCola` obtiene el mismo resultado utilizando un acumulador. `frecuencias` mantiene las cantidades correctas durante el recorrido, mientras que `vigenere` conserva el resultado parcial y utiliza la clave en el orden correspondiente.

La funcion `combinaciones` sigue una formula recursiva con casos base definidos. Por otro lado, `desplazamientoProbable` y `romperCesar` realizan el procedimiento programado, pero su resultado depende de una suposicion sobre la letra mas frecuente.

Estas explicaciones permiten justificar como trabajan las funciones y cuales son sus limitaciones.