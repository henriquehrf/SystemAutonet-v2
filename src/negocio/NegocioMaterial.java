/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.CategoriaDAO;
import DAO.MaterialDAO;
import DAO.TipoUnidadeDAO;
import classesAuxiliares.NegociosEstaticos;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitarios.LerMessage;
import vo.Categoria;
import vo.Material;

/**
 *
 * @author Eduardo
 */
public class NegocioMaterial {

    private MaterialDAO materialDAO;
    private TipoUnidadeDAO tuDAO;
    private CategoriaDAO categoriaDAO;

    public NegocioMaterial() {
        materialDAO = new MaterialDAO();
        tuDAO = new TipoUnidadeDAO();
        categoriaDAO = new CategoriaDAO();
    }

    public Material salvar(Material material, Material alterar) throws Exception {
        String erro = validar(material, alterar);

        if (erro.equals("")) {
            return materialDAO.salvar(Material.class, material);
        } else {
            throw new Exception(erro);
        }
    }

    public void remover(Material material) throws Exception {
        try {
            materialDAO.remover(Material.class, material);

        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            throw new Exception(ler.getMessage("msg.remover"));
        }
    }

    public Material consultarPorId(Material material) throws Exception {
        Material mat = materialDAO.consutarPorId(Material.class, material);
        mat.setCategoriaNome(mat.getId_categoria().getDescricao());
        mat.setQuantidadeDisponivel(NegociosEstaticos.getNegocioEstoqueMateria().QtdDisponivelDoMaterial(material));
        return mat;
    }

    public List<Material> buscarPorDescricao(Material material) {
        return preencher(materialDAO.buscarPorDescricao(material));
    }

    public List<Material> buscarPorQuantidade(Material material) {

        return preencher(materialDAO.buscarPorQuantidade(material));
    }

    public List<Material> buscarTodos() {
        return preencher(materialDAO.buscarTodos());

    }

    public List<Material> buscarPorCategoria(Categoria categoria) {
        return preencher(materialDAO.buscarPorCategoria(categoria));
    }

    private String validar(Material material, Material alterar) {
        String erro = "";

        if (material.getId_categoria() == null) {
            erro += "Favor selecionar a categoria do material\n";
        }

        if (material.getId_tipo_unidade() == null) {
            erro += "Favor selecionar a unidade do material";
        }
        List<Material> lista = NegociosEstaticos.getNegocioMaterial().buscarPorDescricao(material);
        if (alterar == null) {
            if (lista.size() > 0) {
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getDescricao().equalsIgnoreCase(material.getDescricao())) {
                        erro += "Existe(m) registro(s) com esta descrição";
                    }
                }
            }
        } else if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getDescricao().equalsIgnoreCase(material.getDescricao())
                        && lista.get(i).getId() != material.getId()) {
                    erro += "Existe(m) registro(s) com esta descrição";
                }
            }
        }

        return erro;
    }

    private List<Material> preencher(List<Material> buscarTodos) {

        for (int i = 0; i < buscarTodos.size(); i++) {
            buscarTodos.get(i).setCategoriaNome(buscarTodos.get(i).getId_categoria().getDescricao());
            buscarTodos.get(i).setUnidadeMedida(buscarTodos.get(i).getId_tipo_unidade().getSigla());
            try {
                buscarTodos.get(i).setQuantidadeDisponivel(NegociosEstaticos.getNegocioEstoqueMateria().QtdDisponivelDoMaterial(buscarTodos.get(i)));
            } catch (Exception ex) {
                Logger.getLogger(NegocioMaterial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return buscarTodos;
    }

}
