package com.appg.djTalk.common.upload;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.appg.djTalk.common.constants.Constants;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

public class FileUpload
{
	private final boolean	mIsDebug				= Constants.IS_DEBUG;
	private final String	mUploadFilePathOrigin	= Constants.UPLOAD_FILE_PATH_ORGIN;

	private final String	mImgResizePath_1080		= Constants.UPLOAD_FILE_PATH_1080;
	private final String	mImgResizePath_720		= Constants.UPLOAD_FILE_PATH_720;
	private final String	mImgResizePath_480		= Constants.UPLOAD_FILE_PATH_480;
	private final String	mImgResizePath_240		= Constants.UPLOAD_FILE_PATH_240;
	private final String	mImgResizePath_100		= Constants.UPLOAD_FILE_PATH_100;

	private final int		mImgSize_100			= 100;
	private final int		mImgSize_240			= 240;
	private final int		mImgSize_480			= 480;
	private final int		mImgSize_720			= 720;
	private final int		mImgSize_1080			= 1080;
	

	private final long		mUploadMaxSize			= 1024 * 1024 * 100;						// 100MB
	private final String[]	mPreventExtension		= { "php", "jsp", "java", "asp", "sh" };
	private final Log		logger					= LogFactory.getLog(FileUpload.class);

	/**
	 * 파일 업로드
	 * 
	 * @param request
	 * @return
	 * @throws FileUploadException
	 */
	public List<FileBean> saveUploadFiles(HttpServletRequest request) throws FileUploadException
	{
		List<FileBean> saveList = new ArrayList<FileBean>();
		saveList = this.execSaveUploadFiles(request, "", null);

		return saveList;
	}

	/**
	 * 파일 업로드
	 * 
	 * @param request
	 * @param isMakeDateFolder
	 * @return
	 * @throws FileUploadException
	 */
	public List<FileBean> saveUploadFiles(HttpServletRequest request, boolean isMakeDateFolder) throws FileUploadException
	{
		List<FileBean> saveList = new ArrayList<FileBean>();
		String subPath = "";

		if(isMakeDateFolder)
			subPath = getDatePath();

		saveList = this.execSaveUploadFiles(request, subPath, null);

		return saveList;
	}

	/**
	 * 파일 업로드
	 * 
	 * @param request
	 * @param isMakeDateFolder
	 * @param availExtension
	 * @return
	 * @throws FileUploadException
	 */
	public List<FileBean> saveUploadFiles(HttpServletRequest request, boolean isMakeDateFolder, String[] availExtension) throws FileUploadException
	{
		List<FileBean> saveList = new ArrayList<FileBean>();
		String subPath = "";

		if(isMakeDateFolder)
			subPath = getDatePath();

		saveList = this.execSaveUploadFiles(request, subPath, availExtension);

		return saveList;
	}

	/**
	 * 파일 업로드
	 * 
	 * @param request
	 * @param subPath
	 * @return
	 * @throws FileUploadException
	 */
	public List<FileBean> saveUploadFiles(HttpServletRequest request, String subPath) throws FileUploadException
	{
		List<FileBean> saveList = new ArrayList<FileBean>();

		saveList = this.execSaveUploadFiles(request, subPath, null);

		return saveList;
	}

	/**
	 * 파일 업로드
	 * 
	 * @param request
	 * @param subPath
	 * @return
	 * @throws FileUploadException
	 */
	public List<FileBean> saveUploadFiles(HttpServletRequest request, String subPath, boolean isMakeDateFolder) throws FileUploadException
	{
		List<FileBean> saveList = new ArrayList<FileBean>();

		if(isMakeDateFolder)
			subPath = getDatePath();

		saveList = this.execSaveUploadFiles(request, subPath, null);

		return saveList;
	}

