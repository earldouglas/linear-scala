/*
rule = LinearTypes
 */
package fix

/**
  * Allow a [[Box]] field to be dereferenced exactly once.
  */
trait FieldUsedOnce {
  val box: Box = Box(42)
  println(box)
}
