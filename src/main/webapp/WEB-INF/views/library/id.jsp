<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:layout title="도서관 상세 정보 - ${library.name}">
    <div class="container mt-5" style="max-width: 800px;">
        <div class="card shadow-sm">
            <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                <h4 class="mb-0">🏛️ 도서관 상세 정보</h4>
                <span class="badge bg-secondary">ID: ${library.id}</span>
            </div>
            <div class="card-body p-4">
                <div class="mb-4">
                    <label class="text-muted small fw-bold">도서관명</label>
                    <h2 class="border-bottom pb-2">${library.name}</h2>
                </div>

                <div class="row mb-4">
                    <div class="col-md-6">
                        <label class="text-muted small fw-bold">위치</label>
                        <p class="lead">${library.location}</p>
                    </div>
                </div>

                <div class="d-grid gap-2 d-md-flex justify-content-md-end pt-3 border-top">
                    <a href="/libraries" class="btn btn-outline-secondary me-md-2">목록으로</a>
                    <a href="/libraries/${library.id}/update" class="btn btn-warning">정보 수정</a>
                    <form:form action="/libraries/${library.id}/delete" method="post" style="display:inline;">
                        <button type="submit" class="btn btn-sm btn-outline-danger">삭제</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</t:layout>