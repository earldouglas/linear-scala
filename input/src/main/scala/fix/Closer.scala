/*
rule = LinearTypes
 */
package fix

import com.earldouglas.linearscala.Linear
import java.io.Closeable
import java.io.RandomAccessFile

case class Closer[C <: Closeable, A](unsafeRun: C => A) {

  def run(c: C): A = {
    val a = unsafeRun(c)
    println("***CLOSING***")
    c.close()
    a
  }

  def flatMap[B](f: A => Closer[C, B]): Closer[C, B] =
    Closer { c: C =>
      val a: A = unsafeRun(c)
      f(a).unsafeRun(c)
    }

  def map[B](f: A => B): Closer[C, B] =
    flatMap { a: A =>
      Closer(_ => f(a))
    }
}

/** Don't allow a [[Linear]] file to be dereferenced more than once,
  * because it might already be closed.
  */
trait ReadFromClosedCloseableWithCloser {

  import java.nio.charset.StandardCharsets

  val readAndCloseFile: Closer[RandomAccessFile, String] =
    for {
      length <- Closer { f: RandomAccessFile => f.length() }
      content <- Closer { f: RandomAccessFile =>
        val buf: Array[Byte] = new Array(length.toInt)
        f.readFully(buf)
        new String(buf, StandardCharsets.UTF_8)
      }
    } yield content

  val f: RandomAccessFile with Linear =
    new RandomAccessFile("/etc/passwd", "r")
      .asInstanceOf[RandomAccessFile with Linear]

  readAndCloseFile.run(f) // assert: LinearTypes
  f.readLine() // assert: LinearTypes
}
