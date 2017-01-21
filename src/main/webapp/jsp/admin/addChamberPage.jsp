<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.hospital.add.chamber"/></title>
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
            <input type="hidden" name="redirectTo" value="manageHospital"/>
            <input type="submit" name="goToStaff" value="<fmt:message key='message.back'/>"/>
        </form>
    </nav>
    <div class="main">
        <h3><fmt:message key="message.hospital.add.chamber"/>:</h3><br/>
        <form class="formBox2" name="registration" method="post"
              action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="addChamber"/>

            <input type="number" name="numb" value="${sessionScope.chamber.number}"
                   placeholder="<fmt:message key='message.hospital.add.number'/>" min="1">
            <div class="error">
                <c:if test="${not empty sessionScope.incorrectNumber}">
                    <fmt:message key="message.hospital.error.number"/><br/>
                </c:if>
            </div>

            <input type="number" name="count" value="${sessionScope.chamber.maxCount}"
                   placeholder="<fmt:message key='message.hospital.add.capacity'/>" min="1" max="20">
            <div class="error">
                <c:if test="${not empty sessionScope.incorrectCapacity}">
                    <fmt:message key="message.hospital.error.capacity"/><br/>
                </c:if>
            </div>

            <label><fmt:message key="message.hospital.type"/>:
                <select name="chambersType">
                    <c:forEach items="${sessionScope.chambersType}" var="item">
                        <option value="${item.idChamberType}">${item.chamberName}</option>
                    </c:forEach>
                </select>
            </label><br/><br/>

            <input type="submit" name="add" value="<fmt:message key='message.hospital.add'/>"><br/>
        </form>
    </div>
    <footer>
        <jsp:include page="../footer.jsp"/>
    </footer>
</div>
</body>
</html>
