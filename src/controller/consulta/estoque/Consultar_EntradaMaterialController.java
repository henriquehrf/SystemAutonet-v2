/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.consulta.estoque;

import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import gui.SystemAutonet;
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
import javafx.scene.control.Alert;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import utilitarios.Alertas;
import vo.Entrada;
import vo.EntradaMaterial;
import vo.EstoqueMaterial;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class Consultar_EntradaMaterialController {

    @FXML
    private Button btnImprimir;

    @FXML
    private ToggleGroup Filtro;

    @FXML
    private TableView<Entrada> tblPrincipal;

    @FXML
    private TableColumn<Entrada, String> tbcValor;

    @FXML
    private Label title;

    @FXML
    private Button btnVisualizar;

    @FXML
    private TextField txtBuscador;

    @FXML
    private RadioButton rdbFornecedor;

    @FXML
    private TableColumn<Entrada, String> tbcDtEntrada;

    @FXML
    private TableColumn<Entrada, String> tbcNumNF;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Entrada, String> tbcFornecedor;

    @FXML
    private TableColumn<Entrada, String> tbcDtNF;

    @FXML
    private RadioButton rdbNumNF;

    @FXML
    private Button btnBuscar;

    @FXML
    private Label txtFornecedor;

    @FXML
    private BorderPane bdpSecundario;

    @FXML
    private Label txtDtNF;

    @FXML
    private Label txtNumNF;

    @FXML
    private Button btnImprimirMat;

    @FXML
    private BorderPane bdpPrincipal;

    @FXML
    private Label txtDtEntrada;

    @FXML
    private TableColumn<EntradaMaterial, String> tbcMaterial;

    @FXML
    private TableColumn<EntradaMaterial, String> tbcValorUnitario;

    @FXML
    private Button btnRetornar;

    @FXML
    private TableColumn<EntradaMaterial, String> tbcQuantidade;

    @FXML
    private TableView<EntradaMaterial> tblSecundaria;

    @FXML
    private Label txtValorTotal;

    List<Entrada> todasEntrada = new ArrayList<>();

    @FXML
    void btnImprimir_OnAction(ActionEvent event) {

    }

    @FXML
    void btnImprimirOnKeyPressed(KeyEvent event) {

    }

    void buscar() {

        if (txtBuscador.getText().length() == 0) {
            completarTabela(todasEntrada);
            return;
        }
        List<Entrada> resultadoDaPesquisa = new ArrayList<>();
        if (rdbFornecedor.isSelected()) {
            for (Entrada vo : todasEntrada) {
                if (vo.getFornecedorNome().toLowerCase().contains(txtBuscador.getText().toLowerCase())) {
                    resultadoDaPesquisa.add(vo);
                }
            }
            completarTabela(resultadoDaPesquisa);
            return;
        }
        if (rdbNumNF.isSelected()) {
            String txt = "";
            for (Entrada vo : todasEntrada) {
                txt = Integer.toString(vo.getNumero_nf());
                if (txt.toLowerCase().contains(txtBuscador.getText().toLowerCase())) {
                    resultadoDaPesquisa.add(vo);
                }
            }
            completarTabela(resultadoDaPesquisa);
            return;
        }

    }

    @FXML
    void btnBuscar_OnAction(ActionEvent event) {
        buscar();
    }

    @FXML
    void btnBuscarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buscar();
        }
    }

    @FXML
    void btnImprimirMat_OnAction(ActionEvent event) {

    }

    @FXML
    void btnImprimirMatOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void btnRetornar_OnAction(ActionEvent event) {
        retornar();
    }

    void retornar() {

        bdpPrincipal.setVisible(true);
        bdpSecundario.setVisible(false);

    }

    @FXML
    void btnRetornarOnKeyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            retornar();
        }

    }

    @FXML
    void btnVisualizar_OnAction(ActionEvent event) {
        visualizar();
    }

    @FXML
    void btnVisualizarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            visualizar();
        }

    }

    void preencherTabelaSecundaria(Entrada entrada) {

        new Thread() {
            @Override
            public void run() {
                List<EntradaMaterial> entradaMaterial = new ArrayList<>();
                tblSecundaria.getItems().clear();

                completarTabelaSecundaria(NegociosEstaticos.getNegocioEntradaMaterial().buscarTodosPorEntrada(entrada));
            }
        }.start();
    }

    void preencherDadosSecundario(Entrada entrada) {

        txtFornecedor.setText(entrada.getFornecedorNome());
        txtDtEntrada.setText(entrada.getDtEntradaFormat());
        txtValorTotal.setText(entrada.getValorTotalFormat());
        txtDtNF.setText(entrada.getDtNFFormat());
        txtNumNF.setText("" + entrada.getNumero_nf());

        preencherTabelaSecundaria(entrada);

    }

    void visualizar() {

        if (tblPrincipal.getSelectionModel().getSelectedItem() == null) {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Operação inválida", "É necessário a seleção de um item na tabela");
            return;
        }
        bdpPrincipal.setVisible(false);
        bdpSecundario.setVisible(true);
        preencherDadosSecundario(tblPrincipal.getSelectionModel().getSelectedItem());

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
    void btnVoltar_OnAction(ActionEvent event) {
        voltar();
    }

    @FXML
    void btnVoltarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            voltar();
        }
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

    void completarTabela(List<Entrada> list) {

        if (list.size() == 0) {
            tableLoading(false);
        } else {
            tableLoading(true);
        }
        
        ObservableList<Entrada> entradaMaterial = FXCollections.observableArrayList();
        entradaMaterial.addAll(list);
        
        this.tbcDtEntrada.setCellValueFactory(new PropertyValueFactory<Entrada, String>("DtEntradaFormat"));
        this.tbcDtNF.setCellValueFactory(new PropertyValueFactory<Entrada, String>("DtNFFormat"));
        this.tbcFornecedor.setCellValueFactory(new PropertyValueFactory<Entrada, String>("FornecedorNome"));
        this.tbcNumNF.setCellValueFactory(new PropertyValueFactory<Entrada, String>("Numero_nf"));
        this.tbcValor.setCellValueFactory(new PropertyValueFactory<Entrada, String>("ValorTotalFormat"));
        tblPrincipal.setItems(entradaMaterial);
    }

    void completarTabelaSecundaria(List<EntradaMaterial> list) {
        ObservableList<EntradaMaterial> entradaMaterial = FXCollections.observableArrayList();
        entradaMaterial.addAll(list);

        this.tbcMaterial.setCellValueFactory(new PropertyValueFactory<EntradaMaterial, String>("MaterialNome"));
        this.tbcQuantidade.setCellValueFactory(new PropertyValueFactory<EntradaMaterial, String>("Quantidade_material"));
        this.tbcValorUnitario.setCellValueFactory(new PropertyValueFactory<EntradaMaterial, String>("Valor_Unitario_Monetario"));
        tblSecundaria.setItems(entradaMaterial);

    }

    void carregarTabelaComDadosIniciais() {
        new Thread() {
            @Override
            public void run() {
                todasEntrada = NegociosEstaticos.getNegocioEntrada().buscarTodos();
                completarTabela(todasEntrada);

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
