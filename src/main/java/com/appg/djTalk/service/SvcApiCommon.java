package com.appg.djTalk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appg.djTalk.common.batch.manager.BatchJobProcessManager;
import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.exception.DefSeCode;
import com.appg.djTalk.common.exception.ServiceException;
import com.appg.djTalk.common.push.DataBean;
import com.appg.djTalk.common.push.PushCls;
import com.appg.djTalk.common.push.PushConstants;
import com.appg.djTalk.common.upload.FileBean;
import com.appg.djTalk.common.util.Base64Coder;
import com.appg.djTalk.common.util.DateUtil;
import com.appg.djTalk.common.util.JsonUtil;
import com.appg.djTalk.common.util.chat.ChatUtil;
import com.appg.djTalk.common.util.chat.contants.ChatConstants;
import com.appg.djTalk.mybatis.board.mapper.BoardMapper;
import com.appg.djTalk.mybatis.core.mapper.CommMapper;
import com.appg.djTalk.mybatis.counsell.mapper.CounsellMapper;
import com.appg.djTalk.mybatis.external.mapper.ExCommMapper;
import com.baidu.yun.core.json.ParseException;

@Service
public class SvcApiCommon {
	@Autowired BatchJobProcessManager batchJobProcessManager;
	@Autowired CommMapper commMapper;
	@Autowired CounsellMapper counsellMapper;
	@Autowired ExCommMapper exCommMapper;
	@Autowired BoardMapper boardMapper;
	
	public static final int ARTICLE_REQUEST = 100;
	
	public List<DataMap> getCounsellingList(String memberId){
		List<DataMap> preProcess = exCommMapper.getCounsellingList(memberId); 
		List<DataMap> retVal = new ArrayList<DataMap>();
		
		List<String> ongoing = commMapper.isAlreadyOngoing(memberId);
		
		for(DataMap map : preProcess){
			if(!ongoing.contains(map.getString("memberId"))) retVal.add(map);
		}
		
		return retVal;
	}
	
	public void updateArticle(int no, int state){
		commMapper.updateArticle(state, no);
	}
	
	public void insertArticle(int article, String from_id, String to_id) throws ServiceException{
		DataMap map = new DataMap();
		
		map.put("from_id", from_id);
		map.put("to_id", to_id);
		
		if(commMapper.getArticleCount(map) > 0) throw new ServiceException(DefSeCode.BASIC_ERROR_CODE);
		
		String entry = "";
		
		switch(article){
		case ARTICLE_REQUEST : entry = getMemberDetail(from_id).getString("name") + "님께서 상담을 요청하셨습니다."; break;
		default : break;
		}
		
		map.put("contents", entry);
		
		commMapper.insertArticle(map);
		
		PushCls pushCls = new PushCls(batchJobProcessManager);
		
		DataMap unit = commMapper.getUser(to_id);
		
		if(unit != null){
			DataBean data = new DataBean(unit.getInt("vibration"), unit.getInt("notifyWithSound"), unit.getString("notificationSound"), "REQ", from_id,
					to_id, "", "", entry, PushConstants.PUSH_TYPE_MSG);
			if(unit.getInt("deviceType") == 2){
//				data.getCustomData().put("message", data.getMessage());
//				data.getCustomData().put("vibration", Integer.toString(data.getVibration()));
//				data.getCustomData().put("notifyWithSound", Integer.toString(data.getNotifyWithSound()));
//				data.getCustomData().put("notificationSound", data.getNotificationSound());
				data.getCustomData().put("rid", data.getRid());
//				data.getCustomData().put("senduid", data.getSenduid());
//				data.getCustomData().put("recvuid", data.getRecvuid());
//				data.getCustomData().put("data", data.getData());
//				data.getCustomData().put("mtime", data.getMtime());				
				pushCls.sendPush(PushConstants.PUSH_DEVICE_TYPE_IOS, unit.getString("regKey"), data);
			}else{
				pushCls.sendPush(PushConstants.PUSH_DEVICE_TYPE_AND, unit.getString("regKey"), data);
			}
		}
		
		
	}
	
	public List<DataMap> getArticles(String memberId){
		return commMapper.getMyArticles(memberId);
	}
	
	public List<DataMap> getMyArticles(String memberId){
		List<DataMap> list = commMapper.getWroteArticles(memberId);
		for(DataMap m : list){
			m.put("contents", exCommMapper.getUserName(m.getString("to_id")) + "님께 요청한 상담 신청");
		}
		return list;
	}
	
