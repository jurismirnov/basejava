<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.model.TextListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganisationListSection" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
        <h3>${sectionEntry.key.title}</h3>
        <c:choose>
            <c:when test="${sectionEntry.key =='OBJECTIVE' || sectionEntry.key =='PERSONAL' }">
                <p>
                    <%=((TextSection) sectionEntry.getValue()).getText()%>
                </p>
            </c:when>
            <c:when test="${sectionEntry.key=='QUALIFICATIONS' || sectionEntry.key=='ACHIEVEMENT'}">
                <c:forEach var="record" items="<%=((TextListSection) sectionEntry.getValue()).getRecords()%>">
                    <p>${record}</p><br/>
                </c:forEach>
            </c:when>
            <c:when test="${sectionEntry.key=='EXPERIENCE' || sectionEntry.key=='EDUCATION'}">
                <c:forEach var="organisation"
                           items="<%=((OrganisationListSection) sectionEntry.getValue()).getOrganisationList()%>">
                    <c:if test="${empty organisation.httpLink}">
                        <h4>"${organisation.firmName}"</h4>
                    </c:if>
                    <c:if test="${not empty organisation.httpLink}">
                        <h4><a href="${organisation.httpLink}">"${organisation.firmName}"</a></h4>
                    </c:if>

                    <c:forEach var="position" items="${organisation.positionList}">
                        <c:set var="sDate" value=" ${position.startDate}"/>
                        <jsp:useBean id="sDate" type="java.lang.String"/>
                        <c:set var="eDate" value=" ${position.endDate}"/>
                        <jsp:useBean id="eDate" type="java.lang.String"/>
                        From: ${sDate}     to: ${eDate}<br/>
                        ${position.position}<br/>
                        ${position.description}<br/>
                        <br/>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
    <button onclick="window.history.back()">Назад</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
