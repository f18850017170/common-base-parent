package cn.vonfly;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//可以此处指定dao包位置，也可以dao使用@Mapper注解
//@MapperScan("cn.vonfly.metadata.dao")
@EnableDubboConfiguration
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
