<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="도서 수정 - 관리자">
    <div class="container mt-5" style="max-width: 700px;">
        <div class="card shadow">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">📝 도서 정보 수정</h4>
            </div>
            <div class="card-body p-4">
                    <%--
                        action: 컨트롤러의 @PostMapping("/{id}/update") 경로와 일치
                        modelAttribute: 컨트롤러에서 넘긴 "updateBookRequest"와 일치
                    --%>
                <form:form action="/books/${id}/update" method="post" modelAttribute="updateBookRequest">

                    <%-- 도서 제목 --%>
                    <div class="mb-3">
                        <form:label path="name" class="form-label fw-bold">도서명</form:label>
                        <form:input path="name" class="form-control" placeholder="도서명을 입력하세요" />
                        <form:errors path="name" cssClass="text-danger small" />
                    </div>

                    <%-- ISBN (일반적으로 ISBN은 수정을 막기도 하지만, 요구사항에 따라 입력창 유지) --%>
                    <div class="mb-3">
                        <form:label path="isbn" class="form-label fw-bold">ISBN</form:label>
                        <form:input path="isbn" class="form-control" placeholder="ISBN 번호" />
                        <form:errors path="isbn" cssClass="text-danger small" />
                    </div>

                    <%-- 도서 설명 --%>
                    <div class="mb-3">
                        <form:label path="description" class="form-label fw-bold">도서 설명</form:label>
                        <form:textarea path="description" class="form-control" rows="5" placeholder="도서 상세 설명" />
                        <form:errors path="description" cssClass="text-danger small" />
                    </div>

                    <%-- 버튼 영역: 모범 사례의 배치 준수 --%>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                        <a href="/books/${id}" class="btn btn-light me-md-2">취소</a>
                        <button type="submit" class="btn btn-primary px-4">수정 완료</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</t:layout>