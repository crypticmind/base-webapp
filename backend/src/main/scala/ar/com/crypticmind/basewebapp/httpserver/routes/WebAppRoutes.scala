package ar.com.crypticmind.basewebapp.httpserver.routes

import spray.routing.Route
import ar.com.crypticmind.basewebapp.httpserver.RouteContainer
import ar.com.crypticmind.basewebapp.core.Core
import spray.http.{ StatusCodes, Uri }

trait WebAppRoutes extends RouteContainer { this: Core ⇒

  abstract override def route: Route = super.route ~
    path("app") {
      redirect(Uri("app/index.html"), StatusCodes.SeeOther)
    } ~
    pathPrefix("app") {
      getFromResourceDirectory("webapp")
    }
}
