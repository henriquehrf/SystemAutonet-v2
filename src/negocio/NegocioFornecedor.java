/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.FornecedorDAO;
import java.util.List;
import java.util.Properties;
import utilitarios.LerMessage;
import vo.Fornecedor;

/**
 *
 * @author Eduardo
 */
public class NegocioFornecedor {

    private final FornecedorDAO fornecedorDAO;

    public NegocioFornecedor() {
        fornecedorDAO = new FornecedorDAO();
    }

    public Fornecedor salvar(Fornecedor fornecedor) throws Exception {
        //Faltando as validações
        String erro = validar(fornecedor);
        if (erro.equals("")) {
            return fornecedorDAO.salvar(Fornecedor.class, fornecedor);
        } else {
            throw new Exception(erro);
        }

    }

    public void remover(Fornecedor fornecedor) throws Exception {
        try {

            fornecedorDAO.remover(Fornecedor.class, fornecedor);

        } catch (Exception ex) {
           LerMessage ler = new LerMessage();
            throw new Exception(ler.getMessage("msg.remover"));
        }
    }

    public Fornecedor consultarPorId(Fornecedor fornecedor) {
        return fornecedorDAO.consutarPorId(Fornecedor.class, fornecedor);
    }

    public List<Fornecedor> buscarPorNomeFantasia(Fornecedor fornecedor) {
        return fornecedorDAO.buscarPorNomeFantasia(fornecedor);
    }

    public List<Fornecedor> buscarPorRazaoSocial(Fornecedor fornecedor) {
        return fornecedorDAO.buscarPorRazaoSical(fornecedor);
    }

    public List<Fornecedor> buscarPorCnpj(Fornecedor fornecedor) {
        return fornecedorDAO.buscarPorCNPJ(fornecedor);
    }

    public List<Fornecedor> buscarPorPessoaResponsavel(Fornecedor fornecedor) {
        return fornecedorDAO.buscarPorPessoaResponsavel(fornecedor);
    }

    public List<Fornecedor> buscarTodos() {
        return fornecedorDAO.buscarTodos();
    }

    public String validar(Fornecedor fornecedor) {
        String erro = "";

        if (fornecedor.getId() == null || fornecedor.getId() == 0) {
            if (!buscarPorCnpj(fornecedor).isEmpty()) {
                erro += "Erro: CNPJ já cadastrado";
            }
        }else{
            List<Fornecedor> lista =buscarPorCnpj(fornecedor); 
            if(lista.size()>0){
                for(int i=0;i<lista.size();i++){
                    if(lista.get(i).getCnpj().equalsIgnoreCase(fornecedor.getCnpj())&&
                            lista.get(i).getId()!=fornecedor.getId()){
                        erro += "Erro: CNPJ já cadastrado";
                    }
                }
            }
        }
        return erro;
    }

}
