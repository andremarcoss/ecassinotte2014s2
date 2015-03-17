/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.modelo.Alternativas;
import br.faccamp.modelo.Genero;
import br.faccamp.modelo.Pergunta;
import br.faccamp.modelo.repositorios.AlternativasRepository;
import br.faccamp.modelo.repositorios.GeneroRepository;
import br.faccamp.modelo.repositorios.PerguntasRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Eduardo
 */
@ManagedBean ( name = "vizualizacaoPerguntasBean" )
@ViewScoped
public class VisualizacaoPerguntasBean implements Serializable {
    
    private List<Pergunta> perguntas;
    private List<Pergunta> perguntasFiltradas;
    private Pergunta selecionada;

    public void Vizualiza() {
       FacesContext fc = FacesContext . getCurrentInstance ();
       ExternalContext ec = fc.getExternalContext();
       HttpSession session = ( HttpSession )ec.getSession( false );
       session.setAttribute("IdPergunta",selecionada.getId());
       
       Map<String,Object> options = new HashMap<String, Object>();
       options.put("modal", true);
       options.put("scroll", true);
       options.put("width",700);
       options.put("height",800);
               
       RequestContext.getCurrentInstance().openDialog("PerguntaSelecionada",options,null);
    }
        
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }

    /**
     * @return the perguntas
     */
    public List<Pergunta> getPerguntas() {
        
        //if (this.perguntas == null)
        {
            EntityManager manager = this.getManager();
            PerguntasRepository repository = new PerguntasRepository(manager);
            this.setPerguntas(repository.getLista());
        }
        
        return this.perguntas;
    }
    
 
    /**
     * @param perguntas the perguntas to set
     */
    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    /**
     * @return the selecionada
     */
    public Pergunta getSelecionada() {
        return selecionada;
    }

    /**
     * @param selecionada the selecionada to set
     */
    public void setSelecionada(Pergunta selecionada) {
        this.selecionada = selecionada;
    }

    /**
     * @return the perguntasFiltradas
     */
    public List<Pergunta> getPerguntasFiltradas() {
        return perguntasFiltradas;
    }

    /**
     * @param perguntasFiltradas the perguntasFiltradas to set
     */
    public void setPerguntasFiltradas(List<Pergunta> perguntasFiltradas) {
        this.perguntasFiltradas = perguntasFiltradas;
    }

      
        
}
