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
    <!-- jQuery -->
    <script src="jslib/jquery/dist/jquery.min.js"></script>
    <script src="jslib/mqtt/mqttclient.js"></script>
</head>
<body>
<div>Application Body</div>
<div id="eventSpace"></div>
<script>
var mqttClient = null;
$(document).ready(function () {
    	
	       //Initiating client
	       var orgId = "ftwaq4";
	       var broker = orgId + ".messaging.internetofthings.ibmcloud.com" ;
	       var port =  1883;
	       var clientId = "a:" + orgId + ":" + "myJsClient";
	       mqttClient = new Paho.MQTT.Client(broker, port,"",clientId);
	       
	       // set callback handlers
		mqttClient.onConnectionLost = onConnectionLost;
		mqttClient.onMessageArrived = onMessageArrived;
		
		// connect the client
		mqttClient.connect({ userName : "a-ftwaq4-fwiu8sk6or", password : "@4ox@0dNvQ7wm+WTgP", timeout:1000, onSuccess:onConnect});
		console.log("Is it connected ?");
	        
    }); //End of document ready 

function  onConnectionLost(responseObject)
{
	console.log("Connection lost");
}
function  onMessageArrived(message)
{
	console.log("Messge arrived"+ message.payloadString);
	var payloadStr = message.payloadString;
	
	var payload = eval("("+payloadStr+")");
	console.log(payload["d"]["temp"]);
	
}

// called when the client connects
function onConnect() {
  // Once a connection has been made, make a subscription and send a message.
  console.log("onConnect");
  var topic = "iot-2/type/raspberrypi/id/sim1/evt/SIM_TEMP_READING_EVT/fmt/json"
  mqttClient.subscribe(topic);
  //message = new Paho.MQTT.Message("Hello");
  //message.destinationName = "/World";
  //client.send(message); 
}



</script>
</body>
</html>