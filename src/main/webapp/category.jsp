<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String category = request.getParameter("cat");
	if(category == null) category = "가슴";
	
	ProductDao dao = new ProductDao();
	List<String> subcategories = dao.selectSubcategories(category);
	request.setAttribute("subcategories", subcategories);
	request.setAttribute("category", category);
%>
<%@ include file="header.jsp" %>

<!-- サブビジュアル -->
<div class="subvisual">
	<h2>${category} 運動器具</h2>
	<p>${category} 運動に最適化された運動器具を選択してください</p>
</div>

<!-- カテゴリーリスト -->
<div class="container">
	<div class="category-list">
		<c:forEach var="sub" items="${subcategories}">
			<div class="category-item" onclick="location.href='product.jsp?cat=${category}&sub=${sub}'">
				<h3>${sub}</h3>
				<p>クリックして商品を見る</p>
			</div>
		</c:forEach>
	</div>
</div>

<%@ include file="footer.jsp" %>
