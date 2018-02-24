name := "rest-api"
version := "1.0"
scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.10",
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.10",
  "com.typesafe.akka" %% "akka-http-core" % "10.0.11",
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.11",
  "org.reactivemongo" %% "reactivemongo" % "0.13.0",
  
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.11" % "test"
)