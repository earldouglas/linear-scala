/*
rule = LinearTypes
*/
package fix

import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear

trait UnusedField {
  val box: Box = // assert: LinearTypes
    Box(42)
}

trait FieldUsedOnce {
  val box: Box = Box(42)
  println(box)
}

trait FieldUsedTwice {
  val box: Box = Box(42)
  println(box) // assert: LinearTypes
  println(box) // assert: LinearTypes
}

trait UnusedParameter {
  def foo(x: Box, y: Box): Int = // assert: LinearTypes
    x.value
}

trait UnusedMethod {
  def foo(): Box = // assert: LinearTypes
    Box(42)
}

trait UnusedValue {
  def foo(): Unit = {
    val x: Box = Box(42)
    println(x) // assert: LinearTypes
    println(x) // assert: LinearTypes
  }
}
