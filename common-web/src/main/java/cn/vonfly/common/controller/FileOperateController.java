package cn.vonfly.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("file/")
public class FileOperateController {
	@RequestMapping(value = "upload", consumes = "multipart/form-data")
	public boolean upload(@RequestParam MultipartFile file){
		//TODO 存储文件
		return true;
	}
}
