
(function(scope){
	console.log("MQTTClient  loader");
	if (typeof MQTTClient === "undefined") {
		
		//Defining MQTTClient wrapper 
		console.log("Defining MQTTClient ");
		
		/**
		 * Creates an instance of MQTTClient
		 * @param orgId String 
		 * @param apiKey String 
		 * @param apiPassword String 
		 * @param topic String 
		 * @param onMessageCallback Callback function on message arrival
		 */
		scope.MQTTClient = function(orgId,apiKey,apiPassword,topic,onMessageCallback){
			
			//_self is used to access this object from private methods.
			var _self = this;
			//Class instance variables
			this.debug = false;
			this.config = {};
			this.config.orgId = orgId ;
			this.config.apiKey = apiKey ;
			this.config.apiPassword = apiPassword ;
			this.config.orgId = orgId ;
			this.config.broker = orgId+".messaging.internetofthings.ibmcloud.com";
			this.config.port = 1883;
			this.config.clientId = "a:" + this.config.orgId + ":" + "myJsClient_"+(new Date()).getTime();
			this.topic = topic;
			//Create the instance of PAHO MQTT Client 
			this.mqttClient = new Paho.MQTT.Client(this.config.broker, this.config.port,"",this.config.clientId);
			
			
			
			/*
			 * Private and default implementation of message arrived
			 * */
			var onMessageArrived = function(payload){
				
				_logmessage("Message received:"+ payload);
			};
			//Override if user gives their callback
			if(typeof onMessageCallback ==="function")
			{
				onMessageArrived = onMessageCallback;
			}
			
			
			/**
			 * init public method to start the ball rolling
			 * **/
			this.init = function(){
				_logmessage("Inside init");
				//Configure the client 
				this.mqttClient.onConnectionLost = onConnectionLost;
				this.mqttClient.onMessageArrived = onMessageArrived;
				this.mqttClient.connect({ userName : this.config.apiKey, password : this.config.apiPassword, timeout:1000, onSuccess:onConnect});
				console.log("Trying to connect to the broker: " + this.config.broker);
				
			};
			/*
			 * Sets a new topic for event subscription
			 */
			this.setTopic=function(newTopic){
				this.topic = newTopic;
			}
			/*
			 * Disconnects the MQTTClient
			 * */
			this.disconnect = function(){
				_logmessage("Disconnecting");
				this.mqttClient.onConnectionLost = onConnectionLostNoAction;
				this.mqttClient.disconnect();
				
			}
			/*
			 * Setting the debug flag
			 * */
			this.setDebug= function(flag){
				if(typeof flag === "boolean")
				{
					
					this.debug = flag;
				}
			};
			/*
			 * Private method for logging the message
			 * */
			var _logmessage = function (message)
			{
				if(_self.debug == true)
				{
					console.log(message)
				}
			}
			/*
			 * Private default on connect . Subscribe to a topic 
			 * */
			var onConnect = function(){
					//Default implementation 
					console.log("Connected to IOT broker.."+ _self.config.broker);
					_self.mqttClient.subscribe(_self.topic);
					
			};
			/*
			 * Private default reconnect callback.
			 * */
			var onConnectionLost = function(responseObject){
				//Reconnect 
				console.log("Connection is lost . Reconnecting as default");
				_self.init();
			};
			/*
			 * When disconnected by caller , this function is set before calling the paho disconnect method.
			 * */
			var onConnectionLostNoAction = function(responseObject){
				_logmessage("Connection is lost . Doing nothig");
			};
		};
				
	}
	
})(window);

(function(scope){
	
	if (typeof MeterGauge === "undefined") {
		
		/**
		 * Creates Hicharts meter gauge
		 * @param divIdToPlace div Id place the widget
		 * @param chartTitle String 
		 * @param maxCount Max count in the Gauge
		 * @param redGreenThreshold Red Green Threshold Value
		 * @param initialValue Initial value
		 */
		scope.MeterGauge = function (divIdToPlace,chartTitle,maxCount,redGreenThreshold,initialValue){
		
		this.selector = "#"+divIdToPlace;
		this.maxValue = maxCount;
		try{
				
			$(this.selector).highcharts({

	   	        chart: {
	   	            type: 'gauge',
	   	            plotBackgroundColor: null,
	   	            plotBackgroundImage: null,
	   	            plotBorderWidth: 0,
	   	            plotShadow: false
	   	        },
	   	        title: {
	   	            text: chartTitle
	   	        },
	   	        pane: {
	   	            startAngle: -150,
	   	            endAngle: 150,
	   	            background: [{
	   	                backgroundColor: {
	   	                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 					},
	   	                    stops: [
	   	                        [0, '#FFF'],
	   	                        [1, '#333']
	   	                    ]
	   	                },
	   	                borderWidth: 0,
	   	                outerRadius: '109%'
	   	            }, {
	   	                backgroundColor: {
	   	                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 					},
	   	                    stops: [
	   	                        [0, '#333'],
	   	                        [1, '#FFF']
	   	                    ]
	   	                },
	   	                borderWidth: 1,
	   	                outerRadius: '107%'
	   	            }, {
	   	                // default background
	   	            }, {
	   	                backgroundColor: '#DDD',
	   	                borderWidth: 0,
	   	                outerRadius: '105%',
	   	                innerRadius: '103%'
	   	            }]
	   	        },

	   	        // the value axis
	   	        yAxis: {
	   	            min: 0,
	   	            max: maxCount,
	   	            tickPixelInterval: 30,
	   	            tickWidth: 2,
	   	            tickPosition: 'inside',
	   	            tickLength: 10,
	   	            tickColor: '#666',
	   	            labels: {
	   	                step: 2,
	   	                rotation: 'auto'
	   	                
	   	            },
	   	            title: {
	   	                text: ''
	   	            },
	   	            plotBands: [{
	   	                from: 0,
	   	                to: redGreenThreshold,
	   	                color: '#55BF3B' // green
	   	                
	   	            }, {
	   	                from: redGreenThreshold,
	   	                to: maxCount,
	   	                color: '#FF0000' // red
	   	            }]
	   	        },

	   	        series: [{
	   	            name: chartTitle,
	   	            data: [initialValue],
	   	            tooltip: {
	   	                valueSuffix: ' Count'
	   	            },
	   	            dataLabels: {
	   	            	format: "{y:.2f}"
	   	            }
	   	         }
	   	        ]
			});
		
		}catch(exp)
		{
			console.log(exp);
			
		};
		
		
			
	}//End of class definition 
	
		
	MeterGauge.prototype.updateReading= function(newReading){
	   		var chart  = $(this.selector).highcharts();
	   		var point = chart.series[0].points[0];
	   		if(newReading>this.maxValue)
	   		{
	   			newReading = this.maxValue;	
	   		}
	   		point.update(newReading);
		};
	
	MeterGauge.prototype.remove= function(){
			var chart = $(this.selector).highcharts();
			if(chart!=null)
			{
				chart.destroy();
			}
		};	
	
	}
})(window);

