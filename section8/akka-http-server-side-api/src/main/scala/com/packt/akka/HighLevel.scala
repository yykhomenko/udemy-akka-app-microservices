package com.packt.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn

object HighLevel extends App {

  implicit val sys = ActorSystem("my-system")
  implicit val mat = ActorMaterializer()
  implicit val dis = sys.dispatcher

  val route =
    path("") {
      get {
        complete("Hello Akka HTTP Server Side API - High Level")
      }
    }

  val binding = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()

  binding
    .flatMap(_.unbind())
    .onComplete(_ => sys.terminate())
}