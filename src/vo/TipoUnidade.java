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
    @NamedQuery(name = "TipoUnidade.descricao",
            query = "select t from TipoUnidade t where UPPER (t.descricao) like :descricao "),
       @NamedQuery(name = "TipoUnidade.BuscarTodos",
            query = "select t from TipoUnidade t ORDER BY (t.descricao)"),
        @NamedQuery(name = "TipoUnidade.Sigla",
            query = "select t from TipoUnidade t  where UPPER (t.sigla) like :sigla ")

})
public class TipoUnidade implements Serializable, EntidadeBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_tipo_unidade;
    
    @Column(length = 20, nullable = false)
    private String descricao;
    
      @Column(length = 3, nullable = false)
    private String sigla;

    @Override
    public Long getId() {
        return id_tipo_unidade;
    }

    public void setId_tipo_unidade(Long id_tipo_unidade) {
        this.id_tipo_unidade = id_tipo_unidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao.toUpperCase();
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla.toUpperCase();
    }
    
}
