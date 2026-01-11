<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm w-100 p-3 text-bg-dark">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-between">

            <ul class="nav">
                <li><a href="/" class="nav-link px-2 text-secondary">Home</a></li>
                <li><a href="/library" class="nav-link px-2 text-white">도서관</a></li>
            </ul>

            <sec:authorize access="isAnonymous()">
                <a href="/login" class="btn btn-outline-light btn-sm me-2">로그인</a>
                <a href="/signup" class="btn btn-primary btn-sm">회원가입</a>
            </sec:authorize>

            <sec:authorize access="isAuthenticated()">
                <div class="dropdown">
                    <a class="btn btn-dark dropdown-toggle d-flex align-items-center" href="#" role="button" data-bs-toggle="dropdown">
                        <div class="bg-secondary rounded-circle d-inline-flex align-items-center justify-content-center me-2" style="width: 24px; height: 24px;">
                            <i class="fa-solid fa-user text-white" style="font-size: 12px;"></i>
                        </div>
                        <span class="small"><sec:authentication property="principal.username"/></span>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end shadow border-0 mt-2">
                        <li><a class="dropdown-item" href="/profile"><i class="fa-solid fa-gear me-2 text-muted"></i>설정</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <form action="/logout" method="post" id="logoutForm">
                                    <%-- 시큐리티 POST 로그아웃을 위한 CSRF 토큰 --%>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="dropdown-item text-danger"><i class="fa-solid fa-right-from-bracket me-2"></i>로그아웃</button>
                            </form>
                        </li>
                    </ul>
                </div>
            </sec:authorize>

        </div>
    </div>
</nav>
