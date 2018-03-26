/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.estoque.baixa;

import classesAuxiliares.ClasseDoSistemaEstatico;
import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import gui.SystemAutonet;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;
import utilitarios.Alertas;
import utilitarios.Mask;
import vo.Baixa;
import vo.BaixaEstoqueMaterial;
import vo.EntradaMaterial;
import vo.EstoqueMaterial;
import vo.Fornecedor;
import vo.Local;
import vo.Material;
import vo.TipoSaida;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class BaixaMaterialController {

    @FXML
    private Button btnAdicionar;

    @FXML
    private TabPane tabPanePrincipal;

    @FXML
    private Button btnExclur;

    @FXML
    private TableColumn<BaixaEstoqueMaterial, String> tbcCategoriaListaMaterial;

    @FXML
    private Button btnBaixar;

    @FXML
    private TableColumn<BaixaEstoqueMaterial, String> tbcDescricaoListaMaterial;

    @FXML
    private TextField txtBuscador;

    @FXML
    private Button btnAdicionarObs;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcQuantidadeDisponivelBuscaMaterial;

    @FXML
    private TableColumn<BaixaEstoqueMaterial, String> tbcQuantidadeSolicitadaListaMaterial;

    @FXML
    private Tab tabBuscarMaterial;

    @FXML
    private ComboBox<String> cbmTipoBaixa;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextArea txtObservacao;

    @FXML
    private Button btnAdicionarMaterial;

    @FXML
    private TextArea txtObservacao1;

    @FXML
    private Button btnVoltarObservacao;

    @FXML
    private Label lblDescricao;

    @FXML
    private TableView<EstoqueMaterial> tblBuscaMateriais;

    @FXML
    private Tab tabObservacao;

    @FXML
    private TableView<BaixaEstoqueMaterial> tblListaMateriais;

    @FXML
    private TextField txtFinalidade;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcMaterialBuscaMaterial;

    @FXML
    private Tab tabListaMaterial;

    @FXML
    private Button btnSalvarObservacao;

    @FXML
    private DatePicker dtpDataEmprestimo;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcLocalBuscaMaterial;

    @FXML
    private Button btnBuscar;

    List<EstoqueMaterial> todoMaterialDoEstoque = new ArrayList<>();
    List<BaixaEstoqueMaterial> materialSeparado = new ArrayList<>();
    BaixaEstoqueMaterial estoqueMaterialSeparado = new BaixaEstoqueMaterial();
    List<TipoSaida> tipoSaida;

    @FXML
    void btnAdicionarOnAction(ActionEvent event) {
        tabPanePrincipal.getSelectionModel().select(tabListaMaterial);
        tabObservacao.setDisable(true);
        tabBuscarMaterial.setDisable(true);
        tabListaMaterial.setDisable(false);
        completarTabelaEstoqueMaterial(todoMaterialDoEstoque);
    }

    @FXML
    void btnEditarOnAction(ActionEvent event) {

    }

    @FXML
    void btnExclurOnAction(ActionEvent event) {

        if (tblListaMateriais.getSelectionModel().getSelectedItem() == null) {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Operação inválida", "É necessário a seleção de um item da tabela");
            return;
        }

        for (EstoqueMaterial vo : todoMaterialDoEstoque) {
            if (vo.getId().equals(tblListaMateriais.getSelectionModel().getSelectedItem().getId_estoquematerial().getId())) {
                vo.setQuantidade(vo.getQuantidade() + tblListaMateriais.getSelectionModel().getSelectedItem().getQuantidade());
            }
        }
        tblBuscaMateriais.refresh();

        materialSeparado.remove(tblListaMateriais.getSelectionModel().getSelectedItem());
        tblListaMateriais.getItems().remove(tblListaMateriais.getSelectionModel().getSelectedItem());

    }

    void salvarEstoque() throws Exception {

        List<Baixa> baixa = NegociosEstaticos.getNegocioBaixa().todasAsBaixas();
        List<EstoqueMaterial> estoqueMaterial = NegociosEstaticos.getNegocioEstoqueMateria().buscarTodosEstoqueMaterial();

        Baixa ultimaBaixa = new Baixa();

        ultimaBaixa = baixa.get(baixa.size() - 1);

        if (ultimaBaixa.getId_pessoa().getId().equals(ClasseDoSistemaEstatico.getPessoa().getId())) {
            for (BaixaEstoqueMaterial vo : tblListaMateriais.getItems()) {
                vo.setId_baixa(ultimaBaixa);
                NegociosEstaticos.getNegocioBaixaEstoqueMaterial().salvar(vo);
                for (EstoqueMaterial estoque : estoqueMaterial) {
                    if (vo.getId_estoquematerial().getId().equals(estoque.getId())) {
                        estoque.setQuantidade(estoque.getQuantidade() - vo.getQuantidade());
                        if (vo.getQuantidade() >= 0) {
                            NegociosEstaticos.getNegocioEstoqueMateria().salvar(estoque);
                        } else {
                            throw new Exception("Ops, não há material suficiênte para essa operação");
                        }

                    }
                }
            }
        }

    }

    void salvarABaixa() throws Exception {
        Baixa baixa = new Baixa();
        baixa.setId_pessoa(ClasseDoSistemaEstatico.getPessoa());

        for (TipoSaida vo : tipoSaida) {
            if (vo.getDescricao().toLowerCase().equals(cbmTipoBaixa.getValue().toLowerCase())) {
                baixa.setId_tipo_saida(vo);
                break;
            }
        }

        Instant instant = dtpDataEmprestimo.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

        String txt = "Finalidade: " + txtFinalidade.getText() + "\nObservação: " + txtObservacao.getText();
        baixa.setObservacao(txt);
        baixa.setDt_baixa(Date.from(instant));
        NegociosEstaticos.getNegocioBaixa().salvar(baixa);

    }

    @FXML
    void btnBaixarOnAction(ActionEvent event) {

        Alertas alert = new Alertas();

        try {
            salvarABaixa();
            salvarEstoque();
            alert.alerta(Alert.AlertType.INFORMATION, "Operação realizada com sucesso", "Foi realizada com sucesso a baixa dos materiais");
            btnCancelarOnAction(event);
        } catch (Exception ex) {
            alert.alerta(Alert.AlertType.ERROR, "Ops, houve foi encontrado um problema", ex.getMessage());
        }

    }

    @FXML
    void btnCancelarOnAction(ActionEvent event) {

        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    private void DadosEntrada() {

        Stage stage = new Stage();
        Label label = new Label("Digite a quantidade: ");
        TextField txt = new TextField();
        Mask mask = new Mask();
        txt.setPrefSize(80, 25);

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setPrefSize(100, 25);
        btnConfirmar.setDefaultButton(true);
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefSize(100, 25);
        btnCancelar.setCancelButton(true);

        HBox hbox = new HBox(btnConfirmar, btnCancelar);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPrefSize(250, 100);
        hbox.setSpacing(20);
        HBox hboxQtd = new HBox(label, txt);
        hboxQtd.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(hboxQtd, hbox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(30, 20, 20, 30));
        BorderPane pane = new BorderPane(vbox);
        Parent parent = pane;
        Scene scene = new Scene(parent, 250, 150);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setTitle("Aguardando a qtd. material");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        estoqueMaterialSeparado = new BaixaEstoqueMaterial();

        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                estoqueMaterialSeparado = null;
                stage.close();
            }
        });
        btnConfirmar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    estoqueMaterialSeparado.setQuantidade(Integer.parseInt(txt.getText()));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    estoqueMaterialSeparado = null;
                }

                stage.close();

            }
        });

        stage.showAndWait();
    }

    @FXML
    void btnAdicionarObsOnAction(ActionEvent event) {
        if (tblListaMateriais.getSelectionModel().getSelectedItem() == null) {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Operação inválida", "É necessário a seleção de um item da tabela");
            return;
        }

        tabPanePrincipal.getSelectionModel().select(tabObservacao);
        tabObservacao.setDisable(false);
        tabBuscarMaterial.setDisable(true);
        tabListaMaterial.setDisable(true);

        lblDescricao.setText(tblListaMateriais.getSelectionModel().getSelectedItem().getId_estoquematerial().getMaterialDescricao());
        txtObservacao1.setText(tblListaMateriais.getSelectionModel().getSelectedItem().getObservacao());
    }

    @FXML
    void btnAdicionarMaterialOnAction(ActionEvent event) {

        if (tblBuscaMateriais.getSelectionModel().getSelectedItem() == null) {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Operação inválida", "É necessário a seleção de um item da tabela");
            return;
        }
        DadosEntrada();

        if (estoqueMaterialSeparado == null) {
            return;
        }

        if (estoqueMaterialSeparado.getQuantidade() <= tblBuscaMateriais.getSelectionModel().getSelectedItem().getQuantidade()
                && estoqueMaterialSeparado.getQuantidade() > 0) {

            BaixaEstoqueMaterial estoqueMaterial = new BaixaEstoqueMaterial();

            estoqueMaterial.setId_estoquematerial(tblBuscaMateriais.getSelectionModel().getSelectedItem());
            estoqueMaterial.setQuantidade(estoqueMaterialSeparado.getQuantidade());
            materialSeparado.add(estoqueMaterial);
            completarListaMaterialSeparado(materialSeparado);

            tblBuscaMateriais.getSelectionModel().getSelectedItem().setQuantidade(tblBuscaMateriais.getSelectionModel().getSelectedItem().getQuantidade() - estoqueMaterialSeparado.getQuantidade());

            tblBuscaMateriais.refresh();
            btnVoltarOnAction(event);

        } else {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Operação inválida", "A quantidade informada é inválida");
        }

    }

    @FXML
    void btnVoltarOnAction(ActionEvent event) {

        tabPanePrincipal.getSelectionModel().select(tabBuscarMaterial);
        tabBuscarMaterial.setDisable(false);
        tabListaMaterial.setDisable(true);
        tabObservacao.setDisable(true);

    }

    void buscar() {

        if (txtBuscador.getText().length() == 0) {
            completarTabelaEstoqueMaterial(todoMaterialDoEstoque);
        }
        List<EstoqueMaterial> listaPesquisada = new ArrayList<>();

        for (EstoqueMaterial vo : todoMaterialDoEstoque) {
            if (vo.getMaterialDescricao().toLowerCase().contains(txtBuscador.getText().toLowerCase())) {
                listaPesquisada.add(vo);
            }
        }

        completarTabelaEstoqueMaterial(listaPesquisada);
    }

    @FXML
    void btnBuscarOnAction(ActionEvent event) {
        buscar();
    }

    @FXML
    void btnSalvarObservacaoOnAction(ActionEvent event) {

        for (BaixaEstoqueMaterial vo : materialSeparado) {

            if (vo.getId_estoquematerial().getId().equals(tblListaMateriais.getSelectionModel().getSelectedItem().getId_estoquematerial().getId())) {
                vo.setObservacao(txtObservacao1.getText());
                break;
            }
        }
        tblListaMateriais.refresh();

        voltarAbaPrincipal();

    }

    void voltarAbaPrincipal() {
        tabPanePrincipal.getSelectionModel().select(tabBuscarMaterial);
        tabBuscarMaterial.setDisable(false);
        tabListaMaterial.setDisable(true);
        tabObservacao.setDisable(true);
    }

    @FXML
    void btnVoltarObservacaoOnAction(ActionEvent event) {

        voltarAbaPrincipal();

    }

    void completarTabelaEstoqueMaterial(List<EstoqueMaterial> list) {

        ObservableList<EstoqueMaterial> dado = FXCollections.observableArrayList();
        dado.addAll(list);

        this.tbcMaterialBuscaMaterial.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("MaterialDescricao"));
        this.tbcQuantidadeDisponivelBuscaMaterial.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("QtdDisponivelFormat"));
        this.tbcLocalBuscaMaterial.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("LocalDescricao"));

        this.tblBuscaMateriais.setItems(dado);

    }

    void completarListaMaterialSeparado(List<BaixaEstoqueMaterial> list) {
        ObservableList<BaixaEstoqueMaterial> estoqueMaterial = FXCollections.observableArrayList();
        estoqueMaterial.addAll(list);

        this.tbcDescricaoListaMaterial.setCellValueFactory(new PropertyValueFactory<BaixaEstoqueMaterial, String>("MaterialDescricao"));
        this.tbcQuantidadeSolicitadaListaMaterial.setCellValueFactory(new PropertyValueFactory<BaixaEstoqueMaterial, String>("QuantidadeFormat"));
        this.tbcCategoriaListaMaterial.setCellValueFactory(new PropertyValueFactory<BaixaEstoqueMaterial, String>("LocalFormat"));
        tblListaMateriais.setItems(estoqueMaterial);
        tblListaMateriais.refresh();
    }

    void completarComboBox() {

        new Thread() {
            @Override
            public void run() {
                tipoSaida = NegociosEstaticos.getNegocioTipoSaida().buscarTodos();
                ObservableList<String> cmb = FXCollections.observableArrayList();

                for (TipoSaida vo : tipoSaida) {
                    cmb.add(vo.getDescricao());
                }
                cbmTipoBaixa.setItems(cmb);
            }
        }.start();

    }

    void carregarTodoEstoqueMaterial() {
        new Thread() {
            @Override
            public void run() {
                todoMaterialDoEstoque = NegociosEstaticos.getNegocioEstoqueMateria().buscarTodosEstoqueMaterial();
            }
        }.start();
    }

    public void initialize() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                completarComboBox();
                tabListaMaterial.setDisable(true);
                tabObservacao.setDisable(true);
                carregarTodoEstoqueMaterial();
                //    mockTable();

            }
        });
        // TODO
    }

}
