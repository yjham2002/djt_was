<%@ page import="com.appg.djTalk.common.constants.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.appg.djTalk.common.bean.db.CommonCodeBean"
	import="java.util.*"
	%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--JSTL
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 --%>
<%
	List<CommonCodeBean> list = (List)request.getAttribute("groupCodeList");
%>

<c:import url="/WEB-INF/views/include/header_admin.jsp"></c:import>
<c:import url="/WEB-INF/views/include/leftMenu_account.jsp"></c:import>

<script type="text/javascript">

	var FH = 
	{
		pathRoot : "${pageContext.request.contextPath}/admin/commonCode/",
		qs : "${qs}",
		obj : 
		{
			data : {},
			handle : {}
		},
		init : function()
		{
			FH.objInit();
			FH.objSetEvent();
		},
		objInit : function()
		{
		
		},
		objSetEvent : function()
		{
			$("#btnAdd").click(function(evt){
				<%-- input field -> hidden filed로 할당. --%>
				
				$("#actionType").val("addGroup");
				$("#groupCode").val($($("[id=ifGroupCode]")[0]).val());
				$("#codeText").val($($("[id=ifCodeText]")[0]).val());
				$("#codeDescription").val($($("[id=ifCodeDescription]")[0]).val());
				//$("#refCode").val($($("[id=ifRefCode]")[0]).val());
				FH.formSubmit("groupCodeAction");
			});
			
			$("[id=btnUpt]").each(function(idx){
				$(this).click(function(evt){
					var _index = parseInt($(this).attr("_index"));
					
					$("#actionType").val("modifyGroup");
					
					$("#groupCode").val($($("[id=ifGroupCode]")[_index + 1]).val());
					$("#codeText").val($($("[id=ifCodeText]")[_index + 1]).val());
					$("#codeDescription").val($($("[id=ifCodeDescription]")[_index + 1]).val());
					//$("#refCode").val($($("[id=ifRefCode]")[_index + 1]).val());
					
					FH.formSubmit("groupCodeAction");
				});
			});
			
			$("[id=btnDel]").each(function(idx){
				$(this).click(function(evt){
					if(confirm(MESSAGE_CONST.CONFIRM_REMOVE))
					{
						var _index = parseInt($(this).attr("_index"));
					
						<%-- _index 값을 통해 삭제하려는 row값의 groupCode를 전달한다. --%>
						$("#actionType").val("removeGroup");
						$("#groupCode").val($($("[id=ifGroupCode]")[_index + 1]).val());
						
						FH.formSubmit("groupCodeAction");
					}
				});
			});
		},
		formSubmit : function(pAction)
		{
			if(!checkEmpty($("#groupCode"),"Group code")) return;
			$("#_form").attr("action",pAction);
			$("#_form").submit();
		}
	};
	
	$(document).ready(function(){
		FH.init();
	});

</script>
<form id="_form" name="_form" enctype="multipart/form-data" method="POST">
	<input type="hidden" name="actionType" id="actionType" value="${param.actionType}"/>
	<input type="hidden" value="" id="groupCode" name="groupCode"/>
	<input type="hidden" value="" id="codeText" name="codeText"/>
	<input type="hidden" value="" id="codeDescription" name="codeDescription"/>
	<input type="hidden" value="" id="refCode" name="refCode"/>
</form>
	<div id="Contents"  class="notice">
	    <h1>권한관리</h1>
		<h2 style="width:98%;">
			권한관리 > <span>공통코드</span>
		</h2>		
		
		<div class="data">
	        <h3>
				<span>공통코드</span>
			</h3>
				<table class="datacList" style="width:100%;">
					<thead>
						<tr>
							<th width="25%">그룹코드</th>
							<th width="25%">한글코드</th>
							<th width="25%">디스크립션</th>
							<th width="25%">설정</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td height="28" align="center"><input type="text" value="" id="ifGroupCode" name="ifGroupCode" style="width: 60px;" maxlength=4/></td>
							<td height="28" align="center"><input type="text" value="" id="ifCodeText" name="ifCodeText" style="width: 230px;"/></td>
							<td height="28" align="center"><input type="text" value="" id="ifCodeDescription" name="ifCodeDescription" style="width: 230px;"/></td>
							<td height="28" align="center"><button type="button" id="btnAdd">등록</button></td>
						</tr>
