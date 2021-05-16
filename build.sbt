lazy val V = _root_.scalafix.sbt.BuildInfo
inThisBuild(
  List(
    scalaVersion := V.scala213,
    crossScalaVersions := List(V.scala213, V.scala212, V.scala211),
    organization := "com.earldouglas",
    homepage := Some(url("https://github.com/earldouglas/linear-scala")),
    licenses := List(("ISC", url("https://opensource.org/licenses/ISC"))),
    developers := List(Developer("earldouglas", "James Earl Douglas", "james@earldouglas.com", url("https://earldouglas.com"))),
    addCompilerPlugin(scalafixSemanticdb),
    scalacOptions ++=
      List( "-Yrangepos"
          , "-P:semanticdb:synthetics:on"
          )
  )
)

publish / skip := true

lazy val library = // a standard dependency providing the `Linear` type
  project
    .settings( moduleName := "linear-scala"
             , crossPaths := false // publish without the Scala version postfix in artifact names
             )

lazy val rules = // a Scalafix dependency providing the `LinearTypes` rule
  project
    .settings( moduleName := "linear-scala-scalafix"
             , libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % V.scalafixVersion
             )

lazy val input = // sample input to use for testing; annotated with expected errors
  project
    .settings(publish / skip := true)
    .dependsOn(rules)
    .dependsOn(library)

lazy val output = // empty since we're building a linter and not a rewriter, but needed by scalafix-testkit
  project
    .settings(publish / skip := true)

lazy val tests = // boilerplate to be able to use scalafix-testkit
  project
    .settings( publish / skip := true
             , libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % V.scalafixVersion % Test cross CrossVersion.full
             , Compile / compile := (Compile / compile).dependsOn(input / Compile / compile).value
             , scalafixTestkitOutputSourceDirectories := (output / Compile / unmanagedSourceDirectories).value
             , scalafixTestkitInputSourceDirectories := (input / Compile / unmanagedSourceDirectories).value
             , scalafixTestkitInputClasspath := (input / Compile / fullClasspath).value
             )
    .dependsOn(rules)
    .dependsOn(library)
    .enablePlugins(ScalafixTestkitPlugin)
