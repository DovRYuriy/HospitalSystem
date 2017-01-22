<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.doctor.patients"/></title>
    <link rel="icon" href="../../../resources/image/favicon.ico" type="image/ico">
    <link rel="stylesheet" href="<c:url value="../../../resources/style/style.css"/>" type="text/css">
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
    <div>
        <h1><fmt:message key="message.patient.hello"/>, ${sessionScope.user.name} ${sessionScope.user.surname}.</h1>
    </div>
    <nav>
        <form name="goToProfile" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="doctorProfile"/>
            <input type="submit" name="goToProfile" value="<fmt:message key='message.patient.profile'/>"/>
        </form>

        <form name="goToMyPatients" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="doctorMainPage"/>
            <input type="submit" name="goToStaff" value="<fmt:message key='message.doctor.patients'/>"/>
        </form>

        <form name="goToAllPatients" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="allPatients"/>
            <input type="submit" name="goToPatients" value="<fmt:message key='message.doctor.patients.all'/>"/>
        </form>
    </nav>
    <div>
        <h3><fmt:message key='message.doctor.patients'/>:</h3>
        <c:choose>
            <c:when test="${not empty sessionScope.myPatients}">
                <table border="1px">
                    <tr>
                        <th><fmt:message key="message.profile.name"/></th>
                        <th><fmt:message key="message.profile.surname"/></th>
                        <th><fmt:message key="message.profile.phone"/></th>
                        <th><fmt:message key="message.doctor.service"/></th>
                        <th><fmt:message key="message.doctor.discharge"/></th>
                    </tr>
                    <c:forEach items="${sessionScope.myPatients}" var="item">
                        <tr>
                            <td>${item.name}</td>
                            <td>${item.surname}</td>
                            <td>${item.phone}</td>
                            <td>
                                <a href="?command=redirect&redirectTo=editPatientPage&id=${item.idPerson}">
                                    <fmt:message key="message.doctor.service"/></a>
                            </td>
                            <td>
                                <a href="?command=redirect&redirectTo=dischargePatientPage&id=${item.idPerson}">
                                    <fmt:message key="message.doctor.discharge"/></a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <h3><fmt:message key="message.doctor.patients.empty"/></h3>
            </c:otherwise>
        </c:choose>
    </div>
    <div>
        <c:choose>
            <c:when test="${empty sessionScope.blockRegistration}">
                <form name="registrationForm" method="GET" action="${pageContext.request.contextPath}/controller/">
                    <input type="hidden" name="command" value="redirect"/>
                    <input type="hidden" name="redirectTo" value="registrationPatient"/>
                    <input class="regButton" type="submit" name="goToRegister"
                           value="<fmt:message key='message.admin.registration'/>"/>
                </form>
            </c:when>
            <c:otherwise>
                <h3><fmt:message key="message.registration.block"/></h3>
            </c:otherwise>
        </c:choose>
    </div>
    <footer>
        <jsp:include page="../../footer.jsp"/>
    </footer>
</div>
</body>
</html>
