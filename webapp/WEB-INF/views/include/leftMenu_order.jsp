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
				<h1>수발주 리스트</h1>
				<li>
					<a href="/NaeKkot/admin/superOrder/orderList">수발주 리스트</a>
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