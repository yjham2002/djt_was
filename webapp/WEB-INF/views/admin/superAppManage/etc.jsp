<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_appManage.jsp"/>

<c:set var="categoryLength" value="${fn:length(retData.categoryList) }" />

<script>
	
	var initNumber = "${categoryLength+300}";

	$(document).ready(function(){
		initFileUpload(200);
		
		for(var i=0; i<"${categoryLength}";  i++){
			initFileUpload(300+i);
		}
		
		for(var i=0; i<5; i++){
			initFileUpload(100+i);
		}
		
		$(".jBack").click2(function(){
			location.href="";
		});
		
		$(".jCategory").click2(function(){
			
			$(".jCategoryName").each(function(){
				if($(this).val() == ""){
					alert("제품선택명을 입력해 주세요.");
					$(this).focus();
					return false;
				}
			})

			$(".jCategoryTitle").each(function(){
				if($(this).val() == ""){
					alert("제품선택 문구를 입력해 주세요.");
					$(this).focus();
					return false;
				}
			});
			
			$("#jCategory").ajaxSubmit({
				url : rootPath + "/superAppManage/saveEtc",
				type : "post",
				forceSync : true,
				dataType : "json",
				success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == "1") {
						location.reload();
					}
				}
			});
			
		});
		 
		$(".jTutorial").click2(function(){
			
			$("#jTutorial").ajaxSubmit({
				url : rootPath + "/superAppManage/saveEtc",
				type : "post",
				forceSync : true,
				dataType : "json",
				success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == "1") {
						location.reload();
					}
				}
			});
			
		});
		 
		$(".jPopup").click2(function(){
			
			$("#jPopup").ajaxSubmit({
				url : rootPath + "/superAppManage/saveEtc",
				type : "post",
				forceSync : true,
				dataType : "json",
				success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == "1") {
						location.reload();
					}
				}
			});
			
		});
		 
		$(".jDiscount").click2(function(){
			
			$("#jDiscount").ajaxSubmit({
				url : rootPath + "/superAppManage/saveEtc",
				type : "post",
				forceSync : true,
				dataType : "json",
				success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == "1") {
						location.reload();
					}
				}
			});
			
		});
		
		
		$(".jAddCategory").click2(function(){
			
			var filePath = "${properties.img_path}";
			var fileNumber = initNumber-300;
			
			var html = "";
			if(fileNumber % 2 == 0){
				html += '<tr>';
			}
			html += '<th class="b" width="15%"><input type="text" class="jCategoryName" name="categoryNames[]" value="" style="width: 95%">';
            html += '</th><td><input type="text" class="jCategoryTitle" name="categoryTitles[]" value="" style="width: 95%"></td><td>';
			html += '<input type="hidden" name="categoryNumbers[]" value="${row.categoryNumber == null ? 0 : row.categoryNumber}"/>';
            html += '<div id="jFileUploadArea'+initNumber+'">';
			html += '<img id="btnFileUpload'+initNumber+'" src="/NaeKkot/resources/fileUpload/bt_attach.gif" alt="Attach a file" />';
			html += '<input type="hidden" id="uploaded_files'+fileNumber+'" name="uploaded_images[]" value="" title="Upload File" />';
			html += '<input type="file" id="files'+initNumber+'"	name="images['+fileNumber+']" title="Upload File" style="display: none;" />';
			html += '<span id="divPreviewFile'+initNumber+'" style="">';
			html += '<div id="priviewFileScale'+initNumber+'" style="position: absolute; background-image: url('+filePath+'); width: 300px; height: 200px; background-repeat: no-repeat; background-size: contain; background-position: center center; display: none;"></div>';
			html += '<img class="imgSample" src="" width="25" height="21"	id="previewFile'+initNumber+'" />'; 
			html += '<img onclick="javascript:previewFileDelete('+ initNumber +');" src="/NaeKkot/resources/fileUpload/btn_x.gif" style="cursor: hand;" id="previewDelete'+initNumber+'" />';
			html += '</span><span id="divFileResult'+initNumber+'" class="validation4"></span></div></<td>';			

			if(fileNumber % 2 == 0){
				$(this).siblings("table").append(html);
			} else {
				$(this).siblings("table").find("td:last").after(html);
			}
			
			previewFileBind(initNumber, "filePath", filePath);			
			initFileUpload(initNumber++);
			
			
			
		
		});
	});

</script>

<style>
	.data h3{margin-top: 40px;}
