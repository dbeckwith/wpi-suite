package wpisuite.core;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;

import wpisuite.models.*;
public class HelloServlet extends HttpServlet 
{

	MockDataStore data;
	
	public HelloServlet()
	{
		data = new MockDataStore();
	}
	
	public void doGet (HttpServletRequest req,
                       HttpServletResponse res) throws ServletException, IOException
	{
        PrintWriter out = res.getWriter();
        User u = data.getDude(Integer.parseInt(req.getPathInfo().substring(6)));
        
        out.println(u.toJSON());
        out.close();
	}
	
	public void doPut (HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException
    {
		BufferedReader in = req.getReader();
		data.addDude(in.readLine());
    }
}