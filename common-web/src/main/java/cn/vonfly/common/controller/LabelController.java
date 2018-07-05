package cn.vonfly.common.controller;

import cn.vonfly.common.api.ILabelProvider;
import cn.vonfly.common.dto.common.LabelVo;
import cn.vonfly.common.dto.request.BasePageRequest;
import cn.vonfly.common.dto.response.BasePageResponse;
import cn.vonfly.metadata.model.Label;
import cn.vonfly.metadata.service.ILabelService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("label")
public class LabelController {
//	@Reference(url = "dubbo://127.0.0.1:20880",version = ILabelProvider.VERSION)
	@Reference(version = ILabelProvider.VERSION)
	private ILabelProvider labelProvider;
	@Autowired
	private ILabelService labelService;

	@RequestMapping("insert")
	public void insert(Label label) {
		labelService.insert(label);
	}

	@RequestMapping("all")
	public PageInfo<Label> selectAll() {
		return labelService.selectAll();
	}

	@RequestMapping("list")
	public BasePageResponse<LabelVo> list() {
		BasePageRequest<LabelVo> request = new BasePageRequest<LabelVo>(1,2,null);
		return labelProvider.query(request);
	}
}
