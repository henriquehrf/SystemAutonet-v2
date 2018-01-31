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
    @NamedQuery(name = "TipoSaida.BuscarPorDescricao",
            query = "Select t from TipoSaida t where UPPER (t.descricao) like :descricao"),
    @NamedQuery(name = "TipoSaida.BuscarTodos",
            query = "Select t from TipoSaida t ORDER BY (t.descricao)")
})
public class TipoSaida implements Serializable, EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_tipo_saida;
    
    @Column(length = 50, nullable = false)
    private String descricao;

    
    @Override
    public Long getId() {
        return id_tipo_saida;
    }

    public void setId_tipo_saida(Long id_tipo_saida) {
        this.id_tipo_saida = id_tipo_saida;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao.toUpperCase();
    }
    
    
}
