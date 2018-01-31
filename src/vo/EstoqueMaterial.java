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

/**
 *
 * @author Eduardo
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "EstoqueMaterial.BuscarPorIdMaterial",
            query = "select e from EstoqueMaterial e where e.id_material.id_material = :idMaterial"),
    @NamedQuery(name = "EstoqueMaterial.QtdDisponivelDoMaterial",
            query = "select SUM(e.quantidade_disponivel) from EstoqueMaterial e where e.id_material.id_material = :idMaterial"),
     @NamedQuery(name = "EstoqueMaterial.BuscarPorIdMaterialIdlocal",
            query = "select e from EstoqueMaterial e where e.id_material.id_material = :idMaterial AND e.id_local.id_local = :idLocal")

})
public class EstoqueMaterial implements Serializable, EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_estoque;

    @Column(nullable = false)
    private int quantidade_emprestada;

    @Column(nullable = false)
    private int quantidade_disponivel;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Local id_local = null;

    @ManyToOne(fetch = FetchType.EAGER)
    private Material id_material = null;

    public Local getId_departamento() {
        return id_local;
    }

    public void setId_departamento(Local id_departamento) {
        this.id_local = id_departamento;
    }

    public Material getId_material() {
        return id_material;
    }

    public void setId_material(Material id_material) {
        this.id_material = id_material;
    }


    @Override
    public Long getId() {
        return id_estoque;
    }

    public void setId_estoque(Long id_estoque) {
        this.id_estoque = id_estoque;
    }

    public int getQuantidade_emprestada() {
        return quantidade_emprestada;
    }

    public void setQuantidade_emprestada(int quantidade_emprestada) {
        this.quantidade_emprestada = quantidade_emprestada;
    }

    public int getQuantidade() {
        return quantidade_disponivel;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade_disponivel = quantidade;
    }

}