	public List<DataMap> getListOfBoard(int seq) throws ServiceException{
		
		
		List<DataMap> list = boardMapper.getListOfBoard(seq);
		return list;
	}
	
	public void resetChat(String type) throws Exception{
		commMapper.resetChatMember(type);
		commMapper.resetChatRoom(type);
	}
	
	public void wakelock(DataMap procData) throws Exception{
		
		System.out.println("PROCDATA :: " + procData);
		
		PushCls pushCls = new PushCls(batchJobProcessManager);
		
		List<DataMap> list = new ArrayList<DataMap>();
		
		String[] rids = procData.getStringArr("rid");
		String[] recvuids = procData.getStringArr("recvuid");
		String[] datarr = procData.getStringArr("data");
		String[] rid = procData.getStringArr("rid");
		String[] senduid = procData.getStringArr("senduid");
		String[] mtime = procData.getStringArr("mtime");
		
		JSONArray arr = new JSONArray();
		
		for(int aq = 0; aq < rids.length; aq++){
			JSONObject json = JsonUtil.Obj.create("{}");
			json.put("recvuid", recvuids[aq]);
			json.put("data", datarr[aq]);
			json.put("rid", rid[aq]);
			json.put("senduid", senduid[aq]);
			json.put("mtime", mtime[aq]);
			
			arr.put(json);
			
		}
		
		System.out.println("DEBUG :: " + arr.toString());
		
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		
		for(int i = 0; i < arr.length(); i++) {
			
			String recvuid = JsonUtil.Arr.getJSONObject(arr, i).getString("recvuid");
			
			hashMap.put(recvuid, i);
			
			DataMap map = new DataMap();
			map.put("no", recvuid);
			System.out.println("LOOP NO :: " + map.getString("no"));
			list.add(map);
		}
		
		List<DataMap> pushTargetList = commMapper.getRegKeyWake(list);
		
		for(int i = 0; i < pushTargetList.size(); i++){
			DataMap unit = pushTargetList.get(i);
			int catcher = -1;
			
			if(hashMap.containsKey(unit.getString("no"))) catcher = hashMap.get(unit.getString("no"));
			else continue;
			
			JSONObject json = JsonUtil.Arr.getJSONObject(arr, catcher);
			DataBean data = new DataBean(unit.getInt("vibration"), unit.getInt("notifyWithSound"), unit.getString("notificationSound"), json.getString("rid"), json.getString("senduid"),
					json.getString("recvuid"), json.getString("data"), json.getString("mtime"), json.getString("data"), PushConstants.PUSH_TYPE_MSG);
			if(unit.getInt("deviceType") == 2){
				String pureMsg = JsonUtil.Obj.getString(JsonUtil.Obj.create(data.getMessage()), "msg");
				data = new DataBean(unit.getInt("vibration"), unit.getInt("notifyWithSound"), unit.getString("notificationSound"), json.getString("rid"), json.getString("senduid"),
						json.getString("recvuid"), pureMsg, json.getString("mtime"), pureMsg, PushConstants.PUSH_TYPE_MSG);
//				data.getCustomData().put("msg", JsonUtil.Obj.getString(JsonUtil.Obj.create(data.getMessage()), "msg"));
//				data.getCustomData().put("message", data.getMessage());
//				data.getCustomData().put("vibration", Integer.toString(data.getVibration()));
//				data.getCustomData().put("notifyWithSound", Integer.toString(data.getNotifyWithSound()));
//				data.getCustomData().put("notificationSound", data.getNotificationSound());
				data.getCustomData().put("rid", data.getRid());
//				data.getCustomData().put("senduid", data.getSenduid());
//				data.getCustomData().put("recvuid", data.getRecvuid());
//				data.getCustomData().put("data", data.getData());
//				data.getCustomData().put("mtime", data.getMtime());
				pushCls.sendPush(PushConstants.PUSH_DEVICE_TYPE_IOS, unit.getString("regKey"), data);
			}else{
				pushCls.sendPush(PushConstants.PUSH_DEVICE_TYPE_AND, unit.getString("regKey"), data);
			}
		}
	}
	
