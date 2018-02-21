package com.packt.akka.model

import com.packt.akka.model.Tweet.{Author, Hashtag}

case class Tweet(author: Author, body: String) {
  def hashtags: Set[Hashtag] = body
    .split(" ")
    .collect { case t if t.startsWith("#") => Hashtag(t) }
    .toSet
}

object Tweet {
  case class Author(name: String)
  case class Hashtag(name: String) {
    require(name.startsWith("#"), "Hash tag must start with #")
  }
}