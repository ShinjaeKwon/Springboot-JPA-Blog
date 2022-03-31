<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form action="/auth/loginProc" method="POST">
        <c:if test="${param.error != null }">
            <script type="text/javascript">
                alert("아이디 또는 비밀번호가 일치하지 않습니다.");
            </script>
        </c:if>
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" value="${username}" name="username" class="form-control" placeholder="Enter username" id="username">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
        </div>

        <button id="btn-login" class="btn btn-primary">로그인</button>
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=8583161e742685cda04a453456426b39&redirect_uri=http://localhost:9090/auth/kakao/callback&response_type=code">
            <img height="38px" src="/image/kakao_login_button.png"></a>
    </form>

</div>

<script src="/js/user.js"></script>


<%@ include file="../layout/footer.jsp" %>