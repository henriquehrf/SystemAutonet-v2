/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.consulta.estoque;

import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import gui.SystemAutonet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import utilitarios.Alertas;
import vo.BaixaEstoqueMaterial;
import vo.EmprestimoEstoqueMaterial;
import vo.EstoqueMaterial;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class Consultar_SaidaMaterialController {

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcQtdDisponivel;

    @FXML
    private ToggleGroup Filtro;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextArea txtAreaRelatorio;

    @FXML
    private TableView<EstoqueMaterial> tblPrincipal;

    @FXML
    private Button btnVisualizar;

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
    private BorderPane bdpPrincipal;

    @FXML
    private BorderPane bdpSecundario;

    @FXML
    private AnchorPane AnchorPaneValidation;

    @FXML
    private Button btnImprimirMat;

    @FXML
    private Button btnRetornar;

    @FXML
    private Label txtMsgLoading;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcMaterial;

    private String txtRelatorio = "";

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
    void btnRetornar_OnAction(ActionEvent event) {

        bdpPrincipal.setVisible(true);
        bdpSecundario.setVisible(false);
        txtAreaRelatorio.setText("");

    }

    @FXML
    void btnImprimirMat_OnAction(ActionEvent event) {

    }

    @FXML
    void btnVisualizarOnAction(ActionEvent event) {

        if (tblPrincipal.getSelectionModel().getSelectedItem() == null) {
            Alertas alert = new Alertas();

            alert.alerta(Alert.AlertType.ERROR, "Operação inválida", "É necessário a seleação de um item da tabela");
            return;
        }

        txtMsgLoading.setText("Aguarde, buscando informações no banco de dados");
        AnchorPaneValidation.setVisible(true);

        new Thread() {
            @Override
            public void run() {
                List<BaixaEstoqueMaterial> listaDeBaixas = new ArrayList<>();
                List<EmprestimoEstoqueMaterial> listaDeEmprestimo = new ArrayList<>();
                listaDeBaixas = NegociosEstaticos.getNegocioBaixaEstoqueMaterial().todasBaixasEstoque();
                listaDeEmprestimo = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarTodos();

                Platform.runLater(() -> txtMsgLoading.setText("Aguarde, estamos preparando o relatório"));

                Date dt_relatorio = new Date();
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");

                txtRelatorio = "Gerado em: " + dt.format(dt_relatorio) + "\n\n";

                txtRelatorio += "Relatório  de saída do estoque " + tblPrincipal.getSelectionModel().getSelectedItem().getLocalDescricao();

                txtRelatorio += "\n\n";

                txtRelatorio += "Todas movimentações de empréstimo\n\n";

                Boolean haMov = false;
                for (EmprestimoEstoqueMaterial vo : listaDeEmprestimo) {
                    if (vo.getId_estoquematerial() != null) {
                        if (vo.getId_estoquematerial().getId().equals(tblPrincipal.getSelectionModel().getSelectedItem().getId())) {

                            txtRelatorio += "\t* Material: " + vo.getId_material().getDescricao() + "\n";
                            txtRelatorio += "\t* Qtd.Emprestada: " + vo.getQtd_emprestada() + " " + vo.getId_material().getId_tipo_unidade().getSigla() + "\n";
                            txtRelatorio += "\t* Qtd.Devolvida: " + vo.getQtd_devolvida() + " " + vo.getId_material().getId_tipo_unidade().getSigla() + "\n";
                            txtRelatorio += "\t* Data do Empréstimo: " + vo.getId_emprestimo().getDt_emprestimoString() + "\n";
                            txtRelatorio += "\t* Data da Devolução: " + vo.getDt_devolucao() + "\n";
                            txtRelatorio += "\t* Situação do Empréstimo: " + vo.getId_emprestimo().getStatus_emprestimo().name() + "\n";
                            txtRelatorio += "\t* Finalidade: " + vo.getId_emprestimo().getFinalidade() + "\n";
                            txtRelatorio += "\t* Observações do Empréstimo: " + vo.getId_emprestimo().getObservacao() + "\n";
                            txtRelatorio += "\t* Solicitante: " + vo.getId_emprestimo().getId_pessoa_solicita().getNome() + "\n";
                            txtRelatorio += "\t* CPF do Solicitante: " + vo.getId_emprestimo().getId_pessoa_solicita().getCpf() + "\n";
                            txtRelatorio += "\t* Autorizador: " + vo.getId_emprestimo().getId_pessoa_autoriza().getNome() + "\n";
                            txtRelatorio += "\t* CPF do Autorizador: " + vo.getId_emprestimo().getId_pessoa_autoriza().getCpf() + "\n";
                            txtRelatorio += "\t* Observação do Material: " + vo.getObservacao() + "\n";
                            txtRelatorio += "\n\n";
                            haMov = true;
                        }
                    }

                }

                if (!haMov) {
                    txtRelatorio += "\n\n";
                    txtRelatorio += " -> NÃO HÁ REGISTRO DE EMPRÉSTIMO PARA ESTE ESTOQUE";
                    txtRelatorio += "\n\n";
                }
                haMov = false;
                Platform.runLater(() -> txtMsgLoading.setText("Aguarde, falta pouco para o término do relatório"));

                txtRelatorio += "Todas movimentações de saída\n\n";
                for (BaixaEstoqueMaterial vo : listaDeBaixas) {
                    if (vo.getId_estoquematerial().getId().equals(tblPrincipal.getSelectionModel().getSelectedItem().getId())) {
                        txtRelatorio += "\t* Material: " + vo.getId_estoquematerial().getId_material().getDescricao() + "\n";
                        txtRelatorio += "\t* Qtd.Baixada: " + vo.getQuantidade() + " " + vo.getId_estoquematerial().getId_material().getId_tipo_unidade().getSigla() + "\n";
                        txtRelatorio += "\t* Autor da Baixa: " + vo.getId_baixa().getId_pessoa().getNome() + "\n";
                        txtRelatorio += "\t* CPF do autor da Baixa: " + vo.getId_baixa().getId_pessoa().getCpf() + "\n";
                        txtRelatorio += "\t* Data da Baixa: " + vo.getId_baixa().getDt_baixaFormat() + "\n";
                        txtRelatorio += "\t* Tipo de Saída: " + vo.getId_baixa().getId_tipo_saida().getDescricao() + "\n";
                        txtRelatorio += "\t* Informações Complementares: " + vo.getId_baixa().getObservacao() + "\n";
                        txtRelatorio += "\n\n";
                        haMov = true;
                    }
                }

                if (!haMov) {
                    txtRelatorio += "\n\n";
                    txtRelatorio += " -> NÃO HÁ REGISTRO DE BAIXA PARA ESTE ESTOQUE";
                    txtRelatorio += "\n\n";
                }

                txtRelatorio += " -> FIM RELATÓRIO <-";

                Platform.runLater(() -> txtMsgLoading.setText("Relatório gerado com sucesso!"));

                Platform.runLater(() -> txtAreaRelatorio.setText(txtRelatorio));
                Platform.runLater(() -> bdpSecundario.setVisible(true));
                Platform.runLater(() -> bdpPrincipal.setVisible(false));
                Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
            }
        }.start();

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
