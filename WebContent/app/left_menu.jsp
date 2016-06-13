<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
                 <li id="left_menu_dashboard">
                     <a class="active" href="home.wss"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                 </li>
                 <li>
                     <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Monitor<span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level">
                         <li>
                             <a href="home.wss">Device</a>
                         </li>
                         <li id="left_menu_seats_motion">
                             <a href="seatmonitor.wss?sensor=motion">Seats ( Motion sensor based)</a>
                         </li>
                         <li id="left_menu_seats_uss">
                             <a href="seatmonitor.wss?sensor=uss">Seats ( Ultrasound sensor  based)</a>
                         </li>
                     </ul>
                     <!-- /.nav-second-level -->
                 </li>
                 <li>
                     <a href="#"><i class="fa fa-table fa-fw"></i>Reports</a>
                     <ul class="nav nav-second-level">
                     	 <li>
                             <a href="historicalReport.wss">Historical device report</a>
                         </li>
                         <li>
                             <a href="#">Location wise report</a>
                         </li>
                         <li>
                             <a href="#">Building wise report</a>
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
    