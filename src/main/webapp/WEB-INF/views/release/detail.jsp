<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <!-- 중앙배치 container -->
    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>

    <c:if test="${release.user.id == principal.user.id}">
        <a href="/release/${release.id}/updateForm" class="btn btn-warning">수정</a>
        <button id="btn-delete" class="btn btn-danger">삭제</button>
    </c:if>
    <br/> <br/>
    <div>
        글 번호 : <span id="id"><i>${release.id} </i></span> 작성자 : <span><i>${release.user.username} </i></span>
    </div>
    <br/>
    <div>
        <h3>${release.title}</h3>
    </div>
    <hr/>
    <div>
        <div>${release.content}</div>
    </div>
    <hr/>

</div>


<script src="/js/release.js"></script>

<%@ include file="../layout/footer.jsp" %>
