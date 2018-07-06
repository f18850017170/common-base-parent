package cn.vonfly.common.oss;

/**
 * 阿里云Oss配置类
 */
public class AliOssConfig {
	private String accessKeyId;//图片上传的key 阿里云建议使用子账号，而且非主账号
	private String accessKeySecret;//图片上传的secret 阿里云建议使用子账号，而且非主账号
	private String ossEndpoint;//上传的阿里云oss地址
	private String bucketName;//存储空间
	private String roleArn;//授权角色（访问敏感图片时需要，走sts授权处理）
	private String StsEndpoint;//阿里云STS (Security Token Service) 授权地址

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getOssEndpoint() {
		return ossEndpoint;
	}

	public void setOssEndpoint(String ossEndpoint) {
		this.ossEndpoint = ossEndpoint;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getRoleArn() {
		return roleArn;
	}

	public void setRoleArn(String roleArn) {
		this.roleArn = roleArn;
	}

	public String getStsEndpoint() {
		return StsEndpoint;
	}

	public void setStsEndpoint(String stsEndpoint) {
		StsEndpoint = stsEndpoint;
	}
}
