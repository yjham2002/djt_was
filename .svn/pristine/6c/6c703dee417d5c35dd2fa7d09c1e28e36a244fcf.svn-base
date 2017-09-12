<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${accessLevelCode ne 2 }">
	<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
	<c:import url="${properties.include_path}/leftMenu_user.jsp"/>
</c:if>
<c:if test="${accessLevelCode eq 2 }">
	<c:import url="${properties.include_path}/header_admin.jsp"/>
	<c:import url="${properties.include_path}/leftMenu_admin.jsp"/>
</c:if>

<c:set var="data" value="${retData.data }" />
<c:set var="optionLength" value="${fn:length(retData.appOptionList) }" />

<script>
	var obj = new Array();
	var _rurl = "${rurl}";
	var initNumber = "${optionLength + 102}";
	
	obj = {
			fn_init : function(){
				
				$(".datepicker").datepicker({
					dateFormat : 'yy-mm-dd'
				});
				
				obj.fn_setGugun();
			},
			
			fn_setGugun : function(){
				var no = $("[name=sido] option:selected").val();

				$.ajax({
					url : rootPath + "/superUser/getGugunList",
					type : "POST" , 
					dataType : "json" , 
					data : {
						sidoNumber : no
					} , 
					success : function(data){
						$("[name=gugun]").empty();
						var html = "<option value = ''>구·군을 선택해주세요.</option>";
						for(var i = 0; i < data.entity.length; i++){
							var selected = "";
							if(data.entity[i].gugunNumber == "${data.gugun}"){
								selected = "selected";
							}
							html += "<option value = '"+data.entity[i].gugunNumber+"' "+selected+">"+data.entity[i].description+"</option>";
						}
						
						$("[name=gugun]").html(html);
					}
				});
				
			}
	}
	
	
	$(document).ready(function(){
		obj.fn_init();
		

		for(var i=0; i<"${optionLength}";  i++){
			initFileUpload(102+i);
		}

		if(getCookie("isSuperAdmin") != "1" && "${accessLevelCode}" == 2) {	//일반관리자일때
			$("input[type='text']").prop("disabled", true);
			$("input[type='radio']").prop("disabled", true);
			$("input[type='checkbox']").prop("disabled", true);
			$("select").prop("disabled", true);

			$("input[class='jOptionPrice']").prop("disabled", false);
			$("input[class='jOptionName']").prop("disabled", false);
			
			$("#previewDelete200").attr("onclick", "");
			$("#previewDelete201").attr("onclick", "");
		} else {
			initFileUpload(200);
			initFileUpload(201);
		}
		
		$(".jBack").click2(function(){
			location.href = _rurl;
		});
		
		$(".jAddOption").click2(function(){
			
			var filePath = "${properties.img_path}";
			var fileNumber = initNumber - 100;
			
			var html = "";
			html += '<tr><th>명칭</th><td><input type="text" class="jOptionName" name="optionNames[]" value="" style="width: 95%"></td>';
			html += '<th>가격</th><td><input type="text" class="jOptionPrice" name="optionPrices[]" value="" style="width: 95%"></td>';
			html += '<th>옵션 이미지</th><td>';	
			html += '<input type="hidden" name="optionNumbers[]" value="${option.optionNumber == null ? 0 : option.optionNumber}"/>';
			html += '<div id="jFileUploadArea'+initNumber+'">';
			html += '<img id="btnFileUpload'+initNumber+'" src="/NaeKkot/resources/fileUpload/bt_attach.gif" alt="Attach a file" />';
			html += '<input type="hidden" id="uploaded_files'+fileNumber+'" name="uploaded_images['+fileNumber+']" value="" title="Upload File" />';
			html += '<input type="file" id="files'+initNumber+'"	name="images['+fileNumber+']" title="Upload File" style="display: none;" />';
			html += '<span id="divPreviewFile'+initNumber+'" style="">';
			html += '<div id="priviewFileScale'+initNumber+'" style="position: absolute; background-image: url(); width: 300px; height: 200px; background-repeat: no-repeat; background-size: contain; background-position: center center; display: none;"></div>';
			html += '<img class="imgSample" src="" width="25" height="21"	id="previewFile'+initNumber+'" />'; 
			html += '<img onclick="javascript:previewFileDelete('+ initNumber +');" src="/NaeKkot/resources/fileUpload/btn_x.gif" style="cursor: hand;" id="previewDelete'+initNumber+'" />';
			html += '</span><span id="divFileResult'+initNumber+'" class="validation4"></span></div></<td>';			
			html += '<td><input type="button" class="button medium white jDeleteOption" value="삭제" _no="${option.optionNumber }"></td></tr>';
			
			$(this).parent().parent().before(html);
			previewFileBind(initNumber, "filePath", filePath);			
			initFileUpload(initNumber++);
			
		});
		
		$(document).on("click", ".jDeleteOption", function(){

			$(this).closest("tr").remove();
			
		});
		
		$(".jSave").click2(function(e){
			e.preventDefault();
			
			var validation = true;
			var isEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
			var isNumber = /^[0-9]*$/;
			var isPhone = /^\d{3}-\d{3,4}-\d{4}$/;
			
			if($("[name='userID']").val() == "") {
				alert("아이디를 입력해 주세요");
				$("[name='userID']").focus();
				return ;
			}
			if(_rurl != "") {
				if($("[name='userPWD']").val() == "") {
					alert("비밀번호를 입력해 주세요");
					$("[name='userPWD']").focus();
					return ;
				}
			}
			if($("[name='franchiseName']").val() == "") {
				alert("가맹점명을 입력해 주세요");
				$("[name='franchiseName']").focus();
				return ;
			}
			$(".jOptionName").each(function(){
				if($(this).val() == ''){
					alert("옵션 명칭을 입력해 주세요");
					$(this).focus();
					validation = false;
					return ;
				}
			});
			$(".jOptionPrice").each(function(){
				var price = $(this).val();
				if(price == ''){
					alert("옵션 가격을 입력해 주세요");
					$(this).focus();
					validation = false;
					return ;
				}
				if(!isNumber.test(price)) {
				    alert('옵션 가격은 숫자만 입력해주세요.');
					$(this).focus();
					validation = false;
				    return ;
				}
			});
			
			if(!isEmail.test($("[name='email']").val())){
				alert("이메일을 올바르게 입력해 주세요");
				$("[name='email']").focus();
				return ;
			}
			
			if(validation){
				if(confirm("저장하시겠습니까?")){
					$("select").prop("disabled", false);
					$("input").prop("disabled", false);
					$("#jData").ajaxSubmit({
						url : rootPath + "/superUser/updateCompany",
						type : "post",
						forceSync : true,
						dataType : "json",
						success : function(data){
							alert(data.returnMessage);
							if(data.returnCode == 1) {
								if(_rurl != "") {
									location.href=_rurl;
								} else {
									location.reload();
								}
							}						
						}
					});
				}
			}
			
		});
		
		$(".jDeleteOption").click2(function(){
			
			var optionNumber = $(this).attr("_no");

			$.ajax({
				url : rootPath + "/superUser/deleteCompanyOption",
				type : "POST",
				dataType : "json",
				data : {
					optionNumber : optionNumber 
				}
				, success : function(data){
					alert(data.returnMessage)
					if(data.returnCode == 1){
						location.reload();
					}
				}
			});
			
		});
			
		$(".jSido").change(function(){
			obj.fn_setGugun();
		});
			
		
	});
		

