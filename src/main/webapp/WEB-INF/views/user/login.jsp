<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="로그인 - 도서관">
    <form action="/login-process" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="mb-3">
            <label class="form-label">이메일</label>
            <input type="text" name="username" value="${signupForm.username}"
                   class="form-control ${not empty fields.errors('username') ? 'is-invalid' : ''}" required>
                <%-- 에러 발생 시 메시지 출력 --%>
            <div class="invalid-feedback">
                <c:forEach items="${fields.errors('username')}" var="err">${err}</c:forEach>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">비밀번호</label>
            <input type="password" name="password"
                   class="form-control ${not empty fields.errors('password') ? 'is-invalid' : ''}" required>
            <div class="invalid-feedback">
                    ${fields.errors('password')[0]}
            </div>
        </div>

            <%-- 글로벌 에러 --%>
        <c:if test="${not empty fields.globalErrors()}">
            <div class="alert alert-danger">
                    ${fields.globalErrors()[0]}
            </div>
        </c:if>

        <button type="submit" class="btn btn-primary w-100 py-2 fw-bold">가입하기</button>
    </form>
</t:layout>