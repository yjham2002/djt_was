<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_user.jsp"/>

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
			if($("[name='userNumbers[]']:checkbox:checked").length==$("[name='userNumbers[]']").length){
				$(".jCheck_All").prop("checked", true);
			}else {
				$(".jCheck_All").prop("checked", false);
			}
		});
		
		$(".jView").click2(function(e){
			e.preventDefault();
			var no = $(this).attr("_no");
			
			location.href = rootPath + "/superUser/userView?userNumber=" + no+"&rurl="+encryptHexStr(document.URL);
			
		});
		
		$(".jPage").click2(function(e){
			e.preventDefault();
			jForm.ci();
			jForm.mi("cpage", $(this).attr("page"));
			jForm.submit();
		});
		
		$(".jSearch").click2(function(){
			jForm.ci();
			jForm.submit();
		});
		
		$(".jWithdraw").click2(function(){
			
			var userNumber = $(this).attr("_no");
			var isApplyID = $(this).attr("isApplyID");
			
			$.ajax({
				url : rootPath + "/setUserIsApplyID",
				type : "POST",
				dataType : "json",
				data : {
					userNumber : userNumber,
					isApplyID : isApplyID
				}, success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1){
						location.reload();
					}
				}
				
			});
			
		});
		
		
		$(".jPush").click2(function(){
			var pushType = $(this).attr("pushType");
			$("[name='type']").val(pushType);
			
			
			if(pushType == 1 && $("input:checkbox[name='userNumbers[]']:checked").length == 0) {
				alert("푸시 발송 대상을 선택해주세요.");
				return;
			}
			
			$.openDOMWindow({
	           width: 800,
	           height: 150,
	           windowSourceID: '#divPush'
	       });
		});
		
		$(".jExit").click2(function(){
			$("#divPush").closeDOMWindow();
		});

		$(".jSend").click2(function(){
			var type = $("[name='type']").val();	//type - 1:선택 2:전체
			var message = $("[name='message']").val();
			
			if(!confirm("푸쉬를 전송 하시겠습니까?"))
				return false;

			var userNumbers = new Array() ;

			if(type == 1){
				$("[name='userNumbers[]']:checked").each(function(){
					userNumbers.push($(this).val());
				});
			}
		
			$.ajax({
				url : "/NaeKkot/admin/sendPush",
				type : "POST",
				dataType : "json",
				data : {
					type : type,
					userNumbers : userNumbers,
					message : message,
					listType : 2
				}
				, success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1){
						location.reload();
					}
				}
			});
			
		});
		
		

	});
</script>
	<div id="Contents"  class="notice">
	    <h1>사용자 회원</h1>
		<h2 style="width:98%;">
			사용자 회원 ><span> 회원 목록</span>
		</h2>
		
		<div class = "data">
			<div style="clear:both">
			
				<fieldset class="search" style="margin-top:20px;">
					<div class="jSearchArea">
						<label>
							<span>이름/회사명</span>
							<input type = "text" name = "searchName" value = "${param.searchName }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>연락처</span>
							<input type = "text" name = "searchTel" value = "${param.searchTel }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>이메일</span>
							<input type = "text" name = "searchEmail" value = "${param.searchEmail }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>사업자등록번호</span>
							<input type = "text" name = "searchCorp" value = "${param.searchCorp }" style = "width:120px;"/>
						</label>				
					</div>
					
					<div class = "jSearchArea" style = "margin-top:10px;">
						<label>
							<span>담당자 이름</span>
							<input type = "text" style = "width:120px;" name = "searchPerson" value = "${param.searchCorp }"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>탈퇴상태</span>
							<select name = "isApplyID" style = "width:120px;">
								<option value = "">전체</option>
								<option value = "-1" ${param.isApplyID == -1 ? 'selected' : '' }>탈퇴</option>
								<option value = "1" ${param.isApplyID == 1 ? 'selected' : ''}>탈퇴해제</option>
							</select>
						</label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<label>
							<span>회원구분</span>
							<select name = "userType" style = "width:120px;">
								<option value = "">전체</option>
								<option value = "1" ${param.userType == 1 ? 'selected' : '' }>개인</option>
								<option value = "2" ${param.userType == 2 ? 'selected' : '' }>기업</option>
							</select>							
						</label>
					</div>
					
					<div class = "jSearchArea btngroupcenter">
						<input type="button" class="button medium white jSearch" value="검색">
					</div>
				</fieldset>
			</div>
			
			<div class = "jUserCount btngroupleft">
			<c:forEach var="row" items="${retData.userCount }">
				<span style="font-size: 18px; margin-left: 15px;">총회원(${row.userTotalCount })명 / 탈퇴(${row.withdrawCount })명</span>
			</c:forEach>
			
			</div>
			<div class = "jSearchArea btngroupright">
				<input type="button" class="button bigrounded2 white jExcel" value="  엑셀  ">
				<input type="button" class="button bigrounded2 white jPush" value="선택푸쉬" pushType="1">
				<input type="button" class="button bigrounded2 white jPush" value="전체푸쉬" pushType="2">
			</div>
			
			<table class = "datacList">
				<thead>
					<tr>
						<th><input type="checkbox" class="jCheck_All"/></th>
						<th>No</th>
						<th>가입일</th>
						<th>구분</th>
						<th>이름/회사명</th>
						<th>연락처</th>
						<th>이메일</th>
						<th>담당자이름</th>
						<th>사업자등록번호</th>
						<th>상세보기</th>
						<th>탈퇴</th>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
					<c:set var="vnum" value="${retData.pager.virtualNum}"/>
					<c:forEach var="row" items="${retData.list }" varStatus="status">
						<tr>
							<td><input type = "checkbox" class="jCheck_Each" name = "userNumbers[]" value = "${row.userNumber}"/></td>
							<td>
								${vnum - status.index}
							</td>
							<td><%-- <fmt:formatDate value="${row.regDate}" pattern="yyyy-MM-dd"/>--%> ${row.regDate }</td>
							<td>${row.userType == 1 ? '일반' : '기업'}</td>
							<td>${row.userName }</td>
							<td>${row.userTel }</td>
							<td>${row.userID }</td>
							<td>${row.personName }</td>
							<td>${row.corporate}</td>
							<td><input type="button" class="button searchsmall white jView" value="보기"  _no = "${row.userNumber }"/></td>
							<td><input type="button" class="button searchsmall white jWithdraw" value="${row.isApplyID == 1 ? '탈퇴' : '탈퇴해제' }"  _no = "${row.userNumber }" isApplyID="${row.isApplyID }" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table> 

			<div class="pageNumber">
				<c:set var="pager" value="${retData.paging}"/>
				${pager }
			</div>
		</div>
		<div id="divPush" style="display:none;">
			<input type="hidden" name="type" value = "" />
			<table width="100%" cellspacing="0" cellpadding="0" class="write_type1" style="margin-top: 10px;">
				<thead>
					<tr>
						<th colspan="3">
							푸쉬 전송<br/><br/>
						</th>
					</tr>
					<tr align="center" >
						<th class="b jTitle" rowspan="2" width="20%" style="text-align:center;">내용</th>
						<td width="80%">
							<textarea name="message" style="resize: none; width: 80%" ></textarea>
						</td>
					</tr>
					<tr align="center">
						<td>
							<br>
							<input type="button" id="btnB" class="button medium white jSend" value="전송" style="width: 150px;" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" id="btnB" class="button medium white jExit" value="취소" style="width: 150px;" />
						</td>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
<c:import url="${properties.include_path}/footer_admin.jsp"/>

