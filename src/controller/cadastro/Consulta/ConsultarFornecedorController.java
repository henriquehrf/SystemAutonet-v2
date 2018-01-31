package controller.cadastro.Consulta;

import classesAuxiliares.NegociosEstaticos;
import classesAuxiliares.Validar;
import controller.PrincipalController;
import controller.cadastro.Cadastro.CadastroFornecedorController;
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
import vo.Fornecedor;

public class ConsultarFornecedorController {

    @FXML
    private TableView<Fornecedor> tblPrincipal;

    @FXML
    private Button btnInserir;

    @FXML
    private Label title;

    @FXML
    private TextField txtBuscador;

    @FXML
    private TableColumn<Fornecedor, String> tbcRazaoSocial;

    @FXML
    private RadioButton rdbRazaoSocial;

    @FXML
    private TableColumn<Fornecedor, String> tbcPessoaResponsavel;

    @FXML
    private Button btnVoltar;

    @FXML
    private RadioButton rdbNomeFantasia;

    @FXML
    private Button btnAlterar;

    @FXML
    private TableColumn<Fornecedor, String> tbcNomeFantasia;

    @FXML
    private TableColumn<Fornecedor, String> tbcCNPJ;

    @FXML
    private RadioButton rdbCNPJ;

    @FXML
    private RadioButton rdbPessoaResponsavel;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnBuscar;

