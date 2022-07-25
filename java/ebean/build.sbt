name := """ybstore"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava,PlayEbean).settings(PlayKeys.playDefaultPort := 8080)

scalaVersion := "2.12.8"

javacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-parameters",
  "-Xlint:unchecked",
  "-Xlint:deprecation",
  "-Werror"
)

crossScalaVersions := Seq("2.11.12", "2.12.8")

libraryDependencies += guice
libraryDependencies += jdbc


// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.11.1" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "3.1.3" % Test
libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1206-jdbc42"
libraryDependencies += evolutions

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
