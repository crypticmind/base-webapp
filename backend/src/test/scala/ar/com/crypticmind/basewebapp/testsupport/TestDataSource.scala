package ar.com.crypticmind.basewebapp.testsupport

import ar.com.crypticmind.basewebapp.misc.IdGenerator
import javax.sql.DataSource
import com.mchange.v2.c3p0.ComboPooledDataSource
import ar.com.crypticmind.basewebapp.dal.DatabaseComponent

trait TestDataSource { this: DatabaseComponent ⇒

  val testDatabaseName = "testdb-" + IdGenerator.shortId

  override def createDataSource: DataSource = {
    val ds = new ComboPooledDataSource
    ds.setJdbcUrl(s"jdbc:h2:mem:$testDatabaseName;DB_CLOSE_DELAY=-1")
    ds
  }
}
