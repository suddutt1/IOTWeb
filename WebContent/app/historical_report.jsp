<%@page import="com.ibm.iotapp.data.ApplicationConfigFactory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IOT Based Monitoring Application</title>

    <!-- Bootstrap Core CSS -->
    <link href="jslib/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="jslib/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/app.css" rel="stylesheet">

   
    <!-- Custom Fonts -->
    <link href="jslib/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">

		<jsp:include page="left_menu.jsp"></jsp:include>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Historical device data</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Historical device data
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        
                        <div class="row">
							  <div class="form-group col-lg-4">
							    <label for="email">Device</label>
							    <label></label>
							    			<!-- - Following drop down should be populated automatically -->
                                            <select class="form-control" id="deviceId">
                                                <option value="b827ebe5bdc9" selected >Device at Chennai id-b827ebe5bdc9 </option>
                                                <option value="b827eb4f260a"  >Device id-b827eb4f260a </option>
                                                <option value="sim1"  >Simulated device 1</option>
                                                <option value="b827ebbf42eb"  >Device Chennai-DLF GF </option>
                                                
                               </select>
							  </div>
							  <div class="form-group col-lg-2">
							    <label>Sensor</label>
                                <select class="form-control" id="sensorType">
                                                <option value="motion">Motion</option>
                                                <option value="obstacle">Distance</option>
                                </select>
							  </div>
							  <div class="form-group col-lg-2">
							  	<label>Time span</label>
                                <select class="form-control" id="timeSpan">
                                	   <option value="2" selected>2 days</option>
                                       <option value="7">1 Week</option>
                                       <option value="14">2 Weeks</option>
                                       <option value="30">1 month</option>
                                </select>
							  </div>
							  <div class="form-group col-lg-2">
							  	<button  id="viewBtn" class="btn btn-lg btn-success btn-block" style="margin-top:20px;" >View</button>
							  </div>
                        </div>
                       
                        <div class="row" class="col-lg-12" >	
                            <div id="static_graph" style="width:auto;height:450px;">
                            </div>
                         </div>   
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="jslib/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="jslib/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="jslib/metisMenu/dist/metisMenu.min.js"></script>
    
	<script src="jslib/highcharts/highcharts.js"></script>
	<script src="jslib/mqtt/mqttclient.js"></script>
	<script src="jslib/appcommon.js"></script>
   	<script>
   	var HIST_DATA_BASE_URL =     "<%=ApplicationConfigFactory.getProperty(ApplicationConfigFactory.HISTORY_SERVICE_API_BASE,"") %>";
  	var existingChart = null;
 
    $(document).ready(function () {
    		//add event handler to view button
    		$( "#viewBtn" ).prop( "disabled",true);
    		$("#viewBtn").click(function(){
    			console.log("View button clicked");
    			$( "#viewBtn" ).prop( "disabled",true);
    			viewNewGraph();
    		});
    		setup();
    		
	        
    }); //End of document ready 
    function setup()
    {
    	var endTime = Date.now();
    	var days = parseInt($("#timeSpan").val());
    	var startTime = endTime-days*86400*1000;//Last 2 days data
    	var deviceId =  $("#deviceId").val();
    	var sensorDesc = $("#sensorType option:selected").text();
    	var sensor = $("#sensorType").val();
    	var url = HIST_DATA_BASE_URL+"getHistoricalDeviceData?rand="+Math.random()+"&deviceId="+deviceId+
    			"&sensorType="+sensor+"&start="+startTime+"&end="+endTime+"&callback=?";
    	console.log(url);
    	$.getJSON(url,function(data){
    		 console.log(data);
    		 existingChart = new HistoricalGraph("static_graph",data,"Historical Sendor Data for " + sensorDesc,"Average count ","Occupied",10,500);
    	});
    	$( "#viewBtn" ).prop( "disabled",false ); 
    }
	function viewNewGraph()
	{
		$( "#viewBtn" ).prop( "disabled",true );
    	//Remove the graph
    	if(existingChart!=null)
    	{
    		existingChart.remove();
    	}
    	setup();
	}
   	</script>

</body>

</html>
    