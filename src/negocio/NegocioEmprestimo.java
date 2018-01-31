/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.EmprestimoDAO;
import java.util.List;
import java.util.Properties;
import utilitarios.LerMessage;
import vo.Emprestimo;
import vo.Pessoa;

/**
 *
 * @author Eduardo
 */
public class NegocioEmprestimo {

    private EmprestimoDAO empDAO;

    public NegocioEmprestimo() {
        empDAO = new EmprestimoDAO();
    }

    public Emprestimo salvar(Emprestimo emp) throws Exception {
        String erro = validar(emp);

        if (erro.equals("")) {
            return empDAO.salvar(Emprestimo.class, emp);
        } else {
            throw new Exception(erro);
        }

    }

    public void remover(Emprestimo emp) throws Exception {
        try {
            empDAO.remover(Emprestimo.class, emp);

        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            throw new Exception(ler.getMessage("msg.remover"));
        }
    }

    public Emprestimo consultarPorId(Emprestimo emp) {
        return empDAO.consutarPorId(Emprestimo.class, emp);
    }

    public List<Emprestimo> buscarPorIdPessoa(Pessoa pessoa) {
        return empDAO.buscarPorIdPessoa(pessoa);
    }

    public List<Emprestimo> buscarPorIdPessoaStatusRetirado(Pessoa pessoa) {
        return empDAO.buscarPorIdPessoaStatusRetirado(pessoa);
    }

    public List<Emprestimo> buscarPorIdPessoaStatusESPERANDO_ANALISE(Pessoa pessoa) {
        return empDAO.buscarPorIdPessoaStatusESPERANDO_ANALISE(pessoa);
    }

    public List<Emprestimo> buscarPorStatusEmprestimoTodos(Emprestimo emprestimo) {
        return empDAO.buscarPorStatusEmprestimoTodos(emprestimo);
    }

    public List<Emprestimo> buscarPorStatusEmprestimoPessoa(Emprestimo emprestimo) {
        return empDAO.buscarPorStatusEmprestimoPessoa(emprestimo);
    }

    public List<Emprestimo> buscarPorTodos() {
        return empDAO.buscarPorTodos();
    }

    private String validar(Emprestimo emp) {
        String erro = "";

        if (emp.getId_pessoa_solicita() == null) {
            erro += "A pessoa que solicita não pode ser nulo";
        }

        return erro;
    }
}
