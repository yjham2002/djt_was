package com.appg.djTalk.mybatis.external.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.appg.djTalk.common.bean.DataMap;

@Repository
public interface ExCommMapper{
	
	public String getReptCode(@Param("dept")String dept);
	
	public DataMap getTiming();
	
	public List<DataMap> getMemberDeptPair();
	
	public List<DataMap> getDeptCache();
	
	public List<DataMap> getAttendanceToday();
	
	public List<DataMap> getTodayBirth();
	
	public List<DataMap> getAllMember();
	
	public String getDeptById(@Param("memberId") String memberId);
	
	public List<DataMap> getCounsellingList(@Param("memberId") String memberId);
	
	public String getUserName(@Param("memberId") String memberId);
	
	public List<DataMap> getSectionMember(@Param("deptcode") String deptcode, @Param("grade") int grade, @Param("class") String class_f, @Param("daynight") String daynight, @Param("YEAR") String year, @Param("TERM_CD") String term);
	
	public List<DataMap> getDeptMember(@Param("deptcode") String deptcode, @Param("rept") String rept);
	
	public List<DataMap> getCourseTaker(@Param("classcode") String classcode);
	
	public List<DataMap> getCourseProf(@Param("classcode") String classcode);
	
	public String getMemberId(@Param("userAccount") String userAccount, @Param("userPassword") String userPassword);
	
	public String getMemberIdDev(@Param("userAccount") String userAccount);
	
	public DataMap getPhoneNumber(@Param("memberId") String memberId);
	
	public String getDeptName(@Param("deptcode") String deptcode);
	
	public String getClassName(@Param("classcode") String classcode);
	
	public List<DataMap> getDepts();
	
	public List<DataMap> getDeptReal();
	
	public List<DataMap> getLectures();
	
	public DataMap getMemberDetail(@Param("memberId") String memberId);
	
	
	public List<DataMap> getMemberDetail2(@Param("list") List<DataMap> list);
	
	public List<DataMap> getMemberDetailBulk(@Param("list") List<DataMap> list);
	
}
 