<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_appManage.jsp"/>


<script type = "text/javascript">

	$(document).ready(function(){
		
		//=====프레입웤 변수들==================================================================================//
		var FORM_TARGET_CLS_NM		= "DIV#Contents"	;		// 폼을 동적 wrap 할 타겟 ID이름
		var FORM_NAME				= "nf"		;		// 폼이름
		var FORM_METHOD				= "GET"		;		// 폼 메쏘드
		var FORM_USE_FILE			= false		;		// 파일폼 사용 여부
		var FORM_ACTION				= document.URL ; 

		var NEXT_CMD				= ""			;		// 다은 수행 할 cmd
		//=====================================================================================================//

		var jForm = $(FORM_TARGET_CLS_NM).mf({ "name" : FORM_NAME , "method" : FORM_METHOD , "action" : FORM_ACTION , "useFile" : FORM_USE_FILE }) ;
		
		// 검색 버튼 enter 허용
		$("input[type='text']").keyup(function(e){
			if (e.keyCode == 13) $(".jSearch").trigger("click");
		});
		
		$(".jSearch").click2(function(e){
			e.preventDefault();
			jForm.ci();
			jForm.submit();			
		});
		
		// checkbox 전체 선택 n 해제
		$(".jCheck_All").click2(function(){
			if(this.checked){
				$(".jCheck_Each").prop("checked", true);
			}
			else{
				$(".jCheck_Each").prop("checked", false);
			}
		});

		// checkbox 단일 선택시 전체 checkbox 상태 변화
		$(".jCheck_Each").click2(function(){
			if($("[name='gids[]']:checkbox:checked").length==$("[name='gids[]']").length){
				$(".jCheck_All").prop("checked", true);
			}else {
				$(".jCheck_All").prop("checked", false);
			}
		});
		
		// 선택된 항목들 삭제
		$(".jDelete").click2(function(){
			var jDel = $(".jCheck_Each:checkbox:checked").length;
			if(jDel <= 0){
				alert("선택된 항목이 없습니다.");
			}else {
				if(!confirm("삭제하시겠습니까?"))	return false;
				
				var pageType = '${param.pageType}';
				var boardNumbers = new Array();
				$(".jCheck_Each:checked").each(function(i){
					boardNumbers[i] = $(this).val();
				});

				$.ajax({
					url : rootPath + "/superAppManage/deleteAppManage",
					type : "POST",
					dataType : "json" ,
					data : {
						boardNumbers : boardNumbers,
						pageType : pageType
					},
					success : function(data){
						alert(data.returnMessage);
						if(data.returnCode == 1) {
							location.reload();
						}
					}
				});
			}
		});
		
		
		$(".jView").click2(function(){
			var number = $(this).attr("_no");
			var pageType = '${param.pageType}';
			location.href=rootPath+"/superAppManage/appManageView?pageType="+pageType+"&boardNumber="+number+"&rurl="+encryptHexStr(document.URL);
		});

		$(".jSubmit").click2(function(){
			var pageType = '${param.pageType}';
			location.href=rootPath+"/superAppManage/appManageView?pageType="+pageType+"&rurl="+encryptHexStr(document.URL);
		});
		
	});
	
</script>
	<div id="Contents"  class="notice">
		<input type="hidden" name="pageType" value="${param.pageType }" />
	    
	    <h1>
	    	<c:choose>
				<c:when test="${param.pageType eq 1 }">
					매거진[리스트]
				</c:when>
				<c:when test="${param.pageType eq 2 }">
					공지[리스트]
				</c:when>
				<c:when test="${param.pageType eq 3 }">
					이벤트[리스트]
				</c:when>
			</c:choose>
	    </h1>
		
		<h2 style="width:98%;">
		</h2>
		
		<div class = "data">
			<div style="clear:both">
			
				<fieldset class="search" style="margin-top:20px; text-align: center;">
					<div class="jSearchArea">
						<label>
							<span>제목</span>
							<input type = "text" name = "title" value = "${param.title }" style = "width:120px;"/>
						</label>
						<label>
							<span>내용</span>
							<input type = "text" name = "contents" value = "${param.contents }" style = "width:120px;"/>
						</label>
						<c:if test="${param.pageType==2 }">
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>메인노출</span>
							<select name="order" style="width: 120px;">
								<option value="0">전체</option>
								<option value="1" ${param.order eq 1 ? 'selected' : '' }>1</option>
								<option value="2" ${param.order eq 2 ? 'selected' : '' }>2</option>
								<option value="3" ${param.order eq 3 ? 'selected' : '' }>3</option>
								<option value="4" ${param.order eq 4 ? 'selected' : '' }>4</option>
								<option value="5" ${param.order eq 5 ? 'selected' : '' }>5</option>
							</select>
						</label>
						&nbsp;&nbsp;&nbsp;
						</c:if>
						<label>
							<input type="button" class="button medium white jSearch" value="검색">
						</label>				
					</div>
				</fieldset>
			</div>
			
			<div class = "jSearchArea btngroupright">
				<input type="button" class="button medium white jSubmit" value="등록" />&nbsp;&nbsp;&nbsp;
				<input type="button" class="button medium white jDelete" value="선택삭제" />&nbsp;&nbsp;&nbsp;
				<select class="" name="">
					<option value="1">100개씩 보기</option>
				</select>
			</div>
			 
			<table class = "datacList" style="margin-top: 15px;">
				<thead>
					<tr>
						<th><input type="checkbox" class="jCheck_All"/></th>
						<th>No</th>
						<th>등록일</th>
						<th>제목</th>
						<th>내용</th>
						<c:if test="${param.pageType eq 2 }">
							<th>메인 노출</th>
						</c:if>
						<th>상세보기</th>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
					<c:set var="vnum" value="${retData.pager.virtualNum}"/>
					<c:forEach var="row" items="${retData.list }" varStatus="status">
						<tr>
							<td><input type = "checkbox" class="jCheck_Each" name = "gids[]" value = "${row.boardNumber}"/></td>
							<td>
								${vnum - status.index}
							</td>
							<td><%-- <fmt:formatDate value="${row.regDate}" pattern="yyyy-MM-dd"/>--%> ${row.regDate }</td>
							<td>${row.title}</td>
							<td>${row.contents}</td>
							<c:if test="${param.pageType eq 2 }">
								<td>${row.ord eq 0 ? '안함' : row.ord}</td>
							</c:if>
							<td><input type="button" class="button medium white jView" value="보기" _no="${row.boardNumber }"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table> 

			<div class="pageNumber">
				<c:set var="pager" value="${retData.paging}"/>
				${pager }
			</div>

		</div>
	</div>

<c:import url="${properties.include_path}/footer_admin.jsp"/>