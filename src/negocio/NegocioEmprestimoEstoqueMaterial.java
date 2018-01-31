/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.EmprestimoEstoqueMaterialDAO;
import java.util.List;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.Material;

/**
 *
 * @author Eduardo
 */
public class NegocioEmprestimoEstoqueMaterial {

    private EmprestimoEstoqueMaterialDAO eemDAO;

    public NegocioEmprestimoEstoqueMaterial() {
        eemDAO = new EmprestimoEstoqueMaterialDAO();
    }

    public EmprestimoEstoqueMaterial salvar(EmprestimoEstoqueMaterial eem) throws Exception {
        String erro = validar(eem);

        if (erro.equals("")) {
            return eemDAO.salvar(EmprestimoEstoqueMaterial.class, eem);
        } else {
            throw new Exception(erro);
        }
    }

    public void remover(EmprestimoEstoqueMaterial eem) throws Exception {
        eemDAO.remover(EmprestimoEstoqueMaterial.class, eem);
    }

    public EmprestimoEstoqueMaterial consultarPorId(EmprestimoEstoqueMaterial eem) {
        return eemDAO.consutarPorId(EmprestimoEstoqueMaterial.class, eem);
    }

    public List<EmprestimoEstoqueMaterial> consultarPorNaoDevolvido(Emprestimo emp) {
        return eemDAO.consultarPorNaoDevolvido(emp);
    }

    public List<EmprestimoEstoqueMaterial> consultarTodos() {
        return eemDAO.consultarTodos();
    }

    public List<EmprestimoEstoqueMaterial> consultarTodosIdEmprestimo(Emprestimo emp) {
        return eemDAO.consultarTodosIdEmprestimo(emp);
    }

    public String validar(EmprestimoEstoqueMaterial eem) {
        String erro = "";
        if (eem.getId_emprestimo() == null) {
            erro += "Erro: falto referencia o emprestimo";
        }
//        if(eem.getId_estoquematerial() == null)erro+="\nErro: Falto referenciar um estoque";

        return erro;
    }
}
