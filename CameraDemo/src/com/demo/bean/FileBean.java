package com.demo.bean;

/**
 * 附件实体类
 * @author hugs
 */
public class FileBean {
	private String fileName;// 文件名
	private String fileContent;// 文件的内容

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
}
