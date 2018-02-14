package com.packt.akka.fsm.actor

import akka.actor.{FSM, Props, Stash}
import com.packt.akka.fsm.actor.UserStorageFSM._

object UserStorageFSM {

  // FSM State
  sealed trait State
  case object Connected extends State
  case object Disconnected extends State

  // FSM Data
  sealed trait Data
  case object EmptyData extends Data

  trait DBOperation
  object DBOperation {
    case object Create extends DBOperation
    case object Update extends DBOperation
    case object Read extends DBOperation
    case object Delete extends DBOperation
  }

  case object Connect
  case object Disconnect
  case class Operation(op: DBOperation, user: User)
  case class User(username: String, email: String)

  def props = Props[UserStorageFSM]
}

class UserStorageFSM extends FSM[State, Data] with Stash {

  // 1. define start with
  startWith(Disconnected, EmptyData)

  // 2. define states
  when(Disconnected) {
    case Event(Connect, _) =>
      println("UserStorage Connected to DB")
      unstashAll()
      goto(Connected) using EmptyData
    case Event(_, _) =>
      stash()
      stay using EmptyData
  }

  when(Connected) {
    case Event(Disconnect, _) =>
      println("UserStorage disconnected from DB")
      goto(Disconnected) using EmptyData

    case Event(Operation(op, user), _) =>
      println(s"UserStorage receive $op operation to do in user: $user")
      stay using EmptyData
  }

  // 3. initialize
  initialize()
}