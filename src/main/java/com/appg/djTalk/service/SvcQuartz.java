package com.appg.djTalk.service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appg.djTalk.common.batch.manager.BatchJobProcessManager;
import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.push.DataBean;
import com.appg.djTalk.common.push.PushCls;
import com.appg.djTalk.common.push.PushConstants;
import com.appg.djTalk.common.util.chat.ChatUtil;
import com.appg.djTalk.mybatis.board.mapper.BoardMapper;
import com.appg.djTalk.mybatis.core.mapper.CommMapper;
import com.appg.djTalk.mybatis.counsell.mapper.CounsellMapper;
import com.appg.djTalk.mybatis.external.mapper.ExCommMapper;
import com.appg.djTalk.mybatis.sms.mapper.SMSCommMapper;

@Service
public class SvcQuartz {
	@Autowired CommMapper commMapper;
	@Autowired ExCommMapper exCommMapper;
	@Autowired BoardMapper boardMapper;
	@Autowired CounsellMapper counsellMapper;
	@Autowired SMSCommMapper smsCommMapper;
	@Autowired BatchJobProcessManager batchJobProcessManager;
	
	public static final String SERVER_AUTO_UID = "==+=_SERVER_AUTO_UID_DAJEON_HIT_TALK_=+==";
	
	public static final String PUSH_BIRTH = "004";
	public static final String PUSH_CYBER = "005";
	public static final String PUSH_ATTEND = "006";
	
	public static final int MAX_GRADE = 3;
	public static final int DAYNIGHT_NONE = 0;
	public static final int DAYNIGHT_DAY = 0;
	public static final int DAYNIGHT_NIGHT = 1;
	
	public static final String DAYNIGHT_DAY_STRING = "10";
	public static final String DAYNIGHT_NIGHT_STRING = "20";
	
	public static final String ROOMTYPE_DEPARTMENT = "DP";
	public static final String ROOMTYPE_SECTION = "DS";
	public static final String ROOMTYPE_CLASS = "CL";
	public static final String ROOMTYPE_COUNSELLING = "CO";
	
	public class Room{
		public String type = "", pref_dept = "", pref_section = "", pref_class = "", pref_daynight = "", roomId = "";
		public int pref_grade = 0, room_no = 0;
		
		public Room(){}
		
		public Room(String type, String pref_dept, String pref_section, String pref_class, int pref_daynight, int pref_grade) {
			this.type = type;
			this.pref_dept = pref_dept;
			this.pref_section = pref_section;
			this.pref_class = pref_class;
			this.pref_grade = pref_grade;
			
			if(pref_daynight == DAYNIGHT_DAY) this.pref_daynight = DAYNIGHT_DAY_STRING;
			else if(pref_daynight == DAYNIGHT_NIGHT) this.pref_daynight = DAYNIGHT_NIGHT_STRING;
			else this.pref_daynight = "";
		}
		
		
	}
	
	public class ChatMember{
		public String device = "";
		public int room = 0;
		public ChatMember(){}
		public ChatMember(String device, int room){
			this.device = device;
			this.room = room;
		}
		public String getHashCode(){
			return device + "#" + room + "#HASH_CODE#";
		}
	}
	
	public String removeHtml(String source){
		return source
				.replaceAll("<br>", "\n")
				.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
				.replaceAll("&nbsp;", " ")
				.replaceAll("&nbsp", " ")
				.replaceAll("<o:p>", "")
				.replaceAll("<[^>]*>", "")
				.replaceAll("</o:p>", "")
				.replaceAll("\r\n", "")
				.replaceAll("<!--StartFragment-->", "\n")
				.trim();
	}
	
