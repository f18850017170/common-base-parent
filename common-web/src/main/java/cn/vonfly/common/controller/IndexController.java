package cn.vonfly.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class IndexController {
	@RequestMapping("/current/time")
	public Date index(){
		return new Date();
	}
}
