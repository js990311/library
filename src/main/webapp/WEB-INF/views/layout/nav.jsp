<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-expand-lg navbar-dark sticky-top shadow-sm py-2">
    <div class="container">
        <a class="navbar-brand fw-bold" href="/">MY LOGO</a>

        <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a href="/" class="nav-link active">Home</a>
                </li>
                <li class="nav-item">
                    <a href="/library" class="nav-link text-white-50">도서관</a>
                </li>
            </ul>

            <div class="d-flex align-items-center">
                <sec:authorize access="isAnonymous()">
                    <a href="/login" class="nav-link text-white me-3">로그인</a>
                    <a href="/signup" class="btn btn-primary btn-sm px-3 rounded-pill">회원가입</a>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <div class="dropdown">
                        <a class="nav-link dropdown-toggle d-flex align-items-center p-0" href="#" role="button" data-bs-toggle="dropdown">
                            <div class="user-avatar rounded-circle d-flex align-items-center justify-content-center me-2 shadow-sm">
                                <i class="fa-solid fa-user text-white small"></i>
                            </div>
                            <span class="d-none d-sm-inline text-white">
                                <sec:authentication property="principal.username"/>님
                            </span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end shadow-lg border-0 animate slideIn">
                            <li><h6 class="dropdown-header">내 계정</h6></li>
                            <li><a class="dropdown-item py-2" href="/profile"><i class="fa-solid fa-user-gear me-2 text-muted"></i>개인 설정</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <form action="/logout" method="post" class="m-0">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button type="submit" class="dropdown-item py-2 text-danger">
                                        <i class="fa-solid fa-arrow-right-from-bracket me-2"></i>로그아웃
                                    </button>
                                </form>
                            </li>
                        </ul>
                    </div>
                </sec:authorize>
            </div>
        </div>
    </div>
</nav>