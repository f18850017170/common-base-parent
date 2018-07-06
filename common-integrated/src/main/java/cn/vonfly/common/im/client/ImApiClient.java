package cn.vonfly.common.im.client;


import cn.vonfly.common.im.dto.reqeust.ApiBaseRequest;
import cn.vonfly.common.im.dto.response.BaseApiResponse;
import cn.vonfly.common.im.utils.ImSpecifyGsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * im api接口访问client
 */
public class ImApiClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImApiClient.class);

	/**
	 * im请求
	 * @param request
	 * @param <R>
	 * @param <D>
	 * @return
	 */
	public static <R extends BaseApiResponse, D extends ApiBaseRequest.AbsApiRequest> R execute(
			ApiBaseRequest<D> request) {
		String fullyUrl = null;
		String requestStr = null;
		String responseStr = null;
		boolean handleSuccess = false;
		try {
			CloseableHttpClient client = HttpClientFactory.getPoolHttpClient();
			fullyUrl = request.buildFullyUrl();
			HttpPost post = new HttpPost(fullyUrl);
			requestStr = ImSpecifyGsonBuilder.build().toJson(request.getData());
			StringEntity stringEntity = new StringEntity(requestStr, ContentType.APPLICATION_JSON);
			post.setEntity(stringEntity);
			CloseableHttpResponse httpResponse = client.execute(post);
			responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			R response = (R) ImSpecifyGsonBuilder.build().fromJson(responseStr, request.getData().responseClass());
			response.setResponseBody(responseStr);
			handleSuccess = true;
			return response;
		} catch (Exception e) {
			LOGGER.error("im请求处理异常，url={},requestStr={}", fullyUrl, requestStr, e);
			return (R) request.getData().fail(500, "im请求处理异常");
		} finally {
			LOGGER.info("im请求处理结束[{}],url={},requestStr={},responseStr={}", handleSuccess, fullyUrl, requestStr,
					responseStr);
		}

	}
}
