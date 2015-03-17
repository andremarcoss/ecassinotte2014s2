package br.faccamp.modelo;

import br.faccamp.modelo.Genero;
import br.faccamp.modelo.Tag;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-12-13T13:06:13")
@StaticMetamodel(Pergunta.class)
public class Pergunta_ { 

    public static volatile SingularAttribute<Pergunta, String> ext;
    public static volatile SingularAttribute<Pergunta, String> Descricao;
    public static volatile SingularAttribute<Pergunta, Integer> estado;
    public static volatile SingularAttribute<Pergunta, Integer> Tipo;
    public static volatile SingularAttribute<Pergunta, byte[]> foto;
    public static volatile SingularAttribute<Pergunta, Genero> genero;
    public static volatile SingularAttribute<Pergunta, Long> Id;
    public static volatile ListAttribute<Pergunta, Tag> tag;
    public static volatile SingularAttribute<Pergunta, String> Pergunta;

}