/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.emprestimo.acompanhar;

import classesAuxiliares.ClasseDoSistemaEstatico;
import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import enumm.PerfilUsuario;
import enumm.StatusEmprestimo;
import gui.SystemAutonet;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utilitarios.Alertas;
import utilitarios.LerMessage;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.Material;
import vo.Pessoa;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class AcompanharEmprestimoController {

    @FXML
    private Label lblPesquisar;

    @FXML
    private Button btnImprimir;

    @FXML
    private Button btnVoltarDescricao;

    @FXML
    private DatePicker dtpFinal;

    @FXML
    private ComboBox<String> cbmStatus;

    @FXML
    private TableColumn<EmprestimoEstoqueMaterial, Number> tbcQuantidade;

    @FXML
    private TableView<Emprestimo> tblPrincipal;

    @FXML
    private TableColumn<EmprestimoEstoqueMaterial, String> tbcCategoria;

    @FXML
    private Button btnConsultar;

    @FXML
    private TabPane tabPrincipal;

    @FXML
    private TextField txtBuscador;

    @FXML
    private Label lblData;

    @FXML
    private Label lblFinalidade;

    @FXML
    private Tab tabListaEmprestimo;

    @FXML
    private TableView<EmprestimoEstoqueMaterial> tblDescricao;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Emprestimo, StatusEmprestimo> tbcStatus;

    @FXML
    private TableColumn<Emprestimo, Date> tbcDtEmprestimo;

    @FXML
    private TableColumn<Emprestimo, String> tbcPessoa;

    @FXML
    private Tab tabDescricaoEmprestimo;

    @FXML
    private DatePicker dtpInicial;

    @FXML
    private Button btnBuscar;

    @FXML
    private Label lblObservacao;

    @FXML
    private TableColumn<EmprestimoEstoqueMaterial, String> tbcMaterial;

    @FXML
    void btnConsultarOnAction(ActionEvent event) throws IOException {

        if (!tblPrincipal.getSelectionModel().isEmpty()) {

            tabListaEmprestimo.setDisable(true);
            tabDescricaoEmprestimo.setDisable(false);
            tabPrincipal.getSelectionModel().select(tabDescricaoEmprestimo);
            lblFinalidade.setText(tblPrincipal.getSelectionModel().getSelectedItem().getFinalidade());
            lblData.setText(tblPrincipal.getSelectionModel().getSelectedItem().getDt_emprestimoString());
            lblObservacao.setText(tblPrincipal.getSelectionModel().getSelectedItem().getObservacao());
            completarTabelaDetalhe(NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().consultarTodosIdEmprestimo(tblPrincipal.getSelectionModel().getSelectedItem()));
        } else {
            Alertas alert = new Alertas();
            LerMessage ler = new LerMessage();
            alert.alerta(Alert.AlertType.ERROR, "Selecionar", ler.getMessage("msg.erro.naoselecionado"));
        }

    }

    @FXML
    void btnVoltarOnAction(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    void btnBuscarOnAction(ActionEvent event) {
        filtro();
    }

    @FXML
    void dtpInicialOnAction(ActionEvent event) {
        filtro();
    }

    @FXML
    void dtpFinalOnAction(ActionEvent event) {
        filtro();
    }

    @FXML
    void cbmStatusOnAction(ActionEvent event) {
//        Emprestimo emp = new Emprestimo();
//        List<Emprestimo> list = new ArrayList();
//        if (cbmStatus.getValue().equals("TODOS")) {
//            if (ClasseDoSistemaEstatico.getPessoa().getFuncao().equals(PerfilUsuario.ADMINISTRADOR)) {
//                list = NegociosEstaticos.getNegocioEmprestimo().buscarPorTodos();
//
//            } else {
//                list = NegociosEstaticos.getNegocioEmprestimo().buscarPorIdPessoa(ClasseDoSistemaEstatico.getPessoa());
//
//            }
//        } else {
//            emp.setStatus_emprestimo(StatusEmprestimo.valueOf(cbmStatus.getValue()));
//
//            if (ClasseDoSistemaEstatico.getPessoa().getFuncao().equals(PerfilUsuario.ADMINISTRADOR)) {
//
//                list = NegociosEstaticos.getNegocioEmprestimo().buscarPorStatusEmprestimoTodos(emp);
//
//            } else {
//
//                emp.setId_pessoa_solicita(ClasseDoSistemaEstatico.getPessoa());
//                list = NegociosEstaticos.getNegocioEmprestimo().buscarPorStatusEmprestimoPessoa(emp);
//            }
//        }
//        completarTabela(list);
        filtro();
    }

    @FXML
    void btnImprimirOnAction(ActionEvent event) {

    }

    @FXML
    void btnVoltarDescricaoOnAction(ActionEvent event) {
        tabListaEmprestimo.setDisable(false);
        tabDescricaoEmprestimo.setDisable(true);
        tabPrincipal.getSelectionModel().select(tabListaEmprestimo);
    }

    private void completarTabelaDetalhe(List<EmprestimoEstoqueMaterial> lista) {
        ObservableList<EmprestimoEstoqueMaterial> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }
        this.tbcMaterial.setCellValueFactory(new PropertyValueFactory<EmprestimoEstoqueMaterial, String>("NomeMaterial"));
        this.tbcQuantidade.setCellValueFactory(new PropertyValueFactory<EmprestimoEstoqueMaterial, Number>("Qtd"));
        this.tbcCategoria.setCellValueFactory(new PropertyValueFactory<EmprestimoEstoqueMaterial, String>("NomeCategoria"));
        this.tblDescricao.setItems(dado);

    }

    private void completarTabela(List<Emprestimo> lista) {
        ObservableList<Emprestimo> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }
        this.tbcDtEmprestimo.setCellValueFactory(new PropertyValueFactory<Emprestimo, Date>("Dt_emprestimoString"));
        this.tbcStatus.setCellValueFactory(new PropertyValueFactory<Emprestimo, StatusEmprestimo>("Status_emprestimo"));
        this.tbcPessoa.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("NomePessoaSolicita"));
        this.tblPrincipal.setItems(dado);

    }

    private void filtro() {
        List<Emprestimo> alterAux = FXCollections.observableArrayList();

        if (txtBuscador.getText().isEmpty()
                && dtpFinal.getValue() == null && dtpInicial.getValue() == null && cbmStatus.getSelectionModel().getSelectedItem().equals("TODOS")) {
            alterAux = altertab;
            // completarTabela(altertab);
        } else {

            for (int i = 0; i < altertab.size(); i++) {
                if (altertab.get(i).getId_pessoa_solicitaNome().toUpperCase().indexOf(txtBuscador.getText().toUpperCase()) >= 0) {
                    alterAux.add(altertab.get(i));
                }
            }
            //  completarTabela(alterAux);
        }

        if (dtpFinal.getValue() == null && dtpInicial.getValue() == null && cbmStatus.getValue().equals("TODOS")) {

            completarTabela(alterAux);
            return;
        }
        if (!cbmStatus.getValue().equals("TODOS")) {
            List<Emprestimo> comparator = FXCollections.observableArrayList();
            for (int i = 0; i < alterAux.size(); i++) {
                if (alterAux.get(i).getStatus_emprestimo().toString().toUpperCase().equals(cbmStatus.getValue().toUpperCase())) {
                    comparator.add(alterAux.get(i));
                }
            }
            alterAux = comparator;
            completarTabela(alterAux);
        }

        List<Emprestimo> aux = FXCollections.observableArrayList();

        if (dtpFinal.getValue() == null && dtpInicial.getValue() != null) {

            for (int i = 0; i < alterAux.size(); i++) {

                if (alterAux.get(i).getDt_emprestimoLocalDate().isAfter(dtpInicial.getValue())
                        || alterAux.get(i).getDt_emprestimoLocalDate().isEqual(dtpInicial.getValue())) {
                    aux.add(alterAux.get(i));
                }
            }
            completarTabela(aux);
            return;
        }
        if (dtpFinal.getValue() != null && dtpInicial.getValue() == null) {

            for (int i = 0; i < alterAux.size(); i++) {

                if (alterAux.get(i).getDt_emprestimoLocalDate().isBefore(dtpFinal.getValue())
                        || alterAux.get(i).getDt_emprestimoLocalDate().isEqual(dtpFinal.getValue())) {
                    aux.add(alterAux.get(i));
                }
            }
            completarTabela(aux);
            return;
        }

        if (dtpFinal.getValue() != null && dtpInicial.getValue() != null) {

            for (int i = 0; i < alterAux.size(); i++) {

                if (alterAux.get(i).getDt_emprestimoLocalDate().isBefore(dtpFinal.getValue())
                        || alterAux.get(i).getDt_emprestimoLocalDate().isEqual(dtpInicial.getValue())
                        && alterAux.get(i).getDt_emprestimoLocalDate().isBefore(dtpFinal.getValue())
                        || alterAux.get(i).getDt_emprestimoLocalDate().isEqual(dtpFinal.getValue())) {
                    aux.add(alterAux.get(i));
                }
            }
            completarTabela(aux);
            return;
        }

    }
    List<Emprestimo> altertab = FXCollections.observableArrayList();

    public void initialize() {
        StatusEmprestimo a[] = StatusEmprestimo.values();

        ObservableList<String> dado2 = FXCollections.observableArrayList();

        for (int i = 0; i < a.length; i++) {
            dado2.add(a[i].name());
        }
        dado2.add("TODOS");
        cbmStatus.setItems(dado2);
        cbmStatus.getSelectionModel().select(cbmStatus.getItems().size() - 1);
        tabDescricaoEmprestimo.setDisable(true);

        if (ClasseDoSistemaEstatico.getPessoa().getFuncao().equals(PerfilUsuario.ALUNO) || ClasseDoSistemaEstatico.getPessoa().getFuncao().equals(PerfilUsuario.TESTE)) {

            txtBuscador.setVisible(false);
            btnBuscar.setVisible(false);
            lblPesquisar.setVisible(false);
            tbcPessoa.setVisible(false);
            tbcDtEmprestimo.setPrefWidth(167 + 146);
            tbcStatus.setPrefWidth(317 + 146 + 8);
            altertab = NegociosEstaticos.getNegocioEmprestimo().buscarPorIdPessoa(ClasseDoSistemaEstatico.getPessoa());
            completarTabela(altertab);
        } else {
            altertab = NegociosEstaticos.getNegocioEmprestimo().buscarPorTodos();
            completarTabela(altertab);
        }

        // TODO
    }

}
