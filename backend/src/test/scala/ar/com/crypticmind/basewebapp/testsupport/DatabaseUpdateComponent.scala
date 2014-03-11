package ar.com.crypticmind.basewebapp.testsupport

import ar.com.crypticmind.basewebapp.dal.DatabaseComponent
import org.apache.tools.ant.taskdefs.SQLExec
import org.apache.tools.ant.Project
import scala.util.{ Failure, Success, Try }
import ar.com.crypticmind.basewebapp.dal.DatabaseComponent.Version
import java.io.{ FileOutputStream, File }
import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory

trait DatabaseUpdateComponent { this: DatabaseComponent with TestDataSource ⇒

  private val log = LoggerFactory.getLogger(getClass)

  private class SqlScriptRun extends SQLExec {
    val _project = new Project()
    _project.init()
    setProject(_project)
    setTaskType("SQL")
    setTaskName("SQL")
  }

  def updateDatabase() {

    val path = "database/"

    val currentVersion = Try(database.currentVersion) match {
      case Success(Some(version)) ⇒
        log.info(s"Test database $testDatabaseName version: $version")
        version.version
      case _ ⇒
        log.info(s"Test database $testDatabaseName version: Uninitialized")
        Version.zero
    }

    val scriptVersions =
      DirectoryListing
        .listResources(path)
        .map(version ⇒ Version(version.replaceAllLiterally(".sql", "")))
        .takeWhile(_ > currentVersion)
        .sorted

    scriptVersions.foreach { scriptVersion ⇒
      log.debug(s"Updating database to version $scriptVersion")
      val tmpFile = File.createTempFile(s"database-update-$scriptVersion-", ".sql")
      val inputSQL = getClass.getResourceAsStream(s"/$path$scriptVersion.sql")
      val outputSQL = new FileOutputStream(tmpFile)
      IOUtils.copy(inputSQL, outputSQL)
      val sqlScriptRun = new SqlScriptRun
      sqlScriptRun.setSrc(tmpFile)
      sqlScriptRun.setDriver("org.h2.Driver")
      sqlScriptRun.setUrl(s"jdbc:h2:mem:$testDatabaseName;DB_CLOSE_DELAY=-1")
      sqlScriptRun.setUserid("sa")
      sqlScriptRun.setPassword("")
      Try(sqlScriptRun.execute()) match {
        case Success(_) ⇒
        case Failure(exception) ⇒
          sys.error(s"Failed execution of $path$scriptVersion.sql: $exception")
      }
    }

    if (scriptVersions.nonEmpty) {
      database.invalidateCachedCurrentVersion()
      log.info(s"Updated test database $testDatabaseName version to: " + database.currentVersion.map(_.version).getOrElse("unknown"))
    }
  }

}
