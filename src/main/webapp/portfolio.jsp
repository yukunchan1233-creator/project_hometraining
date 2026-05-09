<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.*" %>
<%@ include file="header.jsp" %>

<style>
/* BODYX 스타일 포트폴리오 페이지 */
.portfolio-container {
    width: 1200px;
    margin: 40px auto;
    padding: 0 20px;
    background: #0a0a0a;
}

.portfolio-header {
    margin-bottom: 30px;
}

.portfolio-header h2 {
    font-size: 32px;
    font-weight: bold;
    color: #ffffff;
    margin-bottom: 20px;
}

/* 정렬 옵션 */
.sort-options {
    display: flex;
    gap: 10px;
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 2px solid rgba(255, 255, 255, 0.1);
}

.sort-options a {
    padding: 10px 20px;
    background: #1a1a1a;
    color: #ffffff;
    text-decoration: none;
    border-radius: 5px;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.3s;
    border: 1px solid rgba(255, 255, 255, 0.1);
}

.sort-options a:hover {
    background: #ffd700;
    color: #1a1a1a;
    border-color: #ffd700;
}

.sort-options a.active {
    background: #ffd700;
    color: #1a1a1a;
    border-color: #ffd700;
}

/* 제품 목록 테이블 */
.product-list-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    background: #0a0a0a;
}

.product-list-table thead {
    background: #1a1a1a;
}

.product-list-table th {
    padding: 15px;
    text-align: center;
    font-weight: bold;
    color: #ffffff;
    border-bottom: 2px solid rgba(255, 255, 255, 0.1);
}

.product-list-table td {
    padding: 20px 15px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.05);
    vertical-align: middle;
    background: #0a0a0a;
}

.product-list-table tr:hover {
    background: #1a1a1a;
}

/* 제품 이미지 컨테이너 - 레이어 분리로 성능 향상 */
.product-image-container {
    width: 200px;
    height: 200px;
    border-radius: 8px;
    border: 1px solid rgba(255, 255, 255, 0.1);
    overflow: hidden;
    position: relative;
    background: #1a1a1a;
    /* GPU 가속 - 컨테이너에만 적용 */
    transform: translateZ(0);
    -webkit-transform: translateZ(0);
}

/* 제품 이미지 - 최소한의 스타일만 적용 */
.product-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
    /* 복잡한 CSS 제거 - GIF 성능 향상 */
}

/* 제품 정보 */
.product-info {
    text-align: left;
    padding-left: 20px;
}

.product-title {
    font-size: 18px;
    font-weight: bold;
    color: #ffffff;
    margin-bottom: 10px;
    line-height: 1.4;
}

.product-meta {
    display: flex;
    flex-wrap: wrap;
    gap: 15px;
    align-items: center;
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
    margin-top: 8px;
}

.product-meta span {
    display: flex;
    align-items: center;
    gap: 5px;
}

/* 제품 찜 행 */
.product-wish-row {
    margin-top: 10px;
}

.wish-checkbox {
    display: flex;
    align-items: center;
    gap: 5px;
}

.wish-checkbox input[type="checkbox"] {
    width: 18px;
    height: 18px;
    cursor: pointer;
    accent-color: #00B4D8;
}

.wish-checkbox label {
    cursor: pointer;
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
}

/* 가격 */
.product-price {
    font-size: 24px;
    font-weight: bold;
    color: #ffd700;
    text-align: center;
    line-height: 1.2;
    margin-bottom: 5px;
}

/* 후기 및 별점 섹션 */
.review-rating-section {
    text-align: left;
    padding-left: 20px;
}

.review-count {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
    margin-bottom: 8px;
}

.rating-display {
    display: flex;
    align-items: center;
    gap: 10px;
}

.rating-score {
    font-size: 18px;
    font-weight: bold;
    color: #ffd700;
}

.star-rating {
    display: flex;
    gap: 2px;
}

.star {
    font-size: 20px;
    line-height: 1;
}

.star.filled {
    color: #ffc107; /* 노란색 별 */
}

