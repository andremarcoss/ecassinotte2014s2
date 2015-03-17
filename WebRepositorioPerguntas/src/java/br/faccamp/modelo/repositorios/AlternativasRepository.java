/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.modelo.repositorios;

import br.faccamp.modelo.Alternativas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Eduardo
 */
public class AlternativasRepository {
    
    private EntityManager manager;
    
    public AlternativasRepository(EntityManager manager)
    {
        this.manager = manager;
    }
    
    public void adiciona ( Alternativas alternativas )
    {
        this.manager.persist(alternativas);
    }
    
    public void remove ( Long id )
    {
       Alternativas alternativas = this.procura(id);
       this.manager.remove(alternativas);
    }
    
    public Alternativas atualiza ( Alternativas alternativas )
    {
        return this.manager.merge(alternativas);
    }
    
    public Alternativas procura(Long id)
    {
        return this.manager.find(Alternativas.class,id);
    }
    
    public List<Alternativas> getLista()
    {
        Query query = this.manager.createQuery("select x from Alternativas x");
        return query.getResultList();
    }
   
    
    
    
    
}
