package tests

import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear

trait UnusedField {
  val box: Box = Box(42) // box is never used
}

trait FieldUsedOnce {
  val box: Box = Box(42)
  println(box)
}

trait FieldUsedTwice {
  val box: Box = Box(42)
  println(box) // box is used twice
  println(box) // box is used twice
}

trait UnusedParameter {
  def foo(x: Box, y: Box): Int = // y is never used
    x.value
}

trait UnusedMethod {
  def foo(): Box = Box(42) // foo is never used
}

trait UnusedValue {
  def foo(): Unit = {
    val x: Box = Box(42)
    println(x) // x is used twice
    println(x) // x is used twice
  }
}
