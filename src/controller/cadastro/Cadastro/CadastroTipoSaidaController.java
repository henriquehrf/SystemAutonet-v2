/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.cadastro.Cadastro;

import classesAuxiliares.NegociosEstaticos;
import controller.cadastro.Consulta.ConsultarTipoSaidaController;
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
import vo.TipoSaida;

/**
 *
 * @author PET Autonet
 */
public class CadastroTipoSaidaController {

    @FXML
    private Label Title;

    @FXML
    private TextField txtfinalidade;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnsalvar;

    @FXML
    private Label lblfinalidadeobrigatorio;

    //   private NegocioTipoSaida NegocioT;
    private static TipoSaida alterar;

    private static boolean cadastrar;

    public static TipoSaida getAlterar() {
        return alterar;
    }

    public static void setAlterar(TipoSaida alterar) {
        CadastroTipoSaidaController.alterar = alterar;
    }

    public static boolean isCadastrar() {
        return cadastrar;
    }

    public static void setCadastrar(boolean cadastrar) {
        CadastroTipoSaidaController.cadastrar = cadastrar;
    }

    public void initialize() {
        //  NegocioT = new NegocioTipoSaida();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                txtfinalidade.requestFocus();
            }
        });

        setcamposObrigatorio();

        if (!isCadastrar()) {
            completar();
        } else {
            alterar = null;
        }

    }

    @FXML
    void btnsalvar_OnAction(ActionEvent event) {
        if (verificaCampoObrigatorio()) {
            if (alterar != null) {
                try {
                    salvar(alterar);
                } catch (Exception ex) {
                    Logger.getLogger(CadastroTipoSaidaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                TipoSaida ts = new TipoSaida();
                try {
                    salvar(ts);
                } catch (Exception ex) {
                    Logger.getLogger(CadastroTipoSaidaController.class.getName()).log(Level.SEVERE, null, ex);
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
            // NegocioT = null;
            alterar = null;
            root = FXMLLoader.load(ConsultarTipoSaidaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_TipoSaida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setcamposObrigatorio() {
        lblfinalidadeobrigatorio.setVisible(false);
    }

    private boolean verificaCampoObrigatorio() {
        setcamposObrigatorio();
        boolean verifica = true;
        if (txtfinalidade.getText().isEmpty()) {
            lblfinalidadeobrigatorio.setVisible(true);
            verifica = false;
        }
        return verifica;
    }

    private void salvar(TipoSaida ts) throws Exception {

        ts.setDescricao(txtfinalidade.getText());

        try {
            // NegocioT.salvar(ts);
            NegociosEstaticos.getNegocioTipoSaida().salvar(ts, alterar);
            alterar = null;

            Parent root;
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            //   NegocioT = null;
            aviso.alerta(AlertType.INFORMATION, ler.getMessage("msg.cadastro.confirmacao"), ler.getMessage("msg.cadastro.sucesso"));
            root = FXMLLoader.load(ConsultarTipoSaidaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_TipoSaida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
        }
    }

    private void completar() {
        txtfinalidade.setText(alterar.getDescricao());

        try {
            LerMessage ler = new LerMessage();

            Title.setText(ler.getMessage("title.alterar.TipoSaida"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void txtfinalidade_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnsalvar_OnKeyPressed(event);
        }

    }

    @FXML
    void btnsalvar_OnKeyPressed(KeyEvent event) {
        if (verificaCampoObrigatorio()) {
            if (alterar != null) {
                try {
                    salvar(alterar);
                } catch (Exception ex) {
                    Logger.getLogger(CadastroTipoSaidaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                TipoSaida ts = new TipoSaida();
                try {
                    salvar(ts);
                } catch (Exception ex) {
                    Logger.getLogger(CadastroTipoSaidaController.class.getName()).log(Level.SEVERE, null, ex);
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
    void btnCancelar_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Parent root;
                // NegocioT = null;
                alterar = null;
                root = FXMLLoader.load(ConsultarTipoSaidaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_TipoSaida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
