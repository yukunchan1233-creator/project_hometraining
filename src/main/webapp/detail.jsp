<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String pnoStr = request.getParameter("pno");
	int pno = 0;
	if(pnoStr != null) {
		pno = Integer.parseInt(pnoStr);
	}
	
	ProductDao dao = new ProductDao();
	ProductDto dto = dao.selectProduct(pno);
	
	if(dto == null) {
		response.sendRedirect("index.jsp");
		return;
	}
	
	// 상세 이미지들을 배열로 분리 (쉼표로 구분)
	String[] detailImages = null;
	if(dto.getDetail_images() != null && !dto.getDetail_images().isEmpty()) {
		detailImages = dto.getDetail_images().split(",");
	}
	
	request.setAttribute("dto", dto);
	request.setAttribute("detailImages", detailImages);
%>
<%@ include file="header.jsp" %>

<!-- 商品詳細情報 -->
<div class="product-detail">
	<h2>${dto.product_name}</h2>
	
	<div class="product-info">
		<div class="product-main-image">
			<c:choose>
				<c:when test="${not empty dto.image_path}">
					<img src="${dto.image_path}" alt="${dto.product_name}">
				</c:when>
				<c:otherwise>
					<img src="images/no-image.jpg" alt="画像なし">
				</c:otherwise>
			</c:choose>
		</div>
		<div class="product-details">
			<h3>${dto.site_name} - ${dto.product_name}</h3>
			<div class="price"><%
				ProductDto product = (ProductDto)request.getAttribute("dto");
				out.print(String.format("%,d", product.getPrice()) + "円");
			%></div>
			<div class="review">レビュー: ${dto.review_count}件</div>
			<div style="margin-top:30px;">
				<strong>カテゴリー:</strong> ${dto.category} > ${dto.subcategory}
			</div>
		</div>
	</div>
	
	<!-- 詳細画像ギャラリー -->
	<c:if test="${not empty detailImages && detailImages.length > 0}">
		<div class="detail-gallery">
			<h3>商品詳細画像</h3>
			<div class="gallery-images">
				<c:forEach var="img" items="${detailImages}">
					<c:if test="${not empty img && img.trim() != ''}">
						<img src="${img.trim()}" alt="商品詳細画像">
					</c:if>
				</c:forEach>
			</div>
		</div>
	</c:if>
	
	<!-- 購入リンク -->
	<div class="buy-link">
		<a href="${dto.buy_link}" target="_blank">
			${dto.site_name}で購入する
		</a>
	</div>
</div>

<%@ include file="footer.jsp" %>
