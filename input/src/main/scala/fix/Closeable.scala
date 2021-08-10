/*
rule = LinearTypes
 */
package fix

import com.earldouglas.linearscala.Linear
import java.io.RandomAccessFile

/** Don't allow a [[Linear]] file to be dereferenced more than once,
  * because it might already be closed.
  */
trait ReadFromClosedCloseable {

  def readLineAndCloseFile(
      f: RandomAccessFile
  ): String = {
    val line = f.readLine()
    f.close()
    line
  }

  val f: RandomAccessFile with Linear =
    new RandomAccessFile("/etc/passwd", "r")
      .asInstanceOf[RandomAccessFile with Linear]

  readLineAndCloseFile(f) // assert: LinearTypes
  f.readLine() // assert: LinearTypes
}
