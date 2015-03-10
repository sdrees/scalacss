import sbt._
import Keys._
import com.typesafe.sbt.pgp.PgpKeys._
import org.scalajs.sbtplugin.ScalaJSPlugin
import ScalaJSPlugin._
import ScalaJSPlugin.autoImport._
import Dialect._
import Typical.{settings => _, _}

object ScalaCSS extends Build {

  val Scala211 = "2.11.6"

  val commonSettings: CDS =
    CDS.all(
      _.settings(
        organization       := "com.github.japgolly.scalacss",
        version            := "0.1.0-SNAPSHOT",
        homepage           := Some(url("https://github.com/japgolly/scalacss")),
        licenses           += ("LGPL v2.1+" -> url("http://www.gnu.org/licenses/lgpl-2.1.txt")),
        scalaVersion       := Scala211,
        crossScalaVersions := Seq("2.10.5", Scala211),
        scalacOptions     ++= Seq("-deprecation", "-unchecked", "-feature",
                                "-language:postfixOps", "-language:implicitConversions",
                                "-language:higherKinds", "-language:existentials"),
        updateOptions      := updateOptions.value.withCachedResolution(true))
    ) :+ Typical.settings("scalacss")

  val scalazCore = Library("org.scalaz", "scalaz-core", "7.1.1").myJsFork("scalaz").jsVersion(_+"-2")
  val shapeless  = Library("com.chuusai", "shapeless", "2.1.0").myJsFork("shapeless").jsVersion(_+"-2")

  // ==============================================================================================
  override def rootProject = Some(core)

  lazy val (core, coreJvm, coreJs) =
    crossDialectProject("core", commonSettings
      .configure(utestSettings())
      .addLibs(scalazCore, shapeless)
    )
}