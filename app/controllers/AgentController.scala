package controllers

import javax.inject.Inject
import play.api.Logger
import play.modules.reactivemongo.json._
import play.api.libs.json.{JsArray,Json,JsObject}
import play.api.mvc.{Controller}
import play.modules.reactivemongo._
import utils.AuthenticatedRequest
import models.{ AgentDC, Agent}
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by nirbhaykundan on 29/11/15.
  */
class AgentController @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents with AuthenticatedRequest {

  private def asJson(v: Option[JsObject]) = v.map(Ok(_)).getOrElse(NotFound)

  def agents() = AuthenticatedAction {
    _ =>
      Agent.getAgents() map {
        agents =>
          Ok( Json.toJson(agents))
      }
  }

  def findAgentByMail(email:String) = AuthenticatedAction {
    _ =>
      Logger.debug(s"debug : $email")
      Agent.findAgentByEmail(email) map asJson
  }

  def addAgent() = JsonAuthenticatedAction[AgentDC] {
    (agent, _) =>
      Agent.addAgent(agent) map {
        _ => Ok(Json.toJson(agent))
      }
  }
}
