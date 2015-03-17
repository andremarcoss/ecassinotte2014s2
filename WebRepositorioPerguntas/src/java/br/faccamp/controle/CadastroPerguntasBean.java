/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.controle;

import br.faccamp.Util.ConversaoArquivo;
import br.faccamp.modelo.Alternativas;
import br.faccamp.modelo.Genero;
import br.faccamp.modelo.Pergunta;
import br.faccamp.modelo.repositorios.AlternativasRepository;
import br.faccamp.modelo.repositorios.GeneroRepository;
import br.faccamp.modelo.repositorios.PerguntasRepository;
import br.faccamp.modelo.repositorios.TagRepository;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.event.Event;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Eduardo
 */
@ManagedBean ( name = "perguntasBean" )
@ViewScoped
public class CadastroPerguntasBean implements Serializable {
    
    private Pergunta pergunta;
    private ArrayList<Alternativas> alternativas = new ArrayList<>();
    private Long generoID;
    private String RespostaAux;
    private Alternativas selectedAlternativa;
    private UploadedFile file;
    private String StatusArquivo;
    
    
    @PostConstruct
    public void init()
    {
        pergunta = new Pergunta();
        pergunta.setTipo(1);
    }
    
    
    public void handleFileUpload(FileUploadEvent event) {
        
        this.setFile(event.getFile());
            
        FacesContext ctx = FacesContext.getCurrentInstance();  
          
        ServletContext servletContext =  (ServletContext)ctx.getExternalContext().getContext();
        
        String arquivo = file.getFileName();
        
        String path = servletContext.getRealPath("/resources/imagens");
        
        ConversaoArquivo conver = new ConversaoArquivo();
        
        File FileSalvo;
        
        try 
        {
            FileSalvo = conver.SalvarArquivoEmDisco(path, arquivo, file.getInputstream());
        } 
        catch (IOException ex) 
        {
            FacesMessage message = new FacesMessage("Erro", event.getFile().getFileName() + "não foi possivel fazer uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
          
        this.pergunta.setFoto(conver.getBytes(FileSalvo));
        this.pergunta.setExt(this.file.getFileName().substring(file.getFileName().lastIndexOf('.') + 1));
        
        FacesMessage message = new FacesMessage("Sucesso", event.getFile().getFileName() + " feito uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        this.StatusArquivo = "Arquivo Carregado";
        
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
    
    public void ModificaoStatus(Alternativas alt)
    {
        // Verifica se e Objetiva
        // se for devo verificar se alguma ja nao foi marcada
                        
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
    
       
    public void adiciona()
    {
        EntityManager manager = this.getManager();
        PerguntasRepository repository = new PerguntasRepository(manager);
        GeneroRepository repositoryGenero = new GeneroRepository(manager);
        AlternativasRepository repositorioPerguntas = new AlternativasRepository(manager);
        
        if ( this.getGeneroID() != null)
        {
            Genero generoAux = repositoryGenero.procura(this.getGeneroID());
            this.getPergunta().setGenero(generoAux);
        }
                
        if ( this.getPergunta().getId() == null)
        {
            repository.adiciona(this.getPergunta());
            
            for (Alternativas alt:alternativas)
            {
                alt.setPergunta(pergunta);
                repositorioPerguntas.adiciona(alt);
            }
        }
        
               
        this.pergunta  = new Pergunta();
        this.alternativas = new ArrayList<>();
        this.generoID = 0l;
        this.RespostaAux = "";
        this.StatusArquivo = "";
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Pergunta Adicionada"));
                    
    }
    
    public void RemoveItem(Alternativas remove)
    {
       alternativas.remove(remove);
    }
    
    public void adicionaAlternativas()
    {
        Alternativas aux = new Alternativas();
        
        aux.setResposta(this.RespostaAux);
        aux.setTipo(false);
        
        alternativas.add(aux);
    }
    
    
    private EntityManager getManager()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
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
    public ArrayList<Alternativas> getAlternativas() {
        return alternativas;
    }

    /**
     * @param alternativas the alternativas to set
     */
    public void setAlternativas(ArrayList<Alternativas> alternativas) {
        this.alternativas = alternativas;
    }

    /**
     * @return the generoID
     */
    public Long getGeneroID() {
        return generoID;
    }

    /**
     * @param generoID the generoID to set
     */
    public void setGeneroID(Long generoID) {
        this.generoID = generoID;
    }

    /**
     * @return the RespostaAux
     */
    public String getRespostaAux() {
        return RespostaAux;
    }

    /**
     * @param RespostaAux the RespostaAux to set
     */
    public void setRespostaAux(String RespostaAux) {
        this.RespostaAux = RespostaAux;
    }

    /**
     * @return the selectedAlternativa
     */
    public Alternativas getSelectedAlternativa() {
        return selectedAlternativa;
    }

    /**
     * @param selectedAlternativa the selectedAlternativa to set
     */
    public void setSelectedAlternativa(Alternativas selectedAlternativa) {
        this.selectedAlternativa = selectedAlternativa;
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
