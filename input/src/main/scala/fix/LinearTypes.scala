/*
rule = LinearTypes
*/
package fix

import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear

trait UnusedField {
  val box: Box = Box(42) // assert: LinearTypes
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
  def foo(): Box = Box(42) // assert: LinearTypes
}

trait UnusedValue {
  def foo(): Unit = {
    val x: Box = Box(42)
    println(x) // assert: LinearTypes
    println(x) // assert: LinearTypes
  }
}