	public String testIOS(){
		PushCls pushCls = new PushCls(batchJobProcessManager);
		String retVal = null;
		DataBean data = new DataBean(0, 1, "default", "RID", "suid", "ruid", "data", "mtime", "data", PushConstants.PUSH_TYPE_MSG);
		data.getCustomData().put("message", data.getMessage());
		data.getCustomData().put("vibration", Integer.toString(data.getVibration()));
		data.getCustomData().put("notifyWithSound", Integer.toString(data.getNotifyWithSound()));
		data.getCustomData().put("notificationSound", data.getNotificationSound());
		data.getCustomData().put("rid", data.getRid());
		data.getCustomData().put("senduid", data.getSenduid());
		data.getCustomData().put("recvuid", data.getRecvuid());
		data.getCustomData().put("data", data.getData());
		data.getCustomData().put("mtime", data.getMtime());
		pushCls.sendPush(PushConstants.PUSH_DEVICE_TYPE_AND, "APA91bGNKm0-OyhfeiUM8LhsYR996q7tNzhgBUz_Mg0Nh97PXlLFzb3q9dxCvip4NsXiNQdI3CiK0Mw2w4Fh612Gb88YE--fE2zxMDcjd7EI8afqA14a4zDWqMshz9iU-yZ2fd20p9Kw", data);
		System.out.println("testIOS called");
		return retVal;
	}
	
	public void sendPushAll(DataMap procData){
		
		PushCls pushCls = new PushCls(batchJobProcessManager);
		
		List<DataMap> pushTargetList = commMapper.getRegKeyAll();
		
		
		for(int i = 0; i < pushTargetList.size(); i++){
			DataMap unit = pushTargetList.get(i);
			DataBean data = new DataBean(unit.getInt("vibration"), unit.getInt("notifyWithSound"), unit.getString("notificationSound"), "rid", "senduid",
					"recvuid", "data", "mtime", "message", PushConstants.PUSH_TYPE_MSG);
			if(unit.getInt("deviceType") == 2){
				//pushCls.sendPush(PushConstants.PUSH_DEVICE_TYPE_IOS, unit.getString("regKey"), data);
			}else{
				pushCls.sendPush(PushConstants.PUSH_DEVICE_TYPE_AND, unit.getString("regKey"), data);
			}
		}
	}

	public void sendPush(DataMap procData) throws ServiceException{
		PushCls pushCls = new PushCls(batchJobProcessManager);
		
		List<DataMap> targets = null;
		
		String unitCode = procData.getString("unit").toUpperCase();
		String message = procData.getString("message");
		String longMessage = procData.getString("longMessage");
		
		if(unitCode.equals("Y")){
			targets = exCommMapper.getAllMember();
		}else if(unitCode.equals("N")){
			String userKeys = procData.getString("userKeys");
			
			JSONArray arr = JsonUtil.Arr.create(userKeys);
			List<DataMap> list = new ArrayList<DataMap>();
			
			System.out.println(arr.toString());
			
			for(int i = 0; i < arr.length(); i++) {
				DataMap temp = new DataMap();
				temp.put("no", JsonUtil.Arr.getString(arr, i));
				list.add(temp);
			}
			
			targets = exCommMapper.getMemberDetailBulk(list);
		}else{
			throw new ServiceException(0, "유효하지 않은 파라미터입니다.");
		}
		
		sendNotiPack(targets, unitCode, message, longMessage);
	}
	
	public void sendNotiPack(List<DataMap> targets, String unitCode, String message, String longMessage){
		int filterType = 0;
		if(unitCode.toUpperCase().equals("N".toUpperCase())) filterType = 5;
		
		List<DataMap> rawList = new ArrayList<DataMap>();
		List<DataMap> leftTargetList = new ArrayList<DataMap>();
		
		Set<String> hashSet = new HashSet<String>();
		
		for(DataMap unit : targets){
			DataMap map = new DataMap();
			map.put("no", unit.getString("memberId"));
			rawList.add(map);
		}
		
		List<DataMap> pushTargetList = commMapper.getUsersWithKey(rawList);
		
		for(DataMap unit : pushTargetList){
			hashSet.add(unit.getString("no"));
		}
		
		for(DataMap unit : targets){
			if(!hashSet.contains(unit.getString("memberId"))){
				leftTargetList.add(unit);
			}
		}
		
		String hitCode = "008";
		
		for(DataMap map : targets){
			String filter_f = "";
			if(unitCode.toUpperCase().equals("N".toUpperCase())){
				filter_f = map.getString("memberId");
				writeArticle(hitCode, longMessage, filterType, filter_f, "", 0);
			}
		}
		
		if(!unitCode.toUpperCase().equals("N".toUpperCase())) writeArticle(hitCode, longMessage, filterType, "", "", 0);
				
		
		sendPushBulk(pushTargetList, message);
		sendSMSBulk(leftTargetList, longMessage);
	}
	
