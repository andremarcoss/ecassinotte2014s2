package br.faccamp.controle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edu - Notebook
 */

import br.faccamp.modelo.Usuario;
import br.faccamp.modelo.repositorios.UsuarioRepository;
import java.util.Date;
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

@ManagedBean( name = "AutenticadorBean")
@ViewScoped
public class AutenticadorBean {
    
    private Map<String,String> mapa;
       
    private String usuario;
    
    private String senha;
    
    private boolean primeiroAcesso;
    
    public String voltarHome()
    {
        return "/Home";
    }
    
    public void criacaoUsuario()
    {
        Usuario usuario = new Usuario();
        usuario.setNome(this.usuario);
        usuario.setSenha(senha);
        
        EntityManager manager = getManager();
        
        UsuarioRepository usuarioRep = new UsuarioRepository(manager);
        usuarioRep.adiciona(usuario);
        
        init();
        
        this.usuario = "";
        this.senha = "";
        
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage("Sucesso","Usuário criado com sucesso");
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null,fm)
                ;
    }
    
    public void init()
    {
        mapa = new HashMap<String, String>();
        
        EntityManager manager = getManager();
        
        UsuarioRepository repository = new UsuarioRepository(manager);
        List<Usuario> lista = repository.getLista();
        
        for (Usuario usu:lista)
        {
            this.mapa.put(usu.getNome(),usu.getSenha());
        }
        
        this.primeiroAcesso = this.mapa.isEmpty();
            
        
    }
           
    public AutenticadorBean()
    {
        init();
    }
        
    
    public String autentica()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        
        if ( this.mapa.containsKey(this.getUsuario()) &&
                this.mapa.get(this.getUsuario()).equals(this.getSenha()) )
        {
            ExternalContext ec = fc.getExternalContext();
            HttpSession session = (HttpSession) ec.getSession(false);
            session.setAttribute("usuario", this.getUsuario());
            
            EntityManager manager = getManager();
            
            UsuarioRepository repository = new UsuarioRepository(manager);
            
            Usuario usuario = repository.getUsuarioPorNome(this.getUsuario());
            
            session.setAttribute("ultimoAcesso", usuario.getUltimoAcesso());
           
            return "/Home";
            
        }
        else
        {
            FacesMessage fm = new FacesMessage("Falha Autenticação","Usuário e/ou senha inválidos");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null,fm);
            return "/Login";
        }
        
    }
        
    public String registraSaida()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(false);
        
        String nome = (String) session.getAttribute("usuario");
        
        session.removeAttribute("usuario");
        
        // Grava o ultimo acesso

        Date data = new Date(System.currentTimeMillis());
        
        EntityManager manager = getManager();
        UsuarioRepository repository = new UsuarioRepository(manager);
        
        Usuario usuario = repository.getUsuarioPorNome(nome);
        usuario.setUltimoAcesso(data);
        
        repository.atualiza(usuario);
         
        return "/Login";
    
    }
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the primeiroAcesso
     */
    public boolean isPrimeiroAcesso() {
        return primeiroAcesso;
    }
    
    
    
}
