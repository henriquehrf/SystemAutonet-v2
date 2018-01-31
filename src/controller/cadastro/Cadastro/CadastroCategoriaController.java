/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.cadastro.Cadastro;

import classesAuxiliares.NegociosEstaticos;
import controller.cadastro.Consulta.ConsultarCategoriaController;
import gui.SystemAutonet;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.Categoria;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class CadastroCategoriaController implements Initializable {

    @FXML
    private TextField txtCategoria;

    @FXML
    private Label lblCategoriaObrigatorio;

    @FXML
    private Button btnCancelar;

    @FXML
    private Label Title;

    @FXML
    private Button btnsalvar;

    @FXML
    private Tab tabCadastro;

    private static Categoria alterar;

    private static boolean cadastrar;

    public static Categoria getAlterar() {
        return alterar;
    }

    public static void setAlterar(Categoria alterar) {
        CadastroCategoriaController.alterar = alterar;
    }

    public static boolean isCadastrar() {
        return cadastrar;
    }

    public static void setCadastrar(boolean cadastrar) {
        CadastroCategoriaController.cadastrar = cadastrar;
    }

    @FXML
    void btnsalvar_OnAction(ActionEvent event) {
        if (verificaCampoObrigatorio()) {

            try {
                if (alterar != null) {
                    System.out.println("caso 1");
                    salvar(alterar);
                } else {
                    Categoria categoria = new Categoria();
                    System.out.println("caso 2");
                    salvar(categoria);

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
                aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ler.getMessage("msg.cadastro.incompleto"));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());

            }
        }

    }

    @FXML
    void btnCancelar_OnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(ConsultarCategoriaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Categoria.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnsalvarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (verificaCampoObrigatorio()) {

                try {
                    if (alterar != null) {
                        System.out.println("caso 1");
                        salvar(alterar);
                    } else {
                        Categoria categoria = new Categoria();
                        System.out.println("caso 2");
                        salvar(categoria);

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
                    aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ler.getMessage("msg.cadastro.incompleto"));
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
                root = FXMLLoader.load(ConsultarCategoriaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Categoria.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    @FXML
    void txtCategoriaOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnsalvarOnKeyPressed(event);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               
                txtCategoria.requestFocus();
            }
        });
        setcamposObrigatorio();

        if (!isCadastrar()) {
            completar();
        } else {
            alterar = null;
        }

    }

    private void setcamposObrigatorio() {
        lblCategoriaObrigatorio.setVisible(false);
    }

    private void completar() {
        txtCategoria.setText(alterar.getDescricao());
        LerMessage ler = new LerMessage();
        try {
            Title.setText(ler.getMessage("title.alterar.categoria"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean verificaCampoObrigatorio() {
        setcamposObrigatorio();
        boolean verifica = true;

        if (txtCategoria.getText().isEmpty()) {
            lblCategoriaObrigatorio.setVisible(true);
            verifica = false;
        }
        return verifica;

    }

    private void salvar(Categoria categoria) throws Exception {
        categoria.setDescricao(txtCategoria.getText());

        try {
            //   NegocioP.salvar(pessoa);
            NegociosEstaticos.getNegocioCategoria().salvar(categoria);
            Parent root;
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            alterar = null;

            aviso.alerta(AlertType.INFORMATION, ler.getMessage("msg.cadastro.confirmacao"), ler.getMessage("msg.cadastro.sucesso"));
            root = FXMLLoader.load(CadastroCategoriaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Categoria.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
        }

    }

}
