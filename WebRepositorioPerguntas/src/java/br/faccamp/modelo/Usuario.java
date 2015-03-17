/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.modelo;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Eduardo
 */
@Entity
public class Usuario {
    
    @Id @GeneratedValue
    private Long Id;
    
    private String Nome;
    private String Senha;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date UltimoAcesso;

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
     * @return the Nome
     */
    public String getNome() {
        return Nome;
    }

    /**
     * @param Nome the Nome to set
     */
    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    /**
     * @return the Senha
     */
    public String getSenha() {
        return Senha;
    }

    /**
     * @param Senha the Senha to set
     */
    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    /**
     * @return the UltimoAcesso
     */
    public Date getUltimoAcesso() {
        return UltimoAcesso;
    }

    /**
     * @param UltimoAcesso the UltimoAcesso to set
     */
    public void setUltimoAcesso(Date UltimoAcesso) {
        this.UltimoAcesso = UltimoAcesso;
    }

 
    
}
