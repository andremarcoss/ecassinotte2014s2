package br.faccamp.modelo;

import br.faccamp.modelo.Pergunta;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-12-13T13:06:13")
@StaticMetamodel(Alternativas.class)
public class Alternativas_ { 

    public static volatile SingularAttribute<Alternativas, String> Resposta;
    public static volatile SingularAttribute<Alternativas, Boolean> Tipo;
    public static volatile SingularAttribute<Alternativas, Pergunta> pergunta;
    public static volatile SingularAttribute<Alternativas, Long> Id;

}