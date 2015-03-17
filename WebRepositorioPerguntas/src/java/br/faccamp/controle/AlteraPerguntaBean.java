/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.Util.ConversaoArquivo;
import br.faccamp.modelo.Alternativas;
import br.faccamp.modelo.Pergunta;
import br.faccamp.modelo.repositorios.AlternativasRepository;
import br.faccamp.modelo.repositorios.PerguntasRepository;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Eduardo
 */
@ManagedBean ( name = "alteraPergBean" )
@ViewScoped
public class AlteraPerguntaBean implements Serializable {
    
    private Long IdSelecionado;
    private Pergunta pergunta;
    private List<Alternativas> alternativas;
    private ArrayList<Alternativas> removidas;
    private String respostaAux;
    private UploadedFile file;
    private String StatusArquivo;
    
    public void handleFileUpload(FileUploadEvent event) {
        
        this.setFile(event.getFile());
            
        FacesContext ctx = FacesContext.getCurrentInstance();  
          
        ServletContext servletContext =  (ServletContext)ctx.getExternalContext().getContext();
        
        String arquivo = getFile().getFileName();
        
        String path = servletContext.getRealPath("/resources/imagens");
        
        ConversaoArquivo conver = new ConversaoArquivo();
        
        File FileSalvo;
        
        try 
        {
            FileSalvo = conver.SalvarArquivoEmDisco(path, arquivo, getFile().getInputstream());
        } 
        catch (IOException ex) 
        {
            FacesMessage message = new FacesMessage("Erro", event.getFile().getFileName() + "não foi possivel fazer uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
          
        this.pergunta.setFoto(conver.getBytes(FileSalvo));
        this.pergunta.setExt(this.getFile().getFileName().substring(getFile().getFileName().lastIndexOf('.') + 1));
        
        FacesMessage message = new FacesMessage("Sucesso", event.getFile().getFileName() + " feito uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        this.setStatusArquivo("Arquivo Carregado");
        
        FileSalvo.delete();
       
    }
    
    public void alteraTipo()
    {
        // Se trocou o Tipo ( Objetiva ou Multipla-Escolha ) 
        // limpo verdadeiro todas as alternativas
        for (Alternativas alt:alternativas)
        {
            alt.setTipo(false);
        }
    }
    
    public void removerArquivo()
    {
        this.pergunta.setFoto(null);
        this.pergunta.setExt(null);
        
        FacesMessage message = new FacesMessage("Sucesso", "Imagem Removida");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }
    
    public void preparaAlteracao()
    {
        EntityManager manager = getManager();
        PerguntasRepository repositoryPerg = new PerguntasRepository(manager);
        this.pergunta = repositoryPerg.procura(IdSelecionado);
        
        this.setAlternativas(repositoryPerg.ObterAlternativasPergunta(IdSelecionado));
        
        this.setRemovidas((ArrayList<Alternativas>) new ArrayList());
   
    }
    
    public void Alterar()
    {
        EntityManager manager = getManager();
        PerguntasRepository repositoryPerg = new PerguntasRepository(manager);
        repositoryPerg.atualiza(pergunta);
        
        AlternativasRepository repositoryAlternativas = new AlternativasRepository(manager);
        
        for (Alternativas alt:alternativas)
        {
            if ( alt.getId() == null )
                repositoryAlternativas.adiciona(alt);
            else
                repositoryAlternativas.atualiza(alt);
        }
        
        for (Alternativas alt:getRemovidas())
        {
            repositoryAlternativas.remove(alt.getId());
        }
        
        this.IdSelecionado = 0l;
        this.pergunta = null;
        this.alternativas = new ArrayList<>();
                
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Pergunta Alterada"));
        
    }
    
    public void removeItem(Alternativas alternativas)
    {
        if ( alternativas.getId() != null)
        {
            this.removidas.add(alternativas);
        }
        
        this.alternativas.remove(alternativas);
    }
    
    public void adicionaAlternativas()
    {
        Alternativas altaux = new Alternativas();
        altaux.setResposta(this.respostaAux);
        altaux.setPergunta(pergunta);
        altaux.setTipo(false);
        this.alternativas.add(altaux);
    }
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }
    
    private void ModificaoStatus(Alternativas alt)
    {
        // Verifica se e Objetiva
        // se for devo verificar se alguma ja não foi marcada
                        
        if ( this.pergunta.getTipo() == 1 && alt.getTipo() == true)
        {
           // Verifico na lista se posso trocar para verdadeiro
           // Ja que pode existir apenas uma verdadeira
           
            for ( Alternativas aux:alternativas)
            {
                if (aux.getTipo() == true && !aux.equals(alt))
                {
                    alt.setTipo(false);
                    return;
                }
            }
            
            // Se saiu do laço significa que nenuma esta selecionada
            alt.setTipo(true);
            
        }
            
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Pergunta Editada", "Descricao : " + ((Alternativas) event.getObject()).getResposta());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        ModificaoStatus((Alternativas) event.getObject());
        
        
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edição Pergunta Cancelada", "Descricao : " +  ((Alternativas) event.getObject()).getResposta());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
 
    /**
     * @return the IdSelecionado
     */
    public Long getIdSelecionado() {
        return IdSelecionado;
    }

    /**
     * @param IdSelecionado the IdSelecionado to set
     */
    public void setIdSelecionado(Long IdSelecionado) {
        this.IdSelecionado = IdSelecionado;
    }

    /**
     * @return the pergunta
     */
    public Pergunta getPergunta() {
        return pergunta;
    }

    /**
     * @param pergunta the pergunta to set
     */
    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }


    /**
     * @return the alternativas
     */
    public List<Alternativas> getAlternativas() {
        return alternativas;
    }

    /**
     * @param alternativas the alternativas to set
     */
    public void setAlternativas(List<Alternativas> alternativas) {
        this.alternativas = alternativas;
    }

    /**
     * @return the removidas
     */
    public ArrayList<Alternativas> getRemovidas() {
        return removidas;
    }

    /**
     * @param removidas the removidas to set
     */
    public void setRemovidas(ArrayList<Alternativas> removidas) {
        this.removidas = removidas;
    }

    /**
     * @return the respostaAux
     */
    public String getRespostaAux() {
        return respostaAux;
    }

    /**
     * @param respostaAux the respostaAux to set
     */
    public void setRespostaAux(String respostaAux) {
        this.respostaAux = respostaAux;
    }

    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * @return the StatusArquivo
     */
    public String getStatusArquivo() {
        return StatusArquivo;
    }

    /**
     * @param StatusArquivo the StatusArquivo to set
     */
    public void setStatusArquivo(String StatusArquivo) {
        this.StatusArquivo = StatusArquivo;
    }

 
   

  
}
