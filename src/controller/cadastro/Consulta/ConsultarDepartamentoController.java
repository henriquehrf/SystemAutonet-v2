package controller.cadastro.Consulta;

import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import controller.cadastro.Cadastro.CadastroDepartamentoController;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.Departamento;
import vo.Local;

public class ConsultarDepartamentoController {

    @FXML
    private RadioButton rdbSigla;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableView<Departamento> tblPrincipal;

    @FXML
    private Button btnAlterar;

    @FXML
    private TableColumn<Departamento, String> tbcNome;

    @FXML
    private Button btnInserir;

    @FXML
    private Label Title;

    @FXML
    private Button btnExcluir;

    @FXML
    private TableColumn<Departamento, String> tbcSigla;

    @FXML
    private RadioButton rdbNome;

    @FXML
    private TextField txtBuscador;

    @FXML
    private Button btnBuscar;

    @FXML
    private ToggleGroup FIltro;

    // private NegocioDepartamento negocioD;
    public void initialize() {
        //  negocioD = new NegocioDepartamento();
        List<Departamento> lista = NegociosEstaticos.getNegocioDepartamento().buscarTodos();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtBuscador.requestFocus();
            }
        });
        rdbNome.setSelected(true);
        completarTabela(lista);

    }

    void completarTabela(List<Departamento> lista) {
        ObservableList<Departamento> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }
        this.tbcNome.setCellValueFactory(new PropertyValueFactory<Departamento, String>("nome"));
        this.tbcSigla.setCellValueFactory(new PropertyValueFactory<Departamento, String>("sigla"));

        if (rdbNome.isSelected()) {
            Comparator<Departamento> cmp = new Comparator<Departamento>() {
                @Override
                public int compare(Departamento dep1, Departamento dep2) {
                    return dep1.getNome().compareTo(dep2.getNome());

                }
            };
            Collections.sort(dado, cmp);
        }
        if (rdbSigla.isSelected()) {
            Comparator<Departamento> cmp = new Comparator<Departamento>() {
                @Override
                public int compare(Departamento dep1, Departamento dep2) {
                    return dep1.getSigla().compareTo(dep2.getSigla());

                }
            };
            Collections.sort(dado, cmp);

        }
        this.tblPrincipal.setItems(dado);
    }

    @FXML
    void btnInserir_OnAction(ActionEvent event) {
        try {
            //  negocioD = null;
            CadastroDepartamentoController.setCadastrar(true);
            Parent root;
            root = FXMLLoader.load(CadastroDepartamentoController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Departamento.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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

        Departamento p = tblPrincipal.getSelectionModel().getSelectedItem();
        CadastroDepartamentoController.setCadastrar(false);
        CadastroDepartamentoController.setAlterar(p);
        try {
            //negocioD = null;
            Parent root;
            root = FXMLLoader.load(CadastroDepartamentoController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Departamento.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
                Local aux = new Local();
                aux.setId_departamento(tblPrincipal.getSelectionModel().getSelectedItem());
                if (NegociosEstaticos.getNegocioLocal().buscarPorDepartamento(aux).size()==0) {

                    NegociosEstaticos.getNegocioDepartamento().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                    completarTabela(NegociosEstaticos.getNegocioDepartamento().buscarTodos());
                } else {

                    alert.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.erro.remover"), ler.getMessage("msg.remover"));
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ConsultarDepartamentoController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Passou no ");
            alert.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.erro.remover"), ex.getMessage());
        }
    }

    @FXML
    void btnBuscar_OnAction(ActionEvent event) {
        if (rdbNome.isSelected()) {
            Departamento p = new Departamento();
            p.setNome(txtBuscador.getText());
            List<Departamento> lista = NegociosEstaticos.getNegocioDepartamento().buscarPorNome(p);
            completarTabela(lista);
        }

        if (rdbSigla.isSelected()) {
            Departamento p = new Departamento();
            p.setSigla(txtBuscador.getText());
            List<Departamento> lista = NegociosEstaticos.getNegocioDepartamento().buscarPorSigla(p);
            completarTabela(lista);
        }
    }

    @FXML
    void btnVoltar_OnAction(ActionEvent event) {
        try {
            // negocioD =  null;
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void txtBuscadorOnKeyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            if (rdbNome.isSelected()) {
                Departamento p = new Departamento();
                p.setNome(txtBuscador.getText());
                List<Departamento> lista = NegociosEstaticos.getNegocioDepartamento().buscarPorNome(p);
                completarTabela(lista);
            }

            if (rdbSigla.isSelected()) {
                Departamento p = new Departamento();
                p.setSigla(txtBuscador.getText());
                List<Departamento> lista = NegociosEstaticos.getNegocioDepartamento().buscarPorSigla(p);
                completarTabela(lista);
            }
        }

    }

    @FXML
    void btnBuscarOnKeyPressed(KeyEvent event) {
        txtBuscadorOnKeyPressed(event);

    }

    @FXML
    void rdbSiglaOnAction(ActionEvent event) {

        rdbSigla.setSelected(true);
        btnBuscar_OnAction(event);

    }

    @FXML
    void rdbNomeOnAction(ActionEvent event) {
        rdbNome.setSelected(true);
        btnBuscar_OnAction(event);
    }

    @FXML
    void rdbNomeOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            rdbNome.setSelected(true);
            btnBuscarOnKeyPressed(event);
        }
    }

    @FXML
    void rdbSiglaOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            rdbSigla.setSelected(true);
            btnBuscarOnKeyPressed(event);
        }

    }

    @FXML
    void btnInserirOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                //  negocioD = null;
                CadastroDepartamentoController.setCadastrar(true);
                Parent root;
                root = FXMLLoader.load(CadastroDepartamentoController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Departamento.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
                Alertas aviso = new Alertas();
                LerMessage ler = new LerMessage();
                aviso.alerta(Alert.AlertType.WARNING, ler.getMessage("msg.warning.selecao"), ler.getMessage("msg.warning.faltaselecao"));
                return;
            }
            Departamento p = tblPrincipal.getSelectionModel().getSelectedItem();
            CadastroDepartamentoController.setCadastrar(false);
            CadastroDepartamentoController.setAlterar(p);
            try {
                //negocioD = null;
                Parent root;
                root = FXMLLoader.load(CadastroDepartamentoController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Departamento.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
                    Local aux = new Local();
                    aux.setId_departamento(tblPrincipal.getSelectionModel().getSelectedItem());
                     if (NegociosEstaticos.getNegocioLocal().buscarPorDepartamento(aux).size()==0) {

                        NegociosEstaticos.getNegocioDepartamento().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                        completarTabela(NegociosEstaticos.getNegocioDepartamento().buscarTodos());
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
                // negocioD =  null;
                Parent root;
                root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

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
}