	public void sendPushBulk(List<DataMap> pushTargetList, String msg){
		PushCls pushCls = new PushCls(batchJobProcessManager);
		
		for(int i = 0; i < pushTargetList.size(); i++){
			DataMap unit = pushTargetList.get(i);
			
			DataBean data = 
					new DataBean(
							unit.getInt("vibration"), 
							unit.getInt("notifyWithSound"), 
							unit.getString("notificationSound"), 
							msg, 
							PushConstants.PUSH_TYPE_MSG);
			if(unit.getInt("deviceType") == 2){
				data.getCustomData().put("msg", msg);
				data.getCustomData().put("message", msg);
				data.getCustomData().put("vibration", Integer.toString(data.getVibration()));
				data.getCustomData().put("notifyWithSound", Integer.toString(data.getNotifyWithSound()));
				data.getCustomData().put("notificationSound", data.getNotificationSound());
				//pushCls.sendPush(PushConstants.PUSH_DEVICE_TYPE_IOS, unit.getString("regKey"), data);
			}else{
				pushCls.sendPush(PushConstants.PUSH_DEVICE_TYPE_AND, unit.getString("regKey"), data);
			}
		}
	}
	
	public void writeArticle(String title, String message, int filterType, String filter_f, String class_f, int mType){
		DataMap map = new DataMap();
		map.put("title", title);
		map.put("message", message);
		map.put("filterType", filterType);
		map.put("filter_f", filter_f);
		map.put("class_f", class_f);
		map.put("mType", mType);
		commMapper.insertPublicOnNotice(map);
	}
	
	public int sendSMSBulk(List<DataMap> pushTargetLeftList, String msg){
		return 0;
	}
	
	public DataMap makeCounsellingRoom(String memberId, String opposite) throws Exception{
		
		if(commMapper.isRedundantCounselling(memberId) != 0) throw new ServiceException(100);
		
		DataMap m = null;
		
		String rid = ChatUtil.creatTeamRoom(memberId, new String[]{memberId, opposite}, "CO");
		
		if(rid == null) {
			System.out.println("-----> Creating Job Failed.");
			throw new Exception();	
		}
		else {
			System.out.println("-----> Creating Job Succeeded.");
			
			DataMap params = new DataMap();
			params.put("desc", "1:1 상담방");
			params.put("roomId", rid);
			params.put("type", "CO");
			params.put("pref_dept", "");
			params.put("pref_section", "");
			params.put("pref_class", "");
			params.put("pref_daynight", "");
			params.put("pref_grade", 0);
			
			commMapper.createRoom(params);
			
			int roomNo = commMapper.getRoomNumber(rid);
			
			m = commMapper.getRoomDetail(roomNo);
			
			commMapper.setMember(roomNo, memberId, "1:1 상담방");
			commMapper.setMember(roomNo, opposite, "1:1 상담방");
			
			JSONObject roomInfoJson = ChatUtil.getChatRoomInfo(m.get("roomId").toString());
			DataMap roomInfo = new DataMap();
			List<String> memuids = new ArrayList<String>();	
			
			JSONArray arr = ChatUtil.getChatLastMsgList(new String[]{m.getString("roomId")});
			
			String msg = null;
			String cTime = null;
			try{
				msg = arr.getJSONObject(0).getJSONObject("data").getString("msg");
				cTime = DateUtil.convertMillisToDatetime(arr.getJSONObject(0).getString("mtime"));
			}catch(JSONException e){
				msg = "";
				cTime = "";
			}
			
			for(int i = 0; i < roomInfoJson.getJSONArray("memuids").length(); i++) memuids.add(roomInfoJson.getJSONArray("memuids").getString(i));
		
			roomInfo.put("rcode", roomInfoJson.getInt("rcode"));
			roomInfo.put("uid", roomInfoJson.getString("uid"));
			roomInfo.put("rtype", roomInfoJson.getString("rtype"));
			roomInfo.put("memuids", memuids);
			roomInfo.put("lastMessage", msg);
			roomInfo.put("lastTime", cTime);
		
			m.put("roomInfo", roomInfo);
			
			Set<String> hashFilter = new HashSet<String>();
			
			List<DataMap> detailList = commMapper.getUserListOfRoom(m.getInt("no"));
			
			for(DataMap map : detailList) hashFilter.add(map.getString("no"));
			
			for(int i = 0; i < roomInfoJson.getJSONArray("memuids").length(); i++)
				if(!hashFilter.contains(roomInfoJson.getJSONArray("memuids").getString(i))){
					DataMap nones = new DataMap();
					nones.put("no", roomInfoJson.getJSONArray("memuids").getString(i));
					nones.put("nickname", getMemberDetail(roomInfoJson.getJSONArray("memuids").getString(i)).getString("name"));
					detailList.add(nones);
				}
			
			for(DataMap map : detailList){
				DataMap mDetail = getMemberDetail(map.getString("no"));
				map.put("belongsTo", getUserDescWithDataMap(mDetail));
				map.put("img", commMapper.getMultiMedia(map.getInt("profile_f")));
				map.put("detail", mDetail);
			}
			m.put("users", detailList);
		}
		
		return m;
	}
	
