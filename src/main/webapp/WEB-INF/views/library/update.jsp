<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="도서관 정보 수정">
    <div class="container mt-5" style="max-width: 700px;">
        <div class="card shadow border-0">
            <div class="card-header bg-warning text-dark fw-bold">
                <h4 class="mb-0">🔄 도서관 정보 수정</h4>
            </div>
            <div class="card-body p-4">
                <p class="text-muted mb-4 small">도서관의 이름과 위치 정보를 수정할 수 있습니다.</p>

                    <%-- 컨트롤러의 @PathVariable("id")를 활용하기 위해 action 구성 --%>
                <form:form action="/libraries/${id}/update" method="post" modelAttribute="libraryRequest">

                    <%-- 도서관 이름 --%>
                    <div class="mb-3">
                        <form:label path="name" class="form-label fw-bold">도서관 이름</form:label>
                        <form:input path="name" class="form-control"
                                    placeholder="수정할 이름을 입력하세요" required="required" />
                        <form:errors path="name" cssClass="text-danger small" />
                    </div>

                    <%-- 위치 --%>
                    <div class="mb-3">
                        <form:label path="location" class="form-label fw-bold">위치</form:label>
                        <form:input path="location" class="form-control"
                                    placeholder="수정할 위치를 입력하세요" required="required" />
                        <form:errors path="location" cssClass="text-danger small" />
                    </div>

                    <hr class="my-4">

                    <%-- 버튼 영역 --%>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <%-- 취소 시 상세 페이지로 이동 --%>
                        <a href="/libraries/${id}" class="btn btn-light me-md-2">취소</a>
                        <button type="submit" class="btn btn-warning px-4 fw-bold">수정 완료</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</t:layout>