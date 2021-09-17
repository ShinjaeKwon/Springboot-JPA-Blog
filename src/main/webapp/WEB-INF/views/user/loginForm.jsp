<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<script>	
	$(document).ready(function(){ 
		$("#btn-login").click(function(){
				if($("#username").val().length==0){ alert("UserName을 입력하세요."); $("#username").focus(); return false; }
				if($("#password").val().length==0){ alert("Password를 입력하세요."); $("#password").focus(); return false; }
			});		
	});
	</script>

	<form action="/auth/loginProc" method="POST">
		<div class="form-group">
			<label for="username">Username</label> <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password</label> <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>

		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=8583161e742685cda04a453456426b39&redirect_uri=http://localhost:9090/auth/kakao/callback&response_type=code
		"><img height="38px"
			src="/image/kakao_login_button.png"></a>
	</form>

</div>

<script src="/js/user.js"></script>


<%@ include file="../layout/footer.jsp"%>