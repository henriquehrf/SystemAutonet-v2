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
import vo.Material;

/**
 *
 * @author Eduardo
 */
public class MaterialDAO extends GenericoDAO<Material> {

    public List<Material> buscarPorDescricao(Material material) {

        EntityManager em = getEM();
        List<Material> list;
        try {
            Query query = em.createNamedQuery("Material.consultarPorDescricao");
            query.setParameter("descricao", "%" + material.getDescricao().toUpperCase() + "%");
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();

        } finally {
            em.close();
        }

        return list;
    }

    public List<Material> buscarPorQuantidade(Material material) {

        EntityManager em = getEM();
        List<Material> list;
        try {
            Query query = em.createNamedQuery("Material.consultarPorQuantidade");
            query.setParameter("quantidade", material.getQuantidade());
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();

        } finally {
            em.close();
        }

        return list;
    }
    
     public List<Material> buscarPorCategoria(Categoria categoria) {

        EntityManager em = getEM();
        List<Material> list;
        try {
            Query query = em.createNamedQuery("Material.consultarPorCategoria");
            query.setParameter("idcategoria", categoria.getId());
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();

        } finally {
            em.close();
        }

        return list;
    }
     
      public List<Material> buscarTodos() {

        EntityManager em = getEM();
        List<Material> list;
        try {
            Query query = em.createNamedQuery("Material.consultarTodos");
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();

        } finally {
            em.close();
        }

        return list;
    }

}
