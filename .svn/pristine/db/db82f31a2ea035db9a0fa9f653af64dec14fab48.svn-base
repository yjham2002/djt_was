<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:if test="${accessLevelCode ne 2 }">
	<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
	<c:import url="${properties.include_path}/leftMenu_adminBill.jsp"/>
</c:if>
<c:if test="${accessLevelCode eq 2 }">
	<c:import url="${properties.include_path}/header_admin.jsp"/>
	<c:import url="${properties.include_path}/leftMenu_adminBill.jsp"/>
</c:if>


<jsp:useBean id="toDay" class="java.util.Date" />

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
		
		$(".jCalcurate").click2(function(e){
			var uno = $(this).attr("uno");
			var fno = $(this).attr("fno");
			
			$.ajax({
				type : "POST" , 
				dataType : "json" , 
				url : "/NaeKkot/admin/setFranchiseCalcurate" , 
				data : {
					uno : uno , 
					fno : fno
				} , 
				success : function(data) {
					alert(data.returnMessage);
					if(data.returnCode == 1)
						location.reload();
				}
			});
			
		});
		
		$(".jExcel").click2(function(){
			location.href = "/NaeKkot/admin/getFranchiseCalcurateExcel";
		});

		$(".jConfirm").click2(function(){
			
			if($("input:checkbox[name=check_row]:checked").length == 0) {
				alert("정산할 가맹점을 선택해주세요.");
				return;
			}
			
			var fnos = new Array();
			var unos = new Array();
			$("input:checkbox[name=check_row]:checked").each(function(i){
				fnos[i] = $(this).val();
				unos[i] = $(this).attr("uno");
			});
			
			$.ajax({
				type : "POST" , 
				dataType : "json" , 
				url : "/NaeKkot/admin/setFranchiseCalcurateBulk" , 
				data : {
					fnos : fnos , 
					unos : unos
				} , 
				success : function(data) {
					alert(data.returnMessage);
					if(data.returnCode == 1)
						location.reload();
				}
			});
			
		});
	});
