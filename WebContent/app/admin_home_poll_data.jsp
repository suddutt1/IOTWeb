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

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="home.wss">IOT Based Seat Monitoring Application</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="logout.wss"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="home.wss"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Monitor<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#">Building</a>
                                </li>
                                <li>
                                    <a href="#">Device</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="tables.html"><i class="fa fa-table fa-fw"></i>Reports</a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#">Location wise report</a>
                                </li>
                                <li>
                                    <a href="#">Buildwise report</a>
                                </li>
                            </ul>
                        </li>
                       
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i> User administration<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="panels-wells.html">Add user</a>
                                </li>
                                <li>
                                    <a href="buttons.html">Modify user</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-sitemap fa-fw"></i> Device administration<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#">Add new</a>
                                </li>
                                <li>
                                    <a href="#">View devices</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-files-o fa-fw"></i>Resource management<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="blank.html">Manage building</a>
                                </li>
                                <li>
                                    <a href="login.html">Manage seats</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Device data dashboard</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-comments fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">26</div>
                                    <div>Devices </div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-tasks fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">12</div>
                                    <div>Buildings</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-yellow">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-shopping-cart fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">124</div>
                                    <div>Seats</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-red">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-support fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">85%</div>
                                    <div>Seat utilization </div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Device Data Display
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        <form role="form">
                        <div class="row">
							  <div class="form-group col-lg-4">
							    <label for="email">Device</label>
							    <label>Selects</label>
                                            <select class="form-control">
                                                <option value="sim1" >Simulated device 1</option>
                                                <option value="sim2" >Simulated device 2</option>
                                                <option value="sim3" >Simulated device 3</option>
                               </select>
							  </div>
							  <div class="form-group col-lg-4">
							    <label>Events</label>
                                <select class="form-control">
                                                <option>Temp</option>
                                                <option>Motion</option>
                                                
                                </select>
							  </div>
							  <div class="form-group col-lg-4"> <button  class="btn btn-lg btn-success btn-block" style="margin-top:20px;" >View</button></div>
							 
							
						
                        </div>
                        </form>
                        <div class="row">	
                            <div id="admin_home_running_graph" style="width:850px;height:450px;" class="col-lg-12">
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
	
    
   	<script>
   	var existingChart = null;
    $(document).ready(function () {
    	
	        Highcharts.setOptions({
   	            global: {
   	                useUTC: false
   	            }
   	        });
	        getSensorData();
	        
	        
    }); //End of document ready 
    
    function drawInitialGraph(inputData)
    {
    	existingChart = $('#admin_home_running_graph').highcharts({
	            chart: {
	                type: 'spline',
	                animation: Highcharts.svg, // don't animate in old IE
	                marginRight: 10,
	                events: {
	                    load: function () {
							console.log("Load function is invoked..") ;
	                    }
	                }
	            },
	            title: {
	                text: 'Data from device'
	            },
	            xAxis: {
	                type: 'datetime',
	                tickPixelInterval: 150
	            },
	            yAxis: {
	                title: {
	                    text: 'Value'
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
	                name: 'Random data',
	                data: inputData
	            }]
	        });
    }
    
   	
   	function getSensorData()
   	{
   		//var sensorData;
  		$.getJSON( "retrieveSensorData.wss?rand="+Math.random(), function( data ) {
  			console.log(data);
  			
  			if(existingChart == null)
  			{
  				drawInitialGraph(data);	
  			}
  			else
  			{
  				var chart = $('#admin_home_running_graph').highcharts();
  				if(data.length>0)
  				{
  					for(index=0;index<data.length;index++)
  					{
  						chart.series[0].addPoint(data[index],true,true);	
  					}
  					
  				}
  				
  			}
  			
  			setTimeout(getSensorData, 30000);
  		});
   			
   	}
   	</script>

</body>

</html>
    