package cn.vonfly.common.dto.enumcode;

public enum SimpleStatus implements EnumHandle{
	ENABLE(10,"启用"),
	DISABLE(40,"禁用");
	private int code;
	private String desc;

	SimpleStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public int code() {
		return code;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
