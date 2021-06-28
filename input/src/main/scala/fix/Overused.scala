/*
rule = LinearTypes
 */
package fix

import com.earldouglas.linearscala.Linear

/**
  * Don't allow a [[Box]] field to be dereferenced more than once.
  */
trait FieldUsedTwice {
  val box: Box = Box(42)
  println(box) // assert: LinearTypes
  println(box) // assert: LinearTypes
}

/**
  * Don't allow a [[Box]] binding in a for comprehension to be
  * dereferenced more than once.
  */
trait BindingUsedTwice {
  for {
    x <- Some(Box(6))
    y <- Some(Box(x.value + 1)) // assert: LinearTypes
    z <- Some(Box(x.value * y.value)) // assert: LinearTypes
  } yield z

  for {
    x <- Some(Box(6))
    y <- Some(Box(7))
    z <- Some(Box(x.value * y.value)) // assert: LinearTypes
  } yield (x, y, z) // assert: LinearTypes
}

/**
  * Don't allow a field with a [[Linear]] structural type to be
  * dereferenced more than once.
  */
trait FieldWithStructuralTypeUsedTwice {
  val x: Int with Linear = 42.asInstanceOf[Int with Linear]
  println(x) // assert: LinearTypes
  println(x) // assert: LinearTypes
}
