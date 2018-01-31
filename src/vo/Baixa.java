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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Eduardo
 */
@Entity
public class Baixa implements Serializable, EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_baixa;

    @Column(length = 200, nullable = false)
    private String observacao;

    @Temporal(TemporalType.DATE)
    private Calendar dt_baixa;

    @ManyToOne(fetch = FetchType.EAGER)
    private TipoSaida id_tipo_saida = null;

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

    public Calendar getDt_baixa() {
        return dt_baixa;
    }

    public void setDt_baixa(Calendar dt_baixa) {
        this.dt_baixa = dt_baixa;
    }

    public TipoSaida getId_tipo_saida() {
        return id_tipo_saida;
    }

    public void setId_tipo_saida(TipoSaida id_tipo_saida) {
        this.id_tipo_saida = id_tipo_saida;
    }

}
