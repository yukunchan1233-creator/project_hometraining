<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../header.jsp" %>

<style>
.product-form-container {
    width: 800px;
    margin: 40px auto;
    padding: 30px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.product-form-header {
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 2px solid #e1e1e1;
}

.product-form-header h2 {
    font-size: 28px;
    font-weight: bold;
    color: #333;
}

.form-group {
    margin-bottom: 25px;
}

.form-group label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
    color: #333;
    font-size: 16px;
}

.form-group input[type="text"],
.form-group input[type="file"],
.form-group input[type="number"] {
    width: 100%;
    padding: 12px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 14px;
    box-sizing: border-box;
}

.form-group input[type="file"] {
    padding: 8px;
}

.form-buttons {
    display: flex;
    gap: 10px;
    justify-content: flex-end;
    margin-top: 30px;
    padding-top: 20px;
    border-top: 2px solid #e1e1e1;
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
    background: #2196F3;
    color: white;
}

.btn-submit:hover {
    background: #0096C7;
}

.btn-cancel {
    background: #f5f5f5;
    color: #333;
}

.btn-cancel:hover {
    background: #e0e0e0;
}

.btn-delete {
    background: #0077B6;
    color: white;
}

.btn-delete:hover {
    background: #006494;
}

.current-image {
    margin-top: 10px;
}

.current-image img {
    max-width: 200px;
    max-height: 200px;
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 5px;
}
</style>

<div class="product-form-container">
    <div class="product-form-header">
        <h2>${product != null ? '商品編集' : '商品追加'}</h2>
    </div>
    
    <form method="post" action="${pageContext.request.contextPath}/admin/product/${product != null ? 'updatepro.do' : 'addpro.do'}" enctype="multipart/form-data">
        <c:if test="${product != null}">
            <input type="hidden" name="pno" value="${product.pno}">
            <input type="hidden" name="image_path" value="${product.image_path}">
        </c:if>
        <input type="hidden" name="subcategory" value="${subcategory}">
        
        <!-- 代表画像添付 -->
        <div class="form-group">
            <label>代表画像添付 :</label>
            <input type="file" name="image_file" accept="image/*">
            <c:if test="${product != null && product.image_path != null}">
                <div class="current-image">
                    <p style="font-size: 14px; color: #666; margin: 10px 0 5px 0;">現在の画像:</p>
                    <img src="${pageContext.request.contextPath}/${product.image_path}" alt="現在の画像">
                </div>
            </c:if>
        </div>
        
        <!-- 商品名(ブランド名) -->
        <div class="form-group">
            <label>商品名(ブランド名) :</label>
            <input type="text" name="product_name" value="${product != null ? product.product_name : ''}" required>
        </div>
        
        <!-- サイト名 (ブランド名から抽出または直接入力) -->
        <div class="form-group">
            <label>サイト名 (ブランド名) :</label>
            <input type="text" name="site_name" value="${product != null ? product.site_name : ''}" placeholder="例: イゴジン、バンソクなど" required>
        </div>
        
        <!-- 公式ホームページリンク -->
        <div class="form-group">
            <label>公式ホームページリンク :</label>
            <input type="text" name="buy_link" value="${product != null ? product.buy_link : ''}" placeholder="https://...">
        </div>
        
        <!-- 価格 -->
        <div class="form-group">
            <label>価格 :</label>
            <input type="number" name="price" value="${product != null && product.price != null ? product.price : ''}" placeholder="例: 500000" min="0" step="1">
            <small style="color: #666; font-size: 12px; display: block; margin-top: 5px;">数字のみ入力してください (例: 500000 = 500,000円)</small>
        </div>
        
        <!-- カテゴリー及び下位カテゴリー (編集モードでのみ表示) -->
        <c:if test="${product != null}">
            <input type="hidden" name="category" value="${product.category}">
        </c:if>
        <c:if test="${product == null}">
            <div class="form-group">
                <label>カテゴリー :</label>
                <select name="category" required style="width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px;">
                    <option value="">選択してください</option>
                    <option value="유산소">有酸素運動</option>
                    <option value="근력">筋力運動</option>
                </select>
            </div>
        </c:if>
        
        <div class="form-buttons">
            <button type="button" class="btn-cancel" onclick="location.href='${pageContext.request.contextPath}/portfolio.do?sub=${subcategory}'">キャンセル</button>
            <button type="submit" class="btn-submit">${product != null ? '編集' : '追加'}</button>
            <c:if test="${product != null}">
                <button type="button" class="btn-delete" onclick="if(confirm('本当に削除しますか？')) { location.href='${pageContext.request.contextPath}/admin/product/delete.do?pno=${product.pno}&sub=${subcategory}'; }">削除</button>
            </c:if>
        </div>
    </form>
</div>

<%@ include file="../footer.jsp" %>
