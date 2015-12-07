package models
import play.modules.reactivemongo.json._
import play.api.libs.json.{ JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
/**
  * Created by nirbhaykundan on 28/11/15.
  */

///**
//  * Ticket Status Model
//  * @param _id
//  * @param status
//  * @param order
//  */
//case class TicketStatusDC ( _id : String = BSONObjectID.generate.toString(),
//                          status : String,
//                          order : Int)
//
//object TicketStatusDC {
//  implicit val ticketStatusFormat = Json.format[TicketStatusDC]
//}


object TicketStatus {
  lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  private def ticketStatusCollection = reactiveMongoApi.db.collection[JSONCollection]("ticketstatus")

  def getStatuses() = {
    ticketStatusCollection
      .find(Json.obj())
      .cursor[JsObject]
      .collect[List]()
  }
}