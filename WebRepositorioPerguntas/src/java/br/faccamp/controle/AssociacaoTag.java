/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.modelo.Pergunta;
import br.faccamp.modelo.Tag;
import br.faccamp.modelo.repositorios.PerguntasRepository;
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
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Eduardo
 */
@ManagedBean ( name = "associacaoTag" )
@ViewScoped
public class AssociacaoTag {
    
    private DualListModel<Pergunta> perguntas;
    private Long IdSelecionado;
    private Tag tag;
    
    public void preparaAssociacao()
    {
        EntityManager manager = getManager();
        TagRepository repositoryTag = new TagRepository(manager);
        this.setTag(repositoryTag.procura(getIdSelecionado()));
                
        List<Pergunta> listaPerguntas = repositoryTag.getPerguntaNaoAssociadasATag(this.tag);
        List<Pergunta> listaPerguntasSelecionadas = new ArrayList<Pergunta>();
        
        this.perguntas = new DualListModel<Pergunta>(listaPerguntas, listaPerguntasSelecionadas);
                
    }
    
    public void associar()
    {
        List<Pergunta> perguntasAssociadas = this.perguntas.getTarget();
        
        if ( perguntasAssociadas.isEmpty())
        {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Falha",  "Nenhuma Pergunta Associada a Tag"));
            return;
        }
        
        EntityManager manager = getManager();
        TagRepository associamento = new TagRepository(manager);
        
        // Tenho que pegar os objetos no banco novamente
        // pois ao colocar na DualListModel ele acaba
        // zoando na pergunta as Tag's
        // Não sei o motivo
                
        PerguntasRepository reposityPerg = new PerguntasRepository(manager);
        this.tag = associamento.procura(this.tag.getId());
                      
        for (Pergunta alt:perguntasAssociadas)
        {
            Pergunta aux = reposityPerg.procura(alt.getId());
            this.tag.getPerguntas().add(aux);
        }
        
        associamento.adiciona(tag);
        
        this.perguntas = null;
        this.tag = null;
        this.IdSelecionado = 0l;
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Tag Associada a Pergunta(s)"));
        
    }
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }
     
    public void AposTransferir(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        
        for(Object item : event.getItems()) {
            Pergunta pergunta;
            pergunta = (Pergunta) item;
            builder.append(pergunta.getDescricao()).append("<br />");
        }
         
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Itens Transferidos");
        msg.setDetail(builder.toString());
         
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }  

    /**
     * @return the perguntas
     */
    public DualListModel<Pergunta> getPerguntas() {
        return perguntas;
    }

    /**
     * @param perguntas the perguntas to set
     */
    public void setPerguntas(DualListModel<Pergunta> perguntas) {
        this.perguntas = perguntas;
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
