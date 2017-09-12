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
		
		if(getCookie("isSuperAdmin") == "1") {
			$(".jHome").show();
		} else {
			$(".jHome").hide();
		}
		
		$("div.nav").find("a").removeClass("selected");
		$("div.nav").find("a[matchUrl='" + $(location).attr("pathname") + $(location).attr("search") + "']").addClass("selected");
		
		
		$(".jHome").click2(function(){
			setCookie("isSuperAdmin", "0", -3600);
			
			location.href = "/NaeKkot/admin/home";
			/*
			$.ajax({
				url : rootPath + "/home",
				type : "POST",
				data : {},
				complete : function(){
					location.href = rootPath + "/superOrder/orderList?pageType=1";
				}
				
			});
			*/
			
		});
		
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
					<td class="logo">
						내꽃 <span>${franchiseName } 관리자</span>
					 	<span class="jHome" style="display:none; font-weight: normal; color: white; background-color: #014a7e; margin: 10px 15px; width: 100px; padding: 5px 40px; ">홈</span>					
					</td>
					<td class="log" style="font-size: 12px;">
						<strong>${adminID }</strong> 님이 로그인하였습니다.  
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
					<a matchUrl="1"
						href="${properties.project_path}/admin/order/orderList?pageType=1&userNumber=${userNumber}">수주내역</a>					
				</li>
				<li>
					<a matchUrl="1"
						href="${properties.project_path}/admin/order/orderList?pageType=2&userNumber=${userNumber}">발주내역</a>					
				</li>
				<li>
					<a matchUrl=""
						href="${properties.project_path}/admin/product/productList?userNumber=${userNumber}">상품리스트</a>					
				</li>
				<li>
					<a matchUrl=""
						href="${properties.project_path}/admin/user/companyView?userNumber=${userNumber}">가맹점정보</a>					
				</li>
				<li>
					<a matchUrl=""
						href="${properties.project_path}/admin/superBill/corporateList">정산</a>					
				</li>
				<li>
					<a matchUrl=""
						href="${properties.project_path}/admin/superStatistics/statistics">통계</a>					
				</li>
				<li>
					<a matchUrl=""
						href="${properties.project_path}/admin/photo/photoList?userNumber=${userNumber}">사진방</a>					
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
		
	