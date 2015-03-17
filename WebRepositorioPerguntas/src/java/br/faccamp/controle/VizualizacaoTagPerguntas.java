/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.modelo.Pergunta;
import br.faccamp.modelo.repositorios.TagRepository;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.TagCloudItem;

/**
 *
 * @author Eduardo
 */

@ManagedBean ( name = "vizualizacaoTagPergBean" )
@ViewScoped
public class VizualizacaoTagPerguntas implements Serializable {
    
    private List<Pergunta> perguntas;
    private Long idSelecionado = 0l;
    
    public void onSelect(SelectEvent event) 
    {
        TagCloudItem item = (TagCloudItem) event.getObject();
        this.idSelecionado = new Long(item.getStrength());
        filtra();
    }

    public void vizualiza(Pergunta pergunta) 
    {
       FacesContext fc = FacesContext . getCurrentInstance ();
       ExternalContext ec = fc.getExternalContext();
       HttpSession session = ( HttpSession )ec.getSession( false );
       session.setAttribute("IdPergunta",pergunta.getId());
       
       Map<String,Object> options = new HashMap<String, Object>();
       options.put("modal", true);
       options.put("scroll", true);
       options.put("width",700);
       options.put("height",800);
               
       RequestContext.getCurrentInstance().openDialog("PerguntaSelecionada",options,null);
    }
    
    public void filtra()
    {
        if ( this.idSelecionado != 0)
        {
            EntityManager manager = getManager();
        
            TagRepository tagRepository = new TagRepository(manager);
            this.perguntas = tagRepository.procura(idSelecionado).getPerguntas();
        }
    }
    
    public List<Pergunta> getPerguntas() 
    {
        if (this.idSelecionado == 0)
        {    
            EntityManager manager = getManager();
       
            TagRepository repository =  new TagRepository(manager);
       
            this.perguntas = repository.ObterListaAssociadasATag();
        }
        
        return this.perguntas;
    }
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }

    /**
     * @param perguntas the perguntas to set
     */
    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    /**
     * @return the idSelecionado
     */
    public Long getIdSelecionado() {
        return idSelecionado;
    }

    /**
     * @param idSelecionado the idSelecionado to set
     */
    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }
    
    
    
}
