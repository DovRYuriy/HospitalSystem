<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.hospital.manage"/></title>
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
            <h3><fmt:message key="message.hospital"/>:</h3>
            <div class="errorRemove">
                <c:if test="${not empty sessionScope.removeChamberFailed}">
                    <fmt:message key="message.remove.chamber.failed"/><br/>
                </c:if>
            </div>
            <table border="2px">
                <tr>
                    <th><fmt:message key="message.hospital.number"/></th>
                    <th><fmt:message key="message.hospital.capacity"/></th>
                    <th><fmt:message key="message.crud.remove"/></th>
                </tr>
                <c:forEach items="${sessionScope.chambersInHospital}" var="var">
                    <tr>
                        <td>${var.number}</td>
                        <td>${var.maxCount}</td>
                        <td>
                            <form name="removeForm" action="${pageContext.request.contextPath}/controller/"
                                  method="POST">
                                <input type="hidden" name="command" value="removeChamber"/>
                                <input type="hidden" name="id" value="${var.idChamber}"/>
                                <input type="submit" name="submitRemove"
                                       value="<fmt:message key='message.crud.remove'/>">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div>
            <form name="addChamberForm" method="GET" action="${pageContext.request.contextPath}/controller/">
                <input type="hidden" name="command" value="redirect"/>
                <input type="hidden" name="redirectTo" value="addChamberPage"/>
                <input class="regButton" type="submit" name="goToAdd"
                       value="<fmt:message key='message.hospital.add'/>"/>
            </form>
        </div>
    </div>

    <footer>
        <jsp:include page="../footer.jsp"/>
    </footer>
</div>
</body>
</html>