	public boolean hasAlreadyRegistered(String memberId){
		if(commMapper.hasAlreadyRegistered(memberId) != 0) return true;
		return false;
	}
	
	public DataMap getPhoneNumber(String memberId){
		return exCommMapper.getPhoneNumber(memberId);
	}
	
	public DataMap getMemberDetail(String memberId){
		return exCommMapper.getMemberDetail(memberId);
	}
	
	public List<Integer> uploadFileLog(String memberId, String room, List<FileBean> files){
		//int result = 0;
		
		List<Integer> keys = new ArrayList<Integer>();
		
		for(FileBean f : files){
			DataMap map = new DataMap();
			map.put("mediaPath",f.getFileUrlPath());
			map.put("originalName",f.getFileName());
			map.put("extension",f.getExtension());
			map.put("size", (int)f.getFileSize());
			map.put("type",f.getFileType());
			map.put("memberId", memberId);
			map.put("room", room);
			
			commMapper.logFile(map);
			
			keys.add(map.getInt("no"));
			//saveList.add(map);
		}
		
		//result = commMapper.logFileBulk(saveList);
		
		return keys;
	}
	
	public void setProfile(String memberId, int profile, String nickName) throws Exception{
		String nickTemp = nickName;
		if(nickName.equals("")) nickTemp = exCommMapper.getUserName(memberId);
		if(profile == -1) commMapper.setProfileNoProfile(memberId, nickTemp);
		else commMapper.setProfile(memberId, profile, nickTemp);
	}
	
	public String getMemberIdDev(String userAccount){
		return exCommMapper.getMemberIdDev(userAccount);
	}
	
	public String getMemberId(String userAccount, String userPassword){
		return exCommMapper.getMemberId(userAccount, userPassword);
	}
	
	public String getUserName(String memberId){
		return exCommMapper.getUserName(memberId);
	}
	
	public int logoutPreviousDevice(String memberId){ // MD : 로그아웃
		// TODO 소켓이나 푸시를 통한 로그아웃 처리
		return 0;
	}
	
	public DataMap setDisturb(String memberId, String start, String end){
		commMapper.setDisturb(memberId, start, end);
		return commMapper.getDisturbState(memberId);
	}
	
	/**
	 * 사용자 배열을 쿼리 문자로 변환하여 이들의 리스트를 반환한다
	 * @param userKeys
	 * @return
	 * @throws ServiceException
	 */
	public List<DataMap> getUserList(String userKeys) throws ServiceException{
		List<DataMap> retVal = null;
		try{
			if("".equals(userKeys))
				throw new ServiceException(DefSeCode.INVALID_PARAMS_CODE, DefSeCode.INVALID_PARAMS_MSG);
			
			JSONArray arr = JsonUtil.Arr.create(userKeys);
			List<String> list = new ArrayList<String>();
			
			System.out.println(arr.toString());
			
			for(int i = 0; i < arr.length(); i++) {
				list.add(JsonUtil.Arr.getString(arr, i));
			}
			
			System.out.println(list.toString());
			retVal = commMapper.getUserList(list);
			
			Set<String> hashFilter = new HashSet<String>();
			
			for(DataMap map : retVal) hashFilter.add(map.getString("no"));
			
			for(int i = 0; i < arr.length(); i++)
				if(!hashFilter.contains(JsonUtil.Arr.getString(arr, i))){
					DataMap nones = new DataMap();
					nones.put("no", JsonUtil.Arr.getString(arr, i));
					nones.put("nickname", getMemberDetail(JsonUtil.Arr.getString(arr, i)).getString("name"));
					retVal.add(nones);
				}
			
			for(DataMap map : retVal){
				DataMap mDetail = getMemberDetail(map.getString("no"));
				map.put("detail", mDetail);
				DataMap img = new DataMap();
				if(map.getInt("profile_f") != 0) img = commMapper.getMultiMedia(map.getInt("profile_f")); 
				map.put("img", img);
				map.put("belongsTo", getUserDescWithDataMap(mDetail));
			}
			
		} catch(ServiceException e){
			throw e;
		} catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		
		// TODO Merge
		
		return retVal;
	}
	