</script>


<div id="Contents"  class="">
	<form id="jData" method="post" enctype="multipart/form-data">

	<h1> 가맹점 </h1>
	<!-- location area -->
	<h2 style="width:98%;">
	</h2>
	
	<input type="hidden" name="userNumber" value="${param.userNumber }">
	<div class="data" style="width:100%;">
	<c:if test="${accessLevelCode ne 2 }">
		<h3>가맹점 회원</h3>
		<table class="datacList" style="width:100%; ">
			<thead align="left">
			
				<tr>
					<th width="10%">수수료율<br/>변경 내역</th>
					<td width="70%"> 
						<ul>
							<c:forEach var="row" items="${retData.commissionList }">
								<li style="width: 19%; float: left;">
									<div align="center" style="float: left; width: 100%">
										<ul>
											<li>${row.value }%</li>
											<li>${row.regDate }</li>
										</ul>
									</div>
								</li>
								<div style="float: left; vertical-align: middle; padding-top: 15px;">></div>
							</c:forEach>	
						</ul>
					</td>
					<th width="10%">수수료율</th>
					<td width="10%"><input type="text" class="" name="commission" value="${data.commission}" style="width: 25px;">%</td>
				</tr>
			</thead>
		</table>
			
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="left">
				<tr>
					<th width="10%">인센티브<br/>변경 내역</th>
					<td width="70%">
						<ul>
							<c:forEach var="row" items="${retData.incentiveList }">
								<li style="width: 19%; float: left;">
									<div align="center" style="float: left; width: 100%">
										<ul>
											<li>${row.value }%</li>
											<li>${row.regDate }</li>
										</ul>
									</div>
								</li>
								<div style="float: left; vertical-align: middle; padding-top: 15px;">></div>
							</c:forEach>	
						</ul>
					</td>
					<th width="10%">인센티브율</th>
					<td width="10%"><input type="text" class="" name="incentive" value="${data.incentive}" style="width: 25px;">%</td>
				</tr>
				
			</thead>
		</table>
		
		<table class="datacList" style="width:100%; margin-bottom: 50px; ">
			<thead align="center">
				
				<tr>
					<th>메모</th>
					<td><input type="text" class="" name="memo" value="${data.memo }" style="width: 95%" ></td>
				</tr>
			
			</thead>
		</table>
	</c:if>
		<h3>가맹점 설정 정보</h3>
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="center">
			
				<tr>
					<th width="10%">관리자 / 어플 아이디</th>
					<td width="40%"><input type="text" class="" name="userID" value="${data.userID }" style="width: 95%"></td>
					<th width="10%">관리자 / 어플 비밀번호</th>
					<td width="40%"><input type="text" class="" name="userPWD" value="${data.userPWD }" style="width: 95%"></td>
				</tr>
			
				<tr>
					<th>가맹점명</th>
					<td><input type="text" class="" name="franchiseName" value="${data.franchiseName }" style="width: 95%"></td>
					<th>대표자명</th>
					<td><input type="text" class="" name="owner" value="${data.owner }" style="width: 95%"></td>
				</tr>
			
				<tr>
					<th>사업자번호</th>
					<td><input type="text" class="" name="corporate" value="${data.corporate }" style="width: 95%"></td>
					<th>전화번호</th>
					<td><input type="text" class="" name="tel" value="${data.tel }" style="width: 95%"></td>
				</tr>
			
				<tr>
					<th>팩스번호</th>
					<td><input type="text" class="" name="fax" value="${data.fax }" style="width: 95%"></td>
					<th>핸드폰 번호</th>
					<td><input type="text" class="" name="phone" value="${data.phone }" style="width: 95%"></td>
				</tr>
			
				<tr>
					<th>홈페이지 주소</th>
					<td><input type="text" class="" name="homepage" value="${data.homepage }" style="width: 95%"></td>
					<th>이메일</th>
					<td><input type="text" class="" name="email" value="${data.email }" style="width: 95%"></td>
				</tr>
			
				<tr>
					<th>계좌번호</th>
					<td><input type="text" class="" name="accountNumber" value="${data.accountNumber }" style="width: 95%"></td>
					<th>메인상품</th>
					<td>
						<input type="radio" class="" name="mainProduct" ${data.mainProduct == 1 ? "checked" : "" } value="1"><span>생화</span>&nbsp;&nbsp;
						<input type="radio" class="" name="mainProduct" ${data.mainProduct == 2 ? "checked" : "" } value="2"><span>관엽</span>&nbsp;&nbsp;
						<input type="radio" class="" name="mainProduct" ${data.mainProduct == 3 ? "checked" : "" } value="3"><span>동서양한</span>&nbsp;&nbsp;
						<input type="radio" class="" name="mainProduct" ${data.mainProduct == 4 ? "checked" : "" } value="4"><span>화환</span>&nbsp;&nbsp;
						<input type="radio" class="" name="mainProduct" ${data.mainProduct == 5 ? "checked" : "" } value="5"><span>기타</span>
					</td>
				</tr>
			
				<tr>
					<th>업력</th>
					<td><input type="text" class="" name="extra" value="${data.extra }" style="width: 95%"></td>
					<th>배송가능시간</th>
					<td><input type="text" class="" name="deliverTime" value="${data.deliverTime }" style="width: 95%"></td>
				</tr>
			 
				<tr>
					<th>영업시간</th>
					<td colspan="3">
						<select class="" name="openTimeFlag">
							<option value="오전" ${data.openTimeFlag == '오전' ? "selected" : "" }>오전</option>
							<option value="오후" ${data.openTimeFlag == '오후' ? "selected" : "" }>오후</option>
						</select>&nbsp;
						<select class="" name="openSHour">
							<c:forEach begin="1" end="12" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${data.openSHour == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${data.openSHour == i ? 'selected' : '' }>${i }</option></c:if>
							</c:forEach>
						</select>&nbsp;시
						<select class="" name="openSMin">
							<c:forEach begin="0" end="59" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${data.openSMin == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${data.openSMin == i ? 'selected' : '' }>${i }</option></c:if>
								
							</c:forEach>
						</select>&nbsp;분 ~
						<select class="" name="closeTimeFlag">
							<option value="오전" ${data.closeTimeFlag == '오전' ? "selected" : "" }>오전</option>
							<option value="오후" ${data.closeTimeFlag == '오후' ? "selected" : "" }>오후</option>
						</select>&nbsp;
						<select class="" name="closeEHour">
							<c:forEach begin="1" end="12" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${data.closeEHour == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${data.closeEHour == i ? 'selected' : '' }>${i }</option></c:if>
							</c:forEach>
						</select>&nbsp;시
						<select class="jMin" name="closeEmin">
							<c:forEach begin="0" end="59" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${data.closeEmin == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${data.closeEmin == i ? 'selected' : '' }>${i }</option></c:if>
							</c:forEach>
						</select>&nbsp;분 
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<c:set var="week" value="${data.dayOfWeek }"  />
						<c:set var="weekArray" value="${fn:split(week, '|') }" />
						<input type="checkbox" name="dayOfWeek[]" value="1" <c:forEach var="w" items="${weekArray }">${w eq 1 ? 'checked' : '' }</c:forEach>><span>월</span>					
						<input type="checkbox" name="dayOfWeek[]" value="2" <c:forEach var="w" items="${weekArray }">${w eq 2 ? 'checked' : '' }</c:forEach>><span>화</span>
						<input type="checkbox" name="dayOfWeek[]" value="3" <c:forEach var="w" items="${weekArray }">${w eq 3 ? 'checked' : '' }</c:forEach>><span>수</span>
						<input type="checkbox" name="dayOfWeek[]" value="4" <c:forEach var="w" items="${weekArray }">${w eq 4 ? 'checked' : '' }</c:forEach>><span>목</span>
						<input type="checkbox" name="dayOfWeek[]" value="5" <c:forEach var="w" items="${weekArray }">${w eq 5 ? 'checked' : '' }</c:forEach>><span>금</span>
						<input type="checkbox" name="dayOfWeek[]" value="6" <c:forEach var="w" items="${weekArray }">${w eq 6 ? 'checked' : '' }</c:forEach>><span>토</span>
						<input type="checkbox" name="dayOfWeek[]" value="0" <c:forEach var="w" items="${weekArray }"></c:forEach>><span>일</span>
					</td>
				</tr>
				 
			</thead>
		</table>
	
		<h3>어플 설정 정보</h3>	
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="center">
			
				<tr>
					<th rowspan="3">가맹점 이미지</th>
					<td rowspan="3" colspan="3">
						<img src="${properties.img_path }/480/${data.franchiseImg }" onerror="this.style.visibility='hidden'" width="100px" height="75px" style="float: left;">
						<c:import url="${properties.include_path}/fileUpload.jsp">
							<c:param name="fileIndex" value="200" />
							<c:param name="fileName" value="images[0]" />
							<c:param name="filePath" value="${data.franchiseImg }" />
							<c:param name="includePath" value="${properties.img_path }" />
						</c:import>
					</td>
					<th>지역</th>
					<td colspan="2">
						<select class="jSido" name="sido" style="width: 120px;">
							<c:forEach var="sidoList" items="${retData.sidoList }">
								<option value="${sidoList.sidoNumber }" ${data.sido == sidoList.sidoNumber ? 'selected' : '' }>${sidoList.description }</option>
							</c:forEach>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select class="" name="gugun" style="width: 140px;">
						</select>
					</td>
				</tr>
				<tr>
					<th>상세주소</th>
					<td colspan="2"><input type="text" class="" name="address" value="${data.address }" style="width: 95%"></td>
				</tr>
				<tr>
					<th>가맹점설명</th>
					<td colspan="2"><input type="text" class="" name="franchiseDesc" value="${data.franchiseDesc }" style="width: 95%"></td>
				</tr>
				
				
				<tr>
					<th>상세 이미지</th>
					<td colspan="3">
						<img src="${properties.img_path }/480/${data.detailImg }" onerror="this.style.visibility='hidden'" width="100px" height="75px" style="float: left;">
						<c:import url="${properties.include_path}/fileUpload.jsp">
							<c:param name="fileIndex" value="201" />
							<c:param name="fileName" value="images[1]" />
							<c:param name="filePath" value="${data.detailImg }" />
							<c:param name="includePath" value="${properties.img_path }" />
						</c:import>
					</td>
					<th>상세내용</th>
					<td colspan="2"><input type="text" class="" name="contents" value="${data.contents }" style="width: 95%"></td>
				</tr>
				
				<c:forEach var="option" items="${retData.appOptionList }" varStatus="status">
				<tr class="jOptionRow">
					<th>선택 옵션 명칭</th>
					<td><input type="text" class="jOptionName" name="optionNames[]" value="${option.optionName }" style="width: 95%"></td>
					<th>선택 옵션 가격</th>
					<td><input type="text" class="jOptionPrice" name="optionPrices[]" value="${option.optionPrice }" style="width: 95%"></td>
					<th>선택 옵션 이미지</th>
					<td>
						<input type="hidden" name="optionNumbers[]" value="${option.optionNumber == null ? 0 : option.optionNumber}"/>
						<c:import url="${properties.include_path}/fileUpload.jsp">
							<c:param name="fileIndex" value="${102 + status.index }" />
							<c:param name="fileName" value="images[${status.index + 2}]" />
							<c:param name="filePath" value="${option.optionImg }" />
							<c:param name="includePath" value="${properties.img_path }" />
						</c:import>
					</td>
					<td>
						<input type="button" class="button medium white jDeleteOption" value="삭제" _no="${option.optionNumber }">
					</td>
				</tr>
				</c:forEach>
				
				<tr>
					<th colspan="7"><input type="button" class="button medium white jAddOption" value="선택옵션추가" imgNumber="104"></th>
				</tr>
				 
			</thead>
		</table>
		
		<h3>수발주 정보</h3>	
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="center">
			
				<tr>
					<th width="10%">주문서 팩스</th>
					<td width="40%">
						<input type="radio" name="orderSheetFax" ${data.orderSheetFax eq 'Y' ? 'checked' : '' } value="Y"><span>받음</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="orderSheetFax" ${data.orderSheetFax eq 'N' ? 'checked' : '' } value="N"><span>받지 않음</span>
					</td>
					<th width="10%">인수증팩스</th>
					<td width="40%">
						<input type="radio" name="receiptFax" ${data.receiptFax == 'Y' ? 'checked' : '' } value="Y"><span>받음</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="receiptFax" ${data.receiptFax == 'N' ? 'checked' : '' } value="N"><span>받지 않음</span>
					</td>
				</tr>
				
				<tr>
					<th>발주허용여부</th>
					<td>
						<input type="radio" name="order" ${data.order == 'Y' ? 'checked' : '' } value="Y"><span>허용</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="order" ${data.order == 'N' ? 'checked' : '' } value="N"><span>불가</span>
					</td>
					<th>수주허용여부</th>
					<td>
						<input type="radio" name="obtainOrder" ${data.obtainOrder == 'Y' ? 'checked' : '' } value="Y"><span>허용</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="obtainOrder" ${data.obtainOrder == 'N' ? 'checked' : '' } value="N"><span>불가</span>
					</td>
				</tr>
				
				<tr>
					<th>장례용품가능</th>
					<td>
						<input type="radio" name="funeral" ${data.funeral eq 'Y' ? 'checked' : '' } value="Y"><span>가능</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="funeral" ${data.funeral eq 'N' ? 'checked' : '' } value="N"><span>불가</span>
					</td>
					<th>휴일배송</th>
					<td>
						<input type="radio" name="holidayDeliver" ${data.holidayDeliver == 'Y' ? 'checked' : '' } value="Y"><span>받음</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="holidayDeliver" ${data.holidayDeliver == 'N' ? 'checked' : '' } value="N"><span>받지 않음</span>
					</td>
				</tr>
				
				<tr>
					<th>자체배송</th>
					<td>
						<input type="radio" name="selfDeliver" ${data.selfDeliver == 'Y' ? 'checked' : '' } value="Y"><span>가능</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="selfDeliver" ${data.selfDeliver == 'N' ? 'checked' : '' } value="N"><span>불가</span>
					</td>
					<th>부재중 설정</th>
					<td>
						<input type="radio" name="absence" ${data.absence == 'Y' ? 'checked' : '' } value="Y"><span>영업중</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="absence" ${data.absence == 'N' ? 'checked' : '' } value="N"><span>부재중</span>
					</td>
				</tr>
				
				<tr>
					<th>임시휴일 설정</th>
					<td colspan="3">
						<c:set var="sHolidayArray" value="${fn:split(data.sHoliday, '-' )}"/>
						<c:set var="eHolidayArray" value="${fn:split(data.eHoliday, '-' )}"/>
						<select class="" name="sHolidayYear">
							<c:forEach begin="2016" end="2099" var="i">
								<option value="${i }" ${sHolidayArray[0] == i ? 'selected' : '' }>${i }</option>
							</c:forEach>
						</select>&nbsp;년
						<select class="" name="sHolidayMon">
							<c:forEach begin="1" end="12" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${sHolidayArray[1] == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${sHolidayArray[1] == i ? 'selected' : '' }>${i }</option></c:if>
							</c:forEach>
						</select>&nbsp;월
						<select class="" name="sHolidayDay">
							<c:forEach begin="1" end="31" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${sHolidayArray[2] == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${sHolidayArray[2] == i ? 'selected' : '' }>${i }</option></c:if>
							</c:forEach>
						</select>&nbsp;일 ~
						<select class="" name="eHolidayYear">
							<c:forEach begin="2016" end="2099" var="i">
								<option value="${i }" ${eHolidayArray[0] == i ? 'selected' : '' }>${i }</option>
							</c:forEach>
						</select>&nbsp;년
						<select class="" name="eHolidayMon">
							<c:forEach begin="1" end="12" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${eHolidayArray[1] == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${eHolidayArray[1] == i ? 'selected' : '' }>${i }</option></c:if>
							</c:forEach>
						</select>&nbsp;월
						<select class="" name="eHolidayDay">
							<c:forEach begin="1" end="31" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${eHolidayArray[2] == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${eHolidayArray[2] == i ? 'selected' : '' }>${i }</option></c:if>
							</c:forEach>
						</select>&nbsp;일 
					</td>
				</tr>
				
			</thead>
		</table>
		
		<h3>세금계산서 발행정보</h3>	
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="left">
			
				<tr>
					<th width="10%">사업자번호</th>
					<td width="40%">
						<input type="text" class="" name="billCorporate" value="${data.billCorporate }" style="width: 95%">
					</td>
					<th width="10%">등록/변경일</th>
					<td width="40%">
						<input type="text" class="datepicker" name="billRegDate" value="${data.billRegDate }" style="width: 95%">
					</td>
				</tr>
				
				<tr>
					<th>법인명</th>
					<td>
						<input type="text" class="" name="billCompanyName" value="${data.billCompanyName }" style="width: 95%">
					</td>
					<th>대표자</th>
					<td>
						<input type="text" class="" name="billOwner" value="${data.billOwner }" style="width: 95%">
					</td>
				</tr>
				
				<tr>
					<th>업태</th>
					<td>
						<input type="text" class="" name="billBusiness" value="${data.billBusiness }" style="width: 95%">
					</td>
					<th>업종</th>
					<td>
						<input type="text" class="" name="billCategory" value="${data.billCategory }" style="width: 95%">
					</td>
				</tr>
				
				<tr>
					<th>사업장 주소</th>
					<td colspan="3">
						<input type="text" class="" name="billAddress" value="${data.billAddress }" style="width: 95%">
					</td>
				</tr>
				
				<tr>
					<th>계산서 유형</th>
					<td colspan="3">
						<input type="radio" class="" name="billType" ${data.billType eq 1 ? 'checked' : '' } value="1"><span>수발주별도</span>
						<input type="radio" class="" name="billType" ${data.billType eq 2 ? 'checked' : '' } value="2"><span>수발주차액</span>
					</td>
				</tr>
				
				
			</thead>
		</table>
		
		<div class="btngroupcenter">		
			<!-- 
			<input type="button" class="button medium white jBack" value="목록으로">
			 -->
			<input type="button" class="button medium white jSave" value="저장">
		</div>
		
	</div>
	</form>	
</div>

<c:import url="${properties.include_path}/footer_admin.jsp"/>