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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Eduardo
 */
@Entity

@NamedQueries({
    @NamedQuery(name = "Departamento.ConsultarPorNome", query = "select d from Departamento d where UPPER (d.nome) Like :nome"),
    @NamedQuery(name = "Departamento.ConsultarPorSigla", query = "select d from Departamento d where UPPER (d.sigla) Like :sigla"),
    @NamedQuery(name = "Departamento.ConsultarTodos", query = "select d from Departamento d ORDER BY (d.nome)")

})
public class Departamento implements Serializable, EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_departamento;

    @Column(length = 5, nullable = false)
    private String sigla;

    @Column(length = 100, nullable = false)
    private String nome;

    public Departamento() {
        this.id_departamento = null;
        this.sigla = null;
        this.nome = null;
    }

    @Override
    public Long getId() {
        return id_departamento;
    }

    public void setId_departamento(long id_departamento) {
        this.id_departamento = id_departamento;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla.toUpperCase();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.toUpperCase();
    }

}
