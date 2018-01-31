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
import vo.Departamento;

/**
 *
 * @author Eduardo
 */
public class DepartamentoDAO extends GenericoDAO<Departamento> {

    public List<Departamento> buscarPorNome(Departamento departamento) {
        EntityManager em = getEM();
        List<Departamento> list;
        try {
            Query query = em.createNamedQuery("Departamento.ConsultarPorNome");
            query.setParameter("nome", "%" + departamento.getNome().toUpperCase() + "%");
            list = query.getResultList();

        } catch (Exception ex) {
            list = new ArrayList();

        } finally {
            em.close();
        }
      
        return list;

    }
    
    public List<Departamento> buscarPorSigla(Departamento departamento){
        EntityManager em = getEM();
        List<Departamento> list;
        Query query;
        
        try{
            query = em.createNamedQuery("Departamento.ConsultarPorSigla");
            query.setParameter("sigla", "%"+departamento.getSigla().toUpperCase()+"%");
            list = query.getResultList();
            
        }catch(Exception ex){
            list = new ArrayList();
        }finally{
            em.close();
        }
        return list;
    }
    
      public List<Departamento> buscarTodos(){
        EntityManager em = getEM();
        List<Departamento> list;
        Query query;
        
        try{
            query = em.createNamedQuery("Departamento.ConsultarTodos");
            list = query.getResultList();
            
        }catch(Exception ex){
            list = new ArrayList();
        }finally{
            em.close();
        }
        return list;
    }
}
