/*
rule = LinearTypes
*/
package fix

trait FieldUsedTwice {
  val box: Box = Box(42)
  println(box) // assert: LinearTypes
  println(box) // assert: LinearTypes
}

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
