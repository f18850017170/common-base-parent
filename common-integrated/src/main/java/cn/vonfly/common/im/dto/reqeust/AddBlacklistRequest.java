package cn.vonfly.common.im.dto.reqeust;

import cn.vonfly.common.im.config.ImRequestConfig;
import cn.vonfly.common.im.dto.response.AddBlacklistResponse;
import cn.vonfly.common.im.utils.annotation.FieldAlias;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 添加黑名单请求
 */
public class AddBlacklistRequest extends ApiBaseRequest.AbsApiRequest<AddBlacklistResponse> {
	@FieldAlias("From_Account")
	private String fromAccount;//请求为该 Identifier 添加黑名单。
	@FieldAlias("To_Account")
	private List<String> toAccount;//待添加为黑名单的用户 Identifier 列表，单次请求的 To_Account 数不得超过 1000

	/**
	 * 构建添加黑名单请求
	 * @param userId
	 * @param targetUserId
	 */
	public AddBlacklistRequest(Long userId, Long targetUserId) {
		this.fromAccount = String.valueOf(userId);
		this.toAccount = Lists.newArrayList(String.valueOf(targetUserId));
	}

	@Override
	public String getServiceName() {
		return ImRequestConfig.SERVICE_NAME_SMS;
	}

	@Override
	public String getCommand() {
		return "black_list_add";
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public List<String> getToAccount() {
		return toAccount;
	}

	public void setToAccount(List<String> toAccount) {
		this.toAccount = toAccount;
	}
}
