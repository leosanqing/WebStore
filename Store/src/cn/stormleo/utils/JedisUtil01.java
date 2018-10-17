package cn.stormleo.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil01 {
    private static JedisPoolConfig config;
    private  static JedisPool pool;


    static {
        config=new JedisPoolConfig();
        config.setMaxIdle(2);
        config.setMaxTotal(30);

        pool= new JedisPool(config,"127.0.0.1",6379);

    }
    public  static Jedis getJedis(){
        return pool.getResource();
    }
    public static void closeJedis(Jedis jedis){
        jedis.close();
    }
}
