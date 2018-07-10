package cn.vonfly.common.dto.convert;

import cn.vonfly.common.dto.request.AbsBaseRequest;
import cn.vonfly.common.dto.response.AbsBaseResponse;
import cn.vonfly.common.utils.SimpleReflectUtils;

/**
 * 抽象类
 * @param <Req>
 * @param <Resp>
 * @param <NReq>
 * @param <NResp>
 */
public abstract class AbsConverter<Req extends AbsBaseRequest,Resp extends AbsBaseResponse,NReq,NResp> implements Converter<Req,Resp,NReq,NResp>{
	@Override
	public Class requestClass() {
		return (Class<Resp>) SimpleReflectUtils.getGenericParameterType(getClass(), AbsBaseRequest.class)[0];
	}
}
