/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.modelo.Tag;
import br.faccamp.modelo.repositorios.TagRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

/**
 *
 * @author Eduardo
 */
@ManagedBean ( name = "tagBean" )
@ViewScoped
public class TagBean implements Serializable {
    
    private Tag tag = new Tag();
    private List<Tag> tags;
    private List<Tag> tagsSelecionadas;
    
    private TagCloudModel modelTag;
    
    public void adiciona()
    {
        EntityManager manager = this.getManager();
        TagRepository repository = new TagRepository(manager);
        
        repository.adiciona(this.getTag());

        this.tag = new Tag();
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Tag Adicionado"));
                    
    }
    
    public List<Tag> getTags()
    {
        //if (this.tags == null)
        {
            EntityManager manager = this.getManager();
            TagRepository repository = new TagRepository(manager);
            this.setTags(repository.getLista());
        }
        
        return this.tags;
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
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the tagsSelecionadas
     */
    public List<Tag> getTagsSelecionadas() {
        return tagsSelecionadas;
    }

    /**
     * @param tagsSelecionadas the tagsSelecionadas to set
     */
    public void setTagsSelecionadas(List<Tag> tagsSelecionadas) {
        this.tagsSelecionadas = tagsSelecionadas;
    }

    /**
     * @return the modelTag
     */
    public TagCloudModel getModelTag() {
        
        this.modelTag = new DefaultTagCloudModel();
        
        List<Tag> listaTag = this.getTags();
        
        Random gerador = new Random();
        
        for(Tag tagLi:listaTag)
        {
            boolean resultado = gerador.nextBoolean();
            
            if ( resultado )
                this.modelTag.addTag(new DefaultTagCloudItem(tagLi.getTag(), tagLi.getId().intValue()));
            else
               this.modelTag.addTag(new DefaultTagCloudItem(tagLi.getTag(), "#", tagLi.getId().intValue()));
        }
      
        return modelTag;
    }

    /**
     * @param modelTag the modelTag to set
     */
    public void setModelTag(TagCloudModel modelTag) {
        this.modelTag = modelTag;
    }

    
    
}
