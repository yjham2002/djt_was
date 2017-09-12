<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.appg.djTalk.common.bean.db.MenuRightBean"
	import="java.util.*"%>
<%
	List<String> topMenu = new ArrayList<String>();
	String accessLevelCode = (String)request.getAttribute("accessLevelCode");
	List<MenuRightBean> leftMenuList = (List)request.getAttribute("leftMenuList");
%>
		<div class="leftWrap">
			<ul class="sidemenu">
<%
				for(int i = 0; i < leftMenuList.size(); i++){
					MenuRightBean row = leftMenuList.get(i);
					if(row.getUseFlag().equalsIgnoreCase("N")){
						topMenu.add(row.getCodeText());
						if(row.getCodeAttribute().equalsIgnoreCase("기타관리")){
%>						
							<h1><%=row.getCodeText() %></h1>
							<%--
							<a href="${pageContext.request.contextPath}/admin/commonCode/groupCodeList">
							</a>
							 --%>
<%
						}
					}
					else{
						if(row.getCodeAttribute().equalsIgnoreCase("기타관리")){
%>
							<li>
								<a href="${pageContext.request.contextPath}<%=row.getCodeDescription()%>"><%=row.getCodeText() %></a>
							</li>
<%						
						}
					}
				}
%>
				<%--<li matchUrl="${pageContext.request.contextPath}/admin/commonCode/groupCodeList"></li> --%>
		

				<!-- 
				<li matchUrl="/admin/userManage/userList.php">
					<a href="/admin/userManage/userList.php?isApplyID=0">탈퇴회원 리스트</a>
				</li>
		 		-->
			</ul>
		
			<div class="left_blank"></div>
		
		</div>