<%
						for(int i = 0; i < list.size(); i++) {
							CommonCodeBean row = list.get(i);
%>
							<tr>
								<td align="center">
									<a href="./memberCodeList?groupCode=<%=row.getGroupCode()%>"><%=row.getGroupCode() %></a>
									<input type="hidden" value="<%=row.getGroupCode() %>" id="ifGroupCode" name="ifGroupCode" style="width: 60px;" maxlength=4/>
								</td>
								<td align="center"><input type="text" value="<%=row.getCodeText() %>" id="ifCodeText" name="ifCodeText" style="width: 230px;"/></td>
								<td align="center"><input type="text" value="<%=row.getCodeDescription() %>" id="ifCodeDescription" name="ifCodeDescription" style="width: 230px;"/></td>
								<td align="center">
									<button type="button" id="btnUpt" _index="<%=i%>">수정</button>
									<button type="button" id="btnDel" _index="<%=i%>">삭제</button>
								</td>
							</tr>
<%
						}
%>					
					</tbody>
					
				</table>			
		</div>
	</div>

<!-- 
	<form name="_form" id="_form" method="post" >
		<input type="hidden" name="actionType" id="actionType" value="${param.actionType}"/>
		<input type="hidden" value="" id="groupCode" name="groupCode"/>
		<input type="hidden" value="" id="codeText" name="codeText"/>
		<input type="hidden" value="" id="codeDescription" name="codeDescription"/>
		<input type="hidden" value="" id="refCode" name="refCode"/>
	</form>
	<table style="width:100%;border-spacing:0px;border-collapse:collapse;" class="table1">
		<tr>
			<td class="cell1 subj2">권한관리 &gt; 공통코드 &gt; 그룹코드</td>
		</tr>
		<tr>
			<td>
				<table style="width:100%;border-spacing:0px;border-collapse:collapse;">
					<thead>
						<tr>
							<th class="cell1 subj2" align="center" height="28">Group Code</th>
							<th class="cell1 subj2" align="center" >Code Text</th>
							<th class="cell1 subj2" align="center" >Description</th>
							<th class="cell1 subj2" align="center" >Reference Code</th>
							<th class="cell1 subj2" align="center" >Function</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="cell1" height="28" align="center"><input type="text" value="" id="ifGroupCode" name="ifGroupCode" style="width: 60px;" maxlength=4/></td>
							<td class="cell1" height="28" align="center"><input type="text" value="" id="ifCodeText" name="ifCodeText" style="width: 230px;"/></td>
							<td class="cell1" height="28" align="center"><input type="text" value="" id="ifCodeDescription" name="ifCodeDescription" style="width: 230px;"/></td>
							<td class="cell1" height="28" align="center"><input type="text" value="" id="ifRefCode" name="ifRefCode" style="width: 80px;" maxlength=8/></td>
							<td class="cell1" height="28" align="center"><button class="button-style-first" type="button" id="btnAdd">Add</button></td>
						</tr>
						<c:forEach var="row"  items="${list}"  varStatus="status">
							<tr>
								<td class="cell1" align="center">
									<a href="./memberCodeList?groupCode=${row.groupCode}">${row.groupCode}</a>
									<input type="hidden" value="${row.groupCode}" id="ifGroupCode" name="ifGroupCode" style="width: 60px;" maxlength=4/>
								</td>
								<td class="cell1" align="center"><input type="text" value="${row.codeText}" id="ifCodeText" name="ifCodeText" style="width: 230px;"/></td>
								<td class="cell1" align="center"><input type="text" value="${row.codeDescription}" id="ifCodeDescription" name="ifCodeDescription" style="width: 230px;"/></td>
								<td class="cell1" align="center"><input type="text" value="${row.refCode}" id="ifRefCode" name="ifRefCode" style="width: 80px;" maxlength=8/></td>
								<td class="cell1" align="center">
									<button class="button-style-first" type="button" id="btnUpt" _index="${status.index}">Upt</button>
									<button class="button-style-first" type="button" id="btnDel" _index="${status.index}">Del</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
-->
<c:import url="/WEB-INF/views/include/footer_admin.jsp"></c:import>
						