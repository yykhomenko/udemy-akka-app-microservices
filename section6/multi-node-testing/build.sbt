import com.typesafe.sbt.SbtMultiJvm
import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm

lazy val root = (project in file("."))
  .enablePlugins(MultiJvmPlugin)
  .configs(MultiJvm)
  .settings(

    SbtMultiJvm.multiJvmSettings,

    organization := "org.cbi",
    name := "cluster-multi-node-testing",
    version := "0.1",
    scalaVersion := "2.12.4",

    parallelExecution in Test := false,

    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-remote" % "2.5.9",
      "com.typesafe.akka" %% "akka-multi-node-testkit" % "2.5.9",
      "org.scalatest" %% "scalatest" % "3.0.5"),
  )