<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<script>	
	$(document).ready(function(){ 
		$("#btn-save").click(function(){
				if($("#username").val().length==0){ alert("Username를 입력하세요."); $("#username").focus(); return false; }
				if($("#password").val().length==0){ alert("Password를 입력하세요."); $("#password").focus(); return false; }
				if($("#email").val().length==0){ alert("Email을 입력하세요."); $("#email").focus(); return false; }
			});		
	});
	</script>

	<form>
		<div class="form-group">
			<label for="username">Username</label> <input type="text" class="form-control" placeholder="Enter username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>

		<div class="form-group">
			<label for="email">Email</label> <input type="email" class="form-control" placeholder="Enter email" id="email">
		</div>
	</form>
	<button id="btn-save" class="btn btn-primary">회원가입완료</button>
</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>