<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> <%-- 👈 Taglib 추가 확인 --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${book.name} - 상세 정보">
    <div class="container mt-5" style="max-width: 900px;">
        <nav aria-label="breadcrumb" class="mb-4">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/books">도서 목록</a></li>
                <li class="breadcrumb-item active" aria-current="page">상세 정보</li>
            </ol>
        </nav>

        <div class="row g-4">
            <div class="col-md-4">
                <div class="bg-light border rounded d-flex align-items-center justify-content-center" style="height: 400px;">
                    <span class="text-muted">No Image</span>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card h-100 border-0 shadow-sm">
                    <div class="card-body p-4">
                        <h1 class="display-6 fw-bold mb-3">${book.name}</h1>
                        <p class="text-muted mb-4">ISBN: <span class="badge bg-secondary">${book.isbn}</span></p>

                        <h5 class="fw-bold border-bottom pb-2">도서 설명</h5>
                        <p class="card-text text-secondary mt-3" style="line-height: 1.8;">
                                ${book.description}
                        </p>

                            <%-- 버튼 영역 --%>
                        <div class="mt-auto pt-4 d-flex gap-2">
                            <a href="/books/${book.id}/update" class="btn btn-primary px-4">수정하기</a>

                                <%-- 삭제하기 버튼 추가 (모범 사례 반영) --%>
                            <form:form action="/books/${book.id}/delete" method="post" style="display:inline;">
                                <button type="submit" class="btn btn-danger px-4">
                                    삭제하기
                                </button>
                            </form:form>

                            <a href="/books" class="btn btn-light px-4 border ms-auto">목록으로</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:layout>