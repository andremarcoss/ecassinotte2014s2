/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.modelo.Pergunta;
import br.faccamp.modelo.Tag;
import br.faccamp.modelo.repositorios.TagRepository;
import java.util.ArrayList;
import java.util.List;
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
@ManagedBean ( name = "desassociarTag" )
@ViewScoped
public class DesassociarTag {
    
    private Tag tag;
    private Long IdSelecionado;
    private List<Pergunta> itensSelecionados;
    
    public void preparaDesc()
    {
        EntityManager manager = getManager();
        TagRepository tagRepo = new TagRepository(manager);
        
        this.tag = tagRepo.procura(IdSelecionado);
        
    }
    
    public void desassociar()
    {
        EntityManager manager = getManager();
        TagRepository tagRepo = new TagRepository(manager);
        
        if (this.itensSelecionados.isEmpty())
        {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Falha",  "Nenhuma Pergunta selecionada para desassociar "));
            return;
        }
        
        // Removo do objeto as perguntas selecionadas
        for (Pergunta tagSelec:getItensSelecionados())
        {
            for (Pergunta pergAux:this.tag.getPerguntas())
            {
                if ( tagSelec.getId() == pergAux.getId() )
                {
                    tag.getPerguntas().remove(pergAux);
                    break;
                }
            }
        }
        
        tagRepo.atualiza(tag);
        
        this.tag = null;
        this.IdSelecionado = 0l;
        this.itensSelecionados = new ArrayList<Pergunta>();
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Tag desassociada a Pergunta(s)"));
    }
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
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

    /**
     * @return the IdSelecionado
     */
    public Long getIdSelecionado() {
        return IdSelecionado;
    }

    /**
     * @param IdSelecionado the IdSelecionado to set
     */
    public void setIdSelecionado(Long IdSelecionado) {
        this.IdSelecionado = IdSelecionado;
    }

    /**
     * @return the itensSelecionados
     */
    public List<Pergunta> getItensSelecionados() {
        return itensSelecionados;
    }

    /**
     * @param itensSelecionados the itensSelecionados to set
     */
    public void setItensSelecionados(List<Pergunta> itensSelecionados) {
        this.itensSelecionados = itensSelecionados;
    }

 
}
