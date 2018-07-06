package cn.vonfly.common.api;

import cn.vonfly.common.dto.common.CustomerVo;
import cn.vonfly.common.dto.request.BasePageRequest;
import cn.vonfly.common.dto.response.BasePageResponse;

public interface ICustomerProvider extends IGenericProvider{
	/**
	 * 请求处理
	 * @param request
	 * @return
	 */
	BasePageResponse<CustomerVo> query(BasePageRequest<CustomerVo> request);
}
