<%-- 
    Document   : reports
    Created on : Oct 3, 2018, 3:48:07 AM
    Author     : Pamuditha
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <title>Reports Page</title>
        <script>
            function getEmployeename() {
                var startDate = document.getElementById("sDAte").value;
                var endDate = document.getElementById("eDate").value;
                var steps = document.getElementById("steps").value;
                var timePeriod = document.getElementById("timePeriod").value;
                window.location = "ReportServlet?command=Get-Employees&startDate=" + startDate
                        + "&endDate=" + endDate + "&step=" + steps + "&timePeriod=" + timePeriod;
            }
        </script>
    </head>
    <body>
        <form action="ReportServlet" method="post" class="w3-margin w3-amber w3-round"
              style="display: inline-block; margin:25%; width:75%;" >
            <p class="w3-margin">Date Range</p> 
            <input type="date" name="startDate" class="w3-margin-left w3-margin-right" id="sDAte" required>
            <p class="w3-margin">Till</p>
            <input type="date" name="endDate" class="w3-margin-left w3-margin-right" id="eDate" required>
            <br><br>

            <p class="w3-margin">Step Id</p>
            
                <select name="Stepid" class="w3-margin-left w3-margin-right" id="steps" required>
                    <c:forEach var="steps" items="${steps}">
                        <option value="${steps}">${steps}</option>
                    </c:forEach>
                </select>
           
            <br>
            <p class="w3-margin" >Time Period</p>
            <select name="timePeriod" class="w3-margin-left w3-margin-right" id="timePeriod" onclick="getEmployeename()" required>
                <option value="Hourly">Hourly</option>
                <option value="Daily">Daily</option>
                <option value="Monthly">Monthly</option>
                <option value="Yearly">Yearly</option>
            </select>
            <br>
            <p class="w3-margin">Employee Name</p>
            <select name="EmpId" class="w3-margin-left w3-margin-right" id="employees" required>
                    <c:forEach var="emp" items="${empList}">
                        <option value="${emp}">${emp}</option>
                    </c:forEach>
                </select>
            <br>
            <input type="submit" value="Submit" class="w3-button w3-black w3-round w3-margin"/>
            
        </form>

        <div class="w3-margin" style="width:90%">
            <canvas id="barchart"></canvas>
        </div>
        
        <script>
            var color = Chart.helpers.color;
            var records;
            //barchart dataset (Every 1 second)
            var barChartData = {
                labels: steps,
                datasets: [{
                        label: 'Process Rate',
                        backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.blue,
                        borderWidth: 1,
                        data: [0, 0, 0, 0, 0
                        ]
                    }, {
                        label: 'Error Percentage',
                        backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.red,
                        borderWidth: 1,
                        data: [0, 0, 0, 0, 0
                        ]
                    }]

            };
            window.onload = function () {
                var ctx = document.getElementById('barchart').getContext('2d');
                window.barChart = new Chart(ctx, {
                    type: 'bar',
                    data: barChartData,
                    options: {
                        responsive: true,
                        legend: {
                            position: 'bottom',
                        },
                        title: {
                            display: true,
                            text: 'Real Time Product Line'
                        }
                    }
                });
            };

        </script>
    </body>
</html>
