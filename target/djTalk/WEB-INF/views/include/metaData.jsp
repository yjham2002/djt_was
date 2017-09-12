<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCVT2NJEl9kr1cbrhuNpQDRlYOImjhak2Q&sensor=false&language=ko" ></script> -->
<script type="text/javascript" src="${properties.resources_path}/js/common.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/message.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/jsMap.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/jquery-1.9.0.min.js"></script>

<script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="${properties.resources_path}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/jquery.form.js"></script>

<%--
<script type="text/javascript" src="<%=Constants.RESOURCES_PATH %>/js/jQueryRotate.2.2.js"></script>
<script type="text/javascript" src="<%=Constants.RESOURCES_PATH %>/js/jQueryRotateCompressed.2.2.js"></script>
 --%>

<!-- 리치 js -->
<script type="text/javascript" src="${properties.resources_path}/js/jquery_rich/RichBaseExtends.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/jquery_rich/RichFramework-1.0.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/jquery_rich/RichElement-1.0.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/jquery_rich/RichBaseElementObject-1.0.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/jquery_rich/RHForm-1.0.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/jquery.DOMWindow.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="${properties.resources_path}/js/imgPreview.js"></script>

<script type="text/javascript" src="${properties.resources_path}/js/click_cal.js"></script>
<script type="text/javascript" src="${properties.resources_path}/fileUpload/fileUploadJS.js"></script>
	
<script>
	//*********** 전역변수 선언 스코프 ***********//
	var _domain			= "http://106.240.232.36:8890";
	var _urlPath		= getUrlPath();			// top menu를 위해
	var _fullUrlPath	= getFullUrlPath();		// left menu 선택을 위해 
	var _documentUrl	= getOriginUrl();		// #제거한 url
	//*********** 전역변수 선언 스코프 ***********//
	
	//url 경로 추출
	function getUrlPath()
	{
		var currentUrl = urlRemoveSharp(document.URL);
		currentUrl = currentUrl.replace(_domain, "");

		var urlArr = currentUrl.split("/");
		var retPath = "";

		for (var i=1; i < 3 ; i++)
		{
			if(urlArr[i] != "")
			{
				retPath += "/"+urlArr[i];
			}
		}

		return retPath;
	}


	//url 전체경로 추출
	function getFullUrlPath()
	{
		var currentUrl = document.URL;
		var urlPath = currentUrl.split("?");

		urlPath = urlPath[0];
		urlPath = urlRemoveSharp(urlPath);
		urlPath = urlPath.replace(_domain, "");

		return urlPath;
	}


	//document.URL 원본 추출
	function getOriginUrl()
	{
		var currentUrl = urlRemoveSharp(document.URL);
		return currentUrl;
	}
	
	//url에서 #제거
	function urlRemoveSharp(url)
	{
		var urlArr = url.split("?");
		urlArr[0] = urlArr[0].replace("#", "");
		urlArr = urlArr.join("?");

		return urlArr;
	}


</script>