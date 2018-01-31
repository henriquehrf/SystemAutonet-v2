/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.cadastro.Cadastro;

import classesAuxiliares.NegociosEstaticos;
import controller.cadastro.Consulta.ConsultarDepartamentoController;
import controller.cadastro.Consulta.ConsultarPessoaController;
import gui.SystemAutonet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.Departamento;

/**
 *
 * @author PET Autonet
 */
public class CadastroDepartamentoController {

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtNome;

    @FXML
    private Label Title;

    @FXML
    private Label lblSiglaObrigatorio;

    @FXML
    private Label lblNomeObrigatorio;

    @FXML
    private TextField txtSigla;

    //   private NegocioDepartamento NegocioP;
    private static Departamento alterar;

    private static boolean cadastrar;

    public void initialize() {
        
            Platform.runLater(new Runnable() {
            @Override
            public void run() {
               
                txtNome.requestFocus();
            }
        });
        setcamposObrigatorio();
        // NegocioP = new NegocioDepartamento();

        if (!isCadastrar()) {
            completar();
        } else {
            alterar = null;
        }

    }

    @FXML
    void btnSalvar_OnAction(ActionEvent event) {
        if (verificaCampoObrigatorio()) {
            if (alterar != null) {
                try {
                    salvar(alterar);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                Departamento departamento = new Departamento();

                try {
                    salvar(departamento);
                } catch (Exception ex) {

                    try {
                        LerMessage ler = new LerMessage();
                        Alertas aviso = new Alertas();
                        aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
                    } catch (Exception ex1) {
                        Logger.getLogger(CadastroDepartamentoController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
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
    void txtNomeOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvarOnKeyPressed(event);
        }

    }

    @FXML
    void txtSiglaOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvarOnKeyPressed(event);
        }

    }

    @FXML
    void btnCancelarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                alterar = null;
                // NegocioP = null;
                Parent root;
                root = FXMLLoader.load(ConsultarDepartamentoController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Departamento.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

    @FXML
    void btnSalvarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (verificaCampoObrigatorio()) {
                if (alterar != null) {
                    try {
                        salvar(alterar);
                    } catch (Exception ex) {
                        Logger.getLogger(CadastroDepartamentoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Departamento departamento = new Departamento();

                    try {
                        salvar(departamento);
                    } catch (Exception ex) {

                        try {
                            LerMessage ler = new LerMessage();
                            Alertas aviso = new Alertas();
                            aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
                        } catch (Exception ex1) {
                            Logger.getLogger(CadastroDepartamentoController.class.getName()).log(Level.SEVERE, null, ex1);
                        }
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
    void btnCancelar_OnAction(ActionEvent event) {
        try {
            alterar = null;
            // NegocioP = null;
            Parent root;
            root = FXMLLoader.load(ConsultarDepartamentoController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Departamento.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Departamento getAlterar() {
        return alterar;
    }

    public static void setAlterar(Departamento alterar) {
        CadastroDepartamentoController.alterar = alterar;
    }

    public static boolean isCadastrar() {
        return cadastrar;
    }

    public static void setCadastrar(boolean cadastrar) {
        CadastroDepartamentoController.cadastrar = cadastrar;
    }

    private void setcamposObrigatorio() {
        lblNomeObrigatorio.setVisible(false);
        lblSiglaObrigatorio.setVisible(false);
    }

    private boolean verificaCampoObrigatorio() {
        setcamposObrigatorio();
        boolean verifica = true;

        if (txtNome.getText().isEmpty()) {
            lblNomeObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtSigla.getText().isEmpty()) {
            lblSiglaObrigatorio.setVisible(true);
            verifica = false;
        }

        return verifica;

    }

    private void salvar(Departamento departamento) throws Exception {

        departamento.setNome(txtNome.getText());
        departamento.setSigla(txtSigla.getText());

        try {
            // NegocioP.salvar(departamento);
            NegociosEstaticos.getNegocioDepartamento().salvar(departamento,cadastrar);
            alterar = null;
            // NegocioP = null;
            Parent root;
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            aviso.alerta(AlertType.INFORMATION, ler.getMessage("msg.cadastro.confirmacao"), ler.getMessage("msg.cadastro.sucesso"));
            root = FXMLLoader.load(ConsultarPessoaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Departamento.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
        }
    }

    private void completar() {
        LerMessage ler = new LerMessage();

        txtNome.setText(alterar.getNome());
        txtSigla.setText(alterar.getSigla());
        try {

            Title.setText(ler.getMessage("title.alterar.pessoa"));
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }

    }

}
