/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.DepartamentoDAO;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import utilitarios.LerMessage;
import vo.Departamento;

/**
 *
 * @author Eduardo
 */
public class NegocioDepartamento {

    private final DepartamentoDAO departamentoDAO;

    public NegocioDepartamento() {
        departamentoDAO = new DepartamentoDAO();
    }

    public Departamento salvar(Departamento dp, boolean cadastrar) throws Exception {
        String erro = validar(dp, cadastrar);
        if (erro.equals("")) {
            return departamentoDAO.salvar(Departamento.class, dp);
        } else {
            throw new Exception(erro);

        }
    }

    public void remover(Departamento dp) throws Exception {

        try {
            departamentoDAO.remover(Departamento.class, dp);

        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            throw new Exception(ler.getMessage("msg.remover"));
        }
    }

    public Departamento consultarPorId(Departamento dp) {
        return departamentoDAO.consutarPorId(Departamento.class, dp);
    }

    public List<Departamento> buscarPorNome(Departamento dp) {
        return departamentoDAO.buscarPorNome(dp);
    }

    public List<Departamento> buscarPorSigla(Departamento dp) {
        return departamentoDAO.buscarPorSigla(dp);
    }

    public List<Departamento> buscarTodos() {
        return departamentoDAO.buscarTodos();
    }
//arrumar aqui

    private String validar(Departamento dp, boolean cadastrar) {
        String erro = "";
        List<Departamento> lista = buscarTodos();
        if (cadastrar) {

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getNome().equals(dp.getNome())) {
                    erro += "Nome ja existente";
                }
                if (lista.get(i).getSigla().equals(dp.getSigla())) {
                    erro += "\nSigla ja existente";
                    break;
                }
            }
            return erro;
        } else {

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getNome().equals(dp.getNome()) && lista.get(i).getId() != dp.getId()) {
                  
                        erro += "Nome ja existente";
                    
                }
                if (lista.get(i).getSigla().equals(dp.getSigla())&& lista.get(i).getId() != dp.getId()) {
                  
                        erro += "\nSigla ja existente";
                    
                    break;
                }

            }

        }
        return erro;

    }
}
