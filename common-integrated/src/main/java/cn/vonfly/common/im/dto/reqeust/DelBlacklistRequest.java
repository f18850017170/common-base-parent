package cn.vonfly.common.im.dto.reqeust;

import cn.vonfly.common.im.config.ImRequestConfig;
import cn.vonfly.common.im.dto.response.DelBlacklistResponse;
import cn.vonfly.common.im.utils.annotation.FieldAlias;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 删除黑名单请求
 */
public class DelBlacklistRequest extends ApiBaseRequest.AbsApiRequest<DelBlacklistResponse>{
	@FieldAlias("From_Account")
	private String fromAccount;//需要删除该 Identifier 的黑名单。
	@FieldAlias("To_Account")
	private List<String> toAccount;//待删除的黑名单的 Identifier 列表，单次请求的 To_Account 数不得超过 1000

	public DelBlacklistRequest(Long userId, Long targetUserId) {
		this.fromAccount = String.valueOf(userId);
		this.toAccount = Lists.newArrayList(String.valueOf(targetUserId));
	}

	@Override
	public String getServiceName() {
		return ImRequestConfig.SERVICE_NAME_SMS;
	}

	@Override
	public String getCommand() {
		return "black_list_delete";
	}
}
