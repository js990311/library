<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="회원가입 - 도서관">
    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value='/css/user/user.css' />">
    </jsp:attribute>

    <jsp:body>
        <div class="signup-wrapper py-5">
            <div class="mb-5">
                <span class="step-indicator mb-2 d-block text-uppercase fw-bold text-muted small" style="letter-spacing: 1px;">Join our Library</span>
                <h2 class="fw-bold text-dark">새로운 계정 만들기</h2>
                <p class="text-muted small">단 몇 초면 도서관 회원이 될 수 있습니다.</p>
            </div>

            <form:form modelAttribute="signupForm" action="/signup" method="POST">
                <div class="mb-4">
                    <label for="username" class="form-label small fw-bold text-secondary">아이디</label>
                    <div class="form-floating">
                        <form:input path="username" id="username" class="form-control rounded-3 bg-light border-0" placeholder="Username" />
                        <label for="username" class="text-muted small">Username</label>
                    </div>
                    <form:errors path="username" cssClass="text-danger small mt-1 d-block" />
                    <div class="form-text small" style="font-size: 0.75rem;">영문, 숫자 조합 4~12자 이내</div>
                </div>

                <div class="mb-4">
                    <label for="password" class="form-label small fw-bold text-secondary">비밀번호</label>
                    <div class="form-floating">
                        <form:password path="password" id="password" class="form-control rounded-3 bg-light border-0" placeholder="Password" />
                        <label for="password" class="text-muted small">Password</label>
                    </div>
                    <form:errors path="password" cssClass="text-danger small mt-1 d-block" />
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary btn-lg fw-bold shadow-sm py-3 rounded-3">
                        무료로 시작하기
                    </button>
                    <a href="/login" class="btn btn-link btn-sm text-decoration-none text-muted mt-2">
                        이미 계정이 있으신가요? 로그인
                    </a>
                </div>
            </form:form>
        </div>
    </jsp:body>
</t:layout>