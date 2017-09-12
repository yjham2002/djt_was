package com.appg.djTalk.common.util.chat.contants;

public class ChatConstants{
	
	public static final String	HOST_DOMAIN							= "http://localhost:4080";

	public static final int		VENDOR								= 3000;

	public static final String	SERVER_UID							= "AGCMASTER";

	public static final String	CMD_MAKE_ROOM						= "MAKE_ROOM";
	public static final String	CMD_DESTROY_ROOM					= "DESTROY_ROOM";
	public static final String	CMD_SEND_MESSAGE					= "SEND_MESSAGE";
	public static final String	CMD_JOIN_MEMBER_SEND_MSG			= "JOIN_MEMBER";
	public static final String 	CMD_JOIN_MEMBER 					= "JOIN_MEMBER_WITHOUT_ALERT";
	public static final String	CMD_UNJOIN_MEMBER					= "UNJOIN_MEMBER_WITHOUT_ALERT";	// 디비상 강퇴처리만
	public static final String	CMD_UNJOIN_MEMBER_SEND_MSG			= "UNJOIN_MEMBER";					// 강퇴처리후 강퇴메세지 전송하는 CMD
	public static final String	CMD_CHAT_LIST						= "CHAT_LIST";
	public static final String	CMD_LAST_CHAT						= "LAST_CHAT";
	public static final String	CMD_ROOM_INFO						= "ROOM_INFO";

	public static final String	ROOM_TYPE_SINGLE					= "S";
	public static final String	ROOM_TYPE_MUTI						= "M";

	public static final short	PLATFORM_ID_AND						= 1;
	public static final short	PLATFORM_ID_IOS						= 2;
	public static final short	PLATFORM_ID_ALL						= 5;

	public static final short	MSG_TYPE_TEXT						= 0;
	public static final short	MSG_TYPE_IMAGE						= 1;
	public static final short	MSG_TYPE_VIDEO						= 2;
	public static final short	MSG_TYPE_DIRECT_REQUEST				= 3;
	public static final short 	MSG_TYPE_EMOTICON 					= 4;
	public static final short 	MSG_TYPE_VOICE 						= 5;
	public static final short	MSG_TYPE_LOCATION					= 6;
	public static final short	MSG_TYPE_SCHEDULE					= 7;
	public static final short	MSG_TYPE_VOTE						= 8;
	public static final short	MSG_TYPE_PHONE						= 9;
	public static final short	MSG_TYPE_SYSTEM						= 11;
	public static final short	MSG_TYPE_CUSTOM_SYSTEM_ROOM_DESTORY	= 99;
	public static final short	MSG_TYPE_CUSTOM_SYSTEM_MIN			= 90;

}