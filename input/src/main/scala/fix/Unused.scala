/*
rule = LinearTypes
*/
package fix

trait UnusedField {
  val box: Box = // assert: LinearTypes
    Box(42)
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

trait UnusedBinding {
  for {
    x <- Some(Box(6)) // assert: LinearTypes
    y <- Some(Box(7)) // assert: LinearTypes
    z <- Some(Box(42))
  } yield z
}
