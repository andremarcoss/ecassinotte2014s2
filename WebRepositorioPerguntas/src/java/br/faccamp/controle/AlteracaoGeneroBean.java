/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.modelo.Genero;
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
 * @author Edu - Notebook
 */

@ManagedBean ( name = "alteracaoGeneroBean" )
@ViewScoped
public class AlteracaoGeneroBean implements Serializable {
    
    private Genero genero;
    private Long Seleciondado;
      
    
    public void altera()
    {
        EntityManager manager = this.getManager();
        GeneroRepository repository = new GeneroRepository(manager);
        
        repository.atualiza(genero);
        
        this.Seleciondado = 0l;
        this.genero = null;
                
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Gênero Alterado"));
        
    }
    
   
    public void preparaAlteracao()
    {
        
        EntityManager manager = this.getManager();
        GeneroRepository repository = new GeneroRepository(manager);
        
        this.genero = repository.procura(this.Seleciondado);
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
     * @return the Seleciondado
     */
    public Long getSeleciondado() {
        return Seleciondado;
    }

    /**
     * @param Seleciondado the Seleciondado to set
     */
    public void setSeleciondado(Long Seleciondado) {
        this.Seleciondado = Seleciondado;
    }
 
}
