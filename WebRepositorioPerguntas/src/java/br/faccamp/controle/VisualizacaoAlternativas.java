/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.Util.ConversaoArquivo;
import br.faccamp.modelo.Alternativas;
import br.faccamp.modelo.Pergunta;
import br.faccamp.modelo.repositorios.PerguntasRepository;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eduardo
 */
@ManagedBean ( name = "vizualizacaoAlternativas" )
@ViewScoped
public class VisualizacaoAlternativas implements Serializable {
    
    private List<Alternativas> alternativas;
    private Pergunta pergunta;
    private String arquivoImagem;
    private File File;
    
    /**
     * @return the alternativas
     */
    public List<Alternativas> getAlternativas() {
        
       FacesContext fc = FacesContext . getCurrentInstance ();
       ExternalContext ec = fc.getExternalContext();
       HttpSession session = ( HttpSession )ec.getSession( false );
              
        Long id = (Long) session.getAttribute("IdPergunta");
        
        EntityManager manager = this.getManager();
        PerguntasRepository repository = new PerguntasRepository(manager);
        this.setAlternativas(repository.ObterAlternativasPergunta(id));
        
        return alternativas;
    }

    /**
     * @param alternativas the alternativas to set
     */
    public void setAlternativas(List<Alternativas> alternativas) {
        this.alternativas = alternativas;
    }
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }

    /**
     * @return the pergunta
     */
    public Pergunta getPergunta()
    {
       FacesContext fc = FacesContext . getCurrentInstance ();
       ExternalContext ec = fc.getExternalContext();
       HttpSession session = ( HttpSession )ec.getSession( false );
              
       long id = (Long) session.getAttribute("IdPergunta");
       
       EntityManager manager = this.getManager();
       PerguntasRepository repository = new PerguntasRepository(manager);
       this.setPergunta(repository.procura(id));
       
       return this.pergunta;
    }
  
    /**
     * @param pergunta the pergunta to set
     */
    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }
    
    @PreDestroy
    public void destroy()
    {
        // remove o arquivo temporario
        this.File.delete();
    }
    

    /**
     * @return the arquivoImagem
     */
    public String getArquivoImagem() {
        
       if (this.pergunta.getFoto() == null)         
           return "";
       
       FacesContext ctx = FacesContext.getCurrentInstance();  
          
       ServletContext servletContext =  (ServletContext)ctx.getExternalContext().getContext();
       
       String path = servletContext.getRealPath("/resources/imagens");
       
       ConversaoArquivo conver = new ConversaoArquivo();
       
       File fileAux;
       
       try
       {
            fileAux = conver.getFile(this.pergunta.getFoto(), this.pergunta.getExt(), path, "imagemTep" );
            
       } 
       catch (IOException ex) 
       {
           return null; 
       }
       
       this.arquivoImagem = fileAux.getName();
       this.File = fileAux;
        
       return arquivoImagem;
    }

    /**
     * @param arquivoImagem the arquivoImagem to set
     */
    public void setArquivoImagem(String arquivoImagem) {
        this.arquivoImagem = arquivoImagem;
    }
            
    
}
    
   