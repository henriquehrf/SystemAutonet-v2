/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.cadastro.Cadastro;

import classesAuxiliares.NegociosEstaticos;
import controller.cadastro.Consulta.ConsultarMaterialController;
import controller.estoque.entrada.EntradaMaterialController;
import enumm.PoliticaUso;
import gui.SystemAutonet;
import java.util.Collections;
import java.util.List;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.Categoria;
import vo.Material;
import vo.TipoUnidade;

/**
 *
 * @author PET Autonet
 */
public class CadastroMaterialController {

    @FXML
    private Label lblDadostecnicosObrigatorio;

    @FXML
    private Label lbldescricaoObrigatorio;

    @FXML
    private Label lblcategorioobrigatorio;

    @FXML
    private Label lblUnidadeMedidaObrigatorio;

    @FXML
    private Label lblPolitacaDeUsoObrigatorio;

    @FXML
    private ComboBox<PoliticaUso> cmbPoliticaUso;

    @FXML
    private Button btnSalvar;

    @FXML
    private TextArea txtDadosTecnicos;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtdescricao;

    @FXML
    private Label Title;

    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private ComboBox<String> cmbUnidadeMedida;

    private static Material alterar;

    private static boolean cadastrar;

    private static Stage adicionar = null;

    public static Stage isAdicionar() {
        return adicionar;
    }

    public static void setAdicionar(Stage adicionar) {
        CadastroMaterialController.adicionar = adicionar;
    }

    public static Material getAlterar() {
        return alterar;
    }

    public static void setAlterar(Material alterar) {
        CadastroMaterialController.alterar = alterar;
    }

    public static boolean isCadastrar() {
        return cadastrar;
    }

    public static void setCadastrar(boolean cadastrar) {
        CadastroMaterialController.cadastrar = cadastrar;
    }