	/**
	 * 파일 업로드
	 * 
	 * @param request
	 * @param subPath
	 * @param availExtension
	 * @return
	 * @throws FileUploadException
	 */
	public List<FileBean> saveUploadFiles(HttpServletRequest request, String subPath, String[] availExtension) throws FileUploadException
	{
		List<FileBean> saveList = new ArrayList<FileBean>();

		saveList = this.execSaveUploadFiles(request, subPath, availExtension);

		return saveList;
	}

	/**
	 * 실제 파일 업로드
	 * 
	 * @param request
	 * @throws FileUploadException
	 */
	private List<FileBean> execSaveUploadFiles(HttpServletRequest request, String subPath, String[] availExtension) throws FileUploadException
	{
		if( !(request instanceof MultipartHttpServletRequest) ) {
			return new ArrayList<FileBean>() ;
		}
		
		List<FileBean> saveList = new ArrayList<FileBean>();
		
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		List<MultipartFile> multipartFileList = multipartHttpServletRequest.getFiles("images[]");

		
		if(Constants.IS_DEBUG)
		{
			System.out.println("FILE NAMES => " + multipartHttpServletRequest.getFileNames());
			System.out.println("FILE MAP => " + multipartHttpServletRequest.getFileMap());
		}

		List<MultipartFile> uploadAbleFileList = new ArrayList<MultipartFile>();
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		String storePath = mUploadFilePathOrigin;
		int fileIndex = 0;
		
		// 서브 날짜 폴더 생성
		if(subPath != "")
		{
			storePath += "/" + subPath;
		}

		if(!makeDir(storePath))
			throw new FileUploadException(FileUploadException.FUECode.DO_NOT_MAKE_DIR_CODE, FileUploadException.FUECode.DO_NOT_MAKE_DIR_MSG);

		// 파일 사이즈 용량 체크 및 데이터 확인
		if(multipartFileList.size() > 0) {
		for(int i = 0; i < multipartFileList.size(); i++)
		{
			MultipartFile multipartFile = multipartFileList.get(i);
			if(multipartFile.isEmpty() == false)
			{
				if(mIsDebug)
				{
					logger.info("======== FILE DATA ========");
					logger.info("Name : " + multipartFile.getName());
					logger.info("FileName : " + multipartFile.getOriginalFilename());
					logger.info("ContentType : " + multipartFile.getContentType());
					logger.info("Size : " + multipartFile.getSize());
					logger.info("======== FILE DATA ========");
				}

				originalFileExtension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));

				// 업로드 사이트 체크
				if(multipartFile.getSize() > mUploadMaxSize || multipartFile.getSize() == 0)
					throw new FileUploadException(FileUploadException.FUECode.FILE_SIZE_ERROR_CODE, FileUploadException.FUECode.FILE_SIZE_ERROR_MSG);

				// 업로드 불가 파일 확장자 체크
				if(!isValidFileExtension(originalFileExtension, mPreventExtension, false))
					throw new FileUploadException(FileUploadException.FUECode.FILE_PREVENT_EXTENSION_ERROR_CODE, FileUploadException.FUECode.FILE_PREVENT_EXTENSION_ERROR_MSG);

				// 업로드 가능 파일 확장자 체크
				if(availExtension != null && !isValidFileExtension(originalFileExtension, availExtension, true))
					throw new FileUploadException(FileUploadException.FUECode.FILE_AVAIL_EXTENSION_ERROR_CODE, FileUploadException.FUECode.FILE_AVAIL_EXTENSION_ERROR_MSG);

				// 밸리데이션 통과한 파일 업로드 대상에 추가
				uploadAbleFileList.add(multipartFile);

			}
		}
		} else {
			while(iterator.hasNext())
			{
				MultipartFile multipartFile = multipartHttpServletRequest.getFile(iterator.next()); 
				if(multipartFile.isEmpty() == false)
				{
					if(mIsDebug)
					{
						logger.info("======== FILE DATA ========");
						logger.info("Name : " + multipartFile.getName());
						logger.info("FileName : " + multipartFile.getOriginalFilename());
						logger.info("ContentType : " + multipartFile.getContentType());
						logger.info("Size : " + multipartFile.getSize());
						logger.info("======== FILE DATA ========");
					}

					originalFileExtension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));

					// 업로드 사이트 체크
					if(multipartFile.getSize() > mUploadMaxSize || multipartFile.getSize() == 0)
						throw new FileUploadException(FileUploadException.FUECode.FILE_SIZE_ERROR_CODE, FileUploadException.FUECode.FILE_SIZE_ERROR_MSG);

					// 업로드 불가 파일 확장자 체크
					if(!isValidFileExtension(originalFileExtension, mPreventExtension, false))
						throw new FileUploadException(FileUploadException.FUECode.FILE_PREVENT_EXTENSION_ERROR_CODE, FileUploadException.FUECode.FILE_PREVENT_EXTENSION_ERROR_MSG);

					// 업로드 가능 파일 확장자 체크
					if(availExtension != null && !isValidFileExtension(originalFileExtension, availExtension, true))
						throw new FileUploadException(FileUploadException.FUECode.FILE_AVAIL_EXTENSION_ERROR_CODE, FileUploadException.FUECode.FILE_AVAIL_EXTENSION_ERROR_MSG);

					// 밸리데이션 통과한 파일 업로드 대상에 추가
					uploadAbleFileList.add(multipartFile);

				}
			}
		}

		// 파일 업로드
		for (MultipartFile multipartFile : uploadAbleFileList)
		{
			try
			{

				if(mIsDebug)
				{
					logger.info("======== UP TARGET FILE DATA ========");
					logger.info("Name : " + multipartFile.getName());
					logger.info("FileName : " + multipartFile.getOriginalFilename());
					logger.info("ContentType : " + multipartFile.getContentType());
					logger.info("Size : " + multipartFile.getSize());
					logger.info("======== UP TARGET FILE DATA ========");
				}
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = getExtension(originalFileName);
				storedFileName = this.getUniqueFileName(originalFileExtension);
				String fileUrlPath = ((subPath != "") ? subPath + "/" + storedFileName : storedFileName);
				String fileOrder =  multipartFile.getName().replaceAll("[^0-9]", "");

				fileIndex = fileOrder.equals("") ? 0 : Integer.parseInt(fileOrder);
				
				
				File file = new File(storePath + "/" + storedFileName);
				multipartFile.transferTo(file);

				int imageWidth = 0;
				int imageHeight = 0;
				
				// 이미지일 경우 리사이즈
				if(multipartFile.getContentType().startsWith("image/")){
					BufferedImage bimg = ImageIO.read(new File(storePath + "/" + storedFileName));
					
					if(bimg != null){
					
					imageWidth = bimg.getWidth();
					imageHeight = bimg.getHeight();
					}
					
					execImageResize(storePath + "/" + storedFileName, originalFileExtension, mImgResizePath_1080 + ((subPath != "") ? "/" + subPath : ""), storedFileName, mImgSize_1080);
					execImageResize(storePath + "/" + storedFileName, originalFileExtension, mImgResizePath_720 + ((subPath != "") ? "/" + subPath : ""), storedFileName, mImgSize_720);
					execImageResize(storePath + "/" + storedFileName, originalFileExtension, mImgResizePath_480 + ((subPath != "") ? "/" + subPath : ""), storedFileName, mImgSize_480);
					execImageResize(storePath + "/" + storedFileName, originalFileExtension, mImgResizePath_240 + ((subPath != "") ? "/" + subPath : ""), storedFileName, mImgSize_240);
					execImageResize(storePath + "/" + storedFileName, originalFileExtension, mImgResizePath_100 + ((subPath != "") ? "/" + subPath : ""), storedFileName, mImgSize_100);
				}

				FileBean fileBean = new FileBean();
				fileBean.setWidth(imageWidth);
				fileBean.setHeight(imageHeight);
				fileBean.setExtension(originalFileExtension);
				fileBean.setContentType(multipartFile.getContentType());
				fileBean.setFileName(originalFileName);
				fileBean.setFileSize(multipartFile.getSize());
				fileBean.setFileUrlPath(fileUrlPath);
				fileBean.setFileType("IM");
				fileBean.setStoredFileName(storedFileName);
				fileBean.setIndex(fileIndex);
				
				
				saveList.add(fileBean);

				Runtime.getRuntime().exec("chmod -R 755 " + storePath);
				
			}
			catch (IllegalStateException e)
			{
				e.printStackTrace();
				continue;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				continue;
			}

		}

		return saveList;
	}

	/**
	 * 파일 네임 생성
	 * 
	 * @return
	 */
	private String getUniqueFileName(String extension)
	{
		return UUID.randomUUID().toString().replaceAll("-", "") + "." + extension;
	}

	/**
	 * 경로 생성
	 * 
	 * @param file
	 * @return
	 */
	private boolean makeDir(String path)
	{

		File file = new File(path);

		if(file.exists() == false)
		{
			return file.mkdirs();
		}

		return true;
	}

	/**
	 * 날짜 경로 생성
	 * 
	 * @return
	 */
	private String getDatePath()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String ymd = df.format(cal.getTime());

		return ymd;
	}

	/**
	 * 확장자 체크
	 * 
	 * @param fileExtension
	 * @param extensions
	 * @return
	 * @throws Exception
	 */
	private boolean isValidFileExtension(String fileExtension, String[] extensions, boolean isAvailValid)
	{
		boolean result = false;
		if(fileExtension != null && !"".equals(fileExtension))
		{
			if(isAvailValid && isContainFileExtension(fileExtension, extensions))
			{
				result = true;
			}
			else if(!isAvailValid && !isContainFileExtension(fileExtension, extensions))
			{
				result = true;
			}
		}
		else
		{
			result = false;
		}

		return result;
	}

	private boolean isContainFileExtension(String fileExtension, String[] extensions)
	{
		boolean result = false;
		fileExtension = fileExtension.toLowerCase();
		for (String ex : extensions)
		{
			ex = ex.toLowerCase();
			if(fileExtension.equals(ex))
			{
				result = true;
				break;
			}
		}
		return result;
	}

	private String getExtension(String filename)
	{
		String extension = filename.substring(filename.lastIndexOf(".") + 1);

		return extension;
	}

	/**
	 * 이미지 리사이즈
	 * 
	 * @param originFile
	 * @param extension
	 * @param resizePath
	 * @param imageSize
	 */
	private void execImageResize(String originFilePath, String extension, String resizePath, String storedFileName, int imageSize)
	{

		if(!makeDir(resizePath))
			return;

		try
		{
			File targetFile = new File(resizePath + "/" + storedFileName);
			
			try {
				File originFile = new File(originFilePath);			
				BufferedImage uploadImage = ImageIO.read(originFile);
	
				BufferedImage resizeImage = Scalr.resize(uploadImage, imageSize);
				ImageIO.write(resizeImage, extension, targetFile);
				
				/*
				ImageInformation info = FileUpload.readImageInformation(new File(originFilePath)) ;
				fixedDegree = FileUpload.degreeFixOrientation(info) ;
				System.out.println("image infomation : " + info.orientation);
				*/				
				
				Runtime.getRuntime().exec("chmod -R 755 " + resizePath);

			}
			catch(Exception ex){
				
			}
			/*
			// 2. jpeg 대응하기 위해 파일을 imageICon을 이용해서 취득
			Image image = new ImageIcon(originFilePath).getImage() ;
	
			// 3. image rgb type 결정
			BufferedImage scaledImage = scaledImage = ImageIO.read(new File(originFilePath)) ; ;
			int type = (scaledImage.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB ;
			
			int width = scaledImage.getWidth() ;
			int height = scaledImage.getHeight() ;
			
			ImageIO.write(scaledImage, extension, targetFile);
			
			/*
			double multiStepRatio = 0.9 ;
			
			
			do{
				if(width > imageSize * 2)
				{
					width *= multiStepRatio ;
				}
				else
				{
					double resizeRatio = 1.0 ;
					resizeRatio = (double)imageSize / width ;
					
					width = (int)(width * resizeRatio) ;
					height = (int)(height * resizeRatio) ;
					
					// 3. 임시 buffer image 생성
					BufferedImage thumbImage = new BufferedImage(width, height, type) ;

					Graphics2D g2 = thumbImage.createGraphics() ;
					
					g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g2.setBackground(Color.WHITE);
					g2.clearRect(0, 0, width, height);
					g2.drawImage(image, 0, 0, width, height, null) ;
					g2.dispose() ;
					
					BufferedImage rotatedThumbImage = FileUpload.rotateImage(thumbImage, fixedDegree) ;
					
					
					
//					ImageIO.write(thumbImage, extension, targetFile);
					
					
					
					// 4. buffer write 
//					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
//			        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
//			        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);			        
//			        param.setQuality((float)100 / 100.0f, false);			        
//			        encoder.setJPEGEncodeParam(param);
//			        encoder.encode(thumbImage);			        
//			        bos.close();
//			        
//			        
//			        logger.info("3. finish");
			           
					
					scaledImage = rotatedThumbImage ;
				}
				
			} while(width > imageSize) ;
			*/
			 
			

			if(mIsDebug)
			{
				logger.info("originFilePath : " + originFilePath);
				logger.info("resizeExtension : " + extension);
				logger.info("resizePath : " + resizePath + "/" + storedFileName);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public static BufferedImage rotateImage(BufferedImage src, double degrees) {
	    double radians = Math.toRadians(degrees);

	    int srcWidth = src.getWidth();
	    int srcHeight = src.getHeight();

	    double sin = Math.abs(Math.sin(radians));
	    double cos = Math.abs(Math.cos(radians));
	    int newWidth = (int) Math.floor(srcWidth * cos + srcHeight * sin);
	    int newHeight = (int) Math.floor(srcHeight * cos + srcWidth * sin);

	    BufferedImage result = new BufferedImage(newWidth, newHeight,
	        src.getType());
	    Graphics2D g = result.createGraphics();
	    g.translate((newWidth - srcWidth) / 2, (newHeight - srcHeight) / 2);
	    g.rotate(radians, srcWidth / 2, srcHeight / 2);
	    g.drawRenderedImage(src, null);

	    return result;
	}
	
	public static class ImageInformation {
	    public final int orientation;
	    public final int width;
	    public final int height;

	    public ImageInformation(int orientation, int width, int height) {
	        this.orientation = orientation;
	        this.width = width;
	        this.height = height;
	    }

	}
	
	public static ImageInformation readImageInformation(File imageFile)  throws IOException, MetadataException, ImageProcessingException {
	    Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
	    ExifIFD0Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
	    JpegDirectory jpegDirectory = (JpegDirectory)metadata.getDirectory(JpegDirectory.class);

	    int orientation = 1;
	    try {
	        orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
	    } catch (MetadataException me) {
	        System.out.println("Could not get orientation");
	    }
	    int width = jpegDirectory.getImageWidth();
	    int height = jpegDirectory.getImageHeight();

	    return new ImageInformation(orientation, width, height);
	}
	
	public static double degreeFixOrientation(ImageInformation info) {

		double degree = 0 ;

	    switch (info.orientation) {
	    case 3: // PI rotation
	    	degree = Math.toDegrees(Math.PI) ;
	        break;
	    case 5: // - PI/2 and Flip X
	        degree = Math.toDegrees(-Math.PI / 2);
	        break;
	    case 6: // -PI/2 and -width
	    	degree = Math.toDegrees(Math.PI / 2);
	        break;
	    case 7: // PI/2 and Flip
	    	degree = Math.toDegrees( 3 * Math.PI / 2);
	        break;
	    case 8: // PI / 2
	    	degree = Math.toDegrees( 3 * Math.PI / 2);
	        break;
	    }

	    return degree;
	}

}
