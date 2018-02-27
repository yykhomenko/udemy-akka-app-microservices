name := "throttler-messages-pattern"
organization := "akka.pattern.throttle"
scalaVersion := "2.12.4"
 
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.10",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.10",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)