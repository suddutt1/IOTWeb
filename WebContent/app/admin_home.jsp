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
                    <h1 class="page-header">Device data dashboard</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
            	<div class="alert alert-info">
				  <strong>New feature added</strong> Please use Seats( Motion sensor based ) and Seats( Ultrasound sensor based ) menu from left menu bar to view real time seat occupency monitoring.
				</div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Device Data Real Time Monitoring
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
                                                <option value="b827ebbf42eb"  >Device Chennai-DLF GF </option>
                                                <option value="sim1"  >Simulated device 1</option>
                                                
                               </select>
							  </div>
							  <div class="form-group col-lg-4">
							    <label>Sensor</label>
                                <select class="form-control" id="sensorType">
                                                <option value="temp" selected>Temperature</option>
                                                <option value="motion">Motion</option>
                                                <option value="obstacle">Distance</option>
                                </select>
							  </div>
							  <div class="form-group col-lg-4">
							  	<button  id="viewBtn" class="btn btn-lg btn-success btn-block" style="margin-top:20px;" >View</button>
							  </div>
                        </div>
                       
                        <div class="row">	
                            <div id="admin_home_running_graph" style="width:auto;height:450px;" class="col-lg-12">
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
   	
  	var existingChart = null;
   	var mqttClient = null;
   	var closedByUser = false;
   	var iotConfig = { 
		   			orgId: "ftwaq4" , 
		   			username: "a-ftwaq4-fwiu8sk6or", 
		   			password:"@4ox@0dNvQ7wm+WTgP",
		   			deviceType:"raspberrypi"
		   			
   				};
 
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
    	var deviceId =  $("#deviceId").val();
    	var topic = "iot-2/type/"+iotConfig.deviceType+"/id/"+deviceId+"/evt/"+"status"+"/fmt/json";
    	var sensorDesc = $("#sensorType option:selected").text();
    	existingChart = new RunningGraph("admin_home_running_graph",sensorDesc+ " sensor data from device id "+deviceId,sensorDesc,sensorDesc+" sensor data");
    	if(mqttClient ==null)
		{
    		mqttClient = new MQTTClient(iotConfig.orgId,iotConfig.username,iotConfig.password,topic,function(message){
				try{
					var payloadStr = message.payloadString;
					//console.log("Payload arrived "+ payloadStr);
					var payload = eval("("+payloadStr+")");
					var sensorType = $("#sensorType").val();
					var x = (new Date()).getTime();
					var y = 0;
					
					if(payload["d"][sensorType]!=null)
					{	
					 	y = parseFloat(payload["d"][sensorType]);
					}
					existingChart.addReading(x,y);
				}catch(exp)
				{
					console.log(exp);
				}
			});
		}
    	else
    	{
    		mqttClient.setTopic(topic);	
    	}
    	mqttClient.init();
    	$( "#viewBtn" ).prop( "disabled",false );
    }
    function viewNewGraph()
    {
    	//Stop MQTT
    	if(mqttClient!=null)
    	{	
    		mqttClient.disconnect();
    	}	
    	//Delete the existing graph
    	
    	if(existingChart!=null)
    	{
    		existingChart.remove();
    	}
    	setup();
    }
   	</script>

</body>

</html>
    