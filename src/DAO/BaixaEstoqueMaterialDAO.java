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
import vo.BaixaEstoqueMaterial;

/**
 *
 * @author Henrique
 */
public class BaixaEstoqueMaterialDAO extends GenericoDAO<BaixaEstoqueMaterial> {
    
     public List<BaixaEstoqueMaterial> buscarTodasEntrada() {
        EntityManager em = getEM();
        Query query;
        List<BaixaEstoqueMaterial> lista;

        try {
            query = em.createNamedQuery("BaixaEstoqueMaterial.BuscarTodasBaixasDeEstoque");
            lista = query.getResultList();

        } catch (Exception ex) {
            lista = new ArrayList();
        } finally {
            em.close();
        }

        return lista;
    }
    
}
