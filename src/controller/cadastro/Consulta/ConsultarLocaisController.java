package controller.cadastro.Consulta;

import classesAuxiliares.NegociosEstaticos;
import classesAuxiliares.Validar;
import controller.PrincipalController;
import controller.cadastro.Cadastro.CadastroSalaBlocoController;
import gui.SystemAutonet;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
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
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.Departamento;
import vo.Local;

public class ConsultarLocaisController {

    @FXML
    private RadioButton rdbNumero;

    @FXML
    private RadioButton rdbBloco;

    @FXML
    private TableView<Local> tblPrincipal;

    @FXML
    private Button btnInserir;

    @FXML
    private Label Title;

    @FXML
    private TableColumn<Local, Integer> tbcNumero;

    @FXML
    private TextField txtBuscador;

    @FXML
    private TableColumn<Local, String> tbcPessoaResponsavel;

    @FXML
    private TableColumn<Local, String> tbcBloco;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnAlterar;

    @FXML
    private RadioButton rdbPessoaResponsavel;

    @FXML
    private Button btnExcluir;

    @FXML
    private RadioButton rdbDescricao;

    @FXML
    private TableColumn<Local, String> tbcDescricao;

    @FXML
    private Button btnBuscar;

    @FXML
    private ComboBox<String> cbmDepartamento;

    @FXML
    private TableColumn<Local, String> tbcDepartamento;

    //  private NegocioLocal negocioLocal;
    List<Local> lista = NegociosEstaticos.getNegocioLocal().buscarTodos();
    List<Departamento> aux = NegociosEstaticos.getNegocioDepartamento().buscarTodos();

