package ar.com.crypticmind.basewebapp.misc

import scala.util.Random
import org.apache.commons.lang3.RandomStringUtils
import java.util.UUID

object IdGenerator {

  val random = new Random()

  def shortId: String = RandomStringUtils.randomAlphanumeric(8)

  def uuid: String = new UUID(random.nextLong(), random.nextLong()).toString
}
