<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="로그인 - 도서관">
    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value='/css/user/user.css' />">
    </jsp:attribute>
    <jsp:body>
        <div class="login-wrapper py-5">
            <div class="text-center mb-5">
                <h2 class="fw-bold text-dark">Welcome Back</h2>
                <p class="text-muted">도서관 서비스를 이용하려면 로그인하세요.</p>
            </div>

            <form:form modelAttribute="loginForm" action="/login-process" method="POST">
                <div class="form-floating mb-3">
                    <form:input path="username"
                                id="username"
                                class="form-control border-0 bg-light"
                                placeholder="아이디"
                                required="true"
                    />
                    <label for="username" class="text-muted">아이디</label>
                    <form:errors path="username" cssClass="text-danger small mt-1 d-block" />
                </div>

                <div class="form-floating mb-3">
                    <form:password path="password"
                                   id="password"
                                   class="form-control border-0 bg-light"
                                   placeholder="비밀번호"
                                   required="true"
                    />
                    <label for="password" class="text-muted">비밀번호</label>
                    <form:errors path="password" cssClass="text-danger small mt-1 d-block" />
                </div>

                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="rememberMe" name="remember-me">
                        <label class="form-check-label small text-muted" for="rememberMe">로그인 상태 유지</label>
                    </div>
                    <a href="/find-pw" class="small text-decoration-none">비밀번호 찾기</a>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-primary btn-lg fw-bold shadow-sm py-3 mb-4 rounded-3">
                        로그인
                    </button>
                </div>

                <div class="text-center">
                    <span class="small text-muted">아직 계정이 없으신가요?</span>
                    <a href="/signup" class="small fw-bold text-decoration-none ms-1">회원가입 하기</a>
                </div>
            </form:form>
        </div>
    </jsp:body>

</t:layout>