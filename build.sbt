name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  //"org.seleniumhq.selenium" % "selenium-java" % "2.48.2" % "test",
  //"org.hamcrest" % "hamcrest-all" % "1.3" % "test",
  //"org.seleniumhq.selenium.fluent" % "fluent-selenium" % "1.14.2" % "test" exclude("org.seleniumhq.selenium", "selenium-java") ,
  //"org.specs2" %% "specs2-core" % "3.6.5" % "test"
   specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true