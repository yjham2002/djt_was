package com.appg.djTalk.common.util.chat;

import org.json.JSONArray;
import org.json.JSONObject;

import com.appg.djTalk.common.constants.Constants;
import com.appg.djTalk.common.util.FileLogUtil;
import com.appg.djTalk.common.util.JsonUtil;
import com.appg.djTalk.common.util.chat.contants.ChatConstants;
import com.appg.djTalk.common.util.httpClient.SimpleHttpClient;
import com.appg.djTalk.common.util.httpClient.SimpleHttpClient.GBean;

public class ChatUtil{

	private static final boolean	IS_DEBUG	= Constants.IS_DEBUG;

	/**
	 * 방생성
	 * 
	 * @param uid
	 * @param memuids
	 * @return
	 */
	public static String creatTeamRoom(String uid, String[] memuids, String type)
	{
		//String rid = ID.generateSingleRoomId(uid, uid2);
		String rid = type + ID.generateMultiRoomId(ChatConstants.PLATFORM_ID_ALL, uid);
		
		
		SimpleHttpClient client = new SimpleHttpClient();

		client.setUri(ChatConstants.HOST_DOMAIN);
		client.addParameter("cmd", ChatConstants.CMD_MAKE_ROOM);
		client.addParameter("uid", uid);
		client.addParameter("rid", rid);
		client.addParameter("vid", ChatConstants.VENDOR);
		client.addParameter("rtype", ChatConstants.ROOM_TYPE_MUTI);

		for (int i = 0; i < memuids.length; i++)
		{
			client.addParameter("memuids", memuids[i]);
		}

		GBean bean = client.execute();

		if(IS_DEBUG)
		{
			System.out.println("HOST_DOMAIN -> " + ChatConstants.HOST_DOMAIN);
			System.out.println("ChatUtil -> creatTeamRoom");
			System.out.println("Http Status : " + bean.getHttpStatus());
			System.out.println("body : " + (String) bean.get("body"));
		}

		if(bean.getException() != null)
			return null;

		if(bean.getHttpStatus() != 200)
			return null;

		if(bean == null || bean.get("error") != null)
			return null;

		JSONObject json = JsonUtil.Obj.create((String) bean.get("body"));

		if(json == null)
			return null;

		if(JsonUtil.Obj.getInt(json, "rcode", -1) == 1)
			return rid;
		else
			return null;
	}

	/**
	 * 방 파기
	 * 
	 * @param rid
	 * @return
	 */
	public static boolean destroyRoom(String rid)
	{
		SimpleHttpClient client = new SimpleHttpClient();
		client.setUri(ChatConstants.HOST_DOMAIN);
		client.addParameter("cmd", ChatConstants.CMD_DESTROY_ROOM);
		client.addParameter("rid", rid);
		client.addParameter("vid", ChatConstants.VENDOR);
		client.addParameter("data", "1");
		GBean bean = client.execute();

		if(IS_DEBUG)
		{
			System.out.println("HOST_DOMAIN -> " + ChatConstants.HOST_DOMAIN);
			System.out.println("ChatUtil -> destroyRoom");
			System.out.println("Http Status : " + bean.getHttpStatus());
			System.out.println("body : " + bean.get("body").toString());
		}

		if(bean.getException() != null)
			return false;

		if(bean.getHttpStatus() != 200)
			return false;

		if(bean == null || bean.get("error") != null)
			return false;

		JSONObject json = JsonUtil.Obj.create(bean.get("body").toString());

		if(json == null)
			return false;

		if(JsonUtil.Obj.getInt(json, "rcode", -1) == 1)
			return true;
		else
			return false;
	}

	/**
	 * 메세지 전송
	 * 
	 * @param uid
	 * @param rid
	 * @param rtype
	 * @param data
	 * @return
	 */
	public static boolean sendMessage(String uid, String rid, String rtype, JSONObject data)
	{
		SimpleHttpClient client = new SimpleHttpClient();
		client.setUri(ChatConstants.HOST_DOMAIN);
		client.addParameter("cmd", ChatConstants.CMD_SEND_MESSAGE);
		client.addParameter("vid", ChatConstants.VENDOR);
		client.addParameter("uid", uid);
		client.addParameter("rid", rid);
		client.addParameter("rtype", rtype);
		// client.addJsonParameter("data", new String(data.toString().getBytes(), Charset.forName("UTF-8")));
		client.addJsonParameter("data", data.toString());

		GBean bean = client.execute();

		if(bean.getException() != null)
			return false;
		else if(bean.getHttpStatus() != 200)
			return false;
		else if(bean == null || bean.get("error") != null)
			return false;

		if(IS_DEBUG)
		{
			System.out.println("HOST_DOMAIN -> " + ChatConstants.HOST_DOMAIN);
			System.out.println("ChatUtil -> sendMessage");
			System.out.println("data : " + data.toString());
			System.out.println("Http Status : " + bean.getHttpStatus());
			System.out.println("body : " + bean.get("body").toString());
		}
		JSONObject json = JsonUtil.Obj.create(bean.get("body").toString());

		if(json == null)
			return false;
		else if(JsonUtil.Obj.getInt(json, "rcode", -1) == 1)
			return true;
		else
			return false;
	}

