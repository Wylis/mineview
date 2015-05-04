import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "org.wylis"
  val buildScalaVersion = "2.11.6"

  val akkaVersion = "2.4-SNAPSHOT"

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := buildOrganization,
    scalaVersion := buildScalaVersion,
    resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
    resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
    resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )
}

object Resolvers {

}

object Dependencies {
  import BuildSettings._
//  lazy val jodatime = "joda-time" % "joda-time" % "2.+" % "compile"
//  lazy val scalaz = "org.scalaz" %% "scalaz-core" % "7.1.+" % "provided"
  lazy val akka_actor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
}

trait MineviewCoreProjectBuild {

  import Dependencies._
  import Resolvers._

  def mineviewCoreProject(name: String, baseFile: java.io.File) =
    Project(id = name, base = baseFile, settings = BuildSettings.buildSettings).settings(
      libraryDependencies += akka_actor
    )
}

trait TestAppProjectBuild {

  import Dependencies._
  import Resolvers._

  def testAppProject(name: String, baseFile: java.io.File) =
    Project(id = name, base = baseFile, settings = BuildSettings.buildSettings).settings(
      mainClass in(Compile, run) := Some("testapp.main")
    )
}

object MineviewBuild extends Build
with MineviewCoreProjectBuild
with TestAppProjectBuild {
  lazy val root = Project(
    id = "mineview",
    settings = BuildSettings.buildSettings,
    base = file(".")
  ).aggregate(core)

  lazy val core = mineviewCoreProject(name = "mineview-core",
    baseFile = file("mineview-core"))

  lazy val testApp = testAppProject(name = "test-app", baseFile = file("test-app")) dependsOn core
}