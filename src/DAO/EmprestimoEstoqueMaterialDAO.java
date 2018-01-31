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
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.Material;

/**
 *
 * @author Eduardo
 */
public class EmprestimoEstoqueMaterialDAO extends GenericoDAO<EmprestimoEstoqueMaterial> {

    public List<EmprestimoEstoqueMaterial> consultarPorNaoDevolvido(Emprestimo emp) {
        EntityManager em = getEM();
        List<EmprestimoEstoqueMaterial> list;
        Query query;

        try {
            query = em.createNamedQuery("EmprestimoEstoqueMaterial.consultarPorNaoDevolvido");
            query.setParameter("id_emprestimo", emp.getId());
            list = query.getResultList();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            list = new ArrayList();
        } finally {
            em.close();
        }
        return list;
    }

    public List<EmprestimoEstoqueMaterial> consultarTodosIdEmprestimo(Emprestimo emp) {
        EntityManager em = getEM();
        List<EmprestimoEstoqueMaterial> list;
        Query query;

        try {
            query = em.createNamedQuery("EmprestimoEstoqueMaterial.consultarTodosIdEmprestimo");
            query.setParameter("id_emprestimo", emp.getId());
            list = query.getResultList();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            list = new ArrayList();
        } finally {
            em.close();
        }
        return list;
    }

    public List<EmprestimoEstoqueMaterial> consultarTodos() {
        EntityManager em = getEM();
        List<EmprestimoEstoqueMaterial> list;
        Query query;

        try {
            query = em.createNamedQuery("EmprestimoEstoqueMaterial.consultarTodos");
            list = query.getResultList();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            list = new ArrayList();
        } finally {
            em.close();
        }
        return list;
    }

    public List<EmprestimoEstoqueMaterial> consultaPorMateriaisEmprestimo(Emprestimo emp) {
        EntityManager em = getEM();
        List<EmprestimoEstoqueMaterial> list;
        Query query;

        try {

            query = em.createNamedQuery("EmprestimoEstoqueMaterial.consultarMaterial");
            query.setParameter("id_emprestimo", emp.getId());
            list = query.getResultList();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            list = new ArrayList();
        } finally {
            em.close();
        }
        return list;
    }


}
