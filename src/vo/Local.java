/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import DAO.EntidadeBase;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 *
 * @author Eduardo
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Local.consultarPorDescricao",
            query = "select l from Local l where UPPER (l.descricao) like :descricao"),
    
    @NamedQuery(name = "Local.consultarPorNumero",
            query = "select l from Local l where l.numero = :numero"),
    
    @NamedQuery(name = "Local.consultarPorBloco",
            query = "select l from Local l where UPPER (l.descricao) like :bloco"),
    
    @NamedQuery(name = "Local.consultarPorPessoaResponsavel",
            query = "select l from Local l where UPPER (l.responsavel) like :responsavel"),
    
      @NamedQuery(name = "Local.consultarTodos",
            query = "select l from Local l ORDER BY (l.descricao)"),
      
       @NamedQuery(name = "Local.consultaDepartamento",
            query = "select l from Local l WHERE l.id_departamento.id_departamento = :departamento ORDER BY (l.descricao)")

})
public class Local implements Serializable, EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_local;

    @Column(length = 100, nullable = false)
    private String descricao;

    @Column(nullable = false)
    private int numero;
    
//    @Column(length = 10)
//    private String bloco;

    @Column(length = 100, nullable = false)
    private String responsavel;

    @ManyToOne(fetch = FetchType.EAGER)
    private Departamento id_departamento = null;
    
    public String getSigla(){
        return id_departamento.getSigla();
    }
    
        

    public Local() {
        this.id_local = null;
        this.descricao = null;
        this.numero = 0;
        this.responsavel = null;
    }

    @Override
    public Long getId() {
        return id_local;
    }

    public void setId_local(Long id_local) {
        this.id_local = id_local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao.toUpperCase();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel.toUpperCase();
    }

    public Departamento getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(Departamento id_departamento) {
        this.id_departamento = id_departamento;
    }
//
//    public String getBloco() {
//        return bloco;
//    }
//
//    public void setBloco(String bloco) {
//        this.bloco = bloco;
//    }

 
    
    

    
    
}
