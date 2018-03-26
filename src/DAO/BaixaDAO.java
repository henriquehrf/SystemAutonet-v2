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
import vo.Baixa;

/**
 *
 * @author Henrique
 */
public class BaixaDAO extends GenericoDAO<Baixa> {
    
     public List<Baixa> buscarTodasBaixa() {
        EntityManager em = getEM();
        Query query;
        List<Baixa> lista;

        try {
            query = em.createNamedQuery("Baixa.BuscarTodasBaixas");
            lista = query.getResultList();

        } catch (Exception ex) {
            lista = new ArrayList();
        } finally {
            em.close();
        }

        return lista;
    }
    
}
