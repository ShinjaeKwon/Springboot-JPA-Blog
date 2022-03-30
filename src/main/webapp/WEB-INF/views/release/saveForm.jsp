<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Enter Title" id="title">
        </div>

        <div class="form-group">
            <textarea class="form-control summernote" rows="5" id="content"></textarea>
        </div>
    </form>
    <button id="btn-save" class="btn btn-primary">글쓰기완료</button>
    <c:choose>
        <c:when test="${principal.user.role == 'ADMIN' }">
            <button id="btn-release-update" class="btn btn-warning">발매정보 업데이트</button>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
</div>
<br>

<script>
    $('.summernote').summernote({
        tabsize: 2,
        height: 300
    });
</script>
<script src="/js/release.js"></script>

<%@ include file="../layout/footer.jsp" %>