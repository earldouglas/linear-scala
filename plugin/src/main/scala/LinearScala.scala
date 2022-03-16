package com.earldouglas.linearscala

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import scalafix.sbt.ScalafixPlugin
import scalafix.internal.sbt.SemanticdbPlugin

object LinearScala extends AutoPlugin {

  override def requires = JvmPlugin && ScalafixPlugin
  override def trigger = allRequirements

  override val buildSettings: Seq[Def.Setting[_]] =
    Seq(
      ScalafixPlugin.autoImport.scalafixDependencies +=
        "com.earldouglas" %% "linear-scala-scalafix" % LinearScalaBuildInfo.version,
      ScalafixPlugin.autoImport.scalafixScalaBinaryVersion :=
        CrossVersion.binaryScalaVersion(scalaVersion.value),
      SemanticdbPlugin.semanticdbEnabled := true,
      SemanticdbPlugin.semanticdbVersion :=
        ScalafixPlugin.autoImport.scalafixSemanticdb.revision
    )

  override val projectSettings: Seq[Def.Setting[_]] =
    Seq(
      libraryDependencies +=
        "com.earldouglas" % "linear-scala" % LinearScalaBuildInfo.version,
      ScalafixPlugin.autoImport.scalafixOnCompile := true
    ) ++ Seq(Compile, Test).map { c =>
      c / ScalafixPlugin.autoImport.scalafix :=
        (c / ScalafixPlugin.autoImport.scalafix).partialInput(" LinearTypes").evaluated
    }
}
