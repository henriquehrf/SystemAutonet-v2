/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classesAuxiliares;

import java.text.SimpleDateFormat;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.Pessoa;

/**
 *
 * @author Eduardo
 */
public class TblPessoaEmprestimo {

    private Pessoa pessoa;
    private Emprestimo emprestimo;


    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

  
    public String getEmprestimoDt() {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        return dt.format(emprestimo.getDt_emprestimo());
      
    }

    public String getFinalidade() {
        return emprestimo.getFinalidade();
    }

    public String getNome() {
        return pessoa.getNome();
    }
    
 
}
