/*
rule = LinearTypes
 */
package fix

import com.earldouglas.linearscala.Linear
import java.io.RandomAccessFile

class Affine[E <: Linear, A](val run: E => A) {

  def flatMap[B](f: A => Affine[E, B]): Affine[E, B] =
    new Affine({ e: E =>
      val a: A = run(e)
      f(a).run(e)
    })

  def map[B](f: A => B): Affine[E, B] =
    flatMap { a: A =>
      new Affine(_ => f(a))
    }
}

class AffineConstructor[E] {
  def apply[A](run: E => A): Affine[E with Linear, A] =
    new Affine(run.asInstanceOf[E with Linear => A])
}

object Affine {
  def apply[E]: AffineConstructor[E] =
    new AffineConstructor
}

/** Don't allow a [[Linear]] file to be dereferenced more than once,
  * except for where we need to.
  */
trait ReadFromLinearWithAffine {

  import java.nio.charset.StandardCharsets

  val readAndCloseFile: Affine[RandomAccessFile with Linear, String] =
    for {
      length <- Affine[RandomAccessFile] { f => f.length() }
      content <- Affine[RandomAccessFile] { f =>
        val buf: Array[Byte] = new Array(length.toInt)
        f.readFully(buf)
        new String(buf, StandardCharsets.UTF_8)
      }
      _ <- Affine[RandomAccessFile] { f => f.close() }
    } yield content

  val f: RandomAccessFile with Linear =
    new RandomAccessFile("/etc/passwd", "r")
      .asInstanceOf[RandomAccessFile with Linear]

  readAndCloseFile.run(f) // assert: LinearTypes
  f.readLine() // assert: LinearTypes
}
