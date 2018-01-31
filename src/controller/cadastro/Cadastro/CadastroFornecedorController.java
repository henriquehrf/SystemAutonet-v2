/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.cadastro.Cadastro;

import classesAuxiliares.NegociosEstaticos;
import classesAuxiliares.Validar;
import controller.cadastro.Consulta.ConsultarFornecedorController;
import controller.estoque.entrada.EntradaMaterialController;
import gui.SystemAutonet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.Fornecedor;

/**
 *
 * @author PET Autonet
 */
public class CadastroFornecedorController {

    private static Fornecedor alterar;

    private static boolean cadastrar;

    // private NegocioFornecedor negocioF;
    @FXML
    private Label lblCnpjObrigatorio;

    @FXML
    private Label lblPessoaResponsavelObrigatorio;

    @FXML
    private Label lblInscricaoObrigatorio;

    @FXML
    private Label lblEmailObrigatorio;

    @FXML
    private Label lblnomefantasiaObrigatorio;

    @FXML
    private Label lblTelefoneObrigatorio;

    @FXML
    private Label lblEnderecoObrigatorio;

    @FXML
    private Label lblrazaoSocialObrigatorio;

    @FXML
    private Label Title;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtNomeFantasia;

    @FXML
    private TextField txtInscricaoEstadual;

    @FXML
    private TextArea txtEndereco;

    @FXML
    private TextField txtPessoaResponsavel;

    @FXML
    private TextField txtRazaoSocial;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCnpj;

    private static Stage adicionar = null;

    public static Stage getAdicionar() {
        return adicionar;
    }

    public static void setAdicionar(Stage adicionar) {
        CadastroFornecedorController.adicionar = adicionar;
    }

    public static Fornecedor getAlterar() {
        return alterar;
    }

    public static void setAlterar(Fornecedor alterar) {
        CadastroFornecedorController.alterar = alterar;
    }

    public static boolean isCadastrar() {
        return cadastrar;
    }

    public static void setCadastrar(boolean cadastrar) {
        CadastroFornecedorController.cadastrar = cadastrar;
    }