</style>

<div id="Contents"  class="notice">

	<h1>기타 설정</h1>
	<h2 style="width:98%;">
		앱관리 > <span>기타 설정</span>
	</h2>


    <div class="data" style="width:100%;">
    <form id="jDiscount" method="post" enctype="multipart/form-data">
		<h3>할인률</h3>
		<input type="hidden" name="etcType" value="1">
        <table class="datav" style="width:100%; ">
            <tr>
                <th class="b" width="15%">기업포인트</th>
                <td width="35%"><input type="text" class="" name="companyDiscountRate" value="${retData.discountRate.companyDiscountRate }" style="width: 85%; text-align: right;"> % </td>
                <th class="b" width="15%">사용자포인트</th>
                <td width="35%"><input type="text" class="" name="userDiscountRate" value="${ retData.discountRate.userDiscountRate }" style="width: 85%; text-align: right;"> % </td>
            </tr>
        </table>
	</form>
	
        <div class="btngroupcenter">
			<input type="button" class="button medium white jDiscount" value="할인율 저장" />
		</div>
	        
		<form id="jCategory" method="post" enctype="multipart/form-data">
			<h3>제품선택문구</h3>
			<input type="button" class="button medium white jAddCategory" value="추가">
            <input type="hidden" name="etcType" value="4">
	        <table class="datav" style="width:100%; ">
	        	<c:forEach var="row" items="${retData.categoryList }" varStatus="status">
	            	<c:if test="${row.ord % 2 == 0 }">
			            <tr>
	            	</c:if>
		                <th class="b" width="15%">
		                	<input type="text" class="jCategoryName" name="categoryNames[]" value="${row.categoryName }" readonly="readonly" style="border: none;" />
		                </th>
		                <td width="20%">
		                	<input type="text" class="jCategoryTitle" name="categoryTitles[]" value="${row.categoryTitle }" style="width: 95%" />
		                </td>
		                <td width="15%">
		                	<input type="hidden" name="categoryNumbers[]" value="${row.categoryNumber == null ? 0 : row.categoryNumber}"/>
		                	<c:import url="${properties.include_path}/fileUpload.jsp">
								<c:param name="fileIndex" value="${300 + status.index }" />
								<c:param name="fileName" value="images[${status.index}]" />
								<c:param name="filePath" value="${row.categoryImg }" />
								<c:param name="includePath" value="${properties.img_path}" />
							</c:import>
		                </td>
	        	</c:forEach>
	        </table>
        </form>

        <div class="btngroupcenter">
			<input type="button" class="button medium white jCategory" value="제품선택문구 저장" />
		</div>
	        
		<form id="jTutorial" method="post" enctype="multipart/form-data">
	        <h3>튜토리얼</h3>
	        <input type="hidden" name="etcType" value="2">
	        <table class="datav" style="width:100%;">
	        	<c:forEach var="i" begin="1" end="4" step="1">
		        	<tr>
		                <th class="b" width="15%">${i }번째</th>
		                <td>
							<c:import url="${properties.include_path}/fileUpload.jsp">
								<c:param name="fileIndex" value="${100+i }" />
								<c:param name="fileName" value="images[${i}]" />
								<c:param name="filePath" value="${retData.tutorialList[i-1].img }" />
								<c:param name="includePath" value="${properties.img_path}" />
							</c:import>
						</td>
		            </tr>
	        	</c:forEach>
	        </table>
        </form>
        <div class="btngroupcenter">
			<input type="button" class="button medium white jTutorial" value="튜토리얼 저장" />
		</div>
	        
		<form id="jPopup" method="post" enctype="multipart/form-data">
	        <h3>공지사항 팝업</h3>
	        <input type="hidden" name="etcType" value="3">
	        <table class="datav" style="width:100%;">
	            <tr>
	                <th class="b" width="15%">팝업이미지</th>
	                <td>
						<c:import url="${properties.include_path}/fileUpload.jsp">
							<c:param name="fileIndex" value="200" />
							<c:param name="fileName" value="images[]" />
							<c:param name="filePath" value="${retData.popup.img }" />
							<c:param name="includePath" value="${properties.img_path}" />
						</c:import>
					</td>
	            </tr>
	        </table>
        </form>
        
		<div class="btngroupcenter">
			<input type="button" class="button medium white jPopup" value="팝업이미지 저장" />
		</div>
    </div>

</div>






<c:import url="${properties.include_path}/footer_admin.jsp"/>