    public void initialize() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtBuscador.requestFocus();
            }
        });

        //  negocioLocal = new NegocioLocal();
        rdbDescricao.setSelected(true);
        completarCombo(aux);
        completarTabela(lista);

    }

    @FXML
    void rdbDescricao_OnAction(ActionEvent event) {
        // List<Local> lista = NegociosEstaticos.getNegocioLocal().buscarTodos();

        completarTabela(tblPrincipal.getItems());
    }

    @FXML
    void rdbDescricao_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            completarTabela(tblPrincipal.getItems());
        }
    }

    @FXML
    void rdbNumero_OnAction(ActionEvent event) {
        completarTabela(tblPrincipal.getItems());
    }

    @FXML
    void rdbNumero_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            completarTabela(tblPrincipal.getItems());
        }
    }

    @FXML
    void rdbPessoaResponsavel_OnAction(ActionEvent event) {
        completarTabela(tblPrincipal.getItems());
    }

    @FXML
    void rdbPessoaResponsavel_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            completarTabela(tblPrincipal.getItems());
        }

    }

    void completarCombo(List<Departamento> lista) {
        ObservableList<String> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i).getSigla());
        }
        dado.add("TODOS");
        Collections.sort(dado);
        cbmDepartamento.setItems(dado);
        cbmDepartamento.setValue("TODOS");
    }

    @FXML
    void btnVoltar_OnAction(ActionEvent event) {
        try {
            Parent root;
            //  negocioLocal = null;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void btnInserir_OnAction(ActionEvent event) {
        try {
            CadastroSalaBlocoController.setCadastrar(true);
            Parent root;
            //  negocioLocal = null;
            root = FXMLLoader.load(CadastroSalaBlocoController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_SalaBloco.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
        Local p = tblPrincipal.getSelectionModel().getSelectedItem();
        try {
            CadastroSalaBlocoController.setCadastrar(false);
            CadastroSalaBlocoController.setAlterar(p);
            Parent root;
            //   negocioLocal = null;
            root = FXMLLoader.load(CadastroSalaBlocoController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_SalaBloco.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
                NegociosEstaticos.getNegocioLocal().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                completarTabela(NegociosEstaticos.getNegocioLocal().buscarTodos());
            }
        } catch (Exception ex) {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Erro na remoção", ex.getMessage());
        }
    }

    @FXML
    void btnBuscar_OnAction(ActionEvent event) {
//        if (rdbBloco.isSelected()) {
//            Local local = new Local();
////            local.setBloco(txtBuscador.getText());
//            completarTabela(NegociosEstaticos.getNegocioLocal().buscarPorBloco(local));
//        }

        if (!cbmDepartamento.getValue().equals("TODOS")) {

            if (rdbNumero.isSelected()) {
                Local local = new Local();
                char buscar[] = txtBuscador.getText().toCharArray();

                if (Validar.isDigit(buscar)) {
                    if (txtBuscador.getText().isEmpty()) {
                        listarDepartamento(NegociosEstaticos.getNegocioLocal().buscarTodos());
                    } else {

                        local.setNumero(Integer.parseInt(txtBuscador.getText()));
                        listarDepartamento(NegociosEstaticos.getNegocioLocal().buscarPorNumero(local));
                    }
                } else {
                    try {
                        LerMessage ler = new LerMessage();
                        Alertas aviso = new Alertas();
                        aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.incompatibilidade.numero"), ler.getMessage("msg.incompatibilidade.numero"));
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            if (rdbPessoaResponsavel.isSelected()) {
                Local local = new Local();
                local.setResponsavel(txtBuscador.getText());
                listarDepartamento(NegociosEstaticos.getNegocioLocal().buscarPorPessoaResponsavel(local));
            }
            if (rdbDescricao.isSelected()) {
                Local local = new Local();
                local.setDescricao(txtBuscador.getText());
                listarDepartamento(NegociosEstaticos.getNegocioLocal().buscarPorDescricao(local));
            }
        } else {
            if (rdbNumero.isSelected()) {
                Local local = new Local();
                char buscar[] = txtBuscador.getText().toCharArray();

                if (Validar.isDigit(buscar)) {
                    if (txtBuscador.getText().isEmpty()) {
                        completarTabela(NegociosEstaticos.getNegocioLocal().buscarTodos());
                    } else {

                        local.setNumero(Integer.parseInt(txtBuscador.getText()));
                        completarTabela(NegociosEstaticos.getNegocioLocal().buscarPorNumero(local));
                    }
                } else {
                    try {
                        LerMessage ler = new LerMessage();
                        Alertas aviso = new Alertas();
                        aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.incompatibilidade.numero"), ler.getMessage("msg.incompatibilidade.numero"));
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            if (rdbPessoaResponsavel.isSelected()) {
                Local local = new Local();
                local.setResponsavel(txtBuscador.getText());
                completarTabela(NegociosEstaticos.getNegocioLocal().buscarPorPessoaResponsavel(local));
            }
            if (rdbDescricao.isSelected()) {
                Local local = new Local();
                local.setDescricao(txtBuscador.getText());
                completarTabela(NegociosEstaticos.getNegocioLocal().buscarPorDescricao(local));
            }
        }
    }

    @FXML
    void btnBuscar_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            if (!cbmDepartamento.getValue().equals("TODOS")) {

                if (rdbNumero.isSelected()) {
                    Local local = new Local();
                    char buscar[] = txtBuscador.getText().toCharArray();

                    if (Validar.isDigit(buscar)) {
                        if (txtBuscador.getText().isEmpty()) {
                            listarDepartamento(NegociosEstaticos.getNegocioLocal().buscarTodos());
                        } else {

                            local.setNumero(Integer.parseInt(txtBuscador.getText()));
                            listarDepartamento(NegociosEstaticos.getNegocioLocal().buscarPorNumero(local));
                        }
                    } else {
                        try {
                            LerMessage ler = new LerMessage();
                            Alertas aviso = new Alertas();
                            aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.incompatibilidade.numero"), ler.getMessage("msg.incompatibilidade.numero"));
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                if (rdbPessoaResponsavel.isSelected()) {
                    Local local = new Local();
                    local.setResponsavel(txtBuscador.getText());
                    listarDepartamento(NegociosEstaticos.getNegocioLocal().buscarPorPessoaResponsavel(local));
                }
                if (rdbDescricao.isSelected()) {
                    Local local = new Local();
                    local.setDescricao(txtBuscador.getText());
                    listarDepartamento(NegociosEstaticos.getNegocioLocal().buscarPorDescricao(local));
                }
            } else {
                if (rdbNumero.isSelected()) {
                    Local local = new Local();
                    char buscar[] = txtBuscador.getText().toCharArray();

                    if (Validar.isDigit(buscar)) {
                        if (txtBuscador.getText().isEmpty()) {
                            completarTabela(NegociosEstaticos.getNegocioLocal().buscarTodos());
                        } else {

                            local.setNumero(Integer.parseInt(txtBuscador.getText()));
                            completarTabela(NegociosEstaticos.getNegocioLocal().buscarPorNumero(local));
                        }
                    } else {
                        try {
                            LerMessage ler = new LerMessage();
                            Alertas aviso = new Alertas();
                            aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.incompatibilidade.numero"), ler.getMessage("msg.incompatibilidade.numero"));
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                if (rdbPessoaResponsavel.isSelected()) {
                    Local local = new Local();
                    local.setResponsavel(txtBuscador.getText());
                    completarTabela(NegociosEstaticos.getNegocioLocal().buscarPorPessoaResponsavel(local));
                }
                if (rdbDescricao.isSelected()) {
                    Local local = new Local();
                    local.setDescricao(txtBuscador.getText());
                    completarTabela(NegociosEstaticos.getNegocioLocal().buscarPorDescricao(local));
                }
            }

        }
    }

    @FXML
    void txtBuscador_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnBuscar_OnKeyPressed(event);
        }
    }

    void completarTabela(List<Local> lista) {
        ObservableList<Local> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }

        if (rdbDescricao.isSelected()) {
            Comparator<Local> cmp = new Comparator<Local>() {
                @Override
                public int compare(Local loc1, Local loc2) {
                    int x = loc1.getDescricao().compareTo(loc2.getDescricao());
                    if (x != 0) {
                        return x;
                    } else {
                        Integer x1 = ((Local) loc1).getNumero();
                        Integer x2 = ((Local) loc2).getNumero();
                        return x1.compareTo(x2);
                    }
                }
            };

            Collections.sort(dado, cmp);
        }
        if (rdbNumero.isSelected()) {
            Comparator<Local> cmp = new Comparator<Local>() {
                @Override
                public int compare(Local num1, Local num2) {
                    Integer x1 = ((Local) num1).getNumero();
                    Integer x2 = ((Local) num2).getNumero();

                    return x1.compareTo(x2);
                }
            };

            Collections.sort(dado, cmp);
        }

        if (rdbPessoaResponsavel.isSelected()) {
            Comparator<Local> cmp = new Comparator<Local>() {
                @Override
                public int compare(Local loc1, Local loc2) {
                    return loc1.getResponsavel().compareTo(loc2.getResponsavel());
                }
            };
            Collections.sort(dado, cmp);
        }
        this.tbcDescricao.setCellValueFactory(new PropertyValueFactory<Local, String>("descricao"));
        this.tbcNumero.setCellValueFactory(new PropertyValueFactory<Local, Integer>("numero"));
        this.tbcPessoaResponsavel.setCellValueFactory(new PropertyValueFactory<Local, String>("responsavel"));
        this.tbcDepartamento.setCellValueFactory(new PropertyValueFactory<Local, String>("Sigla"));

        this.tblPrincipal.setItems(dado);

    }

    @FXML
    void cbmDepartamento_OnAction(ActionEvent event) {
        if (cbmDepartamento.getValue().equals("TODOS")) {
            btnBuscar_OnAction(event);
        } else {
            btnBuscar_OnAction(event);
            listarDepartamento(tblPrincipal.getItems());
        }
    }

    void listarDepartamento(List<Local> filter) {
        List<Local> aux = new ArrayList<Local>();
        for (int i = 0; i < filter.size(); i++) {
            if (filter.get(i).getSigla().contains(cbmDepartamento.getValue())) {
                aux.add(filter.get(i));
            }
        }
        completarTabela(aux);

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
    void btnInserirOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                CadastroSalaBlocoController.setCadastrar(true);
                Parent root;
                //  negocioLocal = null;
                root = FXMLLoader.load(CadastroSalaBlocoController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_SalaBloco.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
            Local p = tblPrincipal.getSelectionModel().getSelectedItem();
            try {
                CadastroSalaBlocoController.setCadastrar(false);
                CadastroSalaBlocoController.setAlterar(p);
                Parent root;
                //   negocioLocal = null;
                root = FXMLLoader.load(CadastroSalaBlocoController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_SalaBloco.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
            try {
                Alertas alert = new Alertas();
                LerMessage ler = new LerMessage();
                if (alert.alerta(Alert.AlertType.CONFIRMATION, "Remoção", ler.getMessage("msg.temcerteza"), "Sim", "Não")) {
                //    if(NegociosEstaticos.getNegocioEstoqueMateria().g)
                    NegociosEstaticos.getNegocioLocal().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                    completarTabela(NegociosEstaticos.getNegocioLocal().buscarTodos());
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
                Parent root;
                //  negocioLocal = null;
                root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

        }

    }
}
