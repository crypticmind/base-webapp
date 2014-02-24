package ar.com.crypticmind.basewebapp.httpserver

import spray.routing._

trait RouteContainer extends Directives {
  def route: Route = reject
}
