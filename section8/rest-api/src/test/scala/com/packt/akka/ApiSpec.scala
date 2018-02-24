package com.packt.akka

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.packt.akka.db.{Created, TweetManager}
import com.packt.akka.model._
import org.scalatest.{BeforeAndAfterAll, FlatSpec, MustMatchers}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Await
import scala.concurrent.duration._

class ApiSpec extends FlatSpec with MustMatchers
  with ScalatestRouteTest with BeforeAndAfterAll with RestApi {

  import TweetEntityProtocol.EntityFormat
  import TweetProtocol._

  override implicit val ec = system.dispatcher

  override def afterAll =
    TweetManager.collection.drop(failIfNotFound = true)

  "The Server" should "return Ok response when get all tweets" in {

    val tweet = Tweet("akka", "Hello World")
    val f = TweetManager.save(TweetEntity.toTweetEntity(tweet))
    val Created(id) = Await.result(f, 1.second)

    Get("/tweets") ~> route ~> check {
      status must equal(StatusCodes.OK)
      val res = responseAs[List[TweetEntity]]
      res.size must equal(1)
      res.head must equal(TweetEntity(BSONObjectID.parse(id).get, tweet.author, tweet.body))
    }
  }

  it should "return created response when create new tweet" in {
    Post("/tweets", Tweet("akka", "hello world")) ~> route ~> check {
      status must equal(StatusCodes.Created)
    }
  }

  it should "return No Content response when delete a tweet" in {

    val tweet = Tweet("akka", "hello world")
    val f = TweetManager.save(TweetEntity.toTweetEntity(tweet))
    val Created(id) = Await.result(f, 1.second)

    Delete(s"/tweets/$id") ~> route ~> check {
      status must equal(StatusCodes.NoContent)
    }
  }

  it should "return Ok response when get a tweet" in {

    val tweetEntity = TweetEntity.toTweetEntity(Tweet("akka", "hello world"))
    val f = TweetManager.save(tweetEntity)
    val Created(id) = Await.result(f, 1.second)

    Get(s"/tweets/$id") ~> route ~> check {
      status must equal(StatusCodes.OK)
      responseAs[TweetEntity] must equal(tweetEntity)
    }
  }
}