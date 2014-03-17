import sbt._
import Keys._

object Build extends Build {
  import Settings._
  import Dependencies._

  lazy val root = Project("base-webapp", file("."))
    .aggregate(backend)
    .settings(basicSettings: _*)
    .settings(formatSettings: _*)
    .settings(noPublishing: _*)

  lazy val backend = Project("base-webapp-backend", file("backend"))
    .settings(basicSettings: _*)
    .settings(formatSettings: _*)
    .settings(backendSettings: _*)
    .settings(revolverSettings: _*)
    .settings(testSettings: _*)
    .settings(assemblySettings: _*)
    .settings(
      libraryDependencies ++=
        compile(akkaActor, akkaSlf4j, sprayCan, sprayRouting, sprayJson, mapperdao, c3p0, h2Driver, scalaReflect, slf4japi, logback, commons_lang, cacheable) ++
        test(scalatest, akkaTestKit, mapperdao, c3p0, h2Driver, slf4japi, logback, ant, commons_io, commons_lang, cacheable) ++
        runtime(h2Driver)
    )

  val noPublishing = Seq(publish := (), publishLocal := ())
}

