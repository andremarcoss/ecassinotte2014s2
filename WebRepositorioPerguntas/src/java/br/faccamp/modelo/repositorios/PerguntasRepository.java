/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.modelo.repositorios;

import br.faccamp.modelo.Alternativas;
import br.faccamp.modelo.Pergunta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Eduardo
 */
public class PerguntasRepository {
    
    private EntityManager manager;
    
    public PerguntasRepository(EntityManager manager)
    {
        this.manager = manager;
    }
    
    public void adiciona ( Pergunta pergunta )
    {
        this.manager.persist(pergunta);
    }
    
    public void remove ( Long id )
    {
        Pergunta pergunta = this.procura(id);
        this.manager.remove(pergunta);
    }
    
    public Pergunta atualiza ( Pergunta pergunta )
    {
        return this.manager.merge(pergunta);
    }
    
    public Pergunta procura(Long id)
    {
        return this.manager.find(Pergunta.class,id);
    }
    
    public List<Pergunta> getLista()
    {
        Query query = this.manager.createQuery("select x from Pergunta x");
        return query.getResultList();
    }
    
    public List<Alternativas> ObterAlternativasPergunta(Long id)
    {
        Pergunta pergunta = this.procura(id);
        Query query = this.manager.createQuery("select x from Alternativas x where x.pergunta = :pergunta");
        query.setParameter("pergunta", pergunta);
        List<Alternativas> alternativas = query.getResultList();
        return alternativas;
    }
    
    
}
