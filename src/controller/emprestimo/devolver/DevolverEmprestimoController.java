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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import utilitarios.Alertas;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.EstoqueMaterial;
import vo.Local;
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
    private TableView<Material> tbvMaterialListSeparar;

    @FXML
    private TableColumn<Material, String> tbcQtdMaterialSeparar;

    @FXML
    private TableColumn<Material, String> tbcMaterialListaSeparar;

    @FXML
    private TableView<EstoqueMaterial> tbvLocalListSeparar;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcQtdSeparar;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcLocalSeparar;

    @FXML
    private TextField txtQtdDesejada;

    @FXML
    private TextField txtMaterialEscolhido;

    @FXML
    private TextField txtLocalEscolhido;

    @FXML
    private TableView<EstoqueMaterial> tbvMaterialSeparado;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcQtdSeparada;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcMaterialSeparado;

    @FXML
    private TableColumn<EstoqueMaterial, String> tbcLocalSeparado;

    @FXML
    private Tab tabItensSeparado;

    @FXML
    private Button btnCancelarOperacao;

    @FXML
    private Button btnSalvarOperacao;

    @FXML
    private Button btnAdicionarLista;

    @FXML
    private Button btnRemoverLista;

    List<EstoqueMaterial> todosEstoqueMaterial = new ArrayList<>();
    List<EstoqueMaterial> estoqueMaterialSeparado = new ArrayList<>();
    Alertas alerta = new Alertas();

    @FXML
    void btnRemoverListaOnAction(ActionEvent event) {

        if (tbvMaterialSeparado.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        EstoqueMaterial estoqueSeparado = tbvMaterialSeparado.getSelectionModel().getSelectedItem();
        int index = tbvMaterialSeparado.getSelectionModel().getSelectedIndex();

        for (Material vo : tbvMaterialListSeparar.getItems()) {
            if (vo.getId().equals(estoqueSeparado.getId_material().getId())) {
                vo.setQuantidadeSolicitada(vo.getQuantidadeSolicitada() + estoqueSeparado.getQuantidade());
                break;
            }
        }
        tbvMaterialListSeparar.refresh();

        for (EstoqueMaterial vo : tbvLocalListSeparar.getItems()) {
            if (vo.getId().equals(estoqueSeparado.getId())) {
                vo.setQuantidade(vo.getQuantidade() - estoqueSeparado.getQuantidade());
                break;
            }
        }
        tbvLocalListSeparar.refresh();

        estoqueMaterialSeparado.remove(index);
        tbvMaterialSeparado.getItems().remove(estoqueSeparado);
        tbvMaterialSeparado.refresh();

        txtLocalEscolhido.setText("");
        txtMaterialEscolhido.setText("");
        txtQtdDesejada.setText("");

        tbvLocalListSeparar.getSelectionModel().select(null);
        tbvMaterialListSeparar.getSelectionModel().select(null);

    }

    @FXML
    void btnAdicionarListaOnAction(ActionEvent event) {

        try {
            if (txtQtdDesejada.getText().isEmpty() || txtLocalEscolhido.getText().isEmpty() || txtMaterialEscolhido.getText().isEmpty()) {
                return;
            }

            int qtd = Integer.parseInt(txtQtdDesejada.getText());
            if (qtd <= 0) {
                return;
            }
            Material material = tbvMaterialListSeparar.getSelectionModel().getSelectedItem();
            EstoqueMaterial estoqueLocal = tbvLocalListSeparar.getSelectionModel().getSelectedItem();
            EstoqueMaterial estq = new EstoqueMaterial();
            Boolean isAdd = true;
            estq.setId_estoque(estoqueLocal.getId());
            estq.setId_departamento(estoqueLocal.getId_departamento());
            estq.setId_material(estoqueLocal.getId_material());
            estq.setQuantidade_emprestada(estoqueLocal.getQuantidade_emprestada());
            estq.setQuantidade(qtd);
            int index = -1;
            if (qtd <= material.getQuantidadeSolicitada()) {
                index = tbvMaterialListSeparar.getItems().indexOf(material);
                material.setQuantidadeSolicitada(material.getQuantidadeSolicitada() - qtd);
                tbvMaterialListSeparar.getItems().set(index, material);
                tbvMaterialListSeparar.refresh();

                index = tbvLocalListSeparar.getItems().indexOf(estoqueLocal);
                estoqueLocal.setQuantidade(estoqueLocal.getQuantidade() + qtd);
                tbvLocalListSeparar.getItems().set(index, estoqueLocal);
                tbvLocalListSeparar.refresh();

                for (EstoqueMaterial vo : estoqueMaterialSeparado) {
                    if (vo.getId().equals(estoqueLocal.getId())) {
                        vo.setQuantidade(vo.getQuantidade() + qtd);
                        isAdd = false;
                    }
                }
                if (isAdd) {
                    estoqueMaterialSeparado.add(estq);
                }
                completarTbvEstoqueSeparado(estoqueMaterialSeparado);
                tbvMaterialSeparado.refresh();

                txtLocalEscolhido.setText("");
                txtMaterialEscolhido.setText("");
                txtQtdDesejada.setText("");

                tbvLocalListSeparar.getSelectionModel().select(null);
                tbvMaterialListSeparar.getSelectionModel().select(null);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    void atualizarTabelaLocal() {

        Material material = tbvMaterialListSeparar.getSelectionModel().getSelectedItem();
        List<EstoqueMaterial> estoqueLocal = new ArrayList<>();
        for (EstoqueMaterial vo : todosEstoqueMaterial) {
            if (material.getId().equals(vo.getId_material().getId())) {
                estoqueLocal.add(vo);
            }
        }

        completaTabelaEstoqueLocal(estoqueLocal);

    }

    @FXML
    void tbvMaterialListSepararOnMouseClicked(MouseEvent event) {

        if (tbvMaterialListSeparar.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        txtMaterialEscolhido.setText(tbvMaterialListSeparar.getSelectionModel().getSelectedItem().getDescricao());
        txtLocalEscolhido.setText("");
        txtQtdDesejada.setText("");
        tbvLocalListSeparar.getSelectionModel().select(null);
        if (event.getButton() == MouseButton.PRIMARY) {
            new Thread() {
                @Override
                public void run() {
                    atualizarTabelaLocal();
                }
            }.start();

        }

    }

    @FXML
    void tbvLocalListSepararOnClicked(MouseEvent event) {

        if (event.getButton() == MouseButton.PRIMARY) {
            if (tbvLocalListSeparar.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            txtLocalEscolhido.setText(tbvLocalListSeparar.getSelectionModel().getSelectedItem().getLocalDescricao());
        }

    }

    @FXML
    void tbvMaterialSeparadoOnClicked(MouseEvent event) {

        if (event.getButton() == MouseButton.PRIMARY) {
            if (tbvMaterialSeparado.getSelectionModel().getSelectedItem() != null) {

                EstoqueMaterial estoque = tbvMaterialSeparado.getSelectionModel().getSelectedItem();
                new Thread() {
                    @Override
                    public void run() {
                        for (Material vo : tbvMaterialListSeparar.getItems()) {
                            if (vo.getId().equals(estoque.getId_material().getId())) {
                                tbvMaterialListSeparar.getSelectionModel().select(vo);
                                break;
                            }
                        }
                        atualizarTabelaLocal();
                        for (EstoqueMaterial vo : tbvLocalListSeparar.getItems()) {
                            if (vo.getId().equals(estoque.getId())) {
                                tbvLocalListSeparar.getSelectionModel().select(vo);
                                break;
                            }
                        }

                    }
                }.start();

                txtQtdDesejada.setText("" + estoque.getQuantidade());
                txtMaterialEscolhido.setText(estoque.getMaterialDescricao());
                txtLocalEscolhido.setText(estoque.getLocalDescricao());

            }
        }

    }

    @FXML
    void btnSalvarOperacaoOnAction(ActionEvent event) {

        if (tbvMaterialSeparado.getItems().size() == 0) {
            return;
        }
        Boolean result = false;

        for (Material vo : tbvMaterialListSeparar.getItems()) {
            if (vo.getQuantidadeSolicitada() > 0) {
                result = true;
                break;
            }
        }
        if (!result) {

            devolucaoTotal();
            return;
        }
        if (result) {
            result = alerta.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação do usuário", "Há material que não foi separado para devolução.\n"
                    + "Deseja realizar a DEVOLUÇÃO PARCIAL dos materiais separados?", "Sim", "Não");

        }

        if (result) {
            devolucaoParcial(true);
        } else {
            return;
        }
    }

    void limparSepararMaterial() {
        todosEstoqueMaterial.clear();
        tbvLocalListSeparar.getItems().clear();
        tbvLocalListSeparar.refresh();

        tbvMaterialListSeparar.getItems().clear();
        tbvMaterialListSeparar.refresh();

        estoqueMaterialSeparado.clear();
        tbvMaterialSeparado.getItems().clear();
        tbvMaterialSeparado.refresh();

        txtQtdDesejada.setText("");
        txtMaterialEscolhido.setText("");
        txtLocalEscolhido.setText("");
    }

    @FXML
    void btnCancelarOperacaoOnAction(ActionEvent event) {

        Boolean result = alerta.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação do usuário", "Deseja desistir de separar os materiais?\n\nATENÇÃO - Todas ações realizadas até o momento não serão salvas", "Sim, confirmo", "Não, quero voltar");
        System.out.println("Entrei");
        if (result) {
            PanePrincipal.getSelectionModel().select(tabAnaliseMaterial);
            tabAnaliseMaterial.setDisable(false);
            tabItensSeparado.setDisable(true);

            limparSepararMaterial();
        }

    }

    @FXML
    void btnRetornarOnAction(ActionEvent event) {

        tabBuscarEmprestimo.setDisable(false);
        tabAnaliseMaterial.setDisable(true);
        tblListaMaterial.getItems().clear();
        tblListaMaterial.refresh();
        PanePrincipal.getSelectionModel().select(tabBuscarEmprestimo);

    }

    void devolucaoTotal() {

        new Thread() {
            @Override
            public void run() {

                Emprestimo emp = NegociosEstaticos.getNegocioEmprestimo().consultarPorId(tblPrincipalBuscarEmprestimo.getSelectionModel().getSelectedItem());
                emp.setStatus_emprestimo(StatusEmprestimo.FINALIZADO);
                try {
                    devolucaoParcial(false);
                    NegociosEstaticos.getNegocioEmprestimo().salvar(emp);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        alerta.alerta(Alert.AlertType.INFORMATION, "Operação realizada com sucesso", "A devolução dos materiais foi realizada com sucesso");
                        Boolean result = alerta.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação do usuário", "Deseja realizar uma nova revolução?", "Sim", "Não");
                        if (result) {
                            carregarTabelaEmprestimo();
                            limparSepararMaterial();
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

    void devolucaoParcial(Boolean option) {

        new Thread() {
            @Override
            public void run() {
                List<EstoqueMaterial> estoqueMaterial = NegociosEstaticos.getNegocioEstoqueMateria().buscarTodosEstoqueMaterial();
                List<EmprestimoEstoqueMaterial> eem = NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarTodosIdEmprestimo(tblPrincipalBuscarEmprestimo.getSelectionModel().getSelectedItem());

                for (EstoqueMaterial vo : tbvMaterialSeparado.getItems()) {
                    for (EstoqueMaterial estq : estoqueMaterial) {
                        if (vo.getId().equals(estq.getId())) {

                            estq.setQuantidade_emprestada(estq.getQuantidade_emprestada() - vo.getQuantidade());
                            estq.setQuantidade(estq.getQuantidade() + vo.getQuantidade());
                            try {
                                NegociosEstaticos.getNegocioEstoqueMateria().salvar(estq);
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }

                            break;
                        }
                    }
                    for (EmprestimoEstoqueMaterial emprestimo : eem) {

                        if (emprestimo.getId_material().getId().equals(vo.getId_material().getId())
                                && (emprestimo.getQtd_emprestada() - emprestimo.getQtd_devolvida()) > 0) {

                            emprestimo.setDt_devolucao(new Date());

                            int qtd = emprestimo.getQtd_emprestada() - emprestimo.getQtd_devolvida();

                            if (vo.getQuantidade() <= qtd) {
                                emprestimo.setQtd_devolvida(emprestimo.getQtd_devolvida() + vo.getQuantidade());
                                try {
                                    NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().salvar(emprestimo);
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                                break;
                            }
                            if (vo.getQuantidade() > qtd) {
                                emprestimo.setQtd_devolvida(emprestimo.getQtd_devolvida() + qtd);
                                vo.setQuantidade(vo.getQuantidade() - qtd);
                                try {
                                    NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().salvar(emprestimo);
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }

                        }
                    }
                }

                if (option) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            alerta.alerta(Alert.AlertType.INFORMATION, "Operação realizada com sucesso", "A devolução dos materiais foi realizada com sucesso");
                            Boolean result = alerta.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação do usuário", "Deseja realizar uma nova revolução?", "Sim", "Não");
                            if (result) {
                                carregarTabelaEmprestimo();
                                limparSepararMaterial();
                                PanePrincipal.getSelectionModel().select(tabBuscarEmprestimo);
                                tabBuscarEmprestimo.setDisable(false);
                                tabAnaliseMaterial.setDisable(true);

                            } else {
                                voltar();
                            }

                        }
                    });
                }

            }
        }.start();

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

    void devolucaoManual() {
        tabAnaliseMaterial.setDisable(true);
        tabItensSeparado.setDisable(false);
        PanePrincipal.getSelectionModel().select(tabItensSeparado);

        new Thread() {
            @Override
            public void run() {
                todosEstoqueMaterial = NegociosEstaticos.getNegocioEstoqueMateria().buscarTodosEstoqueMaterial();
                List<Material> material = new ArrayList<>();
                Boolean isAdd = true;
                for (EmprestimoEstoqueMaterial vo : tblListaMaterial.getItems()) {
                    if (material.isEmpty()) {
                        Material mat = new Material();
                        mat.setQuantidadeSolicitada(vo.getQtd_emprestada());
                        mat.setId_material(vo.getId_material().getId());
                        mat.setId_categoria(vo.getId_material().getId_categoria());
                        mat.setUnidadeMedida(vo.getId_material().getId_tipo_unidade().getSigla());
                        mat.setDescricao(vo.getId_material().getDescricao());
                        material.add(mat);
                    } else {
                        for (Material mat : material) {
                            if (vo.getId_material().getId().equals(mat.getId())) {
                                mat.setQuantidadeSolicitada(mat.getQuantidadeSolicitada() + vo.getQtd_emprestada());
                                isAdd = false;
                            }
                        }
                        if (vo.getQtd_emprestada() - vo.getQtd_devolvida() > 0) {
                            if (isAdd) {
                                Material mat = new Material();
                                mat.setQuantidadeSolicitada(vo.getQtd_emprestada() - vo.getQtd_devolvida());
                                mat.setId_material(vo.getId_material().getId());
                                mat.setId_categoria(vo.getId_material().getId_categoria());
                                mat.setUnidadeMedida(vo.getId_material().getId_tipo_unidade().getSigla());
                                mat.setDescricao(vo.getId_material().getDescricao());
                                material.add(mat);
                            }
                        }
                        isAdd = true;
                    }
                }

                completarTabelaAnaliseMaterial(material);
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
            devolucaoManual();
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

    void completarTbvEstoqueSeparado(List<EstoqueMaterial> lista) {
        ObservableList<EstoqueMaterial> dado = FXCollections.observableArrayList();
        dado.addAll(lista);

        this.tbcQtdSeparada.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("QtdDisponivelFormat"));
        this.tbcMaterialSeparado.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("MaterialDescricao"));
        this.tbcLocalSeparado.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("LocalDescricao"));

        this.tbvMaterialSeparado.setItems(dado);
    }

    void completarTabelaAnaliseMaterial(List<Material> lista) {
        ObservableList<Material> dado = FXCollections.observableArrayList();
        dado.addAll(lista);
        this.tbcMaterialListaSeparar.setCellValueFactory(new PropertyValueFactory<Material, String>("Descricao"));
        this.tbcQtdMaterialSeparar.setCellValueFactory(new PropertyValueFactory<Material, String>("QuantidadeSolicitadaFormat"));
        tbvMaterialListSeparar.setItems(dado);
    }

    void completaTabelaEstoqueLocal(List<EstoqueMaterial> lista) {

        ObservableList<EstoqueMaterial> dado = FXCollections.observableArrayList();
        dado.addAll(lista);

        this.tbcQtdSeparar.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("QtdDisponivelFormat"));
        this.tbcLocalSeparar.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("LocalDescricao"));
        tbvLocalListSeparar.setItems(dado);

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
                List<EmprestimoEstoqueMaterial> aux = new ArrayList<>();

                for (EmprestimoEstoqueMaterial vo : mat) {
                    if (vo.getQtd_emprestada() - vo.getQtd_devolvida() > 0) {
                        vo.setQtd_emprestada(vo.getQtd_emprestada() - vo.getQtd_devolvida());
                        aux.add(vo);
                    }
                }

                completarTabelaMaterial(aux);
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
        tabItensSeparado.setDisable(true);

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
