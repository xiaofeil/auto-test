<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<title>参数列表</title>
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
	var repeatMeetingRows = "<%=request.getParameter("repeatMeetingRows")%>";//"${repeatMeetingRows }";
	var nullMeetingNameRows = "<%=request.getParameter("nullMeetingNameRows")%>";//"${nullMeetingNameRows }";
	var nullMemberNameRows = "<%=request.getParameter("nullMemberNameRows")%>";//"${nullMemberNameRows }";
	if (errMsg != "null" && errMsg != null && errMsg != "") {
		alert(errMsg);
	}
	if (repeatMeetingRows != "null" && repeatMeetingRows != null && repeatMeetingRows != "") {
		alert(repeatMeetingRows);
	}
	if (nullMeetingNameRows != "null" && nullMeetingNameRows != null && nullMeetingNameRows != "") {
		alert(nullMeetingNameRows);
	}
	if (nullMemberNameRows != "null" && nullMemberNameRows != null && nullMemberNameRows != "") {
		alert(nullMemberNameRows);
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
				<div class="row xr-right-operation">
					<div class="col-md-12">
						<%--<button type="button" class="btn btn-default" id="newMeetingBtn">添加会议</button>--%>
						<button type="button" class="btn btn-default"
							id="importMeetingBtn">导入脚本</button>
						<%--<button type="button" class="btn btn-default"--%>
							<%--id="meetingQueryBtn">会议搜索</button>--%>
						<%--<button type="button" class="btn btn-default"--%>
							<%--id="importMeetingPersonBtn">导入参会人员</button>--%>
					</div>
					<div class="col-lg-6">
						<div class="input-group">
							
						</div>
						<!-- /input-group -->
					</div>
				</div>
				<div class="row xr-right-content">
					<div class="xr-meeting-list" id="meetingList">
						<table class="table table-striped">
							<thead>
								<tr>
									<td>序号</td>
									<td>配置文件名称</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!empty requestScope.fileDtoList}">
										<c:forEach items="${requestScope.fileDtoList}" begin="0"
											step="1" var="paramFile" varStatus="status">
											<tr>
												<td>${status.index + 1}</td>
												<td>${paramFile.fileName }</td>
												<td data-meeting='{"paramFileName":"${paramFile.fileName }"}'>
													<button value="${paramFile.fileName }" class="btn btn-default executeBtn" id="editMeetingBtn">执行</button>
													<button value="${paramFile.fileName }" class="btn btn-default editBtn" id="showResultBtn">查看结果</button>
													<button value="${paramFile.fileName }" class="btn btn-default downloadResultBtn" id="downloadResultBtn">下载结果</button>
													<button value="${paramFile.fileName }" class="btn btn-default deleteBtn delMeetingBtnClass" id="delMeetingBtn">删除</button>
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
								<li><a href="#" onclick="meetingManager.gotoPage(${currPageNo+1})">下一页</a>
								</li>
						   </c:when>
						   <c:when test="${moreFlag==1 && currPageNo!=1}">  
								<li><a href="#" onclick="meetingManager.gotoPage(${currPageNo-1})">上一页</a>
								</li>
								<li><a href="#" onclick="meetingManager.gotoPage(${currPageNo+1})">下一页</a>
								</li>
						   </c:when>
						   <c:when test="${moreFlag==0 && currPageNo!=1}">  
								<li><a href="#" onclick="meetingManager.gotoPage(${currPageNo-1})">上一页</a>
								</li>
								<li class="disabled"><a href="#">下一页</a>
								</li>
						   </c:when>
						   <c:otherwise> 
						   
						   </c:otherwise>
						</c:choose>
						</ul>
					</div>
					<div class="hidden" id="importMeeting">
						<form role="form" action="configFileUpload" method="post"
							enctype="multipart/form-data">
							<div class="form-group">
								<label for="exampleInputFile">选择文件：</label> <input type="file"
									id="exampleInputFile" name="uploadfile">
								<p class="help-block">请选择你要导入的文件</p>
							</div>
							<input type="submit" id="importSubmitBtn"
								class="btn btn-default" value="导&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;入" />
						</form>
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
			var addNewBtn = $("#newMeetingBtn"),submitBtn = $('#addSubmit'), importMeetingBtn = $("#importMeetingBtn"),importSubmitBtn = $("#importSubmitBtn"),
				meetingWrap = $(".xr-meeting-list"),addMeetingForm = $("#saveOrUpdateMeeting"),importMeeting = $("#importMeeting"),
				meeting_menu = $("#meeting_menu"), member_menu = $("#member_menu"), setting_menu = $("#setting_menu"),
				updateMeetingFormWrap = $("#saveOrUpdateMeeting"),meetingQueryBtn = $("#meetingQueryBtn"),
				importMeetingPersonBtn = $("#importMeetingPersonBtn");
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
			function editMeeting(fileName){
				//showModal("处理中。。。");
				//todo ajax delete meeting
				var formEle = document.createElement("form");
				document.body.appendChild(formEle);
				var inputEle = document.createElement("input");
				inputEle.type = "hidden";
				formEle.appendChild(inputEle);
				inputEle.name = "fileName";
				inputEle.value = fileName;
				formEle.action = "showExecuteResult";
				formEle.method = "post";
				formEle.submit();
			}
			function sendMeetingEmail(id){
				var formEle = document.createElement("form");
				document.body.appendChild(formEle);
				var inputEle = document.createElement("input");
				inputEle.type = "hidden";
				formEle.appendChild(inputEle);
				inputEle.name = "meeting_id";
				inputEle.value = id;
				formEle.action = "skipSendMeetingEmail";
				formEle.method = "post";
				formEle.submit();
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
				//添加会议
				addNewBtn.on("click",function(e){
					showWrap("saveOrUpdateMeeting");
					clearForm("saveOrUpdateMeeting");
				});
				//确认添加会议
				submitBtn.on("click",function(e){
					//meetingWrap.show();
					//addMeetingForm.hide();
				});
				//导入会议
				importMeetingBtn.on("click",function(e){
					showWrap("importMeeting");
				});
				//确认导入会议
				importSubmitBtn.on("click",function(e){
					
				});
				//会议查询
				meetingQueryBtn.on("click",function(e){
					showWrap("meetingQueryFormWrap");
				});
				//导入参会人员
				importMeetingPersonBtn.on("click",function(e){
					showWrap("importMeetingPerson");
				});
				
				//编辑按钮
				meetingWrap.on("click",".editBtn",function(e){
					var meetingId = $(e.target).attr("value");
					editMeeting(meetingId);
				});
				meetingWrap.on("click",".downloadResultBtn",function(e){
					var fileName = $(e.target).attr("value");
					location.href="excel/exportResult?fileName=" + fileName;
				});
				meetingWrap.on("click",".executeBtn",function(e){
					var fileName = $(e.target).attr("value");
					var formEle = document.createElement("form");
					document.body.appendChild(formEle);
					var inputEle = document.createElement("input");
					inputEle.type = "hidden";
					formEle.appendChild(inputEle);
					inputEle.name = "fileName";
					inputEle.value = fileName;
					formEle.action = "executeScript";
					formEle.method = "post";
					formEle.submit();
				});
				//发送邮件按钮
				meetingWrap.on("click",".sendEmailBtn",function(e){
					var meetingId = $(e.target).attr("value");
					sendMeetingEmail(meetingId);
				});
				//删除按钮
				meetingWrap.on("click",".deleteBtn",function(e){
					var meetingId = $(e.target).attr("value");
					bootbox.confirm("确定删除？", function(result){
						if(result){
							deleteMeeting(meetingId);
						}
					});
				});
				
				$(".showOrHide").on("click",function(e){
					$(this).parent().toggleClass("showMore");
					var temp = $(this).attr("data-value");
					$(this).attr("data-value",$(this).text());
					$(this).text(temp);
				});
				
				meeting_menu.on("click", function(e) {
					$("#query_form").attr("action","listall");
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
			meetingManager.gotoPage = function(pageNo){
				var formEle = document.createElement("form");
				document.body.appendChild(formEle);
				var inputEle = document.createElement("input");
				inputEle.type = "hidden";
				formEle.appendChild(inputEle);
				inputEle.name = "pageNo";
				inputEle.value = pageNo;
				formEle.action = "queyMeeting";
				//formEle.method = "post";
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
