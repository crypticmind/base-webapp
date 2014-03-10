package ar.com.crypticmind.basewebapp.httpserver.routes

import ar.com.crypticmind.basewebapp.service.UserServiceComponent
import spray.http.{ HttpResponse, Uri, StatusCodes }
import spray.routing.Route
import ar.com.crypticmind.basewebapp.httpserver.RouteContainer
import ar.com.crypticmind.basewebapp.httpserver.dto.UserDto
import ar.com.crypticmind.basewebapp.model.User
import org.slf4j.LoggerFactory
import spray.http.HttpHeaders.Location

trait UserRoutes extends RouteContainer { this: UserServiceComponent ⇒

  import spray.httpx.SprayJsonSupport._
  import spray.json.DefaultJsonProtocol._

  private val log = LoggerFactory.getLogger(this.getClass)

  implicit val userDtoJsonFormat = jsonFormat1(UserDto)

  abstract override def route: Route = super.route ~
    path("users" / Segment) { username ⇒
      get {
        complete {
          log.debug(s"Getting user $username")
          userService.findByUsername(username) match {
            case Some(user) ⇒ UserDto(username = user.username)
            case None       ⇒ StatusCodes.NotFound
          }
        }
      } ~
        put {
          entity(as[UserDto]) { userDto ⇒
            complete {
              log.debug(s"Updating user $userDto")
              userService.findByUsername(username) match {
                case Some(existingUser) ⇒
                  val updatedUser = userService.save(User(existingUser.id, userDto.username))
                  HttpResponse(status = StatusCodes.SeeOther, headers = Location(Uri(s"/users/${updatedUser.username}")) :: Nil)
                case None ⇒
                  StatusCodes.NotFound
              }
            }
          }
        } ~
        delete {
          complete {
            log.debug(s"Deleting user $username")
            userService.findByUsername(username).foreach(existingUser ⇒ userService.delete(existingUser))
            StatusCodes.NoContent
          }
        }
    } ~
    path("users") {
      get {
        complete {
          log.debug(s"Getting all users")
          userService.getAllUsers.map(u ⇒ UserDto(u.username))
        }
      } ~
        post {
          entity(as[UserDto]) { userDto ⇒
            complete {
              log.debug(s"Creating new user $userDto")
              userService.findByUsername(userDto.username) match {
                case Some(existingUser) ⇒
                  StatusCodes.BadRequest
                case None ⇒
                  val newUser = userService.save(User(0, userDto.username))
                  HttpResponse(status = StatusCodes.SeeOther, headers = Location(Uri(s"/users/${newUser.username}")) :: Nil)
              }
            }
          }
        }
    }

}
