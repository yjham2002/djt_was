<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<%
/*
	List<String> topMenu = new ArrayList<String>();
	String accessLevelCode = (String)request.getAttribute("accessLevelCode");
	List<MenuRightBean> leftMenuList = (List)request.getAttribute("leftMenuList");
*/
%>
		<div class="leftWrap">
			<ul class="sidemenu">
				<h1>정산</h1>
				<li>
					<a href="/NaeKkot/admin/superBill/corporateList">가맹점별 정산</a>
				</li>
				<li>
					<a href="/NaeKkot/admin/superBill/orderList">주문별 정산</a>
				</li>
				<li>
					<c:if test="${accessLevelCode ne 2 }">
						<a href="/NaeKkot/admin/superBill/depositList">예치금</a>
					</c:if>
					<c:if test="${accessLevelCode eq 2 }">
						<a href="/NaeKkot/admin/superBill/depositView?franchiseNumber=${franchiseNumber }">예치금</a>
					</c:if>

				</li>
		

				<!-- 
				<li matchUrl="/admin/userManage/userList.php">
					<a href="/admin/userManage/userList.php?isApplyID=0">탈퇴회원 리스트</a>
				</li>
		 		-->
			</ul>
		
			<div class="left_blank"></div>
		
		</div>