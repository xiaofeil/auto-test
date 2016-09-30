<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>edit meeting email</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/cdrf.css">
<style>
	.mce-i-alignleft:before {
	    content: "\e003"
	}
</style>
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
			<h1>&nbsp;中国发展基金会会议管理系统</h1>
		</div>
	</div>
	<div class="cdrf-content send-emails">
		<div class="row">
			<div class="col-md-2">
				<div class="cdrf-left-nav bs-sidebar affix">
					<ul class="nav nav-pills nav-stacked">
						<li  class="active"><a href="javascript:void(0);" id="meeting_menu">会议管理</a></li>
						<li><a href="javascript:void(0);" id="member_menu">人员管理</a></li>
						<li><a href="javascript:void(0);" id="setting_menu">权限设置</a></li>
					</ul>
				</div>

			</div>
			<div class="col-md-10">
				<div class="row cdrf-right-content">
					<div id="editMeetingEmail">
						<div class="cdrf-email-list">
							<table class="table table-striped">
								<thead>
									<tr>
										<td>序号</td>
										<td>邮件主题</td>
										<td>创建时间</td>
										<td>发送时间</td>
										<td>发送状态</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${!empty requestScope.emailList}">
											<c:forEach items="${requestScope.emailList}" begin="0"
												step="1" varStatus="status" var="email">
												<tr data-id="${email.id}" data-subject="${email.email_subject}" data-content='${email.email_content }'>
													<td>${status.index + 1}</td>
													<td>${email.email_subject }</td>
													<td>${email.create_time }</td>
													<td>${email.send_time }</td>
													<td>${email.status }</td>
													<td data-email='{"id":"${email.id}","email_subject":"${email.email_subject }","email_content":"${email.email_content }"}'>
														<button value="${email.id }" type="button" class="btn btn-default deleteEmailBtn deleteEmailBtnClass" id="deleteEmailBtn">删除</button>
													</td>
												</tr>
											</c:forEach>
										</c:when>
									</c:choose>
								</tbody>
							</table>
						</div>
						<iframe id="form_target" name="form_target" style="display:none"></iframe>
						<form id="my_form" action="imageUpload" target="form_target" method="post"
							enctype="multipart/form-data"
							style="width:0px;height:0;overflow:hidden">
							<input name="image" id="image" type="file" onchange="getFileName();">
						</form>
					
						<form id="tinymce_form" method="post" action="" class="col-md-12 form-horizontal">
							<input type="hidden" class="form-control" name="meeting_id"
															id="meeting_id" value="${meeting_id }" >
							<input type="hidden" class="form-control" name="email_content"
															id="email_content" >
							<input type="hidden" class="form-control" name="id"
															id="id" >
							<div class="form-group">
								<label for="inputEmailSubject" class="col-sm-2 control-label">邮件主题</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" name="email_subject"
										id="inputEmailSubject" placeholder="邮件主题" data-requirenull data-info="请输入邮件主题">
								</div>
							</div>
							<div class="form-group">
								<label for="inputTestRecipient" class="col-sm-2 control-label">测试收件人</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" name="test_recipient"
										id="inputTestRecipient" placeholder="测试收件人">
								</div>
							</div>
							<div class="form-group">
								<label for="emailContent" class="control-label">邮件正文</label>
								<div class="">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="tinymceContainer">
		<textarea id="emailContent" name="emailContent" style="width:100%"></textarea>
		<div class="row">
			<div class="col-md-6"><button type="button" style="margin: 20px auto;" class="authButton btn btn-lg btn-primary pull-right " id="saveEmailBtn" value="">保&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存</button>
			</div>
			<div class="col-md-6"><button type="button" style="margin: 20px auto;" class="authButton btn btn-lg btn-primary pull-left" id="sendEmailBtn" value="">发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送</button>
		</div>
			</div>
		</div>
							
</body>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootbox.js"></script>
<!-- <script src="tinymce/js/tinymce/classes/util/JSON.js"></script> -->
<script src="js/ajaxfileupload.js"></script>
<script language="javascript" type="text/javascript"
	src="tinymce/js/tinymce/tinymce.js"></script>
<script language="javascript" type="text/javascript"
	src="tinymce/js/tinymce/jquery.tinymce.min.js"></script>
