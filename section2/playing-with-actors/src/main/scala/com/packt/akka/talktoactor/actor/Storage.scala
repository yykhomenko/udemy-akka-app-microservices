package com.packt.akka.talktoactor.actor

import akka.actor.{Actor, Props}
import com.packt.akka.talktoactor.dto.User

object Storage {
  case class AddUser(user: User)
  def props = Props[Storage]
}

class Storage extends Actor {
  import Storage._

  var users = List.empty[User]

  def receive = {
    case AddUser(user) =>
      println(s"Storage: $user stored")
      users = user :: users
    case m =>
      println(s"$getClass: unknown message: $m")
  }
}