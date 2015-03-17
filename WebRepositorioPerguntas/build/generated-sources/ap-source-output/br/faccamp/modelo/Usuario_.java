package br.faccamp.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-12-13T13:06:13")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> Senha;
    public static volatile SingularAttribute<Usuario, Long> Id;
    public static volatile SingularAttribute<Usuario, Date> UltimoAcesso;
    public static volatile SingularAttribute<Usuario, String> Nome;

}