<script language="javascript" type="text/javascript"
	src="tinymce/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript">
	tinymce.init({
		selector : "#emailContent",
		width : 900,
		height : 400,
		//     	theme : "advanced",
		relative_urls : false,
		plugins : [
				"advlist autolink autosave link image lists charmap print preview hr anchor pagebreak",
				"searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime nonbreaking",
				"table contextmenu directionality emoticons textcolor paste fullpage textcolor" ],
		file_browser_callback: function(field_name, url, type, win) {
	        if(type=='image') $('#my_form input').click();
	    },
		toolbar1 : "undo redo | cut copy paste | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect",
		toolbar2 : " searchreplace | bullist numlist | outdent indent blockquote | link unlink anchor image code | inserttime preview | forecolor backcolor",
		toolbar3 : "table | hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | visualchars visualblocks nonbreaking pagebreak restoredraft",

		menubar : false,
		toolbar_items_size : 'small',

		style_formats : [ {
			title : 'Bold text',
			inline : 'b'
		}, {
			title : 'Red text',
			inline : 'span',
			styles : {
				color : '#ff0000'
			}
		}, {
			title : 'Red header',
			block : 'h1',
			styles : {
				color : '#ff0000'
			}
		}, {
			title : 'Example 1',
			inline : 'span',
			classes : 'example1'
		}, {
			title : 'Example 2',
			inline : 'span',
			classes : 'example2'
		}, {
			title : 'Table styles'
		}, {
			title : 'Table row 1',
			selector : 'tr',
			classes : 'tablerow1'
		} ],

		templates : [ {
			title : 'Test template 1',
			content : 'Test 1'
		}, {
			title : 'Test template 2',
			content : 'Test 2'
		} ],
		language : 'zh_CN'
	});
	
    function getFileName() {
		var imagePath = $("#image").val();
    	imagePath = imagePath.replace("C:\\fakepath\\","/cdrf-meeting/image/");
      	$(".mce-textbox").eq(0).val(imagePath);
		ajaxFileUpload();
// 		tinyMCE.execCommand("mceReplaceContent",false,"<img src=/cdrf-meeting/image/icon.jpg>");
// 		var content = tinyMCE.activeEditor.getContent();
// 		content = content.replace("C:\\fakepath\\","/cdrf-meeting/image/");
// 		tinyMCE.activeEditor.setContent(content);
		// 获取编辑器内容
// 		alert("content=[" + tinyMCE.activeEditor.getContent()+"]");
    }
    
    function ajaxFileUpload() {  
        $.ajaxFileUpload({  
            url : '/cdrf-meeting/imageUpload',// servlet请求路径  
            secureuri : false,  
            fileElementId : 'image',// 上传控件的id  
            dataType : 'json',  
            type : "POST",
            data : null, // 其它请求参数  
            success : function(data, status) {
//             	console.info("success=" + data);  
            },  
            error : function(data, status, e) {
//             	console.info("error=" + data);  
            }  
        });   
    }  
    
    (function($, w) {
		var meetingEamilManager = {};
		var meeting_menu = $("#meeting_menu"), member_menu = $("#member_menu"), setting_menu = $("#setting_menu"),
		saveEmailBtn = $("#saveEmailBtn"),sendEmailBtn = $("#sendEmailBtn");
		function initData(){
			//todo
		}
		function bindEvent(){
			meeting_menu.on("click", function(e) {
				location.href = "queyMeeting";
			});
			member_menu.on("click", function(e) {
				location.href = "queyMember";
			});
			setting_menu.on("click", function(e) {
				location.href = "queryUser";
			});
			saveEmailBtn.on("click", function(e) {
				var subject = $("#inputEmailSubject").val();
				if(!subject){
					alert("请输入邮件主题");
					return;
				}
				var email_content = tinyMCE.activeEditor.getContent();
				if(email_content == "<!DOCTYPE html><html><head></head><body></body></html>"){
					alert("请输入邮件正文");
					return;
				}
				$("#email_content").val(email_content);
				$("#tinymce_form").attr("action","saveOrUpdateEmail");
				$("#tinymce_form").submit();
			});
			sendEmailBtn.on("click", function(e) {
				var subject = $("#inputEmailSubject").val();
				if(!subject){
					alert("请输入邮件主题");
					return;
				}
				var email_content = tinyMCE.activeEditor.getContent();
				if(email_content == "<!DOCTYPE html><html><head></head><body></body></html>"){
					alert("请输入邮件正文");
					return;
				}
				$("#email_content").val(email_content);
				$("#tinymce_form").attr("action","sendEmail");
				$("#tinymce_form").submit();
			});
			
			$(".cdrf-email-list table").on("click","tr",function(e){
				var target = $(e.target);
				if(target.hasClass("deleteEmailBtn")){
					var emailId = target.attr("value");
					bootbox.confirm("确定删除？", function(result){
						if(result){
							deleteEmail(emailId);
						}
					});
				}
				var email_id = $(this).attr("data-id");
				var emailSubject = $(this).attr("data-subject");
				var emailContent = $(this).attr("data-content");
				if(emailSubject){
					$("#inputEmailSubject").val(emailSubject);
				}
				if(emailContent){
					tinyMCE.activeEditor.setContent(emailContent);
				}
				if(email_id){
					$("#id").val(email_id);
				}
			});
		}
		function deleteEmail(emailId){
			var formEle = document.createElement("form");
			document.body.appendChild(formEle);
			var inputEleEmail = document.createElement("input");
			inputEleEmail.type = "hidden";
			var inputEleMeeting = document.createElement("input");
			inputEleMeeting.type = "hidden";
			formEle.appendChild(inputEleEmail);
			formEle.appendChild(inputEleMeeting);
			inputEleEmail.name = "id";
			inputEleEmail.value = emailId;
			inputEleMeeting.name = "meeting_id";
			inputEleMeeting.value = $("#meeting_id").val();
			formEle.action = "deleteMeetingEmail";
			formEle.method = "post";
			formEle.submit();
		}
		
		meetingEamilManager.init = function() {
			//init data
			initData();
			//bind event
			bindEvent();
		};

		w.meetingEamilManager = meetingEamilManager;
	})($, window);

	meetingEamilManager.init();
</script>
</html>
