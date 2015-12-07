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
object Priority {
  lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  private def priorityCollection = reactiveMongoApi.db.collection[JSONCollection]("priority")

  def getPriorites() = {
    priorityCollection
      .find(Json.obj())
      .cursor[JsObject]
      .collect[List]()
  }

}
