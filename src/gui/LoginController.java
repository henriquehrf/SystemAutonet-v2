/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import classesAuxiliares.ClasseDoSistemaEstatico;
import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import utilitarios.Criptografia;
import vo.Pessoa;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class LoginController {

    @FXML
    private Label labelUsuarioAlerta;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnLogin;

    @FXML
    private HBox hboxInfo;

    @FXML
    private Label labelSenhaAlerta;

    @FXML
    private Label lblautenticacao;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtUsuario;

    @FXML
    private ProgressIndicator loading;

    private boolean autenticar() {
        labelSenhaAlerta.setVisible(false);
        labelUsuarioAlerta.setVisible(false);
        lblautenticacao.setVisible(false);

        Pessoa user = new Pessoa();
        Pessoa otherUser = null;

        try {

            Criptografia x = new Criptografia(txtSenha.getText());

            user.setUsuario(txtUsuario.getText());
            user.setSenha(x.getSenha_criptografada());

            otherUser = NegociosEstaticos.getNegocioPessoa().buscarPorUsuario(user);
            System.out.println("Aqui -> " + otherUser);
            if (otherUser.getId() != null) {
                if (otherUser.getSenha().equals(x.getSenha_criptografada())) {
                    ClasseDoSistemaEstatico.setPessoa(otherUser);
                    return true;
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lblautenticacao.setText("*Senha Inválida");
                            labelSenhaAlerta.setVisible(true);
                            lblautenticacao.setVisible(true);
                        }
                    });

                    return false;
                }
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lblautenticacao.setText("Usuário não encontrado");
                        labelUsuarioAlerta.setVisible(true);
                        lblautenticacao.setVisible(true);
                    }
                });

                return false;
            }

        } catch (Exception ex) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lblautenticacao.setText("Não há conexão com a base de dados");
                    labelUsuarioAlerta.setVisible(true);
                    lblautenticacao.setVisible(true);
                }
            });

            System.out.println(ex.getMessage());
        }
        return false;
    }

    void logar() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                BorderPane root = null;
                try {
                    root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                    Scene scene = new Scene(root);
                    SystemAutonet.SCENE = scene;
                    SystemAutonet.login.setScene(scene);
                    SystemAutonet.login.centerOnScreen();
                    return;
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });

    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws InterruptedException {

        new Thread() {
            @Override
            public void run() {
                loading.setVisible(true);
                btnLogin.setDisable(true);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                if (autenticar()) {

                    BorderPane root = null;
                    try {
                        logar();
                        return;

                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }

                } else {
                    loading.setVisible(false);
                    btnLogin.setDisable(false);
                }
            }
        }.start();

    }

    @FXML
    void btnCancelarOnAction(ActionEvent event
    ) {
        System.exit(0);
    }

    @FXML
    void txtUsuarioOnKeyPressed(KeyEvent event
    ) {
        //   habilitarLogin();

        labelUsuarioAlerta.setVisible(false);
        lblautenticacao.setVisible(false);
        labelSenhaAlerta.setVisible(false);
    }

    @FXML
    void txtSenhaonKeyPressed(KeyEvent event
    ) {
        //      habilitarLogin();
        labelUsuarioAlerta.setVisible(false);
        lblautenticacao.setVisible(false);
        labelSenhaAlerta.setVisible(false);
    }

    public void initialize() {
        loading.setVisible(false);
        new Thread() {
            @Override
            public void run() {
                NegociosEstaticos.iniciar();
            }
        }.start();

        labelSenhaAlerta.setVisible(false);
        labelUsuarioAlerta.setVisible(false);
        btnLogin.setDefaultButton(true);
        lblautenticacao.setVisible(false);

    }

}
