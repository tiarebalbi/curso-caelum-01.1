package br.com.caelum.financas.util;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


import br.com.caelum.financas.infra.JPAUtil;

@WebFilter(urlPatterns="/*")
public class EntityManagerInViewFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		EntityManager em = new JPAUtil().getEntityManager();
		try{
			em.getTransaction().begin();
			request.setAttribute("em", em);
			chain.doFilter(request, response);
			em.getTransaction().commit();
		}catch(Exception e) {
			em.getTransaction().rollback();
			throw new ServletException(e);
		}finally {
			em.close();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
