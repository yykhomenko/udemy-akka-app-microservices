package com.packt.akka

import akka.actor.ActorSystem
import akka.persistence.query.PersistenceQuery
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal
import akka.stream.ActorMaterializer

import scala.concurrent.Await
import scala.concurrent.duration._

object Reporter extends App {

  val system = ActorSystem("persistent-query")
  implicit val mat = ActorMaterializer()(system)

  val queries = PersistenceQuery(system)
    .readJournalFor[LeveldbReadJournal](LeveldbReadJournal.Identifier)

  val evts = queries.eventsByPersistenceId("account")
  evts.runForeach(evt => println(s"Event $evt"))

  Thread.sleep(1000)

  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}