	/**
	 * 방 초대
	 * 
	 * @param uid
	 * @param rid
	 * @param memuids
	 * @return
	 */
	public static boolean joinMember(String uid, String rid, String[] memuids, boolean isSendUnjoinMsg)
	{
		SimpleHttpClient client = new SimpleHttpClient();
		client.setUri(ChatConstants.HOST_DOMAIN);
		
		if(isSendUnjoinMsg)
			client.addParameter("cmd", ChatConstants.CMD_JOIN_MEMBER_SEND_MSG);
		else
			client.addParameter("cmd", ChatConstants.CMD_JOIN_MEMBER);
		
		client.addParameter("vid", ChatConstants.VENDOR);
		client.addParameter("uid", uid);
		client.addParameter("rid", rid);
		client.addParameter("data", "1");

		for (int i = 0; i < memuids.length; i++)
		{
			client.addParameter("memuids", memuids[i]);
		}

		GBean bean = client.execute();

		if(IS_DEBUG)
		{
			System.out.println("HOST_DOMAIN -> " + ChatConstants.HOST_DOMAIN);
			System.out.println("ChatUtil -> joinMember");
			System.out.println("Http Status : " + bean.getHttpStatus());
			System.out.println("body : " + bean.get("body").toString());
		}

		if(bean.getException() != null)
			return false;

		if(bean.getHttpStatus() != 200)
			return false;

		if(bean == null || bean.get("error") != null)
			return false;

		JSONObject json = JsonUtil.Obj.create(bean.get("body").toString());

		if(json == null)
			return false;

		if(JsonUtil.Obj.getInt(json, "rcode", -1) == 1)
			return true;
		else
			return false;
	}

	/**
	 * 강제퇴장
	 * 
	 * @param uid
	 * @param rid
	 * @param memuids
	 * @return
	 */
	public static boolean unjoinMember(String uid, String rid, String[] memuids, boolean isSendUnjoinMsg)
	{
		SimpleHttpClient client = new SimpleHttpClient();
		client.setUri(ChatConstants.HOST_DOMAIN);

		if(isSendUnjoinMsg)
			client.addParameter("cmd", ChatConstants.CMD_UNJOIN_MEMBER_SEND_MSG);
		else
			client.addParameter("cmd", ChatConstants.CMD_UNJOIN_MEMBER);

		client.addParameter("vid", ChatConstants.VENDOR);
		client.addParameter("uid", uid);
		client.addParameter("rid", rid);
		client.addParameter("data", "1");

		for (int i = 0; i < memuids.length; i++)
		{
			client.addParameter("memuids", memuids[i]);
		}

		GBean bean = client.execute();

		if(IS_DEBUG)
		{
			System.out.println("HOST_DOMAIN -> " + ChatConstants.HOST_DOMAIN);
			System.out.println("ChatUtil -> unjoinMember");
			System.out.println("Http Status : " + bean.getHttpStatus());
			System.out.println("body : " + bean.get("body").toString());
		}

		if(bean.getException() != null)
			return false;

		if(bean.getHttpStatus() != 200)
			return false;

		if(bean == null || bean.get("error") != null)
			return false;

		JSONObject json = JsonUtil.Obj.create(bean.get("body").toString());

		if(json == null)
			return false;

		if(JsonUtil.Obj.getInt(json, "rcode", -1) == 1)
			return true;
		else
			return false;
	}

