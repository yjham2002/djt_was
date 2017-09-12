package com.appg.djTalk.common.upload;

public class FileBean
{
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	private String	fileName		= "";
	private String	storedFileName	= "";
	private long	fileSize		= 0;
	private String	contentType		= "";
	private String	fileUrlPath		= "";
	private int width = 0;
	private int height = 0;
	private String fileType = ""; // IM : Image&Video / LO : Location / VR : Voice Record
	private String  extension = "";
	private int index = 0;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getStoredFileName()
	{
		return storedFileName;
	}

	public void setStoredFileName(String storedFileName)
	{
		this.storedFileName = storedFileName;
	}

	public long getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(long fileSize)
	{
		this.fileSize = fileSize;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getFileUrlPath()
	{
		return fileUrlPath;
	}

	public void setFileUrlPath(String fileUrlPath)
	{
		this.fileUrlPath = fileUrlPath;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileBean [fileName=" + fileName + ", storedFileName=" + storedFileName + ", fileSize=" + fileSize
				+ ", contentType=" + contentType + ", fileUrlPath=" + fileUrlPath + ", width=" + width + ", height="
				+ height + ", fileType=" + fileType + ", extension=" + extension + ", index=" + index + "]";
	}

	
}
