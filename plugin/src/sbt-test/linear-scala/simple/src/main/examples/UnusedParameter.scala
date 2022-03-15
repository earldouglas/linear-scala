import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear

trait UnusedParameter {
  def foo(x: Box, y: Box): Int = // error: y is never used
    x.value
}
