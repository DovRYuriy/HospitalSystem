<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.patient.profile"/></title>
    <link rel="icon" href="../../../resources/image/favicon.ico" type="image/ico">
    <link rel="stylesheet" href="<c:url value="../../../resources/style/style.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="../../../resources/style/style1.css"/>" type="text/css">
</head>
<body>
<div class="container">
    <header>
        <jsp:include page="../../header.jsp"/>
        <div class="linkRef">
            <a href="${pageContext.request.contextPath}/controller/?command=logout"><fmt:message
                    key="message.logout"/></a>
        </div>
    </header>
</div>
<div class="container1">
    <nav>
        <form name="goToProfile" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="nurseProfile"/>
            <input type="submit" name="goToProfile" value="<fmt:message key='message.patient.profile'/>"/>
        </form>

        <form name="goToMyPatients" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="nurseMainPage"/>
            <input type="submit" name="goToStaff" value="<fmt:message key='message.doctor.patients'/>"/>
        </form>
    </nav>
    <div class="motherCountry">
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
        <br/>
        <div>
            <h3><fmt:message key="message.profile.changepass"/>:</h3>
            <form name="changePasswordForm" method="POST" action="${pageContext.request.contextPath}/controller/"
                  class="formBox2">
                <input type="hidden" name="command" value="changePassword"/>
                <input type="password" name="newpass"
                       placeholder="<fmt:message key='message.profile.changepass.new'/>"/>
                <br/>
                <input type="password" name="newrepeat"
                       placeholder="<fmt:message key='message.profile.changepass.newrepeat'/>"/>
                <br/>
                <div class="error">
                    <c:if test="${not empty sessionScope.error}">
                        <fmt:message key="message.profile.changepass.error"/><br/>
                    </c:if>
                </div>
                <input type="submit" name="changePassword"
                       value="<fmt:message key='message.profile.changepass.change'/>"/>
            </form>

            <h3><fmt:message key="message.profile.update"/>:</h3>
            <form name="updateProfileForm" method="POST" action="${pageContext.request.contextPath}/controller/"
                  class="formBox2">
                <input type="hidden" name="command" value="updateProfile"/>
                <input type="text" name="name" value="${sessionScope.user.name}"
                       placeholder="<fmt:message key='message.profile.name'/>"
                       pattern="[A-Z][a-z]+|[А-Я][а-я]+" title="First capital letter (eg. Alex or Алексей)"/><br/>
                <div class="error">
                    <c:if test="${not empty sessionScope.incorrectData.incorrectName}">
                        <fmt:message key="message.registration.error.name"/><br/>
                    </c:if>
                </div>

                <input type="text" name="surname" value="${sessionScope.user.surname}"
                       placeholder="<fmt:message key='message.profile.surname'/>"
                       pattern="[A-Z][a-z]+|[А-Я][а-я]+" title="First capital letter (eg. Zhuk or Жук)"/><br/>
                <div class="error">
                    <c:if test="${not empty sessionScope.incorrectData.incorrectSurname}">
                        <fmt:message key="message.registration.error.surname"/><br/>
                    </c:if>
                </div>

                <input type="text" name="birthday" value="${sessionScope.user.birthday}"
                       placeholder="<fmt:message key='message.profile.birthday'/>" readonly/><br/>

                <input type="text" name="phone" value="${sessionScope.user.phone}"
                       placeholder="<fmt:message key='message.profile.phone'/>"
                       pattern="[0-9]{10}" title="10 digit number (e.g. 0630574512)"/><br/>

                <div class="error">
                    <c:if test="${not empty sessionScope.incorrectData.incorrectPhone}">
                        <fmt:message key="message.registration.error.phone"/><br/>
                    </c:if>
                </div>

                <input type="text" name="email" value="${sessionScope.user.email}"
                       placeholder="<fmt:message key='message.profile.email'/>" readonly/><br/>
                <input type="submit" name="updateProfile" value="<fmt:message key="message.profile.update.btn"/>">

            </form>
        </div>
    </div>
    <footer>
        <jsp:include page="../../footer.jsp"/>
    </footer>
</div>
</body>
</html>
