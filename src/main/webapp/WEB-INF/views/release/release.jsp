
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	
	<c:forEach var="release" items="${release.content}">
		<div class="card m-2"> 
			<div class="card-body">
				<h4 class="card-title">${release.title}</h4>
				<a href="/release/${release.id}" class="btn btn-primary">발매정보보기</a>
				
			</div>
		</div>
	</c:forEach>
</div>
<ul class="pagination justify-content-center">
	<c:choose>
		<c:when test="${release.first}">
		<li class="page-item disabled"><a class="page-link" href="?page=${release.number-1}">Previous</a></li>
		</c:when>
		<c:otherwise>
		<li class="page-item"><a class="page-link" href="?page=${release.number-1}">Previous</a></li>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${release.last}">
		<li class="page-item disabled"><a class="page-link" href="?page=${release.number+1}">Next</a></li>
		</c:when>
		<c:otherwise>
		<li class="page-item"><a class="page-link" href="?page=${release.number+1}">Next</a></li>
		</c:otherwise>
	</c:choose>
	<c:if test="${principal.user.role == 'ADMIN'}">
		<a href="/release/saveForm">발매정보 글쓰기</a>
	</c:if>
	
	
</ul>

<%@ include file="../layout/footer.jsp"%>