    public void initialize() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                txtdescricao.requestFocus();
            }
        });

        ObservableList<PoliticaUso> perf = FXCollections.observableArrayList((PoliticaUso.values()));
        List<TipoUnidade> lista = NegociosEstaticos.getNegocioTipoUnidade().buscarTodos();
        List<Categoria> lista2 = NegociosEstaticos.getNegocioCategoria().bucarTodos();

        ObservableList<String> dado = FXCollections.observableArrayList();
        ObservableList<String> dado2 = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i).getSigla());
        }

        for (int i = 0; i < lista2.size(); i++) {
            dado2.add(lista2.get(i).getDescricao());
        }

        setcamposObrigatorio();
        Collections.sort(dado);
        Collections.sort(dado2);
        Collections.sort(perf);
        cmbPoliticaUso.setItems(perf);
        cmbUnidadeMedida.setItems(dado);
        cmbCategoria.setItems(dado2);

        cmbUnidadeMedida.getSelectionModel().select(0);
        cmbPoliticaUso.getSelectionModel().select(0);
        cmbCategoria.getSelectionModel().select(0);

        if (!isCadastrar()) {
            completar();
        } else {
            alterar = null;
        }

    }

    private void setcamposObrigatorio() {
        lblDadostecnicosObrigatorio.setVisible(false);
        lblPolitacaDeUsoObrigatorio.setVisible(false);
        lblUnidadeMedidaObrigatorio.setVisible(false);
        lblcategorioobrigatorio.setVisible(false);
        lbldescricaoObrigatorio.setVisible(false);
    }

    private void completar() {
        txtDadosTecnicos.setText(alterar.getDadosTecnicos());
        txtdescricao.setText(alterar.getDescricao());
        cmbCategoria.setValue(alterar.getId_categoria().getDescricao());
        cmbUnidadeMedida.setValue(alterar.getId_tipo_unidade().getSigla());
        cmbPoliticaUso.setValue(alterar.getPoliticaUso());
        LerMessage ler = new LerMessage();
        try {

            Title.setText(ler.getMessage("title.alterar.material"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean verificaCampoObrigatorio() {
        setcamposObrigatorio();
        boolean verifica = true;

        if (txtdescricao.getText().isEmpty()) {
            lbldescricaoObrigatorio.setVisible(true);
            verifica = false;
        }
        if (cmbCategoria.getValue() == null) {
            lblcategorioobrigatorio.setVisible(true);
            verifica = false;
        }
        if (cmbPoliticaUso.getValue() == null) {
            lblPolitacaDeUsoObrigatorio.setVisible(true);
            verifica = false;
        }
        if (cmbUnidadeMedida.getValue() == null) {
            lblUnidadeMedidaObrigatorio.setVisible(true);
            verifica = false;
        }
        return verifica;
    }

    private void salvar(Material material) throws Exception {

        material.setDescricao(txtdescricao.getText());
        material.setDadosTecnicos(txtDadosTecnicos.getText());
        material.setPoliticaUso(cmbPoliticaUso.getValue());
        material.setQuantidade(0);

        Categoria cat = new Categoria();
        TipoUnidade tu = new TipoUnidade();
        tu.setSigla(cmbUnidadeMedida.getValue());
        cat.setDescricao(cmbCategoria.getValue());

        List<Categoria> categoria = NegociosEstaticos.getNegocioCategoria().buscarPorDescricao(cat);
        List<TipoUnidade> TUnidade = NegociosEstaticos.getNegocioTipoUnidade().buscarPorSigla(tu);

        if (categoria.size() == 1) {
            material.setId_categoria(categoria.get(0));
        }
        if (TUnidade.size() == 1) {
            material.setId_tipo_unidade(TUnidade.get(0));
        }
        if (categoria.size() > 1) {
            for (int i = 0; i < categoria.size(); i++) {
                if (categoria.get(i).getDescricao().equals(cat.getDescricao())) {
                    material.setId_categoria(categoria.get(i));
                }
            }
        }
        if (TUnidade.size() > 1) {
            for (int i = 0; i < TUnidade.size(); i++) {
                if (TUnidade.get(i).getSigla().equals(tu.getSigla())) {
                    material.setId_tipo_unidade(TUnidade.get(i));
                }
            }
        }

        try {
            //   NegocioP.salvar(pessoa);
            NegociosEstaticos.getNegocioMaterial().salvar(material, alterar);
            Parent root;
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            // NegocioP = null;
            alterar = null;

            aviso.alerta(AlertType.INFORMATION, ler.getMessage("msg.cadastro.confirmacao"), ler.getMessage("msg.cadastro.sucesso"));

            if (adicionar != null) {
                adicionar.close();
                adicionar = null;
                EntradaMaterialController.materalAdd=material;
                return;
            }

            root = FXMLLoader.load(ConsultarMaterialController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Material.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
        }
    }

    @FXML
    void btnSalvar_OnAction(ActionEvent event) {
        if (verificaCampoObrigatorio()) {

            try {
                if (alterar != null) {
                    salvar(alterar);
                } else {
                    Material material = new Material();
                    salvar(material);

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
            // NegocioP = null;
            alterar = null;
            if (adicionar != null) {
                adicionar.close();
                adicionar = null;
                return;
            }
            root = FXMLLoader.load(ConsultarMaterialController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Material.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void txtdescricao_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvar_OnKeyPressed(event);
        }
    }

    @FXML
    void cmbCategoria_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvar_OnKeyPressed(event);
        }
    }

    @FXML
    void cmbUnidadeMedida_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvar_OnKeyPressed(event);
        }
    }

    @FXML
    void cmbPoliticaUso_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSalvar_OnKeyPressed(event);
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
                        Material material = new Material();
                        salvar(material);

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
                // NegocioP = null;
                alterar = null;
                if (adicionar != null) {
                    adicionar.close();
                    adicionar = null;
                    return;
                }
                root = FXMLLoader.load(ConsultarMaterialController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Material.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

}