	public void retrieveBoard(){
		int maxSeq = 0;
		try {
			maxSeq = commMapper.getBoardMaxSequence();
		}catch(Exception e) {
			e.printStackTrace();
			maxSeq = 0;
		}
		List<DataMap> list = boardMapper.getListOfBoard(maxSeq);
		
		for(DataMap map : list){
			
			
			int seq = map.getInt("SEQ");
			String contents = map.getString("CONTENTS");
			String target = map.getString("TARGETCODE");
			String regDate = map.getString("REGDATE");
			
			DataMap params = new DataMap();
			
			params.put("title", target);
			params.put("content", removeHtml(contents));
			params.put("seq", seq);
			params.put("regDate", regDate);
			
			commMapper.insertBoard(params);
		}
		
	}
	
	public void retrieveNotice(){
		int maxSeq = 0;
		try {
			maxSeq = commMapper.getNoticeMaxSequence();
		}catch(Exception e) {
			e.printStackTrace();
			maxSeq = 0;
		}
		List<DataMap> list = boardMapper.getListOfNotice(maxSeq);
		
		for(DataMap map : list){
			
			
			int seq = map.getInt("SEQ");
			String contents = map.getString("CONTENTS");
			String regDate = map.getString("REGDATE");
			
			DataMap params = new DataMap();
			
			params.put("title", "003");
			params.put("content", removeHtml(contents));
			params.put("seq", seq);
			params.put("regDate", regDate);
			
			commMapper.insertNotice(params);
		}
		
	}
	
	public int testQuery(){
		retrieveBoard();
		retrieveNotice();
		
		return 0;
	}
	
	private class Attendance extends HashMap<String, HashMap<String, List<String>>>{
		public Attendance(){
			super();
		}
	}
	
	public void attendancePush(){
		List<DataMap> targets = new ArrayList<DataMap>();
		String message = "출결알림";

		Attendance at = new Attendance();
		List<DataMap> attd = exCommMapper.getAttendanceToday();
		
		for(DataMap map : attd){
			
			if(map.get("memberId") == null) {
				continue;
			}else {
				if(map.getString("memberId").equals("")) continue;
			}
			
			HashMap<String, List<String>> cursor = null;
			
			String memberId = map.getString("memberId");
			
			if(at.containsKey(memberId)) cursor = at.get(memberId);
			else {
				cursor = new HashMap<String, List<String>>();
				at.put(memberId, cursor);
			}
			List<String> studs = null;
			
			String lectKey = map.getString("CORS_NM") + "[" + map.getString("LECT_CD") + "]";
			
			if(cursor.containsKey(lectKey)){
				studs = cursor.get(lectKey);
			}else{
				studs = new ArrayList<String>();
				cursor.put(lectKey, studs);
			}
			
			studs.add(map.getString("NM_KOR")); // TODO 학번이 필요할 경우 문자열 합으로 하나 추가해주면 됨 (STUD_ID)
			
		}
		
		for(String key : at.keySet()){
			String longMessage = "[오늘의 출결알림]\n";
			for(String lectKey : at.get(key).keySet()){
				longMessage += lectKey + " (결석 "+ at.get(key).get(lectKey).size() +"명)\n-결석자 명단\n";
				for(int i = 0; i < at.get(key).get(lectKey).size(); i++){
					longMessage += at.get(key).get(lectKey).get(i);
					if(i + 1 < at.get(key).get(lectKey).size()) longMessage += ", ";
				}
				longMessage += "\n\n";
			}
			List<DataMap> tempList = new ArrayList<DataMap>();
			tempList.add(exCommMapper.getMemberDetail(key));
			sendNotiPack(tempList, PUSH_ATTEND, message, longMessage, "");	
		}
		
	}
	
	public void birthDayPush(){
		List<DataMap> targets = exCommMapper.getTodayBirth();
		String message = "님이 태어났기에 더 특별한 오늘, 대전보건대학교 가족 모두가 기쁜 한마음으로 축하드립니다.";
		sendNotiPack(targets, PUSH_BIRTH, message, message, "");
	}
	