	public DataMap offDisturb(String memberId){
		commMapper.offDisturb(memberId);
		return commMapper.getDisturbState(memberId);
	}
	
	public DataMap getUser(String i){
		DataMap user = commMapper.getUser(i);
		if(user == null){
			user = new DataMap();
			user.put("nickname", getMemberDetail(i).getString("name"));
		}else{
			int mf = user.getInt("profile_f");
			user.put("img", commMapper.getMultiMedia(mf));			
		}
		DataMap mDetail = getMemberDetail(i);
		user.put("detail", mDetail);
		user.put("belongsTo", getUserDescWithDataMap(mDetail));
		
		return user;
	}
	
	public void updateUser(String memberId, String deviceId, int deviceType, int allowPush, String regKey, String appVersion, String lastIp){
		DataMap params = new DataMap();
		
		String prevRegKey = commMapper.getCurrentRegKey(memberId);
		
		params.put("no", memberId);
		params.put("deviceId", deviceId);
		params.put("deviceType", deviceType);
		params.put("allowPush", allowPush);
		params.put("regKey", regKey);
		params.put("prevRegKey", prevRegKey);
		params.put("appVersion", appVersion);
		params.put("lastIp", lastIp);
		
		logoutPreviousDevice(memberId);
		
		commMapper.updateUser(params);
	}
	
	public void updateUser(String memberId, String deviceId, String regKey, String appVersion, String lastIp){
		DataMap params = new DataMap();
		
		String prevRegKey = commMapper.getCurrentRegKey(memberId);
		
		params.put("no", memberId);
		params.put("deviceId", deviceId);
		params.put("regKey", regKey);
		params.put("prevRegKey", prevRegKey);
		params.put("appVersion", appVersion);
		params.put("lastIp", lastIp);
		
		logoutPreviousDevice(memberId);
		
		commMapper.updateUserIntro(params);
	}
	
	public void setVibration(String memberId, int mode){
		commMapper.setVibration(memberId, mode);
	}
	
	public void setNotiSound(String memberId, String mode){
		commMapper.setNotiSound(memberId, mode);
	}
	
	public int toggleAndGetPush(String memberId){
		commMapper.pushToggle(memberId);
		return commMapper.getPushState(memberId);
	}
	
	public int toggleAndGetSound(String memberId){
		commMapper.soundToggle(memberId);
		return commMapper.getNSoundState(memberId);
	}
	
	public String getUserDescription(String type, String deptName, int grade, String daynight, String section){
		String desc = "";
		String dn = "";
		if(daynight != null){
			if(daynight.equals("주")) dn = "주간";
			else if(daynight.equals("야")) dn = "야간";
			else dn = "";
		}
		
		switch(type){
		case "학": desc = deptName + " " + grade + "학년 " + dn + " " + section + "반" ; break;
		case "조": desc = deptName; break;
		case "교": desc = deptName; break;
		case "직": desc = deptName; break;
		default: break;
		}
		
		return desc;
	}
	
	public String getUserDescWithDataMap(DataMap m){
		if(m == null) return null;
		String type = m.getString("access");
		String dept = m.getString("deptName");
		int grade = m.getInt("grade");
		String daynight = m.getString("daynight");
		String section = m.getString("class");
		return getUserDescription(type, dept, grade, daynight, section);
	}
	
