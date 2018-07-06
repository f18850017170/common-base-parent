package cn.vonfly.common.im.dto.reqeust;

import cn.vonfly.common.im.config.ImRequestConfig;
import cn.vonfly.common.im.dto.response.DelFriendsResponse;
import cn.vonfly.common.im.utils.annotation.FieldAlias;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 删除好友请求
 */
public class DelFriendsRequest extends ApiBaseRequest.AbsApiRequest<DelFriendsResponse> {
	@FieldAlias("From_Account")
	private String fromAccount;//需要删除该 Identifier 的好友。
	@FieldAlias("To_Account")
	private List<String> toAccount;//待删除的好友的 Identifier 列表，单次请求的 To_Account 数不得超过 1000
	private String deleteType = "Delete_Type_Both";//删除模式

	public DelFriendsRequest(Long userId, Long targetUserId) {
		this.fromAccount = String.valueOf(userId);
		this.toAccount = Lists.newArrayList(String.valueOf(targetUserId));
	}

	@Override
	public String getServiceName() {
		return ImRequestConfig.SERVICE_NAME_SMS;
	}

	@Override
	public String getCommand() {
		return "friend_delete";
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

	public String getDeleteType() {
		return deleteType;
	}

	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}
}
