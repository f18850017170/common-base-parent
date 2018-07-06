package cn.vonfly.common.im.dto.reqeust;

import cn.vonfly.common.im.config.ImRequestConfig;
import cn.vonfly.common.im.dto.response.AddFriendsResponse;
import cn.vonfly.common.im.utils.annotation.FieldAlias;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 添加好友请求
 */
public class AddFriendsRequest extends ApiBaseRequest.AbsApiRequest<AddFriendsResponse>{
	private String addType = "Add_Type_Both"; //加好友方式（默认双向加好友方式）："Add_Type_Single" 表示单向加好友；"Add_Type_Both" 表示双向加好友。
	private Integer forceAddFlags;//管理员强制加好友标记：1 表示强制加好友；0 表示常规加好友方式。
	@FieldAlias("From_Account")
	private String fromAccount;//需要为该 Identifier 添加好友。
	private List<AddFriendItem> addFriendItem;//好友结构体对象。

	public AddFriendsRequest(Long userId, Long targetUserId) {
		List<AddFriendItem> items = Lists.newArrayList();
		AddFriendItem item = new AddFriendItem();
		item.setToAccount(targetUserId.toString());
		items.add(item);
		this.addFriendItem = items;
		this.fromAccount = userId.toString();
	}

	@Override
	public String getServiceName() {
		return ImRequestConfig.SERVICE_NAME_SMS;
	}

	@Override
	public String getCommand() {
		return "friend_add";
	}

	/**
	 * 好友结构体对象。
	 */
	public static class AddFriendItem {
		@FieldAlias("To_Account")
		private String toAccount;//好友的 Identifier。 AddSource_Type_XXXXXXXX
		private String remark;//From_Account 对 To_Account 的好友备注，详情可参见 标配好友字段。
		private String groupName;//From_Account 对 To_Account 的分组信息，详情可参见标配好友字段。
		private String addSource = "AddSource_Type_wawang";//加好友来源字段，详情可参见 标配好友字段。
		private String addWording;//From_Account 和 To_Account 形成好友关系时的附言信息，详情可参见 标配好友字段

		public String getToAccount() {
			return toAccount;
		}

		public void setToAccount(String toAccount) {
			this.toAccount = toAccount;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}

		public String getAddSource() {
			return addSource;
		}

		public void setAddSource(String addSource) {
			this.addSource = addSource;
		}

		public String getAddWording() {
			return addWording;
		}

		public void setAddWording(String addWording) {
			this.addWording = addWording;
		}
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public Integer getForceAddFlags() {
		return forceAddFlags;
	}

	public void setForceAddFlags(Integer forceAddFlags) {
		this.forceAddFlags = forceAddFlags;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public List<AddFriendItem> getAddFriendItem() {
		return addFriendItem;
	}

	public void setAddFriendItem(List<AddFriendItem> addFriendItem) {
		this.addFriendItem = addFriendItem;
	}
}
