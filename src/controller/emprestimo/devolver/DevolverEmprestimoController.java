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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
import utilitarios.Alertas;
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
public class DevolverEmprestimoController {

    @FXML
    private TableColumn<Emprestimo, String> tbcFinalidadeBuscarPorEmprestimo;

    @FXML
    private Button btnBuscarEmprestimo;

    @FXML
    private DatePicker dtpDtFinal;

    @FXML
    private TableColumn<Emprestimo, String> tbcPessoaBuscarEmprestimo;

    @FXML
    private RadioButton rdbFinalidade;

    @FXML
    private RadioButton rdbPessoa;

    @FXML
    private TableColumn<Emprestimo, String> tbcDtEmprestimoBuscarEmprestimo;

    @FXML
    private TextField txtBuscadorEmprestimo;

    @FXML
    private Button btnConsultarBuscarEmprestimo;

    @FXML
    private ToggleGroup Filtro;

    @FXML
    private Button btnVoltarBuscarEmprestimo;

    @FXML
    private DatePicker dtpDtInicial;

    @FXML
    private Tab tabBuscarEmprestimo;

    @FXML
    private TableView<Emprestimo> tblPrincipalBuscarEmprestimo;

    @FXML
    private TabPane PanePrincipal;

    @FXML
    private TableView<EmprestimoEstoqueMaterial> tblListaMaterial;

    @FXML
    private TableColumn<EmprestimoEstoqueMaterial, String> tbcMaterialAnalise;

    @FXML
    private TableColumn<EmprestimoEstoqueMaterial, String> tbcLocalOrigem;

    @FXML
    private TableColumn<EmprestimoEstoqueMaterial, String> tbcQuantidadeAnalise;

    @FXML
    private TableColumn<EmprestimoEstoqueMaterial, String> tbcCategoriaAnalise;

    @FXML
    private Tab tabAnaliseMaterial;

    @FXML
    private Label lblDtEmprestimo;

    @FXML
    private Label lblObservacao;

    @FXML
    private Label lblFinalidade;

    @FXML
    private Button btnDevolver;

    @FXML
    private Button btnRetornar;

    @FXML
    void btnRetornarOnAction(ActionEvent event) {

        tabBuscarEmprestimo.setDisable(false);
        tabAnaliseMaterial.setDisable(true);
        tblListaMaterial.getItems().clear();
        tblListaMaterial.refresh();
        PanePrincipal.getSelectionModel().select(tabBuscarEmprestimo);

    }

