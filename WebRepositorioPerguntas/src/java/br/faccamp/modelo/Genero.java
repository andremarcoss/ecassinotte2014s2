/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author Eduardo
 */
@Entity
public class Genero {
   
    @Id @GeneratedValue
    private Long Id;
    private String Genero;
    @Lob
    @Column(columnDefinition="TEXT")
    private String Descricao;
    
    public Genero(Long id,String genero)
    {
        this.Id = id;
        this.Genero = genero;
    }

    public Genero() {
       
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
     * @return the genero
     */
    public String getGenero() {
        return Genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.Genero = genero;
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
    
    
    
}
