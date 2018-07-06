package cn.vonfly.common.oss;

import java.io.InputStream;

/**
 * 文件操作接口定义
 */
public interface FileOperator {
	/**
	 * 文件上传
	 * @Param FileUploadRequest 文件信息
	 * @param inputStream 文件流
	 * @return
	 */
	FileOperateResult<String> fileUpload(FileUploadRequest request, InputStream inputStream);

	/**
	 * 文件上传
	 * @Param FileUploadRequest 文件信息
	 * @param fileBytes
	 * @return
	 */
	FileOperateResult<String> fileUpload(FileUploadRequest request, byte[] fileBytes);

	/**
	 * 构建私有文件的临时访问链接
	 * @param fileOssPath oss中的文件访问地址
	 * @param sessionName 临时访问的会话名称
	 * @param expireTime 过期时间，单位分钟
	 * @return
	 */
	String generateStsUrl(String fileOssPath, String sessionName, int expireTime, String ip);
}
