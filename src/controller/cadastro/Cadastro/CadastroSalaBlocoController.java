/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.cadastro.Cadastro;

import classesAuxiliares.NegociosEstaticos;
import controller.cadastro.Consulta.ConsultarLocaisController;
import controller.estoque.entrada.EntradaMaterialController;
import gui.SystemAutonet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.Departamento;
import vo.Local;

/**
 *
 * @author PET Autonet
 */
public class CadastroSalaBlocoController {

    @FXML
    private Button btnSalvar;

    @FXML
    private ComboBox<String> cmbDepartamento;

    @FXML
    private TextField txtnumero;

    @FXML
    private TextField txtresponsavel;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtdescricao;

    @FXML
    private Label Title;

    @FXML
    private Label lblNumeroObrigatorio;

    @FXML
    private Label lblResponsavelObrigatorio;

    @FXML
    private Label lblDepartamentoObrigatorio;

    @FXML
    private Label lblDescricaoObrigatorio;

    //private NegocioLocal NegocioL;
    private static Local alterar;

    private static boolean cadastrar;

    private static Stage adicionar = null;

    public static Stage isAdicionar() {
        return adicionar;
    }

    public static void setAdicionar(Stage adicionar) {
        CadastroSalaBlocoController.adicionar = adicionar;
    }

    //  private NegocioDepartamento negocioDepartamento;
    List<Departamento> lista;

    public void initialize() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtdescricao.requestFocus();
            }
        });

        //   NegocioL = new NegocioLocal();
        //  negocioDepartamento = new NegocioDepartamento();
        setcamposObrigatorio();
        lista = NegociosEstaticos.getNegocioDepartamento().buscarTodos();

        ObservableList<String> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i).getSigla());
        }
        Collections.sort(dado);
        cmbDepartamento.setItems(dado);

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
                    Local local = new Local();
                    salvar(local);
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
            //NegocioL = null;
            alterar = null;
            if (adicionar != null) {
                adicionar.close();
                adicionar = null;
                return;
            }
            //negocioDepartamento = null;
            root = FXMLLoader.load(ConsultarLocaisController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Locais.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Local getAlterar() {
        return alterar;
    }

    public static void setAlterar(Local alterar) {
        CadastroSalaBlocoController.alterar = alterar;
    }

    public static boolean isCadastrar() {
        return cadastrar;
    }

    public static void setCadastrar(boolean cadastrar) {
        CadastroSalaBlocoController.cadastrar = cadastrar;
    }

    private void setcamposObrigatorio() {
        lblDepartamentoObrigatorio.setVisible(false);
        lblDescricaoObrigatorio.setVisible(false);
        lblNumeroObrigatorio.setVisible(false);
        lblResponsavelObrigatorio.setVisible(false);
    }

    private boolean verificaCampoObrigatorio() {
        setcamposObrigatorio();
        boolean verifica = true;
        if (txtdescricao.getText().isEmpty()) {
            lblDescricaoObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtnumero.getText().isEmpty()) {
            lblNumeroObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtresponsavel.getText().isEmpty()) {
            lblResponsavelObrigatorio.setVisible(true);
            verifica = false;
        }
        return verifica;
    }

    private void completar() {
        txtdescricao.setText(alterar.getDescricao());
        txtnumero.setText(String.valueOf(alterar.getNumero()));
        txtresponsavel.setText(alterar.getResponsavel());
        cmbDepartamento.setValue(alterar.getId_departamento().getSigla());
        try {
            LerMessage ler = new LerMessage();

            Title.setText(ler.getMessage("title.alterar.local"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void salvar(Local local) throws Exception {

        local.setDescricao(txtdescricao.getText());
        local.setNumero(Integer.parseInt(txtnumero.getText()));
        local.setResponsavel(txtresponsavel.getText());
        //local.setBloco(bloco);
        Departamento dp = new Departamento();
        for (Departamento vo : lista) {
            if (vo.getSigla().equals(cmbDepartamento.getValue())) {
                dp = vo;
                break;
            }
        }
        local.setId_departamento(dp);
        try {
            //NegocioL.salvar(local);
            NegociosEstaticos.getNegocioLocal().salvar(local, alterar);
            //  NegocioL = null;
            alterar = null;
            //  negocioDepartamento = null;

            Parent root;
            LerMessage ler = new LerMessage();

            Alertas aviso = new Alertas();
            aviso.alerta(AlertType.INFORMATION, ler.getMessage("msg.cadastro.confirmacao"), ler.getMessage("msg.cadastro.sucesso"));

            if (adicionar != null) {
                adicionar.close();
                adicionar = null;
                EntradaMaterialController.localAdd = local;
                return;
            }

            root = FXMLLoader.load(ConsultarLocaisController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Locais.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));

            SystemAutonet.SCENE.setRoot(root);

        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
        }
    }

    @FXML
    void txtdescricao_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (verificaCampoObrigatorio()) {
                try {
                    if (alterar != null) {
                        salvar(alterar);
                    } else {
                        Local local = new Local();
                        salvar(local);
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
    void txtnumero_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (verificaCampoObrigatorio()) {
                try {
                    if (alterar != null) {
                        salvar(alterar);
                    } else {
                        Local local = new Local();
                        salvar(local);
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
    void cmbDepartamento_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (verificaCampoObrigatorio()) {
                try {
                    if (alterar != null) {
                        salvar(alterar);
                    } else {
                        Local local = new Local();
                        salvar(local);
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
    void txtresponsavel_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (verificaCampoObrigatorio()) {
                try {
                    if (alterar != null) {
                        salvar(alterar);
                    } else {
                        Local local = new Local();
                        salvar(local);
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
    void btnSalvar_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            if (verificaCampoObrigatorio()) {
                try {
                    if (alterar != null) {
                        salvar(alterar);
                    } else {
                        Local local = new Local();
                        salvar(local);
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
    void btnCancelar_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            try {
                Parent root;
                //NegocioL = null;
                alterar = null;
                if (adicionar != null) {
                    adicionar.close();
                    adicionar = null;
                    return;
                }
                //negocioDepartamento = null;
                root = FXMLLoader.load(ConsultarLocaisController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Locais.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

}
