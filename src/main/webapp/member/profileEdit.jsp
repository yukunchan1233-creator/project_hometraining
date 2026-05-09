<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../header.jsp" %>

<style>
.profile-edit-container {
    width: 800px;
    margin: 40px auto;
    padding: 30px;
    background: #1a1a1a;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.6);
    border: 1px solid rgba(255, 255, 255, 0.1);
}

.profile-edit-header {
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 2px solid rgba(255, 255, 255, 0.1);
}

.profile-edit-header h2 {
    font-size: 28px;
    font-weight: bold;
    color: #ffffff;
}

.form-group {
    margin-bottom: 25px;
}

.form-group label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
    color: #ffffff;
    font-size: 16px;
}

.form-group input[type="text"],
.form-group input[type="email"],
.form-group input[type="tel"],
.form-group input[type="password"] {
    width: 100%;
    padding: 12px;
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 5px;
    font-size: 14px;
    box-sizing: border-box;
    background: #2a2a2a;
    color: #ffffff;
}

.form-group input[readonly] {
    background: #1a1a1a;
    color: rgba(255, 255, 255, 0.5);
}

.form-buttons {
    display: flex;
    gap: 10px;
    justify-content: flex-end;
    margin-top: 30px;
    padding-top: 20px;
    border-top: 2px solid rgba(255, 255, 255, 0.1);
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

.btn-cancel {
    background: #2a2a2a;
    color: #ffffff;
    border: 1px solid rgba(255, 255, 255, 0.2);
}

.btn-cancel:hover {
    background: #3a3a3a;
    border-color: rgba(255, 255, 255, 0.3);
}

.alert {
    padding: 12px;
    margin-bottom: 20px;
    border-radius: 5px;
    background: rgba(255, 215, 0, 0.2);
    color: #ffd700;
    border: 1px solid rgba(255, 215, 0, 0.4);
}
</style>

<div class="profile-edit-container">
    <div class="profile-edit-header">
        <h2>個人情報編集</h2>
    </div>
    
    <c:if test="${sessionScope.userid == null}">
        <div class="alert">ログインが必要です。</div>
        <script>
            setTimeout(function() {
                location.href = '${pageContext.request.contextPath}/mem/login.do';
            }, 2000);
        </script>
    </c:if>
    
    <c:if test="${sessionScope.userid != null}">
        <form method="post" action="${pageContext.request.contextPath}/mem/profileUpdate.do" enctype="multipart/form-data" onsubmit="return checkForm()">
            <!-- プロフィール写真 -->
            <div class="form-group">
                <label>プロフィール写真</label>
                <div style="display: flex; align-items: center; gap: 20px; margin-bottom: 10px;">
                    <div style="width: 100px; height: 100px; border-radius: 50%; overflow: hidden; border: 2px solid #ddd; background: #f5f5f5; display: flex; align-items: center; justify-content: center;">
                        <c:choose>
                            <c:when test="${not empty member.profile_image}">
                                <img id="profilePreview" src="${pageContext.request.contextPath}/${member.profile_image}" 
                                     alt="プロフィール写真" style="width: 100%; height: 100%; object-fit: cover;">
                            </c:when>
                            <c:otherwise>
                                <img id="profilePreview" src="${pageContext.request.contextPath}/images/default-profile.png" 
                                     alt="デフォルトプロフィール" style="width: 100%; height: 100%; object-fit: cover;"
                                     onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%22http://www.w3.org/2000/svg%22 width=%22100%22 height=%22100%22%3E%3Ccircle cx=%2250%22 cy=%2250%22 r=%2250%22 fill=%22%23ddd%22/%3E%3Ctext x=%2250%22 y=%2255%22 text-anchor=%22middle%22 font-size=%2230%22 fill=%22%23999%22%3E%3F%3C/text%3E%3C/svg%3E';">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div style="flex: 1;">
                        <input type="file" name="profile_image" id="profile_image" accept="image/*" style="margin-bottom: 5px;" onchange="previewProfileImage(this)">
                        <small style="color: #666; font-size: 12px; display: block;">変更しない場合は選択しないでください。 (JPG, PNG, GIFのみ可能)</small>
                    </div>
                </div>
            </div>
            
            <div class="form-group">
                <label>ユーザーID</label>
                <input type="text" name="userid" value="${member.userid}" readonly>
            </div>
            
            <div class="form-group">
                <label>名前</label>
                <input type="text" name="writer" value="${member.writer}" required>
            </div>
            
            <div class="form-group">
                <label>電話番号</label>
                <input type="tel" name="phone" value="${member.phone}" placeholder="010-1234-5678">
            </div>
            
            <div class="form-group">
                <label>メールアドレス</label>
                <input type="email" name="email" value="${member.email}" required>
            </div>
            
            <div class="form-group">
                <label>パスワード変更 (変更しない場合は空欄のまま)</label>
                <input type="password" name="password" placeholder="新しいパスワード">
                <small style="color: #666; font-size: 12px;">Googleログインユーザーはパスワードを変更できません。</small>
            </div>
            
            <div class="form-buttons">
                <button type="button" class="btn-cancel" onclick="location.href='${pageContext.request.contextPath}/mem/mypageMain.do'">キャンセル</button>
                <button type="submit" class="btn-submit">編集</button>
            </div>
        </form>
    </c:if>
</div>

<script>
    var contextPath = "${pageContext.request.contextPath}";
    
    function checkForm() {
        var writer = document.getElementsByName("writer")[0].value;
        var email = document.getElementsByName("email")[0].value;
        
        if(!writer || writer.trim() === "") {
            alert("名前を入力してください。");
            return false;
        }
        
        if(!email || email.trim() === "") {
            alert("メールアドレスを入力してください。");
            return false;
        }
        
        return true;
    }
    
    function previewProfileImage(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('profilePreview').src = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>

<%@ include file="../footer.jsp" %>
