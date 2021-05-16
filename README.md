# linear-scala

linear-scala adds support for linear types in Scala via a custom
Scalafix linter.

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

## Usage

See the [example project](example/).

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
