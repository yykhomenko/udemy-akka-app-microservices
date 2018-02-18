package com.packt.akka.singleton.actor

import akka.actor.{ActorLogging, ActorRef}
import akka.persistence._
import com.packt.akka.singleton.actor.Master._

object Master {

  trait Command
  case class RegisterWorker(worker: ActorRef) extends Command
  case class RequestWork(requester: ActorRef) extends Command
  case class Work(requester: ActorRef, op: String) extends Command

  trait Event
  case class AddWorker(worker: ActorRef) extends Event
  case class AddWork(work: Work) extends Event
  case class UpdateWorks(works: List[Work]) extends Event

  case class State(workers: Set[ActorRef], works: List[Work])

  case object NoWork
}

class Master extends PersistentActor with ActorLogging {

  var workers = Set[ActorRef]()
  var works = List[Work]()

  override def persistenceId = self.path.parent.name + "-" + self.path.name

  def updateState(evt: Event) = evt match {
    case AddWorker(w)     => workers = workers + w
    case AddWork(w)       => works = works :+ w
    case UpdateWorks(ws)  => works = ws
  }

  val receiveRecover = {

    case evt: Event =>
      updateState(evt)

    case SnapshotOffer(_, snapshot: State) =>
      workers = snapshot.workers
      works = snapshot.works
  }

  val receiveCommand = {

    case RegisterWorker(worker) =>
      persist(AddWorker(worker))(updateState(_))

    case RequestWork if works.isEmpty =>
      sender ! NoWork

    case RequestWork(requester) if workers.contains(requester) && works.nonEmpty =>
      sender ! works.head
      persist(UpdateWorks(works.tail))(updateState(_))

    case w: Work =>
      persist(AddWork(w))(updateState(_))
  }
}