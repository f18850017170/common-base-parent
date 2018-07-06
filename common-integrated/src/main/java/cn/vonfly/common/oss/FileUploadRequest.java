package cn.vonfly.common.oss;

public class FileUploadRequest {
	private FileType type;//文件类型
	private String subDirectory;//文件子文件夹
	private String fileName;//文件名称
	private boolean isPrivate;//是否私有

	public FileUploadRequest(FileType type, String subDirectory, String fileName) {
		this.type = type;
		this.subDirectory = subDirectory;
		this.fileName = fileName;
	}

	public FileUploadRequest(FileType type, String subDirectory, String fileName, boolean isPrivate) {
		this.type = type;
		this.subDirectory = subDirectory;
		this.fileName = fileName;
		this.isPrivate = isPrivate;
	}

	public FileType getType() {
		return type;
	}

	public void setType(FileType type) {
		this.type = type;
	}

	public String getSubDirectory() {
		return subDirectory;
	}

	public void setSubDirectory(String subDirectory) {
		this.subDirectory = subDirectory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean aPrivate) {
		isPrivate = aPrivate;
	}
}
