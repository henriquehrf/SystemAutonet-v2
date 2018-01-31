/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.EntradaDAO;
import vo.Entrada;

/**
 *
 * @author Eduardo
 */
public class NegocioEntrada {
    private EntradaDAO entradaDAO;
    
    public NegocioEntrada(){
        entradaDAO = new EntradaDAO();
    }
    
    public Entrada salvar(Entrada entrada) throws Exception{
        String erro = validar(entrada);
        if(erro.equals("")){
            return entradaDAO.salvar(Entrada.class, entrada);
        }else{
            throw new Exception(erro);
        }
    }
    
    public void remover(Entrada entrada) throws Exception{
        entradaDAO.remover(Entrada.class, entrada);
    }
    
    public Entrada consultarPorId(Entrada entrada){
        return entradaDAO.consutarPorId(Entrada.class, entrada);
    }
        
    private String validar(Entrada entrada){
        String erro = "";
        
        if(entrada.getId_fornecedor() ==null) erro+="Falto adicionar um fornecedor";
        
        return erro;
    }
}
