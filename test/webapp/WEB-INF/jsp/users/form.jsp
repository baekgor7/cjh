<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>

<%@ include file="../commons/_header.jspf" %>
</head>
<body>
	<%@ include file="../commons/_top.jspf" %>
	
	<div class="container">
		<div class="row">
			<div class="span12">
				<section id="typography" />
				<div class="page-header">					
				<c:choose>
					<c:when test="${empty user.userId}">
					<h1>회원가입</h1>
					</c:when>
					<c:otherwise>
					<h1>개인정보수정</h1>
					</c:otherwise>
				</c:choose>				
				</div>		
				
				<c:choose>
					<c:when test="${empty user.userId}">
						<c:set var="method" value="post" />
					</c:when>
					<c:otherwise>
						<c:set var="method" value="put" />
					</c:otherwise>
				</c:choose>
						
				<form:form modelAttribute="user" cssClass="form-horizontal" action="/users" method="${method}">
					<div class="control-group">
						<label class="control-label" for="userId">사용자 아이디</label>
						<div class="controls">
						<c:choose>
							<c:when test="${empty user.userId}">
							<form:input path="userId" />
							<form:errors path="userId" cssClass="error" />							
							</c:when>
							<c:otherwise>
							${userId}
							<form:hidden path="userId"/>
							</c:otherwise>
						</c:choose>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">비밀번호</label>
						<div class="controls">
							<form:password path="password"/>
							<form:errors path="password" cssClass="error" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="name">이름</label>
						<div class="controls">
							<form:input path="name"/>
							<form:errors path="name" cssClass="error" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="email">이메일</label>
						<div class="controls">
							<form:input path="email"/>
							<form:errors path="email" cssClass="error" />
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-promary">회원가입</button>
						</div>
					</div>				
				</form:form>
<!-- 				
				<form class="form-horizontal" action="/users" method="post">
					<div class="control-group">
						<label class="control-label" for="userId">사용자 아이디</label>
						<div class="controls">
							<input type="text" id="userId" name="userId" value="" placeholder="사용자 아이디" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">비밀번호</label>
						<div class="controls">
							<input type="password" id="password" name="password" placeholder="" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="name">이름</label>
						<div class="controls">
							<input type="text" id="name" name="name" value="" placeholder="" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="email">이메일</label>
						<div class="controls">
							<input type="text" id="email" name="email" value="" placeholder="" />
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-promary">회원가입</button>
						</div>
					</div>
				</form>
 -->			
			</div>
		</div>
	</div>
</body>
</html>