/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.consulta.estoque;

import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import gui.SystemAutonet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import utilitarios.GerarPDF;
import vo.EstoqueMaterial;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class ConsultarEstoqueController {

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcQtdDisponivel;

    @FXML
    private ToggleGroup Filtro;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableView<EstoqueMaterial> tblPrincipal;

    @FXML
    private Button btnImprimir;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcLocal;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcQtdEmprestada;

    @FXML
    private Label title;

    @FXML
    private TextField txtBuscador;

    @FXML
    private Button btnBuscar;

    @FXML
    private RadioButton rdbMaterial;

    @FXML
    private RadioButton rdbLocal;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcMaterial;

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

    List<EstoqueMaterial> todosMaterialEstoque = new ArrayList<>();

    @FXML
    void btnVoltar_OnAction(ActionEvent event) {
        voltar();
    }

    @FXML
    void btnVoltarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            voltar();
        }
    }

    void voltar() {
        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void tblPrincipalOnMouseClicked(ActionEvent event) {

    }

    @FXML
    void btnImprimirOnAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            GerarPDF pdf = new GerarPDF();
            try {
                pdf.consultaEstoqueGeral(selectedDirectory.getAbsolutePath(), "Relatório de Consulta de Estoque", "ConsultaEstoque", tblPrincipal.getItems());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    @FXML
    void btnBuscar_OnAction(ActionEvent event) {
        pesquisar(this.txtBuscador.getText());
    }

    @FXML
    void btnBuscarOnKeyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            pesquisar(this.txtBuscador.getText());
        }

    }

    void pesquisar(String txt) {

        if (txt.length() == 0) {
            completarTabela(todosMaterialEstoque);
            return;
        }
        List<EstoqueMaterial> listaDeItensFiltrado = new ArrayList<>();

        if (rdbMaterial.isSelected()) {
            for (EstoqueMaterial vo : todosMaterialEstoque) {
                if (vo.getMaterialDescricao().toLowerCase().contains(txt.toLowerCase())) {
                    listaDeItensFiltrado.add(vo);
                }
            }
            completarTabela(listaDeItensFiltrado);
            return;
        }
        if (rdbLocal.isSelected()) {

            for (EstoqueMaterial vo : todosMaterialEstoque) {
                if (vo.getLocalDescricao().toLowerCase().contains(txt.toLowerCase())) {
                    listaDeItensFiltrado.add(vo);
                }
            }
            completarTabela(listaDeItensFiltrado);
            return;

        }
    }

    void completarTabela(List<EstoqueMaterial> list) {

        if (list.size() == 0) {
            tableLoading(false);
        } else {
            tableLoading(true);
        }

        ObservableList<EstoqueMaterial> estoqueMaterial = FXCollections.observableArrayList();
        estoqueMaterial.addAll(list);

        this.tbcMaterial.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("MaterialDescricao"));
        this.tbcQtdDisponivel.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("QtdDisponivelFormat"));
        this.tbcQtdEmprestada.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("QtdEmprestadaFormat"));
        this.tbcLocal.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("LocalDescricao"));
        tblPrincipal.setItems(estoqueMaterial);
    }

    void carregarTabelaComDadosIniciais() {
        new Thread() {
            @Override
            public void run() {
                todosMaterialEstoque = NegociosEstaticos.getNegocioEstoqueMateria().buscarTodosEstoqueMaterial();
                completarTabela(todosMaterialEstoque);
            }
        }.start();
    }

    public void initialize() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtBuscador.requestFocus();
                carregarTabelaComDadosIniciais();
                tableLoading(true);
            }
        });

    }
}
