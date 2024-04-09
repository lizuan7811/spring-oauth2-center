package spring.boot.oath2.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import spring.boot.oath2.commons.properties.CommonProperties;

  /**
    * description: RedisConnBuilder
    * author: Lizuan
    * date: 2023/11/26
    * time: 00:48:18
    **/
@Component
@SuperBuilder
@AllArgsConstructor
public class RedisConnBuilder {

    private final static JedisConn jedisConn = JedisConn.builder().build();
  /**
    * description: 存資料 取資料方法
    * author: Lizuan
    * date: 2023/11/26
    * time: 00:47:41
    **/
    public static void setUserIdentifier(String key,String value) {
        try (Jedis redis = jedisConn.pool.getResource()) {
            redis.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  /**
    * description: 查資料
    * author: Lizuan
    * date: 2023/11/26
    * time: 00:47:54
    **/
    public static String getUserIdentifier(String key) {
        String result= Strings.EMPTY;
        try (Jedis redis = jedisConn.pool.getResource()) {
            result=redis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

      public static void delIdentifier(String key) {
          try (Jedis redis = jedisConn.pool.getResource()) {
              redis.del(key);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }

      public static boolean checkExist(String key) {
        boolean isExist=false;
          try (Jedis redis = jedisConn.pool.getResource()) {
              isExist= redis.exists(key);
          } catch (Exception e) {
              e.printStackTrace();
          }
          return isExist;
      }




  /**
    * description: JedisConn
    * author: Lizuan
    * date: 2023/11/26
    * time: 00:48:08
    **/
    @AllArgsConstructor
    @NoArgsConstructor
    @Component
    @SuperBuilder
    private static class JedisConn {

        @Autowired
        private CommonProperties commonProperties;

        private JedisPool pool;

        public JedisConn(CommonProperties commonProperties) {
            this.commonProperties = commonProperties;
            this.pool = initJedis();
        }

        private JedisPool initJedis() {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(commonProperties.getMaxTotal());
            poolConfig.setMaxIdle(commonProperties.getMaxIdel());
            return new JedisPool(poolConfig, commonProperties.getRedisHost(), commonProperties.getRedisPort(), "default", commonProperties.getRedisAuth());
        }
    }
}
