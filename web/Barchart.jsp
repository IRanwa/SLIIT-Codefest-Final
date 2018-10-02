<%-- 
    Document   : Home
    Created on : Oct 2, 2018, 10:52:27 AM
    Author     : Frank
--%>
<%@page import="java.util.TimerTask"%>
<%@page import="java.util.Timer"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="codefest.Calculations"%>
<%@page import="Model.DAO"%>
<%@page import="codefest.loadData"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        </style>
        <script>
            var steps = <%=dao.getStepID()%>;
            var color = Chart.helpers.color;
            var barChartData = {
                labels: steps,
                datasets: [{
                        label: 'Process Rate',
                        backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.red,
                        borderWidth: 1,
                        data: [
                            randomScalingFactor(),
                            randomScalingFactor(),
                            randomScalingFactor(),
                            randomScalingFactor(),
                            randomScalingFactor()
                        ]
                    }, {
                        label: 'Error Rate',
                        backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.blue,
                        borderWidth: 1,
                        data: [
                            randomScalingFactor(),
                            randomScalingFactor(),
                            randomScalingFactor(),
                            randomScalingFactor(),
                            randomScalingFactor()
                        ]
                    }]

            };
            var config = {
                type: 'line',
                data: {
                    labels: steps,
                    datasets: [{
                            label: 'Processing Rate',
                            backgroundColor: window.chartColors.blue,
                            borderColor: window.chartColors.blue,
                            data: [
                                randomScalingFactor(),
                                randomScalingFactor(),
                                randomScalingFactor(),
                                randomScalingFactor(),
                                randomScalingFactor(),
                                randomScalingFactor(),
                                randomScalingFactor()
                            ],
                            fill: false,
                        }]
                },
                options: {
                    responsive: true,
                    title: {
                        display: true,
                        text: 'Chart.js Line Chart'
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

            window.onload = function () {
                var ctx = document.getElementById('canvas').getContext('2d');
                window.myBar = new Chart(ctx, {
                    type: 'bar',
                    data: barChartData,
                    options: {
                        responsive: true,
                        legend: {
                            position: 'top',
                        },
                        title: {
                            display: true,
                            text: 'Chart.js Bar Chart'
                        }
                    }
                });

                var ctx = document.getElementById('canvas-line-pro').getContext('2d');
                window.myLine = new Chart(ctx, config);
            };

            function update() {
                config.data.datasets.forEach(function (dataset) {
                    dataset.data = dataset.data.map(function () {
                        return randomScalingFactor();
                    });

                });

                window.myLine.update();

                barChartData.datasets.forEach(function (dataset) {
                    dataset.data = dataset.data.map(function () {
                        return randomScalingFactor();
                    });

                });
                
                window.myBar.update();
            }
            function randomScalingFactor(){
                
                var noOfItem = Math.round(Math.random() * (1000-800)+800);
                for (var x = 0; x < noOfItem; x++) {
                    //Get IOT Device No
                    var iotDevNo = Math.round(Math.random() * (steps-1)+1);
                    //Get Received item error or not (error when value = 1)
                    var num = Math.round(Math.random() * (5 - 1 + 1));
                    if (num === 1) {
                        //Increment each IOT Device error count
                    }
                    //Increment each IOT Device total count
                }
                console.log(noOfItem);
                return noOfItem;
            }
            window.setInterval(
                    function(){update()}
                    , 1000);
        </script>
    </head>
    <body>

        <br>
        <div style="width:75%;">
            <canvas id="canvas"></canvas>
        </div>
        <div style="width:75%;">
            <canvas id="canvas-line-pro"></canvas>
        </div>
        <div style="width:75%;">
            <canvas id="canvas-line-error"></canvas>
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


    </body>
</html>
