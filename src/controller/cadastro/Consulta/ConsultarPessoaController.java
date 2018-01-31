package controller.cadastro.Consulta;

import classesAuxiliares.NegociosEstaticos;
import classesAuxiliares.Validar;
import controller.PrincipalController;
import controller.cadastro.Cadastro.CadastroPessoaController;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.Pessoa;

public class ConsultarPessoaController {

    @FXML
    private TableColumn<Pessoa, String> tbcCPF;

    @FXML
    private TableView<Pessoa> tblPrincipal;

    @FXML
    private TableColumn<Pessoa, String> tbcNome;

    @FXML
    private Button btnInserir;

    @FXML
    private Label Title;

    @FXML
    private TextField txtBuscador;

    @FXML
    private TableColumn<Pessoa, String> tbcNumMatricula;

    @FXML
    private RadioButton rdbRG;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnAlterar;

    @FXML
    private TableColumn<Pessoa, String> tbcRG;

    @FXML
    private RadioButton rgbNumMatricula;

    @FXML
    private Button btnExcluir;

    @FXML
    private RadioButton rdbNome;

    @FXML
    private Button btnBuscar;

    @FXML
    private RadioButton rdbCPF;

    //  NegocioPessoa pessoa;
    void completarTabela(List<Pessoa> lista) {
        ObservableList<Pessoa> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }
        if (rdbNome.isSelected()) {
            Comparator<Pessoa> cmp = new Comparator<Pessoa>() {
                @Override
                public int compare(Pessoa pes1, Pessoa pes2) {
                    return pes1.getNome().compareToIgnoreCase(pes2.getNome());
                }
            };
            Collections.sort(dado, cmp);
        }
        if (rdbCPF.isSelected()) {
            Comparator<Pessoa> cmp = new Comparator<Pessoa>() {
                @Override
                public int compare(Pessoa pes1, Pessoa pes2) {
                    return pes1.getCpf().compareToIgnoreCase(pes2.getCpf());
                }
            };
            Collections.sort(dado, cmp);
        }
        if (rdbRG.isSelected()) {
            Comparator<Pessoa> cmp = new Comparator<Pessoa>() {
                @Override
                public int compare(Pessoa pes1, Pessoa pes2) {
                    return pes1.getRg().compareToIgnoreCase(pes2.getRg());
                }
            };
            Collections.sort(dado, cmp);
        }
        if (rgbNumMatricula.isSelected()) {
            Comparator<Pessoa> cmp = new Comparator<Pessoa>() {
                @Override
                public int compare(Pessoa pes1, Pessoa pes2) {
                    return pes1.getNum_matricula().compareToIgnoreCase(pes2.getNum_matricula());
                }
            };
            Collections.sort(dado, cmp);
        }

