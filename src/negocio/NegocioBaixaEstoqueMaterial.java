/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.BaixaEstoqueMaterialDAO;
import vo.BaixaEstoqueMaterial;

/**
 *
 * @author Eduardo
 */
public class NegocioBaixaEstoqueMaterial {
    private BaixaEstoqueMaterialDAO bemDAO;
    
    public NegocioBaixaEstoqueMaterial(){
        bemDAO = new BaixaEstoqueMaterialDAO();
    }
    
    public BaixaEstoqueMaterial salvar(BaixaEstoqueMaterial bem) throws Exception{
        String erro = validar(bem);
        if(erro.equals("")){
            return bemDAO.salvar(BaixaEstoqueMaterial.class, bem);
        }else{
            throw new Exception(erro);
        }
    }
    
    public void remover(BaixaEstoqueMaterial bem) throws Exception{
        bemDAO.remover(BaixaEstoqueMaterial.class, bem);
    }
    
    public BaixaEstoqueMaterial consultarPorId(BaixaEstoqueMaterial bem){
        return bemDAO.consutarPorId(BaixaEstoqueMaterial.class, bem);
    }   
    
    public String validar(BaixaEstoqueMaterial bem){
        String erro = "";
        
        if(bem.getId_baixa() == null) erro +="Erro: falto adicionar a baixa";
        if(bem.getId_estoquematerial() == null)erro+="\nErro: falto adicionar o estoque do material";
        
        return erro;
    }
}
