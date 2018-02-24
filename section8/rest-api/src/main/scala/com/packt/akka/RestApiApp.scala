package com.packt.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.io.StdIn

object RestApiApp extends App with RestApi {

  override implicit val system = ActorSystem("rest-api")
  override implicit val materializer = ActorMaterializer()
  override implicit val ec = system.dispatcher

  val binding = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()

  binding
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}