/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.BaixaDAO;
import vo.Baixa;

/**
 *
 * @author Eduardo
 */
public class NegocioBaixa {
    private BaixaDAO baixaDAO;
    
    public NegocioBaixa(){
        baixaDAO = new BaixaDAO();
    }
    
    
    public Baixa salvar(Baixa baixa) throws Exception{
        String erro = validar(baixa);
        if(erro.equals("")) {
            return baixaDAO.salvar(Baixa.class, baixa);
        }else{
            throw new Exception(erro);
        }
    }
    
    public void remover(Baixa baixa) throws Exception{
        baixaDAO.remover(Baixa.class, baixa);
    }
    
    public Baixa consultarPorId(Baixa baixa){
        return baixaDAO.consutarPorId(Baixa.class, baixa);
    }
    
    private String validar(Baixa baixa){
        String erro  = "";
        
        if(baixa.getId_tipo_saida() == null) erro+="Favor adicionar o tipo da saida";
        
        return erro;
    }
}
