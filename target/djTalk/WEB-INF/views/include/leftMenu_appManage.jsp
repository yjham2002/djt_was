<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*"%>
<%
/*
	List<String> topMenu = new ArrayList<String>();
	String accessLevelCode = (String)request.getAttribute("accessLevelCode");
	List<MenuRightBean> leftMenuList = (List)request.getAttribute("leftMenuList");
*/
%>
		<div class="leftWrap">
			<ul class="sidemenu">
				<h1>앱관리 및 사진방</h1>
				<li>
					<a href="/NaeKkot/admin/superAppManage/photoList">사진방</a>
				</li>
				<li>
					<a href="/NaeKkot/admin/superAppManage/requestList">가맹점 신청 내역</a>
				</li>
				<li>
					<a href="/NaeKkot/admin/superAppManage/magazine?pageType=1">INFORMATION</a>
				</li>
				<li>
					<a href="/NaeKkot/admin/superAppManage/event?pageType=3">이벤트</a>
				</li>
				<li>
					<a href="/NaeKkot/admin/superAppManage/notice?pageType=2">공지</a>
				</li>
				<li>
					<a href="/NaeKkot/admin/superAppManage/account">계정</a>
				</li>
				<li>
					<a href="/NaeKkot/admin/superAppManage/etc">기타</a>
				</li>
				<%--<li matchUrl="${pageContext.request.contextPath}/admin/commonCode/groupCodeList"></li> --%>
		

				<!-- 
				<li matchUrl="/admin/userManage/userList.php">
					<a href="/admin/userManage/userList.php?isApplyID=0">탈퇴회원 리스트</a>
				</li>
		 		-->
			</ul>
		
			<div class="left_blank"></div>
		
		</div>