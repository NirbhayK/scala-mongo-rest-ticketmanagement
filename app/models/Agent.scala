package models

import play.modules.reactivemongo.json._
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext


/**
  * Created by nirbhaykundan on 29/11/15.
  */

case class AgentDC(
                  
                 name : String,
                 email: String
                  )

/**
  * Companion object provides JSON serialization thanks to Play JSON formats
  */
object AgentDC {
  implicit val agentFro = Json.format[AgentDC]
}





object Agent {
  lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  private def agentCollection = reactiveMongoApi.db.collection[JSONCollection]("agent")

  def getAgents() = {
    agentCollection
      .find(Json.obj())
      .cursor[JsObject]
      .collect[List]()
  }

  def findAgentByEmail(email:String) = {
    agentCollection
      .find(Json.obj("email" -> email))
      .one[JsObject]
  }


  def addAgent(agent:AgentDC) = {
      agentCollection.insert(agent)
  }

}
