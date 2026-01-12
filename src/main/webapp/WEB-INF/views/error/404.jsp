<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="404 - 페이지를 찾을 수 없습니다">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8 text-center">
                <div class="error-template">
                    <h1 class="display-1 fw-bold text-muted">404</h1>
                    <h2 class="mb-3">페이지를 찾을 수 없습니다</h2>

                    <div class="error-details mb-4">
                        <p class="text-secondary lead">
                            <c:out value="${errorMessage}" default="요청하신 페이지가 존재하지 않거나 이동되었을 수 있습니다." />
                        </p>
                    </div>

                    <div class="error-actions mt-5">
                        <a href="/libraries" class="btn btn-primary btn-lg px-4">
                            <i class="bi bi-house-door"></i> 도서관 목록으로 돌아가기
                        </a>
                        <a href="javascript:history.back();" class="btn btn-outline-secondary btn-lg px-4">
                            이전 페이지로
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:layout>