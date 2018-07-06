package cn.vonfly.common.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * oss上传文件实现
 */
public class OssFileOperatorImpl implements FileOperator {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private AliOssConfig config;

	public OssFileOperatorImpl(AliOssConfig config) {
		this.config = config;
	}

	@Override
	public FileOperateResult<String> fileUpload(FileUploadRequest request, InputStream inputStream) {
		try {
			Assert.notNull(request.getType(), "上传文件类型不能为空");
			ObjectMetadata metadata = new ObjectMetadata();
			CannedAccessControlList acl = request.isPrivate() ?
					CannedAccessControlList.Private :
					CannedAccessControlList.PublicRead;
			metadata.setObjectAcl(acl);
			OSSClient ossClient = new OSSClient(config.getOssEndpoint(), config.getAccessKeyId(),
					config.getAccessKeySecret());
			String key = buildFileOssKey(request.getType(), request.getSubDirectory(), request.getFileName());
			boolean exist = false;
			try {
				exist = ossClient.doesObjectExist(config.getBucketName(), key);
			}catch (Exception e){
				logger.error("判断oss是否存在该文件,请求异常，BucketName={},key={}",config.getBucketName(),key,e);
			}
			int retryTime = 0;
			while (exist && retryTime < 5) {
				key = buildFileOssKey(request.getType(), request.getSubDirectory(), request.getFileName());
				exist = ossClient.doesObjectExist(config.getBucketName(), key);
				retryTime++;
			}
			if (!exist) {
				PutObjectResult result = ossClient
						.putObject(config.getBucketName(), key, inputStream, metadata);
				String ossFilePath = buildOssFilePath(config, key);
				logger.info("文件上传处理结束，文件类型={},上传文件={},生成文件地址={}", request.getType(), request.getFileName(),
						ossFilePath);
				if (null != result) {
					return new FileOperateResult<String>(ossFilePath);
				}
			}
			throw new IllegalStateException("文件上传处请求理失败");
		} finally {
			IOUtils.safeClose(inputStream);
		}
	}

	@Override
	public FileOperateResult<String> fileUpload(FileUploadRequest request, byte[] fileBytes) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
		return fileUpload(request, inputStream);
	}

	/***
	 * policy={
	 "Version": "1",
	 "Statement": [
	 {
	 "Effect": "Allow",
	 "Action": "oss:GetObject",
	 "Resource":"acs:oss:*:*:wawang/image/20180531/172318/7AFv1QCtM7.jpg",
	 "Condition": {
	 "IpAddress": {
	 "acs:SourceIp":"218.85.130.202"
	 }
	 }
	 }
	 ]
	 }
	 * @param fileOssPath oss中的文件访问地址
	 * @param sessionName 临时访问的会话名称
	 * @param expireTime 过期时间，单位分钟
	 * @param ip
	 * @return
	 */
	@Override
	public String generateStsUrl(String fileOssPath, String sessionName, int expireTime, String ip) {
		Assert.hasText(sessionName, "临时访问的会话名称不能为空");
		StringBuilder ossHostAddress = buildOssHostAddress(config);
		String hostAddress = ossHostAddress.toString();
		Assert.isTrue(fileOssPath.startsWith(hostAddress), "非法的oss文件访问地址，请检查是否与配置的ossEndpoint匹配");
		String fileKey = fileOssPath.replace(hostAddress + "/", "");
		try {
			DefaultProfile.addEndpoint("", "", "Sts", config.getStsEndpoint());
			IClientProfile profile = DefaultProfile
					.getProfile("", config.getAccessKeyId(), config.getAccessKeySecret());
			DefaultAcsClient client = new DefaultAcsClient(profile);
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setMethod(MethodType.POST);
			request.setRoleArn(config.getRoleArn());
			request.setRoleSessionName(sessionName);
			StringBuilder defPolicy = new StringBuilder();
			defPolicy.append("{");
			defPolicy.append("\"Version\": \"1\",");
			defPolicy.append("\"Statement\": [");
			defPolicy.append("{");
			defPolicy.append("\"Effect\": \"Allow\",");
			defPolicy.append("\"Action\": \"oss:GetObject\",");
			defPolicy.append("\"Resource\":\"acs:oss:*:*:").append(config.getBucketName()).append("/").append(fileKey)
					.append("\",");
			defPolicy.append("\"Condition\": {");
			defPolicy.append("\"IpAddress\": {");
			defPolicy.append("\"acs:SourceIp\":\"").append(ip).append("\"");
			defPolicy.append("}");
			defPolicy.append("}");
			defPolicy.append("}");
			defPolicy.append("]");
			defPolicy.append("}");
			request.setPolicy(defPolicy.toString()); // Optional
			final AssumeRoleResponse response = client.getAcsResponse(request);
			OSSClient ossClient = new OSSClient(config.getOssEndpoint(), response.getCredentials().getAccessKeyId(),
					response.getCredentials().getAccessKeySecret(), response.getCredentials().getSecurityToken());
			// 设置URL过期时间为1小时
			Date expiration = new Date(new Date().getTime() + 60 * expireTime * 1000);
			// 生成URL。
			URL url = ossClient.generatePresignedUrl(config.getBucketName(), fileKey, expiration);
			ossClient.shutdown();
			return url.toString();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 构建oss访问地址
	 * @param config
	 * @param key
	 * @return https://wawang.oss-cn-shanghai.aliyuncs.com/image/wawang.jpg
	 */
	private String buildOssFilePath(AliOssConfig config, String key) {
		StringBuilder ossFilePath = buildOssHostAddress(config);
		if (!config.getOssEndpoint().endsWith("/")) {
			ossFilePath.append("/");
		}
		ossFilePath.append(key);
		return ossFilePath.toString();
	}

	/**
	 * 构建oss访问的hostAddress
	 * @param config
	 * @return
	 */
	private StringBuilder buildOssHostAddress(AliOssConfig config) {
		StringBuilder ossFilePath = new StringBuilder();
		String regex = "//";
		String[] split = config.getOssEndpoint().split(regex);
		Assert.isTrue(split.length == 2, "ossEndpoint配置值不合法");
		ossFilePath.append(split[0]);
		ossFilePath.append(regex);
		ossFilePath.append(config.getBucketName());
		ossFilePath.append(".");
		ossFilePath.append(split[1]);
		return ossFilePath;
	}

	/**
	 * 构建文件的key
	 * @param type
	 * @param subDirectory
	 * @param fileName
	 * @return
	 */
	private String buildFileOssKey(FileType type, String subDirectory, String fileName) {
		StringBuilder key = new StringBuilder().append(type.getType()).append("/");
		key.append(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)).append("/");
		if (StringUtils.isNotBlank(subDirectory)) {
			if (subDirectory.startsWith("/")) {
				String temp = subDirectory.substring(1);
				key.append(temp);
				if (!temp.endsWith("/")) {
					key.append("/");
				}
			}
		}
		key.append(RandomStringUtils.randomAlphanumeric(10));
		if (StringUtils.isNotBlank(fileName) && fileName.indexOf(".") > 0) {
			key.append(fileName.substring(fileName.lastIndexOf(".")));
		} else {
			if (FileType.IMAGE == type) {
				key.append(".jpg");
			}
		}
		return key.toString();
	}
}
