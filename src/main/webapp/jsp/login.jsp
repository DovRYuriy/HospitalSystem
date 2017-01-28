<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.login.title"/></title>
    <link rel="icon" href="../resources/image/favicon.ico" type="image/ico">
    <link rel="stylesheet" href="../resources/style/login.css" type="text/css">
</head>
<body>
<div class="container">
    <p class="caption_p"><fmt:message key="message.login.signin"/></p>
    <form name="LoginForm" method="POST" action="${pageContext.request.contextPath}/controller/" class="formBox">
        <input type="hidden" name="command" value="login"/>
        <br/>
        <input type="text" placeholder=" <fmt:message key='message.login.login'/>" name="login"
               value="${sessionScope.login}"/><br/>
        <br/>
        <input type="password" placeholder="<fmt:message key='message.login.password'/>" name="password"/><br/>
        <div class="error">
            <c:if test="${not empty sessionScope.errorLoginPassMessage}">
                <fmt:message key="message.loginpass.error"/><br/>
            </c:if>
        </div>
        <input type="submit" value="<fmt:message key='message.login.btn.login'/>"/>
        <div class="countryBox">
            <a href="${pageContext.request.contextPath}/controller/?command=changeLanguage&language=en">
                <img border="1px" src="<c:url value="/resources/image/lang/usa.png" />" alt="en" width="25"
                     height="18"/>
            </a>
            <a href="${pageContext.request.contextPath}/controller/?command=changeLanguage&language=ru">
                <img border="1px" src="<c:url value="/resources/image/lang/ru.png" />" alt="ru" width="25" height="18"/>
            </a>
            <a href="${pageContext.request.contextPath}/controller/?command=changeLanguage&language=uk">
                <img border="1px" src="<c:url value="/resources/image/lang/ukr.png" />" alt="uk" width="25"
                     height="18"/>
            </a>
        </div>
    </form>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
</div>
</body>
</html>
