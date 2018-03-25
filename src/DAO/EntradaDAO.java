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

/**
 *
 * @author Henrique
 */
public class EntradaDAO extends GenericoDAO<Entrada> {

    public List<Entrada> buscarTodasEntrada() {
        EntityManager em = getEM();
        Query query;
        List<Entrada> lista;

        try {
            query = em.createNamedQuery("Entrada.BuscarTodos");
            lista = query.getResultList();

        } catch (Exception ex) {
            lista = new ArrayList();
        } finally {
            em.close();
        }

        return lista;
    }

}
