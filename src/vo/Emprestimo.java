/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import DAO.EntidadeBase;
import enumm.StatusEmprestimo;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @NamedQuery(name = "Emprestimo.BuscarTodos", query = "Select e from Emprestimo e ORDER BY(e.dt_emprestimo)"),
    @NamedQuery(name = "Emprestimo.BuscarPorStatusTodos", query = "Select e from Emprestimo e WHERE e.status_emprestimo = :Status"),
    @NamedQuery(name = "Emprestimo.BuscarPorStatusPessoa", query = "Select e from Emprestimo e WHERE e.status_emprestimo = :Status AND e.id_pessoa_solicita.id_pessoa = :idpessoa ORDER BY(e.dt_emprestimo)"),
    @NamedQuery(name = "Emprestimo.BuscarPorIdPessoa",
            query = "Select e from Emprestimo e WHERE e.id_pessoa_solicita.id_pessoa = :idPessoaSolicita"),
    @NamedQuery(name = "Emprestimo.BuscarPorIdPessoaStatusRetirado",
            query = "Select e from Emprestimo e WHERE e.id_pessoa_solicita.id_pessoa = :idPessoaSolicita AND e.status_emprestimo like 'RETIRADO'"),
    @NamedQuery(name = "Emprestimo.BuscarPorIdPessoaStatusESPERANDO_ANALISE",
            query = "Select e from Emprestimo e WHERE e.id_pessoa_solicita.id_pessoa = :idPessoaSolicita AND e.status_emprestimo like 'ESPERANDO_ANALISE'")

})
public class Emprestimo implements Serializable, EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_emprestimo;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dt_emprestimo;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status_emprestimo;

    @Column(length = 100, nullable = false)
    private String finalidade;

    @Column(length = 200, nullable = false)
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa id_pessoa_solicita;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa id_pessoa_autoriza = null;

    public Long getId() {
        return id_emprestimo;
    }

    public void setId_emprestimo(Long id_emprestimo) {
        this.id_emprestimo = id_emprestimo;
    }

    public Date getDt_emprestimo() {
        return dt_emprestimo;
    }

    public String getDt_emprestimoString() {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        return dt.format(dt_emprestimo);
    }

    public LocalDate getDt_emprestimoLocalDate() {
        Instant instant = Instant.ofEpochMilli(dt_emprestimo.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    public void setDt_emprestimo(Date dt_emprestimo) {
        this.dt_emprestimo = dt_emprestimo;
    }

    public StatusEmprestimo getStatus_emprestimo() {
        return status_emprestimo;
    }

    public void setStatus_emprestimo(StatusEmprestimo status_emprestimo) {
        this.status_emprestimo = status_emprestimo;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade.toUpperCase();
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao.toUpperCase();
    }

    public Pessoa getId_pessoa_solicita() {
        return id_pessoa_solicita;
    }

    public String getId_pessoa_solicitaNome() {
        return id_pessoa_solicita.getNome();
    }

    public void setId_pessoa_solicita(Pessoa id_pessoa_solicita) {
        this.id_pessoa_solicita = id_pessoa_solicita;
    }

    public Pessoa getId_pessoa_autoriza() {
        return id_pessoa_autoriza;
    }

    public void setId_pessoa_autoriza(Pessoa id_pessoa_autoriza) {
        this.id_pessoa_autoriza = id_pessoa_autoriza;
    }

    public String getNomePessoaSolicita() {
        return this.id_pessoa_solicita.getNome();
    }

}
