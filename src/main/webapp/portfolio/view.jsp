<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../header.jsp"%>

<div class="sub-container">
	<div class="sub-left">
		<h3 class="subtit">ポートフォリオ</h3>
		<div class="btn-sns">
			<a href="">ブログ</a> <a href="">Facebook</a> <a href="">Instagram</a>
		</div>
	</div>
	<div class="board-view">
		<div class="board-header">
			<span class="date">Regdate : ${viewdto.regdate.substring(0,10) }</span>
			<strong>${viewdto.title }</strong> <span class="icon-view">views
				: ${viewdto.views }</span>
		</div>
		<div class="board-body">
			<p>楽しい旅行に行ってきました。</p>
			<p>
				<img src="${pageContext.request.contextPath}/img/${viewdto.imgfile}" alt="">
			</p>
			<p>${viewdto.content }</p>
		</div>
		<div class="board-controller">
			<a href="" class="prev"> <strong>Previous</strong> <span>ヨーロッパ旅行</span>
			</a> <a href="" class="next"> <strong>Next</strong> <span>エベレスト
					旅行</span>
			</a>
		</div>
		<div class="btn-board">
			<button type="button" class="submit"
				onclick="location.href='${pageContext.request.contextPath}/port/list.do'">リスト</button>
			<button type="button" class="reset"
				onclick="updateGet(${viewdto.bno})">編集</button>
			<button type="button" class="list"
				onclick="deleteGet(${viewdto.bno})">削除</button>
		</div>
	</div>
</div>
	

<script>
    // context path를 JavaScript 변수로 설정
    var contextPath = "${pageContext.request.contextPath}";
    
	// jQuery開始
	$(function() {
		// ブログ削除
		$(".list").on("click", function() {
			var bno = ${viewdto.bno};
			if(confirm("削除しますか？")) {
				location.href = contextPath + "/port/delete.do?bno=" + bno;
			}
		});
		
		// ブログ編集
		$(".reset").on("click", function() {
			var bno = ${viewdto.bno};
			if(confirm("編集しますか？")) {
				location.href = contextPath + "/port/update.do?bno=" + bno;
			}
		});
	}); // end of $(function()
</script>
<%@ include file="../footer.jsp"%>




