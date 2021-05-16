This project uses `scalafixOnCompile := true` to run Scalafix on
compile:

```
$ sbt
> compile
[error] (Compile / scalafix) scalafix.sbt.ScalafixFailed: LinterError
```

Scalafix can also be run manually:

```
$ sbt
> scalafix LinearTypes
[error] (Compile / scalafix) scalafix.sbt.ScalafixFailed: LinterError
```
