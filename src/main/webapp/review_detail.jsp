<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.*" %>
<%@ include file="header.jsp" %>

<style>
.review-container {
    width: 1000px;
    margin: 40px auto;
    padding: 0 20px;
    background: #0a0a0a;
}

.review-header {
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 2px solid rgba(255, 255, 255, 0.1);
}

.review-header h2 {
    font-size: 28px;
    font-weight: bold;
    color: #ffffff;
    margin-bottom: 20px;
}

.product-info-box {
    display: flex;
    gap: 20px;
    align-items: center;
    padding: 20px;
    background: #1a1a1a;
    border-radius: 8px;
    margin-bottom: 30px;
    border: 1px solid rgba(255, 255, 255, 0.1);
}

.product-info-box img {
    width: 150px;
    height: 150px;
    object-fit: cover;
    border-radius: 8px;
    border: 1px solid #ddd;
}

.product-info-details {
    flex: 1;
}

.product-info-details h3 {
    font-size: 22px;
    font-weight: bold;
    color: #ffffff;
    margin-bottom: 10px;
}

.product-rating-summary {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-top: 10px;
}

.rating-score-large {
    font-size: 24px;
    font-weight: bold;
    color: #ffd700;
}

.star-rating-large {
    display: flex;
    gap: 3px;
}

.star-large {
    font-size: 24px;
    line-height: 1;
}

.star-large.filled {
    color: #ffc107;
}

.star-large.empty {
    color: rgba(255, 255, 255, 0.2);
}

.review-count-large {
    font-size: 16px;
    color: rgba(255, 255, 255, 0.7);
    margin-left: 10px;
}

/* 후기 작성 폼 박스 */
.review-form-box {
    background: #1a1a1a;
    border: 2px solid rgba(255, 255, 255, 0.1);
    border-radius: 8px;
    padding: 30px;
    margin-bottom: 30px;
}

.review-form-box h3 {
    font-size: 20px;
    font-weight: bold;
    color: #ffffff;
    margin-bottom: 20px;
    padding-bottom: 15px;
    border-bottom: 2px solid #e1e1e1;
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
    color: #ffffff;
    font-size: 16px;
}

.form-group input[type="file"],
.form-group textarea,
.form-group select {
    width: 100%;
    padding: 12px;
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 5px;
    font-size: 14px;
    box-sizing: border-box;
    background: #2a2a2a;
    color: #ffffff;
}

.form-group textarea {
    min-height: 120px;
    resize: vertical;
}

.form-group select {
    cursor: pointer;
}

.form-buttons {
    display: flex;
    gap: 10px;
    justify-content: flex-end;
    margin-top: 20px;
}

.form-buttons button {
    padding: 12px 30px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
    transition: all 0.3s;
}

.btn-submit {
    background: #ffd700;
    color: #1a1a1a;
    font-weight: 600;
}

.btn-submit:hover {
    background: #ffed4e;
}

/* 후기 목록 */
.review-list {
    margin-top: 30px;
}

.review-list h3 {
    font-size: 20px;
    font-weight: bold;
    color: #333;
    margin-bottom: 20px;
    padding-bottom: 15px;
    border-bottom: 2px solid #e1e1e1;
}

.review-item {
    background: #fff;
    border: 1px solid #e1e1e1;
    border-radius: 8px;
    padding: 25px;
    margin-bottom: 20px;
}

.review-item-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
    padding-bottom: 15px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.review-author-info {
    display: flex;
    align-items: center;
    gap: 12px;
    flex: 1;
}

.review-author-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    overflow: hidden;
    flex-shrink: 0;
    background: #2a2a2a;
    display: flex;
    align-items: center;
    justify-content: center;
}

.review-author-avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.default-avatar {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #ffd700 0%, #ffed4e 100%);
    color: #1a1a1a;
    font-weight: bold;
    font-size: 16px;
    text-transform: uppercase;
}

.review-author {
    font-size: 16px;
    font-weight: bold;
    color: #ffffff;
    display: block;
    line-height: 1.5;
}

