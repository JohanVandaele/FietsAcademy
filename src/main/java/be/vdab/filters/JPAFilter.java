package be.vdab.filters;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

// enkele imports ...

@WebFilter("*.htm")
public class JPAFilter implements Filter
{
	// Deze variabele is de EnityManagerFactory. Een static variabele blijft in RAM gedurende de levensduur van de website.
	// Je maakt een EntityManagerFactory object met de static Persistence method createEntityManagerFactory. Je geeft de name mee van het element persistence-unit uit persistence.xml.
	private static final EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("allesvoordekeuken");
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		ServletContext context = filterConfig.getServletContext();
		
		// Elke JSP zal in ${contextPath} de context path van de website kunnen lezen. Die bevat bij ons de String /fietsacademy
		context.setAttribute("contextPath", context.getContextPath());
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws ServletException, IOException
	{
		// Je geeft aan dat request parameters uitgedrukt zijn in UTF-8. Je verwerkt zo request parameters met ‘vreemde tekens’ op een correcte manier.
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}
	
	// De webserver roept deze method op bij het stoppen van de website.
	@Override
	public void destroy()
	{
		// Je sluit de EntityManagerFactory.
		entityManagerFactory.close();
	}
}
