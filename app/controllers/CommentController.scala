package controllers

import javax.inject.Inject
import play.api.Logger
import play.modules.reactivemongo.json._

import play.api.libs.json.{Json, JsObject, JsArray}
import play.api.mvc.{Controller}
import play.modules.reactivemongo._
import utils.AuthenticatedRequest
import models.{CommentDC, Comment}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by nirbhaykundan on 29/11/15.
  */
class CommentController @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents with AuthenticatedRequest {

  def findCommentsByTicket(ticketId:String) = AuthenticatedAction {
    _ =>
      Comment.findCommentsByTicketId(ticketId) map {
        comments =>
          Ok(JsArray(comments))
      }
  }

  def addComment() = JsonAuthenticatedAction[CommentDC] {
    (comment, _) =>
      Comment.addComment(comment) map {
        _ => Ok(Json.toJson(comment))
      }
  }
}
