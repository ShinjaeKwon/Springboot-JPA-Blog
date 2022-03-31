<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>


<div class="container">
    <form>
        <input type="hidden" id="state" value="${board.state}">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Enter Title" id="title">
        </div>

        <div class="form-group">
            <textarea class="form-control summernote" rows="5" id="content" >

                판매 물품 이름 : <br>
                사이즈 : <br>
                판매 가격 : <br>
                연락 가능 번호 : <br>
                직거래 유무 :  <br>
                물품 상태(1~10) : <br>
                물품 사진 :

            </textarea>
        </div>
    </form>
    <button id="btn-save" class="btn btn-primary">글쓰기완료</button>
</div>
<br>

<script>
    $('.summernote').summernote({
        tabsize: 2,
        height: 300
    });
</script>
<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp" %>