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
    @NamedQuery(name = "Categoria.buscarPorDescricao",query = "select c from Categoria c where UPPER (c.descricao) like :descricao"), 
    @NamedQuery(name = "Categoria.BuscarTodos", query = "Select c from Categoria c ORDER BY (c.descricao)")})
public class Categoria implements Serializable, EntidadeBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_categoria;
    
    @Column(length = 50, nullable = false)
    private String descricao;
    

    @Override
    public Long getId() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao.toUpperCase();
    }
    
    
    
}
