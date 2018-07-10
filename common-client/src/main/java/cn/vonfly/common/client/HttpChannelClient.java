package cn.vonfly.common.client;

import cn.vonfly.common.dto.common.CommonSettleApiConfig;
import cn.vonfly.common.dto.common.HttpApiConfig;
import cn.vonfly.common.dto.convert.Converter;
import cn.vonfly.common.dto.request.AbsBaseRequest;
import cn.vonfly.common.dto.request.HttpRequestWrap;
import cn.vonfly.common.dto.response.AbsBaseResponse;
import cn.vonfly.common.utils.GsonBuilderUtils;
import cn.vonfly.common.utils.HttpClientFactory;
import cn.vonfly.common.utils.TipAssert;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * http渠道client实现
 * @param <NReq>
 * @param <NResp>
 */
public abstract class HttpChannelClient<NReq, NResp> implements ChannelClient {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	protected volatile HttpApiConfig httpApiConfig;
	private final Map<Class, Converter> converterMap = Maps.newHashMap();

	/**
	 * 构造器
	 * @param converterList
	 */
	public HttpChannelClient(List<Converter> converterList) {
		if (null != converterList && !converterList.isEmpty()) {
			for (Converter converter : converterList) {
				converterMap.put(converter.requestClass(), converter);
			}
		}
	}

	/**
	 * 注入apiConfig
	 * @param config
	 */
	public void registerApiConfig(CommonSettleApiConfig config) {
		HttpApiConfig apiConfig = build(config);
		this.httpApiConfig = apiConfig;
	}

	/**
	 * 具体通道具体构建
	 * @param config
	 * @return
	 */
	protected abstract HttpApiConfig build(CommonSettleApiConfig config);

	@Override
	public <Req extends AbsBaseRequest<Resp>, Resp extends AbsBaseResponse> Resp execute(Req request) {
		String requestUrl = null;
		HttpUriRequest httpUriRequest = null;
		CloseableHttpResponse closeableHttpResponse = null;
		String responseStr = null;
		Resp resp = null;
		HttpRequestWrap<NReq, NResp> requestWrap = null;
		try {
			Converter converter = converterMap.get(request.getClass());
			requestWrap = (HttpRequestWrap<NReq, NResp>) converter
					.requestConvert(request, httpApiConfig);
			requestUrl = requestWrap.buildRequestUrl();
			switch (requestWrap.getHttpMethod()) {
			case POST:
				httpUriRequest = new HttpPost(requestUrl);
				((HttpPost) httpUriRequest).setEntity(requestWrap.buildHttpEntity());
				break;
			}
			TipAssert.notNull(httpUriRequest, "暂不支持该http请求方式" + requestWrap.getHttpMethod());
			//添加请求头
			Map<String, String> map = requestWrap.requestHeads();
			if (null != map && !map.isEmpty()) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					httpUriRequest.addHeader(entry.getKey(), entry.getValue());
				}
			}
			//发起请求
			closeableHttpResponse = HttpClientFactory.getHttpClient().execute(httpUriRequest);

			TipAssert.isTrue(HttpStatus.SC_OK == closeableHttpResponse.getStatusLine().getStatusCode(),
					"http请求状态码异常，StatusCode=" + closeableHttpResponse.getStatusLine().getStatusCode());
			HttpEntity responseEntity = closeableHttpResponse.getEntity();
			responseStr = EntityUtils.toString(responseEntity, Consts.UTF_8);
			NResp nResp = requestWrap.buildResponse(responseStr);
			resp = (Resp) converter.responseConvert(nResp, httpApiConfig);
			logger.info("[HttpChannelClient]请求处理成功，requestUrl={}，send={}，receive={}，req={}，resp={}", requestUrl,
					null != requestWrap ? requestWrap.getRequestContent() : "null", responseStr,
					request.printContent(), GsonBuilderUtils.gson().toJson(resp));
			return resp;
		} catch (Exception e) {
			logger.error("[HttpChannelClient]http请求处理异常，requestUrl={}，send={}，receive={}，req={}，resp={}，异常信息：", requestUrl,
					null != requestWrap ? requestWrap.getRequestContent() : "null", responseStr,
					request.printContent(), GsonBuilderUtils.gson().toJson(resp), e);
			return request.buildFail(Throwables.getStackTraceAsString(e));
		} finally {
			if (null != closeableHttpResponse) {
				try {
					closeableHttpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
