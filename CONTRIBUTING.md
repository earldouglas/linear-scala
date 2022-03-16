[![Build Status][build-badge]][build-link]
[![Release Artifacts][release-badge]][release-link]

[build-badge]: https://github.com/earldouglas/linear-scala/workflows/build/badge.svg "Build Status"
[build-link]: https://github.com/earldouglas/linear-scala/actions "GitHub Actions"
[release-link]: https://oss.sonatype.org/content/repositories/releases/com/earldouglas/linear-scala/ "Sonatype Releases"
[release-badge]: https://img.shields.io/nexus/r/https/oss.sonatype.org/com.earldouglas/linear-scala "Sonatype Releases"

# Contributing

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
$ export VERSION=0.0.1
$ sbt
> set ThisBuild / version := sys.env("VERSION")
> library/publishSigned
> +rules/publishSigned
> plugin/publishSigned
> sonatypeBundleRelease
$ git tag $VERSION
$ git push --tags
```

## References

### Scalafix

* https://scalacenter.github.io/scalafix/docs/developers/tutorial.html
* https://www.javadoc.io/doc/ch.epfl.scala/scalafix-core_2.13/latest/scalafix/index.html

### Scalameta

* https://scalameta.org/docs/trees/guide.html
* https://scalameta.org/docs/semanticdb/guide.html
* https://www.javadoc.io/doc/org.scalameta/trees_2.13/latest/scala/meta/index.html
