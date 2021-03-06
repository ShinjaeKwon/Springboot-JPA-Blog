<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form>
        <input type="hidden" id="id" value="${board.id} "/>
        <input type="hidden" id="state" value="${board.state}">
        <div class="form-group">
            <input value="${board.title} " type="text" class="form-control" placeholder="Enter Title" id="title">
        </div>

        <div class="form-group">
            <textarea class="form-control summernote" rows="5" id="content">${board.content}</textarea>
        </div>
    </form>
    <button id="btn-update" class="btn btn-primary">글수정완료</button>
    <button id="btn-sell" class="btn btn-primary">판매완료로 변경</button>
</div>

<script>
    $('.summernote').summernote({
        tabsize: 2,
        height: 300
    });
</script>
<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp" %>
