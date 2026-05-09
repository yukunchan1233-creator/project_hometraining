<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
    
    <div class="subvisual">
        <div class="sub-inner">
            <div class="sub-title">
                <h2>PORTFOLIO</h2>
                <p>Goooooooooood Man</p>
            </div>
        </div>
    </div>
    
    <div class="sub-container">
        <div class="writebox">
            <form name="my" method="post" action="${pageContext.request.contextPath}/port/writepro.do" enctype="multipart/form-data" onsubmit="return check()">
                <table class="border-table">
                    <tr>
                        <th>作成者</th>
                        <td><input type="text" name="name" value="java02" readonly></td>
                    </tr>
                    <tr>
                        <th>タイトル</th>
                        <td><input type="text" name="title" id="title"></td>
                    </tr>
                    <tr>
                        <th>内容</th>
                        <td>
                        	<textarea name="content" id="content"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>添付ファイル</th>
                        <td>
                            <input type="file" name="imgFile">
                            <img src="${pageContext.request.contextPath}/images/image_1.jpg" alt="" style="width: 36px; height: 36px;">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button type="submit" class="submit">保存</button>
                            <button type="reset" class="reset" onclick="alert('全て消去して書き直しますか？')">書き直し</button>
                            <button type="button" class="list" onclick="location.href='${pageContext.request.contextPath}/port/list.do'">リスト</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    
<script>
    // context path를 JavaScript 변수로 설정
    var contextPath = "${pageContext.request.contextPath}";
    
	function check() {
		if(!my.title.value) {
			alert("タイトルを入力してください");
			my.title.focus();
			return false;
		}
		if(!my.content.value) {
			alert("内容を入力してください");
			my.content.focus();
			return false;
		}
		if(!my.imgFile.value) {
			alert("添付画像を追加してください");
			my.imgFile.focus();
			return false;
		}
		alert("登録完了");
		return true;
	}
	
</script>
    
<%@ include file="../footer.jsp" %>




