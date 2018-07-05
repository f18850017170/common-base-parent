package cn.vonfly;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//可以此处指定dao包位置，也可以dao使用@Mapper注解
//@MapperScan("cn.vonfly.metadata.dao")
@SpringBootApplication
@EnableDubboConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