    public void initialize() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                txtRazaoSocial.requestFocus();
            }
        });

        //    negocioF = new NegocioFornecedor();
        setcamposObrigatorio();
        if (!isCadastrar()) {
            completar();
        } else {
            alterar = null;
        }

    }

    @FXML
    void txtRazaoSocialOnKeyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            btnSalvarOnKeyPressed(event);
        }

    }

    @FXML
    void txtNomeFantasiaOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvarOnKeyPressed(event);
        }

    }

    @FXML
    void txtCnpjOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvarOnKeyPressed(event);
        }

    }

    @FXML
    void txtInscricaoEstadualOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvarOnKeyPressed(event);
        }

    }

    @FXML
    void txtTelefoneOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvarOnKeyPressed(event);
        }

    }

    @FXML
    void txtEmailOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvarOnKeyPressed(event);
        }

    }

    @FXML
    void txtPessoaResponsavelOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvarOnKeyPressed(event);
        }
    }

    @FXML
    void btnSalvarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (verificaCampoObrigatorio()) {
                try {
                    if (alterar != null) {
                        salvar(alterar);
                    } else {
                        Fornecedor fornecedor = new Fornecedor();
                        salvar(fornecedor);
                    }
                } catch (Exception ex) {
                    LerMessage ler = new LerMessage();
                    Alertas aviso = new Alertas();
                    try {

                        aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
                    } catch (Exception ex1) {
                        System.out.println(ex1.getMessage());
                    }
                }
            } else {
                try {
                    LerMessage ler = new LerMessage();
                    Alertas aviso = new Alertas();
                    aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ler.getMessage("msg.cadastro.incompleto"));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());

                }
            }
        }
    }

    @FXML
    void btnCancelarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Parent root;
                alterar = null;
                if (adicionar != null) {
                    adicionar.close();
                    adicionar = null;
                    return;
                }
                //     negocioF = null;
                root = FXMLLoader.load(ConsultarFornecedorController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Fornecedor.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @FXML
    void btnSalvar_OnAction(ActionEvent event) {
        if (verificaCampoObrigatorio()) {
            try {
                if (alterar != null) {
                    salvar(alterar);
                } else {
                    Fornecedor fornecedor = new Fornecedor();
                    salvar(fornecedor);
                }
            } catch (Exception ex) {
                LerMessage ler = new LerMessage();
                Alertas aviso = new Alertas();
                try {

                    aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
                } catch (Exception ex1) {
                    System.out.println(ex1.getMessage());
                }
            }
        } else {
            try {
                LerMessage ler = new LerMessage();
                Alertas aviso = new Alertas();
                aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ler.getMessage("msg.cadastro.incompleto"));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());

            }
        }
    }

    @FXML
    void btnCancelar_OnAction(ActionEvent event) {
        try {
            Parent root;
            alterar = null;
            if (adicionar != null) {
                adicionar.close();
                adicionar = null;
                return;
            }
            //     negocioF = null;
            root = FXMLLoader.load(ConsultarFornecedorController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Fornecedor.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void completar() {
        txtCnpj.setText(alterar.getCnpj());
        txtEmail.setText(alterar.getEmail());
        txtEndereco.setText(alterar.getEndereco());
        txtInscricaoEstadual.setText(alterar.getInscricao_estadual());
        txtNomeFantasia.setText(alterar.getNome_fantasia());
        txtPessoaResponsavel.setText(alterar.getPessoa_responsavel());
        txtRazaoSocial.setText(alterar.getRazao_social());
        txtTelefone.setText(alterar.getTelefone());
        LerMessage ler = new LerMessage();

        try {

            Title.setText(ler.getMessage("title.alterar.fornecedor"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void salvar(Fornecedor fornecedor) throws Exception {
        boolean trava = false;

        fornecedor.setEmail(txtEmail.getText());
        fornecedor.setEndereco(txtEndereco.getText());
        fornecedor.setInscricao_estadual(txtInscricaoEstadual.getText());
        fornecedor.setNome_fantasia(txtNomeFantasia.getText());
        fornecedor.setPessoa_responsavel(txtPessoaResponsavel.getText());
        fornecedor.setRazao_social(txtRazaoSocial.getText());
        fornecedor.setTelefone(txtTelefone.getText());
        if (Validar.isDigit(txtCnpj.getText().toCharArray())) {
            fornecedor.setCnpj(txtCnpj.getText());
        } else {
            this.lblCnpjObrigatorio.setVisible(true);
            trava = true;
        }

        if (trava) {
            IncompatibilidadeNumero();
            return;
        }
        try {
            //negocioF.salvar(fornecedor);
            NegociosEstaticos.getNegocioFornecedor().salvar(fornecedor);
            alterar = null;
            // negocioF = null;
            Parent root;
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            aviso.alerta(Alert.AlertType.INFORMATION, ler.getMessage("msg.cadastro.confirmacao"), ler.getMessage("msg.cadastro.sucesso"));

            if (adicionar != null) {
                adicionar.close();
                adicionar = null;
                EntradaMaterialController.fornecedorAdd = fornecedor;
                return;
            }

            root = FXMLLoader.load(ConsultarFornecedorController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Fornecedor.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
        }
    }

    private void IncompatibilidadeNumero() throws Exception {
        LerMessage ler = new LerMessage();
        Alertas aviso = new Alertas();
        aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.dados.erro"), ler.getMessage("msg.incompatibilidade.numero"));

    }

    private void setcamposObrigatorio() {
        lblCnpjObrigatorio.setVisible(false);
        lblEmailObrigatorio.setVisible(false);
        lblEnderecoObrigatorio.setVisible(false);
        lblInscricaoObrigatorio.setVisible(false);
        lblPessoaResponsavelObrigatorio.setVisible(false);
        lblTelefoneObrigatorio.setVisible(false);
        lblnomefantasiaObrigatorio.setVisible(false);
        lblrazaoSocialObrigatorio.setVisible(false);
    }

    private boolean verificaCampoObrigatorio() {
        setcamposObrigatorio();
        boolean verifica = true;

        if (txtCnpj.getText().isEmpty()) {
            lblCnpjObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtEmail.getText().isEmpty()) {
            lblEmailObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtEndereco.getText().isEmpty()) {
            lblEnderecoObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtInscricaoEstadual.getText().isEmpty()) {
            lblInscricaoObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtNomeFantasia.getText().isEmpty()) {
            lblnomefantasiaObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtPessoaResponsavel.getText().isEmpty()) {
            lblPessoaResponsavelObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtRazaoSocial.getText().isEmpty()) {
            lblrazaoSocialObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtTelefone.getText().isEmpty()) {
            lblTelefoneObrigatorio.setVisible(true);
            verifica = false;
        }

        return verifica;
    }
}
