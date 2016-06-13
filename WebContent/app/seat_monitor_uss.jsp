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
    <!-- Bootstrap Switch CSS -->
    <link href="css/bootstrap-switch.min.css" rel="stylesheet">
    
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

        <!-- Navigation -->
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
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i>  Ultrasound sensor real time data
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        
                        <div class="row">
							  <div class="form-group col-lg-4">
							    <label for="email">Device</label>
							    <label>Select</label>
							    			<!-- - Following drop down should be populated automatically -->
                                            <select class="form-control" id="deviceId">
                                                <option value="b827ebe5bdc9" selected >Device at Chennai id-b827ebe5bdc9 </option>
                                                <option value="b827eb4f260a"  >Device id-b827eb4f260a </option>
                                                <option value="b827ebbf42eb"  >Device Chennai-DLF GF </option>
                                                <option value="sim1" >Simulated device 1</option>
                                                
                               </select>
							  </div>
							  <div class="form-group col-lg-4">
							  	<button  id="viewBtn" class="btn btn-lg btn-success btn-block" style="margin-top:20px;" >View</button>
							  </div>
                        </div>
                       
                        <div class="row">	
                            <div id="admin_home_running_graph" style="width:auto;height:450px;" class="col-lg-8">
                            </div>
                            <div class="col-lg-4">
                            	<div id="occupency_ind_ph" style="width:auto;height:200px;" ></div>
                            	<div style="padding-left: 40px;">
                            		<input data-indeterminate=true id="occupied" type="checkbox" checked data-on-text="Occupied" data-off-text="Empty" />
                            	</div>
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
    <!-- Bootstarp switch -->
	<script src="jslib/bootstrap-switch.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="jslib/metisMenu/dist/metisMenu.min.js"></script>
    
	<script src="jslib/highcharts/highcharts.js"></script>
	<script src="jslib/highcharts/highcharts-more.js"></script>
	<script src="jslib/mqtt/mqttclient.js"></script>
	<script src="jslib/appcommon.js"></script>
    
   	<script>
   	var MIN_DIST = <%=ApplicationConfigFactory.getLongProperty(ApplicationConfigFactory.US_SENSOR_MIN_DIST, 10)%>;;
   	var MAX_DIST = <%=ApplicationConfigFactory.getLongProperty(ApplicationConfigFactory.US_SENSOR_MAX_DIST, 100)%>;;
   	var HISTORICAL_TIME_LIMIT = <%=ApplicationConfigFactory.getLongProperty(ApplicationConfigFactory.MOTION_DATA_HISTORY_LIMIT, 600000L)%>;
   	var OCCUPENCY_THRESHOLD = <%=ApplicationConfigFactory.getLongProperty(ApplicationConfigFactory.US_SENSOR_OCCUPENCY_TH, 10)%>;;
   	var LAST_ONE_HOUR_DATA = null;
  	var runningGraph = null;
  	var meterGauge = null;
   	var mqttClient = null;
   	
   	var iotConfig = { 
		   			orgId: "ftwaq4" , 
		   			username: "a-ftwaq4-fwiu8sk6or", 
		   			password:"@4ox@0dNvQ7wm+WTgP",
		   			deviceType:"raspberrypi",
		   			eventId:{
		   					"temp":"status",
		   					"motion":"status"
		   					}
   				};
 
    $(document).ready(function () {
    		//Get the last 1 hours motion data
    		//add event handler to view button
			$("#viewBtn").click(function(){
				
				viewNewGraph();
			});
    		//disbale the button initially 
    		$( "#viewBtn" ).prop( "disabled",true );
    		$.getJSON( "retrieveUsSenorData.wss?rand="+Math.random()+"&deviceId="+$("#deviceId").val(), function( data ) {
  				if(data!=null)
  				{
  					LAST_ONE_HOUR_DATA = data;
  				}
  				//console.log(LAST_ONE_HOUR_DATA);
  				$("#occupied").bootstrapSwitch();
  				setupAll();
  			});
	        
    }); //End of document ready 
    
    function setupAll()
    {
		var deviceId =  $("#deviceId").val();
    	var topic = "iot-2/type/"+iotConfig.deviceType+"/id/"+deviceId+"/evt/"+"status"+"/fmt/json";
    	//Setup the graph
    	exisitingGrpah = new RunningGraph("admin_home_running_graph","Ultrasound sensor data from device id "+deviceId,"Distance(in c.m)","Ultrasound sensor data");
    	//Setup the gauge
    	meterGauge = new  MeterGauge("occupency_ind_ph","Distance meter",200,MIN_DIST,0);
		//Initialize the mqtt client 
		if(mqttClient ==null)
		{	
			mqttClient = new MQTTClient(iotConfig.orgId,iotConfig.username,iotConfig.password,topic,function(message){
				try{
					var payloadStr = message.payloadString;
					console.log("Payload arrived "+ payloadStr);
					
					var payload = eval("("+payloadStr+")");
					var sensortype = "obstacle";
					var x = (new Date()).getTime();
					var y = 0;
					if(payload["d"][sensortype]!=null)
					{
					 	y = parseFloat(payload["d"][sensortype]);
					}
					 var newData = {"ts": x,"motion": y};
					exisitingGrpah.addReading(x,y);
					LAST_ONE_HOUR_DATA.push(newData);
					analyze(y);
				}catch(exp)
				{
					console.log(exp);
				}
			});
		}
		else
		{
			//Set the changed topic only 
			mqttClient.setTopic(topic);
		}
		mqttClient.init();
		$( "#viewBtn" ).prop( "disabled",false );
    }
    
    /* Function to analyze the motion data
   	*/
   	function analyze(distance)
   	{
   		var maxSize = LAST_ONE_HOUR_DATA.length;
   		var motionCount =0;
   		var index=0;
   		for(index=0;index<maxSize;index++)
   		{
   			var dataPoint = LAST_ONE_HOUR_DATA[index].motion;
   			if(dataPoint<= MAX_DIST && dataPoint >=MIN_DIST)
   			{
   				motionCount++;	
   			}
   			if(motionCount>=OCCUPENCY_THRESHOLD)
   			{
   				break;
   			}
   		}
   		console.log("Motion count" + motionCount);
   		meterGauge.updateReading(distance);
   		if(motionCount>=OCCUPENCY_THRESHOLD)
		{
   			//set occupency to OCCUPED
   			$("#occupied").bootstrapSwitch('state', true, true);
		}
   		else
   		{
   			//set occupency to GREEN
   			$("#occupied").bootstrapSwitch('state', false, true);
   		}
   		//Remove 1 hour old data
   		var currTime = (new Date()).getTime();
   		
   		if(currTime - LAST_ONE_HOUR_DATA[0].ts >= HISTORICAL_TIME_LIMIT || LAST_ONE_HOUR_DATA.length>240 )
   		{
   			//Remove the first element
   			LAST_ONE_HOUR_DATA.splice(0,1);
   		}
   		console.log("Motion history count" + LAST_ONE_HOUR_DATA.length);
   	}
    
    function viewNewGraph()
    {
    	
    	 //Disable the button
    	$( "#viewBtn" ).prop( "disabled",true );
    	$("#occupied").bootstrapSwitch('state', false, true);
    	//Remove the graph
    	if(runningGraph!=null)
    	{
    		runningGraph.remove();
    	}
    	
    	if(meterGauge!=null)
    	{	
    		meterGauge.remove();
    	}
		
    	if(mqttClient!=null)
    	{
    		mqttClient.disconnect();	
    	}
    	
    	//Get the last 1 hours motion data
		$.getJSON( "retrieveUsSenorData.wss?rand="+Math.random()+"&deviceId="+$("#deviceId").val(), function( data ) {
				if(data!=null)
				{
					LAST_ONE_HOUR_DATA = data;
				}
				console.log(LAST_ONE_HOUR_DATA);
		    	setupAll();
				
			}); 
    }
   	</script>

</body>

</html>
    