package cn.vonfly.common.utils;

import org.apache.commons.codec.Charsets;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * http client工厂类
 */
public class HttpClientFactory {
	private static volatile CloseableHttpClient httpClient = null;

	/**
	 * https 请求默认信任所有证书
	 * 获取httpClient实例
	 * @return
	 */
	public static CloseableHttpClient getHttpClient(){
		if (null == httpClient){
			synchronized (HttpClientFactory.class){
				if (null == httpClient){
					RequestConfig.Builder config = RequestConfig.custom();
					config.setSocketTimeout(10000);
					config.setConnectTimeout(8000);
					config.setConnectionRequestTimeout(10000);
					HttpClientBuilder builder = HttpClientBuilder.create();
					builder.setDefaultRequestConfig(config.build());
					SSLContext sslContext = null;
					try {
						TrustManager[] trustManagers = new TrustManager[]{
								new X509TrustManager() {

									/**
									 * 该方法体为空时信任所有客户端证书
									 * @param x509Certificates
									 * @param s
									 * @throws CertificateException
									 */
									@Override
									public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws
											CertificateException {
									}

									/**
									 * 该方法体为空时信任所有服务器证书
									 * @param x509Certificates
									 * @param s
									 * @throws CertificateException
									 */
									@Override
									public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
									}

									/**
									 * 返回信任的证书
									 * @return
									 */
									@Override
									public X509Certificate[] getAcceptedIssuers() {
										return null;
									}
								}
						};
						sslContext = SSLContext.getInstance("TLS");
						sslContext.init(null, trustManagers, new SecureRandom());

					} catch (Exception e) {
						e.printStackTrace();
					}
					Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
							.<ConnectionSocketFactory>register("https", new SSLConnectionSocketFactory(sslContext))
							.<ConnectionSocketFactory>register("http",new PlainConnectionSocketFactory())
							.build();
					PoolingHttpClientConnectionManager pools = new PoolingHttpClientConnectionManager();
					pools.setDefaultMaxPerRoute(20);
					pools.setMaxTotal(200);
					builder.setConnectionManager(pools);
					httpClient = builder.build();
				}
			}
		}
		return httpClient;
	}

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClientFactory.getHttpClient();
		try {
			CloseableHttpResponse response = httpClient.execute(new HttpGet("http://www.baidu.com"));
			String result = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}