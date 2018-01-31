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
import vo.Categoria;

/**
 *
 * @author Eduardo
 */
public class CategoriaDAO extends GenericoDAO<Categoria> {

    public List<Categoria> buscarPorDescricao(Categoria categoria) {
        EntityManager em = getEM();
        List<Categoria> list;
        Query query;

        try {
            query = em.createNamedQuery("Categoria.buscarPorDescricao");
            query.setParameter("descricao", "%" + categoria.getDescricao().toUpperCase() + "%");
            list = query.getResultList();
        } catch (Exception ex) {
            list = new ArrayList();
        } finally {
            em.close();
        }
        return list;
    }

    public boolean compararPorDescricao(Categoria categoria) {

        EntityManager em = getEM();
        Categoria cat = null;
        Query query;
        try {
            query = em.createNamedQuery("Categoria.buscarPorDescricao");
            query.setParameter("descricao", categoria.getDescricao().toUpperCase());
            cat = (Categoria) query.getSingleResult();

            System.out.println(cat.getDescricao());

        } catch (Exception ex) {
            cat = null;
        } finally {
            em.close();
        }
        if (cat != null) {
            return false;
        } else {
            return true;
        }
    }
    
    public List<Categoria> buscarTodos() {
        EntityManager em = getEM();
        List<Categoria> list;
        Query query;

        try {
            query = em.createNamedQuery("Categoria.BuscarTodos");
            list = query.getResultList();
        } catch (Exception ex) {
            list = new ArrayList();
        } finally {
            em.close();
        }
        return list;
    }

}
