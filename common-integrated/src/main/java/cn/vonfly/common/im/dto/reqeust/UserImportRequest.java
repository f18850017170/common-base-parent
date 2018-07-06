package cn.vonfly.common.im.dto.reqeust;

import cn.vonfly.common.im.config.ImRequestConfig;
import cn.vonfly.common.im.dto.response.DefApiResponse;

/**
 * 独立模式账号导入接口
 */
public class UserImportRequest extends ApiBaseRequest.AbsApiRequest<DefApiResponse> {
	private String identifier;//用户名，长度不超过 32 字节
	private String nick;//用户昵称
	private String faceUrl;//用户头像URL。
	private Integer type = 0;//帐号类型，开发者默认无需填写，值0表示普通帐号，1表示机器人帐号。

	public UserImportRequest(String identifier, String nick, String faceUrl) {
		this.identifier = identifier;
		this.nick = nick;
		this.faceUrl = faceUrl;
	}

	@Override
	public String getServiceName() {
		return ImRequestConfig.SERVICE_NAME_USER;
	}

	@Override
	public String getCommand() {
		return "account_import";
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getFaceUrl() {
		return faceUrl;
	}

	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
