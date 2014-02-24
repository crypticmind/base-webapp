package ar.com.crypticmind.basewebapp.httpserver

import spray.routing.HttpServiceActor


trait ServiceActor
    extends HttpServiceActor
    with RouteContainer {

  def receive = runRoute(route)
}