	public String getChatSet(String rid){
		int roomNo = commMapper.getRoomNumber(rid);
		List<DataMap> memberPair = commMapper.getChatMemberPair(roomNo);
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put(memberPair.get(0).getString("device_f"), exCommMapper.getUserName((memberPair.get(0).getString("device_f"))));
		map.put(memberPair.get(1).getString("device_f"), exCommMapper.getUserName((memberPair.get(1).getString("device_f"))));
		
		String set = "";
		
		JSONObject msg = ChatUtil.getChatMsgList(rid, "");
		try {
			JSONArray msgList = msg.getJSONArray("list");
			
			for(int v = 0; v < msgList.length(); v++){
				JSONObject obj = msgList.getJSONObject(v);
				set += map.get(obj.getString("senduid")) + " : " + obj.getJSONObject("data").getString("msg") + "\r\n";
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return set;
	}
	
	public void invalidateChat(String rid, String by, String isSave, String kind) throws Exception{
		
		if(rid == null) throw new Exception();
		if(rid.length() == 0) throw new Exception();
		
		int roomNo = commMapper.getRoomNumber(rid);
		List<DataMap> memberPair = commMapper.getChatMemberPair(roomNo);
		
		String stuid = "";
		
		for(DataMap map : memberPair) System.out.println(map);
		
		if(memberPair.get(0).getString("device_f").equals(by)) stuid = memberPair.get(1).getString("device_f");
		else stuid = memberPair.get(0).getString("device_f");
		
		JSONObject msg = ChatUtil.getChatMsgList(rid, "");
		try {
			if (isSave.toUpperCase().equals("Y")) {
				JSONArray arr = msg.getJSONArray("list");
				String result = getChatSet(rid);
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);

					DataMap params = new DataMap();
					// params.put("prof_uid", Integer.toString(memberId));
					// params.put("stu_uid", Integer.toString(opposite));
					params.put("roomId", rid);
					String data = Base64Coder.encodeString(obj.getJSONObject("data").toString());
					String dec_data = Base64Coder.decodeString(data);
					params.put("data", data);
					params.put("mtime", obj.getString("mtime"));

					//commMapper.insertChatHistory(params);

				}
				
				DataMap paramCo = new DataMap();
				
				paramCo.put("apply", stuid);
				paramCo.put("prof", by);
				paramCo.put("kind", kind);
				paramCo.put("content", result);
				
				System.out.println(paramCo);
				
				counsellMapper.insertHistory(paramCo);

			
			}
			
			
			if(ChatUtil.sendMessage(by, rid, "M", ChatUtil.makeMsgJsonData("", ChatConstants.MSG_TYPE_CUSTOM_SYSTEM_ROOM_DESTORY))) commMapper.invalidateRoom(rid);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new Exception();
		}
		
	}
	
	public void forceUpdateToken(String no, String regKey){
		DataMap params = new DataMap();
		params.put("no", no);
		params.put("regKey", regKey);
		commMapper.forceUpdateToken(params);
	}
	
