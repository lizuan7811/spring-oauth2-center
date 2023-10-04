package spring.boot.oath2.redis;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import spring.boot.oath2.commons.properties.CommonProperties;
import java.util.HashMap;
import java.util.Map;
@Component
public class RedisConnUtil {

    @Autowired
    private final CommonProperties commonProperties;
    public RedisConnUtil(CommonProperties commonProperties){
        this.commonProperties=commonProperties;
        init();
    }

    public String init(){
        JedisPool pool = new JedisPool(commonProperties.getRedisHost(), commonProperties.getRedisPort(),"default",commonProperties.getRedisAuth());
        String result= Strings.EMPTY;
        try (Jedis jedis = pool.getResource()) {
            // Store & Retrieve a simple string
            jedis.set("foo", "bar");
            System.out.println(jedis.get("foo")); // prints bar

            // Store & Retrieve a HashMap
            Map<String, String> hash = new HashMap<>();;
            hash.put("name", "John");
            hash.put("surname", "Smith");
            hash.put("company", "Redis");
            hash.put("age", "29");
            jedis.hset("user-session:123", hash);
            System.out.println(jedis.hgetAll("user-session:123"));
            result=jedis.hgetAll("user-session:123").toString();
            // Prints: {name=John, surname=Smith, company=Redis, age=29}

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public void test(){
//        System.out.println("TEST JEDIS");
//        jedis.getPool().get
//        jedis.set("foo", "bar");
//        System.out.println(jedis.get("foo")); // prints bar
//
//        // Store & Retrieve a HashMap
//        Map<String, String> hash = new HashMap<>();;
//        hash.put("name", "John");
//        hash.put("surname", "Smith");
//        hash.put("company", "Redis");
//        hash.put("age", "29");
//       ("user-session:123", hash);
//        System.out.println(jedis.hgetAll("user-session:123"));



    }


}