        this.tbcNome.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nome"));
        this.tbcCPF.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpf"));
        this.tbcNumMatricula.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("num_matricula"));
        this.tbcRG.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("rg"));
        this.tblPrincipal.setItems(dado);
    }

    public void initialize() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                txtBuscador.requestFocus();
            }
        });
        // pessoa = new NegocioPessoa();
        List<Pessoa> lista = NegociosEstaticos.getNegocioPessoa().buscarTodos();
        rdbNome.setSelected(true);
        completarTabela(lista);

    }

    @FXML
    void btnVoltarOnAction(ActionEvent event) {
        try {
            //   pessoa = null;
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnInserirOnAction(ActionEvent event) {
        try {
            CadastroPessoaController.setCadastrar(true);
            Parent root;
            // pessoa = null;
            root = FXMLLoader.load(CadastroPessoaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Pessoa.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnAlterarOnAction(ActionEvent event) {
        if (tblPrincipal.getSelectionModel().getSelectedItem() == null) {
            Alertas aviso = new Alertas();
            LerMessage ler = new LerMessage();
            aviso.alerta(Alert.AlertType.WARNING, ler.getMessage("msg.warning.selecao"), ler.getMessage("msg.warning.faltaselecao"));
            return;

        }

        Pessoa p = tblPrincipal.getSelectionModel().getSelectedItem();
        CadastroPessoaController.setCadastrar(false);
        CadastroPessoaController.setAlterar(p);
        try {
            Parent root;
            //  pessoa = null;
            root = FXMLLoader.load(CadastroPessoaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Pessoa.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) {
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
                NegociosEstaticos.getNegocioPessoa().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                 completarTabela(NegociosEstaticos.getNegocioPessoa().buscarTodos());
            }
        } catch (Exception ex) {

            try {
                NegociosEstaticos.getNegocioPessoa().Inativar(tblPrincipal.getSelectionModel().getSelectedItem());
                completarTabela(NegociosEstaticos.getNegocioPessoa().buscarTodos());
            } catch (Exception ex1) {
                Alertas alert = new Alertas();
                alert.alerta(Alert.AlertType.ERROR, "Erro na remoção", ex1.getMessage());

            }

        }
    }

    @FXML
    void btnBuscarOnAction(ActionEvent event) {

        if (rdbNome.isSelected()) {
            Pessoa p = new Pessoa();
            p.setNome(txtBuscador.getText());
            List<Pessoa> lista = NegociosEstaticos.getNegocioPessoa().buscarPorNome(p);
            completarTabela(lista);

        }
        if (rdbCPF.isSelected()) {
            char buscar[] = txtBuscador.getText().toCharArray();

            if (Validar.isDigit(buscar)) {
                Pessoa p = new Pessoa();
                p.setCpf(txtBuscador.getText());
                List<Pessoa> lista = NegociosEstaticos.getNegocioPessoa().buscarPorCPF(p);
                completarTabela(lista);
            } else {
                try {
                    IncompatibilidadeNumero();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }
        if (rdbRG.isSelected()) {
            char buscar[] = txtBuscador.getText().toCharArray();
            if (Validar.isDigit(buscar)) {
                Pessoa p = new Pessoa();
                p.setRg(txtBuscador.getText());
                List<Pessoa> lista = NegociosEstaticos.getNegocioPessoa().buscarPorRG(p);
                completarTabela(lista);
            } else {
                try {
                    IncompatibilidadeNumero();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }
        if (rgbNumMatricula.isSelected()) {
            char buscar[] = txtBuscador.getText().toCharArray();
            if (Validar.isDigit(buscar)) {

                Pessoa p = new Pessoa();
                p.setNum_matricula(txtBuscador.getText());
                List<Pessoa> lista = NegociosEstaticos.getNegocioPessoa().buscarPorMatricula(p);
                completarTabela(lista);

            } else {
                try {
                    IncompatibilidadeNumero();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }
    }

    private void IncompatibilidadeNumero() throws Exception {
        LerMessage ler = new LerMessage();
        Alertas aviso = new Alertas();
        aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.dados.erro"), ler.getMessage("msg.incompatibilidade.numero"));

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

            if (rdbNome.isSelected()) {
                Pessoa p = new Pessoa();
                p.setNome(txtBuscador.getText());
                List<Pessoa> lista = NegociosEstaticos.getNegocioPessoa().buscarPorNome(p);
                completarTabela(lista);

            }
            if (rdbCPF.isSelected()) {
                char buscar[] = txtBuscador.getText().toCharArray();

                if (Validar.isDigit(buscar)) {
                    Pessoa p = new Pessoa();
                    p.setCpf(txtBuscador.getText());
                    List<Pessoa> lista = NegociosEstaticos.getNegocioPessoa().buscarPorCPF(p);
                    completarTabela(lista);
                } else {
                    try {
                        IncompatibilidadeNumero();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

            }
            if (rdbRG.isSelected()) {
                char buscar[] = txtBuscador.getText().toCharArray();
                if (Validar.isDigit(buscar)) {
                    Pessoa p = new Pessoa();
                    p.setRg(txtBuscador.getText());
                    List<Pessoa> lista = NegociosEstaticos.getNegocioPessoa().buscarPorRG(p);
                    completarTabela(lista);
                } else {
                    try {
                        IncompatibilidadeNumero();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

            }
            if (rgbNumMatricula.isSelected()) {
                char buscar[] = txtBuscador.getText().toCharArray();
                if (Validar.isDigit(buscar)) {

                    Pessoa p = new Pessoa();
                    p.setNum_matricula(txtBuscador.getText());
                    List<Pessoa> lista = NegociosEstaticos.getNegocioPessoa().buscarPorMatricula(p);
                    completarTabela(lista);

                } else {
                    try {
                        IncompatibilidadeNumero();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

            }
        }
    }

    @FXML
    void btnInserir_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                CadastroPessoaController.setCadastrar(true);
                Parent root;
                // pessoa = null;
                root = FXMLLoader.load(CadastroPessoaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Pessoa.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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

            Pessoa p = tblPrincipal.getSelectionModel().getSelectedItem();
            CadastroPessoaController.setCadastrar(false);
            CadastroPessoaController.setAlterar(p);
            try {
                Parent root;
                //  pessoa = null;
                root = FXMLLoader.load(CadastroPessoaController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Pessoa.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
                    NegociosEstaticos.getNegocioPessoa().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                    completarTabela(NegociosEstaticos.getNegocioPessoa().buscarTodos());
                }
            } catch (Exception ex) {

                try {
                    NegociosEstaticos.getNegocioPessoa().Inativar(tblPrincipal.getSelectionModel().getSelectedItem());
                    completarTabela(NegociosEstaticos.getNegocioPessoa().buscarTodos());
                } catch (Exception ex1) {
                    Alertas alert = new Alertas();
                    alert.alerta(Alert.AlertType.ERROR, "Erro na remoção", ex1.getMessage());

                }

            }
        }
    }

    @FXML
    void btnVoltar_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                //   pessoa = null;
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
            final ContextMenu cm = new ContextMenu();
            LerMessage ler = new LerMessage();
            MenuItem cmItem1 = new MenuItem(ler.getMessage("btn.alterar"));
            MenuItem cmItem2 = new MenuItem(ler.getMessage("btn.excluir"));

            cm.getItems().add(cmItem1);
            cm.getItems().add(cmItem2);

            cmItem1.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    btnAlterarOnAction(e);
                }
            });
            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    btnExcluirOnAction(e);
                }
            });
            cm.show(Title, event.getScreenX(), event.getScreenY());
        }
    }

}
