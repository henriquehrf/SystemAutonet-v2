/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.emprestimo.devolver;

import classesAuxiliares.ClasseDoSistemaEstatico;
import classesAuxiliares.NegociosEstaticos;
import classesAuxiliares.TblEmprestimoEstoque;
import classesAuxiliares.TblPessoaEmprestimo;
import controller.PrincipalController;
import enumm.StatusEmprestimo;
import gui.SystemAutonet;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.Material;
import vo.Pessoa;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class DevolverEmprestimoController {

    @FXML
    private Button btnBaixarItensEmprestimo;

    @FXML
    private Button btnAdicionarLocal;

    @FXML
    private Button btnBuscarLocalizarEstoque;

    @FXML
    private Button btnCancelarInformarEstoque;

    @FXML
    private Tab tabBuscarMaterial;

    @FXML
    private TableColumn<Emprestimo, String> tbcFinalidadeBuscarPorEmprestimo;

    @FXML
    private Button btnBuscarEmprestimo;

    @FXML
    private TextArea txtObservacao;

    @FXML
    private TableColumn<Pessoa, String> tbcMaterialInformarEstoque;

    @FXML
    private DatePicker dtpDtFinal;

    @FXML
    private TableColumn<Emprestimo, String> tbcPessoaBuscarEmprestimo;

    @FXML
    private TableColumn<Emprestimo, String> tbcResponsavelLocalizarEstoque;

    @FXML
    private Button btnDevolverItensEmprestimo;

    @FXML
    private Button btnVoltarItensEmprestimo;

    @FXML
    private RadioButton rdbFinalidade;

    @FXML
    private RadioButton rdbPessoa;

    @FXML
    private TableColumn<Emprestimo, String> tbcDtEmprestimoBuscarEmprestimo;

    @FXML
    private TextField txtBuscadorEmprestimo;

    @FXML
    private TextField txtBuscadorLocalizarEstoque;

    @FXML
    private Button btnConsultarBuscarEmprestimo;

    @FXML
    private TableColumn<?, ?> tbcNumeroLocalizarEstoque;

    @FXML
    private Button btnEditarLocal;

    @FXML
    private ToggleGroup Filtro;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcCategoriaItensEmprestimo;

    @FXML
    private TableColumn<?, ?> tbcDescricaoLocalInformarEstoque;

    @FXML
    private TableColumn<TblPessoaEmprestimo, String> tbcMaterialItensEmprestimo;

    @FXML
    private TableColumn<TblPessoaEmprestimo, Number> tbcQtdItensEmprestimo;

    @FXML
    private Button btnSalvarInformarEstoque;

    @FXML
    private TableView<?> tblPrincipalBuscarMaterial;

    @FXML
    private Button btnSelecionarLocalizarEstoque;

    @FXML
    private RadioButton rdbDescricao;

    @FXML
    private Button btnVoltarObservacao;

    @FXML
    private Label lblDescricao;

    @FXML
    private TableColumn<?, ?> tbcDescricaoLocalizarEstoque;

    @FXML
    private RadioButton rdbNumero;

    @FXML
    private Tab tabItensEmprestimo;

    @FXML
    private Tab tabObservacao;

    @FXML
    private TableView<TblEmprestimoEstoque> tblPrincipalItensEmprestimo;

    @FXML
    private Button btnVoltarBuscarEmprestimo;

    @FXML
    private DatePicker dtpDtInicial;

    @FXML
    private Tab tabBuscarEmprestimo;

    @FXML
    private TableView<Emprestimo> tblPrincipalBuscarEmprestimo;

    @FXML
    private Button btnVoltarLocalizarEstoque;

    @FXML
    private Button btnRemoverLocal;

    @FXML
    private Button btnImprimirInformarEstoque;

    @FXML
    private TableColumn<?, ?> tbcDepartamentoLocalizarEstoque;

    @FXML
    private Tab tabInformarEstoque;

    @FXML
    private Button btnSalvarObservacao;

    @FXML
    private TabPane PanePrincipal;

    @FXML
    private RadioButton rdbPessoaResponsavel;

    @FXML
    private TableColumn<?, ?> tbcQtdInformarEstoque;

    @FXML
    private TableView<?> tblPrincipalInformarEstoque;

    List<Emprestimo> ListAllEmprestimo = new ArrayList<>();

    @FXML
    void rdbPessoaOnAction(ActionEvent event) {
        buscar();
    }

    @FXML
    void rdbFinalidadeOnAction(ActionEvent event) {
        buscar();
    }

    void buscar() {
        new Thread() {
            @Override
            public void run() {
                String txt = txtBuscadorEmprestimo.getText();
                List<Emprestimo> aux = new ArrayList<>();
                List<Emprestimo> result = new ArrayList<>();
                if (rdbPessoa.isSelected()) {
                    for (Emprestimo vo : ListAllEmprestimo) {
                        if (vo.getId_pessoa_solicita().getNome().toUpperCase().contains(txt.toUpperCase())) {
                            aux.add(vo);
                        }
                    }
                }
                if (rdbFinalidade.isSelected()) {
                    for (Emprestimo vo : ListAllEmprestimo) {
                        if (vo.getFinalidade().toUpperCase().contains(txt.toUpperCase())) {
                            aux.add(vo);
                        }
                    }
                }

                if (dtpDtFinal.getValue() != null && dtpDtInicial.getValue() != null) {
                    Date dtInicial = Date.from(dtpDtInicial.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    Date dtFinal = Date.from(dtpDtFinal.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

                    for (Emprestimo vo : aux) {
                        if ((vo.getDt_emprestimo().after(dtInicial) || vo.getDt_emprestimo().equals(dtInicial)) && (vo.getDt_emprestimo().before(dtFinal) || vo.getDt_emprestimo().equals(dtFinal))) {
                            result.add(vo);
                        }
                    }
                }

                if (dtpDtInicial.getValue() != null && dtpDtFinal.getValue() == null) {
                    Date dtInicial = Date.from(dtpDtInicial.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    for (Emprestimo vo : aux) {
                        if (vo.getDt_emprestimo().after(dtInicial) || vo.getDt_emprestimo().equals(dtInicial)) {
                            result.add(vo);
                        }
                    }
                }

                if (dtpDtFinal.getValue() != null && dtpDtInicial.getValue() == null) {
                    Date dtFinal = Date.from(dtpDtFinal.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    for (Emprestimo vo : aux) {
                        if (vo.getDt_emprestimo().before(dtFinal) || vo.getDt_emprestimo().equals(dtFinal)) {
                            result.add(vo);
                        }
                    }
                }
                if (dtpDtFinal.getValue() != null || dtpDtInicial.getValue() != null) {
                    completarTabelaTblPrincipalBuscarEmprestimo(result);
                } else {
                    completarTabelaTblPrincipalBuscarEmprestimo(aux);
                }

            }
        }.start();
    }

    @FXML
    void btnBuscarEmprestimoOnAction(ActionEvent event) {

        buscar();
    }

    @FXML
    void btnBuscarEmprestimoOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buscar();
        }
    }

    @FXML
    void dtpDtInicialOnAction(ActionEvent event) {
        buscar();
    }

    @FXML
    void dtpDtFinalOnAction(ActionEvent event) {
        buscar();
    }

    @FXML
    void btnDevolverItensEmprestimoOnAction(ActionEvent event) {

    }

    @FXML
    void btnBaixarItensEmprestimoOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarItensEmprestimoOnAction(ActionEvent event) {
        PanePrincipal.getSelectionModel().select(tabBuscarEmprestimo);
        tabBuscarEmprestimo.setDisable(false);
        tabItensEmprestimo.setDisable(true);
        tabInformarEstoque.setDisable(true);
        tabBuscarMaterial.setDisable(true);
        tabObservacao.setDisable(true);
    }

    @FXML
    void btnSalvarInformarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnImprimirInformarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarBuscarEmprestimo_onAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnCancelarInformarEstoqueOnAction(ActionEvent event) {

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
    void btnSelecionarLocalizarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarLocalizarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnBuscarLocalizarEstoqueOnAction(ActionEvent event) {

    }

    @FXML
    void btnSalvarObservacaoOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarObservacaoOnAction(ActionEvent event) {

    }

    @FXML
    void btnConsultarBuscarEmprestimo_onAction(ActionEvent event) {

//        ListaPessoaMaterial.clear();
//        for (TblPessoaEmprestimo vo : ListaOf) {
//            if (vo.getEmprestimo() == tblPrincipalBuscarEmprestimo.getSelectionModel().getSelectedItem().getEmprestimo()) {
//                List<EmprestimoEstoqueMaterial> list = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarPorNaoDevolvido(vo.getEmprestimo());
//
//                for (EmprestimoEstoqueMaterial voe : list) {
//                    TblEmprestimoEstoque EE = new TblEmprestimoEstoque();
//                    EE.setEmp(vo.getEmprestimo());
//                    EE.setEmpEstoque(voe);
//                    ListaPessoaMaterial.add(EE);
//                }
//
//            }
//        }
//        completartblPrincipalItensEmprestimo(ListaPessoaMaterial);
//        tabBuscarEmprestimo.setDisable(true);
//        tabItensEmprestimo.setDisable(false);
//        tabInformarEstoque.setDisable(true);
//        tabBuscarMaterial.setDisable(true);
//        tabObservacao.setDisable(true);
//        PanePrincipal.getSelectionModel().select(tabItensEmprestimo);
    }

    void carregarTabelaEmprestimo() {

        new Thread() {
            @Override
            public void run() {
                List<Emprestimo> listEmprestimo = NegociosEstaticos.getNegocioEmprestimo().buscarPorTodos();

                for (Emprestimo vo : listEmprestimo) {
                    if (!vo.getId_pessoa_solicita().getId().equals(ClasseDoSistemaEstatico.getPessoa().getId())) {
                        if (vo.getStatus_emprestimo().equals(StatusEmprestimo.APROVADO)) {
                            ListAllEmprestimo.add(vo);
                        }
                    }
                }

                completarTabelaTblPrincipalBuscarEmprestimo(ListAllEmprestimo);
            }
        }.start();
    }

    public void initialize() {
        PanePrincipal.getSelectionModel().select(tabBuscarEmprestimo);
        tabBuscarEmprestimo.setDisable(false);
        tabItensEmprestimo.setDisable(true);
        tabInformarEstoque.setDisable(true);
        tabBuscarMaterial.setDisable(true);
        tabObservacao.setDisable(true);
        rdbPessoa.setSelected(true);

        carregarTabelaEmprestimo();

        // TODO
    }

    private void completartblPrincipalItensEmprestimo(List<TblEmprestimoEstoque> lista) {

        ObservableList<TblEmprestimoEstoque> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }

        this.tbcMaterialItensEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("Material"));
        this.tbcQtdItensEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, Number>("Quantidade"));
        this.tbcCategoriaItensEmprestimo.setCellValueFactory(new PropertyValueFactory<TblPessoaEmprestimo, String>("Categoria"));
        this.tblPrincipalItensEmprestimo.setItems(dado);

    }

    private void completarTabelaTblPrincipalBuscarEmprestimo(List<Emprestimo> lista) {
        ObservableList<Emprestimo> dado = FXCollections.observableArrayList();
        dado.addAll(lista);
        this.tbcPessoaBuscarEmprestimo.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("NomePessoaSolicita"));
        this.tbcDtEmprestimoBuscarEmprestimo.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("Dt_emprestimoString"));
        this.tbcFinalidadeBuscarPorEmprestimo.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("Finalidade"));

        Comparator<Emprestimo> cmp = new Comparator<Emprestimo>() {
            @Override
            public int compare(Emprestimo emp1, Emprestimo emp2) {
                int x = emp1.getDt_emprestimo().compareTo(emp2.getDt_emprestimo());
                if (x != 0) {
                    return x;
                } else {
                    Date x1 = ((Emprestimo) emp1).getDt_emprestimo();
                    Date x2 = ((Emprestimo) emp2).getDt_emprestimo();
                    return x1.compareTo(x2);
                }
            }

        };
        Collections.sort(dado, cmp);

        this.tblPrincipalBuscarEmprestimo.setItems(dado);
    }

}
