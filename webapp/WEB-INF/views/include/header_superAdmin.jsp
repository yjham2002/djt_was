<%@page import="java.util.HashMap"%>
<%@page import="com.appg.djTalk.common.util.CookieUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${properties.resources_path}/css/admin/base.css" rel="stylesheet" type="text/css" />
<link href="${properties.resources_path}/css/admin/btn.css" rel="stylesheet" type="text/css" />
<link href="${properties.resources_path}/css/admin/common.css" rel="stylesheet" type="text/css" />
<link href="${properties.resources_path}/css/admin/layout.css" rel="stylesheet" type="text/css" />

<c:import url="${properties.include_path}/metaData.jsp"/>

<title> Application Back Office</title>

<script type="text/javascript">
	var rootPath = "/NaeKkot/admin";
	
	$(document).ready(function(){
		$("div.nav").find("a").removeClass("selected");
		$("div.nav").find("a[matchUrl='" + $(location).attr("pathname") + $(location).attr("search") + "']").addClass("selected");
		
		//alert(document.URL);
		
		$(".jLogout").click2(function(){
			$.ajax({
				type : "POST" , 
				url : "/NaeKkot/admin/logout" , 
				dataType : "json" , 
				success : function(data){
					alert(data.returnMessage);
					setCookie("isSuperAdmin", "0", 0);
					location.replace("/NaeKkot/admin/login");
				} , 
				error : function(res) {
					alert(res.responseText);
				}
			});
		});
	});
	
</script>

</head>

<body>
<div id="Wrap">
	<!-- header -->
	<div id="Header">

		<!-- topmenu area -->
		<div class="hgroup">			
			<table>
				<tr>
					<td class="logo">내꽃 <span>${accessLevelCode eq 4 ? "슈퍼" : "서브" }관리자</span></td>
					<td class="log">
						<strong>${adminName }</strong> 님이 로그인하였습니다.  
						<a href="#" class = "jLogout"><img src="${properties.resources_path }/imgs/admin/btn_logout.gif"  alt="로그아웃" width="48" height="12" align="absmiddle"></a>
					</td> 
				</tr>
			</table>
		</div>

		<!-- topmenu area -->

		<!-- 메인메뉴요 -->
		<div class="nav">
			<ul>
				<li>
					<a matchUrl="/NaeKkot/admin/order"
						href="${properties.project_path}/admin/superOrder/orderList?pageType=1">전체수발주리스트</a>					
				</li>
				<li>
					<a matchUrl="/NaeKkot/admin/order"
						href="${properties.project_path}/admin/superOrder/orderList?pageType=2">미확인리스트</a>					
				</li>
				<li>
					<a matchUrl="/NaeKkot/admin/order"
						href="${properties.project_path}/admin/superOrder/orderList?pageType=3">취소리스트</a>					
				</li>
				<li>
					<a matchUrl="${properties.project_path}/admin/user/companyList"
						href="${properties.project_path}/admin/superUser/companyList">회원관리</a>					
				</li>
				<li>
					<a matchUrl="${properties.project_path}/admin/bill/"
						href="${properties.project_path}/admin/superBill/corporateList">정산</a>					
				</li>
				<li>
					<a matchUrl="${properties.project_path}/admin/statistics"
						href="${properties.project_path}/admin/superStatistics/statistics">통계</a>					
				</li>
				<li>
					<a matchUrl="1${properties.project_path}/admin/appManage/photoList"
						href="${properties.project_path}/admin/superAppManage/photoList">앱관리 및 사진방</a>					
				</li>
			</ul>
		</div>
		<!-- //메인메뉴요 -->

	</div>
	<!-- // header -->

	<div class="conHeight">
		<!-- contents start-->
		<div class="contWrap">

		<%-- 메뉴 권한에 따라 보여지는게 다른 부분 --%>
		
	