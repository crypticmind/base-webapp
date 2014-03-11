import sbt._
import Keys._
import spray.revolver.RevolverPlugin.Revolver
import sbtrelease.ReleasePlugin._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

object Settings {

  val theScalaVersion = "2.10.3"

  lazy val basicSettings = Seq(
    organization  := "crypticmind.com.ar",
    scalaVersion  := theScalaVersion,
    resolvers    ++= Dependencies.resolutionRepos,
    fork in run   := true,
    scalacOptions := Seq(
      "-encoding",
      "utf8",
      "-g:vars",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.6",
      "-language:postfixOps",
      "-language:implicitConversions",
      "-Xlog-reflective-calls"
    )
    ) ++ releaseSettings

  import spray.revolver.RevolverPlugin.Revolver._
  lazy val revolverSettings = Revolver.settings

  lazy val formatSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile := formattingPreferences,
    ScalariformKeys.preferences in Test    := formattingPreferences
  )

  import scalariform.formatter.preferences._
  def formattingPreferences =
    FormattingPreferences()
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(DoubleIndentClassDeclaration, true)

  // SLF4J initializes itself upon the first logging call.  Because sbt
  // runs tests in parallel it is likely that a second thread will
  // invoke a second logging call before SLF4J has completed
  // initialization from the first thread's logging call, leading to
  // these messages:
  //   SLF4J: The following loggers will not work because they were created
  //   SLF4J: during the default configuration phase of the underlying logging system.
  //   SLF4J: See also http://www.slf4j.org/codes.html#substituteLogger
  //   SLF4J: com.imageworks.common.concurrent.SingleThreadInfiniteLoopRunner
  //
  // As a workaround, load SLF4J's root logger before starting the unit
  // tests [1].
  //
  // [1] http://stackoverflow.com/a/12095245
  val testSettings = Seq(
    testOptions in Test += Tests.Setup(classLoader =>
    classLoader
      .loadClass("org.slf4j.LoggerFactory")
      .getMethod("getLogger", classLoader.loadClass("java.lang.String"))
      .invoke(null, "ROOT")
  ))

  lazy val backendSettings = Seq(
    name := "base-webapp-backend",
    mainClass in run := Some("ar.com.crypticmind.basewebapp.Main")
  )
}

