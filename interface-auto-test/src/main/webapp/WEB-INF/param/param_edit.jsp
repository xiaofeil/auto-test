<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<title>中国发展基金会会议管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/xr.css">
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
</script>
<body>
	<div class="navbar navbar-fixed-top xr-header">
		<div class="container">
			<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;接口自动化测试</h1>
		</div>
	</div>
	<div class="xr-content">
		<div class="row">
			<div class="col-md-2">
				<div class="xr-left-nav bs-sidebar affix">
					<ul class="nav nav-pills nav-stacked">
						<li  class="active"><a href="javascript:void(0);" id="meeting_menu">会议管理</a></li>
						<li><a href="javascript:void(0);" id="member_menu">人员管理</a></li>
						<li><a href="javascript:void(0);" id="setting_menu">权限设置</a></li>
					</ul>
				</div>

			</div>
			<div class="col-md-10">
				<div class="row xr-right-content">
					<div id="saveOrUpdateMeeting">
						<div class="xr-person-list">
								<table class="table table-striped">
									<thead>
										<tr>
											<td>序号</td>
											<td>URL</td>
											<td>参数</td>
											<td>请求方式</td>
											<td>期望结果</td>
											<td>是否成功</td>
											<td>HTTP返回码</td>
											<td>异常描述</td>
										</tr>
									</thead>
									<tbody>
									<c:choose>
										<c:when test="${!empty requestScope.executeResultDtos}">
											<c:forEach items="${requestScope.executeResultDtos}" begin="0"
												step="1" varStatus="status" var="member">
												<tr>
													<td>${status.index + 1}</td>
													<td>${member.url }</td>
													<td>${member.params }</td>
													<td>${member.request_method }</td>
													<td>${member.expect_result }</td>
													<td>${member.pass_flag }</td>
													<td>${member.status_code }</td>
													<td>${member.error_msg }</td>
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
										<li><a href="#" onclick="meetingManager.gotoPage(${currPageNo+1},${meetingInfo.id })">下一页</a>
										</li>
								   </c:when>
								   <c:when test="${moreFlag==1 && currPageNo!=1}">  
										<li><a href="#" onclick="meetingManager.gotoPage(${currPageNo-1},${meetingInfo.id })">上一页</a>
										</li>
										<li><a href="#" onclick="meetingManager.gotoPage(${currPageNo+1},${meetingInfo.id })">下一页</a>
										</li>
								   </c:when>
								   <c:when test="${moreFlag==0 && currPageNo!=1}">  
										<li><a href="#" onclick="meetingManager.gotoPage(${currPageNo-1},${meetingInfo.id })">上一页</a>
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

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> -->
					<h4 class="modal-title" id="myModalLabel">操作提示</h4>
				</div>
				<div class="modal-body">
					
				</div>
				<div class="modal-footer" style="display: none;">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary">确认</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!-- <script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script> -->
	<script src="js/jquery.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootbox.js"></script>
	<%--<script src="js/tag.js"></script>--%>
	<script type="text/javascript">
		(function($, w) {
			var meetingManager = {};
			var meeting_menu = $("#meeting_menu"), member_menu = $("#member_menu"), setting_menu = $("#setting_menu"),
				updateMeetingFormWrap = $("#saveOrUpdateMeeting");
			function initData(){
				//todo
			}
			function showModal(content,showButton){
				$("#myModal").modal({
					keyboard :true
				});
				$(".modal-body").html("<h4>"+content+"</h4>");
				if(showButton){
					$(".modal-footer").show();
				}
			}
			function hideModal(){
				$('#myModal').modal('hide');
			}
			
			function deleteMeeting(id){
				//showModal("处理中。。。");
				//todo ajax delete meeting
				var formEle = document.createElement("form");
				document.body.appendChild(formEle);
				var inputEle = document.createElement("input");
				inputEle.type = "hidden";
				formEle.appendChild(inputEle);
				inputEle.name = "id";
				inputEle.value = id;
				formEle.action = "deleteMeeting";
				formEle.method = "post";
				formEle.submit();
			}
			function deleteMember(memberId, meetingId){
				var formEle = document.createElement("form");
				document.body.appendChild(formEle);
				var inputEleMember = document.createElement("input");
				inputEleMember.type = "hidden";
				var inputEleMeeting = document.createElement("input");
				inputEleMeeting.type = "hidden";
				formEle.appendChild(inputEleMember);
				formEle.appendChild(inputEleMeeting);
				inputEleMember.name = "memberId";
				inputEleMember.value = memberId;
				inputEleMeeting.name = "meetingId";
				inputEleMeeting.value = meetingId;
				formEle.action = "deleteMeetingRela";
				formEle.method = "post";
				formEle.submit();
			}
			function updateMeeting(formWrapId){
				showModal("处理中。。。");
				var from = $("#" + formWrapId).find("form");
				//todo ajax update meeting 
			}
			function editMeeting(data){
				
				//设置对应的input元素的值
				for(var name in data){
					$("#saveOrUpdateMeeting").find("[name="+name+"]").val(data[name]);
				}
				submeeting({el:'#submeeting'});
				$(".showOrHide").show();
				$("#saveOrUpdateMeeting").find(".submeetingWrap").addClass("showMore");
				//绑定change事件
				/* 
				$("#updateMeetingForm").on("change","input",function(e){
					$("#updateSubmitBtn").removeClass("hidden");
				});
				*/
			}
			function showWrap(id){
				var wrapArr = ['meetingList','saveOrUpdateMeeting','importMeeting','meetingQueryFormWrap','importMeetingPerson'];
				var hasHidden = $("#" + id).hasClass("hidden");
				if(hasHidden){
					$("#" + id).removeClass("hidden");
				}
				$(wrapArr).each(function(index,ele){
					if(ele !== id){
						$("#" + ele).hide();
					}else{
						$("#" + id).show();
					}
				});
			}
			
			function clearForm(id){
				$("#" + id + " form")[0].reset();
				if(id == "saveOrUpdateMeeting"){
					submeeting({el:'#submeeting'});
					$(".showOrHide").hide();
				}
			}
			
			function bindEvent(){
				$(".showOrHide").on("click",function(e){
					$(this).parent().toggleClass("showMore");
					var temp = $(this).attr("data-value");
					$(this).attr("data-value",$(this).text());
					$(this).text(temp);
				});
				
				meeting_menu.on("click", function(e) {
					location.href = "listall";
				});
				member_menu.on("click", function(e) {
					location.href = "queyMember";
				});
				setting_menu.on("click", function(e) {
					location.href = "queryUser";
				});
				
				$(".xr-person-list table").on("click","tr",function(e){
					var target = $(e.target);
					if(target.hasClass("deleteMemberBtn")){
						var memberId = target.attr("value");
						var meetingId = $("#id").val();
						bootbox.confirm("确定删除？", function(result){
							if(result){
								deleteMember(memberId,meetingId);
							}
						});
// 						return;
					}
					var meetingId = $("#id").val();  //meeting id
					var id = $(this).attr("data-id");
					var data_name = $(this).attr("data-name");
					if(data_name){
						$("#submeeting_name").html("【" + data_name + "】");
					}
					if(id){
						$.ajax({
							url : "/xr-meeting/queryMemberMeetingRela",
							data : {
								meetingId : meetingId,
								memberId : id
							},
							dataType : "json",
							type : "POST",
							success: function(result){
								console.info(result);
								$("#meetingRela").show();
								var relaList = result.relaList[0];
								for(var item in relaList){
									console.info(relaList[item]);
									//处理分会场信息
									if(item == "propList"){
										var propList = relaList[item];
										var html = "";
										var selected = [];
										for(var i=0;i<propList.length;i++){
											html += propList[i].flag == "1" ? '<label class="selected"><input data-id="'+propList[i].id+'" type="checkbox" checked>'+propList[i].prop+'</label>' : '<label><input data-id="'+propList[i].id+'" type="checkbox">'+propList[i].prop+'</label>';
											if(propList[i].flag == "1"){
												selected.push(propList[i].id);
											}
										}
										$(".xr-propList").empty().html(html);
										$('input[name="propList"]').val(selected.join(";"));
									}else{
										$('#meetingRela input[name="'+item+'"]').val(relaList[item]);
									}
								}
								
								//select element init
								var selectEle = ["privatePlaneFlag","airportDropOffFlag","levavePrivatePlanePlag","stayFishingTerraceFlag","smokelessFlag"];
								for(var j=0;j<selectEle.length;j++){
									$('#meetingRela select[name="'+selectEle[j]+'"] option').each(function(index,ele){
										if($(ele).val() === relaList[selectEle[j]]){
											$(ele).prop("selected",true);
										}
									});
								}
								
								//修改分会场信息
								$(".xr-propList input").on("click",function(e){
									var selectValue = [];
									var checkedInput = $(".xr-propList input:checked");
									for(var j=0;j<checkedInput.length;j++){
										selectValue.push($(checkedInput[j]).attr("data-id"));
									}
									$('input[name="selectSubMeeting"]').val(selectValue.join(";"));
								});
								
							},
							error: function(error){
								alert(error.message);
							}
						});
					}
				});
			}
			meetingManager.gotoPage = function(pageNo,meetingId){
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
				inputEle.value = meetingId;
				formEle.action = "editMeeting";
				formEle.method = "post";
				formEle.submit();
			}
			meetingManager.init = function() {
				//init data
				initData();
				//bind event
				bindEvent();
			};

			w.meetingManager = meetingManager;
		})($, window);

		meetingManager.init();
	</script>
</body>
</html>
