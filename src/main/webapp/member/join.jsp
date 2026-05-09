<%-- 
    [회원가입 페이지 - join.jsp]
    설명: 새로운 회원을 등록하는 폼 페이지입니다.
    흐름: 사용자가 정보 입력 → JavaScript 유효성 검사 → AJAX로 아이디 중복 체크 → 
          "회원가입" 버튼 클릭 → /mem/membersave.do로 POST 요청 → MemberController → 
          MemberSave 서비스 → MemberDao → DB 저장 → 메인 페이지로 리다이렉트
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
    
    <%-- 회원가입 폼 영역1 --%>
    <div class="sub-container">
        <div class="join-wrapper">
            <div class="join-card">
                <h2 class="join-title">会員登録</h2>
                <%-- 
                    会員登録フォーム
                    - name="my": JavaScriptでフォームを参照するための名前
                    - id="myform": jQueryでフォームを参照するためのID
                    - actionとmethodはJavaScriptで動的に設定されます (my.js参照)
                --%>
                <form name="my" id="myform" class="join-form">
                    <%-- プロフィール写真アップロード --%>
                    <div class="profile-section">
                        <div class="profile-preview">
                            <img id="profile-preview-img" src="${pageContext.request.contextPath}/images/image_1.jpg" alt="プロフィールプレビュー">
                            <div class="profile-overlay">
                                <label for="imgFile" class="profile-upload-btn">
                                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                        <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
                                        <polyline points="17 8 12 3 7 8"></polyline>
                                        <line x1="12" y1="3" x2="12" y2="15"></line>
                                    </svg>
                                    <span>写真変更</span>
                                </label>
                            </div>
                        </div>
                        <input type="file" name="imgFile" id="imgFile" accept="image/*" style="display: none;">
                        <p class="profile-hint">プロフィール写真を登録してください (任意)</p>
                    </div>
                    
                    <div class="form-group">
                        <label for="writer" class="form-label">ユーザー名</label>
                        <input type="text" name="writer" id="writer" class="form-input" placeholder="ユーザー名を入力してください">
                        <p class="error-msg writer-msg"></p>
                    </div>
                    
                    <div class="form-group">
                        <label for="userid" class="form-label">ユーザーID</label>
                        <input type="text" name="userid" id="userid" class="form-input" placeholder="ユーザーIDを入力してください">
                        <p class="error-msg userid-msg"></p>
                    </div>
                    
                    <div class="form-group">
                        <label for="password" class="form-label">パスワード</label>
                        <input type="password" name="password" id="password" class="form-input" placeholder="パスワードを入力してください">
                        <p class="error-msg password-msg"></p>
                    </div>
                    
                    <div class="form-group">
                        <label for="pw2" class="form-label">パスワード確認</label>
                        <input type="password" name="passwordcheck" id="pw2" class="form-input" placeholder="パスワードを再度入力してください">
                        <p class="error-msg pw2-msg"></p>
                    </div>
                    
                    <div class="form-group">
                        <label for="email" class="form-label">メールアドレス</label>
                        <input type="email" name="email" id="email" class="form-input" placeholder="メールアドレスを入力してください">
                        <p class="error-msg email-msg"></p>
                    </div>
                    
                    <div class="form-group">
                        <label for="phone" class="form-label">電話番号</label>
                        <input type="text" name="phone" id="phone" class="form-input" placeholder="電話番号を入力してください">
                        <p class="error-msg phone-msg"></p>
                    </div>
                    
                    <div class="form-actions">
                        <button type="button" class="btn-primary" id="btn-submit">会員登録</button>
                        <button type="button" class="btn-secondary" id="btn-reset">書き直し</button>
                        <button type="button" class="btn-outline" onclick="javascript:history.go(-1)">戻る</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script>
    // context path를 JavaScript 변수로 설정
    var contextPath = "${pageContext.request.contextPath}";
    
    // 프로필사진 미리보기
    $(function() {
        $("#imgFile").on("change", function(e) {
            var file = e.target.files[0];
            if(file) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    $("#profile-preview-img").attr("src", e.target.result);
                };
                reader.readAsDataURL(file);
            }
        });
    });
    </script>
    
<%@ include file="../footer.jsp" %>




