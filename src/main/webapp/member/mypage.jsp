<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../header.jsp" %>

<style>
.mypage-container {
    width: 1200px;
    margin: 40px auto;
    padding: 0 20px;
    background: #0a0a0a;
}

.mypage-header {
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 2px solid rgba(255, 255, 255, 0.1);
}

.mypage-header h2 {
    font-size: 32px;
    font-weight: bold;
    color: #ffffff;
    margin-bottom: 10px;
}

.mypage-header p {
    color: rgba(255, 255, 255, 0.7);
}

.mypage-menu {
    display: flex;
    gap: 20px;
    margin-top: 30px;
}

.menu-item {
    flex: 1;
    padding: 30px;
    background: #1a1a1a;
    border-radius: 8px;
    text-align: center;
    cursor: pointer;
    transition: all 0.3s;
    border: 2px solid rgba(255, 255, 255, 0.1);
    color: #ffffff;
}

.menu-item:hover {
    background: #ffd700;
    color: #1a1a1a;
    border-color: #ffd700;
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(255, 215, 0, 0.4);
}

.menu-item h3 {
    font-size: 24px;
    margin-bottom: 10px;
}

.menu-item p {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
}

.menu-item:hover p {
    color: #1a1a1a;
}

.user-info {
    background: #1a1a1a;
    padding: 20px;
    border-radius: 8px;
    margin-bottom: 30px;
    border: 1px solid rgba(255, 255, 255, 0.1);
}

.user-info p {
    margin: 10px 0;
    font-size: 16px;
    color: rgba(255, 255, 255, 0.8);
}

.user-info strong {
    color: #ffd700;
    margin-right: 10px;
}
</style>

<div class="mypage-container">
    <div class="mypage-header">
        <h2>マイページ</h2>
        <p>会員情報管理及びお気に入りリストを確認できます。</p>
    </div>
    
    <c:if test="${sessionScope.userid != null}">
        <div class="user-info">
            <p><strong>ユーザーID:</strong> ${sessionScope.userid}</p>
            <p><strong>名前:</strong> ${sessionScope.username}</p>
        </div>
    </c:if>
    
    <div class="mypage-menu">
        <div class="menu-item" onclick="location.href='${pageContext.request.contextPath}/mem/profileEdit.do'">
            <h3>📝 個人情報編集</h3>
            <p>名前、電話番号、メールアドレスなど<br>個人情報を編集できます。</p>
        </div>
        
        <div class="menu-item" onclick="location.href='${pageContext.request.contextPath}/mem/mypage.do'">
            <h3>❤️ お気に入りリスト</h3>
            <p>お気に入りにしたポートフォリオを<br>確認して管理できます。</p>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>
