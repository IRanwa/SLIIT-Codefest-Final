<%-- 
    Document   : Tables
    Created on : Oct 2, 2018, 1:24:27 PM
    Author     : Frank
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            table {
                font-family: arial, sans-serif;
                border-collapse: collapse;
                width: 100%;
            }

            td, th {
                border: 1px solid #dddddd;
                text-align: left;
                padding: 8px;
            }

            tr:nth-child(even) {
                background-color:#f2f2f2;
            }

            th{
                background-color: green;
            }

            body {
                font-size: 20px;
            }
        </style>
    </head>
    <body>

        <h2>Table</h2>

        <table>
            <tr>
                <th>Employee ID</th>
                <th>Production Percentage</th>
                <th>Error Rate</th>

            </tr>

            <c:forEach var="lis" items="${plist}">


                <tr>
                    <td>${lis.empID}</td>
                    <td>${lis.eError}
                        <c:if test="${lis.eError}>${lis.errorbenchmark}">
                            *
                        </c:if>
                    </td>
                    <td>${lis.eProcess}
                        <c:if test="${lis.eProcess}<${lis.processbenchmark}">
                            *
                        </c:if>
                    </td>




                </tr>
            </c:forEach>

            <c:out value="${avg}"/>
        </table>

    </body>
</html>

