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
    @NamedQuery(name = "Fornecedor.consultarPorNomeFantasia",
            query = "select f from Fornecedor f where UPPER(f.nome_fantasia) Like :nomeFantasia"),
    
    @NamedQuery(name = "Fornecedor.consultarPorRazaoSical",
            query = "select f from Fornecedor f where UPPER (f.razao_social) Like :razaoSocial"),
    
    @NamedQuery(name = "Fornecedor.consultarPorCNPJ",
            query = "select f from Fornecedor f where f.cnpj Like :cnpj"),
    
    @NamedQuery(name = "Fornecedor.consultarPorPessoaResponsavel",
            query = "select f from Fornecedor f where UPPER (f.pessoa_responsavel) Like :nomePessoaResponsavel"),
        
    @NamedQuery(name = "Fornecedor.consultarTodos",
            query = "select f from Fornecedor f ORDER BY (f.nome_fantasia)")

})
public class Fornecedor implements Serializable, EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_fornecedor;

    @Column(length = 100, nullable = false)
    private String razao_social;

    @Column(length = 100, nullable = false)
    private String nome_fantasia;

    @Column(length = 14, nullable = false)
    private String cnpj;

    @Column(length = 20, nullable = false)
    private String inscricao_estadual;

    @Column(length = 20, nullable = false)
    private String telefone;

    @Column(length = 200, nullable = false)
    private String endereco;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String pessoa_responsavel;

    public Fornecedor() {
        this.id_fornecedor = null;
        this.razao_social = null;
        this.nome_fantasia = null;
        this.cnpj = null;
        this.inscricao_estadual = null;
        this.telefone = null;
        this.endereco = null;
        this.email = null;
        this.pessoa_responsavel =null;
    }

    
    @Override
    public Long getId() {
        return id_fornecedor;
    }

    public void setId_fornecedor(Long id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social.toUpperCase();
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia.toUpperCase();
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricao_estadual() {
        return inscricao_estadual;
    }

    public void setInscricao_estadual(String inscricao_estadual) {
        this.inscricao_estadual = inscricao_estadual.toUpperCase();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco.toUpperCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPessoa_responsavel() {
        return pessoa_responsavel;
    }

    public void setPessoa_responsavel(String pessoa_responsavel) {
        this.pessoa_responsavel = pessoa_responsavel.toUpperCase();
    }

}
