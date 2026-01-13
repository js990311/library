<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="홈 - 도서">
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>📚 도서 목록</h2>
            <a href="/books/create" class="btn btn-primary">새 도서 등록</a>
        </div>

            <%-- 도서 테이블 --%>
        <div class="card shadow-sm">
            <div class="card-body p-0">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                    <tr>
                        <th style="width: 10%">ID</th>
                        <th style="width: 40%">도서명</th>
                        <th style="width: 30%">ISBN</th>
                        <th style="width: 20%" class="text-center">관리</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${not empty bookPage.content}">
                            <c:forEach items="${bookPage.content}" var="book">
                                <tr>
                                    <td>${book.id}</td>
                                    <td>
                                        <a href="/books/${book.id}"
                                           class="text-decoration-none link-primary fw-bold d-block py-1">
                                                ${book.name}
                                        </a>
                                    </td>
                                    <td>${book.isbn}</td>
                                    <td class="text-center">
                                        <a href="/books/${book.id}/update" class="btn btn-sm btn-outline-secondary">수정</a>
                                        <form:form action="/books/${book.id}/delete" method="post" style="display:inline;">
                                            <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
                                        </form:form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                    <%-- 컬럼 수(4개)에 맞춰 colspan 조절 --%>
                                <td colspan="4" class="text-center py-5 text-muted">등록된 도서가 없습니다.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

            <%-- 페이징 처리 (Library 모범 사례 스타일 적용) --%>
        <div class="d-flex justify-content-center mt-4">
            <nav>
                <ul class="pagination">
                        <%-- 이전 페이지 --%>
                    <li class="page-item ${!bookPage.hasPrevious() ? 'disabled' : ''}">
                        <a class="page-link" href="?page=${bookPage.number - 1}&size=${bookPage.size}">이전</a>
                    </li>

                        <%-- 현재 페이지 정보 (분수 형태 표시) --%>
                    <li class="page-item disabled">
                        <span class="page-link">
                            ${bookPage.number + 1} / ${bookPage.totalPages == 0 ? 1 : bookPage.totalPages}
                        </span>
                    </li>

                        <%-- 다음 페이지 --%>
                    <li class="page-item ${!bookPage.hasNext() ? 'disabled' : ''}">
                        <a class="page-link" href="?page=${bookPage.number + 1}&size=${bookPage.size}">다음</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</t:layout>