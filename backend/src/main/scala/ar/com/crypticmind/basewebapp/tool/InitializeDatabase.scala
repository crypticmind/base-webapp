package ar.com.crypticmind.basewebapp.tool

import akka.actor.ActorSystem
import com.mchange.v2.c3p0.ComboPooledDataSource
import scala.slick.driver.JdbcDriver
import scala.reflect.runtime._
import ar.com.crypticmind.basewebapp.core.ServiceSettings
import ar.com.crypticmind.basewebapp.dal.{DatabaseComponent, AppDataStore}
import scala.util.{Success, Failure, Try}
import org.slf4j.LoggerFactory


object InitializeDatabase extends App {

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

  val schemaContainer = new Object with AppDataStore with DatabaseComponent {
    val jdbcDriver = InitializeDatabase.this.slickDriver
    val database = InitializeDatabase.this.database

    import jdbcDriver.simple._
    val log = LoggerFactory.getLogger(this.getClass)

    database.withSession { implicit session =>
      Try(schema.users.ddl.drop) match {
        case Success(_) => log.info("Schema dropped")
        case Failure(f) => log.error("Schema drop failed", f)
      }
      Try(schema.users.ddl.create) match {
        case Success(_) => log.info("Schema created")
        case Failure(f) => log.error("Schema create failed", f)
      }
    }
  }

  system.shutdown()
}