(function(scope){
	
	if(typeof HistoricalGraph=== "undefined"){
		Highcharts.setOptions({
            global: {
                useUTC: true
            }
        });
		scope.HistoricalGraph = function(divToPlace,initialData,chartTitle,yAxisTitle,seriesName,startRange,endRange)
		{
			this.selector = "#"+divToPlace ;
			$(this.selector).highcharts({
	            chart: {
	                type: 'area',
	                zoomType: 'x',
	                marginRight: 10,
	                events: {
	                    load: function () {
							console.log("Load complete for historical chart") ;
	                    }
	                }
	            },
	            title: {
	                text: chartTitle
	            },
	            xAxis: {
	                type: 'datetime',
	                tickPixelInterval: 150
	            },
	            yAxis: {
	                title: {
	                    text: yAxisTitle
	                },
	                plotLines: [{
	                    value: 0,
	                    width: 1,
	                    color: '#808080'
	                }],
	                plotBands: [{
	                    color: '#c1ff84', // Color value
	                    from: startRange, // Start of the plot band
	                    to: endRange,
	                    label: { 
	                        text: 'Seat occupied' 
	                      }
	                  }]
	            },
	            tooltip: {
	                formatter: function () {
	                    return '<b> Count </b><br/>' +
	                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
	                        Highcharts.numberFormat(this.y, 0);
	                }
	            },
	            legend: {
	                enabled: false
	            },
	            exporting: {
	                enabled: false
	            },
	            series: [{
	                name: seriesName,
	                data: initialData
	            }]
	        });
			
			HistoricalGraph.prototype.remove  = function(){
				
				var chart = $(this.selector).highcharts();
				if(chart!=null)
				{
					chart.destroy();
				}
			}
		}
	}
})(window);
(function(scope){
	if(typeof RunningGraph ==="undefined"){
		Highcharts.setOptions({
	            global: {
	                useUTC: false
	            }
	        });
		scope.RunningGraph = function(divToPlace,chartTitle,yAxisTitle,seriesName,initDataArray){
			this.selector = "#"+divToPlace ;
			//If initial data is not supplied then build a new initialData
			var initialData = null;
			if(typeof initDataArray === "undefined")
			{
				initialData = [];
				var time = (new Date()).getTime();
		        var i;
			    for (i = -19; i <= 0; i += 1) {
			    	initialData.push({
			            x: time + i * 1000,
			            y: null
			        });
			    }
			}
			else
			{
				initialData = initDataArray;
			}
			
			$(this.selector).highcharts({
	            chart: {
	                type: 'spline',
	                animation: {
	                    duration: 50
	                }, // don't animate in old IE
	                marginRight: 10,
	                events: {
	                    load: function () {
							console.log("Running graph function is invoked..") ;
	                    }
	                }
	            },
	            title: {
	                text: chartTitle
	            },
	            xAxis: {
	                type: 'datetime',
	                tickPixelInterval: 150
	            },
	            yAxis: {
	                title: {
	                    text: yAxisTitle
	                },
	                plotLines: [{
	                    value: 0,
	                    width: 1,
	                    color: '#808080'
	                }]
	            },
	            tooltip: {
	                formatter: function () {
	                    return '<b>' + this.series.name + '</b><br/>' +
	                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
	                        Highcharts.numberFormat(this.y, 2);
	                }
	            },
	            legend: {
	                enabled: false
	            },
	            exporting: {
	                enabled: false
	            },
	            series: [{
	                name: seriesName,
	                data: initialData
	            }]
	        });
			
			
		};
		
		RunningGraph.prototype.addReading = function(x,y){
			
			var chart = $(this.selector).highcharts();
			if(chart!=null)
			{
				chart.series[0].addPoint([x,y],true,true);	
			}
			
		}
		
		RunningGraph.prototype.remove  = function(){
			
			var chart = $(this.selector).highcharts();
			if(chart!=null)
			{
				chart.destroy();
			}
		}
	}
	
})(window);