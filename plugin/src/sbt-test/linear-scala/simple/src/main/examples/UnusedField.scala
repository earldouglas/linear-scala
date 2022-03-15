import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear

trait UnusedField {
  val box: Box = Box(42) // error: box is never used
}
