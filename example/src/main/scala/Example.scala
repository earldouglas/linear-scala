package example

import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear

trait UnusedField {
  val box: Box = // error: box is never used
    Box(42)
}

trait FieldUsedOnce {
  val box: Box = Box(42)
  println(box)
}

trait FieldUsedTwice {
  val box: Box = Box(42)
  println(box) // error: box is used twice
  println(box) // error: box is used twice
}

trait UnusedParameter {
  def foo(x: Box, y: Box): Int = // error: y is never used
    x.value
}

trait UnusedMethod {
  def foo(): Box = // error: foo is never used
    Box(42)
}

trait UnusedValue {
  def foo(): Unit = {
    val x: Box = Box(42)
    println(x) // error: x is used twice
    println(x) // error: x is used twice
  }
}
