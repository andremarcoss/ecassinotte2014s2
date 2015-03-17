/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.modelo.repositorios;

import br.faccamp.modelo.Genero;
import br.faccamp.modelo.Pergunta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Eduardo
 */
public class GeneroRepository {
    
    private EntityManager manager;
    
    public GeneroRepository(EntityManager manager)
    {
        this.manager = manager;
    }
    
    public void adiciona ( Genero genero )
    {
        this.manager.persist(genero);
    }
    
    public void remove ( Long id )
    {
        Genero genero = this.procura(id);
        this.manager.remove(genero);
    }
    
    public Genero atualiza ( Genero genero )
    {
        return this.manager.merge(genero);
    }
    
    public Genero procura(Long id)
    {
        return this.manager.find(Genero.class,id);
    }
    
    public List<Genero> getLista()
    {
        Query query = this.manager.createQuery("select x from Genero x");
        return query.getResultList();
    }
    
    public List<Pergunta> getPerguntasPorGenero(Long id)
    {
        Genero genero = this.procura(id);
        Query query = this.manager.createQuery("select x from Pergunta x where x.genero = :genero");
        query.setParameter("genero", genero);
        List<Pergunta> perguntas = query.getResultList();
        return perguntas;        
    }
   
}
