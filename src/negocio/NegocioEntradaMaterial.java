/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.EntradaMaterialDAO;
import vo.EntradaMaterial;

/**
 *
 * @author Eduardo
 */
public class NegocioEntradaMaterial {
    private EntradaMaterialDAO emDAO;
    
    public NegocioEntradaMaterial(){
        emDAO = new EntradaMaterialDAO();
    }
    
    public EntradaMaterial salvar(EntradaMaterial em) throws Exception{
        String erro = validar(em);
        if(erro.equals("")){
            return emDAO.salvar(EntradaMaterial.class, em);
            
        }else{
            throw new Exception(erro);
        }
    }
    
    public void remover(EntradaMaterial em) throws Exception{
        emDAO.remover(EntradaMaterial.class, em);
    }
    
    public EntradaMaterial consultarPorId(EntradaMaterial em){
        return emDAO.consutarPorId(EntradaMaterial.class, em);
    }
    
    public String validar(EntradaMaterial em){
        String erro = "";
        
        if(em.getId_entrada() == null) erro +="Erro: falto especificar a entrada do material";
        if(em.getId_material() == null)erro+="\nErro: falto especificar o material";
        return erro;
    }
}
