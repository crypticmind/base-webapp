import sbt._

object Dependencies {

  val resolutionRepos = Seq(
      "spray repo"          at   "http://repo.spray.io/",
      "sonatype releases"   at   "http://oss.sonatype.org/content/repositories/releases/"
  )

  val sprayVersion = "1.2.0"
  val akkaVersion  = "2.2.3"

  val sprayJson       = "io.spray"                  %%  "spray-json"            % "1.2.5"
  val scalatest       = "org.scalatest"             %   "scalatest_2.10"        % "2.1.0"
  val slf4japi        = "org.slf4j"                 %   "slf4j-api"             % "1.7.5"
  val logback         = "ch.qos.logback"            %   "logback-classic"       % "1.0.13"
  val akkaActor       = "com.typesafe.akka"         %%  "akka-actor"            % akkaVersion
  val akkaSlf4j       = "com.typesafe.akka"         %%  "akka-slf4j"            % akkaVersion
  val akkaTestKit     = "com.typesafe.akka"         %%  "akka-testkit"          % akkaVersion
  val sprayCan        = "io.spray"                  %   "spray-can"             % sprayVersion
  val sprayRouting    = "io.spray"                  %   "spray-routing"         % sprayVersion
  val sprayTestkit    = "io.spray"                  %   "spray-testkit"         % sprayVersion
  val sprayClient     = "io.spray"                  %   "spray-client"          % sprayVersion
  val mapperdao       = "com.googlecode.mapperdao"  %   "mapperdao"             % s"1.0.0.rc25-${Settings.theScalaVersion}"
  val c3p0            = "c3p0"                      %   "c3p0"                  % "0.9.1.2"
  val h2Driver        = "com.h2database"            %   "h2"                    % "1.2.127"
  val scalaReflect    = "org.scala-lang"            %   "scala-reflect"         % Settings.theScalaVersion
  val ant             = "org.apache.ant"            %   "ant"                   % "1.9.3"
  val commons_io      = "commons-io"                %   "commons-io"            % "2.4"
  val commons_lang    = "org.apache.commons"        %   "commons-lang3"         % "3.0"
  val cacheable       = "com.github.cb372"          %%  "cacheable-ehcache"     % "0.1.1"

  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")
}
