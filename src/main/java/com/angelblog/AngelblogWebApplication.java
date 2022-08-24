package com.angelblog;

import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.angelblog.project.*.*.mapper")
public class AngelblogWebApplication {

    public final static Logger log = LoggerFactory.getLogger(AngelblogWebApplication.class);
    public static void main(String[] args) throws UnknownHostException
    {
        ConfigurableApplicationContext application = SpringApplication.run(AngelblogWebApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        //String path = env.getProperty("server.servlet.context-path");

        log.info("\n----------------------------------------------------------\n\t" +
                "Application  is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port  + "\n\t" +
                "External: \thttp://" + ip + ":" + port  + "\n\t" +
                "-------------s---------------------------------------------");
    }





//    public static void main(String[] args) throws SchedulerException, InterruptedException {
//        SpringApplication.run(AngelblogWebApplication.class, args);
//
//
//
////        HashMap<String,String> ha =new HashMap<>();
////        ha.put("ke1","test1");
////        ha.put("ke2","test2");
////        ha.put("ke3","test3");
////        ha.put("ke4","test4");
////        for (String values : ha.values()) {
////            System.out.println(values);
////        }
////        System.out.println("=========================");
//
//       // System.out.println("ttttttttttttttttttttt");
//
//    }

}
