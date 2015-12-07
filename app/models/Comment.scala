package models

import play.modules.reactivemongo.json._
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import reactivemongo.bson.{BSONDocument}

/**
  * Created by nirbhaykundan on 29/11/15.
  */
//- id
//- ticketid
//- commnetdate
//- comment
//- commentBy
case class CommentDC(
                    ticketId : String,
                    commentDate: String,
                    comment: String,
                    commentBy:String
                  )

/**
  * Companion object provides JSON serialization thanks to Play JSON formats
  */
object CommentDC {
  implicit val commentFormat = Json.format[CommentDC]
}



object Comment {
  lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  private def commentCollection = reactiveMongoApi.db.collection[JSONCollection]("comment")

  def addComment(comment:CommentDC) = {
    commentCollection.insert(comment)
  }

  def findCommentsByTicketId(ticketId:String) = {
    commentCollection
      .find(Json.obj("ticketId" -> ticketId))
      .cursor[JsObject]
      .collect[List]()
  }
}
