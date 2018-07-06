package cn.vonfly.common.im.dto.reqeust;

import cn.vonfly.common.im.config.ImRequestConfig;
import cn.vonfly.common.im.dto.response.DefApiResponse;
import cn.vonfly.common.im.utils.annotation.FieldAlias;
import com.google.common.collect.Lists;

import java.util.List;

public class ProfileUpdateRequest extends ApiBaseRequest.AbsApiRequest<DefApiResponse> {
	public static final String TAG_SIGNATURE="Tag_Profile_IM_SelfSignature";
	public static final String TAG_GENDER="Tag_Profile_IM_Gender";
	public static final String TAG_BIRTHDAY="Tag_Profile_IM_BirthDay";
	public static final String TAG_NICK="Tag_Profile_IM_Nick";
	public static final String GENDER_VALUE_MALE="Gender_Type_Male";
	public static final String GENDER_VALUE_FEMALE="Gender_Type_Female";
	public static final String GENDER_VALUE_UNKNOWN="Gender_Type_Unknown";


	@FieldAlias("From_Account")
	private String fromAccount;//需要设置该Identifier的资料。
	private List<ProfileItem> profileItem;//待设置的用户的资料对象数组

	/**
	 * 构建头像更新请求
	 * @param userId
	 * @param imImg
	 * @return
	 */
	public static ProfileUpdateRequest buildImImage(long userId, String imImg) {
		ProfileItem profileItem = new ProfileItem("Tag_Profile_IM_Image", imImg);
		ProfileUpdateRequest request = new ProfileUpdateRequest();
		request.setFromAccount(String.valueOf(userId));
		request.setProfileItem(Lists.newArrayList(profileItem));
		return request;
	}

	@Override
	public String getServiceName() {
		return ImRequestConfig.SERVICE_NAME_PROFILE;
	}

	@Override
	public String getCommand() {
		return "portrait_set";
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public List<ProfileItem> getProfileItem() {
		return profileItem;
	}

	public void setProfileItem(
			List<ProfileItem> profileItem) {
		this.profileItem = profileItem;
	}

	/**
	 * 待设置的用户的资料对象
	 */
	public static class ProfileItem {
		private String tag;//必填	指定要设置的资料字段的名称，支持设置的资料字段有：
		private Object value;//	待设置的资料字段的值

		public ProfileItem(String tag, Object value) {
			this.tag = tag;
			this.value = value;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
