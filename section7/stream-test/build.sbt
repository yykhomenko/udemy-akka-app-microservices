name := "stream-test"
version := "1.0"
scalaVersion := "2.12.4"

val akkaVer = "2.5.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVer,
  "com.typesafe.akka" %% "akka-stream" % akkaVer,
  "com.typesafe.akka" %% "akka-stream" % akkaVer,
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "com.typesafe.akka" %% "akka-testkit" % akkaVer % "test",
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVer % "test"
)