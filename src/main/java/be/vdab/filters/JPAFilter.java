package be.vdab.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
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
	private static final EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("fietsacademy");
	// Je maakt een ThreadLocal object.
	// Gezien de variabele static is, blijft het object in het geheugen gedurende de levensduur van de website.
	private static final ThreadLocal<EntityManager> entityManagers=new ThreadLocal<>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		ServletContext context = filterConfig.getServletContext();
		
		// Elke JSP zal in ${contextPath} de context path van de website kunnen lezen.
		// Die bevat bij ons de String "/fietsacademy"
		context.setAttribute("contextPath", context.getContextPath());
	}

	public static EntityManager getEntityManager()
	{
		//return entityManagerFactory.createEntityManager();
		// Je haalt de EntityManager vanaf nu op uit de ThreadLocal variabele.
		return entityManagers.get();
	}	
	
	// Deze method verwerkt een request.
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws ServletException, IOException
	{
//		// Je geeft aan dat request parameters uitgedrukt zijn in UTF-8. Je verwerkt zo request parameters met ‘vreemde tekens’ op een correcte manier.
//		request.setCharacterEncoding("UTF-8");
//		chain.doFilter(request, response);

		// Je maakt een EntityManager en plaatst die in entityManagers.
		// Als gelijktijdige threads dit doen, hebben ze elk een EntityManager in entityManagers.
		entityManagers.set(entityManagerFactory.createEntityManager());
		
		try
		{
			request.setCharacterEncoding("UTF-8");
			// Je geeft de request door aan de servlet waarvoor hij bestemd is.
			chain.doFilter(request, response);
		}
		finally
		{
			// De servlet verwerkte de request, je leest de EntityManager van de huidige thread.
			EntityManager entityManager = entityManagers.get();
			
			// Als er nog een transactie actief is, doe je een rollback van de transactie.
			// Dit gebeurt als de service layer geen commit deed op de transactie, omdat een exception optrad.
			if (entityManager.getTransaction().isActive())
			{
				entityManager.getTransaction().rollback();
			}
			
			// Je sluit de EntityManager.
			entityManager.close();
			// Je verwijdert de EntityManager van de huidige thread uit entityManagers.
			entityManagers.remove();
		}	
	}
	
	// De webserver roept deze method op bij het stoppen van de website.
	@Override
	public void destroy()
	{
		// Je sluit de EntityManagerFactory.
		entityManagerFactory.close();
	}
}
