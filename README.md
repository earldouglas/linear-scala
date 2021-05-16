[![Build status](https://github.com/earldouglas/linear-scala/workflows/build/badge.svg)](https://github.com/earldouglas/linear-scala/actions)
![Latest version](https://img.shields.io/github/tag/earldouglas/linear-scala.svg)

# linear-scala

linear-scala adds support for linear types in Scala via a custom
Scalafix linter.

## Usage

```scala
trait UnusedField {
  val box: Box = Box(42) // box is never used
}

trait FieldUsedTwice {
  val box: Box = Box(42)
  println(box) // box is used twice
  println(box) // box is used twice
}

trait UnusedParameter {
  def foo(x: Box, y: Box): Int = // y is never used
    x.value
}

trait UnusedMethod {
  def foo(): Box = Box(42) // foo is never used
}
```

See the [example project](example/) for more.

## Testing

Using scalafix-testkit:

```
$ sbt tests/test
> tests/test
[info] All tests passed.
```

## Publishing

Two artifacts are published:

* *linear-scala*: a standard dependency providing the `Linear` type
* *linear-scala-scalafix*: a Scalafix dependency providing the
  `LinearTypes` rule

## Publishing

```
$ sbt
> set ThisBuild / version := "0.0.1"
> library/publishSigned
> +rules/publishSigned
> sonatypeBundleRelease
```

## References

### Scalafix

* https://scalacenter.github.io/scalafix/docs/developers/tutorial.html
* https://www.javadoc.io/doc/ch.epfl.scala/scalafix-core_2.13/latest/scalafix/index.html

### Scalameta

* https://scalameta.org/docs/trees/guide.html
* https://scalameta.org/docs/semanticdb/guide.html
* https://www.javadoc.io/doc/org.scalameta/trees_2.13/latest/scala/meta/index.html

### Linear Types

* https://gitlab.haskell.org/ghc/ghc/-/wikis/linear-types
* https://en.wikipedia.org/wiki/Substructural_type_system#Linear_type_systems
* https://github.com/ryanorendorff/lc-2018-linear-types
