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
import java.util.Map;
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
@ManagedBean ( name = "generoBean" )
@ViewScoped
public class GeneroBean implements Serializable {
    
    private Genero genero = new Genero();
    private List<Genero> generos;
    private List<Genero> generosSelecionados;
        
    public void adiciona()
    {
        EntityManager manager = this.getManager();
        GeneroRepository repository = new GeneroRepository(manager);
        
        repository.adiciona(this.getGenero());

        this.genero = new Genero();
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Genero Adicionado"));
                    
    }
    
    public List<Genero> getGeneros()
    {
        //if (this.generos == null)
        {
            EntityManager manager = this.getManager();
            GeneroRepository repository = new GeneroRepository(manager);
            this.setGeneros(repository.getLista());
        }
        
        return this.generos;
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
     * @param generos the generos to set
     */
    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    /**
     * @return the generosSelecionados
     */
    public List<Genero> getGenerosSelecionados() {
        return generosSelecionados;
    }

    /**
     * @param generosSelecionados the generosSelecionados to set
     */
    public void setGenerosSelecionados(List<Genero> generosSelecionados) {
        this.generosSelecionados = generosSelecionados;
    }

    
    
}
