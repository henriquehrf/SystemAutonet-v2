/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import vo.Fornecedor;

/**
 *
 * @author Eduardo
 */
public class FornecedorDAO extends GenericoDAO<Fornecedor> {

    public List<Fornecedor> buscarPorNomeFantasia(Fornecedor fornecedor) {
        EntityManager em = getEM();
        List<Fornecedor> list;
        try {
            Query query = em.createNamedQuery("Fornecedor.consultarPorNomeFantasia");
            query.setParameter("nomeFantasia", "%" + fornecedor.getNome_fantasia().toUpperCase() + "%");
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();

        } finally {
            em.close();
        }

        return list;
    }

    public List<Fornecedor> buscarPorRazaoSical(Fornecedor fornecedor) {
        EntityManager em = getEM();
        List<Fornecedor> list;
        try {
            Query query = em.createNamedQuery("Fornecedor.consultarPorRazaoSical");
            query.setParameter("razaoSocial", "%" + fornecedor.getRazao_social().toUpperCase() + "%");
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();

        } finally {
            em.close();
        }

        return list;
    }

    public List<Fornecedor> buscarPorCNPJ(Fornecedor fornecedor) {
        EntityManager em = getEM();
       List<Fornecedor> forne;
        try {
            Query query = em.createNamedQuery("Fornecedor.consultarPorCNPJ");
            query.setParameter("cnpj", "%" + fornecedor.getCnpj().toUpperCase() + "%");
           forne = query.getResultList();

        } catch (Exception ex) {
           forne= new ArrayList();

        } finally {
            em.close();
        }

        return forne;
    }

    public List<Fornecedor> buscarPorPessoaResponsavel(Fornecedor fornecedor) {
        EntityManager em = getEM();
        List<Fornecedor> list;
        try {
            Query query = em.createNamedQuery("Fornecedor.consultarPorPessoaResponsavel");
            query.setParameter("nomePessoaResponsavel", "%" + fornecedor.getPessoa_responsavel().toUpperCase() + "%");
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();

        } finally {
            em.close();
        }

        return list;
    }

        public List<Fornecedor> buscarTodos() {
        EntityManager em = getEM();
        List<Fornecedor> list;
        try {
            Query query = em.createNamedQuery("Fornecedor.consultarTodos");
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();

        } finally {
            em.close();
        }

        return list;
    }
}
