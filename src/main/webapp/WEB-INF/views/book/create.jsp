<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="새 도서 등록">
    <div class="container mt-5" style="max-width: 800px;">
        <div class="card shadow border-0">
            <div class="card-header bg-success text-white py-3">
                <h4 class="mb-0 px-2">📖 새 도서 등록</h4>
            </div>
            <div class="card-body p-4">
                <form:form action="/books/create" method="post" modelAttribute="createBookRequest">

                    <%-- 도서명 --%>
                    <div class="mb-3">
                        <form:label path="name" class="form-label fw-bold">도서 제목</form:label>
                        <form:input path="name" class="form-control form-control-lg" placeholder="도서명을 입력하세요" />
                        <form:errors path="name" cssClass="text-danger small mt-1" />
                    </div>

                    <%-- ISBN --%>
                    <div class="mb-3">
                        <form:label path="isbn" class="form-label fw-bold">ISBN</form:label>
                        <form:input path="isbn" class="form-control" placeholder="ISBN-13 번호를 입력하세요" />
                        <form:errors path="isbn" cssClass="text-danger small mt-1" />
                    </div>

                    <%-- 설명 --%>
                    <div class="mb-4">
                        <form:label path="description" class="form-label fw-bold">도서 설명</form:label>
                        <form:textarea path="description" class="form-control" rows="5" placeholder="도서에 대한 상세 설명을 입력하세요" />
                        <form:errors path="description" cssClass="text-danger small mt-1" />
                    </div>

                    <div class="border-top pt-4 d-flex justify-content-between">
                        <a href="/books" class="btn btn-outline-secondary px-4">목록으로</a>
                        <button type="submit" class="btn btn-success px-5 fw-bold">도서 등록하기</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</t:layout>