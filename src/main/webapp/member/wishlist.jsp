<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../header.jsp" %>

<style>
.wishlist-container {
    width: 1200px;
    margin: 40px auto;
    padding: 0 20px;
    background: #0a0a0a;
}

.wishlist-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 2px solid rgba(255, 255, 255, 0.1);
}

.wishlist-header h2 {
    font-size: 32px;
    font-weight: bold;
    color: #ffffff;
    margin: 0;
}

.wishlist-header .header-actions {
    display: flex;
    align-items: center;
    gap: 15px;
}

.wishlist-header .select-all {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
    cursor: pointer;
}

.wishlist-header .select-all input[type="checkbox"] {
    width: 18px;
    height: 18px;
    cursor: pointer;
}

.wishlist-header .delete-selected-btn {
    padding: 12px 24px;
    background: #4a9eff;
    color: #fff;
    border: none;
    border-radius: 6px;
    font-size: 15px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s;
}

.wishlist-header .delete-selected-btn:hover {
    background: #6bb0ff;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(74, 158, 255, 0.4);
}

.wishlist-empty {
    text-align: center;
    padding: 100px 20px;
    color: rgba(255, 255, 255, 0.5);
}

.wishlist-empty .empty-icon {
    font-size: 64px;
    margin-bottom: 20px;
}

.wishlist-empty h3 {
    font-size: 24px;
    color: rgba(255, 255, 255, 0.7);
    margin-bottom: 10px;
}

.wishlist-empty p {
    font-size: 16px;
    color: rgba(255, 255, 255, 0.5);
}

.wishlist-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 30px;
    margin-bottom: 40px;
    contain: layout style paint;
}

.wishlist-item {
    background: #1a1a1a;
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 12px;
    overflow: hidden;
    transition: box-shadow 0.2s ease;
    position: relative;
}

.wishlist-item:hover {
    box-shadow: 0 4px 12px rgba(255, 215, 0, 0.3);
    border-color: #ffd700;
}

.wishlist-item .item-checkbox {
    position: absolute;
    top: 15px;
    left: 15px;
    z-index: 10;
    width: 22px;
    height: 22px;
    cursor: pointer;
    accent-color: #ffd700;
}

.wishlist-item .item-image {
    width: 100%;
    height: 200px;
    overflow: hidden;
    background: #2a2a2a;
    position: relative;
}

.wishlist-item .item-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
    background: #2a2a2a;
}

.wishlist-item .item-content {
    padding: 20px;
}

.wishlist-item .item-meta {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 12px;
    color: rgba(255, 255, 255, 0.5);
    margin-bottom: 12px;
}

.wishlist-item .item-title {
    font-size: 18px;
    font-weight: 600;
    color: #ffffff;
    margin-bottom: 10px;
    line-height: 1.4;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.wishlist-item .item-title a {
    color: #ffffff;
    text-decoration: none;
    transition: color 0.3s;
}

.wishlist-item .item-title a:hover {
    color: #ffd700;
}

.wishlist-item .item-excerpt {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
    line-height: 1.6;
    margin-bottom: 15px;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.wishlist-item .item-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 15px;
    border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.wishlist-item .item-date {
    font-size: 12px;
    color: rgba(255, 255, 255, 0.5);
}

.wishlist-item .item-delete {
    padding: 8px 16px;
    background: transparent;
    color: #4a9eff;
    border: 1px solid #4a9eff;
    border-radius: 6px;
    font-size: 13px;
    cursor: pointer;
    transition: all 0.3s;
    font-weight: 500;
}

.wishlist-item .item-delete:hover {
    background: #4a9eff;
    color: #fff;
}

.wishlist-pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 8px;
    margin-top: 40px;
    padding-top: 30px;
    border-top: 1px solid #e1e1e1;
}

.wishlist-pagination a {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: 40px;
    border: 1px solid #e1e1e1;
    border-radius: 6px;
    color: #666;
    text-decoration: none;
    transition: all 0.3s;
    font-size: 14px;
}

.wishlist-pagination a:hover {
    border-color: #00B4D8;
    color: #00B4D8;
    background: #fff5f0;
}

.wishlist-pagination a.active {
    background: #00B4D8;
    border-color: #00B4D8;
    color: #fff;
}

@media (max-width: 768px) {
    .wishlist-container {
        width: 100%;
        padding: 20px;
    }
    
    .wishlist-grid {
        grid-template-columns: 1fr;
        gap: 20px;
    }
    
    .wishlist-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 15px;
    }
    
    .wishlist-header .header-actions {
        width: 100%;
        justify-content: space-between;
    }
}
</style>

