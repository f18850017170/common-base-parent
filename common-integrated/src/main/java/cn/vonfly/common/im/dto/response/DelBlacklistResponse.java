package cn.vonfly.common.im.dto.response;


import cn.vonfly.common.im.utils.annotation.FieldAlias;

import java.util.List;

/**
 * 删除黑名单请求返回结果
 */
public class DelBlacklistResponse extends BaseApiResponse{
	private List<ResultItem> resultItem;//批量删除好友的结果对象数组
	@FieldAlias("Fail_Account")
	private List<String> failAccount;//返回处理失败的 To_Account列表。
	@FieldAlias("Invalid_Account")
	private List<String> invalidAccount;//返回请求包中的非法 To_Account 列表。

	public List<ResultItem> getResultItem() {
		return resultItem;
	}

	public void setResultItem(List<ResultItem> resultItem) {
		this.resultItem = resultItem;
	}

	public List<String> getFailAccount() {
		return failAccount;
	}

	public void setFailAccount(List<String> failAccount) {
		this.failAccount = failAccount;
	}

	public List<String> getInvalidAccount() {
		return invalidAccount;
	}

	public void setInvalidAccount(List<String> invalidAccount) {
		this.invalidAccount = invalidAccount;
	}

	/**
	 * 详情
	 */
	public static class ResultItem {
		@FieldAlias(" To_Account")
		private String toAccount;//请求删除的好友的 Identifier。
		private Integer resultCode;//To_Account 的处理结果，0 表示删除成功，非 0 表示删除失败。
		private String resultInfo;//To_Account 的处理详情。

		public String getToAccount() {
			return toAccount;
		}

		public void setToAccount(String toAccount) {
			this.toAccount = toAccount;
		}

		public Integer getResultCode() {
			return resultCode;
		}

		public void setResultCode(Integer resultCode) {
			this.resultCode = resultCode;
		}

		public String getResultInfo() {
			return resultInfo;
		}

		public void setResultInfo(String resultInfo) {
			this.resultInfo = resultInfo;
		}
	}
}
