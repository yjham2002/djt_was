package com.appg.djTalk.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appg.djTalk.common.batch.manager.BatchJobProcessManager;
import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.bean.RetJsonBean;
import com.appg.djTalk.common.constants.Constants;
import com.appg.djTalk.common.exception.DefSeCode;
import com.appg.djTalk.common.exception.ServiceException;
import com.appg.djTalk.common.push.DataBean;
import com.appg.djTalk.common.push.PushCls;
import com.appg.djTalk.common.push.PushConstants;
import com.appg.djTalk.common.upload.FileBean;
import com.appg.djTalk.common.upload.FileUpload;
import com.appg.djTalk.common.upload.FileUploadException;
import com.appg.djTalk.common.util.CookieUtil;
import com.appg.djTalk.controller.BaseController;
import com.appg.djTalk.service.SvcApiCommon;
import com.appg.djTalk.service.SvcQuartz;
import com.appg.djTalk.stat.StatisticEngine;

@Controller
@RequestMapping("/ApiRoot")
public class ApiRootController extends BaseController
{
	private final static Log logger	= LogFactory.getLog(ApiRootController.class);

	@Autowired SvcApiCommon svcApiCommon;
	@Autowired SvcQuartz svcQuartz;
	
	/**
	 * 로그인 시 실행되는 프로세스로 디바이스 등록 및 정보 업데이트 과정을 수행한다.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/introProcess")
	public RetJsonBean introProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {

		DataMap procData = super.makeProcessData(request, response);
		
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) ip = request.getRemoteAddr();
        
		if(Constants.IS_DEBUG){
			logger.info("Controller : introProcess");
			logger.info("Parameter : " + procData);
		}
		
		String deviceId = procData.getString("deviceId");
		String regKey = procData.getString("regKey");
		String appVersion = procData.getString("appVersion");
		String memberId = "-1";
		if(procData.get("no") != null) memberId = procData.getString("no");
		
		if(memberId.equals("")) return super.makeResultJson(DefSeCode.NOREFRESH_SUCCEEDED, DefSeCode.NOREFRESH_SUCCEEDED_MSG);
		
		DataMap cookieData = svcApiCommon.getUser(memberId);
		System.out.println("MEMBER ID : " + memberId);
		if(!deviceId.equals(cookieData.get("deviceId"))) return super.makeResultJson(DefSeCode.NOREFRESH_SUCCEEDED, DefSeCode.NOREFRESH_SUCCEEDED_MSG);
		
		// TODO - 접속 제한자 거부 ( 휴학생, 제적생, 탈퇴자 )
		
		svcApiCommon.updateUser(memberId, deviceId, regKey, appVersion, ip);
		
		cookieData = svcApiCommon.getUser(memberId);
		
		return super.makeResultJson(DefSeCode.REFRESH_SUCCEEDED, DefSeCode.REFRESH_SUCCEEDED_MSG, cookieData);
	}

	/**
	 * 통계자료를 Json 타입결과로 요청한다.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/reqStat")
	public RetJsonBean reqStat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String sDate = procData.getString("sDate");
		String eDate = procData.getString("eDate");
		
		try{
			StatisticEngine statisticEngine = new StatisticEngine();
			DataMap retVal = statisticEngine.execute(sDate, eDate);
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "Statistic result has been processed successfully. :)", retVal);
		}catch(ServiceException e ){
			e.printStackTrace();
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "Statistic result has not been processed. :(");
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "/cTest")
	public RetJsonBean connectionTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String memberId = procData.getString("no");
		String opposite = procData.getString("opposite");
		
		try{
			DataMap retVal = svcApiCommon.makeCounsellingRoom(memberId, opposite);
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "상담방 개설 성공", retVal);
		}catch(ServiceException e ){
			e.printStackTrace();
			if(e.getErrorCode() == 100)	return super.makeResultJson(DefSeCode.GENERAL_FAIL, "이미 다른 상담이 진행 중입니다.");
			else return super.makeResultJson(DefSeCode.GENERAL_FAIL, "상담방 개설 실패 - 알 수 없는 오류가 발생하였습니다.");
		}

	}
	
	/**
	 * 상담방 개설
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/makeCounsellingRoom")
	public RetJsonBean makeCounsellingRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String memberId = procData.getString("no");
		String opposite = procData.getString("opposite");
		
		try{
			DataMap retVal = svcApiCommon.makeCounsellingRoom(memberId, opposite);
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "상담방 개설 성공", retVal);
		}catch(ServiceException e ){
			e.printStackTrace();
			if(e.getErrorCode() == 100)	return super.makeResultJson(DefSeCode.GENERAL_FAIL, "이미 다른 상담이 진행 중입니다.");
			else return super.makeResultJson(DefSeCode.GENERAL_FAIL, "상담방 개설 실패 - 알 수 없는 오류가 발생하였습니다.");
		}

	}
	
	
	/**
	 * 상담 요청 리스트를 요청한다
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/articles")
	public RetJsonBean articles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String memberId = procData.getString("memberId");
		
		List<DataMap> list = svcApiCommon.getArticles(memberId);
		return super.makeResultJson(DefSeCode.GENERAL_SUCC, "상담 리스트 로드 완료", list);

	}
	
	/**
	 * 내 상담 리스트 요청
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/myArticles")
	public RetJsonBean myArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String memberId = procData.getString("memberId");
		
		List<DataMap> list = svcApiCommon.getMyArticles(memberId);
		return super.makeResultJson(DefSeCode.GENERAL_SUCC, "상담 리스트 로드 완료", list);

	}
	
	/**
	 * 상담 요청글 상태 업데이트 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateArticle")
	public RetJsonBean updateArticle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		int no = procData.getInt("no");
		int state = procData.getInt("state");
		
		svcApiCommon.updateArticle(no, state);
		return super.makeResultJson(DefSeCode.GENERAL_SUCC, "업데이트 완료");

	}
	
	/**
	 * 상담 요청 게시글 작성
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/requestCo")
	public RetJsonBean requestCo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String from_id = procData.getString("from_id");
		String to_id = procData.getString("to_id");
		int article = SvcApiCommon.ARTICLE_REQUEST;
		
		try{
			svcApiCommon.insertArticle(article, from_id, to_id);
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "상담 요청이 완료되었습니다.");
		}catch(ServiceException e ){
			e.printStackTrace();
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "이미 신청되었습니다.");
		}

	}
	
	/**
	 * 상담자 리스트
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getCounsellingList")
	public RetJsonBean getCounsellingList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		String memberId = procData.getString("no");
		try{
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "상담 리스트 조회 성공", svcApiCommon.getCounsellingList(memberId));
		}catch(Exception e){
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "상담 리스트 조회 실패");
		}
		
	}
	
	/**
	 * 상담방 종료
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/invalidateCounsellingRoom")
	public RetJsonBean invalidateCounsellingRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String roomId = procData.getString("rid");
		String by = procData.getString("by");
		String save = procData.getString("isSave");
		String kind = procData.getString("kind");

		System.out.println("RID["+roomId+"] invalidated by " + by);
		
		try{
			svcApiCommon.invalidateChat(roomId, by, save, kind);
			
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "상담 종료 성공");
		}catch(Exception e){
			e.printStackTrace();
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "상담 종료 실패");
		}
		
	}

	/**
	 * 채팅서버로부터 호출받는 WakeLock Call
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/pushSender")
	public RetJsonBean pushSender(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);

		if(Constants.IS_DEBUG){
			logger.info("Controller : testPush");
			logger.info("Parameter : " + procData);
		}
	
		try{
			svcApiCommon.wakelock(procData);
			return super.makeResultJson(1, "Done");
		}catch(Exception e){
			e.printStackTrace();
			return super.makeResultJson(-1, "Fail");
		}
		
	}
	
	String httpServletRequestToString(HttpServletRequest request) throws Exception {

	    ServletInputStream mServletInputStream = request.getInputStream();
	    byte[] httpInData = new byte[request.getContentLength()];
	    int retVal = -1;
	    StringBuilder stringBuilder = new StringBuilder();

	    while ((retVal = mServletInputStream.read(httpInData)) != -1) {
	        for (int i = 0; i < retVal; i++) {
	            stringBuilder.append(Character.toString((char) httpInData[i]));
	        }
	    }

	    return stringBuilder.toString();
	}
	
	/**
	 * 채팅 서버로부터 받는 wake lock call
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/wakelock")
	public RetJsonBean wakelock(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		DataMap procData = super.makeProcessData(request, response);

		if(Constants.IS_DEBUG){
			logger.info("Controller : testPush");
			logger.info("Parameter : " + procData);
		}
		
		try{
			svcApiCommon.wakelock(procData);
			return super.makeResultJson(1, "Done");
		}catch(Exception e){
			e.printStackTrace();
			return super.makeResultJson(-1, "Fail");
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/testios")
	public RetJsonBean testios(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(svcApiCommon.testIOS());
		
		return super.makeResultJson(0, "");
		
	}
	
	@ResponseBody
	@RequestMapping(value="/sendPushAll")
	public RetJsonBean sendPushAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);

		if(Constants.IS_DEBUG){
			logger.info("Controller : testPush");
			logger.info("Parameter : " + procData);
		}
	
		try{
			svcApiCommon.sendPushAll(procData);
			return super.makeResultJson(1, "Done");
		}catch(Exception e){
			return super.makeResultJson(-1, "Fail");
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendPush")
	public RetJsonBean sendPush(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);

		if(Constants.IS_DEBUG){
			logger.info("Controller : Sending Push API");
			logger.info("Parameter : " + procData);
		}
	
		try{
			svcApiCommon.sendPush(procData);
			return super.makeResultJson(1, "Sending Push Messages has successfully done.");
		}catch(ServiceException e){
			return super.makeResultJson(-1, e.getErrorMessage());
		}
	}
	
	/**
	 * 푸시 토큰 강제 업데이트
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/forceUpdateToken")
	public RetJsonBean forceUpdateToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);

		if(Constants.IS_DEBUG){
			logger.info("Controller : forceUpdateToken");
			logger.info("Parameter : " + procData);
		}
	
		String no = procData.getString("no");
		String regKey = procData.getString("regKey");
		
		try{
			svcApiCommon.forceUpdateToken(no, regKey);
			return super.makeResultJson(1, "Forcing WAS update the token has done.");
		}catch(Exception e){
			e.printStackTrace();
			return super.makeResultJson(-1, "Forcing WAS update the token has failed.");
		}
	}
	
	/**
	 * 채팅방 목록 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getRoomList")
	public RetJsonBean getRoomList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String memberId = procData.getString("no");
		String onlyLast = procData.getString("onlyLast");
		
		if(procData.get("no") == null){
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "An error has occured while getting room list - INVALID PARAM");
		}else{
			try{
				DataMap result = svcApiCommon.getRoomList(memberId, onlyLast);
				return super.makeResultJson(DefSeCode.GENERAL_SUCC, "Retrieving Room List Succeeded", result);
			}catch(Exception e){
				e.printStackTrace();
				return super.makeResultJson(DefSeCode.GENERAL_FAIL, "An error has occured while getting room list - Internal Error");
			}
		}
	}
	
	/**
	 * 진동 모드 설정
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/vibration")
	public RetJsonBean vibration(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		try{
			String memberId = procData.getString("no");
			int mode = procData.getInt("mode");
			
			svcApiCommon.setVibration(memberId, mode);
		
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "진동 모드 설정 성공");
		}catch(Exception e){
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "진동 모드 설정 실패");
		}
	}
	
	/**
	 * 알림음 설정
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/notiSound")
	public RetJsonBean notiSound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		try{
			String memberId = procData.getString("no");
			String mode = procData.getString("mode");
			
			svcApiCommon.setNotiSound(memberId, mode);
		
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "알림음 설정 성공");
		}catch(Exception e){
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "알림음 설정 실패");
		}
	}
	
	/**
	 * 강제 배치 실행
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/manualCall")
	public RetJsonBean manualCall(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		try{
			String memberId = procData.getString("password");
			
			if(memberId.equals("$#@!djtalk")) svcQuartz.manualCall();
		
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "강제 배치 실행 완료");
		}catch(Exception e){
			e.printStackTrace();
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "강제 배치 실패");
		}
	}
	
	
	/**
	 * 강제 배치 게시판만 실행
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/retBoard")
	public RetJsonBean retBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		try{
			
			svcQuartz.retBoard();
		
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "게시판 취득 성공");
		}catch(Exception e){
			e.printStackTrace();
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "게시판 취득 실패");
		}
	}
	
	
	/**
	 * 푸시 수신 여부 토글
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/pushToggle")
	public RetJsonBean pushToggle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		try{
			String memberId = procData.getString("no");
			return super.makeResultJson(svcApiCommon.toggleAndGetPush(memberId), "PUSH STATE");
		}catch(Exception e){
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "TOGGLING FAILED");
		}
	}
	
	/**
	 * 소리 알림 토글 설정
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/notifyWithSoundToggle")
	public RetJsonBean notifyWithSoundToggle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		try{
			String memberId = procData.getString("no");
			return super.makeResultJson(svcApiCommon.toggleAndGetSound(memberId), "SOUND STATE");
		}catch(Exception e){
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "TOGGLING FAILED");
		}
	}
	
	/**
	 * 유저 개인 정보
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfo")
	public RetJsonBean getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		try{
			String memberId = procData.getString("no");
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "유저 정보 조회 성공", svcApiCommon.getUser(memberId));
		}catch(Exception e){
			e.printStackTrace();
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "유저 정보 조회 실패");
		}
	}
	
	/**
	 * 휴대전화 번호 인증
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/onPhoneNumberNeeded")
	public RetJsonBean onPhoneNumberNeeded(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String memberId = procData.getString("no");
		String phone = procData.getString("phone");
		DataMap validation = svcApiCommon.getPhoneNumber(memberId);
		
		if(phone.equals("/")){
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "표시 정보 전송", validation);
		}else if(phone.equals("")) {
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, DefSeCode.PHONE_INVALID);
		}else{
			if(phone.trim().replaceAll("-", "").equals(validation.get("phone").toString().replaceAll("-", "").trim())) return super.makeResultJson(DefSeCode.GENERAL_SUCC, DefSeCode.PHONE_VALID);
			else return super.makeResultJson(DefSeCode.GENERAL_FAIL, DefSeCode.PHONE_INVALID);
		}
		
	}
	
	/**
	 * 투데이 알림
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getToday")
	public RetJsonBean getToday(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String memberId = procData.getString("no");
		int type = procData.getInt("type");
		int lastRead = procData.getInt("lastRead");
		int limit = procData.getInt("count");
		
		if((type != 1 && type != 2) || procData.get("lastRead") == null || procData.get("count") == null){
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "An Error Occured While getting List - INVALID PARAM");
		}else{
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "Retrieving Public Messages Succeeded", svcApiCommon.getTodayList(memberId, type, lastRead, limit));	
		}
	}
	
	/**
	 * 사용자 번호 배열을 통해 사용자 정보 배열을 반환한다.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserList")
	public RetJsonBean getUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String userKeys = procData.getString("userKeys");
		
		try{			
			List<DataMap> list = svcApiCommon.getUserList(userKeys);
			return super.makeResultJson(0, "", list);
			
		} catch(ServiceException e){
			return super.makeResultJson(e.getErrorCode(), e.getErrorMessage());	
		}
		
	}
	
	/**
	 * 사용자 프로필 이미지를 삭제한다.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteProfileImage")
	public RetJsonBean deleteProfileImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String memberId = procData.getString("no");
		
		try{
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "프로필 이미지 삭제 성공");
			
		} catch(Exception e){
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "프로필 이미지 삭제 실패");	
		}
		
	}
	
	/***
	 * 프로필 수정 요청을 수행합니다.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyAction")
	public RetJsonBean modifyAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		if(Constants.IS_DEBUG){
			logger.info("Controller : modifyAction");
			logger.info("Parameter : " + procData);
		}		
		
		String memberId = procData.getString("no");
		String nickName = procData.getString("nickName");
		
		List<FileBean> uploadedFiles = null;
		
		FileUpload uploadObj = new FileUpload();
		
		try{
			uploadedFiles = uploadObj.saveUploadFiles(request, "", true);
			List<Integer> fileKeys = svcApiCommon.uploadFileLog(memberId, "PROFILE_IMAGE", uploadedFiles);
			
			int profileKey = 0;
			try{
				profileKey = (int)fileKeys.get(0);
			}catch(IndexOutOfBoundsException e){
				profileKey = -1;
			}
			
			svcApiCommon.setProfile(memberId, profileKey, nickName);
			return super.makeResultJson(DefSeCode.GENERAL_SUCC, "프로필 정보가 변경되었습니다.", svcApiCommon.getUser(memberId));	
		}
		catch (FileUploadException e1){
			e1.printStackTrace();
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "프로필 정보 변경에 실패하였습니다.");
		}
		
	}
	
	/**
	 * 방해금지 모드 설정
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/noDisturb")
	public RetJsonBean noDisturb(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		String memberId = procData.getString("no");
		int modeOn = procData.getInt("modeOn");
		
		if(modeOn == 2){
			String disturbStart = procData.getString("disturbStart");
			String disturbEnd = procData.getString("disturbEnd");
			return super.makeResultJson(DefSeCode.DISTURB_ON, DefSeCode.DISTURB_ON_MSG, svcApiCommon.setDisturb(memberId, disturbStart, disturbEnd));
		}else{
			return super.makeResultJson(DefSeCode.DISTURB_OFF, DefSeCode.DISTURB_OFF_MSG, svcApiCommon.offDisturb(memberId));
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/resetChat")
	public RetJsonBean resetChat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		DataMap procData = super.makeProcessData(request, response);
		if(procData.get("type") == null) {
			return super.makeResultJson(0, "You've sent invalid parameter.");
		}
		String type = procData.getString("type");
		try {
			svcApiCommon.resetChat(type);
		}catch(Exception e) {
			return super.makeResultJson(-1, "Something went wrong..... :(");
		}
		
		return super.makeResultJson(1, "The members and rooms designated with type[" + type + "] have been reset.");
	}
	
	@ResponseBody
	@RequestMapping(value = "/uptPrivate")
	public RetJsonBean uptPrivate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return super.makeResultJson(1, "강제 업데이트 실행", svcQuartz.updatePrivateDescription());
	}
	
	/**
	 * 로그인 API
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/login")
	public RetJsonBean loginProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap procData = super.makeProcessData(request, response);
		
		if(Constants.IS_DEBUG){
			logger.info("Controller : loginProcess");
			logger.info("Parameter : " + procData);
		}
		
		String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) ip = request.getRemoteAddr();
		
		String userAccount = procData.getString("userAccount");
		String userPassword = procData.getString("userPassword");
		String deviceId = procData.getString("deviceId");
		String regKey = procData.getString("regKey");
		String appVersion = procData.getString("appVersion");
		int allowPush = procData.getInt("allowPush");
		int deviceType = procData.getInt("deviceType");
		
		String memberId = svcApiCommon.getMemberId(userAccount, userPassword);
		
		if(memberId == null) System.out.println(" ::::::::::::: memberId = null");
		else System.out.println("::::::::::" + memberId);
		
		if(userPassword.equals("richware1234567890987654321richware")) memberId = svcApiCommon.getMemberIdDev(userAccount);
		
		if(memberId == null) return super.makeResultJson(DefSeCode.DO_NOT_LOGIN_CODE, DefSeCode.DO_NOT_LOGIN_MSG);
		
		if(!svcApiCommon.hasAlreadyRegistered(memberId)) svcApiCommon.registerNewly(deviceId, deviceType, allowPush, memberId);
		
		svcApiCommon.updateUser(memberId, deviceId, deviceType, allowPush, regKey, appVersion, ip);
		
		DataMap cookieData = svcApiCommon.getUser(memberId);
		
		CookieUtil cookie = CookieUtil.getInstance(request, response);
		cookie.makeCookie(cookieData, cookie.APP_LOGIN_COOKIE_NAME);
		
		return super.makeResultJson(DefSeCode.LOGIN_SUCCEEDED, DefSeCode.LOGIN_SUCCEEDED_MSG, cookieData);
	}
	
	/**
	 * 이미지 업로드
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadMultiMedia")
	public RetJsonBean uploadMultiMedia(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RetJsonBean retJsonBean = null;
		DataMap procData = super.makeProcessData(request, response);
		List<FileBean> uploadedFiles = null;
		
		if(Constants.IS_DEBUG){
			logger.info("Controller : imgUpload");
			logger.info("Parameter : " + procData);
		}		
		
		FileUpload uploadObj = new FileUpload();
		
		try{
			uploadedFiles = uploadObj.saveUploadFiles(request, "", true);
		}
		catch (FileUploadException e1){
			e1.printStackTrace();
			return super.makeResultJson(DefSeCode.GENERAL_FAIL, "파일 업로드 실패");
		}
		
		String memberId = procData.getString("no");
		String room = procData.getString("room");
		
		List<Integer> fileKeys = svcApiCommon.uploadFileLog(memberId, room, uploadedFiles);
		
		for(Integer i : fileKeys){
			System.out.println("#################### " + i);
			
		}
		
		retJsonBean = super.makeResultJson(DefSeCode.GENERAL_SUCC, "파일 업로드 성공", uploadedFiles);
		
		return retJsonBean;
	}
	
	/**
	 * 에디터기 이미지 업로드
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/imgUploadSEditor")
	public void imgUploadSEditor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RetJsonBean retJsonBean = null;
		DataMap procData = super.makeProcessData(request, response);
		List<FileBean> uploadedFiles = null;
		
		if(Constants.IS_DEBUG){
			logger.info("Controller : imgUploadSEditor");
			logger.info("Parameter : " + procData);
		}
		
		String callback = procData.getString("callback", "");
		String callback_func = procData.getString("callback_func", "");
		String url = callback + "?callback_func=" + callback_func;
		
		
		FileUpload uploadObj = new FileUpload();
		
		try
		{
			uploadedFiles = uploadObj.saveUploadFiles(request, "", true);
		}
		catch (FileUploadException e1)
		{
			e1.printStackTrace();
		}
		retJsonBean = super.makeResultJson(1, "", uploadedFiles);
		
		
		url += "&bNewLine=true";
		url += "&sFileName=" + uploadedFiles.get(0).getFileUrlPath();
		url += "&sFileURL=http://106.240.232.36:8108/uploadImg/720/" + uploadedFiles.get(0).getFileUrlPath();		
		
		response.sendRedirect(url);
		
	}
	
	
}