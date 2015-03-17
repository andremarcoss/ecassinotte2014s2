/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.modelo.repositorios;

import br.faccamp.modelo.Genero;
import br.faccamp.modelo.Pergunta;
import br.faccamp.modelo.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Eduardo
 */
public class TagRepository {
    
    private EntityManager manager;
    
    public TagRepository(EntityManager manager)
    {
        this.manager = manager;
    }
    
    public void adiciona ( Tag tag )
    {
        this.manager.persist(tag);
    }
    
    public void remove ( Long id )
    {
        Tag tag = this.procura(id);
        this.manager.remove(tag);
    }
    
    public Tag atualiza ( Tag tag )
    {
        return this.manager.merge(tag);
    }
    
    public Tag procura(Long id)
    {
        return this.manager.find(Tag.class,id);
    }
    
    public List<Tag> getLista()
    {
        Query query = this.manager.createQuery("select x from Tag x");
        return query.getResultList();
    }
    
    public List<Pergunta> getPerguntaNaoAssociadasATag(Tag tag)
    {
        Query query = this.manager.createQuery("select x from Pergunta x where :tag not MEMBER OF x.tag  ");
        //Query query = this.manager.createQuery("select x from Pergunta x where not in ( select o from Tag o where o.Tag :tag and  ");
        query.setParameter("tag", tag);
        return query.getResultList();
    }
    
    public List<Pergunta> ObterListaAssociadasATag()
    {
        Query query = this.manager.createQuery("SELECT x FROM Pergunta x where x.tag IS NOT EMPTY");
        List<Pergunta> perguntas = query.getResultList();
        return perguntas;
    }
     
}
