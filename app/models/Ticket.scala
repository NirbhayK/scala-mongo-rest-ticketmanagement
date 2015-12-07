package models


import play.modules.reactivemongo.json._
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import reactivemongo.bson.{BSONDocument}

import scala.util.parsing.json.JSONObject

//
//Ticket
//- id
//- customerid
//- catId
//- Ticket Title
//- Ticket description
//- Priority
//- Case owner
//- creation date
//- verification date
//- close date
//- case status
//- Thread group id

/**
  * Created by nirbhaykundan on 29/11/15.
  */


case class TicketDC(
                     customerName: String,
                     customerEmail:String,
                     categoryId: String,
                     ticketTitle: String,
                     ticketDescription: String,
                     priorityId : String,
                     agentId :String,
                     ticketStatusId:String

                   )

/**
  * Companion object provides JSON serialization thanks to Play JSON formats
  */
object TicketDC {
  implicit val ticketJson = Json.format[TicketDC]
}


object Ticket {



  lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  private def ticketCollection = reactiveMongoApi.db.collection[JSONCollection]("ticket")

  def getTickets() = {
    ticketCollection
      .find(Json.obj())
      .cursor[JsObject]
      .collect[List]()
  }

  def addTicket(ticket:TicketDC) = {
    ticketCollection.insert(ticket)
  }

  def findTicketById(id:String) ={
       ticketCollection.find(Json.obj("_id" -> Json.obj("$oid" ->id))).one[JsObject]
  }

  def findTicketsByCustomerEmail(email:String) ={
    ticketCollection.find(Json.obj("customerEmail" -> email))
      .cursor[JsObject]
      .collect[List]()
  }

  def updateTicket(id: String, json:JsObject) = {
      val query = BSONDocument("_id" -> BSONDocument("$oid" ->id),
                               "ticketStatusId" -> BSONDocument("$ne" -> "565980175c74bd42ffc5faff")
                              )
      ticketCollection.update(query, Json.obj("$set" -> json))
  }
}