</script>
	
	<div id="Contents"  class="notice">
	    <h1>정산관리</h1>
		<h2 style="width:98%;">
			정산 관리 ><span> 가맹점별 정산</span>
		</h2>
		
		<div class = "data">
			<c:if test = "${accessLevelCode != 2 }">
			<div style="clear:both">
				<fieldset class="search" style="margin-top:20px;">
					<div class="jSearchArea" align="center">
						<label>
							<select class="" name="year">
								<option value = "">전체</option>
								<c:forEach var="i" begin="2016" end="2099" step="1">
									<option value = "${i }" ${i == param.year ? 'selected' : '' }>${i }</option>
								</c:forEach>
							</select>
							<span>년</span>
						</label>
						<label>
							<select class="" name="month" >
								<option value = "">전체</option>
								<c:forEach var="i" begin="1" end="12" step="1">
									<c:if test = "${i < 10 }">
										<option value = "0${i }" ${i == param.month ? 'selected' : '' }>0${i }</option>	
									</c:if>
									<c:if test = "${i >= 10 }">
										<option value = "${i }" ${i == param.month ? 'selected' : '' }>${i }</option>
									</c:if>
								</c:forEach>
							</select>
							<span>월</span>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>가맹점명</span>
							<input type = "text" name = "searchFranchise" value = "${param.searchFranchise }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>미정산</span>
							<select class="" name="isCalcurate" style = "width:120px;">
								<option value="">전체</option>
								<option value="N" ${param.isCalcurate eq 'N' ? 'selected' : '' }>미정산</option>
							</select>
						</label>				
					</div>
					
					<div class = "btngroupcenter">
						<input type="button" class="button medium white jSearch" value="검색">
					</div>
					
				</fieldset>
			</div>
			
			<table class = "datacList">
				<thead>
					<tr>
						<th width="18%">미정산된 총 수금액</th>
						<td width="15%"><fmt:formatNumber value="${data.noTotalCollect }" pattern="#,###.##"/></td>
						<th width="18%">총 정산액</th>
						<td width="15%"><fmt:formatNumber value="${data.totalCalcurate }" pattern="#,###.##"/></td>
						<th width="18%">총 수익수수료</th>
						<td width="15%"><fmt:formatNumber value="${data.totalCommission }" pattern="#,###.##"/></td>
					</tr>
					<tr>
						<th>미정산 총 입금액</th>
						<td><fmt:formatNumber value="${data.noTotalDeposit }" pattern="#,###.##"/></td>
						<th>총 보유 예치금</th>
						<td><fmt:formatNumber value="${data.totalBalance }" pattern="#,###.##"/></td>
						<th>총 공제액</th>
						<td><fmt:formatNumber value="${data.totalBite }" pattern="#,###.##"/></td>
					</tr>
				</thead>
			</table>
											
			<div class = "btngroupcenter" style="margin-top: 20px;">
				<input type="button" class="button bigrounded2 white jExcel" value="엑셀다운">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="button bigrounded2 white jConfirm" value="전체완료">
			</div>
			</c:if>
			
			<div class = "btngroupright">
				<select class="" name="">
					<option value="1">100개씩 보기</option>
				</select>
			</div>
			
			<table class = "datacList">
				<thead>
					<tr>
						<c:if test = "${accessLevelCode != 2 }">
						<th width="3%"><input type="checkbox" class="jCheck_All" name = "check_all"/></th>
						</c:if>
						<th width="5%">No</th>
						<c:if test = "${accessLevelCode != 2 }"><th width="8%">생성일</th></c:if>
						<c:if test = "${accessLevelCode == 2 }"><th width="8%">정산완료일</th></c:if>
						<th width="7%">가맹점명</th>
						<th width="7%">발주액</th>
						<th width="7%">수주액</th>
						<th width="7%">사용포인트</th>
						<th width="7%">총선택옵션<br/>(수주)</th>
						<th width="7%">총선택옵션<br/>(발주)</th>
						<th width="7%">인센티브율(%)</th>
						<th width="7%">수수료율(%)</th>
						<th width="7%">총 수익 수수료</th>
						<th width="7%">총 공제액</th>
						<th width="7%">세금계산서 발행금액<br/>(VAT포함)</th>
						<th width="7%">보유예치금</th>
						<th width="7%">정산액</th>
						<c:if test = "${accessLevelCode != 2 }">
						<th width="7%">전체완료</th>
						</c:if>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
					<c:set var="vnum" value="${data.pager.virtualNum}"/>
					<c:forEach var="row" items="${data.list }" varStatus="status">
						<tr>
							<c:if test = "${accessLevelCode != 2 }">
							<td><input type = "checkbox" class="jCheck_Each" name = "check_row" value = "${row.franchiseNumber}" uno = "${row.userNumber }" /></td>
							</c:if>
							<td>
								${status.index + 1}
							</td>
							<td><%-- <fmt:formatDate value="${row.regDate}" pattern="yyyy-MM-dd"/>--%> ${row.regDate }</td>
							<td>${row.franchiseName }</td>
							<td><fmt:formatNumber value="${row.collectPrice }" pattern="#,###.##원" /></td>
							<td><fmt:formatNumber value="${row.depositPrice }" pattern="#,###.##원" /></td>
							<td><fmt:formatNumber value="${row.usePoint }" pattern="#,###.##" /></td>
							<td><fmt:formatNumber value="${row.optionPrice }" pattern="#,###.##원" /></td>
							<td><fmt:formatNumber value="${row.orderOptionPrice }" pattern="#,###.##원" /></td>
							<td>${row.incentive }%</td>
							<td>${row.commission }%</td>
							<td><fmt:formatNumber value="${row.commissionPrice }" pattern="#,###.##원" /></td>
							<td><fmt:formatNumber value="${row.bite }" pattern="#,###.##원" /></td>
							<td></td>
							<td><fmt:formatNumber value="${row.balance }" pattern="#,###.##원" /></td>
							<td><fmt:formatNumber value="${row.collectPrice - row.depositPrice}" pattern="#,###.##원" /></td>
							<c:if test = "${accessLevelCode != 2 }">
							<td><input type="button" class="button searchsmall white jCalcurate" value="정산"  fno = "${row.franchiseNumber }" uno = "${row.userNumber }"/></td>
							</c:if>
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