	public DataMap getRoomList(String memberId, String onlyLast) throws Exception{
		DataMap result = new DataMap();
		
		DataMap lastOfType1 = commMapper.getTodayLast(memberId, 1);
		DataMap lastOfType2 = commMapper.getTodayLast(memberId, 2);
		
		String type1Last = null;
		String type2Last = null;

		
		try{
			type1Last = lastOfType1.getString("regDate");
		}catch(NullPointerException e){
			type1Last = "";
		}

		try{
			type2Last = lastOfType2.getString("regDate");
		}catch(NullPointerException e){
			type2Last = "";
		}
		

		String rTime1 = "";
		if(!type1Last.equals("")) rTime1 =  DateUtil.convertToRelativeTypeWithTime(type1Last);
		String rTime2 = "";
		if(!type2Last.equals("")) rTime2 = DateUtil.convertToRelativeTypeWithTime(type2Last);
		if(rTime1 == null) rTime1 = "";
		if(rTime2 == null) rTime2 = "";
		
		if(lastOfType1 == null) lastOfType1 = new DataMap();
		if(lastOfType2 == null) lastOfType2 = new DataMap();
		
		lastOfType1.put("regDate", rTime1);
		lastOfType2.put("regDate", rTime2);
		
		result.put("lastOfType1", lastOfType1);
		result.put("lastOfType2", lastOfType2);
		
		if(onlyLast.toUpperCase().equals("Y")){
			return result;
		}
		
		try{
			List<DataMap> roomList = commMapper.getAvailableRooms(memberId);
			
			for(DataMap unit : roomList){
				
				
				if(unit.getString("type").equals("CO")){
					List<DataMap> chatPair = commMapper.getChatMemberPair(unit.getInt("no"));
					String partner = chatPair.get(0).getString("device_f");
					if(chatPair.get(0).getString("device_f").equals(memberId)) partner = chatPair.get(1).getString("device_f"); 
					String partnerName = exCommMapper.getUserName(partner);;
					unit.put("desc", partnerName + "님과의 1:1 상담방");
					System.out.println("DEBUG :: ROOM DESC has been replaced with relative name which is [" + unit.getString("desc") + "].");
				}
			}
			
			List<String> rids = new ArrayList<String>();
			
			for(int i = 0; i < roomList.size(); i++) rids.add(roomList.get(i).getString("roomId"));
			
			// JSONArray arr = ChatUtil.getChatLastMsgList(rids.toArray(new String[0]));
			
			for(int e = 0; e < roomList.size(); e++){
				DataMap m = roomList.get(e);
				JSONObject roomInfoJson = ChatUtil.getChatRoomInfo(m.get("roomId").toString());
				DataMap roomInfo = new DataMap();
				List<String> memuids = new ArrayList<String>();	
							
				String msg = null;
				String cTime = null;
				String mTime = null;
//				try{
//					msg = arr.getJSONObject(e).getJSONObject("data").getString("msg");
//					mTime = arr.getJSONObject(e).getString("mtime");
//					cTime = DateUtil.convertToRelativeType(arr.getJSONObject(0).getString("mtime"));
//				}catch(JSONException ee){
//					msg = "";
//					cTime = "";
//				}
				
				for(int i = 0; i < roomInfoJson.getJSONArray("memuids").length(); i++) memuids.add(roomInfoJson.getJSONArray("memuids").getString(i));
			
				roomInfo.put("rcode", roomInfoJson.getInt("rcode"));
				roomInfo.put("uid", roomInfoJson.getString("uid"));
				roomInfo.put("rtype", roomInfoJson.getString("rtype"));
				roomInfo.put("memuids", memuids);
				roomInfo.put("lastMessage", msg);
				
				roomInfo.put("lastTime", cTime);
				roomInfo.put("lastMillis", mTime);
			
				m.put("roomInfo", roomInfo);
				
				Set<String> hashFilter = new HashSet<String>();
				
				List<DataMap> detailList = commMapper.getUserListOfRoom(m.getInt("no"));
				
				for(DataMap map : detailList) hashFilter.add(map.getString("no"));
				
				List<DataMap> selectList = new ArrayList<DataMap>();
				
				for(int i = 0; i < roomInfoJson.getJSONArray("memuids").length(); i++) {
					String memuid = roomInfoJson.getJSONArray("memuids").getString(i);
					
					if(!hashFilter.contains(memuid)){
						DataMap data = new DataMap();
						if(memuid.equals(SvcQuartz.SERVER_AUTO_UID)) continue;
						DataMap nones = new DataMap();
						nones.put("no", memuid);
						detailList.add(nones);
					}
				}
				
				List<DataMap> userList = exCommMapper.getMemberDetailBulk(detailList);
				
				for(int i = 0; i < userList.size(); i++) {
					DataMap map = userList.get(i);
					DataMap img = new DataMap();
					if(detailList.get(i).getInt("profile_f") != 0) img = commMapper.getMultiMedia(detailList.get(i).getInt("profile_f"));
					userList.get(i).put("belongsTo", getUserDescWithDataMap(map));
					
					int catcher = -1;
					
					for(int ii = 0; ii < userList.size(); ii++)
						if(userList.get(ii).getString("memberId").equals(detailList.get(i).getString("no"))) {
							catcher = ii;
							break;
						}
					
					if(detailList.get(i).get("nickname") == null) {
						try{
							detailList.get(i).put("nickname", userList.get(catcher).getString("name"));
						}catch(Exception ee){
							detailList.get(i).put("nickname", "사용자");
							ee.printStackTrace();
						}
					}
					detailList.get(i).put("img", img);
					detailList.get(i).put("detail", userList.get(catcher));
				}
				
				/*
				for(DataMap map : detailList){
					DataMap mDetail = getMemberDetail(map.getString("no"));
					DataMap img = null;
					if(map.getInt("profile_f") != 0) img = commMapper.getMultiMedia(map.getInt("profile_f")); 
					map.put("belongsTo", getUserDescWithDataMap(mDetail));
					map.put("img", img);
					map.put("detail", mDetail);
				}
				*/
				
				m.put("users", detailList);
			}		
			result.put("roomList", roomList);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.GENERAL_FAIL, "Unable to parse JSON Object");
		}
		
		return result;
	}
	
	public String registerNewly(String deviceId, int deviceType, int allowPush, String memberId){ // MD-SQL : 등록된 키를 반환해야 함.
		DataMap params = new DataMap();
		params.put("deviceId", deviceId);
		params.put("allowPush", allowPush);
		params.put("deviceType", deviceType);
		params.put("no", memberId);
		params.put("name", exCommMapper.getUserName(memberId));
		commMapper.registerNewly(params);
		
		return memberId;
	}
	
	public List<DataMap> getTodayList(String memberId, int type, int lastRead, int limit){
		List<DataMap> list = null;
		if(lastRead == -1) list = commMapper.getTodayListInfinite(memberId, type, 0);
		
		else list = commMapper.getTodayList(memberId, type, lastRead * limit, limit);
		
		// TODO
		return list;
	}
	
}
