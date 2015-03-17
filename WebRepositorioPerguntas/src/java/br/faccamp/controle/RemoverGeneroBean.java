/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.modelo.Genero;
import br.faccamp.modelo.Pergunta;
import br.faccamp.modelo.repositorios.GeneroRepository;
import java.io.Serializable;
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
@ManagedBean ( name = "removeGeneroBean" )
@ViewScoped
public class RemoverGeneroBean implements Serializable {
    
    private Genero genero;
    private Long Selecionado;
        
    public void preparaRemocao()
    {
        
        EntityManager manager = this.getManager();
        GeneroRepository repository = new GeneroRepository(manager);
        
        this.setGenero(repository.procura(this.getSelecionado()));
    }
    
    public void remover()
    {
        EntityManager manager = this.getManager();
        GeneroRepository repositoryGenero = new GeneroRepository(manager);
        List<Pergunta> lista = repositoryGenero.getPerguntasPorGenero(genero.getId());
        
        if ( lista.isEmpty() )
        {
            repositoryGenero.remove(genero.getId());
            
            this.setSelecionado((Long) 0l);
            this.genero = null;
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso",  "Genero Excluido"));
        }
        else
        {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Falha" , "Existe Perguntas Associados ao Gênero" ));
        }
            
    }
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }

    /**
     * @return the genero
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

  
    /**
     * @return the Selecionado
     */
    public Long getSelecionado() {
        return Selecionado;
    }

    /**
     * @param Selecionado the Selecionado to set
     */
    public void setSelecionado(Long Selecionado) {
        this.Selecionado = Selecionado;
    }
    
}
