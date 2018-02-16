name := "persistence-fsm-query"
version := "1.0"
scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.9",
  "com.typesafe.akka" %% "akka-stream" % "2.5.9",
  "com.typesafe.akka" %% "akka-persistence" % "2.5.9",
  "com.typesafe.akka" %% "akka-persistence-query" % "2.5.9",
  "org.iq80.leveldb" % "leveldb" % "0.10",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8")