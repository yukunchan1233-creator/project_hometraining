<%-- 
    [메인 페이지 - index.jsp]
    설명: 웹사이트의 첫 화면으로, 최신 포트폴리오 목록을 보여줍니다.
    흐름: 사용자가 "/" 또는 "/main.do" 접속 → MainController → BlogSelectIndex 서비스 → BlogDao → DB 조회 → JSP 출력
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- header.jsp를 포함하여 상단 메뉴와 공통 레이아웃을 가져옵니다 --%>
<%@ include file="header.jsp" %>
    
    <%-- 메인 비주얼 영역: 큰 제목과 부제목을 표시 --%>
    <%-- 메인 비주얼 영역: 베스트 상품 슬라이더 --%>
<div class="mainvisual">
    <div class="visual-inner">
        <%-- 왼쪽 화살표 --%>
        <button class="slider-btn prev-btn" id="prevBtn">‹</button>
        
        <%-- 슬라이더 컨테이너 --%>
        <div class="slider-container">
            <div class="slider-wrapper">
                <%-- 슬라이드 1 --%>
                <div class="slide active">
                    <div class="slide-link">
                        <img src="${pageContext.request.contextPath}/images/index_running.png" alt="베스트 상품 1" style="display: block;">
                        <div class="slide-content">
                            <h2>ランニングマシン</h2>
                            <%-- ボタンクリック時、ランニングマシンポートフォリオへ移動 --%>
                            <a href="${pageContext.request.contextPath}/portfolio.do?sub=런닝머신" class="slide-btn">詳細を見る</a>
                        </div>
                    </div>
                </div>
                
                <%-- 슬라이드 2 --%>
                <div class="slide">
                    <div class="slide-link">
                        <img src="${pageContext.request.contextPath}/images/index_cycle.png" alt="베스트 상품 2">
                        <div class="slide-content">
                            <h2>サイクル</h2>
                            <%-- ボタンクリック時、サイクルポートフォリオへ移動 --%>
                            <a href="${pageContext.request.contextPath}/portfolio.do?sub=사이클" class="slide-btn">詳細を見る</a>
                        </div>
                    </div>
                </div>
                
                <%-- 슬라이드 3 --%>
                <div class="slide">
                    <div class="slide-link">
                        <img src="${pageContext.request.contextPath}/images/index_benchpress.png" alt="베스트 상품 3">
                        <div class="slide-content">
                            <h2>ベンチプレス</h2>
                            <%-- ボタンクリック時、ベンチプレスポートフォリオへ移動 --%>
                            <a href="${pageContext.request.contextPath}/portfolio.do?sub=벤치프레스" class="slide-btn">詳細を見る</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <%-- 오른쪽 화살표 --%>
        <button class="slider-btn next-btn" id="nextBtn">›</button>
        
        <%-- 인디케이터 (선택사항) --%>
        <div class="slider-indicators">
            <span class="indicator active" data-slide="0"></span>
            <span class="indicator" data-slide="1"></span>
            <span class="indicator" data-slide="2"></span>
        </div>
    </div>
