package cn.vonfly.common.im.dto.reqeust;

import cn.vonfly.common.im.client.ImApiClient;
import cn.vonfly.common.im.config.ImRequestConfig;
import cn.vonfly.common.im.dto.response.BaseApiResponse;
import cn.vonfly.common.im.sig.DefUserSigManager;
import cn.vonfly.common.im.utils.ImSpecifyGsonBuilder;
import cn.vonfly.common.im.utils.annotation.FieldAlias;
import cn.vonfly.common.im.utils.annotation.JsonIgnore;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * api请求基本信息
 * @param <T>
 */
public class ApiBaseRequest<T extends ApiBaseRequest.AbsApiRequest> {
	@JsonIgnore
	private T data;
	@JsonIgnore
	private String url;
	@JsonIgnore
	private String ver = "v4";

	@FieldAlias("contenttype")
	private String contentType = "json";//

	@FieldAlias("sdkappid")
	private String sdkAppId;

	@FieldAlias("identifier")
	private String identifier;

	@FieldAlias("usersig")
	private String userSig;
	private String random = RandomStringUtils.randomNumeric(32);

	/**
	 * 请求构造器
	 * @param config
	 * @param data
	 */
	public ApiBaseRequest(ImRequestConfig config, T data) {
		this.url = config.getUrl();
		this.sdkAppId = config.getSdkAppId();
		this.identifier = config.getIdentifier();
		this.userSig = DefUserSigManager.getInstance().getOrGenAvailable(Long.parseLong(sdkAppId), identifier)
				.getUserSig();
		this.data = data;
	}

	/**
	 * 构建完整的请求地址
	 * @return
	 */
	public String buildFullyUrl() {
		String url = getUrl();
		Assert.hasText(url, "请求地址不能为空");
		StringBuilder fullyUrl = new StringBuilder();
		fullyUrl.append(url);
		String urlSeparateFlag = "/";
		if (!url.endsWith(urlSeparateFlag)) {
			fullyUrl.append(urlSeparateFlag);
		}
		fullyUrl.append(getVer()).append(urlSeparateFlag);
		fullyUrl.append(getData().getServiceName());
		String command = getData().getCommand();
		if (!command.startsWith(urlSeparateFlag)) {
			fullyUrl.append(urlSeparateFlag);
		}
		fullyUrl.append(command);
		String json = ImSpecifyGsonBuilder.build().toJson(this);
		Map<String, String> keyValueMap = ImSpecifyGsonBuilder.build()
				.fromJson(json, new TypeToken<Map<String, String>>() {
				}.getType());
		fullyUrl.append("?");
		keyValueMap.forEach((key, value) -> {
			fullyUrl.append(key).append("=").append(value).append("&");
		});
		return fullyUrl.toString().substring(0, fullyUrl.length() - 1);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getSdkAppId() {
		return sdkAppId;
	}

	public void setSdkAppId(String sdkAppId) {
		this.sdkAppId = sdkAppId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getUserSig() {
		return userSig;
	}

	public void setUserSig(String userSig) {
		this.userSig = userSig;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * 抽象请求类
	 */
	public static abstract class AbsApiRequest<R extends BaseApiResponse> {
		/**
		 * 内部服务名，不同的 servicename 对应不同的服务类型
		 * @return
		 */
		public abstract String getServiceName();

		/**
		 * 命令字，与 servicename 组合用来标识具体的业务功能。
		 * @return
		 */
		public abstract String getCommand();

		/**
		 * 获取返回结果class
		 * @return
		 */
		public Class<R> responseClass() {
			Type genericSuperclass = getClass().getGenericSuperclass();
			while (!(genericSuperclass instanceof ParameterizedType) && !genericSuperclass
					.equals(AbsApiRequest.class)) {
				genericSuperclass = ((Class) genericSuperclass).getGenericSuperclass();
			}
			Assert.isTrue(genericSuperclass instanceof ParameterizedType, "AbsApiRequest实现类，未指定泛型<R>的真实类型");
			ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			return (Class<R>) actualTypeArguments[0];
		}

		/**
		 * 失败返回
		 * @param errorCode
		 * @param errorInfo
		 * @return
		 */
		public R fail(int errorCode, String errorInfo) {
			Class<R> responseClass = responseClass();
			try {
				R instance = responseClass.newInstance();
				instance.setErrorCode(errorCode);
				instance.setErrorInfo(errorInfo);
				instance.setActionStatus(BaseApiResponse.ACTION_STATUS_FAIL);
				return instance;
			} catch (Exception e) {
				throw new RuntimeException("构建失败返回信息异常", e);
			}

		}
	}

	public static void main(String[] args) {
		ImRequestConfig config = new ImRequestConfig();
		config.setIdentifier("xxxxxx");
		config.setSdkAppId("xxxxxxx");
		config.setUrl("https://console.tim.qq.com");
		ApiBaseRequest<AddFriendsRequest> baseRequest = new ApiBaseRequest<>(config,
				new AddFriendsRequest(1112l, 2222l));
		BaseApiResponse response = ImApiClient.execute(baseRequest);
		System.out.println(response);
	}
}
