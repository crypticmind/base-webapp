package ar.com.crypticmind.basewebapp.misc

import cacheable._
import ehcache._
import scala.concurrent.duration.Duration
import org.slf4j.LoggerFactory

object CacheRegistry extends Enumeration {
  case class CacheName(name: String) extends Val(name)
  implicit def valueToCacheName(v: Value): CacheName = v.asInstanceOf[CacheName]

  val Users = CacheName("users")
}

trait CacheComponent {

  def cacheSupport: CacheSupport

  trait CacheSupport {

    def cacheFor(cacheName: CacheRegistry.CacheName): Cache

  }

  class EhCacheSupport extends CacheSupport {

    val log = LoggerFactory.getLogger(getClass)
    val cacheManager = new net.sf.ehcache.CacheManager

    CacheRegistry.values.foreach { cacheName ⇒
      if (!cacheManager.cacheExists(cacheName.name))
        log.error(s"EhCache configuration does not have cache $cacheName configured")
    }

    def cacheFor(cacheName: CacheRegistry.CacheName): Cache = {
      Option(cacheManager.getCache(cacheName.name)) match {
        case Some(ehcache) ⇒ EhcacheCache(ehcache)
        case None ⇒
          log.warn(s"Cache name $cacheName unrecognized, provided an oblivious cache. Check EhCache configuration.")
          ObliviousCache
      }
    }

    sys.addShutdownHook {
      cacheManager.shutdown()
    }
  }

  class ObliviousSupport extends CacheSupport {

    def cacheFor(cacheName: CacheRegistry.CacheName): Cache = ObliviousCache

  }

  object ObliviousCache extends Cache {

    override def get[V](key: String): Option[V] = None

    override def put[V](key: String, value: V, ttl: Option[Duration]) {}

  }

}
