package taller

import scala.annotation.tailrec

/**
 * Taller 1 — cifrados clásicos con recursión.
 *
 * Solo se cifran las 26 letras minúsculas del alfabeto inglés; cualquier otro
 * carácter se copia sin cambio.
 */
class CifradosClasicos {

  type Mensaje = String
  type Clave = String

  // Una frecuencia asocia cada letra con las veces que aparece.
  type Frecuencias = List[(Char, Int)]

  val letras = 26
  val primera = 'a'.toInt

  def esMinuscula(c: Char): Boolean = c >= 'a' && c <= 'z'

  // Punto 1 -------------------------------------------------------------------

  /** César con recursión lineal: una operación pendiente por letra. */
   def cesar(m: Mensaje, k: Int): Mensaje = {
    val desplazamiento = ((k % letras) + letras) % letras

    if (m.isEmpty) {
      ""
    } else {
      val letra = m.head
      val resto = m.tail

      if (esMinuscula(letra)) {
        val posicion = letra.toInt - primera
        val nueva = ((posicion + desplazamiento) % letras + primera).toChar

        nueva.toString + cesar(resto, k)
      } else {
        letra.toString + cesar(resto, k)
      }
    }
   }

  // Punto 2 -------------------------------------------------------------------

  /**
   * El mismo César como proceso iterativo: espacio constante.
   * Cuando la función esté escrita, anótela con @tailrec: el compilador
   * comprueba que la llamada recursiva sea lo último que hace.
   */
  @tailrec
  final def cesarCola(m: Mensaje, k: Int, acc: Mensaje = ""):Mensaje = {
    val desplazamiento = ((k % letras) + letras) % letras

    if (m.isEmpty) {
      acc
    } else {
      val letra = m.head
      val resto = m.tail

      if (esMinuscula(letra)) {
        val posicion = letra.toInt - primera
        val nueva = ((posicion + desplazamiento) % letras + primera).toChar

        cesarCola(resto, k, acc + nueva)
      } else {
        cesarCola(resto, k, acc + letra)
      }
    }
  }

  // Punto 3 -------------------------------------------------------------------

  /**
   * Cuenta las letras minúsculas del mensaje, de mayor a menor frecuencia y,
   * en empate, en orden alfabético. El recorrido es recursivo de cola.
   */
  def frecuencias(m: Mensaje): Frecuencias = {
    @tailrec
    def contar(mensaje: Mensaje, lista: Frecuencias): Frecuencias = {
      if (mensaje.isEmpty) {
        lista
      } else {
        val letra = mensaje.head
        val resto = mensaje.tail

        if (esMinuscula(letra)) {
          val encontrada = lista.find(_._1 == letra)

          encontrada match {
            case Some((_, cantidad)) =>
              val nuevaLista = lista.map {
                case (c, n) if c == letra => (c, n + 1)
                case par => par
              }
              contar(resto, nuevaLista)

            case None =>
              contar(resto, lista :+ (letra, 1))
          }
        } else {
          contar(resto, lista)
        }
      }
    }

    contar(m, List.empty).sortBy(par => (-par._2, par._1))
  }

  // Punto 4 -------------------------------------------------------------------

  /**
   * Supone que la letra más frecuente del mensaje cifrado es la 'e' del
   * original y devuelve la distancia entre las dos. Sin letras, cero.
   */
  def desplazamientoProbable(m: Mensaje): Int = ???

  def romperCesar(m: Mensaje): Mensaje = ???

  // Punto 5 -------------------------------------------------------------------

  /**
   * Cuántos mensajes de longitud n se forman con a letras sin dos iguales
   * seguidas.
   */
  def combinaciones(n: Int, a: Int): BigInt = ???

  /**
   * Vigenère: cada letra se corre según la letra de la clave que le toca. Lo
   * que no es letra minúscula se copia y no consume clave.
   */
  def vigenere(m: Mensaje, clave: Clave): Mensaje = ???
}
