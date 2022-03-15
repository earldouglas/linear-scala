import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear

trait FieldUsedTwice {
  val box: Box = Box(42)
  println(box) // error: box is used twice
  println(box) // error: box is used twice
}
