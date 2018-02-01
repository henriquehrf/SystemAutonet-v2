/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.cadastro.Consulta;

import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import controller.cadastro.Cadastro.CadastroCategoriaController;
import gui.SystemAutonet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.Categoria;

/**
 *
 * @author Henrique
 */
public class ConsultarCategoriaController {

    @FXML
    private Button btnVoltar;

    @FXML
    private TableView<Categoria> tblPrincipal;

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
    private TableColumn<Categoria, String> tbcDescricao;

    @FXML
    private Button btnBuscar;

    void tableLoading(Boolean value) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (value) {
                    ProgressIndicator p = new ProgressIndicator();
                    p.setPrefSize(50, 50);
                    p.setStyle("-fx-progress-color:green;");
                    HBox h = new HBox(p);
                    h.setPrefSize(50, 50);
                    h.setAlignment(Pos.CENTER);
                    tblPrincipal.setPlaceholder(h);
                } else {
                    tblPrincipal.setPlaceholder(new Label("Não há conteúdo a ser exibido na tabela"));
                }
            }
        });

    }

    @FXML
    void btnVoltar_OnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnInserir_OnAction(ActionEvent event) {
        CadastroCategoriaController.setCadastrar(true);

        try {
            Parent root;
            root = FXMLLoader.load(CadastroCategoriaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Categoria.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnInserirOnKeyPressed(KeyEvent event) {
        CadastroCategoriaController.setCadastrar(true);
        if (event.getCode() == KeyCode.ENTER) {

            try {
                Parent root;
                root = FXMLLoader.load(CadastroCategoriaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Categoria.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    @FXML
    void btnAlterarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            if (tblPrincipal.getSelectionModel().getSelectedItem() == null) {
                LerMessage ler = new LerMessage();
                Alertas aviso = new Alertas();
                aviso.alerta(Alert.AlertType.WARNING, ler.getMessage("msg.warning.selecao"), ler.getMessage("msg.warning.faltaselecao"));
                return;
            }
            Categoria p = tblPrincipal.getSelectionModel().getSelectedItem();

            CadastroCategoriaController.setCadastrar(false);
            CadastroCategoriaController.setAlterar(p);
            try {
                Parent root;
                root = FXMLLoader.load(CadastroCategoriaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Categoria.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @FXML
    void btnExcluirOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            if (tblPrincipal.getSelectionModel().getSelectedItem() == null) {
                Alertas aviso = new Alertas();
                LerMessage ler = new LerMessage();
                aviso.alerta(Alert.AlertType.WARNING, ler.getMessage("msg.warning.selecao"), ler.getMessage("msg.warning.faltaselecao"));
                return;
            }

            Alertas alert = new Alertas();
            LerMessage ler = new LerMessage();
            try {

                if (alert.alerta(Alert.AlertType.CONFIRMATION, "Remoção", ler.getMessage("msg.temcerteza"), "Sim", "Não")) {
                    Categoria cat = new Categoria();
                    cat.setId_categoria(tblPrincipal.getSelectionModel().getSelectedItem().getId());
                    if (NegociosEstaticos.getNegocioMaterial().buscarPorCategoria(cat).size() == 0) {
                        NegociosEstaticos.getNegocioCategoria().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                        completarTabela(NegociosEstaticos.getNegocioCategoria().bucarTodos());
                    } else {
                        alert.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.erro.remover"), ler.getMessage("msg.remover"));
                    }

                }
            } catch (Exception ex) {
                Logger.getLogger(ConsultarDepartamentoController.class.getName()).log(Level.SEVERE, null, ex);
                alert.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.erro.remover"), ex.getMessage());
            }
        }
    }

    @FXML
    void btnVoltarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            try {
                Parent root;
                root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @FXML
    void btnBuscarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            Categoria cat = new Categoria();
            cat.setDescricao(txtBuscador.getText());
            completarTabela(NegociosEstaticos.getNegocioCategoria().buscarPorDescricao(cat));
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
        Categoria p = tblPrincipal.getSelectionModel().getSelectedItem();

        CadastroCategoriaController.setCadastrar(false);
        CadastroCategoriaController.setAlterar(p);
        try {
            Parent root;
            root = FXMLLoader.load(CadastroCategoriaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Categoria.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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

        Alertas alert = new Alertas();
        LerMessage ler = new LerMessage();
        try {

            if (alert.alerta(Alert.AlertType.CONFIRMATION, "Remoção", ler.getMessage("msg.temcerteza"), "Sim", "Não")) {
                Categoria cat = new Categoria();
                cat.setId_categoria(tblPrincipal.getSelectionModel().getSelectedItem().getId());
                if (NegociosEstaticos.getNegocioMaterial().buscarPorCategoria(cat).size() == 0) {
                    NegociosEstaticos.getNegocioCategoria().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                    completarTabela(NegociosEstaticos.getNegocioCategoria().bucarTodos());
                } else {
                    alert.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.erro.remover"), ler.getMessage("msg.remover"));
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(ConsultarDepartamentoController.class.getName()).log(Level.SEVERE, null, ex);
            alert.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.erro.remover"), ex.getMessage());
        }
    }

    void buscar() {
        new Thread() {
            @Override
            public void run() {
                Categoria cat = new Categoria();
                cat.setDescricao(txtBuscador.getText());
                completarTabela(NegociosEstaticos.getNegocioCategoria().buscarPorDescricao(cat));
            }
        }.start();

    }

    @FXML
    void btnBuscar_OnAction(ActionEvent event) {
        buscar();
    }

    @FXML
    void btnBuscar_OnActionKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buscar();
        }

    }

    @FXML
    void tblPrincipalOnMouseClicked(MouseEvent event) {
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
                }
            });
            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    btnExcluir_OnAction(e);
                }
            });
            cm.show(Title, event.getScreenX(), event.getScreenY());

        }
    }

    @FXML
    void txtBuscadorOnKeyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            btnBuscar_OnActionKey(event);
        }
    }

    public void initialize() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtBuscador.requestFocus();
                tableLoading(true);
            }
        });
        new Thread() {
            @Override
            public void run() {
                List<Categoria> lista = NegociosEstaticos.getNegocioCategoria().bucarTodos();
                completarTabela(lista);
            }
        }.start();

    }

    private void completarTabela(List<Categoria> lista) {

        if (lista.size() == 0) {
            tableLoading(false);
        } else {
            tableLoading(true);
        }

        ObservableList<Categoria> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }
        this.tbcDescricao.setCellValueFactory(new PropertyValueFactory<Categoria, String>("descricao"));

        Comparator<Categoria> cmp = new Comparator<Categoria>() {
            @Override
            public int compare(Categoria cat1, Categoria cat2) {
                return cat1.getDescricao().compareTo(cat2.getDescricao());

            }
        };
        Collections.sort(dado, cmp);
        this.tblPrincipal.setItems(dado);
        tblPrincipal.refresh();

    }

}
