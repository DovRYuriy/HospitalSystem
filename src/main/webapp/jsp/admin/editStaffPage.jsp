<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>
<html>
<head>
    <title><fmt:message key="message.admin.change"/></title>
    <link rel="icon" href="../../resources/image/favicon.ico" type="image/ico">
    <link rel="stylesheet" href="<c:url value="../../resources/style/style.css"/>" type="text/css">
    <link rel="stylesheet" href="<c:url value="../../resources/style/style1.css"/>" type="text/css">

</head>
<body>
<div class="container">
    <header>
        <jsp:include page="../header.jsp"/>
        <div class="linkRef">
            <a href="${pageContext.request.contextPath}/controller/?command=logout"><fmt:message
                    key="message.logout"/></a>
        </div>
    </header>
</div>
<div class="container1">
    <nav>
        <form name="goToStaffPage" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="adminMainPage"/>
            <input type="submit" name="goToStaff" value="<fmt:message key='message.back'/>"/>
        </form>
    </nav>
    <div>
        <c:choose>
            <c:when test="${empty sessionScope.invalidPerson}">
                <h3><fmt:message key="message.admin.change"/>:</h3>
                <form name="changeStaffData" method="POST" action="${pageContext.request.contextPath}/controller/"
                      class="formBox2">
                    <input type="hidden" name="command" value="changeStaffData"/>

                    <input type="text" name="name" value="${sessionScope.person.name}"
                           placeholder="<fmt:message key='message.profile.name'/>"/>
                    <div class="error">
                        <c:if test="${not empty sessionScope.incorrectData.incorrectName}">
                            <fmt:message key="message.registration.error.name"/><br/>
                        </c:if>
                    </div>
                    <br/>

                    <input type="text" name="surname" value="${sessionScope.person.surname}"
                           placeholder="<fmt:message key='message.profile.surname'/>"/>
                    <div class="error">
                        <c:if test="${not empty sessionScope.incorrectData.incorrectSurname}">
                            <fmt:message key="message.registration.error.surname"/><br/>
                        </c:if>
                    </div>
                    <br/>

                    <input type="text" name="phone" value="${sessionScope.person.phone}"
                           placeholder="<fmt:message key='message.profile.phone'/>"/><br/>
                    <div class="error">
                        <c:if test="${not empty sessionScope.incorrectData.incorrectPhone}">
                            <fmt:message key="message.registration.error.phone"/><br/>
                        </c:if>
                    </div>
                    <br/>

                    <input type="email" name="email" value="${sessionScope.person.email}"
                           placeholder="<fmt:message key='message.profile.email'/>"/><br/>
                    <div class="error">
                        <c:if test="${not empty sessionScope.incorrectData.incorrectEmail}">
                            <fmt:message key="message.registration.error.email"/><br/>
                        </c:if>
                    </div>
                    <br/>

                    <input type="submit" name="updateProfile" value="<fmt:message key="message.profile.update.btn"/>">
                </form>
            </c:when>
            <c:otherwise>
                <h2><fmt:message key="message.invalid.person"/>!</h2>
            </c:otherwise>
        </c:choose>
    </div>
    <footer>
        <jsp:include page="../footer.jsp"/>
    </footer>
</div>
</body>
</html>
