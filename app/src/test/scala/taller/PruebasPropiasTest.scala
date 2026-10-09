package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PruebasPropiasTest extends AnyFunSuite {

  val c = new CifradosClasicos()
  import c._

  // ---------- Punto 1: cesar (recursion lineal) ----------

  test("cesar: corre cada letra 4 posiciones y se pasa de la z") {
    assert(cesar("zorro", 4) == "dsvvs")
  }

  test("cesar: desplazamiento negativo") {
    assert(cesar("hola", -1) == "gnkz")
  }

  test("cesar: digitos, espacios y signos pasan sin cambio") {
    assert(cesar("a1 b2!", 2) == "c1 d2!")
  }

  test("cesar: un desplazamiento de 52 deja el mensaje igual") {
    assert(cesar("xyz", 52) == "xyz")
  }

  test("cesar: desplazamiento negativo mayor que 26 en valor absoluto") {
    assert(cesar("abc", -27) == "zab")
  }

  // ---------- Punto 2: cesarCola (recursion de cola) ----------

  test("cesarCola: frase con espacio") {
    assert(cesarCola("quiero cafe", 5) == "vznjwt hfkj")
  }

  test("cesarCola: mensaje vacio") {
    assert(cesarCola("", 7) == "")
  }

  test("cesarCola: desplazamiento negativo") {
    assert(cesarCola("tarde", -5) == "ovmyz")
  }

  test("cesarCola: mensaje muy largo no desborda la pila") {
    assert(cesarCola("a" * 20000, 1) == "b" * 20000)
  }

  test("cesarCola: cifrar y descifrar devuelve el original") {
    val original = "hola, mundo 2026!"
    assert(cesarCola(cesarCola(original, 9), -9) == original)
  }

  // ---------- Punto 3: frecuencias ----------

  test("frecuencias: banana") {
    assert(frecuencias("banana") == List(('a', 3), ('n', 2), ('b', 1)))
  }

  test("frecuencias: una sola letra repetida") {
    assert(frecuencias("zzzz") == List(('z', 4)))
  }

  test("frecuencias: empates de frecuencia se ordenan alfabeticamente") {
    assert(frecuencias("mississippi") == List(('i', 4), ('s', 4), ('p', 2), ('m', 1)))
  }

  test("frecuencias: todas con una aparicion salen en orden alfabetico") {
    assert(frecuencias("dcba") == List(('a', 1), ('b', 1), ('c', 1), ('d', 1)))
  }

  test("frecuencias: las mayusculas no se cuentan") {
    assert(frecuencias("AaBb") == List(('a', 1), ('b', 1)))
  }

  // ---------- Punto 4: desplazamientoProbable y romperCesar ----------

  test("desplazamientoProbable: si la mas frecuente es la e, el desplazamiento es 0") {
    assert(desplazamientoProbable("eeee") == 0)
  }

  test("desplazamientoProbable: la x es la mas frecuente") {
    assert(desplazamientoProbable("xxyz") == 19)
  }

  test("desplazamientoProbable: la distancia a la e se calcula modulo 26") {
    assert(desplazamientoProbable("bbbc") == 23)
  }

  test("romperCesar: recupera un mensaje donde la e es la mas frecuente") {
    val original = "eres el mejor de la sala"
    assert(romperCesar(cesar(original, 5)) == original)
  }

  test("romperCesar: falla cuando la e no es la letra mas frecuente") {
    // en "lalala" gana la a por empate, asi que la estimacion es 25 y no 3
    assert(romperCesar(cesar("lalala", 3)) == "pepepe")
  }

  // ---------- Punto 5: combinaciones y vigenere ----------

  test("combinaciones: 3 letras y mensajes de longitud 4") {
    assert(combinaciones(4, 3) == BigInt(24))
  }

  test("combinaciones: con una sola letra no hay mensajes de longitud 5") {
    assert(combinaciones(5, 1) == BigInt(0))
  }

  test("combinaciones: resultado que no cabe en un Int") {
    assert(combinaciones(30, 26) == BigInt(26) * BigInt(25).pow(29))
  }

  test("vigenere: la clave se repite sobre el mensaje") {
    assert(vigenere("zebra", "abc") == "zfdrb")
  }

  test("vigenere: los caracteres que no son letras no consumen la clave") {
    assert(vigenere("a, a", "bc") == "b, c")
  }
}