    //  private NegocioFornecedor negocioF;
    public void initialize() {
        // negocioF = new NegocioFornecedor();

        rdbNomeFantasia.setSelected(true);
        List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarTodos();
        completarTabela(lista);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtBuscador.requestFocus();
            }
        });

    }

    void completarTabela(List<Fornecedor> lista) {

        ObservableList<Fornecedor> dado = FXCollections.observableArrayList();

        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }

        if (rdbNomeFantasia.isSelected()) {

            Comparator<Fornecedor> cmp = new Comparator<Fornecedor>() {
                @Override
                public int compare(Fornecedor for1, Fornecedor for2) {
                    return for1.getNome_fantasia().compareTo(for2.getNome_fantasia());
                }

            };
            Collections.sort(dado, cmp);
        }
        if (rdbPessoaResponsavel.isSelected()) {

            Comparator<Fornecedor> cmp = new Comparator<Fornecedor>() {
                @Override
                public int compare(Fornecedor for1, Fornecedor for2) {
                    return for1.getPessoa_responsavel().compareTo(for2.getPessoa_responsavel());
                }

            };
            Collections.sort(dado, cmp);
        }
        if (rdbRazaoSocial.isSelected()) {

            Comparator<Fornecedor> cmp = new Comparator<Fornecedor>() {
                @Override
                public int compare(Fornecedor for1, Fornecedor for2) {
                    return for1.getRazao_social().compareTo(for2.getRazao_social());
                }

            };
            Collections.sort(dado, cmp);
        }
        if (rdbCNPJ.isSelected()) {
            if (rdbPessoaResponsavel.isSelected()) {

                Comparator<Fornecedor> cmp = new Comparator<Fornecedor>() {
                    @Override
                    public int compare(Fornecedor for1, Fornecedor for2) {
                        return for1.getCnpj().compareTo(for2.getCnpj());
                    }

                };
                Collections.sort(dado, cmp);
            }
        }
        this.tbcNomeFantasia.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("nome_fantasia"));
        this.tbcCNPJ.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("cnpj"));
        this.tbcPessoaResponsavel.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("pessoa_responsavel"));
        this.tbcRazaoSocial.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("razao_social"));
        this.tblPrincipal.setItems(dado);
    }

    @FXML
    void btnVoltar_OnAction(ActionEvent event) {
        try {
            //  negocioF = null;
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
            CadastroFornecedorController.setCadastrar(true);
            //  negocioF = null;
            Parent root;
            root = FXMLLoader.load(CadastroFornecedorController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Fornecedor.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
        Fornecedor f = tblPrincipal.getSelectionModel().getSelectedItem();

        CadastroFornecedorController.setCadastrar(false);
        CadastroFornecedorController.setAlterar(f);

        try {
            // negocioF = null;
            Parent root;
            root = FXMLLoader.load(CadastroFornecedorController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Fornecedor.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
                NegociosEstaticos.getNegocioFornecedor().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                completarTabela(NegociosEstaticos.getNegocioFornecedor().buscarTodos());
            }
        } catch (Exception ex) {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Erro na remoção", ex.getMessage());
        }
    }

    @FXML
    void btnBuscar_OnAction(ActionEvent event) {
        if (rdbCNPJ.isSelected()) {

            char buscar[] = txtBuscador.getText().toCharArray();

            if (Validar.isDigit(buscar)) {
                Fornecedor f = new Fornecedor();
                f.setCnpj(txtBuscador.getText());
                List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorCnpj(f);
                completarTabela(lista);
            } else {
                try {
                    IncompatibilidadeNumero();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }
        if (rdbNomeFantasia.isSelected()) {
            Fornecedor f = new Fornecedor();
            f.setNome_fantasia(txtBuscador.getText());
            List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorNomeFantasia(f);
            completarTabela(lista);

        }
        if (rdbPessoaResponsavel.isSelected()) {
            Fornecedor f = new Fornecedor();
            f.setPessoa_responsavel(txtBuscador.getText());
            List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorPessoaResponsavel(f);
            completarTabela(lista);

        }
        if (rdbRazaoSocial.isSelected()) {
            Fornecedor f = new Fornecedor();
            f.setRazao_social(txtBuscador.getText());
            List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorRazaoSocial(f);
            completarTabela(lista);
        }
    }

    private void IncompatibilidadeNumero() throws Exception {
        LerMessage ler = new LerMessage();
        Alertas aviso = new Alertas();
        aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.dados.erro"), ler.getMessage("msg.incompatibilidade.numero"));

    }

    @FXML
    void txtBuscadorOnKeyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            if (rdbCNPJ.isSelected()) {

                char buscar[] = txtBuscador.getText().toCharArray();

                if (Validar.isDigit(buscar)) {
                    Fornecedor f = new Fornecedor();
                    f.setCnpj(txtBuscador.getText());
                    List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorCnpj(f);
                    completarTabela(lista);
                } else {
                    try {
                        IncompatibilidadeNumero();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

            }
            if (rdbNomeFantasia.isSelected()) {
                Fornecedor f = new Fornecedor();
                f.setNome_fantasia(txtBuscador.getText());
                List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorNomeFantasia(f);
                completarTabela(lista);

            }
            if (rdbPessoaResponsavel.isSelected()) {
                Fornecedor f = new Fornecedor();
                f.setPessoa_responsavel(txtBuscador.getText());
                List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorPessoaResponsavel(f);
                completarTabela(lista);

            }
            if (rdbRazaoSocial.isSelected()) {
                Fornecedor f = new Fornecedor();
                f.setRazao_social(txtBuscador.getText());
                List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorRazaoSocial(f);
                completarTabela(lista);
            }
        }

    }

    @FXML
    void btnBuscarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (rdbCNPJ.isSelected()) {

                char buscar[] = txtBuscador.getText().toCharArray();

                if (Validar.isDigit(buscar)) {
                    Fornecedor f = new Fornecedor();
                    f.setCnpj(txtBuscador.getText());
                    List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorCnpj(f);
                    completarTabela(lista);
                } else {
                    try {
                        IncompatibilidadeNumero();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

            }
            if (rdbNomeFantasia.isSelected()) {
                Fornecedor f = new Fornecedor();
                f.setNome_fantasia(txtBuscador.getText());
                List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorNomeFantasia(f);
                completarTabela(lista);

            }
            if (rdbPessoaResponsavel.isSelected()) {
                Fornecedor f = new Fornecedor();
                f.setPessoa_responsavel(txtBuscador.getText());
                List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorPessoaResponsavel(f);
                completarTabela(lista);

            }
            if (rdbRazaoSocial.isSelected()) {
                Fornecedor f = new Fornecedor();
                f.setRazao_social(txtBuscador.getText());
                List<Fornecedor> lista = NegociosEstaticos.getNegocioFornecedor().buscarPorRazaoSocial(f);
                completarTabela(lista);
            }
        }

    }

    @FXML
    void rdbNomeFantasiaOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            rdbNomeFantasia.setSelected(true);
            btnBuscarOnKeyPressed(event);

        }

    }

    @FXML
    void rdbRazaoSocialOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            rdbRazaoSocial.setSelected(true);
            btnBuscarOnKeyPressed(event);
        }

    }

    @FXML
    void rdbCNPJOnKeyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            rdbCNPJ.setSelected(true);
            btnBuscarOnKeyPressed(event);
        }

    }

    @FXML
    void rdbPessoaResponsavelOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            rdbPessoaResponsavel.setSelected(true);
            btnBuscarOnKeyPressed(event);
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
            cm.show(title, event.getScreenX(), event.getScreenY());
        }
    }

    @FXML
    void rdbNomeFantasiaOnAction(ActionEvent event) {
        btnBuscar_OnAction(event);

    }

    @FXML
    void rdbRazaoSocialOnAction(ActionEvent event) {
        btnBuscar_OnAction(event);

    }

    @FXML
    void rdbCNPJOnAction(ActionEvent event) {
        btnBuscar_OnAction(event);

    }

    @FXML
    void rdbPessoaResponsavelOnAction(ActionEvent event) {
        btnBuscar_OnAction(event);

    }

    @FXML
    void btnInserirOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                CadastroFornecedorController.setCadastrar(true);
                //  negocioF = null;
                Parent root;
                root = FXMLLoader.load(CadastroFornecedorController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Fornecedor.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
            Fornecedor f = tblPrincipal.getSelectionModel().getSelectedItem();

            CadastroFornecedorController.setCadastrar(false);
            CadastroFornecedorController.setAlterar(f);

            try {
                // negocioF = null;
                Parent root;
                root = FXMLLoader.load(CadastroFornecedorController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Fornecedor.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
                    NegociosEstaticos.getNegocioFornecedor().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                    completarTabela(NegociosEstaticos.getNegocioFornecedor().buscarTodos());
                }
            } catch (Exception ex) {
                Alertas alert = new Alertas();
                alert.alerta(Alert.AlertType.ERROR, "Erro na remoção", ex.getMessage());
            }
        }

    }

    @FXML
    void btnVoltarOnKeyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            try {
                //  negocioF = null;
                Parent root;
                root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

}