	/**
	 * 채팅방 메세지 리스트
	 * 
	 * @param rid
	 * @return
	 */
	public static JSONObject getChatMsgList(String rid, String ymd)
	{
		SimpleHttpClient client = new SimpleHttpClient();
		client.setUri(ChatConstants.HOST_DOMAIN);
		client.addParameter("cmd", ChatConstants.CMD_CHAT_LIST);
		client.addParameter("vid", ChatConstants.VENDOR);
		client.addParameter("rid", rid);
		client.addParameter("ymd", ymd);

		GBean bean = client.execute();

		if(IS_DEBUG)
		{
			System.out.println("HOST_DOMAIN -> " + ChatConstants.HOST_DOMAIN);
			System.out.println("ChatUtil -> getChatMsgList");
			System.out.println("Http Status : " + bean.getHttpStatus());
			System.out.println("body : " + bean.get("body").toString());
		}

		if(bean.getException() != null)
			return null;

		if(bean.getHttpStatus() != 200)
			return null;

		if(bean == null || bean.get("error") != null)
			return null;

		JSONObject json = JsonUtil.Obj.create(bean.get("body").toString());

		if(json == null)
			return null;

		if(JsonUtil.Obj.getInt(json, "rcode", -1) == 1)
			return json;
		else
			return null;
	}

	/**
	 * 채팅방 메세지 리스트
	 * 
	 * @param rid
	 * @return
	 */
	public static JSONArray getChatLastMsgList(String[] rids)
	{
		SimpleHttpClient client = new SimpleHttpClient();
		client.setUri(ChatConstants.HOST_DOMAIN);
		client.addParameter("cmd", ChatConstants.CMD_LAST_CHAT);
		client.addParameter("vid", ChatConstants.VENDOR);

		for (int i = 0; i < rids.length; i++)
		{
			client.addParameter("rids", rids[i]);
		}

		GBean bean = client.execute();

		if(IS_DEBUG)
		{
			System.out.println("HOST_DOMAIN -> " + ChatConstants.HOST_DOMAIN);
			System.out.println("ChatUtil -> getChatLastMsgList");
			System.out.println("Http Status : " + bean.getHttpStatus());
			System.out.println("body : " + bean.get("body").toString());
		}

		if(bean.getException() != null)
		{
			System.out.println(bean.getException());
			return null;
		}

		if(bean.getHttpStatus() != 200)
			return null;

		if(bean == null || bean.get("error") != null)
			return null;

		JSONObject json = JsonUtil.Obj.create(bean.get("body").toString());

		if(json == null)
			return null;

		if(JsonUtil.Obj.getInt(json, "rcode", -1) == 1)
		{
			JSONArray msgList = JsonUtil.Obj.getJSONArray(json, "list");
			return msgList;
		}

		else
			return null;
	}

	/**
	 * 방데이터 조회
	 * 
	 * @param rid
	 * @return
	 */
	public static JSONObject getChatRoomInfo(String rid)
	{
		SimpleHttpClient client = new SimpleHttpClient();

		client.setUri(ChatConstants.HOST_DOMAIN);
		client.addParameter("cmd", ChatConstants.CMD_ROOM_INFO);
		client.addParameter("rid", rid);
		client.addParameter("vid", ChatConstants.VENDOR);

		GBean bean = client.execute();

		if(IS_DEBUG)
		{
			System.out.println("HOST_DOMAIN -> " + ChatConstants.HOST_DOMAIN);
			System.out.println("ChatUtil -> getChatRoomInfo");
			System.out.println("Http Status : " + bean.getHttpStatus());
			System.out.println("body : " + (String) bean.get("body"));
			
			FileLogUtil.writeFileLog(Constants.LOG_PATH_BASE, "sync_error", "debug_chat", "Http Status : " + bean.getHttpStatus());
			FileLogUtil.writeFileLog(Constants.LOG_PATH_BASE, "sync_error", "debug_chat", "body : " + (String) bean.get("body"));

		}

		if(bean.getException() != null)
			return null;

		if(bean.getHttpStatus() != 200)
			return null;

		if(bean == null || bean.get("error") != null)
			return null;

		JSONObject json = JsonUtil.Obj.create(bean.get("body").toString());

		if(json == null)
			return null;

		if(JsonUtil.Obj.getInt(json, "rcode", -1) == 1)
			return json;

		else
			return null;
	}

	/**
	 * 메세지 json 생성
	 * 
	 * @param msg
	 * @param msg_type
	 * @return
	 */
	public static JSONObject makeMsgJsonData(String msg, int msgType)
	{
		JSONObject json = new JSONObject();
		JsonUtil.Obj.put(json, "msg", msg);
		JsonUtil.Obj.put(json, "msg_type", msgType);
		JsonUtil.Obj.put(json, "msg_meta", new JSONObject());

		return json;
	}

}
