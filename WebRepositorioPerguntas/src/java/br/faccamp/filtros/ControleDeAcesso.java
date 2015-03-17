/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.filtros;

/**
 *
 * @author Edu - Notebook
 */

import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jboss.weld.context.unbound.RequestContextImpl;

public class ControleDeAcesso implements PhaseListener  {
    


    /*public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
           
       if ( session.getAttribute("usuario") != null || req.getRequestURI().endsWith("Login.xhtml"))
       {
           chain.doFilter(request, response);
       }
       else
       {
           HttpServletResponse res = (HttpServletResponse) response;
           res.sendRedirect("Login.xhtml"); 
       }
    }*/

    @Override
    public void afterPhase(PhaseEvent event) {
        
        
        FacesContext facesContext = event.getFacesContext();
        
        String currentPage = facesContext.getViewRoot().getViewId();
        
        boolean isLoginPage = (currentPage.lastIndexOf("Login.xhtml") > -1);
        
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

        String usuario = (String) session.getAttribute("usuario");

        if (!isLoginPage && usuario == null) 
        {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "loginPage");
            
        } 
        else 
        {
            
        }
        //caso contrário o jsf passa tranquilamente por aqui!!!
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        
    }

    @Override
    public PhaseId getPhaseId() {
       return PhaseId.RESTORE_VIEW; 
    }
    
}
