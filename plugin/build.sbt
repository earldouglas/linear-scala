scalaVersion := _root_.scalafix.sbt.BuildInfo.scala212
scalacOptions ++=
  Seq(
    "-deprecation",
    "-encoding",
    "utf8",
    "-feature",
    "-language:existentials",
    "-language:experimental.macros",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Ypartial-unification",
    "-Yrangepos",
    "-Ywarn-unused",
    "-Ywarn-unused-import"
  )

sbtPlugin := true
enablePlugins(SbtPlugin)
addSbtPlugin(
  "ch.epfl.scala" % "sbt-scalafix" % _root_.scalafix.sbt.BuildInfo.scalafixVersion
)

scriptedBufferLog := false
scriptedLaunchOpts += "-Dplugin.version=" + version.value

Compile / sourceGenerators += task {
  val dir = (Compile / sourceManaged).value
  val className = "LinearScalaBuildInfo"
  val f = dir / s"${className}.scala"
  IO.write(
    f,
    Seq(
      "package com.earldouglas.linearscala",
      "",
      s"object $className {",
      s"""  def version: String = "${version.value}" """,
      "}"
    ).mkString("", "\n", "\n")
  )
  Seq(f)
}
