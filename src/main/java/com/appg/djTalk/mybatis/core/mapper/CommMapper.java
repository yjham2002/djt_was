package com.appg.djTalk.mybatis.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.json.JSONArray;
import org.springframework.stereotype.Repository;

import com.appg.djTalk.common.bean.DataMap;

@Repository
public interface CommMapper{
	
	public void resetChatRoom(@Param("type") String type);
	
	public void resetChatMember(@Param("type") String type);
	
	public void forceUpdateToken(DataMap params);
	
	public void updateArticle(@Param("state") int state, @Param("no") int no);
	
	public List<DataMap> getMyArticles(@Param("memberId") String memberId);
	
	public List<DataMap> getWroteArticles(@Param("memberId") String memberId);
	
	public int getArticleCount(DataMap params);
	
	public void insertArticle(DataMap params);
	
	public int getBoardMaxSequence();
	
	public int getNoticeMaxSequence();

	public DataMap getRegKeyWakeOnce(@Param("uid") String uid);
	
	public void insertPublicOnNotice(DataMap map);
	
	public void insertPublic(DataMap map);
	
	public void insertBoard(DataMap map);
	
	public void insertNotice(DataMap map);
	
	public List<DataMap> getUsersWithKey(@Param("list") List<DataMap> list);
	
	public List<DataMap> getRegKeyWake(@Param("list") List<DataMap> list);
	
	public List<DataMap> getRegKeyAll();
	
	public List<DataMap> getRegKeyList(@Param("list") List<DataMap> list);
	
	public void invalidateRoom(@Param("rid") String rid);
	
	public void insertChatHistory(DataMap params);
	
	public int isUserExisting(@Param("memberId") String memberId);
	
	public DataMap getRoomDetail(@Param("rNo") int rNo);
	
	public int isRedundantCounselling(@Param("memberId") String memberId);
	
	public List<String> isAlreadyOngoing(@Param("memberId") String memberId);
	
	public String getPrivateChatId(@Param("memberId") String memberId, @Param("memberIdo") int memberIdo);
	
	public List<DataMap> getChatMemberPair(@Param("roomId") int roomId);
	
	public List<DataMap> getChatMemberPairAll();
	
	public List<DataMap> getMemberIds();
	
	public void deleteSpecMember(@Param("roomNo") int room, @Param("device") String device);
	
	public void setMember(@Param("roomNo") int room, @Param("device") String device, @Param("private") String prv);
	
	public void updateChatMemberDesc(DataMap params);
	
	public void setVibration(@Param("memberId") String memberId, @Param("mode") int mode);
	
	public void setNotiSound(@Param("memberId") String memberId, @Param("mode") String mode);
	
	public int getRoomNumber(@Param("roomId") String rid);
	
	public void createRoom(DataMap params);
	
	public void deleteMember(@Param("roomId") String roomId);
	
	public void deleteRoom(@Param("roomId") String roomId);
	
	public DataMap getMultiMedia(@Param("mf") int mf);
	
	public List<DataMap> getCurrentRooms();
	
	public List<DataMap> getAvailableRooms(@Param("memberId") String memberId);
	
	public int logFile(DataMap params);
	
	public int logFileBulk(@Param("saveList") List<DataMap> saveList);
	
	public int hasAlreadyRegistered(@Param("memberId") String memberId);
	
	public int registerNewly(DataMap param);
	
	public DataMap getUser(@Param("memberId") String memberId);
	
	public String getCurrentRegKey(@Param("memberId") String memberId);
	
	public void updateUser(DataMap params);
	
	public void updateUserIntro(DataMap params);
	
	public int getPushState(@Param("memberId") String memberId);
	
	public void pushToggle(@Param("memberId") String memberId);
	
	public int getNSoundState(@Param("memberId") String memberId);
	
	public void soundToggle(@Param("memberId") String memberId);
	
	public void setDisturb(@Param("memberId") String memberId, @Param("start") String start, @Param("end") String end);
	
	public void offDisturb(@Param("memberId") String memberId);
	
	public void setProfile(@Param("memberId")String memberId, @Param("profile")int profile, @Param("nickName") String nickName);
	
	public void setProfileNoProfile(@Param("memberId")String memberId, @Param("nickName") String nickName);
	
	public DataMap getDisturbState(@Param("memberId") String memberId);
	
	public List<DataMap> getUserList(@Param("list") List<String> list);
	
	public List<DataMap> getUserListOfRoom(@Param("room") int room);
	
	public DataMap getTodayLast(@Param("memberId") String memberId, @Param("type") int type);
	
	public List<DataMap> getTodayList(@Param("memberId") String memberId, @Param("type") int type, @Param("page") int lastRead, @Param("limit") int limit);
	
	public List<DataMap> getTodayListInfinite(@Param("memberId") String memberId, @Param("type") int type, @Param("lastRead") int lastRead);
	
}
 