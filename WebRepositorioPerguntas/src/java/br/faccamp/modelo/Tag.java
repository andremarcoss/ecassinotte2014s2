/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.modelo;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

/**
 *
 * @author Eduardo
 */
@Entity
public class Tag {
    
    @Id @GeneratedValue
    private Long Id;
    private String Tag;
    @Lob
    @Column(columnDefinition="TEXT")
    private String Descricao;
   
    @ManyToMany
    @JoinTable(name="TagXPerguntas", joinColumns=
    {@JoinColumn(name="tag_id")}, inverseJoinColumns=
    {@JoinColumn(name="pergunta_id")})
    private List<Pergunta> perguntas;
   
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
     * @return the Tag
     */
    public String getTag() {
        return Tag;
    }

    /**
     * @param Tag the Tag to set
     */
    public void setTag(String Tag) {
        this.Tag = Tag;
    }

    /**
     * @return the Descricao
     */
    public String getDescricao() {
        return Descricao;
    }

    /**
     * @param Descricao the Descricao to set
     */
    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    /**
     * @return the perguntas
     */
    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    /**
     * @param perguntas the perguntas to set
     */
    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

   
   
    
    
    
}
