package com.ai.client.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {
    private static Jedis jedis;
    public static Jedis getInstance(){
        if(jedis==null){
            jedis = new Jedis("127.0.0.1", 6379);
        }
        return jedis;
    }
    public static void main(String[] args){
        Jedis j=RedisUtil.getInstance();
        j.set("name","xinxin");//向key-->name中放入了value-->xinxin
        System.out.println(j.get("name"));//执行结果：xinxin
    }
}
