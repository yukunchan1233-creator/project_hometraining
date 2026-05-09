<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String category = request.getParameter("cat");
	String subcategory = request.getParameter("sub");
	if(category == null) category = "가슴";
	if(subcategory == null) subcategory = "벤치프레스";
	
	ProductDao dao = new ProductDao();
	List<ProductDto> list = dao.selectProducts(category, subcategory);
	request.setAttribute("list", list);
	request.setAttribute("category", category);
	request.setAttribute("subcategory", subcategory);
%>
<%@ include file="header.jsp" %>

<!-- サブビジュアル -->
<div class="subvisual">
	<h2>${category} - ${subcategory}</h2>
	<p>様々なブランドの${subcategory}商品を比較してみてください</p>
</div>

<!-- 商品リスト : カード型 -->
<div class="shop-container">
    <div class="product-grid">
        <c:choose>
            <c:when test="${empty list || list.size() == 0}">
                <div class="empty-box">登録された商品がありません。</div>
            </c:when>
            <c:otherwise>
                <c:forEach var="dto" items="${list}">
                    <div class="product-card" onclick="location.href='detail.jsp?pno=${dto.pno}'">
                        <div class="brand-row">
                            <span class="site-badge">${dto.site_name}</span>
                            <span class="review-count">レビュー ${dto.review_count}件</span>
                        </div>

                        <div class="product-name">${dto.product_name}</div>

                        <div class="price-row">
                            <span class="price"><%
                                ProductDto item = (ProductDto)pageContext.getAttribute("dto");
                                if(item != null) {
                                    out.print(String.format("%,d", item.getPrice()) + "円");
                                }
                            %></span>
                            <button class="view-btn" type="button"
                                    onclick="event.stopPropagation(); location.href='detail.jsp?pno=${dto.pno}';">
                                詳細を見る
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="footer.jsp" %>
