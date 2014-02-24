package ar.com.crypticmind.basewebapp.core

import akka.actor.{ Props, ActorSystem }
import ar.com.crypticmind.basewebapp.httpserver.{ ServerManager, ServiceActor }
import ar.com.crypticmind.basewebapp.httpserver.routes.UserRoutes
import scala.slick.driver.JdbcDriver
import com.mchange.v2.c3p0.ComboPooledDataSource
import ar.com.crypticmind.basewebapp.dal.{AppDataStore, DatabaseComponent}
import scala.reflect.runtime.currentMirror


trait Bootstrap {

  // The actor system.
  implicit val system = ActorSystem("app")
  val settings = ServiceSettings(system)

  // The database.
  val ds = new ComboPooledDataSource
  val slickDriver: JdbcDriver = {
    val module = currentMirror.staticModule(settings.dbDialect)
    val reflectedModule = currentMirror.reflectModule(module)
    val driver = reflectedModule.instance.asInstanceOf[JdbcDriver]
    driver
  }
  val database: slickDriver.simple.Database = slickDriver.simple.Database.forDataSource(ds)

  // The service actor that will dispatch incoming requests.
  private class WiredService extends ServiceActor with DatabaseComponent with AppDataStore with Core with UserRoutes {
    val settings = Bootstrap.this.settings
    val jdbcDriver = Bootstrap.this.slickDriver
    val database = Bootstrap.this.database
  }

  // The manager that handles the lifecycle of the service actor.
  val httpService = system.actorOf(Props(new WiredService))
  val serverManager = system.actorOf(Props(new ServerManager(httpService, settings)))

  // Go!
  serverManager ! ServerManager.Start

}
