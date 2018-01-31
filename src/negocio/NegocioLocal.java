/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.LocalDAO;
import classesAuxiliares.NegociosEstaticos;
import java.util.List;
import java.util.Properties;
import utilitarios.LerMessage;
import vo.Local;

/**
 *
 * @author Eduardo
 */
public class NegocioLocal {

    private LocalDAO localDAO;

    public NegocioLocal() {
        localDAO = new LocalDAO();
    }

    public Local salvar(Local local, Local cadastrar) throws Exception {
        String erro = validar(local, cadastrar);

        if (erro.equals("")) {
            return localDAO.salvar(Local.class, local);
        } else {
            throw new Exception(erro);
        }
    }

    public void remover(Local local) throws Exception {
        try {
            localDAO.remover(Local.class, local);

        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            throw new Exception(ler.getMessage("msg.remover"));
        }
    }

    public Local consultarPorId(Local local) {
        return localDAO.consutarPorId(Local.class, local);
    }

    public String validar(Local local, Local cadastrar) {
        String erro = "";

        if (local.getId_departamento() == null) {
            erro += "Erro: não foi encontrado o departamento";
        }
        if (cadastrar == null) {
            List<Local> loc = NegociosEstaticos.getNegocioLocal().buscarTodos();

            for (int i = 0; i < loc.size(); i++) {
                if (loc.get(i).getDescricao().equals(local.getDescricao())
                        && loc.get(i).getNumero() == local.getNumero()
                        && loc.get(i).getSigla().equals(local.getSigla())) {
                    erro += "Este cadastro com estas informações já existe";
                }
            }

        } else {
            List<Local> loc = NegociosEstaticos.getNegocioLocal().buscarTodos();
            for (int i = 0; i < loc.size(); i++) {
                if (loc.get(i).getDescricao().equals(local.getDescricao())
                        && loc.get(i).getNumero() == local.getNumero()
                        && loc.get(i).getSigla().equals(local.getSigla())) {
                    if (loc.get(i).getId() != local.getId()) {
                        erro += "Este cadastro com estas informações já existe";
                    }
                }
            }
        }
        return erro;
    }

    public List<Local> buscarPorDescricao(Local local) {
        return localDAO.buscarPorDescricao(local);
    }

    public List<Local> buscarPorPessoaResponsavel(Local local) {
        return localDAO.buscarPorPessoaResponsavel(local);
    }

    public List<Local> buscarPorNumero(Local local) {
        return localDAO.buscarPorNumero(local);
    }

    public List<Local> buscarTodos() {
        return localDAO.buscarTodos();
    }
//
//    public List<Local> buscarPorBloco(Local local) {
//        return localDAO.buscarPorBloco(local);
//    }

    public List<Local> buscarPorDepartamento(Local local) {
        return localDAO.buscarPorDepartamento(local);
    }

}
