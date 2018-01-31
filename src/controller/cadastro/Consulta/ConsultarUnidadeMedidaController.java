package controller.cadastro.Consulta;

import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import controller.cadastro.Cadastro.CadastroUnidadeMedidaController;
import gui.SystemAutonet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import negocio.NegocioTipoUnidade;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.TipoUnidade;

public class ConsultarUnidadeMedidaController {

    @FXML
    private Button btnVoltar;

    @FXML
    private TableView<TipoUnidade> tblPrincipal;

    @FXML
    private RadioButton rdbsigla;

    @FXML
    private RadioButton rdbdescricao;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnInserir;

    @FXML
    private Label Title;

    @FXML
    private Button btnExcluir;

    @FXML
    private TableColumn<TipoUnidade, String> tbcSigla;

    @FXML
    private TextField txtBuscador;

    @FXML
    private TableColumn<TipoUnidade, String> tbcDescricao;

    //private NegocioTipoUnidade negocioTU;
    @FXML
    private Button btnBuscar;

    public void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                txtBuscador.requestFocus();
            }
        });
        // negocioTU = new NegocioTipoUnidade();
        List<TipoUnidade> lista = NegociosEstaticos.getNegocioTipoUnidade().buscarTodos();
        completarTabela(lista);
        rdbdescricao.setSelected(true);
    }

    @FXML
    void btnVoltar_OnAction(ActionEvent event) {
        try {
            //  negocioTU = null;
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void btnInserir_OnAction(ActionEvent event) {
        try {
            CadastroUnidadeMedidaController.setCadastrar(true);
            Parent root;
            // negocioTU = null;
            root = FXMLLoader.load(CadastroUnidadeMedidaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_UnidadeMedida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void btnAlterar_OnAction(ActionEvent event) {
        if (tblPrincipal.getSelectionModel().getSelectedItem() == null) {
            Alertas aviso = new Alertas();
            LerMessage ler = new LerMessage();
            aviso.alerta(Alert.AlertType.WARNING, ler.getMessage("msg.warning.selecao"), ler.getMessage("msg.warning.faltaselecao"));
            return;

        }
        TipoUnidade p = tblPrincipal.getSelectionModel().getSelectedItem();
        try {
            CadastroUnidadeMedidaController.setCadastrar(false);
            CadastroUnidadeMedidaController.setAlterar(p);
            Parent root;
            // negocioTU = null;
            root = FXMLLoader.load(CadastroUnidadeMedidaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_UnidadeMedida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void btnExcluir_OnAction(ActionEvent event) {
        if (tblPrincipal.getSelectionModel().getSelectedItem() == null) {
            Alertas aviso = new Alertas();
            LerMessage ler = new LerMessage();
            aviso.alerta(Alert.AlertType.WARNING, ler.getMessage("msg.warning.selecao"), ler.getMessage("msg.warning.faltaselecao"));
            return;

        }

        try {
            Alertas alert = new Alertas();
            LerMessage ler = new LerMessage();
            if (alert.alerta(Alert.AlertType.CONFIRMATION, "Remoção", ler.getMessage("msg.temcerteza"), "Sim", "Não")) {
                NegociosEstaticos.getNegocioTipoUnidade().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                completarTabela(NegociosEstaticos.getNegocioTipoUnidade().buscarTodos());
            }
        } catch (Exception ex) {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Erro na remoção", ex.getMessage());
        }
    }

    @FXML
    void btnBuscar_OnAction(ActionEvent event) {

        if (rdbdescricao.isSelected()) {
            TipoUnidade tu = new TipoUnidade();
            tu.setDescricao(txtBuscador.getText());
            completarTabela(NegociosEstaticos.getNegocioTipoUnidade().buscarPorDescricao(tu));
        }
        if (rdbsigla.isSelected()) {
            TipoUnidade tu = new TipoUnidade();
            tu.setSigla(txtBuscador.getText());
            completarTabela(NegociosEstaticos.getNegocioTipoUnidade().buscarPorSigla(tu));
        }
    }

    private void completarTabela(List<TipoUnidade> lista) {
        ObservableList<TipoUnidade> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }
        this.tbcDescricao.setCellValueFactory(new PropertyValueFactory<TipoUnidade, String>("descricao"));
        this.tbcSigla.setCellValueFactory(new PropertyValueFactory<TipoUnidade, String>("sigla"));

        Comparator<TipoUnidade> cmp = new Comparator<TipoUnidade>() {
            @Override
            public int compare(TipoUnidade t1, TipoUnidade t2) {
                return t1.getDescricao().compareToIgnoreCase(t2.getDescricao());

            }
        };
        Collections.sort(dado, cmp);
        this.tblPrincipal.setItems(dado);

    }

    @FXML
    void txtBuscador_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnBuscar_OnKeyPressed(event);
        }

    }

    @FXML
    void btnBuscar_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (rdbdescricao.isSelected()) {
                TipoUnidade tu = new TipoUnidade();
                tu.setDescricao(txtBuscador.getText());
                completarTabela(NegociosEstaticos.getNegocioTipoUnidade().buscarPorDescricao(tu));
            }
            if (rdbsigla.isSelected()) {
                TipoUnidade tu = new TipoUnidade();
                tu.setSigla(txtBuscador.getText());
                completarTabela(NegociosEstaticos.getNegocioTipoUnidade().buscarPorSigla(tu));
            }
        }
    }

    @FXML
    void btnInserir_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                CadastroUnidadeMedidaController.setCadastrar(true);
                Parent root;
                // negocioTU = null;
                root = FXMLLoader.load(CadastroUnidadeMedidaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_UnidadeMedida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    @FXML
    void btnAlterar_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (tblPrincipal.getSelectionModel().getSelectedItem() == null) {
                Alertas aviso = new Alertas();
                LerMessage ler = new LerMessage();
                aviso.alerta(Alert.AlertType.WARNING, ler.getMessage("msg.warning.selecao"), ler.getMessage("msg.warning.faltaselecao"));
                return;

            }
            TipoUnidade p = tblPrincipal.getSelectionModel().getSelectedItem();
            try {
                CadastroUnidadeMedidaController.setCadastrar(false);
                CadastroUnidadeMedidaController.setAlterar(p);
                Parent root;
                // negocioTU = null;
                root = FXMLLoader.load(CadastroUnidadeMedidaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_UnidadeMedida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @FXML
    void btnExcluir_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (tblPrincipal.getSelectionModel().getSelectedItem() == null) {
                Alertas aviso = new Alertas();
                LerMessage ler = new LerMessage();
                aviso.alerta(Alert.AlertType.WARNING, ler.getMessage("msg.warning.selecao"), ler.getMessage("msg.warning.faltaselecao"));
                return;

            }

            try {
                Alertas alert = new Alertas();
                LerMessage ler = new LerMessage();
                if (alert.alerta(Alert.AlertType.CONFIRMATION, "Remoção", ler.getMessage("msg.temcerteza"), "Sim", "Não")) {
                    NegociosEstaticos.getNegocioTipoUnidade().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                    completarTabela(NegociosEstaticos.getNegocioTipoUnidade().buscarTodos());
                }
            } catch (Exception ex) {
                Alertas alert = new Alertas();
                alert.alerta(Alert.AlertType.ERROR, "Erro na remoção", ex.getMessage());
            }
        }
    }

    @FXML
    void btnVoltar_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                //  negocioTU = null;
                Parent root;
                root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @FXML
    void tblPrincipal_OnMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            if (event.getButton() == MouseButton.SECONDARY) {
                final ContextMenu cm = new ContextMenu();
                LerMessage ler = new LerMessage();
                MenuItem cmItem1 = new MenuItem(ler.getMessage("btn.alterar"));
                MenuItem cmItem2 = new MenuItem(ler.getMessage("btn.excluir"));

                cm.getItems().add(cmItem1);
                cm.getItems().add(cmItem2);

                cmItem1.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        btnAlterar_OnAction(e);
                        // btnAlterarOnAction(e);
                    }
                });
                cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        btnExcluir_OnAction(e);
                        //  btnExcluirOnAction(e);
                    }
                });
                cm.show(Title, event.getScreenX(), event.getScreenY());
            }
        }
    }
}
