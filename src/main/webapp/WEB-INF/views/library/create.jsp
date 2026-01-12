<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 스프링 폼 태그 라이브러리 추가 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="도서관 등록 - 관리자">
    <div class="container mt-5" style="max-width: 700px;">
        <div class="card shadow">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">🏢 새 도서관 등록 (Spring Form Tag)</h4>
            </div>
            <div class="card-body p-4">
                    <%--
                        modelAttribute: 컨트롤러에서 model.addAttribute("libraryRequest", ...)로 넘겨준 이름과 일치해야 함
                    --%>
                <form:form action="/libraries/create" method="post" modelAttribute="libraryRequest">

                    <%-- 도서관 이름 --%>
                    <div class="mb-3">
                        <form:label path="name" class="form-label fw-bold">도서관 이름</form:label>
                        <form:input path="name" class="form-control" placeholder="예: 중앙도서관" required="required" />
                        <form:errors path="name" cssClass="text-danger small" />
                    </div>

                    <%-- 위치 --%>
                    <div class="mb-3">
                        <form:label path="location" class="form-label fw-bold">위치</form:label>
                        <form:input path="location" class="form-control" placeholder="예: 서울시 중구" required="required" />
                        <form:errors path="location" cssClass="text-danger small" />
                    </div>

                    <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                        <a href="/libraries" class="btn btn-light me-md-2">취소</a>
                        <button type="submit" class="btn btn-primary px-4">등록하기</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</t:layout>