	public void cyberSecPush(){
		List<DataMap> targets = exCommMapper.getAllMember();
		String message = "사이버보안 진단의 날";
		String longMessage = "매월 셋째주 수요일은 교육부가 정한 [사이버보안 진단의 날]로 "
							+"\'내PC 지키미\'를 실행하시고 보안상태를 자가점검하여 주시기 바랍니다.\n\n1. 관련 : 교육부  "
							+"정보보안 기본지침  제16조(사이버보안진단의 날)\n2. 위와 관련하여 우리대학 “정보보안업무 세부추진계획”의 "
							+"PC보안 체크리스트의항목을 점검하여 주시기 바랍니다.\n3. 기타 문의 사항은 정보기술센터(김태희, 9111)로 하여 주시기 바랍니다.";
		sendNotiPack(targets, PUSH_CYBER, message, longMessage, "");
	}
	
	public void sendNotiPack(List<DataMap> targets, String title, String message, String longMessage, String class_f){
		int filterType = 0;
		if(title.equals(PUSH_BIRTH) || title.equals(PUSH_ATTEND)) filterType = 5;
		
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
		
		for(DataMap map : targets){
			String filter_f = "";
			if(title.equals(PUSH_BIRTH) || title.equals(PUSH_ATTEND)){
				filter_f = map.getString("memberId");
				if(title.equals(PUSH_BIRTH)) writeArticle(map.getString("name") + title, map.getString("name") + longMessage, filterType, filter_f, class_f, 0);
				else writeArticle(title, longMessage, filterType, filter_f, class_f, 0);
			}
		}
		
		if(title.equals(PUSH_CYBER)) writeArticle(title, longMessage, filterType, "", class_f, 0);
				
		// TODO SMS 및 푸시 분리
		
		sendPushBulk(pushTargetList, message);
		//sendSMSBulk(leftTargetList, longMessage);
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
//				data.getCustomData().put("msg", msg);
//				data.getCustomData().put("message", msg);
//				data.getCustomData().put("vibration", Integer.toString(data.getVibration()));
//				data.getCustomData().put("notifyWithSound", Integer.toString(data.getNotifyWithSound()));
//				data.getCustomData().put("notificationSound", data.getNotificationSound());
				pushCls.sendPush(PushConstants.PUSH_DEVICE_TYPE_IOS, unit.getString("regKey"), data);
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
		commMapper.insertPublic(map);
	}
	
