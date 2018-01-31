/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classesAuxiliares;

import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;

/**
 *
 * @author Eduardo
 */
public class TblEmprestimoEstoque {
    private Emprestimo emp;
    private EmprestimoEstoqueMaterial empEstoque;

    public Emprestimo getEmp() {
        return emp;
    }

    public void setEmp(Emprestimo emp) {
        this.emp = emp;
    }

    public EmprestimoEstoqueMaterial getEmpEstoque() {
        return empEstoque;
    }

    public void setEmpEstoque(EmprestimoEstoqueMaterial empEstoque) {
        this.empEstoque = empEstoque;
    }
    
    public String getMaterial(){
        return empEstoque.getId_material().getDescricao();
    }
    
    public String getCategoria(){
        return empEstoque.getId_material().getId_categoria().getDescricao();
    }
    
    public Number getQuantidade(){
        return empEstoque.getQtd_emprestada();
    }
}
