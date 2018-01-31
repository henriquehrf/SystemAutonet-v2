/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.TipoUnidadeDAO;
import classesAuxiliares.NegociosEstaticos;
import java.util.List;
import java.util.Objects;
import utilitarios.LerMessage;
import vo.TipoUnidade;

/**
 *
 * @author Eduardo
 */
public class NegocioTipoUnidade {

    private TipoUnidadeDAO tuDAO;

    public NegocioTipoUnidade() {
        tuDAO = new TipoUnidadeDAO();
    }

    public TipoUnidade salvar(TipoUnidade tu,TipoUnidade alterar) throws Exception {
        String erro = "";
        erro = validar(tu,alterar);
        if ("".equals(erro)) {
            return tuDAO.salvar(TipoUnidade.class, tu);
        }else{
            throw  new Exception(erro);
        }
    }
    public String validar(TipoUnidade tu,TipoUnidade alterar){
       List<TipoUnidade> lista = NegociosEstaticos.getNegocioTipoUnidade().buscarTodos();
        String erro = "";
        if (alterar == null) {
            if (lista.size() > 0) {
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getDescricao().equalsIgnoreCase(tu.getDescricao())) {
                        erro += "Este tipo de unidade de medida já existe";
                    }
                    if (lista.get(i).getSigla().equalsIgnoreCase(tu.getSigla())) {
                        erro += "Este tipo de sigla para a unidade de medida já existe";
                    }
                    
                }
            }
        } else if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getDescricao().equalsIgnoreCase(tu.getDescricao()) && !Objects.equals(lista.get(i).getId(), tu.getId())) {
                     erro += "Este tipo de unidade de medida já existe";
                }
                 if (lista.get(i).getSigla().equalsIgnoreCase(tu.getSigla()) && !Objects.equals(lista.get(i).getId(), tu.getId())) {
                     erro += "Este tipo de sigla para a unidade de medida já existe";
                }
            }
        }
        return erro;
        
    }

    public void remover(TipoUnidade tu) throws Exception {

        try {
            tuDAO.remover(TipoUnidade.class, tu);

        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            throw new Exception(ler.getMessage("msg.remover"));
        }
    }

    public TipoUnidade consultarPorId(TipoUnidade tu) {
        return tuDAO.consutarPorId(TipoUnidade.class, tu);
    }

    public List<TipoUnidade> buscarPorDescricao(TipoUnidade tu) {
        return tuDAO.buscarPorDescricao(tu);
    }

    public List<TipoUnidade> buscarTodos() {
        return tuDAO.buscarTodos();
    }

    public List<TipoUnidade> buscarPorSigla(TipoUnidade tu) {
        return tuDAO.buscarPorSigla(tu);
    }

}