	private String getLocalServerIp(){
		try{
		    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
		        NetworkInterface intf = en.nextElement();
		        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();){
		            InetAddress inetAddress = enumIpAddr.nextElement();
		            if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()){
		            	return inetAddress.getHostAddress().toString();
		            }
		        }
		    }
		}
		catch (SocketException ex) {}
		return null;
	}
	
	public int sendSMSBulk(List<DataMap> pushTargetLeftList, String msg){
		String ip = getLocalServerIp();
		
		DataMap senderInfo = smsCommMapper.getDynamicSender();
		
		String sender = senderInfo.getString("SENDER");
		String senderdept = senderInfo.getString("SENDER_DEPT");
		String callfrom = senderInfo.getString("CALL_FROM");
		String biz = senderInfo.getString("BIZ_CD");
		String pgmid = senderInfo.getString("PGMID");
		String sendsys = senderInfo.getString("SEND_SYSTEM");
		
		for(DataMap map : pushTargetLeftList){
			DataMap params = new DataMap();
			params.put("phone", map.getString("phone"));
			params.put("dept", map.getString("dept"));
			params.put("memberId", map.getString("memberId"));
			params.put("local", ip);
			params.put("callfrom", callfrom);
			params.put("sender", sender);
			params.put("biz", biz);
			params.put("pgmid", pgmid);
			params.put("sendsys", sendsys);
			params.put("senderdept", senderdept);
			params.put("msg", msg);
			smsCommMapper.sendSMS(params);
		}
		return 0;
	}
	
	public boolean syncHitChatData(){
		
		System.out.println(" * ROOM SYNCHRONIZING STARTED * ");
		
		synchronizeChatState();
		
		retrieveBoard();
		retrieveNotice();
		
		return false;
	}
	
	public boolean updatePrivateDescription(){
		HashMap<String, String> cache = new HashMap<String, String>();
		
		List<DataMap> deptCacheList = exCommMapper.getDeptCache();
		
		for(DataMap dt : deptCacheList){
			cache.put(dt.getString("DEPT_CD"), dt.getString("DEPT_NM"));
		}
		
		List<DataMap> chatPairs = commMapper.getChatMemberPairAll();
		
		int cnt = 0;
		for(DataMap pair : chatPairs){
			cnt++;
			String dept = exCommMapper.getDeptById(pair.getString("device_f"));
			if(cache.containsKey(dept)){
				DataMap map = new DataMap();
				String desc = cache.get(dept);
				map.put("private", desc);
				map.put("mem", pair.getString("device_f"));
				map.put("rm", pair.getString("room_f"));
				
				commMapper.updateChatMemberDesc(map);
				
				System.out.println(" :: Description Set [" + cnt + "] with " + desc);
			}else{
				System.out.println(" :: No Description - Skipping This element. [" + cnt + "]");
			}
			
		}
		
		return true;
	}
	
	public void joinMember(String roomId, String[] memuids, HashMap<String, String> cache, String desc){
		if(ChatUtil.joinMember(SERVER_AUTO_UID, roomId, memuids, false)){
			int roomNo = commMapper.getRoomNumber(roomId);
			
			for(String s : memuids) {
				String dept = exCommMapper.getDeptById(s);
				String description = desc;
				if(cache.containsKey(dept)) description = cache.get(dept);
				
				commMapper.setMember(roomNo, s, description);
			}
			System.out.println("Joining Member Succeeded.");
		}else{
		System.out.println("Joining Member Failed.");
		}
	}
	
	public void unjoinMember(String roomId, String memuid){
		if(ChatUtil.unjoinMember(SERVER_AUTO_UID, roomId, new String[]{memuid}, false)){
			int roomNo = commMapper.getRoomNumber(roomId);
			commMapper.deleteSpecMember(roomNo, memuid);
			System.out.println("Joining Member Succeeded.");
		}else{
		System.out.println("Unjoining Member Failed.");
		}
	}
	
	public void manualCall(){
		synchronizeChatState();
		
		retrieveBoard();
		retrieveNotice();
	}
	
	public void retBoard(){
		
		retrieveBoard();
		retrieveNotice();
	}
	
	public void synchronizeChatState(){
		
		DataMap timing = exCommMapper.getTiming();
		
		String year = Calendar.getInstance().get(Calendar.YEAR) + "";
		String term = "10";
		
		if(timing.get("YR") != null){
			year = timing.getString("YR");
			term = timing.getString("TERM_CD");
		}
		
		HashMap<String, String> deptCache = new HashMap<String, String>();
		
		List<DataMap> deptCacheList = exCommMapper.getDeptCache();
		
		for(DataMap dt : deptCacheList){
			deptCache.put(dt.getString("DEPT_CD"), dt.getString("DEPT_NM"));
		}
		
		List<Room> currents = new ArrayList<Room>();
		List<Room> existings = new ArrayList<Room>();
		
		Set<String> hashFilter = new HashSet<String>();
		Map<String, Room> hashFilterReverse = new HashMap<String, Room>();
		
		List<DataMap> deptsPure = exCommMapper.getDepts();
		List<DataMap> deptsSect = exCommMapper.getDeptReal();
		List<DataMap> lects = exCommMapper.getLectures();
		List<DataMap> currentRooms = commMapper.getCurrentRooms();
		
		if(deptsSect == null || deptsSect.size() < 30) return;
		
		for(DataMap m : deptsPure) {
			currents.add(RawToRoom(m, ROOMTYPE_DEPARTMENT));
			hashFilter.add(createRoomHash(ROOMTYPE_DEPARTMENT, m.getString("no"), "", "", 0, DAYNIGHT_NONE));
		}
		
		for(DataMap m : deptsSect) {
			hashFilter.add(createRoomHash(ROOMTYPE_SECTION, m.getString("no"), m.getString("CLS_DIV"), "", m.getInt("SCHL_YR"), m.getInt("daynight")));
			currents.add(new Room(ROOMTYPE_SECTION, m.getString("no"), m.getString("CLS_DIV"), "", m.getInt("daynight"), m.getInt("SCHL_YR")));
		}
		
		for(DataMap m : lects) {
			currents.add(RawToRoom(m, ROOMTYPE_CLASS));
			hashFilter.add(createRoomHash(ROOMTYPE_CLASS, "", "", m.getString("classcode"), 0, DAYNIGHT_NONE));
		}
	
		for(DataMap m : currentRooms) {
			Room room = dataMaptoRoom(m);
			existings.add(room);			
			hashFilterReverse.put(createRoomHash(room.type, room.pref_dept, room.pref_section, room.pref_class.trim(), room.pref_grade, getIntOfDaynight(room.pref_daynight)), room);
		}
		
		for(Room room : currents){ // Room Lists which have to exist
			if(!hashFilterReverse.containsKey(createRoomHash(room.type, room.pref_dept, room.pref_section, room.pref_class, room.pref_grade, getIntOfDaynight(room.pref_daynight)))){
				String memuids[] = null;
				String rid = null;
				
				System.out.println("Quartz Job - Trying to create new Room.");
				
				switch(room.type){
				case ROOMTYPE_SECTION : case ROOMTYPE_CLASS : case ROOMTYPE_DEPARTMENT :
					
					//memuids = new String[]{SERVER_AUTO_UID};
					memuids = new String[]{SERVER_AUTO_UID};
					
					rid = ChatUtil.creatTeamRoom(SERVER_AUTO_UID, memuids, room.type);
					if(rid == null) System.out.println("-----> Creating Job Failed.");
					else {
						System.out.println("-----> Creating Job Succeeded.");
						String roomDesc = createRoomDescription(room.type, room.pref_dept, room.pref_section, room.pref_class, room.pref_grade, getIntOfDaynight(room.pref_daynight));
						createNewRoomInDB(roomDesc, rid, room.type, room.pref_dept, room.pref_section, room.pref_class, room.pref_daynight, room.pref_grade);
						
					}
					
					break;
				case ROOMTYPE_COUNSELLING: default : break; // Do nothing
				}
				
			} // Create New room if it does not exist
			

			
		}
		
		Set<String> hashFilterForMember = new HashSet<String>();
		Set<String> hashFilterForMemberReverse = new HashSet<String>();
		
		currentRooms = commMapper.getCurrentRooms();
		
		// synchronizing Members - Add
		for(DataMap m : currentRooms){
			hashFilterForMember.clear();
			hashFilterForMemberReverse.clear();
			
			int roomNo = m.getInt("no");
			
			List<DataMap> chatMemberPair = commMapper.getChatMemberPair(roomNo);
			
			for(DataMap chatMember : chatMemberPair){
				hashFilterForMember.add(new ChatMember(chatMember.getString("device_f"), roomNo).getHashCode());
			}
			
			String roomId = m.getString("roomId");
			String memuids[] = new String[]{};
			
			List<String> arr = new ArrayList<String>();
			
			// TODO optional - filtering (Need to be considered) *
			
			List<DataMap> list = null;
			
			
			switch(m.getString("type")){
			case ROOMTYPE_CLASS:
				list = exCommMapper.getCourseTaker(m.getString("pref_class"));
				list.addAll(exCommMapper.getCourseProf(m.getString("pref_class")));
				break;
			case ROOMTYPE_DEPARTMENT: 
				String rept = exCommMapper.getReptCode(m.getString("pref_dept"));
				list = exCommMapper.getDeptMember(m.getString("pref_dept"), rept);
				break;
			case ROOMTYPE_SECTION: 
				list = exCommMapper.getSectionMember(m.getString("pref_dept"), m.getInt("pref_grade"), m.getString("pref_section"), m.getString("pref_daynight"), year, term);			
				break;
			case ROOMTYPE_COUNSELLING: default: break; // Do nothing
			}
			
			for(DataMap map : list){
				arr.add(map.getString("memberId"));
				hashFilterForMemberReverse.add(new ChatMember(map.getString("memberId"), roomNo).getHashCode());
			}
			
			
			
			for(int i = 0; i < arr.size(); i++){
				if(hashFilterForMember.contains(new ChatMember(arr.get(i), roomNo).getHashCode())) arr.remove(i);
			}
			
			memuids = arr.toArray(new String[arr.size()]);
			
			String prv = m.getString("desc");
			
			if(memuids.length != 0) {
				joinMember(roomId, memuids, deptCache, prv);
			}
			
			List<DataMap> chatMemberPairs = commMapper.getChatMemberPair(roomNo);
			
			for(DataMap chatMemberReverse : chatMemberPairs){
				if(!hashFilterForMemberReverse.contains(new ChatMember(chatMemberReverse.getString("device_f"), chatMemberReverse.getInt("room_f")).getHashCode()))
					unjoinMember(roomId, chatMemberReverse.getString("device_f"));
			}
			
		}
		// Synchronizing Member - end
		
		Iterator<String> iterator = hashFilterReverse.keySet().iterator();
		
		while(iterator.hasNext()){
			String key = iterator.next();
			Room room = hashFilterReverse.get(key);
			if(!hashFilter.contains(key)){
				String rid = room.roomId; 
				System.out.println("Quartz Job - Trying to delete the room which has " + rid + " as RoomID.");

				if(!room.type.equals(ROOMTYPE_COUNSELLING)) {
					if(ChatUtil.destroyRoom(rid)) {
							commMapper.deleteMember(rid);
							commMapper.deleteRoom(rid);
						System.out.println("-----> Deleting Job Succeeded.");
					}else{
						System.out.println("-----> Deleting Job Failed.");
					}
				}

			}
		}
		
		currentRooms = commMapper.getCurrentRooms(); // 현재 모든 방을 가져온다.
		for(DataMap m : currentRooms){ // 모든 루프를 돌며 서버 UID를 방에서 제외시킨다.
			ChatUtil.unjoinMember(SERVER_AUTO_UID, m.getString("roomId"), new String[]{SERVER_AUTO_UID}, false); // 채팅 서버 호출
		}
		

	}
	
	public String getTextOfDaynight(int pref_daynight){
		if(pref_daynight == DAYNIGHT_DAY) return DAYNIGHT_DAY_STRING;
		else if(pref_daynight == DAYNIGHT_NIGHT) return DAYNIGHT_NIGHT_STRING;
		else return "";
	}
	
	public int getIntOfDaynight(String pref_daynight){
		if(pref_daynight.equals(DAYNIGHT_DAY_STRING)) return DAYNIGHT_DAY;
		else if(pref_daynight.equals(DAYNIGHT_NIGHT_STRING)) return DAYNIGHT_NIGHT;
		else return DAYNIGHT_NONE;
	}
	
	public void createNewRoomInDB(String desc, String roomId, String type, String pref_dept, String pref_section, String pref_class, String pref_daynight, int grade){
		DataMap params = new DataMap();
		params.put("desc", desc);
		params.put("roomId", roomId);
		params.put("type", type);
		params.put("pref_dept", pref_dept);
		params.put("pref_section", pref_section);
		params.put("pref_class", pref_class);
		params.put("pref_daynight", pref_daynight);
		params.put("pref_grade", grade);
		
		commMapper.createRoom(params);
		
	}
	
	public int getRoomNumber(String rid){
		int no = commMapper.getRoomNumber(rid);
		return no;
	}
	
	public Room RawToRoom(DataMap map, String roomType){
		Room room = new Room();
				
		room.type = roomType;
		
		switch(roomType){
		case ROOMTYPE_CLASS:
			room.pref_dept = "";
			room.pref_section = "";
			room.pref_class = map.getString("classcode") != null ? map.getString("classcode") : "";
			room.pref_daynight = "";
			room.pref_grade = 0;
			break;
		case ROOMTYPE_DEPARTMENT:
			room.pref_dept = map.getString("no") != null ? map.getString("no") : "";
			room.pref_section = "";
			room.pref_class = "";
			room.pref_daynight = "";
			room.pref_grade = 0;
			break;
		case ROOMTYPE_SECTION:
			room.pref_dept = map.getString("no") != null ? map.getString("no") : "";
			room.pref_section = map.getString("pref_section") != null ? map.getString("pref_section") : "";
			room.pref_class = "";
			room.pref_daynight = map.getString("pref_daynight") != null ? map.getString("pref_daynight") : "";
			room.pref_grade = map.get("pref_grade") != null ? map.getInt("pref_grade") : 0;
			break;
		case ROOMTYPE_COUNSELLING: default : return room;
				
		}
		
		return room;
	}
	
	public Room dataMaptoRoom(DataMap map){
		Room room = new Room();
		
		if(map.getString("type").equals(ROOMTYPE_COUNSELLING)) return room;
		
		room.room_no = map.getInt("no");
		room.type = map.getString("type");
		room.pref_dept = map.getString("pref_dept") != null ? map.getString("pref_dept") : "";
		room.pref_section = map.getString("pref_section") != null ? map.getString("pref_section") : "";
		room.pref_class = map.getString("pref_class") != null ? map.getString("pref_class") : "";
		room.pref_daynight = map.getString("pref_daynight") != null ? map.getString("pref_daynight") : "";
		room.pref_grade = map.getInt("pref_grade");
		room.roomId = map.getString("roomId");
		
		return room;
	}
	
	public String createRoomHash(String type, String dept, String section, String classcode, int grade, int dayNight){
		String roomDesc = ""; 
		// TODO
		switch(type){
		case ROOMTYPE_DEPARTMENT : 
			roomDesc = "DEPTCODE_HASH_" + dept + "_DEPT_HASH_";
			break;
		case ROOMTYPE_SECTION :
			String dn = dayNight == 0 ? "DAY" : "NIGHT";
			roomDesc = "DEPTCODE_HASH_"+ dept + "SECTIONHASH" + grade + "_SECTION_HASH_" + dn + "_SECTION_HASH_" + section;
			break;
		case ROOMTYPE_CLASS : 
			roomDesc = "CLASSCODE_HASH_"+ classcode + "_CLASS_HASH_";
			break;
		case ROOMTYPE_COUNSELLING :  
			roomDesc = "_COUNSELLING_ROOM_CANNOT_BE_NAMED_WITH_THIS_PROCESS_ERROR_";
			break;
		default : roomDesc = "_NO_DESC_"; break;
		}
		
		System.out.println("QUARTZ JOB : createRoomHash - " + roomDesc);
		
		return roomDesc;
	}
	
	public String createRoomDescription(String type, String dept, String section, String classcode, int grade, int dayNight){
		String roomDesc = ""; 
		
		switch(type){
		case ROOMTYPE_DEPARTMENT : 
			roomDesc = exCommMapper.getDeptName(dept);
			break;
		case ROOMTYPE_SECTION :
			String dn = dayNight == 0 ? "주간" : "야간";
			roomDesc = exCommMapper.getDeptName(dept) + " " + grade + "학년 " + dn + " " + section + "반";
			break;
		case ROOMTYPE_CLASS : 
			roomDesc = exCommMapper.getClassName(classcode) + classcode.substring(5, 8);
			break;
		case ROOMTYPE_COUNSELLING :  
			roomDesc = "_COUNSELLING_ROOM_CANNOT_BE_NAMED_WITH_THIS_PROCESS_ERROR_";
			break;
		default : roomDesc = "_NO_DESC_"; break;
		}
		
		System.out.println("QUARTZ JOB : createRoomDescription - " + roomDesc);
		
		return roomDesc;
	}
	
}