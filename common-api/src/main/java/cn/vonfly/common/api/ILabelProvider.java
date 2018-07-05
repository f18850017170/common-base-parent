package cn.vonfly.common.api;

import cn.vonfly.common.dto.common.LabelVo;
import cn.vonfly.common.dto.request.BasePageRequest;
import cn.vonfly.common.dto.response.BasePageResponse;

public interface ILabelProvider extends IGenericProvider{
//	String VERSION = "0.0.2";

	/**
	 * 请求处理
	 * @param request
	 * @return
	 */
	BasePageResponse<LabelVo> query(BasePageRequest<LabelVo> request);
}
