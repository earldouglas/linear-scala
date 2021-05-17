/*
rule = LinearTypes
*/
package fix

trait FieldUsedOnce {
  val box: Box = Box(42)
  println(box)
}
