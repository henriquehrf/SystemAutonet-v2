/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import DAO.EntidadeBase;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author Eduardo
 */
@Entity
@NamedQueries({
     @NamedQuery(name = "EmprestimoEstoqueMaterial.consultarPorNaoDevolvido",
            query = "select m from EmprestimoEstoqueMaterial m WHERE m.dt_devolucao IS NULL AND m.id_emprestimo.id_emprestimo = :id_emprestimo"),
    @NamedQuery(name = "EmprestimoEstoqueMaterial.consultarTodosIdEmprestimo",
            query = "Select m from EmprestimoEstoqueMaterial m WHERE m.id_emprestimo.id_emprestimo = :id_emprestimo"),
    @NamedQuery(name = "EmprestimoEstoqueMaterial.consultarTodos",
            query = "Select m from EmprestimoEstoqueMaterial m"),   
    @NamedQuery(name= "EmprestimoEstoqueMaterial.consultarMaterial",
            query="Select q from EmprestimoEstoqueMaterial q WHERE q.id_emprestimo.id_emprestimo = :id_emprestimo")

})
public class EmprestimoEstoqueMaterial implements Serializable, EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_emprestimoestoquematerial;

    @ManyToOne(fetch = FetchType.EAGER)
    private EstoqueMaterial id_estoquematerial;

    @ManyToOne(fetch = FetchType.EAGER)
    private Emprestimo id_emprestimo;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dt_devolucao;

    @Column(length = 200)
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Material id_material;

    @Basic
    private Integer qtd_devolvida;

    @Basic
    private Integer qtd_emprestada;

    public Long getId() {
        return id_emprestimoestoquematerial;
    }

    public void setId_emprestimoestoquematerial(Long id_emprestimoestoquematerial) {
        this.id_emprestimoestoquematerial = id_emprestimoestoquematerial;
    }

    public EstoqueMaterial getId_estoquematerial() {
        return id_estoquematerial;
    }

    public void setId_estoquematerial(EstoqueMaterial id_estoquematerial) {
        this.id_estoquematerial = id_estoquematerial;
    }

    public Emprestimo getId_emprestimo() {
        return id_emprestimo;
    }

    public void setId_emprestimo(Emprestimo id_emprestimo) {
        this.id_emprestimo = id_emprestimo;
    }

    public Date getDt_devolucao() {
        return dt_devolucao;
    }

    public void setDt_devolucao(Date dt_devolucao) {
        this.dt_devolucao = dt_devolucao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao.toUpperCase();
    }

    public Integer getQtd_devolvida() {
        return qtd_devolvida;
    }

    public void setQtd_devolvida(Integer qtd_devolvida) {
        this.qtd_devolvida = qtd_devolvida;
    }

    public Integer getQtd_emprestada() {
        return qtd_emprestada;
    }

    public void setQtd_emprestada(Integer qtd_emprestada) {
        this.qtd_emprestada = qtd_emprestada;
    }

    public Material getId_material() {
        return id_material;
    }

    public void setId_material(Material id_material) {
        this.id_material = id_material;
    }
    
    public String getNomeMaterial(){
        return id_material.getDescricao();
    }
    
    public String getNomeCategoria(){
        return id_material.getId_categoria().getDescricao();
    }
    
    public Number getQtd(){
        return qtd_emprestada;
    }

}
