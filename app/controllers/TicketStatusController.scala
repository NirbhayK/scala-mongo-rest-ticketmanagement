package controllers

import javax.inject.Inject
import play.api.libs.json.{JsArray, JsObject}
import play.api.mvc.{Controller}
import play.modules.reactivemongo._
import play.modules.reactivemongo.json.collection.JSONCollection
import utils.AuthenticatedRequest
import models.TicketStatus
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by nirbhaykundan on 28/11/15.
  */
class TicketStatusController  @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents with AuthenticatedRequest {


  def ticketStatuses() = AuthenticatedAction {
    _ =>
      TicketStatus.getStatuses() map {
        status =>
          Ok(JsArray(status))
      }
  }
}
