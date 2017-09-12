package com.appg.djTalk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appg.djTalk.common.batch.manager.BatchJobProcessManager;
import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.bean.ListBean;
import com.appg.djTalk.common.constants.Constants;
import com.appg.djTalk.common.exception.DefSeCode;
import com.appg.djTalk.common.exception.ServiceException;
import com.appg.djTalk.common.util.DataUtil;
import com.appg.djTalk.common.util.ListPage;
import com.appg.djTalk.mybatis.core.mapper.CommMapper;

@Service
public class SvcCoreCommon{
	
	@Autowired	CommMapper adminMapper;
	//@Autowired	BatchJobProcessManager batchJobProcessManager;
	
	/**
	 * 가맹점 수발주 리스트
	 * @return
	 * @throws ServiceException
	 
	public DataMap getOrderList(int cpage, String pageType, String userName, String franchiseName, String receiveName, String productCode, String processCode, String sDate,
			String eDate, int rowPerPage, int userNumber, int franchiseNumber) throws ServiceException{

		DataMap retVal = new DataMap();
		int blockSize = Constants.BLOCK_SIZE;
		int pageSize = rowPerPage;

		try {
			
			DataMap params = new DataMap();
			
			params.put("pageType", pageType);
			params.put("userName", userName);
			params.put("franchiseName", franchiseName);
			params.put("receiveName", receiveName);
			params.put("productCode", productCode);
			params.put("userName", userName);
			params.put("processCode", processCode);
			params.put("sDate", sDate);
			params.put("eDate", eDate);
			params.put("userNumber", userNumber);
			params.put("franchiseNumber", franchiseNumber);
			
			int totalCount = adminMapper.getOrderListCount(params);

			ListPage listPage = new ListPage(blockSize, pageSize, cpage, totalCount);
			
			int startNum = listPage.starNum;
			//int endNum = listPage.endNum;
			
			params.put("startNum", startNum);
			params.put("endNum", pageSize);
			
			List<DataMap> list = adminMapper.getOrderList(params);
			
			retVal.put("list", list);
			
			if(list.size() > 0){
				ListBean bean = new ListBean(blockSize, pageSize, cpage);
				bean.commit(totalCount);
				DataMap pager = DataUtil.pagingData(bean);
				retVal.put("pager", pager);
				
				String pageStr = DataUtil.pagingStr(bean);
				retVal.put("paging", pageStr);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		return retVal;
	}

	
	public DataMap getProductList(int cpage, int userNumber, String category, String productName, String productCode, int rowPerPage) throws ServiceException{
		DataMap retVal = new DataMap();
		int blockSize = Constants.BLOCK_SIZE;
		int pageSize = rowPerPage;

		try {

			DataMap params = new DataMap();
			
			params.put("userNumber", userNumber);
			params.put("category", category);
			params.put("productName", productName);
			params.put("productCode", productCode);
			
			int totalCount = adminMapper.getProductListCount(params);

			ListPage listPage = new ListPage(blockSize, pageSize, cpage, totalCount);
			
			int startNum = listPage.starNum;
			//int endNum = listPage.endNum;
			
			params.put("startNum", startNum);
			params.put("endNum", pageSize);
			
			List<DataMap> list = adminMapper.getProductList(params);
			List<DataMap> categoryList = superAdminMapper.getCategory();
			retVal.put("list", list);
			retVal.put("categoryList", categoryList);
			
			if(list.size() > 0){
				ListBean bean = new ListBean(blockSize, pageSize, cpage);
				bean.commit(totalCount);
				DataMap pager = DataUtil.pagingData(bean);
				retVal.put("pager", pager);
				
				String pageStr = DataUtil.pagingStr(bean);
				retVal.put("paging", pageStr);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		return retVal;
	}

	
	public void deleteProduct(int orderNumber) throws ServiceException{
		
		DataMap params = new DataMap();
		int result = 0;
	
		try{
	
			params.put("orderNumber", orderNumber);
			
			result = adminMapper.deleteProduct(params);
			
		if(result < 1)
			throw new ServiceException(DefSeCode.NOT_DB_SAVE_CODE, DefSeCode.NOT_DB_SAVE_MSG);
		}
		catch(ServiceException e){
			throw e;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		
	}

	

	

	public DataMap photoList() throws ServiceException{
		
		DataMap retVal = new DataMap();
		
		try{
			DataMap params = new DataMap();
			
//			params.put("productNumber", productNumber);
			
			List<DataMap> list = adminMapper.getImgList(params);
			
			retVal.put("list", list);

		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		return retVal;
		
		
	}
	
	public DataMap userLogin(String userId, String userPw) throws ServiceException{
		DataMap retVal = null;
		try{
			DataMap params = new DataMap();
			params.put("userId", userId);
			params.put("userPw", userPw);
			retVal = adminMapper.userLogin(params);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		return retVal;		
	}
	
	public int userApply(String userId, String userPw, String name) throws ServiceException{
		int ret = 0;
		try{
			DataMap params = new DataMap();
			params.put("userId", userId);
			params.put("userPw", userPw);
			params.put("name", name);
			
			int count = adminMapper.userCount(params);
			if(count != 0) return 0;
			
			ret = adminMapper.userApply(params);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		return ret;
	}
	
	public int writeBoard(int uid, String title, String content) throws ServiceException{
		int ret = 0;
		try{
			DataMap params = new DataMap();
			params.put("uid", uid);
			params.put("title", title);
			params.put("content", content);
			
			ret = adminMapper.writeBoard(params);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		return ret;
	}
	
	public DataMap getBoardDetail(int id) throws ServiceException{
		DataMap ret;
		try{
			DataMap params = new DataMap();
			params.put("id", id);
			params.put("bid", id);
			ret = adminMapper.getBoardDetail(params);
			if(ret != null) ret.put("comments", adminMapper.getCommentList(params));
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		return ret;
	}
	
	public DataMap getBoardList(int start, int count) throws ServiceException{
		DataMap list = new DataMap();
		
		try{
		DataMap params = new DataMap();
		params.put("start", start);
		params.put("count", count);
		
		List<DataMap> boards = adminMapper.getBoardList(params);
		
		for(DataMap map : boards){
			if(map.get("content").toString().length() > 20){
				String replace = map.get("content").toString().substring(0, 20) + " . . . ";
				map.put("content", replace);
			}
		}
		
		list.put("list", boards);
		list.put("boardCount", adminMapper.getBoardCount());
		
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		
		return list;
	}
	
	public int writeComment(String content, int uid, int bid, int flag) throws ServiceException{
		int ret = 0;
		try{
			DataMap params = new DataMap();
			params.put("content", content);
			params.put("uid", uid);
			params.put("bid", bid);
			params.put("flag", flag);
			
			ret = adminMapper.writeComment(params);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(DefSeCode.BASIC_ERROR_CODE, DefSeCode.BASIC_ERROR_MSG);
		}
		
		return ret;
	}
	*/
}
