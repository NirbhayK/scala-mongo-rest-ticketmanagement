package controllers

import javax.inject.Inject
import play.api.libs.json.{JsArray}
import play.api.mvc.{Controller}
import play.modules.reactivemongo._
import utils.AuthenticatedRequest
import models.{ Priority}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by nirbhaykundan on 29/11/15.
  */
class PriorityController @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents with AuthenticatedRequest {

  def priorities() = AuthenticatedAction {
    _ =>
      Priority.getPriorites() map {
        priorities =>
          Ok(JsArray(priorities))
      }
  }

}
