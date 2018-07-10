package cn.vonfly.common.dto.request;

import cn.vonfly.common.dto.enumcode.HttpMethod;
import org.apache.http.HttpEntity;

import java.io.IOException;
import java.util.Map;

/**
 * http请求
 * @param <NReq>
 * @param <NResp>
 */
public interface HttpRequestWrap<NReq,NResp> extends RequestWrap<NReq,NResp>{
	/**
	 * 构建请求地址
	 * @return
	 */
	String buildRequestUrl();

	/**
	 * http请求方式
	 * @return
	 */
	HttpMethod getHttpMethod();

	/**
	 * post请求时构建HttpEntity
	 * @return
	 */
	HttpEntity buildHttpEntity();

	/**
	 * 返回请求内容
	 * @return
	 */
	String getRequestContent();
	/**
	 * 构建返回结果
	 * @param responseStr
	 * @return
	 */
	NResp buildResponse(String responseStr) throws IOException;
	/**
	 * 请求头
	 * @return
	 */
	Map<String,String> requestHeads();
}
