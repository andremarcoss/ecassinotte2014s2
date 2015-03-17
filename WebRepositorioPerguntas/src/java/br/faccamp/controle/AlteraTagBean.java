/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.modelo.Tag;
import br.faccamp.modelo.repositorios.TagRepository;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Eduardo
 */
@ManagedBean ( name = "alteraTagBean" )
@ViewScoped
public class AlteraTagBean {
    
    private Long idSelecionado;
    private Tag tag;
    
    public void preparaAlteracao()
    {
        EntityManager manager = getManager();
        
        TagRepository repository = new TagRepository(manager);
        
        this.tag = repository.procura(this.idSelecionado);
    }
    
    public void alteraTag()
    {
        EntityManager manager = getManager();
        
        TagRepository repository = new TagRepository(manager);
        
        repository.atualiza(this.tag);
        
        this.idSelecionado = 0l;
        this.tag = null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Tag Alterada"));
    }
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
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

    /**
     * @return the tag
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    
    
    
}
