/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import DAO.EntidadeBase;
import java.io.Serializable;
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
import javax.persistence.Transient;
import utilitarios.Mask;

/**
 *
 * @author Henrique
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "EntradaMaterial.BuscarPorEntrada", query = "Select e from EntradaMaterial e WHERE e.id_entrada.id_entrada = :idEntrada ORDER BY(e.id_entrada.dt_entrada)"),
})

public class EntradaMaterial implements Serializable, EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id_entradaMaterial;

    @ManyToOne(fetch = FetchType.EAGER)
    private Material id_material = null;

    @ManyToOne(fetch = FetchType.EAGER)
    private Entrada id_entrada = null;

    @Basic
    private int quantidade_material;

    @Column(scale = 8, precision = 2)
    private float valor_unitario_material;

    @Transient
    private Local local;

    @Transient
    private float Valor_total;

    @Override
    public Long getId() {
        return id_entradaMaterial;
    }

    public void setId_entradaMaterial(Long id_entradaMaterial) {
        this.id_entradaMaterial = id_entradaMaterial;
    }

    public Material getId_material() {
        return id_material;
    }
    
    public String getMaterialNome(){
        return id_material.getDescricao();
    }

    public void setId_material(Material id_material) {
        this.id_material = id_material;
    }

    public Entrada getId_entrada() {
        return id_entrada;
    }

    public void setId_entrada(Entrada id_entrada) {
        this.id_entrada = id_entrada;
    }

    public int getQuantidade_material() {
        return quantidade_material;
    }

    public void setQuantidade_material(int quantidade_material) {
        this.quantidade_material = quantidade_material;
    }

    public float getValor_unitario_material() {
        return valor_unitario_material;
    }

    public void setValor_unitario_material(float valor_unitario_material) {
        this.valor_unitario_material = valor_unitario_material;
    }

    public Local getLocal() {
        return local;
    }

    public String getLocalNome() {
        return local.getDescricao();
    }
    
    

    public void setLocal(Local local) {
        this.local = local;
    }

    public float getValor_total() {
        return Valor_total;
    }

    public void setValor_total(float Valor_total) {
        this.Valor_total = Valor_total;
    }
    
    public String getValor_Unitario_Monetario() {
        String value=""+getValor_unitario_material();
        if (value.length() > 0) {
            int i = value.length();
            i = i - (value.indexOf("."));
            if (i == 2) {
                value += "0";
            }
        }
        Mask mask =new Mask();
        return mask.Monetaria(value);
    }
}
