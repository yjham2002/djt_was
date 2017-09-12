<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <title>APPG</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="refresh" content="${represhSecond};url=${redirectUrl}">
	<link rel="stylesheet" type="text/css" href="${properties.resources_path}/css/basic.css" />
	</head>
<body>
	<div class="wrap">
		<article>
			<div class="content-area">
				<div class="main-content">
					<div class="content-sub-title" style="margin-top:35px;text-align:center;font-size:18px;font-weight:bold;">${message}</div>
				</div>
			</div>
		</article>
	</div>
</body>
</html>