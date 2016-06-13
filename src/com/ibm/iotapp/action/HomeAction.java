package com.ibm.iotapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;
import com.ibm.iotapp.data.ApplicationConstants;

public class HomeAction implements WebActionHandler{

	@RequestMapping("home.wss")
	public ModelAndView loadHomePage(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView mvObject = new ModelAndView(ViewType.JSP_VIEW);
		//Authentication and authorization is done 
		//Get the user role
		if(("ADMIN").equals(request.getSession().getAttribute((ApplicationConstants.USER_ROLE))))
		{
			mvObject.setView("app/admin_home.jsp");
		}
		else if (("EMPLOYEE").equals(request.getSession().getAttribute((ApplicationConstants.USER_ROLE))))
		{
			mvObject.setView("app/ibmer_home.jsp");
		}
		return mvObject;
	}
	@RequestMapping("seatmonitor.wss")
	public ModelAndView loadSeatMonitorPage(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView mvObject = new ModelAndView(ViewType.JSP_VIEW);
		String sensorType = request.getParameter("sensor");
		
		//Authentication and authorization is done 
		//Get the user role
		if(("ADMIN").equals(request.getSession().getAttribute((ApplicationConstants.USER_ROLE))))
		{
			if("motion".equalsIgnoreCase(sensorType))
			{
				mvObject.setView("app/seat_monitor_motion.jsp");
			}
			else
			{
				mvObject.setView("app/seat_monitor_uss.jsp");
			}
		}
		else if (("EMPLOYEE").equals(request.getSession().getAttribute((ApplicationConstants.USER_ROLE))))
		{
			mvObject.setView("app/ibmer_home.jsp");
		}
		return mvObject;
	}
	
	
	@RequestMapping("historicalReport.wss")
	public ModelAndView displayHistorialReport(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView mvObject = new ModelAndView(ViewType.JSP_VIEW);
		//String sensorType = request.getParameter("sensor");
		
		//Authentication and authorization is done 
		//Get the user role
		if(("ADMIN").equals(request.getSession().getAttribute((ApplicationConstants.USER_ROLE))))
		{
			mvObject.setView("app/historical_report.jsp");
		}
		else if (("EMPLOYEE").equals(request.getSession().getAttribute((ApplicationConstants.USER_ROLE))))
		{
			mvObject.setView("app/ibmer_home.jsp");
		}
		return mvObject;
	}
}
