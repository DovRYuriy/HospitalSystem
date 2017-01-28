<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.admin.staff.manage"/></title>
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
    <div>
        <h1><fmt:message key="message.patient.hello"/>, ${sessionScope.user.name} ${sessionScope.user.surname}.</h1>
    </div>
    <nav>
        <form name="goToProfile" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="adminProfile"/>
            <input type="submit" name="goToProfile" value="<fmt:message key='message.patient.profile'/>"/>
        </form>

        <form name="goToHospitalPage" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="manageHospital"/>
            <input type="submit" name="goToStaff" value="<fmt:message key='message.hospital.manage'/>"/>
        </form>

        <form name="goToStaffPage" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="adminMainPage"/>
            <input type="submit" name="goToStaff" value="<fmt:message key='message.admin.staff.manage'/>"/>
        </form>
    </nav>
    <div>
        <div>
            <h3><fmt:message key="message.admin.staff"/>:</h3>
            <c:choose>
                <c:when test="${not empty sessionScope.staffInHospital}">
                    <div class="errorRemove">
                        <c:if test="${not empty sessionScope.removeFailed}">
                            <fmt:message key="message.remove.failed"/><br/>
                        </c:if>
                    </div>
                    <table border="2px">
                        <tr>
                            <th><fmt:message key="message.profile.name"/></th>
                            <th><fmt:message key="message.profile.surname"/></th>
                            <th><fmt:message key="message.profile.birthday"/></th>
                            <th><fmt:message key="message.profile.phone"/></th>
                            <th><fmt:message key="message.profile.email"/></th>
                            <th><fmt:message key="message.profile.post"/></th>
                            <th><fmt:message key="message.crud.edit"/></th>
                            <th><fmt:message key="message.crud.remove"/></th>
                        </tr>
                        <c:forEach items="${sessionScope.staffInHospital}" var="var">
                            <tr>
                                <td>${var.name}</td>
                                <td>${var.surname}</td>
                                <td>${var.birthday}</td>
                                <td>${var.phone}</td>
                                <td>${var.email}</td>
                                <td>${var.role.name}</td>
                                <td>
                                    <a href="?command=redirect&redirectTo=editStaffPage&id=${var.idPerson}"><fmt:message
                                            key="message.crud.edit"/></a>
                                </td>
                                <td>
                                    <form name="removeForm" action="${pageContext.request.contextPath}/controller/"
                                          method="POST">
                                        <input type="hidden" name="command" value="remove"/>
                                        <input type="hidden" name="id" value="${var.idPerson}"/>
                                        <input type="submit" name="submitRemove"
                                               value="<fmt:message key='message.crud.remove'/>">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <fmt:message key="message.admin.staff.empty"/>
                </c:otherwise>
            </c:choose>
        </div>
        <div>
            <c:choose>
                <c:when test="${empty sessionScope.blockRegistration}">
                    <form name="registrationForm" method="GET" action="${pageContext.request.contextPath}/controller/">
                        <input type="hidden" name="command" value="redirect"/>
                        <input type="hidden" name="redirectTo" value="registration"/>
                        <input class="regButton" type="submit" name="goToRegister"
                               value="<fmt:message key='message.admin.registration'/>"/>
                    </form>
                </c:when>
                <c:otherwise>
                    <h3><fmt:message key="message.registration.block"/></h3>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <footer>
        <jsp:include page="../footer.jsp"/>
    </footer>
</div>
</body>
</html>
