/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.modelo.Usuario;
import br.faccamp.modelo.repositorios.UsuarioRepository;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Edu - Notebook
 */
@ManagedBean ( name = "usuarioBean" )
@ViewScoped
public class UsuarioBean implements Serializable  {
    
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSelecionado = new Usuario();
    
    public String interfaceUsuarios()
    {
        return "/DadosUsuarios";
    }
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }
   
    public void adiciona()
    {
        EntityManager manager = getManager();
        UsuarioRepository repository = new UsuarioRepository(manager);
        
        repository.adiciona(usuarioSelecionado);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage("Sucesso","Usuário Adicionado");
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null,fm);
        
        usuarioSelecionado = new Usuario();
        
    }
    
    public void novoUsuario()
    {
        this.usuarioSelecionado = new Usuario();
    }
    
    public void altera()
    {
        EntityManager manager = getManager();
        UsuarioRepository repository = new UsuarioRepository(manager);
        
        repository.atualiza(usuarioSelecionado);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage("Sucesso","Usuário alterado");
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null,fm);
        
        usuarioSelecionado = new Usuario();
        
    }
    
    public void remove()
    {
        EntityManager manager = getManager();
        UsuarioRepository repository = new UsuarioRepository(manager);
        
        repository.remove(usuarioSelecionado.getId());
        
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage("Sucesso","Usuário removido");
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null,fm); 
        
        usuarioSelecionado = new Usuario();
        
    }

    /**
     * @return the listaUsuarios
     */
    public List<Usuario> getListaUsuarios() {
        
        EntityManager manager = getManager();
        
        UsuarioRepository repository = new UsuarioRepository(manager);
        
        this.listaUsuarios = repository.getLista();
        
        return listaUsuarios;
    }

    /**
     * @param listaUsuarios the listaUsuarios to set
     */
    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Usuario Editado", "Nome : " + ((Usuario) event.getObject()).getNome());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edição Usuario Cancelada", "Nome : " +  ((Usuario) event.getObject()).getNome());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void removeItem(Usuario usuario)
    {
        
    }

    /**
     * @return the usuarioSelecionado
     */
    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    /**
     * @param usuarioSelecionado the usuarioSelecionado to set
     */
    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }
    
    
    
}
