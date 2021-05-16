package fix

import scala.meta.Position
import scala.meta.Term
import scalafix.lint.Diagnostic
import scalafix.v1._

case class MultipleDerefs(t: Term, count: Int)(implicit doc: SemanticDocument) extends Diagnostic {
  override def position: Position = t.pos
  override def message: String =
    s"${t.symbol.displayName} is used ${count} times"
}

case class ZeroDerefs(t: Term)(implicit doc: SemanticDocument) extends Diagnostic {
  override def position: Position = t.pos
  override def message: String =
    s"${t.symbol.displayName} is never used"
}

final class LinearTypes extends SemanticRule("LinearTypes") {

  override val isLinter: Boolean = true
  override val isRewrite: Boolean = false

  // Try to figure out if the symbol represents a value that extends Linear
  private def isALinear(symbol: Symbol)(implicit doc: SemanticDocument): Boolean =
    symbol.info match {
      case Some(info) =>
        info.signature match {
          case ClassSignature(typeParameters, parents, self, declarations) =>
            parents.find {
              case TypeRef(prefix, symbol, typeArguments) if symbol.value == "com/earldouglas/linearscala/Linear#" => true
              case _ => false
            } match {
              case Some(parent) => true
              case None => false
            }
          case ValueSignature(TypeRef(prefix, symbol, typeArguments)) => isALinear(symbol)
          case MethodSignature(typeParameters, parameterLists, returnType) =>
            returnType match {
              case TypeRef(prefix, symbol, typeArguments) => isALinear(symbol)
              case _ => false
            }
          case _ => false
        }
      case None => false
    }

  // Find all the terms that dereference a Linear value
  private def findDerefs(implicit doc: SemanticDocument): Map[Symbol, List[Term]] =
    doc.tree.collect {
      case t @ Term.Name(name) if t.isReference && isALinear(t.symbol) => t
    }
    .groupBy(_.symbol)

  // Find all the terms that are references to a Linear value
  private def findRefs(implicit doc: SemanticDocument): Iterable[Term] =
    doc.tree.collect {
      case t @ Term.Name(name) if !t.isReference && isALinear(t.symbol) => t
    }

  override def fix(implicit doc: SemanticDocument): Patch = {

    val derefs: Map[Symbol, List[Term]] = findDerefs(doc)
    val refs: Iterable[Term] = findRefs(doc)

    // Find all Linear values that are used multiple times
    val overused: Iterable[Diagnostic] =
      derefs
        .filter(_._2.length > 1)
        .values
        .flatMap(ts => ts.map(t => MultipleDerefs(t, ts.length)))

    // Find all Linear values that are never used
    val underused: Iterable[Diagnostic] =
     refs
       .filterNot(t => derefs.keySet.contains(t.symbol))
       .map(t => ZeroDerefs(t))

    // Convert the above diagnostics into a Patch
    (overused ++ underused)
      .map(Patch.lint)
      .asPatch
  }
}
