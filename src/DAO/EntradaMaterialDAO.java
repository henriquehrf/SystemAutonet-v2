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
import vo.Entrada;
import vo.EntradaMaterial;

/**
 *
 * @author Eduardo
 */
public class EntradaMaterialDAO extends GenericoDAO<EntradaMaterial>
{
    
     public List<EntradaMaterial> buscarPorEntrada(Entrada Entrada) {
        EntityManager em = getEM();
        Query query;
        List<EntradaMaterial> entradaMaterial;
        try {
            query = em.createNamedQuery("EntradaMaterial.BuscarPorEntrada");
            query.setParameter("idEntrada", Entrada.getId());
            entradaMaterial = query.getResultList();
        } catch (Exception ex) {
            entradaMaterial = new ArrayList();
        } finally {
            em.close();
        }
        return entradaMaterial;

    }
    
}