    void devolucaoAutomatica() {
        new Thread() {
            @Override
            public void run() {
                List<EmprestimoEstoqueMaterial> devolucao = tblListaMaterial.getItems();
                List<EstoqueMaterial> estoque = NegociosEstaticos.getNegocioEstoqueMateria().buscarTodosEstoqueMaterial();

                for (EmprestimoEstoqueMaterial vo : devolucao) {
                    EstoqueMaterial estoqueMaterial = new EstoqueMaterial();
                    EmprestimoEstoqueMaterial eem = vo;
                    for (EstoqueMaterial est : estoque) {
                        if (vo.getId_estoquematerial().getId().equals(est.getId())) {
                            estoqueMaterial = est;
                            estoqueMaterial.setQuantidade(estoqueMaterial.getQuantidade() + vo.getQtd_emprestada());
                            estoqueMaterial.setQuantidade_emprestada(estoqueMaterial.getQuantidade_emprestada() - vo.getQtd_emprestada());
                            try {
                                NegociosEstaticos.getNegocioEstoqueMateria().salvar(estoqueMaterial);
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                            break;
                        }
                    }
                    
                    eem.setDt_devolucao(new Date());
                    eem.setQtd_devolvida(eem.getQtd_emprestada());
                    try {
                        NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().salvar(eem);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage()); 
                    }

                }

                Emprestimo emprestimo = tblPrincipalBuscarEmprestimo.getSelectionModel().getSelectedItem();

                emprestimo.setStatus_emprestimo(StatusEmprestimo.FINALIZADO);
                try {
                    NegociosEstaticos.getNegocioEmprestimo().salvar(emprestimo);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alertas alerta = new Alertas();
                        alerta.alerta(Alert.AlertType.INFORMATION, "Operação realizada com sucesso", "A devolução dos materiais foi realizada com sucesso");
                        Boolean result = alerta.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação do usuário", "Deseja realizar uma nova revolução?", "Sim", "Não");
                        if (result) {
                            carregarTabelaEmprestimo();
                            PanePrincipal.getSelectionModel().select(tabBuscarEmprestimo);
                            tabBuscarEmprestimo.setDisable(false);
                            tabAnaliseMaterial.setDisable(true);

                        } else {
                            voltar();
                        }

                    }
                });

            }
        }.start();

    }

    @FXML
    void btnDevolverOnAction(ActionEvent event) {

        Alertas alert = new Alertas();

        Boolean result = alert.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação de uma opção", "Qual o método de devolução executar?", "Automático", "Manual");
        if (result) {
            result = alert.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação do usuário", "Foi selecionado o método AUTOMÁTICO.\nNo método automático TODOS os materiais irão ser devolvidos em seu local de origem antes do empréstimo.\nDeseja "
                    + "continuar?", "Sim", "Não");

            if (result) {
                devolucaoAutomatica();
            }
        } else {
            System.out.println("Manual");
        }

    }

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
    void btnVoltarBuscarEmprestimo_onAction(ActionEvent event) {
        voltar();
    }

    void completarTabelaMaterial(List<EmprestimoEstoqueMaterial> lista) {

        ObservableList<EmprestimoEstoqueMaterial> dado = FXCollections.observableArrayList();
        dado.addAll(lista);
        this.tbcMaterialAnalise.setCellValueFactory(new PropertyValueFactory<EmprestimoEstoqueMaterial, String>("NomeMaterial"));
        this.tbcQuantidadeAnalise.setCellValueFactory(new PropertyValueFactory<EmprestimoEstoqueMaterial, String>("Qtd_emprestadaFormat"));
        this.tbcCategoriaAnalise.setCellValueFactory(new PropertyValueFactory<EmprestimoEstoqueMaterial, String>("NomeCategoria"));
        this.tbcLocalOrigem.setCellValueFactory(new PropertyValueFactory<EmprestimoEstoqueMaterial, String>("LocalOrigemFormat"));

        tblListaMaterial.setItems(dado);
    }

    void completarTableConsultaMaterial() {
        Emprestimo emp = tblPrincipalBuscarEmprestimo.getSelectionModel().getSelectedItem();

        new Thread() {
            @Override
            public void run() {
                List<EmprestimoEstoqueMaterial> mat = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarTodosIdEmprestimo(emp);
                completarTabelaMaterial(mat);
            }
        }.start();

        lblDtEmprestimo.setText(emp.getDt_emprestimoString());
        lblFinalidade.setText(emp.getFinalidade());
        lblObservacao.setText(emp.getObservacao());

    }

    @FXML
    void btnConsultarBuscarEmprestimo_onAction(ActionEvent event) {

        if (tblPrincipalBuscarEmprestimo.getSelectionModel().getSelectedItem() != null) {
            completarTableConsultaMaterial();
            tabBuscarEmprestimo.setDisable(true);
            tabAnaliseMaterial.setDisable(false);
            PanePrincipal.getSelectionModel().select(tabAnaliseMaterial);
        }

    }

    void carregarTabelaEmprestimo() {

        new Thread() {
            @Override
            public void run() {
                ListAllEmprestimo.clear();
                List<Emprestimo> listEmprestimo = NegociosEstaticos.getNegocioEmprestimo().buscarPorTodos();

                for (Emprestimo vo : listEmprestimo) {
                    if (!vo.getId_pessoa_solicita().getId().equals(ClasseDoSistemaEstatico.getPessoa().getId())) {
                        if (vo.getStatus_emprestimo().equals(StatusEmprestimo.APROVADO)) {
                            ListAllEmprestimo.add(vo);
                        }
                    }
                }

                completarTabelaTblPrincipalBuscarEmprestimo(ListAllEmprestimo);
                tblPrincipalBuscarEmprestimo.refresh();
            }
        }.start();
    }

    public void initialize() {
        PanePrincipal.getSelectionModel().select(tabBuscarEmprestimo);
        tabBuscarEmprestimo.setDisable(false);
        tabAnaliseMaterial.setDisable(true);
        rdbPessoa.setSelected(true);

        carregarTabelaEmprestimo();

        // TODO
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
