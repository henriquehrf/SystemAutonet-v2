package controller.cadastro.Consulta;

import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import controller.cadastro.Cadastro.CadastroTipoSaidaController;
import gui.SystemAutonet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.TipoSaida;

public class ConsultarTipoSaidaController {

    @FXML
    private Button btnVoltar;

    @FXML
    private TableView<TipoSaida> tblPrincipal;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnInserir;

    @FXML
    private Label Title;

    @FXML
    private Button btnExcluir;

    @FXML
    private TextField txtBuscador;

    @FXML
    private TableColumn<TipoSaida, String> tbcDescricao;

    @FXML
    private Button btnBuscar;

    // private NegocioTipoSaida negocioTS;
    public void initialize() {
        // negocioTS = new NegocioTipoSaida();
        List<TipoSaida> lista = NegociosEstaticos.getNegocioTipoSaida().buscarTodos();

        completarTabela(lista);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                txtBuscador.requestFocus();
            }
        });

    }

    @FXML
    void btnVoltar_OnAction(ActionEvent event) {
        try {
            Parent root;
            // negocioTS = null;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void btnInserir_OnAction(ActionEvent event) {
        try {
            CadastroTipoSaidaController.setCadastrar(true);
            Parent root;
            // negocioTS = null;
            root = FXMLLoader.load(CadastroTipoSaidaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Tipo_Saida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
        TipoSaida p = tblPrincipal.getSelectionModel().getSelectedItem();
        try {
            CadastroTipoSaidaController.setCadastrar(false);
            CadastroTipoSaidaController.setAlterar(p);
            Parent root;
            // negocioTS = null;
            root = FXMLLoader.load(CadastroTipoSaidaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Tipo_Saida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
                NegociosEstaticos.getNegocioTipoSaida().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                completarTabela(NegociosEstaticos.getNegocioTipoSaida().buscarTodos());
            }
        } catch (Exception ex) {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Erro na remoção", ex.getMessage());
        }
    }

    @FXML
    void btnBuscar_OnAction(ActionEvent event) {

        TipoSaida tps = new TipoSaida();
        tps.setDescricao(txtBuscador.getText());
        completarTabela(NegociosEstaticos.getNegocioTipoSaida().buscarPorDescricao(tps));

    }

    private void completarTabela(List<TipoSaida> lista) {
        ObservableList<TipoSaida> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }
        this.tbcDescricao.setCellValueFactory(new PropertyValueFactory<TipoSaida, String>("descricao"));

        Comparator<TipoSaida> cmp = new Comparator<TipoSaida>() {
            @Override
            public int compare(TipoSaida t1, TipoSaida t2) {
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
            TipoSaida tps = new TipoSaida();
            tps.setDescricao(txtBuscador.getText());
            completarTabela(NegociosEstaticos.getNegocioTipoSaida().buscarPorDescricao(tps));
        }
    }

    @FXML
    void btnInserir_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                CadastroTipoSaidaController.setCadastrar(true);
                Parent root;
                // negocioTS = null;
                root = FXMLLoader.load(CadastroTipoSaidaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Tipo_Saida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
            TipoSaida p = tblPrincipal.getSelectionModel().getSelectedItem();
            try {
                CadastroTipoSaidaController.setCadastrar(false);
                CadastroTipoSaidaController.setAlterar(p);
                Parent root;
                // negocioTS = null;
                root = FXMLLoader.load(CadastroTipoSaidaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Tipo_Saida.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @FXML
    void btnExcluir_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
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
                        NegociosEstaticos.getNegocioTipoSaida().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                        completarTabela(NegociosEstaticos.getNegocioTipoSaida().buscarTodos());
                    }
                } catch (Exception ex) {
                    Alertas alert = new Alertas();
                    alert.alerta(Alert.AlertType.ERROR, "Erro na remoção", ex.getMessage());
                }
            }
        }
    }

    @FXML
    void btnVoltar_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            try {
                Parent root;
                // negocioTS = null;
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
            final ContextMenu cm = new ContextMenu();
            LerMessage ler = new LerMessage();
            MenuItem cmItem1 = new MenuItem(ler.getMessage("btn.alterar"));
            MenuItem cmItem2 = new MenuItem(ler.getMessage("btn.excluir"));

            cm.getItems().add(cmItem1);
            cm.getItems().add(cmItem2);

            cmItem1.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    //btnAlterarOnAction(e);
                    btnAlterar_OnAction(e);
                }
            });
            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    // btnExcluirOnAction(e);
                    btnExcluir_OnAction(e);
                }
            });
            cm.show(Title, event.getScreenX(), event.getScreenY());
        }
    }
}
