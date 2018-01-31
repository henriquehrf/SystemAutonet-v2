/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import vo.Emprestimo;
import vo.Pessoa;

/**
 *
 * @author Eduardo
 */
public class EmprestimoDAO extends GenericoDAO<Emprestimo> {

    public List<Emprestimo> buscarPorIdPessoa(Pessoa pessoa) {
        EntityManager em = getEM();
        Query query;
        List<Emprestimo> listaEmprestimo;
        try {
            query = em.createNamedQuery("Emprestimo.BuscarPorIdPessoa");
            query.setParameter("idPessoaSolicita", pessoa.getId());
            listaEmprestimo = query.getResultList();
        } catch (Exception ex) {
            listaEmprestimo = new ArrayList();
        } finally {
            em.close();
        }
        return listaEmprestimo;

    }

    public List<Emprestimo> buscarPorIdPessoaStatusRetirado(Pessoa pessoa) {
        EntityManager em = getEM();
        Query query;
        List<Emprestimo> listaEmprestimo;
        try {
            query = em.createNamedQuery("Emprestimo.BuscarPorIdPessoaStatusRetirado");
            query.setParameter("idPessoaSolicita", pessoa.getId());

            listaEmprestimo = query.getResultList();
        } catch (Exception ex) {
            listaEmprestimo = new ArrayList();
        } finally {
            em.close();
        }
        return listaEmprestimo;

    }

    public List<Emprestimo> buscarPorStatusEmprestimoTodos(Emprestimo emprestimo) {
        EntityManager em = getEM();
        Query query;
        List<Emprestimo> listaEmprestimo;
        try {
            query = em.createNamedQuery("Emprestimo.BuscarPorStatusTodos");
            query.setParameter("Status", emprestimo.getStatus_emprestimo());
            listaEmprestimo = query.getResultList();
        } catch (Exception ex) {
            listaEmprestimo = new ArrayList();
        } finally {
            em.close();
        }
        return listaEmprestimo;

    }

    public List<Emprestimo> buscarPorStatusEmprestimoPessoa(Emprestimo emprestimo) {
        EntityManager em = getEM();
        Query query;
        List<Emprestimo> listaEmprestimo;
        try {
            query = em.createNamedQuery("Emprestimo.BuscarPorStatusPessoa");
            query.setParameter("Status", emprestimo.getStatus_emprestimo());
            query.setParameter("idpessoa", emprestimo.getId_pessoa_solicita().getId());
            listaEmprestimo = query.getResultList();
        } catch (Exception ex) {
            listaEmprestimo = new ArrayList();
        } finally {
            em.close();
        }
        return listaEmprestimo;

    }

    public List<Emprestimo> buscarPorTodos() {
        EntityManager em = getEM();
        Query query;
        List<Emprestimo> listaEmprestimo;
        try {
            query = em.createNamedQuery("Emprestimo.BuscarTodos");
            listaEmprestimo = query.getResultList();
        } catch (Exception ex) {
            listaEmprestimo = new ArrayList();
        } finally {
            em.close();
        }
        return listaEmprestimo;

    }

    public List<Emprestimo> buscarPorIdPessoaStatusESPERANDO_ANALISE(Pessoa pessoa) {
        EntityManager em = getEM();
        Query query;
        List<Emprestimo> listaEmprestimo;
        try {
            query = em.createNamedQuery("Emprestimo.BuscarPorIdPessoaStatusESPERANDO_ANALISE");
            query.setParameter("idPessoaSolicita", pessoa.getId());

            listaEmprestimo = query.getResultList();
        } catch (Exception ex) {
            listaEmprestimo = new ArrayList();
        } finally {
            em.close();
        }
        return listaEmprestimo;

    }

}
