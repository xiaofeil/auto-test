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
var errMsg = "<%=request.getParameter("errMsg")%>";//"${errMsg }";
if (errMsg != "null" && errMsg != null && errMsg != "") {
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
						<li><a href="javascript:void(0);" id="member_menu">人员管理</a></li>
						<li  class="active"><a href="javascript:void(0);" id="setting_menu">权限设置</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-10">
				<div class="row cdrf-right-operation">
					<div class="col-md-6">
						<button type="button" class="btn btn-default" id="newUserBtn">添加用户</button>
						<button type="button" class="btn btn-default" id="roleManageBtn">角色管理</button>
					</div>
					<div class="col-lg-6"></div>
				</div>
				<div class="row cdrf-right-content">
					<div class="cdrf-user-list" id="userList">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>序号</th>
									<th>用户名</th>
									<th>手机</th>
									<th>邮箱</th>
									<th>角色</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!empty requestScope.userList}">
										<c:forEach items="${requestScope.userList}" begin="0"
											step="1" varStatus="status" var="user">
											<tr>
												<td>${status.index + 1}</td>
												<td>${user.username }</td>
												<td>${user.mobile }</td>
												<td>${user.email }</td>
												<td>${user.roles }</td>
												<td data-user='{"id":"${user.id }","username":"${user.username }","name":"${user.name }","mobile":"${user.mobile }","email":"${user.email }","roles":"${user.roles }","remark":"${user.remark }"}'>
													<button value="${user.id }" class="btn btn-default editUserBtn editUserBtnClass" id="editUserBtn">编辑</button>
													<button value="${user.id }" class="btn btn-default deleteUserBtn deleteUserBtnClass" id="deleteUserBtn">删除</button>
												</td>
											</tr>
										</c:forEach>
									</c:when>
								</c:choose>
							</tbody>
						</table>
					</div>
					<div class="hidden" id="userInfoWrap">
						<form role="form" class="form-horizontal" action="" method="post" id="userInfoForm">
							<input type="hidden" name="id" id="id" value="">
							<input type="hidden" name="roles" id="" class="roles" value="">
							<div class="col-lg-6">
								<div class="form-group">
									<label for="inputUserName" class="col-sm-4 control-label">帐号</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="username"
											id="inputUserName" placeholder="帐号">
									</div>
								</div>
								<div class="form-group">
									<label for="inputPwd" class="col-sm-4 control-label">密码</label>
									<div class="col-sm-7">
										<input type="password" class="form-control" name="pwd"
											id="inputPwd" placeholder="密码">
									</div>
								</div>
								<div class="form-group">
									<label for="inputName" class="col-sm-4 control-label">业务员姓名</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="name"
											id="inputName" placeholder="业务员姓名">
									</div>
								</div>
								<div class="form-group rolesWrap">
									<label for="inputType" class="col-sm-4 control-label">角色</label>
									<div class="col-sm-7">
										<c:choose>
											<c:when test="${!empty requestScope.roleList}">
												<c:forEach items="${requestScope.roleList}" begin="0"
													step="1" varStatus="status" var="role">
													<div class="checkbox">
														<label> <input type="checkbox" data-value="${role.roleName}"> ${role.roleName } </label>
													</div>
												</c:forEach>
											</c:when>
										</c:choose>
									</div>
								</div>
							</div>
							<div class="col-lg-6">
								<div class="form-group">
									<label for="inputMobile" class="col-sm-4 control-label">手机号</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="mobile"
											id="inputMobile" maxlength="11" placeholder="手机号">
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail" class="col-sm-4 control-label">邮箱</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="email"
											id="inputEmail" placeholder="邮箱">
									</div>
								</div>
								<div class="form-group">
									<label for="inputRemark" class="col-sm-4 control-label">备注</label>
									<div class="col-sm-7">
										<textarea class="form-control" name="remark" id="inputRemark"
											placeholder="备注"></textarea>
									</div>
								</div>
							</div>
						</form>
						<div class="col-lg-12 center-block">
							<div class="col-lg-5">
							</div>
							<div class="col-lg-7">
								<div class="form-group center-block">
									<button type="button"
										class="btn btn-lg btn-primary center-block"
										id="userSubmitBtn">保&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存</button>
								</div>
							</div>
						</div>
					</div>
