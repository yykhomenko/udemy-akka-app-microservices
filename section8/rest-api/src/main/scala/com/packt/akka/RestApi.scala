package com.packt.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import com.packt.akka.db.TweetManager
import com.packt.akka.model.{Tweet, TweetEntity, TweetEntityProtocol, TweetProtocol}
import spray.json._

import scala.concurrent.ExecutionContext

trait RestApi {

  import TweetEntity._
  import TweetEntityProtocol.EntityFormat
  import TweetProtocol._

  implicit val system: ActorSystem
  implicit val materializer: Materializer
  implicit val ec: ExecutionContext

  val route =
    pathPrefix("tweets") {
      (post & entity(as[Tweet])) { tweet =>
        complete {
          TweetManager.save(tweet) map { r =>
            Created -> Map("id" -> r.id).toJson
          }
        }
      } ~
        (get & path(Segment)) { id =>
          complete {
            TweetManager.findById(id) map { t =>
              OK -> t
            }
          }
        } ~
        (delete & path(Segment)) { id =>
          complete {
            TweetManager.deleteById(id) map { _ =>
              NoContent
            }
          }
        } ~
        get {
          complete {
            TweetManager.find map { ts =>
              OK -> ts.map(_.as[TweetEntity])
            }
          }
        }
    }
}