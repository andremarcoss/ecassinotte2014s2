/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Eduardo
 */
@Entity
public class Pergunta {
    
    @Id @GeneratedValue
    private Long Id;
    @Lob
    @Column(columnDefinition="TEXT")    
    private String Pergunta;
    private String Descricao;
    private int estado;
    private int Tipo;
    
    @Lob
    private byte[] foto;
    private String ext;
        
    @ManyToOne
    private Genero genero;
    
    @ManyToMany(mappedBy="perguntas")
    private List<Tag> tag;
        
    public Pergunta(Long id, String pergunta)
    {
        this.Id = id;
        this.Pergunta = pergunta;
    }
    
    public Pergunta()
    {
        
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
     * @return the Pergunta
     */
    public String getPergunta() {
        return Pergunta;
    }

    /**
     * @param Pergunta the Pergunta to set
     */
    public void setPergunta(String Pergunta) {
        this.Pergunta = Pergunta;
    }

    /**
     * @return the genero
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * @return the Descrição
     */
    public String getDescricao() {
        return Descricao;
    }

    /**
     * @param Descrição the Descrição to set
     */
    public void setDescricao(String Descrição) {
        this.Descricao = Descrição;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return the Tipo
     */
    public int getTipo() {
        return Tipo;
    }

    /**
     * @param Tipo the Tipo to set
     */
    public void setTipo(int Tipo) {
        this.Tipo = Tipo;
    }

    /**
     * @return the foto
     */
    public byte[] getFoto() {
        return foto;
    }

  
    /**
     * @param foto the foto to set
     */
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    /**
     * @return the ext
     */
    public String getExt() {
        return ext;
    }

    /**
     * @param ext the ext to set
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * @return the tag
     */
    public List<Tag> getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

}
