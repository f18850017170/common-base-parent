package cn.vonfly.common.controller;

import cn.vonfly.common.api.ILabelProvider;
import cn.vonfly.common.dto.common.LabelVo;
import cn.vonfly.common.dto.request.BasePageRequest;
import cn.vonfly.common.dto.response.BasePageResponse;
import cn.vonfly.metadata.model.Label;
import cn.vonfly.metadata.service.ILabelService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("label")
public class LabelController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
//	@Reference(url = "dubbo://127.0.0.1:20880",version = ILabelProvider.VERSION)
	@Reference(version = ILabelProvider.VERSION)
	private ILabelProvider labelProvider;
	@Autowired
	private ILabelService labelService;
	@Autowired
	private RedisTemplate stringRedisTemplate;
	@RequestMapping("insert")
	public void insert(Label label) {

		labelService.insert(label);
	}

	@RequestMapping("all")
	public PageInfo<Label> selectAll() {
		logger.info("info============");
		logger.debug("debug============");
		logger.warn("warn===========");
		logger.error("error===========");
		logger.trace("trace==========");
		return labelService.selectAll();
	}

	@RequestMapping("list")
	public BasePageResponse<LabelVo> list() {
		BasePageRequest<LabelVo> request = new BasePageRequest<LabelVo>(1,2,null);
		BasePageResponse<LabelVo> response = labelProvider.query(request);
		BoundValueOperations valueOps = stringRedisTemplate
				.boundValueOps(response.getData().iterator().next().getContent());
		//bound后，直接指定超时时间无效
		Boolean setIfAbsent = valueOps.setIfAbsent("0");
		valueOps.expire(30, TimeUnit.MINUTES);//设置超时时间需要在put之后
		valueOps.increment(1);
		System.out.println(valueOps.get());
		return response;
	}
}
