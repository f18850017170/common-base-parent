package cn.vonfly.common.provider;

import cn.vonfly.common.api.ILabelProvider;
import cn.vonfly.common.dto.common.LabelVo;
import cn.vonfly.common.dto.request.BasePageRequest;
import cn.vonfly.common.dto.response.BasePageResponse;
import cn.vonfly.metadata.model.Label;
import cn.vonfly.metadata.service.ILabelService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(version = ILabelProvider.VERSION)
public class LabelProviderImpl implements ILabelProvider{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ILabelService labelService;
	@Override
	public BasePageResponse<LabelVo> query(BasePageRequest<LabelVo> request) {
		logger.info("收到rpc请求=====");
		PageInfo<Label> pageInfo = labelService.selectAll();
		BasePageResponse<LabelVo> result = new BasePageResponse<LabelVo>();
		result.setPageNo(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPageSize());
		result.setTotal(pageInfo.getTotal());
		List<LabelVo> data = new ArrayList<>();
		if (!pageInfo.getList().isEmpty()){
			pageInfo.getList().forEach(item->{
				LabelVo labelVo = new LabelVo();
				BeanUtils.copyProperties(item,labelVo);
				data.add(labelVo);
			});
		}
		result.setData(data);
		return result;
	}
}