.review-date {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.5);
}

.review-rating-item {
    display: flex;
    align-items: center;
    gap: 5px;
    margin-bottom: 15px;
}

.review-rating-item .star {
    font-size: 18px;
    color: #ffc107;
}

.review-image {
    margin: 15px 0;
}

.review-image img {
    max-width: 300px;
    max-height: 300px;
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 5px;
    padding: 5px;
}

.review-content {
    font-size: 15px;
    line-height: 1.6;
    color: rgba(255, 255, 255, 0.9);
    white-space: pre-wrap;
}

.review-actions {
    display: flex;
    gap: 10px;
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.btn-edit, .btn-delete {
    padding: 8px 16px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.3s;
}

.btn-edit {
    background: #00B4D8;
    color: white;
}

.btn-edit:hover {
    background: #0096C7;
}

.btn-delete {
    background: #0077B6;
    color: white;
}

.btn-delete:hover {
    background: #006494;
}

.review-edit-form {
    margin-top: 20px;
    padding: 20px;
    background: #2a2a2a;
    border-radius: 8px;
    border: 1px solid rgba(255, 255, 255, 0.1);
    display: none;
}

.review-edit-form.active {
    display: block;
}

.review-edit-form .form-group {
    margin-bottom: 15px;
}

.review-edit-form .form-buttons {
    margin-top: 15px;
}

.btn-cancel {
    background: #2a2a2a;
    color: white;
    padding: 8px 16px;
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 500;
    margin-left: 10px;
    transition: all 0.3s;
}

.btn-cancel:hover {
    background: #3a3a3a;
    border-color: rgba(255, 255, 255, 0.3);
}

.empty-reviews {
    text-align: center;
    padding: 60px 20px;
    color: rgba(255, 255, 255, 0.5);
    font-size: 16px;
}

.back-link {
    display: inline-block;
    margin-bottom: 20px;
    color: #4a9eff;
    text-decoration: none;
    font-size: 14px;
    transition: color 0.3s;
}

.back-link:hover {
    color: #ffd700;
    text-decoration: underline;
}
</style>

<div class="review-container">
    <a href="${pageContext.request.contextPath}/portfolio.do?sub=${subcategory}" class="back-link">← ポートフォリオに戻る</a>
    
    <div class="review-header">
        <h2>商品レビュー</h2>
    </div>
    
    <!-- 商品情報ボックス -->
    <%
        // request에서 product 객체 가져오기 (한 번만 선언)
        model.ProductDto product = (model.ProductDto) request.getAttribute("product");
        model.ProductReviewDao reviewDao = new model.ProductReviewDao();
        double avgRating = 0.0;
        if(product != null) {
            avgRating = reviewDao.selectAverageRating(product.getPno());
            avgRating = Math.round(avgRating * 10.0) / 10.0;
        }
        int filledStars = (int) Math.round(avgRating);
        if(filledStars > 5) filledStars = 5;
        if(filledStars < 0) filledStars = 0;
        pageContext.setAttribute("avgRating", avgRating);
        pageContext.setAttribute("filledStars", filledStars);
    %>
    <div class="product-info-box">
        <img src="${pageContext.request.contextPath}/${product.image_path}" 
             alt="${product.product_name}" 
             onerror="this.src='${pageContext.request.contextPath}/images/image_1.jpg'">
        <div class="product-info-details">
            <h3>${product.product_name}</h3>
            <div class="product-rating-summary">
                <span class="rating-score-large">${avgRating}</span>
                <div class="star-rating-large">
                    <c:forEach begin="1" end="5" var="i">
                        <c:choose>
                            <c:when test="${i <= filledStars}">
                                <span class="star-large filled">★</span>
                            </c:when>
                            <c:otherwise>
                                <span class="star-large empty">☆</span>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
                <span class="review-count-large">レビュー ${product.review_count}件</span>
            </div>
        </div>
    </div>
    
    <!-- レビュー作成フォームボックス (最上部) -->
    <c:if test="${not empty sessionScope.userid && sessionScope.userid != 'admin'}">
        <div class="review-form-box">
            <h3>レビュー作成</h3>
            <form method="post" action="${pageContext.request.contextPath}/review/write.do" enctype="multipart/form-data">
                <input type="hidden" name="pno" value="${product.pno}">
                <input type="hidden" name="sub" value="${subcategory}">
                
                <!-- 添付ファイル -->
                <div class="form-group">
                    <label>添付ファイル :</label>
                    <input type="file" name="review_image" accept="image/*">
                </div>
                
                <!-- レビュー作成 -->
                <div class="form-group">
                    <label>レビュー作成 :</label>
                    <textarea name="review_text" placeholder="商品に関するレビューを記入してください。" required></textarea>
                </div>
                
                <!-- 評価入力 (0.0 ~ 5.0, 0.5単位) -->
                <div class="form-group">
                    <label>評価入力 :</label>
                    <select name="rating" required>
                        <option value="">選択してください</option>
                        <option value="0.0">0.0</option>
                        <option value="0.5">0.5</option>
                        <option value="1.0">1.0</option>
                        <option value="1.5">1.5</option>
                        <option value="2.0">2.0</option>
                        <option value="2.5">2.5</option>
                        <option value="3.0">3.0</option>
                        <option value="3.5">3.5</option>
                        <option value="4.0">4.0</option>
                        <option value="4.5">4.5</option>
                        <option value="5.0">5.0</option>
                    </select>
                </div>
                
                <div class="form-buttons">
                    <button type="submit" class="btn-submit">レビュー作成</button>
                </div>
            </form>
        </div>
    </c:if>
    
    <!-- レビューリスト (最上部ボックス下) -->
    <div class="review-list">
        <h3>作成されたレビュー</h3>
        <c:choose>
            <c:when test="${empty reviewList || reviewList.size() == 0}">
                <div class="empty-reviews">
                    <p>まだ作成されたレビューがありません。</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="review" items="${reviewList}">
                    <div class="review-item" id="review-item-${review.review_no}">
                        <div class="review-item-header">
                            <div class="review-author-info">
                                <div class="review-author-avatar">
                                    <c:choose>
                                        <c:when test="${not empty userProfileMap[review.userid]}">
                                            <img src="${pageContext.request.contextPath}/${userProfileMap[review.userid]}" 
                                                 alt="プロフィール写真" 
                                                 onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%22http://www.w3.org/2000/svg%22 width=%2240%22 height=%2240%22%3E%3Ccircle cx=%2220%22 cy=%2220%22 r=%2220%22 fill=%22%23ddd%22/%3E%3Ctext x=%2220%22 y=%2225%22 text-anchor=%22middle%22 font-size=%2216%22 fill=%22%23999%22%3E%3F%3C/text%3E%3C/svg%3E';">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="default-avatar">${fn:substring(review.userid, 0, 1)}</div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="review-author">
                                    <c:choose>
                                        <c:when test="${not empty review.userid}">
                                            ${review.userid}様
                                        </c:when>
                                        <c:otherwise>
                                            作成者
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="review-date">${review.regdate}</div>
                        </div>
                        <div class="review-rating-item">
                            <c:forEach begin="1" end="5" var="i">
                                <c:choose>
                                    <c:when test="${i <= review.rating}">
                                        <span class="star">★</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="star" style="color: #ddd;">☆</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <span style="margin-left: 5px; color: #666;">${review.rating}点</span>
                        </div>
                        <c:if test="${not empty review.review_image}">
                            <div class="review-image" id="review-image-${review.review_no}">
                                <p style="font-size: 14px; color: #666; margin-bottom: 5px;">添付した写真:</p>
                                <img src="${pageContext.request.contextPath}/${review.review_image}" 
                                     alt="レビュー画像"
                                     style="max-width: 100%; height: auto;"
                                     onerror="console.error('画像読み込み失敗:', this.src); this.style.display='none'; this.nextElementSibling.style.display='block';">
                                <p style="display:none; color:#999; font-size:12px; padding:10px; background:#f5f5f5; border-radius:4px;">
                                    画像を読み込めません。<br>
                                    <small>パス: ${pageContext.request.contextPath}/${review.review_image}</small>
                                </p>
                            </div>
                        </c:if>
                        <div class="review-content" id="review-content-${review.review_no}">
                            <p style="font-size: 14px; color: #666; margin-bottom: 5px;">レビュー内容:</p>
                            ${review.review_text}
                        </div>
                        
                        <!-- 編集/削除ボタン (本人作成記事または管理者の場合) -->
                        <c:if test="${not empty sessionScope.userid && (sessionScope.userid == review.userid || sessionScope.userid == 'admin')}">
                            <div class="review-actions">
                                <button type="button" class="btn-edit" onclick="toggleEditForm(${review.review_no})">編集</button>
                                <button type="button" class="btn-delete" onclick="deleteReview(${review.review_no}, '${subcategory}')">削除</button>
                            </div>
                            
                            <!-- 編集フォーム -->
                            <div class="review-edit-form" id="edit-form-${review.review_no}">
                                <form id="update-form-${review.review_no}" enctype="multipart/form-data" onsubmit="return updateReview(${review.review_no}, '${subcategory}'); return false;">
                                    <input type="hidden" name="review_no" value="${review.review_no}">
                                    <input type="hidden" name="sub" value="${subcategory}">
                                    
                                    <!-- 添付ファイル -->
                                    <div class="form-group">
                                        <label>添付ファイル (変更しない場合は選択しないでください):</label>
                                        <input type="file" name="review_image" accept="image/*">
                                        <c:if test="${not empty review.review_image}">
                                            <p style="font-size: 12px; color: #666; margin-top: 5px;">現在の画像: ${review.review_image}</p>
                                        </c:if>
                                    </div>
                                    
                                    <!-- レビュー作成 -->
                                    <div class="form-group">
                                        <label>レビュー作成 :</label>
                                        <textarea name="review_text" placeholder="商品に関するレビューを記入してください。" required>${review.review_text}</textarea>
                                    </div>
                                    
                                    <!-- 評価入力 -->
                                    <div class="form-group">
                                        <label>評価入力 :</label>
                                        <select name="rating" required>
                                            <option value="">選択してください</option>
                                            <option value="1.0" <c:if test="${review.rating == 1}">selected</c:if>>1.0</option>
                                            <option value="1.5" <c:if test="${review.rating == 2}">selected</c:if>>1.5</option>
                                            <option value="2.0" <c:if test="${review.rating == 2}">selected</c:if>>2.0</option>
                                            <option value="2.5" <c:if test="${review.rating == 3}">selected</c:if>>2.5</option>
                                            <option value="3.0" <c:if test="${review.rating == 3}">selected</c:if>>3.0</option>
                                            <option value="3.5" <c:if test="${review.rating == 4}">selected</c:if>>3.5</option>
                                            <option value="4.0" <c:if test="${review.rating == 4}">selected</c:if>>4.0</option>
                                            <option value="4.5" <c:if test="${review.rating == 5}">selected</c:if>>4.5</option>
                                            <option value="5.0" <c:if test="${review.rating == 5}">selected</c:if>>5.0</option>
                                        </select>
                                    </div>
                                    
                                    <div class="form-buttons">
                                        <button type="submit" class="btn-submit">編集完了</button>
                                        <button type="button" class="btn-cancel" onclick="toggleEditForm(${review.review_no})">キャンセル</button>
                                    </div>
                                </form>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script>
// ========== 사용자 화면에서 실행되는 JavaScript/jQuery 함수들 ==========

/**
 * [수정 폼 토글 함수]
 * 역할: 수정 버튼 클릭 시 수정 폼을 보이기/숨기기
 */
function toggleEditForm(reviewNo) {
    var form = document.getElementById('edit-form-' + reviewNo);
    if (form) {
        form.classList.toggle('active');
    }
}

/**
 * [후기 수정 함수 - AJAX 방식]
 * 역할: AJAX로 후기 수정 요청을 서버에 전송하고 결과를 처리
 * 파라미터: reviewNo (후기 번호), subcategory (카테고리)
 */
function updateReview(reviewNo, subcategory) {
    // 1단계: 유효성 검사
    var form = document.getElementById('update-form-' + reviewNo);
    var textarea = form.querySelector('textarea[name="review_text"]');
    var rating = form.querySelector('select[name="rating"]');
    
    if (!textarea.value.trim()) {
        alert('レビュー内容を入力してください。');
        textarea.focus();
        return false;
    }
    
    if (!rating.value) {
        alert('評価を選択してください。');
        rating.focus();
        return false;
    }
    
    // 2단계: FormData 객체 생성 (파일 업로드를 위해)
    var formData = new FormData(form);
    formData.append('review_no', reviewNo);
    formData.append('sub', subcategory);
    
    // 3단계: AJAX 요청 전송 (jQuery 사용)
    $.ajax({
        url: '${pageContext.request.contextPath}/review/update.do',
        type: 'POST',
        data: formData,
        processData: false,  // FormData는 자동 처리 안 함
        contentType: false,  // multipart/form-data는 자동 설정
        dataType: 'json',   // 응답을 JSON으로 파싱
        success: function(response) {
            // 성공 시 처리
            if (response.success) {
                alert(response.message);
                // 페이지 새로고침하여 수정된 내용 반영
                location.reload();
            } else {
                alert(response.message);
            }
        },
        error: function(xhr, status, error) {
            // 에러 발생 시 처리
            console.error('에러 발생:', error);
            console.error('상태 코드:', xhr.status);
            console.error('응답 텍스트:', xhr.responseText);
            
            // 서버에서 JSON 응답을 보냈지만 파싱 오류가 발생한 경우
            try {
                var response = JSON.parse(xhr.responseText);
                if (response.message) {
                    alert(response.message);
                    return;
                }
            } catch (e) {
                // JSON 파싱 실패 시 기본 에러 메시지
            }
            
            // 一般的なエラーメッセージ
            alert('レビュー編集中にエラーが発生しました。 (状態コード: ' + xhr.status + ')');
        }
    });
    
    return false;  // 폼 기본 제출 방지
}

/**
 * [후기 삭제 함수 - AJAX 방식]
 * 역할: AJAX로 후기 삭제 요청을 서버에 전송하고 결과를 처리
 * 파라미터: reviewNo (후기 번호), subcategory (카테고리)
 */
function deleteReview(reviewNo, subcategory) {
    // 1段階: 削除確認
    if (!confirm('本当に削除しますか？')) {
        return;
    }
    
    // 2단계: AJAX 요청 전송 (jQuery 사용)
    $.ajax({
        url: '${pageContext.request.contextPath}/review/delete.do',
        type: 'POST',
        data: {
            review_no: reviewNo,
            sub: subcategory
        },
        dataType: 'json',  // 응답을 JSON으로 파싱
        success: function(response) {
            // 성공 시 처리
            if (response.success) {
                alert(response.message);
                // 페이지 새로고침하여 삭제된 내용 반영
                location.reload();
            } else {
                alert(response.message);
            }
        },
        error: function(xhr, status, error) {
            // 에러 발생 시 처리
            console.error('에러 발생:', error);
            console.error('상태 코드:', xhr.status);
            console.error('응답 텍스트:', xhr.responseText);
            
            // 서버에서 JSON 응답을 보냈지만 파싱 오류가 발생한 경우
            try {
                var response = JSON.parse(xhr.responseText);
                if (response.message) {
                    alert(response.message);
                    return;
                }
            } catch (e) {
                // JSON 파싱 실패 시 기본 에러 메시지
            }
            
            // 一般的なエラーメッセージ
            alert('レビュー削除中にエラーが発生しました。 (状態コード: ' + xhr.status + ')');
        }
    });
}
</script>

<%@ include file="footer.jsp" %>
