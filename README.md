[![Build Status][build-badge]][build-link]
[![Release Artifacts][release-badge]][release-link]

# linear-scala

linear-scala adds support for linear types in Scala via a custom
Scalafix linter.

## Setup

Configure your project to use Scalafix with SemanticDB enabled.

*project/plugins.sbt:*

```
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.27")
```

*build.sbt*:

```scala
lazy val V = _root_.scalafix.sbt.BuildInfo

libraryDependencies += "com.earldouglas" % "linear-scala" % "0.0.1"

inThisBuild(
  List( scalaVersion := V.scala213
      , scalafixDependencies += "com.earldouglas" %% "linear-scala-scalafix" % "0.0.1"
      , scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value)
      , semanticdbEnabled := true // enable SemanticDB
      , semanticdbVersion := scalafixSemanticdb.revision // use Scalafix compatible version
      )
)

scalafixOnCompile := true
```

*.scalafix.conf*:

```
rules = [
  LinearTypes
]
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

## Testing

Using scalafix-testkit:

```
$ sbt
> tests/test
[info] All tests passed.
```

## Publishing

Two modules are published from this project:

* *linear-scala*: a standard dependency providing the `Linear` type
* *linear-scala-scalafix*: a Scalafix dependency providing the
  `LinearTypes` rule

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

[build-badge]: https://github.com/earldouglas/linear-scala/workflows/build/badge.svg "Build Status"
[build-link]: https://github.com/earldouglas/linear-scala/actions "GitHub Actions"
[release-link]: https://oss.sonatype.org/content/repositories/releases/com/earldouglas/linear-scala/ "Sonatype Releases"
[release-badge]: https://img.shields.io/nexus/r/https/oss.sonatype.org/com.earldouglas/linear-scala "Sonatype Releases"
