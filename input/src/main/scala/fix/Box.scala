/*
rule = LinearTypes
 */
package fix

import com.earldouglas.linearscala.Linear

/** Mix in the [[Linear]] interface to mark instances for use exactly
  * once.
  */
case class Box(value: Int) extends Linear
