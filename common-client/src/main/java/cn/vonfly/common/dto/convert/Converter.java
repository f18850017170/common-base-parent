package cn.vonfly.common.dto.convert;

import cn.vonfly.common.dto.common.HttpApiConfig;
import cn.vonfly.common.dto.request.AbsBaseRequest;
import cn.vonfly.common.dto.request.RequestWrap;
import cn.vonfly.common.dto.response.AbsBaseResponse;

public interface Converter<Req extends AbsBaseRequest,Resp extends AbsBaseResponse,NReq,NResp> {
	/**
	 * 获取请求类
	 * @return
	 */
	Class<Req> requestClass();
	/**
	 * 请求转换
	 * @param req
	 * @param httpApiConfig
	 * @return
	 */
	RequestWrap<NReq,NResp> requestConvert(Req req, HttpApiConfig httpApiConfig);

	/**
	 * 返回转换
	 * @param nResp
	 * @param httpApiConfig
	 * @return
	 */
	Resp responseConvert(NResp nResp, HttpApiConfig httpApiConfig);
}
