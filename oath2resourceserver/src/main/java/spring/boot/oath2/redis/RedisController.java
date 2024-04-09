package spring.boot.oath2.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RedisController{

    private final RedisConnBuilder redisConnBuilder;

    @Autowired
    public RedisController(RedisConnBuilder redisConnBuilder){
        this.redisConnBuilder = redisConnBuilder;
    }

    @GetMapping("/rediscall")
    public String redisCall(){
        return "redisConnUtil.init()";
    }

}
