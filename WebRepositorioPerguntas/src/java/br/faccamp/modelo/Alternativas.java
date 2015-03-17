/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.modelo;

import java.util.ArrayList;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Eduardo
 */

@Entity
public class Alternativas {
    
    @Id @GeneratedValue
    private Long Id;
    private String Resposta;
    private Boolean Tipo;
    
    @ManyToOne
    private Pergunta pergunta;
            
    public Alternativas(Long id,String resposta)
    {
        this.Id = id;
        this.Resposta = resposta;
    }

    public Alternativas() {
        
    }

    /**
     * @return the Id
     */
    public Long getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(Long Id) {
        this.Id = Id;
    }

    /**
     * @return the Resposta
     */
    public String getResposta() {
        return Resposta;
    }

    /**
     * @param Resposta the Resposta to set
     */
    public void setResposta(String Resposta) {
        this.Resposta = Resposta;
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
     * @return the Tipo
     */
    public Boolean getTipo() {
        return Tipo;
    }

    /**
     * @param Tipo the Tipo to set
     */
    public void setTipo(Boolean Tipo) {
        this.Tipo = Tipo;
    }

   
    
    
    
}
