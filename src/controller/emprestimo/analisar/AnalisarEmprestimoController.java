/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.emprestimo.analisar;

import classesAuxiliares.ClasseDoSistemaEstatico;
import classesAuxiliares.NegociosEstaticos;
import classesAuxiliares.TblEmprestimoEstoque;
import classesAuxiliares.TblPessoaEmprestimo;
import controller.PrincipalController;
import enumm.PerfilUsuario;
import enumm.StatusEmprestimo;
import gui.SystemAutonet;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import utilitarios.Alertas;
import utilitarios.EnviarEmail;
import utilitarios.GerarPDF;
import utilitarios.Mask;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.EstoqueMaterial;
import vo.Material;
import vo.Pessoa;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class AnalisarEmprestimoController implements Initializable {

    @FXML
    private Button btnRecusar;

    @FXML
    private TableView<TblPessoaEmprestimo> tblAguardandoAnalise;

    @FXML
    private TableColumn<Material, String> tbcDisponibilidadeAnalise;

    @FXML
    private Button btnAprovar;

    @FXML
    private Button btnVoltarAnalise;

    @FXML
    private Button btnAnalisar;

    @FXML
    private Button btnSalvar;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcDtEmprestimo;

    @FXML
    private TableColumn<Material, String> tbcMaterialAnalise;

    @FXML
    private Label lblDtEmprestimo;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnVoltarBuscar;

    @FXML
    private Label lblObservacao;

    @FXML
    private Tab tabItensSeparado;

    @FXML
    private TabPane TabPanePrincipal;

    @FXML
    private Button btnVoltarLista;

    @FXML
    private TableColumn<Material, String> tbcCategoriaAnalise;

    @FXML
    private Label lblFinalidade;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcFinalidade;

    @FXML
    private Tab tabListaEmprestimo;

    @FXML
    private Tab tabObservacoes;

    @FXML
    private TableColumn<Material, Integer> tbcQuantidadeAnalise;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcPessoa;

    @FXML
    private Tab tabAnaliseEmprestimo;

    @FXML
    private TableView<Material> tblListaMaterial;

    //-------
    @FXML
    private TableColumn<EstoqueMaterial, Integer> tbcQtdSeparada;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcMaterialSeparado;

    @FXML
    private TableView<Material> tbvMaterialListSeparar;

    @FXML
    private TableView<EstoqueMaterial> tbvMaterialSeparado;

    @FXML
    private RadioButton rdbObsUnica;

    @FXML
    private TextField txtQtdDesejada;

    @FXML
    private Button btnRemoverLista;

    @FXML
    private Button btnCancelarOperacao;

    @FXML
    private AnchorPane AnchorPaneValidation;

    @FXML
    private Button btnSalvarOperacao;

    @FXML
    private RadioButton rdbObsIndividual;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcLocalSeparar;

    @FXML
    private Button btnAdicionarLista;

    @FXML
    private TableColumn<EstoqueMaterial, Integer> tbcQtdSeparar;

    @FXML
    private ProgressIndicator loading;

    @FXML
    private TextField txtLocalEscolhido;

    @FXML
    private TextArea txtObs;

    @FXML
    private Button btnApagarTodasObs;

    @FXML
    private TableColumn<Material, Integer> tbcQtdMaterialSeparar;

    @FXML
    private Label txtMsgLoading;

    @FXML
    private ListView<EmprestimoEstoqueMaterial> tblMaterialObs;

    @FXML
    private TableView<EstoqueMaterial> tbvLocalListSeparar;

    @FXML
    private TextField txtMaterialEscolhido;

    @FXML
    private TableColumn<Material, String> tbcMaterialListaSeparar;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcLocalSeparado;

    List<EstoqueMaterial> estoqueSeparado = new ArrayList<>();
    List<EstoqueMaterial> estoqueASeparar = new ArrayList<>();
    List<EmprestimoEstoqueMaterial> observacao = new ArrayList<>();
    // List<String> observacoes = new ArrayList<>();
    List<Material> ListaMaterial = new ArrayList<>();
    String htmlTable = "";

    @FXML
    void btnAprovarOnAction(ActionEvent event) {

        completarTblListaMaterialSeparar(tblListaMaterial.getItems());

        new Thread() {
            @Override
            public void run() {
                estoqueASeparar = NegociosEstaticos.getNegocioEstoqueMateria().buscarTodosEstoqueMaterial();
            }
        }.start();

        for (int i = 0; i < tblListaMaterial.getItems().size(); i++) {
            observacao.clear();
        }

        tabItensSeparado.setDisable(false);
        tabAnaliseEmprestimo.setDisable(true);
        TabPanePrincipal.getSelectionModel().select(tabItensSeparado);

    }

    @FXML
    void tblMaterialObsOnMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            txtObs.setText(observacao.get(tblMaterialObs.getSelectionModel().getSelectedIndex()).getObservacao());
        }
    }

    @FXML
    void tbvLocalListSepararOnClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (tbvLocalListSeparar.getSelectionModel().getSelectedItem() != null) {
                EstoqueMaterial e = tbvLocalListSeparar.getSelectionModel().getSelectedItem();
                txtLocalEscolhido.setText(e.getLocalDescricao());
                tbvMaterialSeparado.getSelectionModel().select(null);
                txtLocalEscolhido.setStyle("-fx-border-color:darkgrey");
            }
        }
    }

    void filterTbvLocal(Material m) {
        List<EstoqueMaterial> aux = new ArrayList<>();
        for (EstoqueMaterial vo : estoqueASeparar) {
            if (vo.getId_material().getId().equals(m.getId())) {
                aux.add(vo);
            }
        }
        completarTbvLocalListSeparar(aux);
    }

    @FXML
    void btnCancelarOperacaoOnAction(ActionEvent event) {
        Alertas alert = new Alertas();
        if (alert.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação do usuário", "Deseja desistir de separar os materiais?\n\nATENÇÃO - Todas ações realizadas até o momento não serão salvas", "Sim, confirmo", "Não, quero voltar")) {
            TabPanePrincipal.getSelectionModel().select(tabAnaliseEmprestimo);
            tabAnaliseEmprestimo.setDisable(false);
            tabItensSeparado.setDisable(true);
            tabObservacoes.setDisable(true);
            txtLocalEscolhido.setText("");
            txtQtdDesejada.setText("");
            txtMaterialEscolhido.setText("");

            new Thread() {
                @Override
                public void run() {

                    btnApagarTodasObsOnAction(event);
                    for (EstoqueMaterial vo : tbvMaterialSeparado.getItems()) {
                        removeItemListSeparado(vo);
                    }
                    tbvMaterialListSeparar.getSelectionModel().select(null);
                    tbvLocalListSeparar.getSelectionModel().select(null);
                }
            }.start();

        }
    }

    @FXML
    void tbvMaterialListSepararOnMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (tbvMaterialListSeparar.getSelectionModel().getSelectedItem() != null) {
                Material m = tbvMaterialListSeparar.getSelectionModel().getSelectedItem();
                if (m.getQuantidade() > 0) {
                    new Thread() {
                        @Override
                        public void run() {

                            while (estoqueASeparar.size() == 0) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                            filterTbvLocal(m);
                        }
                    }.start();

                    txtMaterialEscolhido.setText(m.getDescricao());
                    txtLocalEscolhido.setText("");
                    txtQtdDesejada.setText("");
                    tbvMaterialSeparado.getSelectionModel().select(null);
                }
                txtMaterialEscolhido.setStyle("-fx-border-color:darkgrey");
            }
        }
    }

    void table(EmprestimoEstoqueMaterial vo) {
        htmlTable += "<tr><td>" + vo.getQtd_emprestada() + " " + vo.getId_material().getId_tipo_unidade().getSigla() + "</td><td>" + vo.getId_material().getDescricao() + "</td><td>" + vo.getId_material().getId_categoria().getDescricao() + "</td><td>" + vo.getId_estoquematerial().getId_departamento().getDescricao() + "</td><td>" + vo.getObservacao() + "</td></tr>";

    }

    @FXML
    void btnRecusarOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarAnaliseOnAction(ActionEvent event) {

        TabPanePrincipal.getSelectionModel().select(tabListaEmprestimo);
        tabAnaliseEmprestimo.setDisable(true);
        tabListaEmprestimo.setDisable(false);

    }
    //---------

    List<TblPessoaEmprestimo> ListaOf = new ArrayList();
    List<TblEmprestimoEstoque> ListaPessoaMaterial = new ArrayList();

    @FXML
    void btnAnalisarOnAction(ActionEvent event) {

        TblPessoaEmprestimo result = tblAguardandoAnalise.getSelectionModel().getSelectedItem();

        btnAprovar.setDisable(false);

        List<EmprestimoEstoqueMaterial> emp = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarTodosIdEmprestimo(result.getEmprestimo());
        List<Material> mat = NegociosEstaticos.getNegocioMaterial().buscarTodos();

        for (Material vo : mat) {
            for (EmprestimoEstoqueMaterial em : emp) {
                if (vo.getId().equals(em.getId_material().getId())) {
                    Material material = new Material();
                    material = vo;
                    material.setQuantidadeSolicitada(em.getQtd_emprestada());
                    ListaMaterial.add(material);
                }
            }
        }

        lblDtEmprestimo.setText(result.getEmprestimoDt());
        lblFinalidade.setText(result.getFinalidade());
        lblObservacao.setText(result.getEmprestimo().getObservacao());

        completarTblListaMaterial(ListaMaterial);
        tabAnaliseEmprestimo.setDisable(false);
        tabListaEmprestimo.setDisable(true);
        TabPanePrincipal.getSelectionModel().select(tabAnaliseEmprestimo);

    }

    void voltarMenu() {
        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void btnVoltarListaOnAction(ActionEvent event) {
        voltarMenu();
    }

    @FXML
    void btnSalvarOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelarOnAction(ActionEvent event) {

    }

    @FXML
    void btnAdicionarLocalOnAction(ActionEvent event) {

    }

    @FXML
    void btnEditarLocalOnAction(ActionEvent event) {

    }

    @FXML
    void btnRemoverLocalOnAction(ActionEvent event) {

    }

    @FXML
    void btnSelecionarOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarBuscarOnAction(ActionEvent event) {

    }

    void apagarObs() {

        for (EmprestimoEstoqueMaterial vo : observacao) {
            vo.setObservacao("");
        }
        txtObs.setText("");
    }

    @FXML
    void rdbObsUnicaOnAction(ActionEvent event) {
        apagarObs();
        tblMaterialObs.getSelectionModel().select(null);
        tblMaterialObs.setDisable(true);

    }

    @FXML
    void rdbObsIndividualOnAction(ActionEvent event) {

        apagarObs();
        tblMaterialObs.getSelectionModel().select(0);
        tblMaterialObs.setDisable(false);

    }

    @FXML
    void btnApagarTodasObsOnAction(ActionEvent event) {
        apagarObs();
    }

    void removeItemListSeparado(EstoqueMaterial estoqueMaterial) {
        int index = -1;
        Material material = new Material();
        for (Material vo : tbvMaterialListSeparar.getItems()) {
            index++;
            if (vo.getId().equals(estoqueMaterial.getId_material().getId())) {
                material = vo;
                break;
            }
        }
        material.setQuantidadeSolicitada(estoqueMaterial.getQuantidade_emprestada() + material.getQuantidadeSolicitada());
        tbvMaterialListSeparar.getItems().set(index, material);
        tbvMaterialListSeparar.refresh();

        EstoqueMaterial aux = new EstoqueMaterial();
        index = -1;
        for (EstoqueMaterial vo : estoqueASeparar) {
            index++;
            if (vo.getId_departamento().getId().equals(estoqueMaterial.getId_departamento().getId())
                    && vo.getId_material().getId().equals(estoqueMaterial.getId_material().getId())) {
                aux = vo;
                break;
            }
        }
        aux.setQuantidade(aux.getQuantidade() + estoqueMaterial.getQuantidade_emprestada());
        estoqueASeparar.set(index, aux);
        tbvLocalListSeparar.refresh();

        estoqueSeparado.remove(estoqueMaterial);
        completeTableMaterialSeparado(estoqueSeparado);
        txtQtdDesejada.setText("");
        txtLocalEscolhido.setText("");
        txtMaterialEscolhido.setText("");
    }

    @FXML
    void btnRemoverListaOnAction(ActionEvent event) {

        if (tbvMaterialSeparado.getSelectionModel().getSelectedItem() != null) {
            observacao.remove(tbvMaterialSeparado.getSelectionModel().getSelectedIndex());
            completarTblListMaterialObs(observacao);
            tblMaterialObs.getSelectionModel().select(null);
            txtObs.setText("");
            if (observacao.size() == 0) {
                tabObservacoes.setDisable(true);
            }
            removeItemListSeparado(tbvMaterialSeparado.getSelectionModel().getSelectedItem());

        }

    }

    @FXML
    void tbvMaterialSeparadoOnClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (tbvMaterialSeparado.getSelectionModel().getSelectedItem() != null) {
                EstoqueMaterial estoqueMaterial = tbvMaterialSeparado.getSelectionModel().getSelectedItem();
                txtLocalEscolhido.setText(estoqueMaterial.getLocalDescricao());
                txtMaterialEscolhido.setText(estoqueMaterial.getMaterialDescricao());
                txtQtdDesejada.setText("" + estoqueMaterial.getQuantidade_emprestada());

                for (Material vo : tbvMaterialListSeparar.getItems()) {
                    if (vo.getId().equals(estoqueMaterial.getId_material().getId())) {
                        tbvMaterialListSeparar.getSelectionModel().select(vo);
                        break;
                    }
                }

                filterTbvLocal(estoqueMaterial.getId_material());

                for (EstoqueMaterial vo : tbvLocalListSeparar.getItems()) {
                    if (vo.getId_departamento().getId().equals(estoqueMaterial.getId_departamento().getId())) {
                        tbvLocalListSeparar.getSelectionModel().select(vo);
                    }
                }
            }
        }
    }

    @FXML
    void btnAdicionarListaOnAction(ActionEvent event) {

        Alertas alert = new Alertas();
        int qtd = -1;
        if (!txtQtdDesejada.getText().isEmpty()) {
            qtd = Integer.parseInt(txtQtdDesejada.getText());
        }

        Material material = tbvMaterialListSeparar.getSelectionModel().getSelectedItem();
        EstoqueMaterial estoqueMaterial = tbvLocalListSeparar.getSelectionModel().getSelectedItem();

        if (!txtLocalEscolhido.getText().isEmpty() && !txtMaterialEscolhido.getText().isEmpty()
                && !txtQtdDesejada.getText().isEmpty()) {

            if (qtd <= material.getQuantidadeSolicitada()
                    && qtd <= estoqueMaterial.getQuantidade()) {

                EstoqueMaterial e = new EstoqueMaterial();
                e.setId_departamento(tbvLocalListSeparar.getSelectionModel().getSelectedItem().getId_departamento());
                e.setId_material(tbvMaterialListSeparar.getSelectionModel().getSelectedItem());
                e.setId_estoque(tbvLocalListSeparar.getSelectionModel().getSelectedItem().getId());
                e.setQuantidade_emprestada(qtd);
                Boolean isAdd = true;

                int index = -1;
                for (EstoqueMaterial vo : estoqueSeparado) {
                    index++;
                    if (vo.getId_departamento().equals(e.getId_departamento())
                            && vo.getId_material().equals(e.getId_material())
                            && vo.getId().equals(e.getId())) {
                        vo.setQuantidade_emprestada(qtd + vo.getQuantidade_emprestada());
                        EmprestimoEstoqueMaterial eem = observacao.get(index);
                        eem.setQtd_emprestada(qtd + vo.getQuantidade_emprestada());
                        observacao.set(index, eem);
                        isAdd = false;
                        break;
                    }
                }
                if (isAdd) {
                    estoqueSeparado.add(e);

                    EmprestimoEstoqueMaterial eem = new EmprestimoEstoqueMaterial();
                    eem.setId_material(material);
                    eem.setId_estoquematerial(estoqueMaterial);
                    eem.setObservacao("");
                    eem.setQtd_emprestada(qtd);
                    observacao.add(eem);
                }
                completarTblListMaterialObs(observacao);
                if (observacao.size() > 0 && tabObservacoes.isDisable()) {
                    tabObservacoes.setDisable(false);
                }

                EstoqueMaterial aux = new EstoqueMaterial();
                index = 0;
                for (int i = 0; i < estoqueASeparar.size(); i++) {
                    if (estoqueASeparar.get(i).getId_departamento().getId().equals(estoqueMaterial.getId_departamento().getId())
                            && estoqueASeparar.get(i).getId_material().getId().equals(estoqueMaterial.getId_material().getId())) {
                        aux = estoqueASeparar.get(i);
                        index = i;
                        break;
                    }
                }

                aux.setQuantidade(aux.getQuantidade() - qtd);
                estoqueASeparar.set(index, aux);

                index = tbvMaterialListSeparar.getItems().indexOf(material);
                if (index >= 0) {
                    material.setQuantidadeSolicitada(material.getQuantidadeSolicitada() - qtd);
                    tbvMaterialListSeparar.getItems().set(index, material);
                }

                completarTblListaMaterialSeparar(tbvMaterialListSeparar.getItems());
                completeTableMaterialSeparado(estoqueSeparado);
                tbvLocalListSeparar.refresh();
                txtQtdDesejada.setText("");
                txtLocalEscolhido.setText("");
                txtMaterialEscolhido.setText("");
                tbvLocalListSeparar.getSelectionModel().select(null);
                tbvMaterialListSeparar.getSelectionModel().select(null);
                txtQtdDesejada.setStyle("-fx-border-color:darkgrey");
            } else {
                alert.alerta(Alert.AlertType.WARNING, "Operação inválida", "Quantidade inválida de material");
                return;

            }

        } else {
            if (txtMaterialEscolhido.getText().isEmpty()) {
                txtMaterialEscolhido.setStyle("-fx-border-color:red");
                alert.alerta(Alert.AlertType.WARNING, "Operação inválida", "É obrigatório a seleção de um material da lista");
                return;
            } else {
                txtMaterialEscolhido.setStyle("-fx-border-color:darkgrey");
            }
            if (txtLocalEscolhido.getText().isEmpty()) {
                txtLocalEscolhido.setStyle("-fx-border-color:red");
                alert.alerta(Alert.AlertType.WARNING, "Operação inválida", "É obrigatório a seleção de um local da lista");
                return;
            } else {
                txtLocalEscolhido.setStyle("-fx-border-color:darkgrey");
            }
            if (txtQtdDesejada.getText().isEmpty()) {
                txtQtdDesejada.setStyle("-fx-border-color:red");
                alert.alerta(Alert.AlertType.WARNING, "Operação inválida", "É obrigatório informar uma quantidade válida do material");
                return;
            } else {
                txtQtdDesejada.setStyle("-fx-border-color:darkgrey");
            }

        }
    }

    private void completeTableMaterialSeparado(List<EstoqueMaterial> lista) {
        ObservableList<EstoqueMaterial> dado = FXCollections.observableArrayList();
        dado.addAll(lista);

        this.tbcLocalSeparado.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("LocalDescricao"));
        this.tbcQtdSeparada.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, Integer>("QtdEmprestadaFormat"));
        this.tbcMaterialSeparado.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("MaterialDescricao"));

        this.tbvMaterialSeparado.setItems(dado);

        tbcMaterialSeparado.setCellFactory(new Callback<TableColumn<EstoqueMaterial, String>, TableCell<EstoqueMaterial, String>>() {
            @Override
            public TableCell<EstoqueMaterial, String> call(TableColumn<EstoqueMaterial, String> p) {
                return new TableCell<EstoqueMaterial, String>() {
                    @Override
                    public void updateItem(String t, boolean empty) {
                        super.updateItem(t, empty);
                        if (t == null) {
                            setTooltip(null);
                            setText(null);
                        } else {
                            Tooltip tooltip = new Tooltip();
                            tooltip.setStyle("-fx-background-color:LightGray;");
                            EstoqueMaterial e = getTableView().getItems().get(getTableRow().getIndex());
                            tooltip.setText(e.getMaterialDescricao());
                            setTooltip(tooltip);
                            setText(t.toString());
                        }
                    }
                };
            }
        });

        tbcLocalSeparado.setCellFactory(new Callback<TableColumn<EstoqueMaterial, String>, TableCell<EstoqueMaterial, String>>() {
            @Override
            public TableCell<EstoqueMaterial, String> call(TableColumn<EstoqueMaterial, String> p) {
                return new TableCell<EstoqueMaterial, String>() {
                    @Override
                    public void updateItem(String t, boolean empty) {
                        super.updateItem(t, empty);
                        if (t == null) {
                            setTooltip(null);
                            setText(null);
                        } else {
                            Tooltip tooltip = new Tooltip();
                            tooltip.setStyle("-fx-background-color:LightGray;");
                            EstoqueMaterial e = getTableView().getItems().get(getTableRow().getIndex());
                            tooltip.setText(e.getLocalDescricao());
                            setTooltip(tooltip);
                            setText(t.toString());
                        }
                    }
                };
            }
        });

    }

    private void completarTbvLocalListSeparar(List<EstoqueMaterial> lista) {
        ObservableList<EstoqueMaterial> dado = FXCollections.observableArrayList();
        dado.addAll(lista);

        this.tbcLocalSeparar.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("LocalDescricao"));
        this.tbcQtdSeparar.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, Integer>("QtdDisponivelFormat"));

        this.tbvLocalListSeparar.setItems(dado);

        tbcLocalSeparar.setCellFactory(new Callback<TableColumn<EstoqueMaterial, String>, TableCell<EstoqueMaterial, String>>() {
            @Override
            public TableCell<EstoqueMaterial, String> call(TableColumn<EstoqueMaterial, String> p) {
                return new TableCell<EstoqueMaterial, String>() {
                    @Override
                    public void updateItem(String t, boolean empty) {
                        super.updateItem(t, empty);
                        if (t == null) {
                            setTooltip(null);
                            setText(null);
                        } else {
                            Tooltip tooltip = new Tooltip();
                            tooltip.setStyle("-fx-background-color:LightGray;");
                            EstoqueMaterial e = getTableView().getItems().get(getTableRow().getIndex());
                            tooltip.setText(e.getLocalDescricao());
                            setTooltip(tooltip);
                            setText(t.toString());
                        }
                    }
                };
            }
        });

    }

    private void completarTblListMaterialObs(List<EmprestimoEstoqueMaterial> lista) {
        ObservableList<EmprestimoEstoqueMaterial> dado = FXCollections.observableArrayList();
        dado.addAll(lista);

        tblMaterialObs.setItems(dado);

        tblMaterialObs.setCellFactory(new Callback<ListView<EmprestimoEstoqueMaterial>, ListCell<EmprestimoEstoqueMaterial>>() {

            @Override
            public ListCell<EmprestimoEstoqueMaterial> call(ListView<EmprestimoEstoqueMaterial> param) {
                ListCell<EmprestimoEstoqueMaterial> cell = new ListCell<EmprestimoEstoqueMaterial>() {
                    @Override
                    protected void updateItem(EmprestimoEstoqueMaterial item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getId_material().getDescricao() + " / " + item.getId_estoquematerial().getId_departamento().getDescricao());
                        }
                    }
                };
                return cell;
            }
        });

        tblMaterialObs.getSelectionModel().select(0);
    }

    Boolean validar() {

        Alertas alert = new Alertas();

        if (tbvMaterialListSeparar.getItems().size() > tbvMaterialSeparado.getItems().size()) {
            alert.alerta(Alert.AlertType.ERROR, "Não foi possível completar a operação", "Há materiais não separados para o empréstimo");
            return false;
        }
        for (Material vo : tbvMaterialListSeparar.getItems()) {
            if (vo.getQuantidadeSolicitada() > 0) {
                alert.alerta(Alert.AlertType.ERROR, "Não foi possível completar a operação", "Há materiais não separados para o empréstimo");
                return false;
            }
        }

        return true;
    }

    void gerarRelatorio() {

        Alertas alert = new Alertas();

        Boolean result = false;

        result = alert.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação do usuário", "Deseja gerar um PDF da listagem dos materiais?", "Sim", "Não");

        if (result) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            Stage stage = new Stage();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                GerarPDF pdf = new GerarPDF();
                try {
                    pdf.analisarEmprestimo(observacao, selectedDirectory.getAbsolutePath());
                    if (alert.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação do usuário", "Deseja gerar um comprovante de entrega dos materiais?", "Sim", "Não")) {
                        pdf.comprovanteEntrega(observacao, selectedDirectory.getAbsolutePath(), tblAguardandoAnalise.getSelectionModel().getSelectedItem().getPessoa(), ClasseDoSistemaEstatico.getPessoa());
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

    void mandarEmail() {
        String conteudo = "";
        String pattern = "dd/MM/yyyy";
        Date dt = new Date();
        SimpleDateFormat dt_format = new SimpleDateFormat(pattern);
        String remetente = "";
        String assunto = "SystemAutoNet - Empréstimo de Materiais APROVADO";

        conteudo += "<html><body>";
        conteudo += "<p align=\"center\"><b>EMPRÉSTIMO APROVADO</b></p>";
        conteudo += "<p><b>AUTORIZADO POR </b>: " + ClasseDoSistemaEstatico.getPessoa().getNome().toUpperCase() + " EM " + dt_format.format(dt) + "</p>";
        conteudo += "<p><b>USUÁRIO FAVORECIDO</b>: " + tblAguardandoAnalise.getSelectionModel().getSelectedItem().getPessoa().getNome() + "</p>";
        conteudo += "<p><b>DATA PARA EMPRÉSTIMO</b>: " + lblDtEmprestimo.getText() + "</p>";
        conteudo += "<p><b>FINALIDADE</b>: " + lblFinalidade.getText().toUpperCase() + "</p>";
        conteudo += "<p><b>OBSERVAÇÃO</b>: " + lblObservacao.getText().toUpperCase() + "</p>";
        conteudo += "<p align=\"center\"><b>LISTA DE MATERIAIS</b></p>";
        conteudo += "<p> </div>";

        htmlTable += "<style>\n"
                + "table, th, td {\n"
                + "border: 1px solid black;\n"
                + "border-collapse: collapse;\n"
                + "}\n"
                + "td {align=center;}\n"
                + "</style>";

        htmlTable += "<div><table style=\"width:100%\">";
        htmlTable += "<tr><td><b>QUANTIDADE</b></td><td><b>MATERIAL</b></td> <td><b>CATEGORIA</b> <td><b>LOCAL</b></td><td><b>OBSERVAÇÃO</b></td></tr>";

        for (int i = 0; i < tblMaterialObs.getItems().size(); i++) {
            table(tblMaterialObs.getItems().get(i));
        }
        htmlTable += "</table></div>";
        conteudo += htmlTable;
        conteudo += "<p> </p>";
        conteudo += "</body></html>";

        conteudo += "<footer><p align=\"center\">Mensagem gerada automáticamente por SystemAutoNet 1.0</p>";
        conteudo += "<p align=\"center\"><b>Não responder essa mensagem</b></p></footer>";

        EnviarEmail email = new EnviarEmail();

        List<Pessoa> users = NegociosEstaticos.getNegocioPessoa().buscarTodos();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getFuncao().equals(PerfilUsuario.ADMINISTRADOR)) {
                remetente += users.get(i).getEmail() + ",";
            }
        }
        remetente += tblAguardandoAnalise.getSelectionModel().getSelectedItem().getPessoa().getEmail();
        email.enviarEmail(remetente, conteudo, assunto);

    }

    @FXML
    private void btnSalvarOperacaoOnAction(ActionEvent event) {

        if (!validar()) {
            return;
        }

        if (rdbObsUnica.isSelected()) {
            observacao.get(0).setObservacao("OBS:.ÚNICA - " + observacao.get(0).getObservacao());
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tabObservacoes.setDisable(true);
                AnchorPaneValidation.setVisible(true);
            }
        });

        new Thread() {
            @Override
            public void run() {

                List<EstoqueMaterial> listEstoq = NegociosEstaticos.getNegocioEstoqueMateria().buscarTodosEstoqueMaterial();
                List<EmprestimoEstoqueMaterial> list = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarTodosIdEmprestimo(tblAguardandoAnalise.getSelectionModel().getSelectedItem().getEmprestimo());

                int qtd = 0;

                for (EstoqueMaterial vo : tbvMaterialSeparado.getItems()) {
                    try {

                        qtd = (int) NegociosEstaticos.getNegocioEstoqueMateria().QtdDisponivelDoMaterialPorEstoque(vo.getId_material(), vo.getId_departamento());

                        if (vo.getQuantidade_emprestada() > qtd) {
                            Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
                            Platform.runLater(() -> tabObservacoes.setDisable(false));

                            Alertas alert = new Alertas();
                            Platform.runLater(() -> alert.alerta(Alert.AlertType.ERROR, "Foi encontrado um erro na operação", "Houve um conflito de informação na base de dados\nTente novamente, caso falhar reinicie a aplicação para atualizar a base de dados local"));

                        }
                    } catch (Exception ex) {
                        Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
                        Platform.runLater(() -> tabObservacoes.setDisable(false));
                        Alertas alert = new Alertas();
                        Platform.runLater(() -> alert.alerta(Alert.AlertType.ERROR, "Foi encontrado um erro na operação", ex.getMessage()));
                        return;
                    }
                }

                if (list.size() == tbvMaterialSeparado.getItems().size()) {

                    int index = -1;
                    for (EmprestimoEstoqueMaterial vo : list) {
                        index++;
                        EmprestimoEstoqueMaterial eem = vo;
                        eem.setQtd_devolvida(0);

                        if (rdbObsUnica.isSelected()) {
                            eem.setObservacao(observacao.get(0).getObservacao());
                        } else {
                            eem.setObservacao(observacao.get(index).getObservacao());
                        }

                        for (EstoqueMaterial est : tbvMaterialSeparado.getItems()) {
                            if (est.getId_material().getId().equals(vo.getId_material().getId())) {
                                eem.setId_estoquematerial(est);
                                break;
                            }
                        }
                        try {
                            NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().salvar(eem);
                        } catch (Exception ex) {
                            Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
                            Platform.runLater(() -> tabObservacoes.setDisable(false));
                            Alertas alert = new Alertas();
                            Platform.runLater(() -> alert.alerta(Alert.AlertType.ERROR, "Foi encontrado um erro na operação", ex.getMessage()));
                            return;
                        }
                    }

                    //---
                    Emprestimo emp = NegociosEstaticos.getNegocioEmprestimo().consultarPorId(tblAguardandoAnalise.getSelectionModel().getSelectedItem().getEmprestimo());
                    emp.setId_pessoa_autoriza(ClasseDoSistemaEstatico.getPessoa());
                    emp.setStatus_emprestimo(StatusEmprestimo.APROVADO);
                    try {
                        NegociosEstaticos.getNegocioEmprestimo().salvar(emp);
                    } catch (Exception ex) {
                        Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
                        Platform.runLater(() -> tabObservacoes.setDisable(false));
                        Alertas alert = new Alertas();
                        Platform.runLater(() -> alert.alerta(Alert.AlertType.ERROR, "Foi encontrado um erro na operação", ex.getMessage()));
                        return;
                    }

                    List<EstoqueMaterial> result = new ArrayList<>();

                    for (int i = 0; i < listEstoq.size(); i++) {
                        for (int j = 0; j < tbvMaterialSeparado.getItems().size(); j++) {
                            if (listEstoq.get(i).getId().equals(tbvMaterialSeparado.getItems().get(j).getId())) {
                                EstoqueMaterial em = new EstoqueMaterial();
                                em.setId_estoque(listEstoq.get(i).getId());
                                em.setId_departamento(listEstoq.get(i).getId_departamento());
                                em.setId_material(listEstoq.get(i).getId_material());
                                em.setQuantidade(listEstoq.get(i).getQuantidade() - tbvMaterialSeparado.getItems().get(j).getQuantidade_emprestada());
                                em.setQuantidade_emprestada(listEstoq.get(i).getQuantidade_emprestada() + tbvMaterialSeparado.getItems().get(j).getQuantidade_emprestada());
                                result.add(em);
                            }
                        }
                    }
                    for (int w = 0; w < result.size(); w++) {
                        try {
                            NegociosEstaticos.getNegocioEstoqueMateria().salvar(result.get(w));
                        } catch (Exception ex) {
                            Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
                            Platform.runLater(() -> tabObservacoes.setDisable(false));
                            Alertas alert = new Alertas();
                            Platform.runLater(() -> alert.alerta(Alert.AlertType.ERROR, "Foi encontrado um erro na operação", ex.getMessage()));
                            return;
                        }
                    }

                } else {

                    for (EmprestimoEstoqueMaterial vo : list) {
                        try {
                            NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().remover(vo);
                        } catch (Exception ex) {
                            Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
                            Platform.runLater(() -> tabObservacoes.setDisable(false));
                            Alertas alert = new Alertas();
                            Platform.runLater(() -> alert.alerta(Alert.AlertType.ERROR, "Foi encontrado um erro na operação", ex.getMessage()));
                            return;
                        }
                    }
                    int index = -1;
                    for (EstoqueMaterial vo : tbvMaterialSeparado.getItems()) {
                        index++;
                        EmprestimoEstoqueMaterial eem = new EmprestimoEstoqueMaterial();
                        eem.setId_emprestimo(tblAguardandoAnalise.getSelectionModel().getSelectedItem().getEmprestimo());
                        eem.setId_estoquematerial(vo);
                        eem.setQtd_devolvida(0);
                        eem.setId_material(vo.getId_material());
                        if (rdbObsUnica.isSelected()) {
                            eem.setObservacao(observacao.get(0).getObservacao());
                        } else {
                            eem.setObservacao(observacao.get(index).getObservacao());
                        }
                        eem.setQtd_emprestada(vo.getQuantidade_emprestada());

                        try {
                            NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().salvar(eem);
                        } catch (Exception ex) {
                            Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
                            Platform.runLater(() -> tabObservacoes.setDisable(false));
                            Alertas alert = new Alertas();
                            Platform.runLater(() -> alert.alerta(Alert.AlertType.ERROR, "Foi encontrado um erro na operação", ex.getMessage()));
                            return;
                        }
                    }
                    Emprestimo emp = NegociosEstaticos.getNegocioEmprestimo().consultarPorId(tblAguardandoAnalise.getSelectionModel().getSelectedItem().getEmprestimo());
                    emp.setId_pessoa_autoriza(ClasseDoSistemaEstatico.getPessoa());
                    emp.setStatus_emprestimo(StatusEmprestimo.APROVADO);
                    try {
                        NegociosEstaticos.getNegocioEmprestimo().salvar(emp);
                    } catch (Exception ex) {
                        Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
                        Platform.runLater(() -> tabObservacoes.setDisable(false));
                        Alertas alert = new Alertas();
                        Platform.runLater(() -> alert.alerta(Alert.AlertType.ERROR, "Foi encontrado um erro na operação", ex.getMessage()));
                        return;
                    }

                    List<EstoqueMaterial> result = new ArrayList<>();

                    for (int i = 0; i < listEstoq.size(); i++) {
                        for (int j = 0; j < tbvMaterialSeparado.getItems().size(); j++) {
                            if (listEstoq.get(i).getId().equals(tbvMaterialSeparado.getItems().get(j).getId())) {
                                EstoqueMaterial em = new EstoqueMaterial();
                                em.setId_estoque(listEstoq.get(i).getId());
                                em.setId_departamento(listEstoq.get(i).getId_departamento());
                                em.setId_material(listEstoq.get(i).getId_material());
                                em.setQuantidade(listEstoq.get(i).getQuantidade() - tbvMaterialSeparado.getItems().get(j).getQuantidade_emprestada());
                                em.setQuantidade_emprestada(listEstoq.get(i).getQuantidade_emprestada() + tbvMaterialSeparado.getItems().get(j).getQuantidade_emprestada());
                                result.add(em);
                            }
                        }
                    }
                    for (int w = 0; w < result.size(); w++) {
                        try {
                            NegociosEstaticos.getNegocioEstoqueMateria().salvar(result.get(w));
                        } catch (Exception ex) {
                            Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
                            Platform.runLater(() -> tabObservacoes.setDisable(false));
                            Alertas alert = new Alertas();
                            Platform.runLater(() -> alert.alerta(Alert.AlertType.ERROR, "Foi encontrado um erro na operação", ex.getMessage()));
                            return;
                        }
                    }
                }

                mandarEmail();
                Platform.runLater(() -> AnchorPaneValidation.setVisible(false));
                Platform.runLater(() -> tabObservacoes.setDisable(false));

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gerarRelatorio();
                        Alertas alert = new Alertas();
                        if (alert.alerta(Alert.AlertType.CONFIRMATION,
                                "Aguardando a confirmação do usuário", "Deseja realizar uma nova análise de empréstimo?", "Sim", "Não")) {
                            TabPanePrincipal.getSelectionModel().select(tabListaEmprestimo);
                            tabListaEmprestimo.setDisable(false);
                            tabObservacoes.setDisable(true);
                            tabItensSeparado.setDisable(true);
                            ListaOf = new ArrayList();
                            completarTblAguardandoAnalise(ListaOf);
                            atualizarTabelaAnalise();
                            for (EstoqueMaterial vo : tbvMaterialSeparado.getItems()) {
                                removeItemListSeparado(vo);
                            }
                        } else {
                            voltarMenu();
                        }

                    }
                });

            }
        }
                .start();

    }

    private void completarTblListaMaterialSeparar(List<Material> lista) {
        ObservableList<Material> dado = FXCollections.observableArrayList();
        dado.addAll(lista);

        this.tbcMaterialListaSeparar.setCellValueFactory(new PropertyValueFactory<Material, String>("descricao"));
        this.tbcQtdMaterialSeparar.setCellValueFactory(new PropertyValueFactory<Material, Integer>("QuantidadeSolicitadaFormat"));

        this.tbvMaterialListSeparar.setItems(dado);

        tbcMaterialListaSeparar.setCellFactory(new Callback<TableColumn<Material, String>, TableCell<Material, String>>() {
            @Override
            public TableCell<Material, String> call(TableColumn<Material, String> p) {
                return new TableCell<Material, String>() {
                    @Override
                    public void updateItem(String t, boolean empty) {
                        super.updateItem(t, empty);
                        if (t == null) {
                            setTooltip(null);
                            setText(null);
                        } else {
                            Tooltip tooltip = new Tooltip();
                            tooltip.setStyle("-fx-background-color:LightGray;");
                            Material m = getTableView().getItems().get(getTableRow().getIndex());
                            tooltip.setText(m.getDescricao());
                            setTooltip(tooltip);
                            setText(t.toString());
                        }
                    }
                };
            }
        });

    }

    private void completarTblListaMaterial(List<Material> lista) {
        ObservableList<Material> dado = FXCollections.observableArrayList();
        dado.addAll(lista);

        this.tbcMaterialAnalise.setCellValueFactory(new PropertyValueFactory<Material, String>("descricao"));
        this.tbcQuantidadeAnalise.setCellValueFactory(new PropertyValueFactory<Material, Integer>("QuantidadeSolicitadaFormat"));
        this.tbcCategoriaAnalise.setCellValueFactory(new PropertyValueFactory<Material, String>("CategoriaNome"));
        this.tbcDisponibilidadeAnalise.setCellValueFactory(new PropertyValueFactory<Material, String>("QuantidadeDisponivelFormat"));

        this.tblListaMaterial.setItems(dado);

        tbcDisponibilidadeAnalise.setCellFactory(column -> {
            return new TableCell<Material, String>() {

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<Material> currentRow = getTableRow();

                    if (!isEmpty()) {

                        int qtdDisp = 0;
                        int qtdSol = 0;

                        try {
                            qtdDisp = dado.get(getTableRow().getIndex()).getQuantidadeDisponivel().intValue();
                            qtdSol = dado.get(getTableRow().getIndex()).getQuantidadeSolicitada();

                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }

                        if (qtdSol <= qtdDisp) {
                            currentRow.setStyle("-fx-background-color:lightgreen");
                        } else {
                            currentRow.setStyle("-fx-background-color:lightcoral");
                            btnAprovar.setDisable(true);
                        }

                    }
                }
            };
        });

    }

    private void completarTblAguardandoAnalise(List<TblPessoaEmprestimo> lista) {

        ObservableList<TblPessoaEmprestimo> dado = FXCollections.observableArrayList();

        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }

        this.tbcPessoa.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("Nome"));
        this.tbcDtEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("EmprestimoDt"));
        this.tbcFinalidade.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("Finalidade"));
        this.tblAguardandoAnalise.setItems(dado);

    }

    void atualizarTabelaAnalise() {
        List<EmprestimoEstoqueMaterial> empm = new ArrayList();

        List<Pessoa> listpessoa = NegociosEstaticos.getNegocioPessoa().buscarTodos();

        for (Pessoa vo : listpessoa) {
            if (vo.getId() == ClasseDoSistemaEstatico.getPessoa().getId()) {
                listpessoa.remove(vo);
                break;
            }
        }
        for (Pessoa vo : listpessoa) {
            List<Emprestimo> emp = NegociosEstaticos.getNegocioEmprestimo().buscarPorIdPessoaStatusESPERANDO_ANALISE(vo);
            for (Emprestimo voEmp : emp) {
                empm = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarPorNaoDevolvido(voEmp);
                if (empm.size() > 0) {
                    TblPessoaEmprestimo tb = new TblPessoaEmprestimo();
                    tb.setEmprestimo(voEmp);
                    tb.setPessoa(vo);
                    ListaOf.add(tb);
                }
            }
        }
        completarTblAguardandoAnalise(ListaOf);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TabPanePrincipal.getSelectionModel().select(tabListaEmprestimo);
        tabListaEmprestimo.setDisable(false);
        tabItensSeparado.setDisable(true);
        tabAnaliseEmprestimo.setDisable(true);
        tabObservacoes.setDisable(true);
        rdbObsIndividual.setSelected(true);

        new Thread() {
            @Override
            public void run() {

                atualizarTabelaAnalise();
            }
        }.start();

        txtObs.focusedProperty().addListener(new ChangeListener<Boolean>() {
            int position = -1;

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                if (t1) {
                    position = tblMaterialObs.getSelectionModel().getSelectedIndex();

                }
                if (!t1) {

                    if (position != -1) {
                        EmprestimoEstoqueMaterial eem = observacao.get(position);
                        eem.setObservacao(txtObs.getText());
                        observacao.set(position, eem);
                    }

                }
                if (rdbObsUnica.isSelected()) {
                    EmprestimoEstoqueMaterial eem = observacao.get(0);
                    eem.setObservacao(txtObs.getText().toUpperCase());
                    observacao.set(0, eem);

                }

            }
        });

        txtQtdDesejada.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }
            Mask mask = new Mask();
            c.setText(mask.OnlyInt(c.getText()));
            return c;
        }));

    }

}
