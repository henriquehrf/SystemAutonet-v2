/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.TipoSaidaDAO;
import classesAuxiliares.NegociosEstaticos;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import utilitarios.LerMessage;
import vo.TipoSaida;

/**
 *
 * @author Eduardo
 */
public class NegocioTipoSaida {

    private TipoSaidaDAO tsDAO;

    public NegocioTipoSaida() {
        tsDAO = new TipoSaidaDAO();
    }

    public TipoSaida salvar(TipoSaida ts,TipoSaida alterar) throws Exception {
        String erro = "";
        erro = Validar(ts,alterar);
        if(erro==""){
        return tsDAO.salvar(TipoSaida.class, ts);
        }else{
            throw new Exception(erro);
        }
    }

    private String Validar(TipoSaida ts, TipoSaida Alterar) {
        List<TipoSaida> lista = NegociosEstaticos.getNegocioTipoSaida().buscarPorDescricao(ts);
        String erro = "";
        if (Alterar == null) {
            if (lista.size() > 0) {
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getDescricao().equalsIgnoreCase(ts.getDescricao())) {
                        erro += "Este tipo de Baixa já existe";
                    }
                }
            }
        } else if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getDescricao().equalsIgnoreCase(ts.getDescricao()) && !Objects.equals(lista.get(i).getId(), ts.getId())) {
                    erro += "Este tipo de Baixa já existe";
                }
            }
        }
        return erro;
    }

    public void remover(TipoSaida ts) throws Exception {
        try {
            tsDAO.remover(TipoSaida.class, ts);

        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            throw new Exception(ler.getMessage("msg.remover"));
        }

    }

    public TipoSaida consultarPorId(TipoSaida ts) {
        return tsDAO.consutarPorId(TipoSaida.class, ts);
    }

    public List<TipoSaida> buscarPorDescricao(TipoSaida ts) {
        return tsDAO.buscarPorDescricao(ts);
    }

    public List<TipoSaida> buscarTodos() {
        return tsDAO.buscarTodos();
    }

}
