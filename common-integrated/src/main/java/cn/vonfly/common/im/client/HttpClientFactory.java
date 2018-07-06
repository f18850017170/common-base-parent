package cn.vonfly.common.im.client;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * 获取http
 */
public class HttpClientFactory {
	private static volatile CloseableHttpClient poolHttpClient = null;

	/**
	 * 获取池化httpclient
	 * @return
	 */
	public static CloseableHttpClient getPoolHttpClient() {
		if (null == poolHttpClient) {
			synchronized (HttpClientFactory.class) {
				if (null == poolHttpClient) {
					RequestConfig.Builder config = RequestConfig.custom();
					config.setSocketTimeout(10000);
					config.setConnectTimeout(8000);
					config.setConnectionRequestTimeout(10000);
					HttpClientBuilder builder = HttpClientBuilder.create();
					builder.setDefaultRequestConfig(config.build());
					PoolingHttpClientConnectionManager pools = new PoolingHttpClientConnectionManager();
					pools.setDefaultMaxPerRoute(20);
					pools.setMaxTotal(200);
					builder.setConnectionManager(pools);
					poolHttpClient = builder.build();
				}
			}
		}
		return poolHttpClient;
	}
}