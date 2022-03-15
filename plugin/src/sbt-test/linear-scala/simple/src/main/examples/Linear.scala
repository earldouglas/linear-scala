import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear

object IsLinear extends App {
  def identity(x: Box): Box = x
  println(identity(Box(42)))
}
