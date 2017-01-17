<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.registration"/></title>
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
    <div class="main">
        <h3><fmt:message key="message.registration.form"/>:</h3><br/>
        <form class="formBox2" name="registration" method="post"
              action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="registrationPerson"/>

            <input type="text" name="name" value="${sessionScope.person.name}"
                   placeholder="<fmt:message key='message.profile.name'/>"/><br/>
            <div class="error">
                <c:if test="${not empty sessionScope.incorrectData.incorrectName}">
                    <fmt:message key="message.registration.error.name"/><br/>
                </c:if>
            </div>

            <input type="text" name="surname" value="${sessionScope.person.surname}"
                   placeholder="<fmt:message key='message.profile.surname'/>"/><br/>
            <div class="error">
                <c:if test="${not empty sessionScope.incorrectData.incorrectSurname}">
                    <fmt:message key="message.registration.error.surname"/><br/>
                </c:if>
            </div>

            <input type="date" name="birthday" value="${sessionScope.person.birthday}" min="1940-01-01" max="2000-01-01"
                   placeholder="<fmt:message key='message.profile.birthday'/>"/><br/>
            <div class="error">
                <c:if test="${not empty sessionScope.incorrectData.incorrectDate}">
                    <fmt:message key="message.registration.error.birtday"/><br/>
                </c:if>
            </div>

            <input type="text" name="phone" value="${sessionScope.person.phone}"
                   placeholder="Phone number"><br/>
            <div class="error">
                <c:if test="${not empty sessionScope.incorrectData.incorrectPhone}">
                    <fmt:message key="message.registration.error.phone"/><br/>
                </c:if>
            </div>

            <label><fmt:message key="message.role"/>:
                <select name="role">
                    <c:forEach items="${sessionScope.roles}" var="item">
                        <option value="${item.idRole}">${item.name}</option>
                    </c:forEach>
                </select>
            </label><br/>

            <label><fmt:message key="message.cabinet"/>:
                <select name="chambers">
                    <c:forEach items="${sessionScope.chambers}" var="item">
                        <option value="${item.idChamber}">${item.number}</option>
                    </c:forEach>
                </select>
            </label><br/><br/>

            <input type="email" name="email" value="${sessionScope.person.email}" placeholder="Email"/><br/>
            <div class="error">
                <c:if test="${not empty sessionScope.incorrectData.incorrectEmail}">
                    <fmt:message key="message.registration.error.email"/><br/>
                </c:if>
            </div>
            <input type="submit" name="register" value="<fmt:message key='message.registration.btn'/>"><br/>
        </form>
    </div>
    <footer>
        <jsp:include page="../footer.jsp"/>
    </footer>
</div>
</body>
</html>
