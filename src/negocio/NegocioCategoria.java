/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import DAO.CategoriaDAO;
import java.util.List;
import java.util.Properties;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import utilitarios.LerMessage;
import vo.Categoria;

/**
 *
 * @author Eduardo
 */
public class NegocioCategoria {

    private final CategoriaDAO categoriaDAO;

    public NegocioCategoria() {
        categoriaDAO = new CategoriaDAO();
    }

    public Categoria salvar(Categoria categoria) throws Exception {
        String erro = validar(categoria);

        if (erro.equals("")) {
            return categoriaDAO.salvar(Categoria.class, categoria);
        } else {
            throw new Exception(erro);
        }

    } 

    public void remover(Categoria categoria) throws Exception {
        try {
           categoriaDAO.remover(Categoria.class, categoria);
            
        }catch(Exception ex){
             LerMessage ler = new LerMessage();
            throw new Exception(ler.getMessage("msg.remover"));
        }
    }

    public Categoria consultarPorId(Categoria categoria) {
        return categoriaDAO.consutarPorId(Categoria.class, categoria);
    }
    
    
    public List<Categoria> buscarPorDescricao(Categoria categoria) {
        return categoriaDAO.buscarPorDescricao(categoria);
    }
    public boolean compararPorDescricao(Categoria categoria) {
        return categoriaDAO.compararPorDescricao(categoria);
    }

    public List<Categoria> bucarTodos() {
        return categoriaDAO.buscarTodos();
    }
    
    public String validar(Categoria cat){
        String erro = "";
        
        if(!compararPorDescricao(cat)){
            erro = "Esse nome ja existe\n";
        }
        if(cat.getDescricao().length()>50){
            erro += "\n Estorou o limite de caracteres ";
        }
        
        
        return erro;       
        
    }
}
