<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>中国发展基金会会议管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/cdrf.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
        <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<script type="text/javascript">
	var errMsg = <%=request.getAttribute("errMsg")%>;//"${errMsg }";
	if (errMsg != null && errMsg != "") {
		alert(errMsg);
	}
	
	var authList = ${limit};
</script>
<body>
	<div class="navbar navbar-fixed-top cdrf-header">
		<div class="container">
			<h1>中国发展基金会会议管理系统</h1>
		</div>
	</div>
	<div class="cdrf-content">
		<div class="row">
			<div class="col-md-2">
				<div class="cdrf-left-nav bs-sidebar affix">
					<ul class="nav nav-pills nav-stacked">
						<li><a href="javascript:void(0);" id="meeting_menu">会议管理</a></li>
						<li  class="active"><a href="javascript:void(0);" id="member_menu">人员管理</a></li>
						<li><a href="javascript:void(0);" id="setting_menu">权限设置</a></li>
					</ul>
				</div>

			</div>
			<div class="col-md-10">
				<div class="row cdrf-right-content">
					<div id="addPersonFormWrap">
						<form role="form" class="form-horizontal" id="editForm" action="saveOrUpdateMeetingMember" method="post">
							<input type="hidden" class="form-control" name="id" id="id" value="${memberInfo.id }" >
							<div class="col-lg-6">
								<div class="form-group">
									<label for="inputName" class="col-sm-4 control-label">界别</label>
									<div class="col-sm-7">
										<select class="form-control" name="sector">
										<c:choose>
										<c:when test="${memberInfo.sector eq '企业' }">  
											<option value="企业" selected="selected">企业</option>
											<option value="国际组织">国际组织</option>
											<option value="学者">学者</option>
										</c:when>
										<c:when test="${memberInfo.sector eq '国际组织' }">  
											<option value="企业">企业</option>
											<option value="国际组织" selected="selected">国际组织</option>
											<option value="学者">学者</option>
										</c:when>
										<c:when test="${memberInfo.sector eq '学者' }">  
											<option value="企业">企业</option>
											<option value="国际组织">国际组织</option>
											<option value="学者" selected="selected">学者</option>
										</c:when>
										<c:otherwise> 
											<option value="企业">企业</option>
											<option value="国际组织">国际组织</option>
											<option value="学者">学者</option>
						   				</c:otherwise>
										</c:choose>
										</select>
										<!-- <input type="text" class="form-control" name="sector" id="inputName" placeholder="0：企业；1：国际组织；2：学者"> -->
									</div>
								</div>
								<div class="form-group">
									<label for="inputType" class="col-sm-4 control-label">中文公司/组织全称</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="companyCn"
											id="inputType" value="${memberInfo.companyCn }" placeholder="中文公司/组织全称">
									</div>
								</div>
								<div class="form-group">
									<label for="inputStime" class="col-sm-4 control-label">英文公司/组织全称</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="companyEn"
											id="inputStime" value="${memberInfo.companyEn }" placeholder="英文公司/组织全称">
									</div>
								</div>
								<div class="form-group">
									<label for="inputEtime" class="col-sm-4 control-label">中文公司/组织缩写</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="companyShortCn"
											id="inputEtime" value="${memberInfo.companyShortCn }" placeholder="中文公司/组织缩写">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPurport" class="col-sm-4 control-label">英文公司/组织缩写</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="companyShortEn"
											id="inputPurport" value="${memberInfo.companyShortEn }" placeholder="英文公司/组织缩写">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">中文姓名</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="nameCn"
											id="inputPlace" value="${memberInfo.nameCn }" placeholder="中文姓名" data-requirenull data-info="请输入中文姓名">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">英文姓</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="surnameEn"
											id="inputPlace" value="${memberInfo.surnameEn }" placeholder="英文姓">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">英文名</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="givenNameEn"
											id="inputPlace" value="${memberInfo.givenNameEn }" placeholder="英文名">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">英文全名</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="nameEn"
											id="inputPlace" value="${memberInfo.nameEn }" placeholder="英文全名">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">称谓</label>
									<div class="col-sm-7">
										<select class="form-control" name="title">
											<c:choose>
											<c:when test="${memberInfo.title eq '女士' }">  
												<option value="先生">先生</option>
												<option value="女士" selected="selected">女士</option>
											</c:when>
											<c:when test="${memberInfo.title eq '先生' }">  
												<option value="先生" selected="selected">先生</option>
												<option value="女士">女士</option>
											</c:when>
											<c:otherwise> 
												<option value="先生">先生</option>
												<option value="女士">女士</option>
							   				</c:otherwise>
											</c:choose>
										</select>
										<!-- <input type="text" class="form-control" name="title" id="inputPlace" placeholder="0：女士；1：先生"> -->
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">性别</label>
									<div class="col-sm-7">
										<select class="form-control" name="sex">
											<c:choose>
											<c:when test="${memberInfo.sex eq '女' }">  
												<option value="男">男</option>
												<option value="女" selected="selected">女</option>
												<option value="未知">未知</option>
											</c:when>
											<c:when test="${memberInfo.sex eq '男' }">  
												<option value="男" selected="selected">男</option>
												<option value="女">女</option>
												<option value="未知">未知</option>
											</c:when>
											<c:when test="${memberInfo.sex eq '未知' }">  
												<option value="男">男</option>
												<option value="女">女</option>
												<option value="未知" selected="selected">未知</option>
											</c:when>
											<c:otherwise> 
												<option value="男">男</option>
												<option value="女">女</option>
												<option value="未知">未知</option>
							   				</c:otherwise>
											</c:choose>
										</select>
										<!-- <input type="text" class="form-control" name="sex" id="inputPlace" placeholder="0：女；1：男；2：未知"> -->
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">证件类型</label>
									<div class="col-sm-7">
										<select class="form-control" name="idType">
											<c:choose>
											<c:when test="${memberInfo.idType eq '身份证' }">  
												<option value="身份证" selected="selected">身份证</option>
												<option value="护照">护照</option>
												<option value="港澳台通行证">港澳台通行证</option>
												<option value="军官证">军官证</option>
												<option value="驾驶证">驾驶证</option>
												<option value="其他">其他</option>
											</c:when>
											<c:when test="${memberInfo.idType eq '护照' }">  
												<option value="身份证">身份证</option>
												<option value="护照" selected="selected">护照</option>
												<option value="港澳台通行证">港澳台通行证</option>
												<option value="军官证">军官证</option>
												<option value="驾驶证">驾驶证</option>
												<option value="其他">其他</option>
											</c:when>
											<c:when test="${memberInfo.idType eq '港澳台通行证' }">  
												<option value="身份证">身份证</option>
												<option value="护照">护照</option>
												<option value="港澳台通行证" selected="selected">港澳台通行证</option>
												<option value="军官证">军官证</option>
												<option value="驾驶证">驾驶证</option>
												<option value="其他">其他</option>
											</c:when>
											<c:when test="${memberInfo.idType eq '军官证' }">  
												<option value="身份证">身份证</option>
												<option value="护照">护照</option>
												<option value="港澳台通行证">港澳台通行证</option>
												<option value="军官证" selected="selected">军官证</option>
												<option value="驾驶证">驾驶证</option>
												<option value="其他">其他</option>
											</c:when>
											<c:when test="${memberInfo.idType eq '驾驶证' }">  
												<option value="身份证">身份证</option>
												<option value="护照">护照</option>
												<option value="港澳台通行证">港澳台通行证</option>
												<option value="军官证">军官证</option>
												<option value="驾驶证" selected="selected">驾驶证</option>
												<option value="其他">其他</option>
											</c:when>
											<c:when test="${memberInfo.idType eq '其他' }">  
												<option value="身份证">身份证</option>
												<option value="护照">护照</option>
												<option value="港澳台通行证">港澳台通行证</option>
												<option value="军官证">军官证</option>
												<option value="驾驶证">驾驶证</option>
												<option value="其他" selected="selected">其他</option>
											</c:when>
											<c:otherwise> 
												<option value="身份证">身份证</option>
												<option value="护照">护照</option>
												<option value="港澳台通行证">港澳台通行证</option>
												<option value="军官证">军官证</option>
												<option value="驾驶证">驾驶证</option>
												<option value="其他">其他</option>
							   				</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="inputIdNo" class="col-sm-4 control-label">证件号</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="idNo"
											id="inputIdNo" value="${memberInfo.idNo }" placeholder="证件号">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">生日</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="birthday"
											id="inputPlace" value="${memberInfo.birthday }" placeholder="生日">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">职务中文</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="jobCn"
											id="inputPlace" value="${memberInfo.jobCn }" placeholder="职务中文">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">职务英文</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="jobEn"
											id="inputPlace" value="${memberInfo.jobEn }" placeholder="职务英文">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">部门中文</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="deptCn"
											id="inputPlace" value="${memberInfo.deptCn }" placeholder="部门中文">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">部门英文</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="deptEn"
											id="inputPlace" value="${memberInfo.deptEn }" placeholder="部门英文">
									</div>
								</div>
							</div>
							<div class="col-lg-6">
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">电话</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="phone"
											id="inputPlace" value="${memberInfo.phone }" placeholder="电话">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">传真</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="fax"
											id="inputPlace" value="${memberInfo.fax }" placeholder="传真">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">手机</label>
									<div class="col-sm-7">
										<input type="text" maxlength="11" class="form-control" name="mobile"
											id="inputPlace" value="${memberInfo.mobile }" placeholder="手机" data-requirenull data-info="请输入手机">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">邮箱</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="email"
											id="inputPlace" value="${memberInfo.email }" placeholder="邮箱">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">地址</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="address"
											id="inputPlace" value="${memberInfo.address }" placeholder="地址">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="contacter"
											id="inputPlace" value="${memberInfo.contacter }" placeholder="联系人">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人电话</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="contacterPhone"
											id="inputPlace" value="${memberInfo.contacterPhone }" placeholder="联系人电话">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人传真</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="contacterFax"
											id="inputPlace" value="${memberInfo.contacterFax }" placeholder="联系人传真">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人手机</label>
									<div class="col-sm-7">
										<input type="text" maxlength="11" class="form-control" name="contacterMobile"
											id="inputPlace" value="${memberInfo.contacterMobile }" placeholder="联系人手机">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人邮箱</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="contacterEmail"
											id="inputPlace" value="${memberInfo.contacterEmail }" placeholder="联系人邮箱">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人地址</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="contacterAdd"
											id="inputPlace" value="${memberInfo.contacterAdd }" placeholder="联系人地址">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">类别</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="type"
											id="inputPlace" value="${memberInfo.type }" placeholder="类别">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">等级</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="grade"
											id="inputPlace" value="${memberInfo.grade }" placeholder="等级">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">领域1</label>
									<div class="col-sm-7">
										<textarea class="form-control" name="remark1" id="inputPlace"
											placeholder="领域1">${memberInfo.remark1 }</textarea>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">领域2</label>
									<div class="col-sm-7">
										<textarea class="form-control" name="remark2" id="inputPlace"
											placeholder="领域2">${memberInfo.remark2 }</textarea>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">领域3</label>
									<div class="col-sm-7">
										<textarea class="form-control" name="remark3" id="inputPlace"
											placeholder="领域3">${memberInfo.remark3 }</textarea>
									</div>
								</div>
							</div>
							<div class="col-lg-12 center-block">
								<div class="col-lg-5">
								</div>
								<div class="col-lg-7">
									<div class="form-group center-block">
										<button type="button" class="btn btn-lg btn-primary center-block"
											id="updatePersonBtn">修&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;改</button>
									</div>
								</div>
							</div>
						</form>
						<br>
						<div class="cdrf-meeting-list" id="meetingList">
						<table class="table table-striped">
							<thead>
								<tr>
									<td>会议名称(中文)</td>
									<td>会议名称(英文)</td>
									<td>会议类别</td>
									<td>会议起始时间</td>
									<td>会议结束时间</td>
									<td>会议主旨</td>
									<td>会议地点</td>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!empty requestScope.meetingList}">
										<c:forEach items="${requestScope.meetingList}" begin="0"
											step="1" var="meeting">
											<tr>
												<td>${meeting.nameCn }</td>
												<td>${meeting.nameEn }</td>
												<td>${meeting.type }</td>
												<td>${meeting.stime }</td>
												<td>${meeting.etime }</td>
												<td>${meeting.purport }</td>
												<td>${meeting.place }</td>
											</tr>
										</c:forEach>
									</c:when>
								</c:choose>
							</tbody>
						</table>
						<ul class="pager">
						<c:choose>
						   <c:when test="${moreFlag==1 && currPageNo==1}">  
								<li class="disabled"><a href="#">上一页</a>
								</li>
								<li><a href="#" onclick="personManager.gotoPage(${currPageNo+1})">下一页</a>
								</li>
						   </c:when>
						   <c:when test="${moreFlag==1 && currPageNo!=1}">  
								<li><a href="#" onclick="personManager.gotoPage(${currPageNo-1})">上一页</a>
								</li>
								<li><a href="#" onclick="personManager.gotoPage(${currPageNo+1})">下一页</a>
								</li>
						   </c:when>
						   <c:when test="${moreFlag==0 && currPageNo!=1}">  
								<li><a href="#" onclick="personManager.gotoPage(${currPageNo-1})">上一页</a>
								</li>
								<li class="disabled"><a href="#">下一页</a>
								</li>
						   </c:when>
						   <c:otherwise> 
						   
						   </c:otherwise>
						</c:choose>
						</ul>
					</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!-- <script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script> -->
	<script src="js/jquery.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
		(function($, w) {
			var personManager = {};
			var meeting_menu = $("#meeting_menu"), member_menu = $("#member_menu"), setting_menu = $("#setting_menu");
			function initData() {
				//todo
			}
			function bindEvent() {
				meeting_menu.on("click", function(e) {
					location.href = "queyMeeting";
				});
				member_menu.on("click", function(e) {
					location.href = "queyMember";
				});
				setting_menu.on("click", function(e) {
					location.href = "queryUser";
				});
			}
			
			personManager.gotoPage = function(pageNo){
				var formEle = document.createElement("form");
				document.body.appendChild(formEle);
				var inputEle = document.createElement("input");
				inputEle.type = "hidden";
				formEle.appendChild(inputEle);
				inputEle.name = "pageNo";
				inputEle.value = pageNo;
				
				inputEle = document.createElement("input");
				inputEle.type = "hidden";
				formEle.appendChild(inputEle);
				inputEle.name = "id";
				inputEle.value = "${memberInfo.id }";
				
				formEle.action = "editMember";
				//formEle.method = "post";
				formEle.submit();
			}
			
			personManager.init = function() {
				//init data
				initData();
				//bind event
				bindEvent();
			}

			w.personManager = personManager;
		})($, window);

		personManager.init();
	</script>
</body>
</html>
