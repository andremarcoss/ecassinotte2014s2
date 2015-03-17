package br.faccamp.modelo;

import br.faccamp.modelo.Pergunta;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-12-13T13:06:13")
@StaticMetamodel(Tag.class)
public class Tag_ { 

    public static volatile SingularAttribute<Tag, String> Descricao;
    public static volatile ListAttribute<Tag, Pergunta> perguntas;
    public static volatile SingularAttribute<Tag, Long> Id;
    public static volatile SingularAttribute<Tag, String> Tag;

}