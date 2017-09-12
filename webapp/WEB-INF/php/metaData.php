<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/admin/inc/css/base.css">
<link rel="stylesheet" type="text/css" href="/admin/inc/css/style.css">
<link rel="stylesheet" type="text/css" href="/admin/inc/css/layout.css">
<link rel="stylesheet" type="text/css" href="/admin/inc/css/common.css">
<link rel="stylesheet" type="text/css" href="/admin/inc/css/btn.css">

<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript" src="/common/js/jsMap.js"></script>
<script type="text/javascript" src="/common/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" language="javascript" src="/common/js/jquery.cookie.js"></script>

<link rel="stylesheet" type="text/css" href="/common/js/jquery.timepicker.css">
<script type="text/javascript" language="javascript" src="/common/js/jquery.timepicker.min.js"></script>


<script type="text/javascript" src="/common/js/jquery.image.js"></script>
<script type="text/javascript" src="/common/js/jquery.validate.min.js"></script>

<!-- 리치 js -->
<script type="text/javascript" src="/common/js/jquery_rich/RichBaseExtends.js"></script>
<script type="text/javascript" src="/common/js/jquery.DOMWindow.js"></script>
<script type="text/javascript" src="/common/js/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="/common/js/imgPreview.js"></script>

<!-- datepicker -->
<script type="text/javascript" src="/common/js/click_cal.js"></script>

<!-- 이미지 업로드 -->
<script src="/admin/inc/fileUpload/fileUploadJS.js"></script>

<script>
	//*********** 전역변수 선언 스코프 ***********//
	var _domain			= "<?=$webRoot?>";
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