<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>接口自动化测试</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/xr.css">
</head>
<script type="text/javascript">
var errMsg = "${errMsg }";
if(errMsg != null && errMsg != ""){
	alert(errMsg);
}
</script>
<body>
	<div class="container">
		<form class="form-signin" role="form" action="login" method="post">
			<h2 class="form-signin-heading">Please sign in</h2>
			<input name="username" type="text" class="form-control"
				placeholder="请输入用户名" value="test" required autofocus> <p>
			<input type="password"  name="pwd" value="111111" class="form-control" placeholder="请输入密码" required><br>
<!-- 			<label class="checkbox"> <input type="checkbox" -->
<!-- 				value="remember-me"> 记住我 </label> -->
			<button class="btn btn-lg btn-primary btn-block" type="submit">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
		</form>
	</div>
</body>
</html>
