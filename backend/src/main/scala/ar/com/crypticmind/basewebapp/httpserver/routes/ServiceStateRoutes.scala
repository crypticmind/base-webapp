package ar.com.crypticmind.basewebapp.httpserver.routes

import ar.com.crypticmind.basewebapp.dal.DatabaseComponent
import spray.routing.Route
import ar.com.crypticmind.basewebapp.httpserver.RouteContainer
import scala.util._
import org.slf4j.LoggerFactory

trait ServiceStateRoutes extends RouteContainer { this: DatabaseComponent ⇒

  private val log = LoggerFactory.getLogger(this.getClass)

  def databaseOperative: Boolean = Try(database.checkDatabaseVersion()) match {
    case Success(_)         ⇒ true
    case Failure(exception) ⇒ log.error("Database non-operative", exception); false
  }

  abstract override def route: Route =
    dynamicIf(!databaseOperative) {
      validate(databaseOperative, "Database non-operative") {
        super.route
      }
    }

}