.star.empty {
    color: rgba(255, 255, 255, 0.2); /* 회색 별 */
}

/* 공홈go 아이콘 */
.official-link-box {
    text-align: center;
}

.official-link-icon {
    display: inline-block;
    padding: 10px 20px;
    background: #ffd700;
    color: #1a1a1a;
    text-decoration: none;
    border-radius: 5px;
    font-size: 14px;
    font-weight: 700;
    cursor: pointer;
    transition: all 0.3s;
}

.official-link-icon:hover {
    background: #ffed4e;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(255, 215, 0, 0.5);
    cursor: pointer;
}

/* 빈 목록 메시지 */
.empty-message {
    text-align: center;
    padding: 60px 20px;
    color: rgba(255, 255, 255, 0.5);
    font-size: 16px;
}

.empty-message p {
    margin: 10px 0;
}

/* 후기 작성 버튼 */
.review-write-btn:hover {
    background: #0096C7 !important;
}

/* 후기 작성 모달 */
.review-modal {
    display: none;
    position: fixed;
    z-index: 1000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0,0,0,0.5);
}

.review-modal-content {
    background-color: #fff;
    margin: 10% auto;
    padding: 30px;
    border-radius: 8px;
    width: 500px;
    max-width: 90%;
    box-shadow: 0 4px 20px rgba(0,0,0,0.3);
}

.review-modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 15px;
    border-bottom: 2px solid #e1e1e1;
}

.review-modal-header h3 {
    margin: 0;
    font-size: 24px;
    color: #333;
}

.review-modal-close {
    font-size: 28px;
    font-weight: bold;
    color: #999;
    cursor: pointer;
    border: none;
    background: none;
}

.review-modal-close:hover {
    color: #333;
}

.review-form-group {
    margin-bottom: 20px;
}

.review-form-group label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
    color: #333;
}

.review-form-group input[type="number"],
.review-form-group textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
    box-sizing: border-box;
}

.review-form-group textarea {
    min-height: 100px;
    resize: vertical;
}

.star-input-group {
    display: flex;
    gap: 5px;
    align-items: center;
}

.star-input-group input[type="number"] {
    width: 80px;
}

.star-input-group span {
    color: #ffc107;
    font-size: 20px;
}

.review-form-buttons {
    display: flex;
    gap: 10px;
    justify-content: flex-end;
    margin-top: 20px;
}

.review-form-buttons button {
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 500;
}

.review-submit-btn {
    background: #2196F3;
    color: white;
}

.review-submit-btn:hover {
    background: #0096C7;
}

.review-cancel-btn {
    background: #f5f5f5;
    color: #333;
}

.review-cancel-btn:hover {
    background: #e0e0e0;
}
</style>

<%-- サブカテゴリー名を日本語に変換 --%>
<c:set var="subcategoryJP" value="${subcategory}" />
<c:choose>
    <c:when test="${subcategory == '런닝머신'}"><c:set var="subcategoryJP" value="ランニングマシン" /></c:when>
    <c:when test="${subcategory == '사이클'}"><c:set var="subcategoryJP" value="サイクル" /></c:when>
    <c:when test="${subcategory == '로잉머신'}"><c:set var="subcategoryJP" value="ローイングマシン" /></c:when>
    <c:when test="${subcategory == '스텝퍼'}"><c:set var="subcategoryJP" value="ステッパー" /></c:when>
    <c:when test="${subcategory == '벤치프레스'}"><c:set var="subcategoryJP" value="ベンチプレス" /></c:when>
    <c:when test="${subcategory == '덤벨'}"><c:set var="subcategoryJP" value="ダンベル" /></c:when>
    <c:when test="${subcategory == '바벨'}"><c:set var="subcategoryJP" value="バーベル" /></c:when>
    <c:when test="${subcategory == '케이블머신'}"><c:set var="subcategoryJP" value="ケーブルマシン" /></c:when>
</c:choose>

