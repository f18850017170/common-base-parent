package cn.vonfly.common.oss;

/**
 * 文件类型
 */
public enum FileType {
	IMAGE("image","图片"),
	DOC("doc","文档"),
	VIDEO("video","视频");
	private String type;
	private String desc;

	FileType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}
}
