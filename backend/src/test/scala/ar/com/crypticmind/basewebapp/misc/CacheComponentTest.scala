package ar.com.crypticmind.basewebapp.misc

import org.scalatest.{ Matchers, WordSpecLike }
import cacheable._
import ar.com.crypticmind.basewebapp.model.User
import scala.concurrent.duration.Duration
import scala.language.reflectiveCalls

class CacheComponentTest extends WordSpecLike with Matchers {

  class TestCache extends Cache {
    val map = scala.collection.mutable.HashMap[Any, Any]()
    override def get[V](key: String): Option[V] = map.get(key).asInstanceOf[Option[V]]
    override def put[V](key: String, value: V, ttl: Option[Duration]) {
      map.put(key, value)
    }
  }

  "A cache" should {

    val impl = new CacheComponent {
      def cacheSupport = new CacheSupport {
        def cacheFor(cacheName: CacheRegistry.CacheName): Cache = new TestCache
      }
      val usersCache = cacheSupport.cacheFor(CacheRegistry.Users).asInstanceOf[TestCache]
      implicit val cacheConfig = CacheConfig(usersCache)
      var getCount = 0
      def testGet_1: User = cacheable {
        getCount = getCount + 1
        User(1, "test-1")
      }
    }

    "cache a value" in {
      impl.testGet_1 should have(
        'id(1),
        'username("test-1"))
      impl.usersCache.map should have size 1
      impl.usersCache.map.values.head.asInstanceOf[User] should have(
        'id(1),
        'username("test-1"))
      impl.getCount should be(1)
    }

    "return the cache values afterwards" in {
      impl.testGet_1 should have(
        'id(1),
        'username("test-1"))
      impl.usersCache.map should have size 1
      impl.usersCache.map.values.head.asInstanceOf[User] should have(
        'id(1),
        'username("test-1"))
      impl.getCount should be(1)
    }

  }

}
