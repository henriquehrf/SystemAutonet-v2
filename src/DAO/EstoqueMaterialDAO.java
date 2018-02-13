/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import vo.EstoqueMaterial;
import vo.Local;
import vo.Material;

/**
 *
 * @author Eduardo
 */
public class EstoqueMaterialDAO extends GenericoDAO<EstoqueMaterial> {

    public List<EstoqueMaterial> buscarPorIdMaterial(Material material) {
        EntityManager em = getEM();
        Query query;
        List<EstoqueMaterial> lista;

        try {
            query = em.createNamedQuery("EstoqueMaterial.BuscarPorIdMaterial");
            query.setParameter("idMaterial", material.getId());
            lista = query.getResultList();

        } catch (Exception ex) {
            lista = new ArrayList();
        } finally {
            em.close();
        }

        return lista;
    }

    public List<EstoqueMaterial> buscarTodosEstoqueMaterial() {
        EntityManager em = getEM();
        Query query;
        List<EstoqueMaterial> lista;

        try {
            query = em.createNamedQuery("EstoqueMaterial.BuscarTodosEstoqueMaterial");
            lista = query.getResultList();

        } catch (Exception ex) {
            lista = new ArrayList();
        } finally {
            em.close();
        }

        return lista;
    }

    public Number QtdDisponivelDoMaterial(Material material) throws Exception {
        EntityManager em = getEM();
        Query query;
        Number qtd;

        try {
            query = em.createNamedQuery("EstoqueMaterial.QtdDisponivelDoMaterial");
            query.setParameter("idMaterial", material.getId());
            qtd = (Number) query.getSingleResult();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());

        } finally {
            em.close();
        }
        return qtd;
    }
    
     public Number QtdDisponivelDoMaterialPorEstoque(Material material,Local local) throws Exception {
        EntityManager em = getEM();
        Query query;
        Number qtd;

        try {
            query = em.createNamedQuery("EstoqueMaterial.QtdDisponivelDoMaterialPorEstoque");
            query.setParameter("idMaterial", material.getId());
            query.setParameter("idLocal", local.getId());
            qtd = (Number) query.getSingleResult();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());

        } finally {
            em.close();
        }
        return qtd;
    }

    public EstoqueMaterial buscarPorIdMaterialIdLocal(EstoqueMaterial estoqueMaterial) throws Exception {
        EntityManager em = getEM();
        Query query;
        EstoqueMaterial lista;

        try {

            query = em.createNamedQuery("EstoqueMaterial.BuscarPorIdMaterialIdlocal");
            query.setParameter("idMaterial", estoqueMaterial.getId_material().getId());
            query.setParameter("idLocal", estoqueMaterial.getId_departamento().getId());
            lista = (EstoqueMaterial) query.getSingleResult();

        } catch (NoResultException ex) {
            lista = salvar(EstoqueMaterial.class, estoqueMaterial);
        } finally {
            em.close();
        }

        return lista;
    }

}