<!-- 					角色相关 -->
					<div class="cdrf-role-list hidden" id="roleList">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>序号</th>
									<th>角色名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!empty requestScope.roleList}">
										<c:forEach items="${requestScope.roleList}" begin="0"
											step="1" varStatus="status" var="role">
											<tr>
												<td>${status.index + 1}</td>
												<td>${role.roleName }</td>
												<td>
													<button value="${role.roleName}" data-authList="${role.menuBtn }" class="btn btn-default editRoleBtn editRoleBtnClass" id="editRoleBtn">编辑</button>
													<button value="${role.roleName}" class="btn btn-default deleteRoleBtn deleteRoleBtnClass" id="deleteRoleBtn">删除</button>
												</td>
											</tr>
										</c:forEach>
									</c:when>
								</c:choose>
							</tbody>
						</table>
					</div>
					<div class="hidden" id="roleInfoWrap">
						<form role="form" class="form-horizontal" action="" method="post" id="roleInfoForm">
							<input type="hidden" name="menuBtn" id="menuBtn" value="">
							<div class="col-lg-6">
								<div class="form-group">
									<label for="inputRoleName" class="col-sm-4 control-label">角色名</label>
									<div class="col-sm-7">
										<input type="text" class="form-control" name="roleName"
											id="inputRoleName" placeholder="角色名">
									</div>
								</div>
								<div class="form-group">
									<label for="inputType" class="col-sm-4 control-label">菜单&按钮</label>
									<div class="col-sm-7">
										<c:choose>
											<c:when test="${!empty requestScope.menuList}">
												<c:forEach items="${requestScope.menuList}" begin="0"
													step="1" varStatus="menu_status" var="menu">
													<div class="menu-item" data-value="${menu.id }">
														<h3><b>${menu.node_name }</b></h3>
														<c:choose>
															<c:when test="${!empty menu.btnList}">
																<c:forEach items="${menu.btnList}" begin="0"
																	step="1" varStatus="btn_status" var="btn">
																	<div class="checkbox" style="margin-left: 2em;">
																		<label> <input data-value="${btn.btnId }" type="checkbox">${btn.btnName } </label>
																	</div>
																</c:forEach>
															</c:when>
														</c:choose>
													</div>
												</c:forEach>
											</c:when>
										</c:choose>
									</div>
								</div>
							</div>
						</form>
						<div class="col-lg-12 center-block">
							<div class="col-lg-5">
							</div>
							<div class="col-lg-7">
								<div class="form-group center-block">
									<button type="button"
										class="btn btn-lg btn-primary center-block"
										id="roleSubmitBtn">保&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存</button>
								</div>
							</div>
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
	<script src="js/bootbox.js"></script>
	<script src="js/bootstrap-treeview.js"></script>
	<script type="text/javascript">
		(function($, w) {
			var settingManager = {};
			var newUserBtn = $("#newUserBtn"),roleManageBtn = $("#roleManageBtn"), userSubmitBtn = $("#userSubmitBtn"),roleSubmitBtn = $("#roleSubmitBtn"), 
				userListWrap = $(".cdrf-user-list"), roleListWrap = $(".cdrf-role-list"), 
				userInfoWrap = $("#userInfoWrap"),roleInfoWrap = $("#roleInfoWrap"), meeting_menu = $("#meeting_menu"), 
				member_menu = $("#member_menu"), setting_menu = $("#setting_menu");
			function initData() {
				//todo
			}
			function deleteUser(id){
				var formEle = document.createElement("form");
				document.body.appendChild(formEle);
				var inputEle = document.createElement("input");
				inputEle.type = "hidden";
				formEle.appendChild(inputEle);
				inputEle.name = "id";
				inputEle.value = id;
				formEle.action = "deleteUser";
				formEle.method = "post";
				formEle.submit();
			}
			function deleteRole(roleName){
				var formEle = document.createElement("form");
				document.body.appendChild(formEle);
				var inputEle = document.createElement("input");
				inputEle.type = "hidden";
				formEle.appendChild(inputEle);
				inputEle.name = "roleName";
				inputEle.value = roleName;
				formEle.action = "deleteRole";
				formEle.method = "post";
				formEle.submit();
			}
			function bindEvent() {
				//添加用户
				newUserBtn.on("click", function(e) {
					userListWrap.hide();
					userInfoWrap.removeClass("hidden");
					userInfoWrap.show();
					roleListWrap.hide();
					roleInfoWrap.hide();
				});
				//确认添加角色
				roleSubmitBtn.on("click", function(e) {
					if($("#inputRoleName").val() == "" || $("#inputRoleName").val() == null){
						alert("角色名为空");
						return;
					}
					//获取选择的权限
					var authList = [];
					$("#roleInfoForm .menu-item").each(function(index,ele){
						var menuItemArr = [];
						var checkedInputs = $(ele).find("input:checked");
						if(checkedInputs.length != 0){
						    menuItemArr.push($(ele).attr("data-value"));
						    checkedInputs.each(function(index,input){
						    	menuItemArr.push($(input).attr("data-value"));
							});
						}
						if(menuItemArr.length>0){
							authList.push(menuItemArr.join("@"));
						}
					});
					//set value
					$("#roleInfoForm input[name='menuBtn']").val(authList.join(";"))
// 					alert("menuBtn=" + $("#menuBtn").val());
					$("#roleInfoForm").attr("action","saveOrUpdateRole");
					$("#roleInfoForm").submit();
				});
				//确认添加用户
				userSubmitBtn.on("click", function(e) {
					if($("#inputUserName").val() == "" || $("#inputUserName").val() == null){
						alert("帐号为空");
						return;
					}
					if($("#inputPwd").val() == "" || $("#inputPwd").val() == null){
						alert("密码为空");
						return;
					}
					
					var rolesArr = [];
					$("#userInfoForm .rolesWrap input:checked").each(function(index,ele){
						rolesArr.push($(ele).attr("data-value"));
					});
					
					$("#userInfoForm input.roles").val(rolesArr.join(";"));
					
					$("#userInfoForm").attr("action","saveOrUpdateUser");
					$("#userInfoForm").submit();
// 					userListWrap.show();
// 					userInfoWrap.hide();
// 					roleListWrap.hide();
// 					roleInfoWrap.hide();
				});
				//编辑用户
				userListWrap.on("click",".editUserBtn",function(e){
					var data = $(e.target).parent().attr("data-user");
					data = JSON.parse(data);
					//设置对应的input元素的值
					for(var name in data){
						if(name == "roles"){
							var roles = data[name];
							var roleArr = roles.split(";");
							for(var i=0;i<roleArr.length;i++){
								$("input[data-value='"+roleArr[i]+"']").attr("checked",true);
							}
						}
						$("#userInfoWrap").find("[name='"+name+"']").val(data[name]);
					}
					$("#inputPwd").val("!@#$%^&*()!QAZxsw2");
					userListWrap.hide();
					userInfoWrap.removeClass("hidden");
					userInfoWrap.show();
					roleListWrap.hide();
					roleInfoWrap.hide();
				});
				//删除用户
				userListWrap.on("click",".deleteUserBtn",function(e){
					var userId = $(e.target).attr("value");
					bootbox.confirm("确定删除？", function(result){
						if(result){
							deleteUser(userId);
						}
					});
				});
				//删除角色
				roleListWrap.on("click",".deleteRoleBtn",function(e){
					var roleName = $(e.target).attr("value");
					bootbox.confirm("确定删除？", function(result){
						if(result){
							deleteRole(roleName);
						}
					});
				});
				//编辑角色
				roleListWrap.on("click",".editRoleBtn",function(e){
					$("#roleInfoForm .menu-item input:checked").prop("checked",false);
					var roleName = $(e.target).attr("value");
					var authList = $(this).attr("data-authList");
					var menuItems = authList.split(";");
					for(var i=0;i<menuItems.length;i++){
						var items = menuItems[i].split("@");
						for(var j=1;j<items.length;j++){
							$("#roleInfoForm .menu-item[data-value='"+items[0]+"']").find("input[data-value='"+items[j]+"']").prop("checked",true);
						}
					}
					$("#inputRoleName").val(roleName); 
				});
				//角色管理
				roleManageBtn.on("click", function(e) {
					userListWrap.hide();
					userInfoWrap.hide();
					roleListWrap.removeClass("hidden");
					roleListWrap.show();
					roleInfoWrap.removeClass("hidden");
					roleInfoWrap.show();
				});
				roleSubmitBtn.on("click", function(e) {
					userListWrap.hide();
					userInfoWrap.hide();
					roleListWrap.removeClass("hidden");
					roleListWrap.show();
					roleInfoWrap.removeClass("hidden");
					roleInfoWrap.show();
				});
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
			settingManager.init = function() {
				//init data
				initData();
				//bind event
				bindEvent();
			}
			
			w.settingManager = settingManager;
		})($, window);

		settingManager.init();
	</script>
</body>
</html>