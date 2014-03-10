package ar.com.crypticmind.basewebapp.misc

import java.io.File
import java.util.jar.JarFile
import java.net.URLDecoder


object DirectoryListing {

  import scala.collection.JavaConversions._

  def listResources(path: String): List[String] = {
    Option(getClass.getClassLoader.getResource(path)) match {
      case Some(dirURL) if dirURL.getProtocol == "file" => new File(dirURL.toURI).list().toList
      case None =>
        val me = getClass.getName.replace(".", "/") + ".class"
        val dirURL = getClass.getClassLoader.getResource(me)
        if (dirURL.getProtocol == "jar") {
          val jarPath = dirURL.getPath.substring(5, dirURL.getPath.indexOf("!"))
          val jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"))
          jar.entries().map(_.getName).toList
        } else throw new UnsupportedOperationException(s"Cannot list files for $dirURL")
    }

  }

}
