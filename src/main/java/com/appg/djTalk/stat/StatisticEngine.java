package com.appg.djTalk.stat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.exception.ServiceException;
import com.appg.djTalk.mybatis.core.mapper.CommMapper;
import com.appg.djTalk.mybatis.external.mapper.ExCommMapper;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import databases.DBMSManager;
import databases.DBManager;

@Service
public class StatisticEngine {

	private static final String HOST = "210.125.136.41";
	private static final int PORT = 27017;
	private static final String DATABASE_NAME = "GCHAT_NG_V1";
	private static final String COLLECTION_NAME = "message";
	private static final String MONGO_AGGR_KEY = "_id";
	
	public DataMap executeMongoCountMap(String sDate, String eDate) {
		DataMap retVal = new DataMap();
		
		MongoClient mongoClient = new MongoClient(HOST, PORT);

		DB db = mongoClient.getDB(DATABASE_NAME);

		DBCollection documentMongoCollection = db.getCollection(COLLECTION_NAME);
		
		final String fmt = "yyyy-MM-dd";
		Date dateS = null;
		Date dateE = null;
		
		try {
			dateS = new SimpleDateFormat(fmt).parse(sDate);
			dateE = new SimpleDateFormat(fmt).parse(eDate);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(dateS);
		long sLong = c.getTimeInMillis();
		c.setTime(dateE);
		long eLong = c.getTimeInMillis();
		
		DBObject time = new BasicDBObject("$match", new BasicDBObject("mtime", new BasicDBObject("$gte", sLong).append("$lte", eLong)));
		
		DBObject gField = new BasicDBObject(MONGO_AGGR_KEY, "$senduid");
		gField.put("cnt", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", gField);
		
		DBObject sortFields = new BasicDBObject("count", -1);
	    DBObject sort = new BasicDBObject("$sort", sortFields );
	    
		AggregationOutput output = documentMongoCollection.aggregate(time, group, sort);

		Iterator<DBObject> iterator = output.results().iterator();
		
		while(iterator.hasNext()) {
			DBObject row = iterator.next();
			Map rowMap = row.toMap();
			String cnt = rowMap.get("cnt").toString();
			if(cnt == null) cnt = "0";
			if(rowMap.get(MONGO_AGGR_KEY) == null) continue;
			retVal.put(rowMap.get(MONGO_AGGR_KEY).toString(), Integer.parseInt(cnt));
		}
		return retVal;
	}
	
	public DataMap executeDeptCache() {
		DataMap deptCache = new DataMap();
		
		List<DataMap> deptCacheList = DBMSManager.getInstance().getList("SELECT * FROM COV_DEPT_TALK");
		
		for(DataMap dt : deptCacheList){
			deptCache.put(dt.getString("DEPT_CD"), dt.getString("DEPT_NM"));
		}
		
		return deptCache;
	}
	
	public DataMap execute(String sDate, String eDate) throws ServiceException {
		DataMap map = new DataMap();
		List<StatModel> installStat = new Vector();
		List<StatModel> messageStat = new Vector();
		int installTotal = 0;
		int messageTotal = 0;
		
		DataMap deptNames = executeDeptCache();
		DataMap msgs = executeMongoCountMap(sDate, eDate);
		DataMap classifier = classifiedMap();
		List<String> installer = getInstallers(sDate, eDate);

		HashMap<String, Integer> counterForInstall = new HashMap<String, Integer>();
		HashMap<String, Integer> counterForMessage = new HashMap<String, Integer>();
		
		for(String memberId : installer) {
			String key = classifier.getString(memberId);
			int temp = 0;
			if(counterForInstall.containsKey(key)) {
				temp = counterForInstall.get(key);
			}
			counterForInstall.put(key, temp + 1);
		}
		
		Iterator<String> mongoIter = msgs.keySet().iterator();
		
		while(mongoIter.hasNext()) {
			String memberId = mongoIter.next();
			int value = msgs.getInt(memberId);
			String dept = classifier.getString(memberId);
			int temp = 0;
			if(counterForMessage.containsKey(dept)) {
				temp = counterForMessage.get(dept);
			}
			counterForMessage.put(dept, temp + value);
		}

		Iterator<String> insIter = counterForInstall.keySet().iterator();
		
		while(insIter.hasNext()) {
			String dept = insIter.next();
			String desc = deptNames.getString(dept);
			int value = counterForInstall.get(dept);
			
			if(dept == null || dept.equals("")) continue;
			
			StatModel statModel = new StatModel(dept, desc, value);
			installTotal += value;
			installStat.add(statModel);
		}
		
		Iterator<String> msgIter = counterForMessage.keySet().iterator();
		
		while(msgIter.hasNext()) {
			String dept = msgIter.next();
			String desc = deptNames.getString(dept);
			int value = counterForMessage.get(dept);
			
			if(dept == null || dept.equals("")) continue;
			
			StatModel statModel = new StatModel(dept, desc, value);
			messageTotal += value;
			messageStat.add(statModel);
		}
		
		Comparator<StatModel> comparator = new Comparator<StatModel>() {
			@Override
			public int compare(StatModel o1, StatModel o2) {
				if(Integer.parseInt(o1.getDept()) > Integer.parseInt(o2.getDept())) return 1;
				else if(Integer.parseInt(o1.getDept()) < Integer.parseInt(o2.getDept())) return -1;
				else return 0;
			}
		};
		
		Collections.sort(installStat, comparator);
		Collections.sort(messageStat, comparator);
		
		map.put("installers", installStat);
		map.put("messages", messageStat);
		
		map.put("iTotal", installTotal);
		map.put("mTotal", messageTotal);
		
		return map;
	}
	
	public List<String> getInstallers(String sDate, String eDate) {
		List<String> set = new Vector<String>();
		
		String sql = "SELECT no FROM tblDevice WHERE 1=1 ";
		
		if((sDate != null && !sDate.equals(""))) {
			sql += " AND DATE('" + sDate + "') <= DATE(regDate) ";
		}
		
		if((eDate != null && !eDate.equals(""))) {
			sql += " AND DATE(regDate) <= DATE('" + eDate + "')";
		}
		
		List<DataMap> users = DBManager.getInstance().getList(sql);
		for(DataMap map : users) {
			set.add(map.getString("no"));
		}
		
		
		return set;
	}
	
	public DataMap classifiedMap() {
		DataMap map = new DataMap();
		
		List<DataMap> list = DBMSManager.getInstance().getList("SELECT memberId, dept FROM\r\n" + 
				"  (\r\n" + 
				"  SELECT STUD_ID AS memberId, DEPT_CD AS dept FROM TALK_SLV_MST WHERE STATE_CD = '10'\r\n" + 
				"  UNION ALL\r\n" + 
				"  SELECT EMP_ID AS memberId, DEPT_CD AS dept FROM AMV_MST_TALK WHERE STATE_CD = '10'\r\n" + 
				") AS temp");
		
		for(DataMap row : list) {
			map.put(row.getString("memberId"), row.getString("dept"));
		}
		
		return map;
	}
	
	public static void main(String... args) {
		StatisticEngine statisticEngine = new StatisticEngine();
		try {
			DataMap retVal = statisticEngine.execute("2017-01-01", "2017-08-29");
			ObjectMapper mapper = new ObjectMapper();
			try {
				System.out.println(mapper.writeValueAsString(retVal));
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
