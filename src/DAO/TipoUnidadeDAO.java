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
import vo.TipoUnidade;

/**
 *
 * @author Eduardo
 */
public class TipoUnidadeDAO extends GenericoDAO<TipoUnidade> {

    public List<TipoUnidade> buscarPorDescricao(TipoUnidade tu) {

        EntityManager em = getEM();
        List<TipoUnidade> list;
        Query query;

        try {
            query = em.createNamedQuery("TipoUnidade.descricao");
            query.setParameter("descricao", "%" + tu.getDescricao().toUpperCase() + "%");
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();
        } finally {
            em.close();
        }
        return list;
    }

    public List<TipoUnidade> buscarTodos() {

        EntityManager em = getEM();
        List<TipoUnidade> list;
        Query query;

        try {

            query = em.createNamedQuery("TipoUnidade.BuscarTodos");
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();
        } finally {
            em.close();
        }
        return list;
    }

    public List<TipoUnidade> buscarPorSigla(TipoUnidade tu) {

        EntityManager em = getEM();
        List<TipoUnidade> list;
        Query query;

        try {
            query = em.createNamedQuery("TipoUnidade.Sigla");
            query.setParameter("sigla", "%" + tu.getSigla().toUpperCase() + "%");
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();
        } finally {
            em.close();
        }
        return list;
    }

}