<div class="wishlist-container">
    <div class="wishlist-header">
        <h2>❤️ お気に入りリスト</h2>
        <div class="header-actions">
            <label class="select-all">
                <input type="checkbox" id="selectAll">
                <span>全選択</span>
            </label>
            <button class="delete-selected-btn" id="deleteSelected">選択削除</button>
        </div>
    </div>
    
    <c:choose>
        <c:when test="${empty wishlist || wishlist.size() == 0}">
            <div class="wishlist-empty">
                <div class="empty-icon">📭</div>
                <h3>お気に入り項目がありません</h3>
                <p>気に入ったポートフォリオをお気に入りに追加してみてください！</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="wishlist-grid">
                <c:forEach var="item" items="${wishlist}">
                    <div class="wishlist-item">
                        <input type="checkbox" class="item-checkbox blogCheckBox" value="${item.wish_bno}" data-type="${item.type}">
                        <div class="item-image">
                            <c:choose>
                                <c:when test="${item.type == 'product'}">
                                    <c:choose>
                                        <c:when test="${not empty item.imgfile}">
                                            <img src="${pageContext.request.contextPath}/${item.imgfile}" 
                                                 alt="${item.product_name}" 
                                                 loading="lazy"
                                                 onerror="this.src='${pageContext.request.contextPath}/images/image_1.jpg'; this.onerror=null;">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/images/image_1.jpg" alt="${item.product_name}">
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/img/${item.imgfile}" 
                                         alt="${item.title}"
                                         loading="lazy"
                                         onerror="this.src='${pageContext.request.contextPath}/images/image_1.jpg'; this.onerror=null;">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="item-content">
                            <div class="item-meta">
                                <c:choose>
                                    <c:when test="${item.type == 'product'}">
                                        <span>제품 No.${item.pno}</span>
                                        <span>${item.site_name}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span>No.${item.bno}</span>
                                        <span>조회수 ${item.views}</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <h3 class="item-title">
                                <c:choose>
                                    <c:when test="${item.type == 'product'}">
                                        <a href="${pageContext.request.contextPath}/review/view.do?pno=${item.pno}&sub=${item.subcategory}">${item.product_name}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/port/view.do?bno=${item.bno}">${item.title}</a>
                                    </c:otherwise>
                                </c:choose>
                            </h3>
                            <div class="item-excerpt">
                                <c:choose>
                                    <c:when test="${item.type == 'product'}">
                                        <span style="color: #00B4D8; font-weight: bold;">
                                            <c:if test="${item.price > 0}">
                                                <%= String.format("%,d", ((model.WishDto)pageContext.getAttribute("item")).getPrice()) %>円
                                            </c:if>
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        ${item.content}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="item-footer">
                                <span class="item-date">
                                    <c:choose>
                                        <c:when test="${fn:length(item.regdate) >= 10}">
                                            ${item.regdate.substring(0, 10)}
                                        </c:when>
                                        <c:otherwise>
                                            ${item.regdate}
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                                <button class="item-delete deleteBtn" 
                                        data-wish_bno="${item.wish_bno}" 
                                        data-type="${item.type}"
                                        <c:if test="${item.type == 'product'}">data-pno="${item.pno}"</c:if>>削除</button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            
            <c:if test="${totalPages > 1}">
                <div class="wishlist-pagination">
                    <c:if test="${startPage > 1}">
                        <a href="${pageContext.request.contextPath}/mem/mypage.do?page=${startPage-1}">«</a>
                    </c:if>
                    <c:forEach begin="${startPage}" end="${endPage}" var="p">
                        <a href="${pageContext.request.contextPath}/mem/mypage.do?page=${p}" 
                           ${p==currentPage?'class="active"':''}>${p}</a>
                    </c:forEach>
                    <c:if test="${endPage < totalPages}">
                        <a href="${pageContext.request.contextPath}/mem/mypage.do?page=${endPage+1}">»</a>
                    </c:if>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>

