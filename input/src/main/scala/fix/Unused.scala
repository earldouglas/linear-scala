/*
rule = LinearTypes
 */
package fix

import com.earldouglas.linearscala.Linear

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
  def foo(
      x: Box,
      y: Box // assert: LinearTypes
  ): Int =
    x.value
}

/**
  * Don't allow a [[Box]] method to be created but never called.
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

/**
  * Don't allow a field with a [[Linear]] structural type to be
  * created but never dereferenced.
  */
trait UnusedFieldWithStructuralType {
  val x: Int with Linear = // assert: LinearTypes
    42.asInstanceOf[Int with Linear]
}

/**
  * Don't allow a parameter with a [[Linear]] structural type to be
  * declared but never dereferenced.
  */
trait UnusedParameterWithStructuralType {
  def foo(
      x: Int,
      y: Int with Linear // assert: LinearTypes
  ): Int =
    42
}
