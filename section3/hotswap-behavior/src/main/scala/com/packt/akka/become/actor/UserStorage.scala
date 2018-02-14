package com.packt.akka.become.actor

import akka.actor.{Actor, Props, Stash}
import com.packt.akka.become.actor.UserStorage._

object UserStorage {

  trait DBOperation
  object DBOperation {
    case object Create extends DBOperation
    case object Update extends DBOperation
    case object Read extends DBOperation
    case object Delete extends DBOperation
  }

  case object Connect
  case object Disconnect
  case class Operation(dBOperation: DBOperation, user: Option[User])
  case class User(username: String, email: String)

  def props = Props[UserStorage]
}

class UserStorage extends Actor with Stash {

  def receive = disconnected

  def connected: Receive = {
    case Disconnect =>
      println("User Storage Disconnect from DB")
      context.unbecome()
    case Operation(op, user) =>
      println(s"User Storage receive $op to do in user: $user")
  }

  def disconnected: Receive = {
    case Connect =>
      println(s"User Storage connected to DB")
      unstashAll()
      context.become(connected)
    case _ =>
      stash()
  }
}