</div>

    <%-- カテゴリーアイコンセクション --%>
    <div class="category-icons">
        <div class="category-icons-inner">
            <a href="${pageContext.request.contextPath}/portfolio.do?sub=벤치프레스" class="category-icon" title="ベンチプレス">
                <div class="icon-circle">
                    <img src="${pageContext.request.contextPath}/images/benchpress_icon.png" alt="ベンチプレス">
                </div>
                <span>ベンチプレス</span>
            </a>
            <a href="${pageContext.request.contextPath}/portfolio.do?sub=사이클" class="category-icon" title="サイクル">
                <div class="icon-circle">
                    <img src="${pageContext.request.contextPath}/images/cycle_icon.jpg" alt="サイクル">
                </div>
                <span>サイクル</span>
            </a>
            <a href="${pageContext.request.contextPath}/portfolio.do?sub=런닝머신" class="category-icon" title="ランニングマシン">
                <div class="icon-circle">
                    <img src="${pageContext.request.contextPath}/images/running_icon.png" alt="ランニングマシン">
                </div>
                <span>ランニングマシン</span>
            </a>
            <a href="${pageContext.request.contextPath}/portfolio.do?sub=덤벨" class="category-icon" title="ダンベル">
                <div class="icon-circle">
                    <img src="${pageContext.request.contextPath}/images/dumbel_icon.png" alt="ダンベル">
                </div>
                <span>ダンベル</span>
            </a>
        </div>
    </div>
    
    <%-- 컨테이너 영역: 포트폴리오 목록을 표시 --%>
    <div class="container">
        <%-- 
            JSTL forEach를 사용하여 서버에서 전달받은 list를 반복 출력합니다.
            ${list}는 MainController에서 BlogSelectIndex 서비스를 통해 설정된 request 속성입니다.
            varStatus를 사용하여 최대 3개만 출력합니다.
        --%>
        <c:forEach var="item" items="${list }" varStatus="status">
        	<c:if test="${status.index < 3}">
		        <div class="box">
		            <div class="over">
		            	<%-- 포트폴리오 이미지 표시 --%>
		            	<img src="${pageContext.request.contextPath}/img/${item.imgfile }">
		            </div>
		            <a href="${pageContext.request.contextPath}/port/view.do?bno=${item.bno}">
		                <%-- 포트폴리오 제목 표시 --%>
		                <h3 class="portTitle">${item.title }</h3>
		                <%-- 포트폴리오 내용의 처음 20자만 표시 (substring(0,20)) --%>
		                <p class="txt" style="height:100px; overflow: hidden;">${item.content.substring(0,20) }</p>
		            </a>
		        </div>
        	</c:if>
        </c:forEach>
    </div>
    
    <%-- レビューカルーセルセクション (フッター上) --%>
    <c:if test="${not empty reviewCarouselList}">
    <div class="review-carousel-section">
        <div class="review-carousel-header">
            <h2>正直なレビューで検証されたお客様の理由ある選択</h2>
        </div>
        <div class="review-carousel-container">
            <div class="review-carousel-wrapper" id="reviewCarousel">
                <c:forEach var="review" items="${reviewCarouselList}">
                    <div class="review-carousel-item" 
                         onclick="location.href='${pageContext.request.contextPath}/review/view.do?pno=${review.pno}&sub=${review.subcategory}'"
                         style="cursor: pointer;">
                        <div class="review-item-image">
                            <c:choose>
                                <c:when test="${not empty review.review_image}">
                                    <img src="${pageContext.request.contextPath}/${review.review_image}" 
                                         alt="후기 이미지"
                                         onerror="this.src='${pageContext.request.contextPath}/images/image_1.jpg'; this.onerror=null;">
                                </c:when>
                                <c:when test="${not empty review.product_image}">
                                    <img src="${pageContext.request.contextPath}/${review.product_image}" 
                                         alt="${review.product_name}"
                                         onerror="this.src='${pageContext.request.contextPath}/images/image_1.jpg'; this.onerror=null;">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/images/image_1.jpg" alt="이미지 없음">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="review-item-rating">
                            <c:forEach begin="1" end="5" var="i">
                                <c:choose>
                                    <c:when test="${i <= review.rating}">
                                        <span class="star filled">★</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="star empty">☆</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                        <div class="review-item-text">
                            <c:choose>
                                <c:when test="${fn:length(review.review_text) > 60}">
                                    ${fn:substring(review.review_text, 0, 60)}...
                                </c:when>
                                <c:otherwise>
                                    ${review.review_text}
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="review-item-product">
                            <span class="product-icon">🛍</span>
                            <span class="product-name">
                                <c:choose>
                                    <c:when test="${fn:length(review.product_name) > 30}">
                                        ${fn:substring(review.product_name, 0, 30)}...
                                    </c:when>
                                    <c:otherwise>
                                        ${review.product_name}
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <div class="review-item-date">
                            <c:choose>
                                <c:when test="${not empty review.regdate}">
                                    ${review.regdate}
                                </c:when>
                                <c:otherwise>
                                    최근
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    </c:if>
    
<%-- footer.jsp를 포함하여 하단 푸터를 가져옵니다 --%>
<%@ include file="footer.jsp" %>

<script>
// 후기 캐러셀 자동 슬라이드
$(document).ready(function() {
    var $carousel = $('#reviewCarousel');
    if($carousel.length === 0) return; // 캐러셀이 없으면 종료
    
    var $items = $carousel.find('.review-carousel-item');
    var itemCount = $items.length;
    if(itemCount === 0) return;
    
    var visibleCount = 6;
    var currentIndex = 0;
    var isAnimating = false;
    
    // 반응형으로 visibleCount 조정
    function updateVisibleCount() {
        var windowWidth = $(window).width();
        if(windowWidth <= 768) {
            visibleCount = 3;
        } else if(windowWidth <= 1200) {
            visibleCount = 4;
        } else if(windowWidth <= 1400) {
            visibleCount = 5;
        } else {
            visibleCount = 6;
        }
    }
    
    updateVisibleCount();
    $(window).on('resize', function() {
        updateVisibleCount();
    });
    
    // 자동 슬라이드 함수
    function slideNext() {
        if(itemCount <= visibleCount || isAnimating) {
            return; // 후기가 적거나 애니메이션 중이면 슬라이드 불필요
        }
        
        isAnimating = true;
        currentIndex++;
        var maxIndex = itemCount - visibleCount;
        
        // 끝에 도달하면 처음으로 (무한 루프)
        if(currentIndex > maxIndex) {
            currentIndex = 0;
        }
        
        // gap을 고려한 이동 거리 계산
        var gap = 20; // CSS gap 값
        var containerWidth = $carousel.parent().width();
        var actualItemWidth = (containerWidth - (gap * (visibleCount - 1))) / visibleCount;
        var translateX = -(currentIndex * (actualItemWidth + gap));
        
        $carousel.css({
            'transform': 'translateX(' + translateX + 'px)',
            'transition': 'transform 0.6s cubic-bezier(0.4, 0, 0.2, 1)'
        });
        
        // 애니메이션 완료 후 플래그 해제
        setTimeout(function() {
            isAnimating = false;
        }, 600);
    }
    
    // 3초마다 자동 슬라이드
    if(itemCount > visibleCount) {
        setInterval(slideNext, 3000);
    }
});
</script>
