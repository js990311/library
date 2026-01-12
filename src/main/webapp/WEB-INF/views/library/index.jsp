<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout title="홈 - 도서관">
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>📚 도서관 목록</h2>
            <a href="/libraries/new" class="btn btn-primary">새 도서관 등록</a>
        </div>

            <%-- 도서관 테이블 --%>
        <div class="card shadow-sm">
            <div class="card-body p-0">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                    <tr>
                        <th style="width: 10%">ID</th>
                        <th style="width: 25%">도서관명</th>
                        <th style="width: 30%">위치</th>
                        <th style="width: 15%">웹사이트</th>
                        <th style="width: 20%" class="text-center">관리</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${not empty libraryPage.content}">
                            <c:forEach items="${libraryPage.content}" var="lib">
                                <tr>
                                    <td>${lib.id}</td>
                                    <td><strong>${lib.name}</strong></td>
                                    <td>${lib.location}</td>
                                    <td>
                                        <c:if test="${not empty lib.webpage}">
                                            <a href="${lib.webpage}" target="_blank" class="btn btn-sm btn-link">방문</a>
                                        </c:if>
                                    </td>
                                    <td class="text-center">
                                        <a href="/libraries/${lib.id}/edit" class="btn btn-sm btn-outline-secondary">수정</a>
                                        <form action="/libraries/${lib.id}/delete" method="post" style="display:inline;">
                                            <button type="submit" class="btn btn-sm btn-outline-danger"
                                                    onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" class="text-center py-5 text-muted">등록된 도서관이 없습니다.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

            <%-- 페이징 처리 --%>
        <div class="d-flex justify-content-center mt-4">
            <nav>
                <ul class="pagination">
                        <%-- 이전 페이지 --%>
                    <li class="page-item ${!libraryPage.hasPrevious() ? 'disabled' : ''}">
                        <a class="page-link" href="?page=${libraryPage.number - 1}&size=${libraryPage.size}">이전</a>
                    </li>

                        <%-- 현재 페이지 정보 --%>
                    <li class="page-item disabled">
                        <span class="page-link">
                            ${libraryPage.number + 1} / ${libraryPage.totalPages == 0 ? 1 : libraryPage.totalPages}
                        </span>
                    </li>

                        <%-- 다음 페이지 --%>
                    <li class="page-item ${!libraryPage.hasNext() ? 'disabled' : ''}">
                        <a class="page-link" href="?page=${libraryPage.number + 1}&size=${libraryPage.size}">다음</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</t:layout>