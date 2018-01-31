/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classesAuxiliares;

import vo.Pessoa;

/**
 *
 * @author Eduardo
 */
public class ClasseDoSistemaEstatico {
    
    private static Pessoa pessoa;

    public static Pessoa getPessoa() {
        return pessoa;
    }

    public static void setPessoa(Pessoa pessoa) {
        ClasseDoSistemaEstatico.pessoa = pessoa;
    }
    
    
    
}
