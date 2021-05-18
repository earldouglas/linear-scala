/*
rule = LinearTypes
*/
package fix

/**
 * Don't allow a [[Box]] field to be created but never dereferenced.
 */
trait UnusedField {
  val box: Box = // assert: LinearTypes
    Box(42)
}

/**
 * Don't allow a [[Box]] parameter to be declared but never dereferenced.
 */
trait UnusedParameter {
  def foo(x: Box, y: Box): Int = // assert: LinearTypes
    x.value
}

/**
 * Don't allow a [[Box]] method to be created but never dereferenced.
 */
trait UnusedMethod {
  def foo(): Box = // assert: LinearTypes
    Box(42)
}

/**
 * Don't allow a [[Box]] value to be created but never dereferenced.
 */
trait UnusedValue {
  def foo(): Unit = {
    val x: Box = Box(42) // assert: LinearTypes
  }
}

/**
 * Don't allow a [[Box]] binding in a for comprehension to be
 * created but never dereferenced.
 */
trait UnusedBinding {
  for {
    x <- Some(Box(6)) // assert: LinearTypes
    y <- Some(Box(7)) // assert: LinearTypes
    z <- Some(Box(42))
  } yield z
}