<div class="portfolio-container">
    <div class="portfolio-header">
        <h2>${subcategoryJP} ポートフォリオ</h2>
        
        <!-- 並び替えオプション -->
        <div class="sort-options">
            <a href="${pageContext.request.contextPath}/portfolio.do?sub=${subcategory}&sort=popular" 
               class="${sortType == 'popular' || sortType == null || sortType == '' ? 'active' : ''}">
                人気商品順
            </a>
            <a href="${pageContext.request.contextPath}/portfolio.do?sub=${subcategory}&sort=price_low" 
               class="${sortType == 'price_low' ? 'active' : ''}">
                価格低い順
            </a>
            <a href="${pageContext.request.contextPath}/portfolio.do?sub=${subcategory}&sort=price_high" 
               class="${sortType == 'price_high' ? 'active' : ''}">
                価格高い順
            </a>
        </div>
    </div>
    
    <!-- 商品リストテーブル (常に表示) -->
    <table class="product-list-table">
        <thead>
            <tr>
                <th style="width: 200px;">画像</th>
                <th style="width: 400px;">商品情報</th>
                <th style="width: 300px;">レビュー及び評価</th>
                <th style="width: 180px;">価格及び購入</th>
                <c:if test="${sessionScope.userid == 'admin'}">
                    <th style="width: 150px;">管理</th>
                </c:if>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty list || list.size() == 0}">
                    <tr>
                        <td colspan="${sessionScope.userid == 'admin' ? '5' : '4'}" style="text-align: center; padding: 60px 20px; color: #999;">
                            登録された商品がありません。
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="product" items="${list}">
                        <tr>
                            <!-- 제품 이미지 -->
                            <td style="text-align: center;">
                                <div class="product-image-container">
                                    <img src="${pageContext.request.contextPath}/${product.image_path}" 
                                         alt="${product.product_name}" 
                                         class="product-image"
                                         loading="eager"
                                         decoding="async"
                                         onerror="this.src='${pageContext.request.contextPath}/images/image_1.jpg'">
                                </div>
                            </td>
                            
                            <!-- 商品情報: タイトル(上段) + お気に入り(下段) -->
                            <td>
                                <div class="product-info">
                                    <!-- 上段: タイトル -->
                                    <div class="product-title">${product.product_name}</div>
                                    <!-- 下段: お気に入り -->
                                    <c:if test="${sessionScope.userid != null}">
                                        <div class="product-wish-row">
                                            <span class="wish-checkbox">
                                                <input type="checkbox" id="wish_${product.pno}" name="wish" value="${product.pno}">
                                                <label for="wish_${product.pno}">お気に入り</label>
                                            </span>
                                        </div>
                                    </c:if>
                                </div>
                            </td>
                            
                            <!-- レビュー及び評価 (クリック可能) -->
                            <td>
                                <div class="review-rating-section" 
                                     onclick="location.href='${pageContext.request.contextPath}/review/view.do?pno=${product.pno}&sub=${subcategory}'"
                                     style="cursor: pointer;">
                                    <div class="review-count">レビュー ${product.review_count}件</div>
                                    <div class="rating-display">
                                        <%
                                            // request에서 평균 별점 가져오기
                                            model.ProductDto prod = (model.ProductDto) pageContext.getAttribute("product");
                                            java.util.Map<Integer, Double> ratingMap = (java.util.Map<Integer, Double>) request.getAttribute("ratingMap");
                                            double avgRating = 0.0;
                                            if(prod != null && ratingMap != null && ratingMap.containsKey(prod.getPno())) {
                                                avgRating = ratingMap.get(prod.getPno());
                                                // 소수점 첫째자리까지
                                                avgRating = Math.round(avgRating * 10.0) / 10.0;
                                            }
                                            pageContext.setAttribute("avgRating", avgRating);
                                            
                                            // 별 개수 계산 (0~5)
                                            int filledStars = (int) Math.round(avgRating);
                                            if(filledStars > 5) filledStars = 5;
                                            if(filledStars < 0) filledStars = 0;
                                            pageContext.setAttribute("filledStars", filledStars);
                                        %>
                                        <c:choose>
                                            <c:when test="${avgRating > 0}">
                                                <span class="rating-score">${avgRating}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="rating-score" style="color: #999;">評価なし</span>
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="star-rating">
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:choose>
                                                    <c:when test="${i <= filledStars}">
                                                        <span class="star filled">★</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="star empty">☆</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            
                            <!-- 価格及び公式サイトアイコン -->
                            <td class="official-link-box">
                                <div style="display: flex; flex-direction: column; align-items: center; gap: 10px;">
                                    <!-- 価格表示 -->
                                    <div class="product-price">
                                        <c:choose>
                                            <c:when test="${product.price != null && product.price > 0}">
                                                <%= String.format("%,d", ((model.ProductDto)pageContext.getAttribute("product")).getPrice()) %>円
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: #999; font-size: 16px;">価格情報なし</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <!-- 公式サイトボタン -->
                                    <c:choose>
                                        <c:when test="${not empty product.buy_link}">
                                            <a href="${product.buy_link}" 
                                               target="_blank" 
                                               class="official-link-icon"
                                               title="公式ホームページで購入する">
                                                公式go
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #999; font-size: 14px;">リンクなし</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </td>
                            <!-- 管理者用編集/削除ボタン -->
                            <c:if test="${sessionScope.userid == 'admin'}">
                                <td style="text-align: center;">
                                    <button type="button" 
                                            class="admin-btn edit-btn" 
                                            onclick="location.href='${pageContext.request.contextPath}/admin/product/edit.do?pno=${product.pno}&sub=${subcategory}'"
                                            style="padding: 8px 15px; margin: 0 5px; background: #00B4D8; color: white; border: none; border-radius: 5px; cursor: pointer;">
                                        編集
                                    </button>
                                    <button type="button" 
                                            class="admin-btn delete-btn" 
                                            onclick="if(confirm('本当に削除しますか？')) { location.href='${pageContext.request.contextPath}/admin/product/delete.do?pno=${product.pno}&sub=${subcategory}'; }"
                                            style="padding: 8px 15px; margin: 0 5px; background: #0077B6; color: white; border: none; border-radius: 5px; cursor: pointer;">
                                        削除
                                    </button>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
        <!-- 管理者用追加ボタン -->
        <c:if test="${sessionScope.userid == 'admin'}">
            <tfoot>
                <tr>
                    <td colspan="${sessionScope.userid == 'admin' ? '5' : '4'}" style="text-align: right; padding: 20px;">
                        <button type="button" 
                                class="admin-btn add-btn" 
                                onclick="location.href='${pageContext.request.contextPath}/admin/product/add.do?sub=${subcategory}'"
                                style="padding: 12px 25px; background: #2196F3; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; font-weight: bold;">
                            + 商品追加
                        </button>
                    </td>
                </tr>
            </tfoot>
        </c:if>
    </table>
</div>

<%@ include file="footer.jsp" %>

<script>
// wish 체크박스 클릭 이벤트 - 제품 찜 기능
$(function() {
    $('input[name="wish"]').click(function() {
        var pno = $(this).val();  // 제품 번호 (product pno)
        var isChecked = $(this).is(':checked');
        var $checkbox = $(this);
        
        // 로그인 체크
        var userid = '${sessionScope.userid}';
        if(!userid) {
            alert('로그인이 필요합니다.');
            $checkbox.prop('checked', false);
            location.href = contextPath + '/mem/login.do';
            return;
        }
        
        // AJAX로 서버에 전송
        $.ajax({
            type: 'post',
            url: contextPath + '/port/productWish.do',
            data: {pno: pno},
            success: function(result) {
                if(result.trim() === '찜 성공') {
                    alert('お気に入りリストに追加されました。');
                } else if(result.trim() === '찜 이미 했어요') {
                    alert('既にお気に入りに追加された商品です。');
                    $checkbox.prop('checked', true);
                } else {
                    alert('お気に入り追加に失敗しました。');
                    $checkbox.prop('checked', false);
                }
            },
            error: function() {
                alert('エラーが発生しました。');
                $checkbox.prop('checked', false);
            }
        });
    });
});
</script>
