[![Build Status][build-badge]][build-link]
[![Release Artifacts][release-badge]][release-link]

[build-badge]: https://github.com/earldouglas/linear-scala/workflows/build/badge.svg "Build Status"
[build-link]: https://github.com/earldouglas/linear-scala/actions "GitHub Actions"
[release-link]: https://oss.sonatype.org/content/repositories/releases/com/earldouglas/linear-scala/ "Sonatype Releases"
[release-badge]: https://img.shields.io/nexus/r/https/oss.sonatype.org/com.earldouglas/linear-scala "Sonatype Releases"

# linear-scala

linear-scala adds support for linear types in Scala via a custom
Scalafix linter.

## Setup

*project/plugins.sbt:*

```scala
addSbtPlugin("com.earldouglas" % "sbt-linear-scala" % "0.0.3")
```

## Usage

Mix in the `Linear` interface to prevent values from being
under/over-used.
 
```scala
import com.earldouglas.linearscala.Linear

case class Box(value: Int) extends Linear
```

Scalafix finds values that are never used:

```scala
trait UnusedField {
  val box: Box = Box(42) // error: box is never used
}

trait UnusedParameter {
  def foo(x: Box, y: Box): Int = // error: y is never used
    x.value
}
```

Scalafix also finds values that are used multiple times:

```scala
trait FieldUsedTwice {
  val box: Box = Box(42)
  println(box) // error: box is used twice
  println(box) // error: box is used twice
}
```

See the tests in [input/src/main/scala/fix/](input/src/main/scala/fix/)
for more examples.

## References

### Linear Types

* <https://earldouglas.com/talks/linear-scala.html>
* <https://gitlab.haskell.org/ghc/ghc/-/wikis/linear-types>
* <https://en.wikipedia.org/wiki/Substructural_type_system#Linear_type_systems>
* <https://github.com/ryanorendorff/lc-2018-linear-types>
