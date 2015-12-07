package utils

import play.api.libs.json.{JsValue, Reads}
import play.api.mvc.{Result,Results}
import scala.concurrent.Future
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
/**
  * Created by nirbhaykundan on 28/11/15.
  */


/**
  * To Validate all incoming request json
  */
trait RedTicketUtils extends  Results{
  def validateJson[T](json: JsValue, success: (T, JsValue) =>
    Future[Result])(implicit reads: Reads[T]) = {
    json.validate[T].asEither match {
      case Left(errors) => {
        Logger.warn(s"Bad request : $errors")
        Future(BadRequest(errors.mkString(",")))
      }
      case Right(valid) => success(valid, json)
    }
  }
}
