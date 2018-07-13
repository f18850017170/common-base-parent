package cn.vonfly.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MultipartConfig {

	/**
	 * 文件上传临时路径（解决默认文件夹被os删除的问题）
	 */
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		Path path = Paths.get(System.getProperty("user.dir"));
		Path temp = path.resolveSibling("data/tmp");
		File tmpFile = temp.toFile();
		if (!tmpFile.exists()) {
			tmpFile.mkdirs();
		}
		factory.setLocation(temp.toString());
		return factory.createMultipartConfig();
	}
}
