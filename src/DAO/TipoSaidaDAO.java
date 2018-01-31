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
import vo.TipoSaida;

/**
 *
 * @author Eduardo
 */
public class TipoSaidaDAO extends GenericoDAO<TipoSaida> {
    
    
    public List<TipoSaida> buscarPorDescricao(TipoSaida ts){
        EntityManager em = getEM();
        List<TipoSaida> list;
        Query query;
        
        try{
            
            query = em.createNamedQuery("TipoSaida.BuscarPorDescricao");
            query.setParameter("descricao", "%"+ts.getDescricao().toUpperCase()+"%");
            list = query.getResultList();
            
        }catch(Exception ex){
            list = new ArrayList();
        }finally{
            em.close();
        }
        return list;
    }
    
        public List<TipoSaida> buscarTodos(){
        EntityManager em = getEM();
        List<TipoSaida> list;
        Query query;
        
        try{
            
            query = em.createNamedQuery("TipoSaida.BuscarTodos");
            list = query.getResultList();
            
        }catch(Exception ex){
            list = new ArrayList();
        }finally{
            em.close();
        }
        return list;
    }
}
