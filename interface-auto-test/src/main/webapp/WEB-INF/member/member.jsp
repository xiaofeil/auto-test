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
	var repeatMemberRows = <%=request.getAttribute("repeatMemberRows")%>;//"${repeatMemberRows }";
	var nullMemberNameRows = <%=request.getAttribute("nullMemberNameRows")%>;//"${nullMemberNameRows }";
	if (errMsg != null && errMsg != "") {
		alert(errMsg);
	}
	if (repeatMemberRows != null && repeatMemberRows != "") {
		alert(repeatMemberRows);
	}
	if (nullMemberNameRows != null && nullMemberNameRows != "") {
		alert(nullMemberNameRows);
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
				<div class="row cdrf-right-operation">
					<div class="col-md-6">
						<button type="button" class="btn btn-default" id="newPersonBtn">添加人员</button>
						<button type="button" class="btn btn-default" id="importPersonBtn">导入人员</button>
						<button type="button" class="btn btn-default" id="exportPersonBtn">导出人员</button>
					</div>
					<div class="col-lg-6">
						<div class="input-group">
							<form class="form-inline" action="queyMember" method="get" id="query_form">
								<div class="form-group">
									<label class="sr-only" for="exampleInputEmail2">人员搜索</label> <input
										type="text" class="form-control" id="exampleInputEmail2" name="nameQ"
										placeholder="人员搜索">
								</div>
								<button type="submit" class="btn btn-default" id="personQueryBtn">搜索</button>
							</form>
						</div>
						<!-- /input-group -->
					</div>
				</div>
				<div class="row cdrf-right-content">
					<div class="hidden" id="addPersonFormWrap">
						<form role="form" class="form-horizontal" action="saveOrUpdateMeetingMember" method="post">
							<div class="col-lg-6">
								<div class="form-group">
									<label for="inputName" class="col-sm-4 control-label">界别</label>
									<div class="col-sm-7">
										<select class="form-control" name="sector">
											<option value="企业">企业</option>
											<option value="国际组织">国际组织</option>
											<option value="学者">学者</option>
										</select>
										<!-- <input type="text" class="form-control" name="sector" id="inputName" placeholder="0：企业；1：国际组织；2：学者"> -->
									</div>
								</div>
								<div class="form-group">
									<label for="inputType" class="col-sm-4 control-label">中文公司/组织全称</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="companyCn"
											id="inputType" placeholder="中文公司/组织全称">
									</div>
								</div>
								<div class="form-group">
									<label for="inputStime" class="col-sm-4 control-label">英文公司/组织全称</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="companyEn"
											id="inputStime" placeholder="英文公司/组织全称">
									</div>
								</div>
								<div class="form-group">
									<label for="inputEtime" class="col-sm-4 control-label">中文公司/组织缩写</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="companyShortCn"
											id="inputEtime" placeholder="中文公司/组织缩写">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPurport" class="col-sm-4 control-label">英文公司/组织缩写</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="companyShortEn"
											id="inputPurport" placeholder="英文公司/组织缩写">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">中文姓名</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="nameCn"
											id="inputPlace" placeholder="中文姓名" data-requirenull data-info="请输入中文姓名">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">英文姓</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="surnameEn"
											id="inputPlace" placeholder="英文姓">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">英文名</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="givenNameEn"
											id="inputPlace" placeholder="英文名">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">英文全名</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="nameEn"
											id="inputPlace" placeholder="英文全名">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">称谓</label>
									<div class="col-sm-7">
										<select class="form-control" name="title">
											<option value="先生">先生</option>
											<option value="女士">女士</option>
										</select>
										<!-- <input type="text" class="form-control" name="title" id="inputPlace" placeholder="0：女士；1：先生"> -->
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">性别</label>
									<div class="col-sm-7">
										<select class="form-control" name="sex">
											<option value="男">男</option>
											<option value="女">女</option>
											<option value="未知">未知</option>
										</select>
										<!-- <input type="text" class="form-control" name="sex" id="inputPlace" placeholder="0：女；1：男；2：未知"> -->
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">证件类型</label>
									<div class="col-sm-7">
										<select class="form-control" name="idType">
											<option value="身份证">身份证</option>
											<option value="护照">护照</option>
											<option value="港澳台通行证">港澳台通行证</option>
											<option value="军官证">军官证</option>
											<option value="驾驶证">驾驶证</option>
											<option value="其他">其他</option>
										</select>
										<!-- <input type="text" class="form-control" name="idType" id="inputPlace" placeholder="0：身份证；1：护照；2：港澳台通行证；3：军官证；4：驾驶证；5：其他"> -->
									</div>
								</div>
								<div class="form-group">
									<label for="inputIdNo" class="col-sm-4 control-label">证件号</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="idNo"
											id="inputIdNo" placeholder="证件号">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">生日</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="birthday"
											id="inputPlace" placeholder="生日">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">职务中文</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="jobCn"
											id="inputPlace" placeholder="职务中文">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">职务英文</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="jobEn"
											id="inputPlace" placeholder="职务英文">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">部门中文</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="deptCn"
											id="inputPlace" placeholder="部门中文">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">部门英文</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="deptEn"
											id="inputPlace" placeholder="部门英文">
									</div>
								</div>
							</div>
							<div class="col-lg-6">
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">电话</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="phone"
											id="inputPlace" placeholder="电话">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">传真</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="fax"
											id="inputPlace" placeholder="传真">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">手机</label>
									<div class="col-sm-7">
										<input type="text" maxlength="11" class="form-control" name="mobile"
											id="inputPlace" placeholder="手机" data-requirenull data-info="请输入手机">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">邮箱</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="email"
											id="inputPlace" placeholder="邮箱">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">地址</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="address"
											id="inputPlace" placeholder="地址">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="contacter"
											id="inputPlace" placeholder="联系人">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人电话</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="contacterPhone"
											id="inputPlace" placeholder="联系人电话">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人传真</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="contacterFax"
											id="inputPlace" placeholder="联系人传真">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人手机</label>
									<div class="col-sm-7">
										<input type="text" maxlength="11" class="form-control" name="contacterMobile"
											id="inputPlace" placeholder="联系人手机">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人邮箱</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="contacterEmail"
											id="inputPlace" placeholder="联系人邮箱">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">联系人地址</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="contacterAdd"
											id="inputPlace" placeholder="联系人地址">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">类别</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="type"
											id="inputPlace" placeholder="类别">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">等级</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="grade"
											id="inputPlace" placeholder="等级">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">领域1</label>
									<div class="col-sm-7">
										<textarea class="form-control" name="remark1" id="inputPlace"
											placeholder="领域1"></textarea>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">领域2</label>
									<div class="col-sm-7">
										<textarea class="form-control" name="remark2" id="inputPlace"
											placeholder="领域2"></textarea>
									</div>
								</div>
								<div class="form-group">
									<label for="inputPlace" class="col-sm-4 control-label">领域3</label>
									<div class="col-sm-7">
										<textarea class="form-control" name="remark3" id="inputPlace"
											placeholder="领域3"></textarea>
									</div>
								</div>
							</div>
							<input type="button" class="btn btn-lg btn-primary center-block"
								id="addSubmit" value="添&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加" />
						</form>
					</div>
					<div class="cdrf-person-list">
						<table class="table table-striped">
							<thead>
								<tr>

									<td>序号</td>
									<td>界别</td>
									<td>中文组织全称</td>
									<td>英文组织全称</td>
									<td>中文姓名</td>
									<td>英文全名</td>
									<td>性别</td>
									<td>证件号</td>
									<td>手机</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!empty requestScope.memberList}">
										<c:forEach items="${requestScope.memberList}" begin="0"
											step="1" varStatus="status" var="member">
											<tr>
												<td>${status.index + 1}</td>
												<td>${member.sector }</td>
												<td>${member.companyCn }</td>
												<td>${member.companyEn }</td>
												<td>${member.nameCn }</td>
												<td>${member.nameEn }</td>
												<td>${member.sex }</td>
												<td>${member.idNo }</td>
												<td>${member.mobile }</td>
												<td>
													<button value="${member.id }" class="btn btn-default editBtn editPersonBtnClass" id="editPersonBtn">编辑</button>
													<button value="${member.id }" class="btn btn-default deleteBtn delPersonBtnClass" id="delPersonBtn">删除</button>
												</td>
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
					<div class="hidden" id="importPerson">
						<form role="form" action="memberUpload" method="post" enctype="multipart/form-data">
							<div class="form-group">
								<label for="exampleInputFile">选择文件：</label> <input type="file"
									id="exampleInputFile" name="uploadfile">
								<p class="help-block">请选择你要导入的会议文件</p>
							</div>
							<input type="submit" id="importSubmitBtn"
								class="btn btn-default" value="导&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;入">
						</form>
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
	<script src="js/bootbox.js"></script>
	<script type="text/javascript">
		(function($, w) {
			var personManager = {};
			var addPersonBtn = $("#newPersonBtn"), submitPersonBtn = $('#addPersonSubmit'), importPersonBtn = $("#importPersonBtn"), 
				importPersonSubmitBtn = $("#importSubmitBtn"), personListWrap = $(".cdrf-person-list"), 
				addPersonFormWrap = $("#addPersonFormWrap"), importPersonWrap = $("#importPerson"), 
				meeting_menu = $("#meeting_menu"), member_menu = $("#member_menu"), setting_menu = $("#setting_menu"), 
				editBtn = $(".editBtn"),exportPersonBtn = $("#exportPersonBtn");
			function initData() {
				//todo
			}
			function deleteMember(id){
				//showModal("处理中。。。");
				//todo ajax delete meeting
				var formEle = document.createElement("form");
				document.body.appendChild(formEle);
				var inputEle = document.createElement("input");
				inputEle.type = "hidden";
				formEle.appendChild(inputEle);
				inputEle.name = "id";
				inputEle.value = id;
				formEle.action = "deleteMember";
				formEle.method = "post";
				formEle.submit();
			}
			function bindEvent() {
				//添加会议
				addPersonBtn.on("click", function(e) {
					personListWrap.hide();
					importPersonWrap.hide();
					addPersonFormWrap.removeClass("hidden");
					addPersonFormWrap.show();
				});
				//确认添加会议
				submitPersonBtn.on("click", function(e) {
					personListWrap.show();
					addPersonFormWrap.hide();
				});
				//导入会议
				importPersonBtn.on("click", function(e) {
					personListWrap.hide();
					addPersonFormWrap.hide();
					importPersonWrap.removeClass("hidden");
					importPersonWrap.show();
				});
				//确认导入会议
				importPersonSubmitBtn.on("click", function(e) {

				});
				exportPersonBtn.on("click",function(e){
					location.href="excel/exportMember?meetingname=中国基金会"; 
				});
				editBtn.on("click", function(e) {
					var formEle = document.createElement("form");
					document.body.appendChild(formEle);
					var inputEle = document.createElement("input");
					inputEle.type = "hidden";
					formEle.appendChild(inputEle);
					inputEle.name = "id";
					inputEle.value = $(e.target).attr("value");
					formEle.action = "editMember";
					formEle.method = "post";
					formEle.submit();
				});
				//删除按钮
				personListWrap.on("click",".deleteBtn",function(e){
					var memberId = $(e.target).attr("value");
					bootbox.confirm("确定删除？", function(result){
						if(result){
							deleteMember(memberId);
						}
					});
				});
				meeting_menu.on("click", function(e) {
					$("#query_form").attr("action","queyMeeting");
					$("#query_form").submit();
				});
				member_menu.on("click", function(e) {
					$("#query_form").attr("action","queyMember");
					$("#query_form").submit();
				});
				setting_menu.on("click", function(e) {
					$("#query_form").attr("action","queryUser");
					$("#query_form").submit();
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
				formEle.action = "queyMember";
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
