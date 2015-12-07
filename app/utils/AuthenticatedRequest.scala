package utils
import play.api.mvc.{AnyContent, Result, Request, Action}
import play.api.libs.json.Reads
import play.api.Logger
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._
/**
  * Created by nirbhaykundan on 28/11/15.
  */
trait AuthenticatedRequest extends  RedTicketUtils{
  val REST_API_KEY_HEADER = "REST-API-KEY-HEADER"

  /**
    * Used in async actions
    * @param f the underlying action if everything goes well
    * @return a promise of an action result
    */
  def AuthenticatedAction(f: (Request[AnyContent]) => Future[Result]) =
    Action.async { implicit request =>
      Logger.debug(s"received request : $request")
      request.headers.get(REST_API_KEY_HEADER).map {
        key => f(request)
      } getOrElse {
        Future(Unauthorized(s"Hackers Go Away : $REST_API_KEY_HEADER"))
      }
    }

  /**
    * Used in async actions that require json validation of any incoming json via POST or PUT
    * @param f the underlying action if everything goes well
    * @param reads an expected implicit JSON Reads in order to marshall json into T
    * @tparam T a generic type parameter indicating the type that the JSON representation conforms to
    * @return a promise of an action result
    */
  def JsonAuthenticatedAction[T](f: (T, Request[AnyContent]) =>
    Future[Result])(implicit reads: Reads[T]) = AuthenticatedAction {
    (request) =>

      request.body.asJson match {
        case Some(json) => validateJson[T](json, (t, validJson) => f(t, request))
        case None => Future(BadRequest("Not a valid JSON format"))
      }
  }
}
