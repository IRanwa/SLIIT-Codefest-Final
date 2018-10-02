<%-- 
    Document   : Home
    Created on : Oct 2, 2018, 10:52:27 AM
    Author     : Frank
--%>
<%@page import="java.util.Random"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="codefest.Calculations"%>
<%@page import="Model.DAO"%>
<%@page import="codefest.loadData"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <%
        loadData data = new loadData();
        DAO dao = new DAO();
        int steps = dao.getStepID().size();
        List<Calculations> calList = new ArrayList<>();
        for (int count = 0; count < steps; count++) {
            calList.add(new Calculations());
        }
    %>
    <c:set var="error" value="${0}"></c:set>
        <head>
            <title>Bar Chart</title>

            <script src="../../../dist/Chart.bundle.js"></script>
            <script src="utils.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
            <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
            <style>
                canvas {
                    -moz-user-select: none;
                    -webkit-user-select: none;
                    -ms-user-select: none;
                }
                .popup-dialog{
                    display:none;
                    position: fixed;
                    z-index: 1;
                    left:0;
                    right:0;
                    top:0;
                    padding-top: 100px;
                    height:100%;
                    width:100%;
                    overflow:auto;
                    background-color: grey;
                    background-color: rgba(0,0,0,0.5);
                }

                .close:hover,.close:focus{
                    text-decoration: none;
                    cursor: pointer;
                    color: black;
                }
            </style>
            <script>
                var steps = <%=dao.getStepID()%>;
                var errorPer = []; //1 Second error percentage
                var procRate = [];//1 Second processing rate
                var errorPer5min = []; //5 minutes error percentage
                var procRate5min = [];//5 minutes processing rate
                var errorPer1hour = []; //1 Hour error percentage
                var procRate1hour = [];//1 Hour processing rate
                for (var x = 0; x < steps.length; x++) {
                    errorPer[x] = 0;
                    procRate[x] = 0;
                    errorPer5min[x] = 0;
                    procRate5min[x] = 0;
                    errorPer1hour[x] = 0;
                    procRate1hour[x] = 0;
                }

                function openPopup(){
                    var modal = document.getElementById("popup-message");
                    modal.style.display = "block";
                }
                function closePopup() {
                    var modal = document.getElementById("popup-message");
                    modal.style.display = "none";
                }
                window.onclick = function (event) {
                    var modal = document.getElementById("popup-message");
                    if (event.target === modal) {
                        modal.style.display = "none";
                    }
                };
        </script>
    </head>
    <body>

        <br>
        <div class="w3-margin" style="width:90%">
            <canvas id="barchart"></canvas>
        </div>
        <div class="w3-margin" style="width:90%">
            <canvas id="linechart-pro"></canvas>
        </div>
        <div class="w3-margin" style="width:90%">
            <canvas id="linechart-error"></canvas>
        </div>
        <br>

        <div class="w3-top">
            <div class="w3-row w3-large w3-light-grey">
                <div class="w3-col s3">
                    <a href="ManagerHomeServlet" class="w3-button w3-block">Home</a>
                </div>
                <div class="w3-col s3">
                    <a href="ManagerHomeServlet?command=View-Report" class="w3-button w3-block">Report</a>
                </div>
            </div>
        </div>
        <script>
            var color = Chart.helpers.color;
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
                        label: 'Error Rate',
                        backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.red,
                        borderWidth: 1,
                        data: [0, 0, 0, 0, 0
                        ]
                    }]

            };
            //linechart processing rate (Every 5 minutes)
            var lineChartProData = {
                type: 'line',
                data: {
                    labels: steps,
                    datasets: [{
                            label: 'Processing Rate',
                            backgroundColor: window.chartColors.blue,
                            borderColor: window.chartColors.blue,
                            data: [0, 0, 0, 0, 0],
                            fill: false,
                        }]
                },
                options: {
                    responsive: true,
                    title: {
                        display: true,
                        text: ''
                    },
                    tooltips: {
                        mode: 'index',
                        intersect: false,
                    },
                    hover: {
                        mode: 'nearest',
                        intersect: true
                    },
                    scales: {
                        xAxes: [{
                                display: true,
                                scaleLabel: {
                                    display: true,
                                    labelString: 'Step'
                                }
                            }],
                        yAxes: [{
                                display: true,
                                scaleLabel: {
                                    display: true,
                                    labelString: 'Processing Rate'
                                }
                            }]
                    }
                }
            };

            //linechart error percentage (Every 5 minutes)
            var lineChartErrorData = {
                type: 'line',
                data: {
                    labels: steps,
                    datasets: [{
                            label: 'Error Percentage',
                            backgroundColor: window.chartColors.red,
                            borderColor: window.chartColors.red,
                            data: [0, 0, 0, 0, 0
                            ],
                            fill: false,
                        }]
                },
                options: {
                    responsive: true,
                    title: {
                        display: true,
                        text: ''
                    },
                    tooltips: {
                        mode: 'index',
                        intersect: false,
                    },
                    hover: {
                        mode: 'nearest',
                        intersect: true
                    },
                    scales: {
                        xAxes: [{
                                display: true,
                                scaleLabel: {
                                    display: true,
                                    labelString: 'Step'
                                }
                            }],
                        yAxes: [{
                                display: true,
                                scaleLabel: {
                                    display: true,
                                    labelString: 'Error Percentage'
                                }
                            }]
                    }
                }
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

                var ctx = document.getElementById('linechart-pro').getContext('2d');
                window.lineChartPro = new Chart(ctx, lineChartProData);

                var ctx = document.getElementById('linechart-error').getContext('2d');
                window.lineChartError = new Chart(ctx, lineChartErrorData);
            };

            function update1Second() {
                updateBarChart();
                checkCriticalLevel();
                for (var count = 0; count < errorPer.length; count++) {
                    //alert(errorRate[count]);
                    barChartData.datasets[0].data[count] = procRate[count];
                    barChartData.datasets[1].data[count] = errorPer[count];

                    procRate5min[count] += procRate[count];
                    errorPer5min[count] += errorPer[count];

                    procRate[count] = 0;
                    errorPer[count] = 0;
                }

                window.barChart.update();
            }

            function update5Minute() {
                //var ctx = document.getElementById('linechart-pro').getContext('2d');
                
                //var data = {data: [65]};
                //window.lineChartPro = new Chart(ctx, data);
                for (var count = 0; count < procRate5min.length; count++) {
                    lineChartProData.data.datasets[0].data[count] = procRate5min[count];
                    lineChartErrorData.data.datasets[0].data[count] = errorPer5min[count];
                    procRate1hour[count] += procRate5min[count];
                    errorPer1hour[count] += errorPer5min[count];

                    procRate5min[count] = 0;
                    errorPer5min[count] = 0;
                }

                window.lineChartPro.update();
                window.lineChartError.update();
            }

            function updateBarChart() {
                //alert(errorRate.length);
                var noOfItem = Math.round(Math.random() * (1000 - 800) + 800);
                for (var x = 0; x < noOfItem; x++) {
                    //Get IOT Device No
                    var iotDevNo = Math.round(Math.random() * (<%=dao.getStepID().size()%> - 1) + 1);
                    //Get Received item error or not (error when value = 1)
                    var num = Math.round(Math.random() * (5 - 1) + 1);
                    if (num === 1) {
                        errorPer[iotDevNo - 1]++;
                        //Increment each IOT Device error count
                    }
                    procRate[iotDevNo - 1]++;
                    //Increment each IOT Device total count
                }
            }

            function checkCriticalLevel() {
                for (var count = 0; count < procRate.length; count++) {
                    if (procRate[count] < 120) {
                        var highest = getHighestProIndex();
                        var message = "Stage " + (count + 1) + " production is low. Please shift person from Stage " + highest;
                        document.getElementById("message").innerHTML = message;
                        openPopup();
                        break;
                    }
                }
            }

            function getHighestProIndex() {
                var highestIndex;
                var highest = 0;
                for (var count = 0; count < procRate.length; count++) {
                    if (procRate[count] > highest) {
                        highestIndex = count + 1;
                        highest = procRate[count];
                    }
                }
                return highestIndex;
            }
            window.setInterval(function () {
                update1Second()
            }, 1000);
            window.setInterval(function () {
                update5Minute()
            }, 1000 * 60 * 5);
        </script>

        <!-- Popup Confirm Box -->
        <div id="popup-message" class="popup-dialog">
            <div class="w3-container w3-round w3-white w3-padding w3-animate-top" style="max-width:400px; margin: 1% 35%">
                    <span class="close" onclick="closePopup()">&times;</span>
                    <p class="w3-large w3-text-red" id="message"></p>
            </div>
        </div>
        <!-- Popup Confirm Box  End -->

    </body>
</html>
