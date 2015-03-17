/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.Util.ConversaoArquivo;
import br.faccamp.modelo.Alternativas;
import br.faccamp.modelo.Pergunta;
import br.faccamp.modelo.Tag;
import br.faccamp.modelo.repositorios.AlternativasRepository;
import br.faccamp.modelo.repositorios.PerguntasRepository;
import br.faccamp.modelo.repositorios.TagRepository;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Eduardo
 */
@ManagedBean ( name = "remocaoPerguntasBean" )
@ViewScoped
public class RemocaoPerguntaBean implements Serializable {
    
    private Long Selecionada;
    private Pergunta pergunta;
    private List<Alternativas> alternativas;
    private String arquivoImagem;
    private File File;
    
    
    public void removerPreparacao()
    {
        EntityManager manager = this.getManager();
        PerguntasRepository repositoryPerg = new PerguntasRepository(manager);
        this.pergunta = repositoryPerg.procura(Selecionada);
        this.alternativas = repositoryPerg.ObterAlternativasPergunta(Selecionada);
        
       
    }
    
    public void remover()
    {
        EntityManager manager = this.getManager();
        AlternativasRepository repositoryAlt = new AlternativasRepository(manager);
        
        for (Alternativas alt:alternativas)
        {
            repositoryAlt.remove(alt.getId());
        }
       
        PerguntasRepository repositoryPerg = new PerguntasRepository(manager);
        repositoryPerg.remove(Selecionada);
        
        this.Selecionada = 0l;
        this.pergunta = null;
        this.alternativas = new ArrayList<>();
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Pergunta Excluida"));
        
    }
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }

    /**
     * @return the Selecionada
     */
    public Long getSelecionada() {
        return Selecionada;
    }

    /**
     * @param Selecionada the Selecionada to set
     */
    public void setSelecionada(Long Selecionada) {
        this.Selecionada = Selecionada;
    }

    /**
     * @param pergunta the pergunta to set
     */
    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    /**
     * @return the alternativas
     */
    public List<Alternativas> getAlternativas() {
        return alternativas;
    }

    /**
     * @return the relacionamento
     */

    /**
     * @return the pergunta
     */
    public Pergunta getPergunta() {
        return pergunta;
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

    /**
     * @return the File
     */
    public File getFile() {
        return File;
    }

    /**
     * @param File the File to set
     */
    public void setFile(File File) {
        this.File = File;
    }
    
    
    
    
}
