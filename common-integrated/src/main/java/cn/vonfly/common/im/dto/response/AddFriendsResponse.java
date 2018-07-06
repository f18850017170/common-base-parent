package cn.vonfly.common.im.dto.response;


import cn.vonfly.common.im.utils.annotation.FieldAlias;

import java.util.List;

/**
 * 添加好友结果
 */
public class AddFriendsResponse extends BaseApiResponse {
	public static final int INVALID_USER_FAIL = 30003;

	private List<ResultItem> resultItem;//批量加好友的结果对象数组。
	@FieldAlias("Fail_Account")
	private List<String> failAccount;//返回处理失败的用户列表
	@FieldAlias("Invalid_Account")
	private List<String> invalidAccount;

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
	 * 是否非法用户
	 * @return
	 */
	public boolean isInvalidUser() {
		return !isActionOk() && INVALID_USER_FAIL == getErrorCode();
	}

	/**
	 * 加好友的结果对象
	 */
	public static class ResultItem {
		@FieldAlias(" To_Account")
		private String toAccount;//请求添加的好友的 Identifier。
		private Integer resultCode;//批量加好友中单个好友的处理结果，0 表示业务成功，非 0 表示业务失败。
		private String ResultInfo;

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
			return ResultInfo;
		}

		public void setResultInfo(String resultInfo) {
			ResultInfo = resultInfo;
		}
	}
}
