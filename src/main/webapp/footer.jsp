<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <footer class="footer">
        <div class="footer-inner">
            <p>Copyright HOMETRAINING &copy; All Rights Reserved.</p>
        </div>
    </footer>
    
    <script>
    // context path를 JavaScript 변수로 설정 (전역 변수)
    if(typeof contextPath === 'undefined') {
        var contextPath = "${pageContext.request.contextPath}";
    }
    </script>
    <script src = "${pageContext.request.contextPath}/js/jquery.cookie.min.js"></script>
    <script src = "${pageContext.request.contextPath}/js/my.js"></script>
    
</body>
</html>

