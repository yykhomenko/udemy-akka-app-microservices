name := "reactive-tweets"
version := "1.0"
scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.9",
  "com.typesafe.akka" %% "akka-stream" % "2.5.9",
  "org.twitter4j" % "twitter4j-stream" % "4.0.6"
)