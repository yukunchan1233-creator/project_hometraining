<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOMETRAINING</title>
<link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
// contextPath를 header에서 먼저 설정 (my.js가 로드되기 전에)
var contextPath = "${pageContext.request.contextPath}";
// jslhrd가 ROOT(/)로 설정되어 있으면 hometraining으로 강제 설정
if(!contextPath || contextPath === '/') {
	contextPath = '/hometraining';
}
</script>
<script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>
<body>
    <div class="gnb_group">
        <!-- 로고 -->
        <h1 class="logo">
            <a href="${pageContext.request.contextPath}/main.do" class="logo-link">
                <div class="logo-main">
                    <img src="${pageContext.request.contextPath}/images/logo.png" 
                         alt="HOME TRAINING" 
                         class="logo-image"
                         onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                    <span class="logo-text-fallback" style="display: none;">
                        <span class="logo-home">HOME</span>
                        <span class="logo-training">TRAINING</span>
                    </span>
                </div>
                <div class="logo-subtitle">
                    <span class="subtitle-text">PRICE COMPARISON</span>
                </div>
            </a>
        </h1>
        
        <!-- メインメニュー (有酸素運動、筋力運動) -->
        <nav class="main_nav">
            <ul class="nav_1depth">
                <li>
                    <a href="">有酸素運動</a>
                    <ul class="nav_2depth">
                        <li><a href="${pageContext.request.contextPath}/portfolio.do?sub=런닝머신">ランニングマシン</a></li>
                        <li><a href="${pageContext.request.contextPath}/portfolio.do?sub=사이클">サイクル</a></li>
                        <li><a href="${pageContext.request.contextPath}/portfolio.do?sub=로잉머신">ローイングマシン</a></li>
                        <li><a href="${pageContext.request.contextPath}/portfolio.do?sub=스텝퍼">ステッパー</a></li>
                    </ul>
                </li>
                <li>
                    <a href="">筋力運動</a>
                    <ul class="nav_2depth">
                        <li><a href="${pageContext.request.contextPath}/portfolio.do?sub=벤치프레스">ベンチプレス</a></li>
                        <li><a href="${pageContext.request.contextPath}/portfolio.do?sub=덤벨">ダンベル</a></li>
                        <li><a href="${pageContext.request.contextPath}/portfolio.do?sub=바벨">バーベル</a></li>
                        <li><a href="${pageContext.request.contextPath}/portfolio.do?sub=케이블머신">ケーブルマシン</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        
        <!-- 右側ユーザーメニュー (検索、ログアウト、マイページ) -->
        <nav class="user_nav">
            <ul>
                <c:choose>
                    <c:when test="${empty sessionScope.userid}">
                        <li class="icon-item">
                            <a href="javascript:void(0);" class="search-icon" title="検索">
                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <circle cx="11" cy="11" r="8"></circle>
                                    <path d="m21 21-4.35-4.35"></path>
                                </svg>
                            </a>
                        </li>
                        <li class="separator">|</li>
                        <li><a href="${pageContext.request.contextPath}/mem/login.do">ログイン</a></li>
                        <li class="separator">|</li>
                        <li><a href="${pageContext.request.contextPath}/mem/join.do">会員登録</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="icon-item">
                            <a href="javascript:void(0);" class="search-icon" title="検索">
                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <circle cx="11" cy="11" r="8"></circle>
                                    <path d="m21 21-4.35-4.35"></path>
                                </svg>
                            </a>
                        </li>
                        <li class="separator">|</li>
                        <li><a href="${pageContext.request.contextPath}/mem/logout.do">ログアウト</a></li>
                        <li class="separator">|</li>
                        <li class="mypage-dropdown">
                            <a href="javascript:void(0);">マイページ</a>
                            <ul class="mypage-menu">
                                <li><a href="${pageContext.request.contextPath}/mem/profileEdit.do">個人情報編集</a></li>
                                <li><a href="${pageContext.request.contextPath}/mem/mypage.do">お気に入りリスト</a></li>
                            </ul>
                        </li>
                        <c:if test="${sessionScope.userid == 'admin'}">
                            <li class="separator">|</li>
                            <li><a href="${pageContext.request.contextPath}/portfolio.do?sub=런닝머신" style="color: #00B4D8; font-weight: bold;">管理者</a></li>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>

