package controller.cadastro.Consulta;

import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import controller.cadastro.Cadastro.CadastroMaterialController;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import vo.Material;

public class ConsultarMaterialController {

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TableColumn<Material, Number> tbcQuantidade;

    @FXML
    private TableView<Material> tblPrincipal;

    @FXML
    private TableColumn<Material, String> tbcCategoria;

    @FXML
    private Button btnInserir;

    @FXML
    private Label Title;

    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private TextField txtBuscador;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnBusca;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TableColumn<Material, String> tbcDescricao;

    @FXML
    private TableColumn<Material, String> tbcUnidadeMedida;

    List<Material> cacheList = null;

    void completarCombo(List<Categoria> lista) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<String> dado = FXCollections.observableArrayList();

                for (int i = 0; i < lista.size(); i++) {
                    dado.add(lista.get(i).getDescricao());
                }
                dado.add("TODOS");
                Collections.sort(dado);
                cmbCategoria.setItems(dado);
                cmbCategoria.setValue("TODOS");
            }
        });

    }

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

    public void initialize() {

        txtQuantidade.setText("0");

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

                cacheList = NegociosEstaticos.getNegocioMaterial().buscarTodos();
                completarCombo(NegociosEstaticos.getNegocioCategoria().bucarTodos());
                completarTabela(cacheList);
            }
        }.start();

    }

    void MaxAndMinSlider(List<Material> lista) {
        int menor, maior;
        if (lista.isEmpty()) {
            menor = 0;
            maior = 0;
        } else {
            menor = lista.get(0).getQuantidade();
            maior = lista.get(0).getQuantidade();

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getQuantidade() > maior) {
                    maior = lista.get(i).getQuantidade();
                }
                if (lista.get(i).getQuantidade() < menor) {
                    menor = lista.get(i).getQuantidade();
                }
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
    void cmbCategoria_OnAction(ActionEvent event) {

        if (cmbCategoria.getValue().equals("TODOS")) {
            buscaMaterial();
            // completarTabela(NegociosEstaticos.getNegocioMaterial().buscarTodos());
        } else {
             buscaMaterial();
            System.out.println("Passei aqui");
           // CategoriaFilter(tblPrincipal.getItems());
        }
    }

    void CategoriaFilter(List<Material> lista) {

        List<Material> aux = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCategoriaNome().equals(cmbCategoria.getValue())) {
                aux.add(lista.get(i));
            }
        }
        cacheList=aux;
        completarTabela(aux);

    }

    @FXML
    void txtBuscador_OnKeyPressed(KeyEvent event) {
        btnBusca_OnKeyPressed(event);

    }

    @FXML
    void cmbCategoria_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            if (cmbCategoria.getValue().equals("TODOS")) {
                btnBusca_OnKeyPressed(event);
            } else {
                btnBusca_OnKeyPressed(event);
                CategoriaFilter(tblPrincipal.getItems());
            }
        }

    }

    @FXML
    void btnInserir_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                CadastroMaterialController.setCadastrar(true);
                Parent root;
                root = FXMLLoader.load(CadastroMaterialController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Material.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
            try {
                Material p = tblPrincipal.getSelectionModel().getSelectedItem();
                CadastroMaterialController.setCadastrar(false);
                CadastroMaterialController.setAlterar(p);
                Parent root;
                root = FXMLLoader.load(CadastroMaterialController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Material.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
                    NegociosEstaticos.getNegocioMaterial().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                    completarTabela(NegociosEstaticos.getNegocioMaterial().buscarTodos());
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
                root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    @FXML
    void btnBusca_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buscaMaterial();
        }
        if (event.getCode() == KeyCode.TAB) {
            cmbCategoria.requestFocus();
        }

    }

    void completarTabela(List<Material> lista) {

        lista = qtdFilter(Integer.parseInt(txtQuantidade.getText()));

        if (lista.size() == 0) {
            tableLoading(false);
        } else {
            tableLoading(true);
        }

        ObservableList<Material> dado = FXCollections.observableArrayList();
        dado.addAll(lista);
        this.tbcDescricao.setCellValueFactory(new PropertyValueFactory<Material, String>("descricao"));
        this.tbcQuantidade.setCellValueFactory(new PropertyValueFactory<Material, Number>("quantidade"));
        this.tbcUnidadeMedida.setCellValueFactory(new PropertyValueFactory<Material, String>("unidadeMedida"));
        this.tbcCategoria.setCellValueFactory(new PropertyValueFactory<Material, String>("CategoriaNome"));
        Comparator<Material> cmp = new Comparator<Material>() {
            @Override
            public int compare(Material mat1, Material mat2) {
                int x = mat1.getDescricao().compareTo(mat2.getDescricao());
                if (x != 0) {
                    return x;
                } else {
                    Integer x1 = ((Material) mat1).getQuantidade();
                    Integer x2 = ((Material) mat2).getQuantidade();
                    return x1.compareTo(x2);
                }
            }

        };
        Collections.sort(dado, cmp);
        this.tblPrincipal.setItems(dado);
        tblPrincipal.refresh();

    }

    List<Material> qtdFilter(int value) {
        List<Material> filter = new ArrayList<>();
        for (int i = 0; i < cacheList.size(); i++) {
            if (cacheList.get(i).getQuantidade() >= value) {
                filter.add(cacheList.get(i));
            }
        }
        return filter;
    }

    void tickCount(Boolean isIncrement) {
        int count = 0;
        if (isIncrement) {
            count = Integer.parseInt(txtQuantidade.getText());
            count++;
            txtQuantidade.setText("" + count);
        } else {
            count = Integer.parseInt(txtQuantidade.getText());
            count--;
            if (count < 0) {
                count = 0;
            }
            txtQuantidade.setText("" + count);
        }
        completarTabela(cacheList);
    }

    @FXML
    void txtQuantidadeOnKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (txtQuantidade.getText().length() == 0) {
                txtQuantidade.setText("0");
                completarTabela(cacheList);
            } else {
                if (Integer.parseInt(txtQuantidade.getText()) < 0) {
                    txtQuantidade.setText("0");
                }
                completarTabela(cacheList);
            }

        }
    }

    @FXML
    void btnDownQtdOnAction(ActionEvent event) {
        tickCount(false);

    }

    @FXML
    void btnDownQtdOnKeyReleased(KeyEvent event) {
        tickCount(false);

    }

    @FXML
    void btnUpQtdOnKeyReleased(KeyEvent event) {
        tickCount(true);

    }

    @FXML
    void btnUpQtdOnAction(ActionEvent event) {
        tickCount(true);

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
    void btnBusca_OnAction(ActionEvent event) {
        buscaMaterial();
    }

    void buscaMaterial() {
        new Thread() {
            @Override
            public void run() {
                if (cmbCategoria.getValue().equals("TODOS")) {
                    Material material = new Material();
                    material.setDescricao(txtBuscador.getText());
                    cacheList = NegociosEstaticos.getNegocioMaterial().buscarPorDescricao(material);
                    completarTabela(cacheList);
                } else {
                    Material material = new Material();
                    material.setDescricao(txtBuscador.getText());
                    cacheList = NegociosEstaticos.getNegocioMaterial().buscarPorDescricao(material);
                    CategoriaFilter(cacheList);

                }
            }
        }.start();

    }

    @FXML
    void btnBusca_OnMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            buscaMaterial();
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
        try {
            Material p = tblPrincipal.getSelectionModel().getSelectedItem();
            CadastroMaterialController.setCadastrar(false);
            CadastroMaterialController.setAlterar(p);
            Parent root;
            root = FXMLLoader.load(CadastroMaterialController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Material.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
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
                NegociosEstaticos.getNegocioMaterial().remover(tblPrincipal.getSelectionModel().getSelectedItem());
                cacheList = NegociosEstaticos.getNegocioMaterial().buscarTodos();
                completarTabela(cacheList);
            }
        } catch (Exception ex) {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Erro na remoção", ex.getMessage());
        }
    }

    @FXML
    void btnInserir_OnAction(ActionEvent event) {
        try {
            CadastroMaterialController.setCadastrar(true);
            Parent root;
            root = FXMLLoader.load(CadastroMaterialController.class.getClassLoader().getResource("fxml/cadastro/Cadastro/Cadastro_Material.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void IncompatibilidadeNumero() throws Exception {
        LerMessage ler = new LerMessage();
        Alertas aviso = new Alertas();
        aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.dados.erro"), ler.getMessage("msg.incompatibilidade.numero"));

    }

}
