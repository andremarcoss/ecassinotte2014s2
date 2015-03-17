/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.modelo.repositorios;

import br.faccamp.modelo.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Eduardo
 */
public class UsuarioRepository {
    
    private EntityManager manager;
    
    public UsuarioRepository(EntityManager manager)
    {
        this.manager = manager;
    }
    
    public void adiciona ( Usuario usuario )
    {
        this.manager.persist(usuario);
    }
    
    public void remove ( Long id )
    {
        Usuario usuario = this.procura(id);
        this.manager.remove(usuario);
    }
    
    public Usuario atualiza ( Usuario usuario )
    {
        return this.manager.merge(usuario);
    }
    
    public Usuario procura(Long id)
    {
        return this.manager.find(Usuario.class,id);
    }
    
    public List<Usuario> getLista()
    {
        Query query = this.manager.createQuery("select x from Usuario x");
        return query.getResultList();
    }
    
    public Usuario getUsuarioPorNome(String nome)
    {
        Query query = this.manager.createQuery("select x from Usuario x where x.Nome = :nome");
        query.setParameter("nome", nome);
        
        List<Usuario> resultados = query.getResultList();
        return resultados.get(0);
    }
    
}
