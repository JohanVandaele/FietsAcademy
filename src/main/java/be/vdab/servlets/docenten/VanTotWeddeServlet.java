package be.vdab.servlets.docenten;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.DocentService;

// enkele imports ...

@WebServlet("/docenten/vantotwedde.htm")
public class VanTotWeddeServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/docenten/vantotwedde.jsp";
	private final DocentService docentService = new DocentService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		// voorlopig dummy waarden voor vanWedde en totWedde meegeven:
//		request.setAttribute("docenten",docentService.findByWeddeBetween(null, null));
//		request.getRequestDispatcher(VIEW).forward(request, response);

		if (request.getQueryString() != null)
		{
			Map<String, String> fouten = new HashMap<>();
		
			try
			{
				BigDecimal van = new BigDecimal(request.getParameter("van"));
				
				try
				{
					BigDecimal tot = new BigDecimal(request.getParameter("tot"));
					
					request.setAttribute("docenten",docentService.findByWeddeBetween(van, tot));
				}
				catch (NumberFormatException ex)
				{
					fouten.put("tot","tik een getal");
				}
			}
			catch (NumberFormatException ex)
			{
				fouten.put("van","tik een getal");
			}
			
			request.setAttribute("fouten", fouten);
		}
		
		request.getRequestDispatcher(VIEW).forward(request, response);	
	}
}
