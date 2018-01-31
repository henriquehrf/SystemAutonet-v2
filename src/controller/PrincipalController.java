package controller;

import classesAuxiliares.ClasseDoSistemaEstatico;
import gui.SystemAutonet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import controller.cadastro.Consulta.*;
import enumm.PerfilUsuario;
import gui.LoginController;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SeparatorMenuItem;

public class PrincipalController {

    @FXML
    private MenuItem cad_material;

    @FXML
    private MenuItem cad_Categoria;

    @FXML
    private Menu mnu_estoque;

    @FXML
    private MenuItem est_entradaMaterial;

    @FXML
    private MenuItem cad_tipoBaixa;

    @FXML
    private MenuItem est_baixarMaterial;

    @FXML
    private BorderPane bdp_principal;

    @FXML
    private MenuItem cad_local;

    @FXML
    private MenuItem emp_acompanhar;

    @FXML
    private MenuItem ajuda_sobre;

    @FXML
    private Menu mnu_emprestimo;

    @FXML
    private MenuBar mnu_bar;

    @FXML
    private MenuItem cad_departamento;

    @FXML
    private MenuItem cons_material;

    @FXML
    private MenuItem cons_pessoa;

    @FXML
    private MenuItem ajuda_manualSistema;

    @FXML
    private Menu mnu_ajuda;

    @FXML
    private MenuItem cad_pessoa;

    @FXML
    private MenuItem cons_local;

    @FXML
    private MenuItem cad_unidadeMedida;

    @FXML
    private Menu mnu_consulta;

    @FXML
    private Menu cons_mnu_Movimetacoes;

    @FXML
    private MenuItem cons_fornecedor;

    @FXML
    private MenuItem emp_analisar;

    @FXML
    private MenuItem cons_mov_saidaMaterial;

    @FXML
    private MenuItem cad_fornecedor;

    @FXML
    private MenuItem cons_emprestimo;

    @FXML
    private MenuItem emp_solicitar;

    @FXML
    private MenuItem cons_mov_entradaMaterial;

    @FXML
    private MenuItem emp_devolver;

    @FXML
    private Menu mnu_Cadastro;

    @FXML
    private SeparatorMenuItem SeparadoDevolverSoliticar;

    @FXML
    private SeparatorMenuItem SeparadoAcompanharAnalisar;

    @FXML
    private Label txtDadosPessoais;

    @FXML
    private Button btnLogoff;
    private String msg = "Usuário: ";

    public void initialize() {
        btnLogoff.setFocusTraversable(false);

        Date date = ClasseDoSistemaEstatico.getPessoa().getUltimo_acesso();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");

        msg += ClasseDoSistemaEstatico.getPessoa().getNome();
        msg += "\nÚltimo Acesso: " + formatter.format(date);
        msg += "\nPerfil de Usuário: " + ClasseDoSistemaEstatico.getPessoa().getFuncao();
        msg += "\nVocê não tem nenhuma mensagem ativa";

        txtDadosPessoais.setText(msg);
//           List<Emprestimo> emp = NegociosEstaticos.getNegocioEmprestimo().buscarPorIdPessoa(ClasseDoSistemaEstatico.getPessoa());
//            List<EmprestimoEstoqueMaterial> empm = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarPorNaoDevolvido(emp.get(0));
//            System.out.println("Inicio");
//            
//            for(EmprestimoEstoqueMaterial vo: empm){
//                System.out.println(vo.getObservacao());
//            }
//             System.out.println("Fim");

        if (ClasseDoSistemaEstatico.getPessoa().getFuncao().equals(PerfilUsuario.ALUNO) || ClasseDoSistemaEstatico.getPessoa().getFuncao().equals(PerfilUsuario.TESTE)) {
            mnu_Cadastro.setVisible(false);
            emp_analisar.setVisible(false);
            emp_devolver.setVisible(false);
            mnu_estoque.setVisible(false);
            SeparadoDevolverSoliticar.setVisible(false);
            SeparadoAcompanharAnalisar.setVisible(false);
        }

    }

    @FXML
    void cad_departamentoOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarDepartamentoController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Departamento.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void cad_fornecedorOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarFornecedorController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Fornecedor.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void cad_local_OnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarLocaisController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Locais.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void cad_materialOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarMaterialController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Material.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void cad_pessoaOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarPessoaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Pessoa.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void cad_tipoBaixaOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarTipoSaidaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_TipoSaida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void cad_unidadeMedidaOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarUnidadeMedidaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_UnidadeMedida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void emp_solicitarOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarUnidadeMedidaController.class.getClassLoader().getResource("fxml/emprestimo/Solicitar/SolicitaEmprestimo.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void emp_analisarOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarUnidadeMedidaController.class.getClassLoader().getResource("fxml/emprestimo/Analisar/AnalisarEmprestimo.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void emp_acompanharOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarUnidadeMedidaController.class.getClassLoader().getResource("fxml/emprestimo/Acompanhar/AcompanharEmprestimo.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void emp_devolverOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarUnidadeMedidaController.class.getClassLoader().getResource("fxml/emprestimo/Devolver/DevolverEmprestimo.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void cad_Categoria(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarCategoriaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Categoria.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void est_entradaMaterial_OnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarCategoriaController.class.getClassLoader().getResource("fxml/estoque/Entrada/EntradaMaterial.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnLogoffOnAction(ActionEvent event) {
        ClasseDoSistemaEstatico.setPessoa(null);
        BorderPane root = null;
        try {

            root = FXMLLoader.load(LoginController.class.getClassLoader().getResource("gui/Login.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        Scene scene = new Scene(root);
        SystemAutonet.SCENE = scene;
        SystemAutonet.login.setScene(scene);
        SystemAutonet.login.centerOnScreen();

    }

}
