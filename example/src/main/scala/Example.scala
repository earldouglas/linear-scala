package tests

import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear

object Example {

  val x: Box = Box(1)
  println(x)
  println(x)

  val y: Box = Box(2)

  def foo(a: Box, b: Box): Box = a
  /*

  def foo(): Unit = {
    val y: Box = Box(2)
    println(y)
    println(y)
  }
  */
}
