package controllers
import javax.inject.Inject
import play.api.libs.json.{JsArray}
import play.api.mvc.{Controller}
import play.modules.reactivemongo._
import utils.AuthenticatedRequest
import models.{ Category}
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by nirbhaykundan on 29/11/15.
  */
class CategoryController @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents with AuthenticatedRequest {

  def categories() = AuthenticatedAction {
    _ =>
      Category.getCategories() map {
        categories =>
          Ok(JsArray(categories))
      }
  }

}
