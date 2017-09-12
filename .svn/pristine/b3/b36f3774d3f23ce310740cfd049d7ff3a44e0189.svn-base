<%@page import="com.appg.djTalk.common.bean.DataMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header_superAdmin.jsp"%>
<%@ include file="/WEB-INF/views/include/leftMenu_user.jsp"%>
<%
	DataMap retVal = (DataMap)request.getAttribute("data");

	DataMap pager = retVal.getDataMap("pager");
	
	List<DataMap> list = retVal.getDataMapList("list");
	
	int vnum = 0;
	if(pager != null) {
		vnum = pager.getInt("virtualNum");
	}
	
	String isApplyID = request.getParameter("isApplyID");
%>
<script type = "text/javascript">
	$(document).ready(function(){
		
		$("[name=check_all]").click2(function(e){
			e.stopPropagation();
			if(this.checked)
				$("[name=check_row]").prop("checked", true);
			else
				$("[name=check_row]").prop("checked", false);
		});
		
		$("[name=check_row]").click2(function(e){
			e.stopPropagation();
			if($("input:checkbox[name=check_row]").length == $("input:checkbox[name=check_row]:checked").length) {
				$("[name=check_all]").prop("checked", true);
			} else {
				$("[name=check_all]").prop("checked", false);
			}
		});

	});
</script>

	<div id="Contents"  class="notice">
	    <h1>회원관리</h1>
		<h2 style="width:98%;">
			회원관리 ><span> 탈퇴회원 관리</span>
		</h2>
		
		<div class = "data">
			<div style="clear:both">
			
				<fieldset class="search" style="margin-top:20px;">
				
					<div class = "jSearchArea" style = "margin-top:10px;">
						<label>
							<select name = "searchType">
								<option value = "">전체</option>
								<option value = "name">아이디</option>
								<option value = "passport">여권이름</option>
							</select>
						</label>
						<input type = "text" style = "width:180px;" name = "searchText" value = ""/>
					</div>
					
					<div class = "jSearchArea btngroupcenter">
						<a href="#" class="button bigrounded2 blue jSearch" >검색 </a>
					</div>
				</fieldset>
			</div>
			
			<div class = "jSearchArea btngroupright">
				<a href="#" class="button bigrounded2 blue jDel" >강제탈퇴</a>
			</div>
			
			<table class = "datacList">
				<thead>
					<tr>
						<th><input type = "checkbox" name = "check_all" /></th>
						<th>No</th>
						<th>ID</th>
						<th>여권영문이름</th>
						<th>탈퇴일</th>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
<%
				for(int i = 0; i < list.size(); i++) {
					DataMap row = list.get(i);
%>
				<tr class = "jView" _no = "<%=row.getInt("userNumber")%>">
					<td><input type = "checkbox" name = "check_row" value = "<%=row.getInt("userNumber")%>"/></td>
					<td><%=vnum-- %></td>
					<td><%=row.getString("userID") %></td>
					<td><%=row.getString("passportName") %></td>
					<td><%=row.getString("withdrawDate") %></td>
				</tr>
<%
				} 
%>
				</tbody>
			</table> 
			
			<div class="pageNumber">
				<%
				if( pager.getInt("isPrevBlock") > 0) 
				{
					out.print("<a href='#' ><img class='jPage' page='1' src='/admin/inc/images/paging_first.gif' alt='처음페이지'></a>");
					out.print("<a href='#' ><img class='jPage' page='" + (pager.getInt("startBlock") - 1) + "' src='/admin/inc/images/paging_prev.gif' alt='이전'></a>");
				}
			
				for(int i = pager.getInt("startBlock"); i <= pager.getInt("endBlock")  ; i++ )
				{
					if( pager.getInt("page") == i )
						out.print("<a href='#' ><strong>"+i+"</strong></a>");
					else
						out.print("<a href='#' class='jPage' page='"+i+"'>"+i+"</a>") ;
				}
			
				if( pager.getInt("isNextBlock") > 0)
				{
					out.print("<a href='#' ><img class='jPage' page='" + (pager.getInt("endBlock") + 1) + "' src='/admin/inc/images/paging_next.gif' alt='다음'></a>") ;
					out.print("<a href='#' ><img class='jPage' page='" + (pager.getInt("endPage")) + "' src='/admin/inc/images/paging_last.gif' alt='마지막페이지'></a>");
				}
				%>
			</div>
			

		</div>
	</div>

<%@ include file="/WEB-INF/views/include/footer_admin.jsp"%>