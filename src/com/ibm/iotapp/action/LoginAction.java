package com.ibm.iotapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;
import com.ibm.iotapp.data.ApplicationConstants;

public class LoginAction implements WebActionHandler {

	
	@RequestMapping("logout.wss")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse	 response)
	{
		ModelAndView mvObject = new ModelAndView(ViewType.JSP_VIEW);
		mvObject.setView("login.jsp");
		request.getSession().invalidate();
		return mvObject;
	}
	@RequestMapping("login.wss")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse	 response)
	{
		ModelAndView mvObject = null;
		boolean isValid = false;
		String user = request.getParameter("userid");
		String password = request.getParameter("password");
		/*DAOResponse daoResponse = DAOFactory.getUserDetailsDAO().getUser(user);
		if(daoResponse!=null && daoResponse.getStatus()== DAOResponse.STATUS_DAO_SUCCESS)
		{
			UserDetails userDetails = (UserDetails)daoResponse.getResponse();
			if(userDetails!=null && userDetails.getPassword().equals(password))
			{
				request.getSession().setAttribute("user_role", userDetails.getRole());
				request.getSession().setAttribute("user_id", userDetails.getUid());
				isValid = true;
			}
		}*/
		//TODO: This has to be replaced with proper authentication
		if("admin".equalsIgnoreCase(user) && "passw0rd".equalsIgnoreCase(password))
		{
			request.getSession().setAttribute(ApplicationConstants.USER_ROLE, "ADMIN");
			request.getSession().setAttribute(ApplicationConstants.USER_ID, "ADMIN");
			isValid = true;
		}
		else if("ibmer".equalsIgnoreCase(user) && "iot4ibm".equalsIgnoreCase(password))
		{
			request.getSession().setAttribute(ApplicationConstants.USER_ROLE,"EMPLOYEE");
			request.getSession().setAttribute(ApplicationConstants.USER_ID,"IBMER");
			isValid = true;
			
		}
		if(isValid)
		{
			mvObject = new ModelAndView(ViewType.FORWARD_ACTION_VIEW);
			mvObject.setView("home.wss");
		}
		else
		{	
			mvObject = new ModelAndView(ViewType.JSP_VIEW);
			mvObject.setView("login.jsp");
			mvObject.addModel("actionError", "Invalid credentials");
		}
		return mvObject;
	}
}
