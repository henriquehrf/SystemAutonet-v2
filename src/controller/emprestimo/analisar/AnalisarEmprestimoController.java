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
import gui.SystemAutonet;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.EntradaMaterial;
import vo.EstoqueMaterial;
import vo.Local;
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
    private ListView<?> tblMaterialObs;

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

    @FXML
    void btnAprovarOnAction(ActionEvent event) {

        completarTblListaMaterialSeparar(tblListaMaterial.getItems());

        new Thread() {
            @Override
            public void run() {
                estoqueASeparar = NegociosEstaticos.getNegocioEstoqueMateria().buscarTodosEstoqueMaterial();
            }
        }.start();

    }

    @FXML
    void tbvLocalListSepararOnClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (tbvLocalListSeparar.getSelectionModel().getSelectedItem() != null) {
                EstoqueMaterial e = tbvLocalListSeparar.getSelectionModel().getSelectedItem();
                txtLocalEscolhido.setText(e.getLocalDescricao());
                tbvMaterialSeparado.getSelectionModel().select(null);
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
            }
        }
    }

    @FXML
    void btnRecusarOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarAnaliseOnAction(ActionEvent event) {

    }
    //---------

    List<TblPessoaEmprestimo> ListaOf = new ArrayList();
    List<TblEmprestimoEstoque> ListaPessoaMaterial = new ArrayList();

    @FXML
    void btnAnalisarOnAction(ActionEvent event) {

        TblPessoaEmprestimo result = tblAguardandoAnalise.getSelectionModel().getSelectedItem();

        List<EmprestimoEstoqueMaterial> emp = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarTodosIdEmprestimo(result.getEmprestimo());
        List<Material> mat = NegociosEstaticos.getNegocioMaterial().buscarTodos();
        List<Material> tblListaMaterial = new ArrayList<>();
        for (Material vo : mat) {
            for (EmprestimoEstoqueMaterial em : emp) {
                if (vo.getId().equals(em.getId_material().getId())) {
                    Material material = new Material();
                    material = vo;
                    material.setQuantidadeSolicitada(em.getQtd_emprestada());
                    tblListaMaterial.add(material);
                }
            }
        }

        lblDtEmprestimo.setText(result.getEmprestimoDt());
        lblFinalidade.setText(result.getFinalidade());
        lblObservacao.setText(result.getEmprestimo().getObservacao());

        completarTblListaMaterial(tblListaMaterial);
        tabAnaliseEmprestimo.setDisable(false);

    }

    @FXML
    void btnVoltarListaOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
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

    @FXML
    void btnRemoverListaOnAction(ActionEvent event) {

        if (tbvMaterialSeparado.getSelectionModel().getSelectedItem() != null) {
            EstoqueMaterial estoqueMaterial = tbvMaterialSeparado.getSelectionModel().getSelectedItem();

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

        if (!txtLocalEscolhido.getText().isEmpty() && !txtMaterialEscolhido.getText().isEmpty()
                && !txtQtdDesejada.getText().isEmpty()) {

            int qtd = Integer.parseInt(txtQtdDesejada.getText());
            Material material = tbvMaterialListSeparar.getSelectionModel().getSelectedItem();
            EstoqueMaterial estoqueMaterial = tbvLocalListSeparar.getSelectionModel().getSelectedItem();

            if (qtd <= material.getQuantidadeSolicitada()
                    && qtd <= estoqueMaterial.getQuantidade()) {

                EstoqueMaterial e = new EstoqueMaterial();
                e.setId_departamento(tbvLocalListSeparar.getSelectionModel().getSelectedItem().getId_departamento());
                e.setId_material(tbvMaterialListSeparar.getSelectionModel().getSelectedItem());
                e.setQuantidade_emprestada(qtd);
                estoqueSeparado.add(e);

                EstoqueMaterial aux = new EstoqueMaterial();
                int index = 0;
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
            }

        }
    }

    private void completeTableMaterialSeparado(List<EstoqueMaterial> lista) {
        ObservableList<EstoqueMaterial> dado = FXCollections.observableArrayList();
        dado.addAll(lista);

        this.tbcLocalSeparado.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("LocalDescricao"));
        this.tbcQtdSeparada.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, Integer>("Quantidade_emprestada"));
        this.tbcMaterialSeparado.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("MaterialDescricao"));

        this.tbvMaterialSeparado.setItems(dado);
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
                            tooltip.setStyle("-fx-background-color:LightGreen;");
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
                            tooltip.setStyle("-fx-background-color:LightGreen;");
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TabPanePrincipal.getSelectionModel().select(tabListaEmprestimo);
        tabListaEmprestimo.setDisable(false);
        tabAnaliseEmprestimo.setDisable(true);
        tabItensSeparado.setDisable(false);

        new Thread() {
            @Override
            public void run() {

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
        }.start();

    }

}
