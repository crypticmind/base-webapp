package ar.com.crypticmind.basewebapp.dal

import scala.slick.driver.JdbcDriver

trait DatabaseComponent {

  val jdbcDriver: JdbcDriver
  val database: jdbcDriver.simple.Database

}
