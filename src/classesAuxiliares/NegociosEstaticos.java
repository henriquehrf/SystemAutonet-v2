/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classesAuxiliares;

import negocio.NegocioBaixa;
import negocio.NegocioBaixaEstoqueMaterial;
import negocio.NegocioCategoria;
import negocio.NegocioDepartamento;
import negocio.NegocioEmprestimo;
import negocio.NegocioEmprestimoEstoqueMaterial;
import negocio.NegocioEntrada;
import negocio.NegocioEntradaMaterial;
import negocio.NegocioEstoqueMaterial;
import negocio.NegocioFornecedor;
import negocio.NegocioLocal;
import negocio.NegocioMaterial;
import negocio.NegocioPessoa;
import negocio.NegocioTipoSaida;
import negocio.NegocioTipoUnidade;

/**
 *
 * @author Eduardo
 */
public class NegociosEstaticos {

    private static NegocioBaixa negocioBaixa;
    private static NegocioBaixaEstoqueMaterial negocioBaixaEstoqueMaterial;
    private static NegocioCategoria negocioCategoria;
    private static NegocioDepartamento negocioDepartamento;
    private static NegocioEmprestimo negocioEmprestimo;
    private static NegocioEmprestimoEstoqueMaterial negocioEmprestiomEstoqueMaterial;
    private static NegocioEntrada negocioEntrada;
    private static NegocioEntradaMaterial negocioEntradaMaterial;
    private static NegocioEstoqueMaterial negocioEstoqueMateria;
    private static NegocioFornecedor negocioFornecedor;
    private static NegocioLocal negocioLocal;
    private static NegocioMaterial negocioMaterial;
    private static NegocioPessoa negocioPessoa;
    private static NegocioTipoSaida negocioTipoSaida;
    private static NegocioTipoUnidade negocioTipoUnidade;

    public static void iniciar(){
       negocioBaixa = new NegocioBaixa();
       negocioBaixaEstoqueMaterial = new NegocioBaixaEstoqueMaterial();
       negocioCategoria = new NegocioCategoria();
       negocioDepartamento = new NegocioDepartamento();
       negocioEmprestimo = new NegocioEmprestimo();
       negocioEmprestiomEstoqueMaterial = new NegocioEmprestimoEstoqueMaterial();
       negocioEntrada= new NegocioEntrada();
       negocioEntradaMaterial = new NegocioEntradaMaterial();
       negocioEstoqueMateria = new NegocioEstoqueMaterial();
       negocioFornecedor = new NegocioFornecedor();
       negocioLocal = new NegocioLocal();
       negocioMaterial = new NegocioMaterial();
       negocioPessoa = new NegocioPessoa();
       negocioTipoSaida = new NegocioTipoSaida();
       negocioTipoUnidade = new NegocioTipoUnidade();
    }    

    public static NegocioBaixa getNegocioBaixa() {
        return negocioBaixa;
    }

    public static NegocioBaixaEstoqueMaterial getNegocioBaixaEstoqueMaterial() {
        return negocioBaixaEstoqueMaterial;
    }

    public static NegocioCategoria getNegocioCategoria() {
        return negocioCategoria;
    }

    public static NegocioDepartamento getNegocioDepartamento() {
        return negocioDepartamento;
    }

    public static NegocioEmprestimo getNegocioEmprestimo() {
        return negocioEmprestimo;
    }

    public static NegocioEmprestimoEstoqueMaterial getNegocioEmprestiomEstoqueMaterial() {
        return negocioEmprestiomEstoqueMaterial;
    }

    public static NegocioEntrada getNegocioEntrada() {
        return negocioEntrada;
    }

    public static NegocioEntradaMaterial getNegocioEntradaMaterial() {
        return negocioEntradaMaterial;
    }

    public static NegocioEstoqueMaterial getNegocioEstoqueMateria() {
        return negocioEstoqueMateria;
    }

    public static NegocioFornecedor getNegocioFornecedor() {
        return negocioFornecedor;
    }

    public static NegocioLocal getNegocioLocal() {
        return negocioLocal;
    }

    public static NegocioMaterial getNegocioMaterial() {
        return negocioMaterial;
    }

    public static NegocioPessoa getNegocioPessoa() {
        return negocioPessoa;
    }

    public static NegocioTipoSaida getNegocioTipoSaida() {
        return negocioTipoSaida;
    }

    public static NegocioTipoUnidade getNegocioTipoUnidade() {
        return negocioTipoUnidade;
    }
    
    
    
    

}
