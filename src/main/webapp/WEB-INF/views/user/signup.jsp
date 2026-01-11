<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="회원가입 - 도서관">
    <div class="form-signin w-100 m-auto">
        <form:form modelAttribute="signupForm" action="/signup" method="POST">
            <h1 class="h3 mb-3 fw-normal">회원가입</h1>
            <div class="form-floating">
                <form:input path="username"
                            id="username"
                            class="form-control"
                            placeholder="아이디"
                />
                <label for="username">아이디</label>
                <form:errors path="username"
                             cssClass="text-danger small"
                />
            </div>
            <div class="form-floating">
                <form:password path="password"
                               id="password"
                               class="form-control"
                               placeholder="비밀번호"
                />
                <label for="password">비밀번호</label>
                <form:errors path="password"
                             cssClass="text-danger small"
                />
            </div>
            <div class="d-grid mt-4">
                <button type="submit" class="btn btn-primary w-100 py-2">
                    로그인
                </button>
            </div>
        </form:form>
    </div>
</t:layout>