<script>
    var contextPath = "${pageContext.request.contextPath}";
    
    // 전체 선택/해제 (이벤트 위임 사용)
    $(document).on("change", "#selectAll", function() {
        $(".blogCheckBox").prop("checked", $(this).prop("checked"));
    });
    
    // 개별 체크박스 변경 시 전체 선택 상태 업데이트 (디바운싱)
    var updateSelectAllTimeout;
    $(document).on("change", ".blogCheckBox", function() {
        clearTimeout(updateSelectAllTimeout);
        updateSelectAllTimeout = setTimeout(function() {
            var total = $(".blogCheckBox").length;
            var checked = $(".blogCheckBox:checked").length;
            $("#selectAll").prop("checked", total === checked);
        }, 10);
    });
    
    // 개별 삭제
    $(document).on("click", ".deleteBtn", function() {
        var wishBno = $(this).data("wish_bno");
        var type = $(this).data("type") || "blog";  // 기본값은 blog
        var $item = $(this).closest(".wishlist-item");
        
        if (!confirm("本当に削除しますか？")) {
            return;
        }
        
        $.ajax({
            type: 'post',
            data: {wishBno: wishBno, type: type},
            url: contextPath + '/mem/mywishDelete.do',
            success: function(res) {
                if (res.trim() === "success") {
                    $item.fadeOut(300, function() {
                        $(this).remove();
                        // 항목이 없으면 빈 상태 표시
                        if ($(".wishlist-item").length === 0) {
                            location.reload();
                        }
                    });
                } else {
                    alert("削除に失敗しました。");
                }
            },
            error: function() {
                alert("エラーが発生しました。");
            }
        });
    });
    
    // 選択削除 (イベント委譲使用)
    $(document).on("click", "#deleteSelected", function() {
        var checked = $(".blogCheckBox:checked");
        
        if (checked.length === 0) {
            alert("削除する項目を選択してください。");
            return;
        }
        
        if (!confirm("選択した " + checked.length + "個の項目を削除しますか？")) {
            return;
        }
        
        // 타입별로 분리
        var blogWishBnos = [];
        var productWishBnos = [];
        
        checked.each(function() {
            var type = $(this).data("type") || "blog";
            if(type === "product") {
                productWishBnos.push($(this).val());
            } else {
                blogWishBnos.push($(this).val());
            }
        });
        
        // 블로그 찜과 제품 찜을 각각 삭제
        var deletePromises = [];
        
        if(blogWishBnos.length > 0) {
            deletePromises.push(
                $.ajax({
                    type: 'post',
                    data: {wishBnos: blogWishBnos.join(",")},
                    url: contextPath + '/mem/mywishDeleteSelected.do'
                })
            );
        }
        
        if(productWishBnos.length > 0) {
            deletePromises.push(
                $.ajax({
                    type: 'post',
                    data: {wishBnos: productWishBnos.join(","), type: "product"},
                    url: contextPath + '/mem/mywishDeleteSelected.do'
                })
            );
        }
        
        $.when.apply($, deletePromises).done(function() {
            var allSuccess = true;
            for(var i = 0; i < arguments.length; i++) {
                if(arguments[i][0].trim() !== "success") {
                    allSuccess = false;
                    break;
                }
            }
            
            if(allSuccess) {
                var $items = checked.closest(".wishlist-item");
                $items.fadeOut(200, function() {
                    $(this).remove();
                    // 항목이 없으면 빈 상태 표시
                    if ($(".wishlist-item").length === 0) {
                        location.reload();
                    }
                });
                $("#selectAll").prop("checked", false);
            } else {
                alert("一部の項目の削除に失敗しました。");
            }
        }).fail(function() {
            alert("エラーが発生しました。");
        });
    });
</script>

<%@ include file="../footer.jsp" %>
