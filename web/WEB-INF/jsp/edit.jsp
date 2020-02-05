<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Редактируем резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name='fullName' size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты</h3>
        <p>
            <c:forEach var="conType" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><input type="text" name='${conType.name()}'size=30 value="${resume.getContact(conType)}"></dd>
        </dl>
        </c:forEach>
        <hr>
        <c:forEach var="section" items="<%=SectionType.values()%>">
            <h4>${section.title}</h4>
            <c:choose>
                <c:when test="${section =='OBJECTIVE' || section =='PERSONAL' }">
                    <c:set var="textData" value="${resume.getSection(section)}"/>
                    <jsp:useBean id="textData" type="ru.javawebinar.basejava.model.Section"
                                 class="ru.javawebinar.basejava.model.TextSection"/>
                    <textarea name='${section}' cols=100 rows=3><%=((TextSection) textData).getText()%></textarea>
                </c:when>
                <c:when test="${section=='QUALIFICATIONS' || section=='ACHIEVEMENT'}">
                    <c:set var="textListData" value="${resume.getSection(section)}"/>
                    <jsp:useBean id="textListData" type="ru.javawebinar.basejava.model.Section"
                                 class="ru.javawebinar.basejava.model.TextListSection"/>
                    <textarea name='${section}' cols=100
                              rows=5><%=String.join("\n", ((TextListSection) textListData).getRecords())%></textarea>
                </c:when>
                <c:when test="${section=='EXPERIENCE' || section=='EDUCATION'}">
                    <c:set var="organisations" value="${resume.getSection(section)}"/>
                    <jsp:useBean id="organisations" type="ru.javawebinar.basejava.model.Section"
                                 class="ru.javawebinar.basejava.model.OrganisationListSection"/>
                    <c:forEach var="organisation"
                               items="<%=((OrganisationListSection) organisations).getOrganisationList()%>" varStatus="counter">
                        <c:if test="${not empty organisation}">
                            <dl>
                                <dt>
                                    <b>Учереждение: </b>
                                </dt>
                                <dd><input type="text" name='${type}' size=70 value="${organisation.firmName}">
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    Сайт:
                                </dt>
                                <dd>
                                    <input type="text" name='${type}url' size=70 value="${organisation.httpLink}">
                                </dd>
                            </dl>
                            <c:set var="positions" value="${organisation.positionList}"/>
                            <c:forEach var="position" items="${positions}">
                                <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Position"
                                             class="ru.javawebinar.basejava.model.Position"/>
                                <dl>
                                    <dt>Позиция:</dt>
                                    <dd>
                                        <input type="text" name='${type}${counter.index}position' size=70 value="${position.position}">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Начало:</dt>
                                    <dd>
                                        <input type="text" name='${type}${counter.index}sDate' size=15 value="${position.startDate}">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>
                                        Окончание:
                                    </dt>
                                    <dd><input type="text" name='${type}${counter.index}eDate' size=15
                                               value="${position.endDate}">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>
                                        Описание:
                                    </dt>
                                    <dd><textarea name='${type}${counter.index}desc' rows=3
                                                  cols=71>${position.description}</textarea>
                                    </dd>
                                </dl>
                                <br>
                            </c:forEach>
                            </p>
                        </c:if>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
