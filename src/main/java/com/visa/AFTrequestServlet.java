package com.visa;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.vdp.util.VdpUtility;
import com.visa.config.ConfigValues;

/**
 * Servlet implementation class AFTrequestServlet
 */
@WebServlet("/AFTrequestServlet")
public class AFTrequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AFTrequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String payload= (String)new ConfigValues().getPropValues().get("payloadAFT");
	    JSONObject jsonObject;
	    String senderPAN=null;
	    String jsonRequest="";
		try {
			 jsonObject = new JSONObject(payload);		
			 jsonObject.put("Amount", request.getParameter("amount"));
			 
			 HttpSession session = request.getSession();
				 senderPAN=(String)session.getAttribute("senderPAN");			
				if(senderPAN != null){
					jsonObject.put("SenderPrimaryAccountNumber",senderPAN);
				}
			  jsonRequest= VdpUtility.convertToPrettyJsonstring(jsonObject.toString());		  
			  response.getWriter().write(jsonRequest);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	   
	  }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	
	}
	
	}


