/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import DAO.EntidadeBase;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.TemporalType;

/**
 *
 * @author Henrique
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Baixa.BuscarTodasBaixas", query = "Select e from Baixa e ORDER BY(e.id_baixa)"),
})
public class Baixa implements Serializable, EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_baixa;

    @Column(length = 200, nullable = false)
    private String observacao;

    @Temporal(TemporalType.DATE)
    private Date dt_baixa;

    @ManyToOne(fetch = FetchType.EAGER)
    private TipoSaida id_tipo_saida = null;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pessoa id_pessoa = null;

    @Override
    public Long getId() {
        return id_baixa;
    }

    public void setId_baixa(Long id_baixa) {
        this.id_baixa = id_baixa;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao.toUpperCase();
    }

    public Date getDt_baixa() {
        return dt_baixa;
    }

    public void setDt_baixa(Date dt_baixa) {
        this.dt_baixa = dt_baixa;
    }

    public TipoSaida getId_tipo_saida() {
        return id_tipo_saida;
    }

    public void setId_tipo_saida(TipoSaida id_tipo_saida) {
        this.id_tipo_saida = id_tipo_saida;
    }

    public Pessoa getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(Pessoa id_pessoa) {
        this.id_pessoa = id_pessoa;
    }
}
