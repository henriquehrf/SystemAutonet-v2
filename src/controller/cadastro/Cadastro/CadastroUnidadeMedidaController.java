/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.cadastro.Cadastro;

import classesAuxiliares.NegociosEstaticos;
import controller.cadastro.Consulta.ConsultarUnidadeMedidaController;
import gui.SystemAutonet;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.TipoUnidade;

/**
 *
 * @author PET Autonet
 */
public class CadastroUnidadeMedidaController {

    @FXML
    private Button btnSalvar;

    @FXML
    private Label Title;

    @FXML
    private Button btnCancelar;

    @FXML
    private Label lbldescricaoOrbrigatorio;

    @FXML
    private Label lblSiglaobrigatorio;

    @FXML
    private TextField txtdescricao;

    @FXML
    private TextField txtsigla;

    // private NegocioTipoUnidade NegocioT;
    private static TipoUnidade alterar;

    private static boolean cadastrar;

    public static TipoUnidade getAlterar() {
        return alterar;
    }

    public static void setAlterar(TipoUnidade alterar) {
        CadastroUnidadeMedidaController.alterar = alterar;
    }

    public static boolean isCadastrar() {
        return cadastrar;
    }

    public static void setCadastrar(boolean cadastrar) {
        CadastroUnidadeMedidaController.cadastrar = cadastrar;
    }

    public void initialize() {
        // NegocioT = new NegocioTipoUnidade();
        setcamposObrigatorio();

        if (!isCadastrar()) {
            completar();
        } else {
            alterar = null;
        }

    }

    @FXML
    void btnSalvar_OnAction(ActionEvent event) {
        if (verificaCampoObrigatorio()) {
            try {
                if (alterar != null) {
                    salvar(alterar);
                } else {
                    TipoUnidade ts = new TipoUnidade();
                    salvar(ts);
                }
            } catch (Exception ex) {
                LerMessage ler = new LerMessage();
                Alertas aviso = new Alertas();
                try {

                    aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
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
            // NegocioT = null;
            alterar = null;
            root = FXMLLoader.load(ConsultarUnidadeMedidaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_UnidadeMedida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setcamposObrigatorio() {
        lblSiglaobrigatorio.setVisible(false);
        lbldescricaoOrbrigatorio.setVisible(false);
    }

    private void completar() {

        txtdescricao.setText(alterar.getDescricao());
        txtsigla.setText(alterar.getSigla());

        try {
            LerMessage ler = new LerMessage();

            Title.setText(ler.getMessage("title.alterar.tipounidade"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void salvar(TipoUnidade ts) throws Exception {

        ts.setDescricao(txtdescricao.getText());
        ts.setSigla(txtsigla.getText());

        try {
            NegociosEstaticos.getNegocioTipoUnidade().salvar(ts, alterar);
            //NegocioT.salvar(ts);
            alterar = null;
            //NegocioT = null;

            Parent root;
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();

            aviso.alerta(AlertType.INFORMATION, ler.getMessage("msg.cadastro.confirmacao"), ler.getMessage("msg.cadastro.sucesso"));
            root = FXMLLoader.load(ConsultarUnidadeMedidaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_UnidadeMedida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
        }
    }

    private boolean verificaCampoObrigatorio() {
        setcamposObrigatorio();
        boolean verifica = true;
        if (txtdescricao.getText().isEmpty()) {
            lbldescricaoOrbrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtsigla.getText().isEmpty()) {
            lblSiglaobrigatorio.setVisible(true);
            verifica = false;
        }
        return verifica;
    }

    @FXML
    void txtdescricao_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvar_onKeyPressed(event);
        }

    }

    @FXML
    void txtsigla_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvar_onKeyPressed(event);
        }
    }

    @FXML
    void btnSalvar_onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (verificaCampoObrigatorio()) {
                try {
                    if (alterar != null) {
                        salvar(alterar);
                    } else {
                        TipoUnidade ts = new TipoUnidade();
                        salvar(ts);
                    }
                } catch (Exception ex) {
                    LerMessage ler = new LerMessage();
                    Alertas aviso = new Alertas();
                    try {

                        aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
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
    void btnCancelar_onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Parent root;
                // NegocioT = null;
                alterar = null;
                root = FXMLLoader.load(ConsultarUnidadeMedidaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_UnidadeMedida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
