package tests

import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear

trait UnusedField {
  val box: Box = Box(42)
}

trait FieldUsedOnce {
  val box: Box = Box(42)
  println(box)
}

trait FieldUsedTwice {
  val box: Box = Box(42)
  println(box)
  println(box)
}

trait UnusedParameter {
  def foo(x: Box, y: Box): Int =
    x.value
}

trait UnusedMethod {
  def foo(): Box = Box(42)
}

trait UnusedValue {
  def foo(): Unit = {
    val x: Box = Box(42)
    println(x)
    println(x)
  }
}
