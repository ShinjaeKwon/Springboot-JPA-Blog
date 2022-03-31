<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="layout/header.jsp" %>

<div class="container">
    <h2>거래 게시판</h2><br><br>

    <c:forEach var="board" items="${boards.content}">
        <div class="card m-2">
            <div class="card-body">
                <h4 class="card-title">${board.title}</h4>

                <c:choose>
                    <c:when test="${board.user.role == 'USER'}">
                        <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
                        <c:choose>
                            <c:when test="${board.state == 1}">
                                <img height="38px" src="/image/selled.png">
                            </c:when>
                            <c:otherwise>
                                <img height="38px" src="/image/sell.png">
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <a href="/board/${board.id}" class="btn btn-warning">상세보기</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:forEach>


</div>
<ul class="pagination justify-content-center">
    <c:choose>
        <c:when test="${boards.first}">
            <li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
        </c:when>
        <c:otherwise>
            <li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${boards.last}">
            <li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
        </c:when>
        <c:otherwise>
            <li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
        </c:otherwise>
    </c:choose>
</ul>
<div class="container">
    <form action="/search" class="needs-validation" novalidate method="get">
        <div class="form-group">
            <input name="keyword" type="text" class="form-control" placeholder="게시글 검색어를 입력해주세요." required>
            <div class="valid-feedback"></div>
            <div class="invalid-feedback">게시글 검색어를 입력해주세요.</div>
        </div>
        <button type="submit" class="btn btn-primary">검색</button>
    </form>
</div>

<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>

<%@ include file="layout/footer.jsp" %>
