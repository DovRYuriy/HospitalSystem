<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dt" uri="/WEB-INF/date-format.tld" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.set.diagnosis.title"/></title>
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
    <nav>
        <c:choose>
            <c:when test="${empty sessionScope.person.idPerson}">
                <form name="goToStaffPage" method="GET" action="${pageContext.request.contextPath}/controller/">
                    <input type="hidden" name="command" value="redirect"/>
                    <input type="hidden" name="redirectTo" value="allPatients"/>
                    <input type="submit" name="goToAllPatients" value="<fmt:message key='message.back'/>"/>
                </form>
            </c:when>
            <c:otherwise>
                <form name="goToStaffPage" method="GET" action="${pageContext.request.contextPath}/controller/">
                    <input type="hidden" name="command" value="redirect"/>
                    <input type="hidden" name="id" value="${sessionScope.person.idPerson}"/>
                    <input type="hidden" name="redirectTo" value="editPatientPage"/>
                    <input type="submit" name="goToStaff" value="<fmt:message key='message.back'/>"/>
                </form>
            </c:otherwise>
        </c:choose>
    </nav>
    <div class="main">
        <h3><fmt:message key="message.set.diagnosis.title"/>:</h3>
        <form name="addNew" id="usrform" action="${pageContext.request.contextPath}/controller/" method="POST"
              class="formBox2">
            <input type="hidden" name="command" value="setDiagnosis"/>
            <input type="text" name="diagnosis" value="${sessionScope.diagnosis.name}"
                   pattern="[A-Z][a-z]+|[А-Я][а-я]+"
                   placeholder="<fmt:message key='message.patient.diagnosis'/>"/>
            <br/>
            <div class="error">
                <c:if test="${not empty sessionScope.incorrectDiagnosis}">
                    <fmt:message key="message.registration.error.diagnosis"/><br/>
                </c:if>
            </div>
            <br/>
            <textarea rows="4" cols="50" name="description" form="usrform">
                <c:choose>
                    <c:when test="${empty sessionScope.diagnosis.description}">
                        <fmt:message key="message.diagnosis.description"/>
                    </c:when>
                    <c:otherwise>
                        ${sessionScope.diagnosis.description}
                    </c:otherwise>
                </c:choose>
            </textarea><br/><br/>

            <input type="text" name="drugs" value="${sessionScope.prescript.drugs}"
                   placeholder="<fmt:message key='message.prescription.drugs'/>"/><br/><br/>
            <input type="text" name="procedure" value="${sessionScope.prescript.procedure}"
                   placeholder="<fmt:message key='message.prescription.procedure'/>"/><br/><br/>
            <input type="text" name="operation" value="${sessionScope.prescript.operation}"
                   placeholder="<fmt:message key='message.prescription.operation'/>"/><br/><br/>

            <c:if test="${not empty sessionScope.personChamber}">
                <label><fmt:message key="message.chamber"/>:
                    <select name="chambers">
                        <c:forEach items="${sessionScope.chambers}" var="item">
                            <option value="${item.idChamber}">${item.number}</option>
                        </c:forEach>
                    </select>
                </label><br/><br/>
            </c:if>

            <input type="submit" name="ok" value="<fmt:message key='message.set.diagnosis'/>"><br/>
        </form>
    </div>
    <footer>
        <jsp:include page="../../footer.jsp"/>
    </footer>
</div>
</body>
</html>
