lazy val V = _root_.scalafix.sbt.BuildInfo

libraryDependencies += "com.earldouglas" % "linear-scala" % "0.1.0-SNAPSHOT"

inThisBuild(
  List( scalaVersion := V.scala213
      , scalafixDependencies += "com.earldouglas" %% "linear-scala-scalafix" % "0.1.0-SNAPSHOT"
      , scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value)
      , semanticdbEnabled := true // enable SemanticDB
      , semanticdbVersion := scalafixSemanticdb.revision // use Scalafix compatible version
      )
)

scalafixOnCompile := true
