package controllers
import javax.inject.Inject
import play.api.Logger
import play.modules.reactivemongo.json._
import play.api.libs.json.{JsObject, Json, JsArray}
import play.api.mvc.{Controller}
import play.modules.reactivemongo._
import utils.AuthenticatedRequest
import models.{Agent, Ticket, TicketDC}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by nirbhaykundan on 29/11/15.
  */
class TicketController @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents with AuthenticatedRequest {
  private def asJson(v: Option[JsObject]) = v.map(Ok(_)).getOrElse(NotFound)

  def tickets() = AuthenticatedAction {
    _ =>
      Ticket.getTickets() map {
        tickets =>
          Ok(JsArray(tickets))
      }
  }


  def addTicket() = JsonAuthenticatedAction[TicketDC] {
    (ticket, _) =>
      Ticket.addTicket(ticket) map {
        _ => Ok(Json.toJson(ticket))
      }
  }

  def findTicketById(id:String) = AuthenticatedAction {
    _ =>
      Ticket.findTicketById(id) map asJson
  }

  def findTicketByCustomerEmail(email:String) = AuthenticatedAction {
    _ =>
      Ticket.findTicketsByCustomerEmail(email) map {
        tickets =>
          Ok(JsArray(tickets))
      }
  }


  def updateTicket(id:String) =  JsonAuthenticatedAction[JsObject] {
    (json, _) =>
      Ticket.updateTicket(id,json)
      Ticket.findTicketById(id) map asJson
  }

}
