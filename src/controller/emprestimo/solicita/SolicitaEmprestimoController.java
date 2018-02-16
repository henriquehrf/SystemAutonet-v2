/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.emprestimo.solicita;

import classesAuxiliares.ClasseDoSistemaEstatico;
import classesAuxiliares.NegociosEstaticos;
import controller.PrincipalController;
import enumm.PerfilUsuario;
import enumm.StatusEmprestimo;
import gui.SystemAutonet;
import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import utilitarios.Alertas;
import utilitarios.EnviarEmail;
import utilitarios.GerarPDF;
import vo.Emprestimo;
import vo.EmprestimoEstoqueMaterial;
import vo.Material;
import vo.Pessoa;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class SolicitaEmprestimoController {

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnAdicionarMaterial;

    @FXML
    private TabPane tabPanePrincipal;

    @FXML
    private TableView<Material> tblBuscaMateriais;

    @FXML
    private Button btnExclur;

    @FXML
    private TableColumn<Material, String> tbcCategoriaListaMaterial;

    @FXML
    private TableView<Material> tblListaMateriais;

    @FXML
    private TextField txtFinalidade;

    @FXML
    private TableColumn<Material, String> tbcMaterialBuscaMaterial;

    @FXML
    private TableColumn<Material, String> tbcDescricaoListaMaterial;

    @FXML
    private TextField txtBuscador;

    @FXML
    private Tab tabListaMaterial;

    @FXML
    private TableColumn<Material, Number> tbcQuantidadeDisponivelBuscaMaterial;

    @FXML
    private TableColumn<Material, Integer> tbcQuantidadeSolicitadaListaMaterial;

    @FXML
    private Tab tabBuscarMaterial;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextArea txtObservacao;

    @FXML
    private Label finalidadeObrigatorio;

    @FXML
    private DatePicker dtpDataEmprestimo;

    @FXML
    private Label dataObrigatorio;

    @FXML
    private TableColumn<Material, String> tbcCategoriaBuscaMaterial;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnSolicitar;

    ObservableList<Material> altertab = FXCollections.observableArrayList();

    String htmlTable = "";

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
                    tblBuscaMateriais.setPlaceholder(h);
                } else {
                    tblBuscaMateriais.setPlaceholder(new Label("Não há conteúdo a ser exibido na tabela"));
                }
            }
        });

    }

    Boolean solicitar() {

        Date data = new Date();
        Emprestimo emp = new Emprestimo();
        Instant instant = dtpDataEmprestimo.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        emp.setDt_emprestimo(Date.from(instant));
        emp.setFinalidade(txtFinalidade.getText());
        emp.setObservacao(txtObservacao.getText());
        emp.setStatus_emprestimo(StatusEmprestimo.ESPERANDO_ANALISE);
        emp.setId_pessoa_solicita(ClasseDoSistemaEstatico.getPessoa());
        try {
            emp = NegociosEstaticos.getNegocioEmprestimo().salvar(emp);
        } catch (Exception ex) {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Ops, houve um erro na solicitação de empréstimo", ex.getMessage());
            System.out.println(ex.getMessage());
            return false;
        }
        for (int i = 0; i < altertab.size(); i++) {
            EmprestimoEstoqueMaterial eem = new EmprestimoEstoqueMaterial();
            eem.setId_emprestimo(emp);
            eem.setQtd_emprestada(altertab.get(i).getQuantidadeSolicitada());
            eem.setQtd_devolvida(0);
            eem.setId_material(altertab.get(i));
            try {
                NegociosEstaticos.getNegocioEmprestiomEstoqueMaterial().salvar(eem);
                eem = null;

            } catch (Exception ex) {
                Alertas alert = new Alertas();
                alert.alerta(Alert.AlertType.ERROR, "Ops, houve um erro na solicitação de empréstimo", ex.getMessage());
                System.out.println(ex.getMessage());
                return false;
            }

        }
        emp = null;
        return true;

    }

    Boolean validar() {

        if (txtFinalidade.getText().isEmpty()) {
            finalidadeObrigatorio.setVisible(true);
            return false;
        } else {
            finalidadeObrigatorio.setVisible(false);
        }
        if (dtpDataEmprestimo.getValue() == null) {
            dataObrigatorio.setVisible(true);
            return false;
        } else {
            dataObrigatorio.setVisible(false);
        }
        if (tblListaMateriais.getItems().size() == 0) {
            tblListaMateriais.setStyle("-fx-border-color:red;");
            return false;
        } else {
            tblListaMateriais.setStyle("-fx-border-color:darkgrey;");
        }

        return true;
    }

    void table(String quantidade, String material, String categoria) {
        htmlTable += "<tr><td>" + quantidade + "</td><td>" + material + " </td> <td>" + categoria + "</td></tr>";
    }

    void enviarEmail(Boolean isSaved) {

        if (!isSaved) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                String pattern = "dd/MM/yyyy";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
                EnviarEmail email = new EnviarEmail();
                String assunto = "SystemAutoNet - Solicitação de Empréstimo de Materiais";
                String conteudo = "";
                String remetente = "";

                conteudo += "<html><body>";
                conteudo += "<p><b>USUÁRIO</b>: " + ClasseDoSistemaEstatico.getPessoa().getNome().toUpperCase() + "</p>";
                conteudo += "<p><b>DATA PARA EMPRÉSTIMO</b>: " + dateFormatter.format(dtpDataEmprestimo.getValue()) + "</p>";
                conteudo += "<p><b>FINALIDADE</b>: " + txtFinalidade.getText().toUpperCase() + "</p>";
                conteudo += "<p><b>OBSERVAÇÃO</b>: " + txtObservacao.getText().toUpperCase() + "</p>";
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
                htmlTable += "<tr><td><b>QUANTIDADE</b></td><td><b>MATERIAL</b></td> <td><b>CATEGORIA</b></td></tr>";

                for (int i = 0; i < tblListaMateriais.getItems().size(); i++) {
                    table(tblListaMateriais.getItems().get(i).getQuantidadeSolicitada() + " " + tblListaMateriais.getItems().get(i).getUnidadeMedida(), tblListaMateriais.getItems().get(i).getDescricao(), tblListaMateriais.getItems().get(i).getCategoriaNome());
                }
                htmlTable += "</table></div>";
                conteudo += htmlTable;
                htmlTable="";
                conteudo += "</body></html>";

                conteudo += "<footer><p align=\"center\">Mensagem gerada automáticamente por SystemAutoNet 1.0</p>";
                conteudo += "<p align=\"center\"><b>Não responder essa mensagem</b></p></footer>";

                List<Pessoa> users = NegociosEstaticos.getNegocioPessoa().buscarTodos();

                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getFuncao().equals(PerfilUsuario.ADMINISTRADOR)) {
                        remetente += users.get(i).getEmail() + ",";
                    }
                }
                remetente += ClasseDoSistemaEstatico.getPessoa().getEmail();
                email.enviarEmail(remetente, conteudo, assunto);
            }
        }.start();

    }

    void reset() {
        try {
            Parent root;
            root = FXMLLoader.load(SolicitaEmprestimoController.class.getClassLoader().getResource("fxml/emprestimo/Solicitar/SolicitaEmprestimo.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    void cancelar() {
        try {
            Parent root;
            root = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/Principal.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    Boolean novoRegistro() {
        Alertas alert = new Alertas();
        return alert.alerta(Alert.AlertType.CONFIRMATION, "Aguardando confirmação do usuário", "Deseja realizar um nova solicitação de empréstimo?", "Sim", "Não");
    }

    void gerarRelatorio(Boolean isSaved) {

        if (!isSaved) {
            return;
        }
        Alertas alert = new Alertas();
        Boolean result = alert.alerta(Alert.AlertType.CONFIRMATION, "Aguardando a confirmação do usuário", "Deseja gerar um PDF da solicitação de empréstimo?", "Sim", "Não");

        if (result) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            Stage stage = new Stage();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                String pattern = "dd/MM/yyyy";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
                GerarPDF pdf = new GerarPDF();
                try {
                    pdf.solicitarEmprestimo(selectedDirectory.getAbsolutePath(), tblListaMateriais.getItems(), txtFinalidade.getText().toUpperCase(), dateFormatter.format(dtpDataEmprestimo.getValue()).toString(), txtObservacao.getText().toUpperCase(), ClasseDoSistemaEstatico.getPessoa());
                    if (!novoRegistro()) {
                        cancelar();
                    } else {
                        reset();
                    }

                } catch (Exception ex) {
                    alert.alerta(Alert.AlertType.ERROR, "Erro ao gerar o PDF", ex.getMessage());
                    gerarRelatorio(true);
                }

            } else {
                if (!novoRegistro()) {
                    cancelar();
                } else {
                    reset();
                }
            }
        } else {
            if (!novoRegistro()) {
                cancelar();
            } else {
                reset();
            }
        }

    }

    void solicitarMateriais() {
        if (validar()) {
            Boolean isSaved = solicitar();
            enviarEmail(isSaved);
            gerarRelatorio(isSaved);
        } else {
            Alertas alert = new Alertas();
            alert.alerta(Alert.AlertType.ERROR, "Erro ao solicitar empréstimo", "Há campos obrigatórios que não foram preenchidos");
            System.out.println("Erro");
        }
    }

    @FXML
    void btnSolicitarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            solicitarMateriais();
        }
    }

    @FXML
    void btnSolicitarOnAction(ActionEvent event) {

        solicitarMateriais();

    }

    @FXML
    void btnCancelarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            cancelar();
        }
    }

    @FXML
    void btnCancelarOnAction(ActionEvent event) {
        cancelar();
    }

    void voltar() {
        tabBuscarMaterial.setDisable(false);
        tabListaMaterial.setDisable(true);
        tabPanePrincipal.getSelectionModel().select(tabBuscarMaterial);
    }

    @FXML
    void btnVoltarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            voltar();
        }
    }

    @FXML
    void btnVoltarOnAction(ActionEvent event) {
        voltar();
    }

    void adicionar() {
        tabListaMaterial.setDisable(false);
        tabPanePrincipal.getSelectionModel().select(tabListaMaterial);
        tabBuscarMaterial.setDisable(true);
    }

    @FXML
    void btnAdicionarMaterialKeyPressed(KeyEvent event) {
        System.out.println("uai??");
        if (event.getCode() == KeyCode.ENTER) {
            if (set_quantidade()) {
                tblListaMateriais.setItems(altertab);
                completar_ListaMaterial(altertab);
                btnVoltarOnKeyPressed(event);

            } else {
                return;
            }
        }
    }

    @FXML
    void btnAdicionarOnAction(ActionEvent event) {
        adicionar();

    }

    void verificaLista(Material mat) {

        if (altertab.size() > 0) {
            int indice = altertab.indexOf(mat);
            if (indice != -1) {
                altertab.set(indice, mat);
            } else {
                altertab.add(mat);
            }
        } else {
            altertab.add(mat);
        }
    }

    boolean set_quantidade() {
        Material mat = new Material();
        mat = tblBuscaMateriais.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Informe a quantidade");
        dialog.setContentText("Informe a quantidade de material:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int resultado = Integer.parseInt(result.get());
            if (resultado <= mat.getQuantidadeDisponivel().intValue() && resultado > 0) {
                mat.setQuantidadeSolicitada(resultado);
                verificaLista(mat);
                //altertab.add(mat);
                return true;
            } else {
                Alertas al = new Alertas();
                al.alerta(Alert.AlertType.ERROR, "Erro", "Quantidade inválida");
                return false;
            }

        }
        return false;
    }

    @FXML
    void btnAdicionarMaterialOnAction(ActionEvent event) {

        // fazer o tratamento da seleção
        if (set_quantidade()) {
            tblListaMateriais.setItems(altertab);
            completar_ListaMaterial(altertab);
            btnVoltarOnAction(event);

        } else {
            return;
        }

    }

    void completar_ListaMaterial(List<Material> list) {

        if (list.size() == 0) {
            tableLoading(false);
        } else {
            tableLoading(true);
        }

        this.tbcCategoriaListaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("CategoriaNome"));
        this.tbcDescricaoListaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("descricao"));
        this.tbcQuantidadeSolicitadaListaMaterial.setCellValueFactory(new PropertyValueFactory<Material, Integer>("QuantidadeSolicitadaFormat"));
        tblListaMateriais.setItems(altertab);
    }

    @FXML
    void btnExclurOnAction(ActionEvent event) {

        if (tblListaMateriais.getSelectionModel().getSelectedItem() != null) {
            tblListaMateriais.getItems().remove(tblListaMateriais.getSelectionModel().getSelectedItem());
        }

    }

    void buscar() {
        Material material = new Material();
        material.setDescricao(txtBuscador.getText());
        new Thread() {
            @Override
            public void run() {
                List<Material> list;
                if (!material.getDescricao().isEmpty()) {
                    list = NegociosEstaticos.getNegocioMaterial().buscarPorDescricao(material);

                } else {
                    list = NegociosEstaticos.getNegocioMaterial().buscarTodos();
                }
                completarTabela(list);
            }
        }.start();
    }

    @FXML
    void btnBuscarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buscar();
        }
    }

    @FXML
    void btnBuscarOnAction(ActionEvent event) {

        buscar();

    }

    private void completarTabela(List<Material> lista) {

        if (lista.size() == 0) {
            tableLoading(false);
        } else {
            tableLoading(true);
        }

        ObservableList<Material> dado = FXCollections.observableArrayList();
        for (int i = 0; i < lista.size(); i++) {
            dado.add(lista.get(i));
        }
        this.tbcCategoriaBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("CategoriaNome"));
        this.tbcMaterialBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("descricao"));
        this.tbcQuantidadeDisponivelBuscaMaterial.setCellValueFactory(new PropertyValueFactory<Material, Number>("QuantidadeDisponivelFormat"));

        Comparator<Material> cmp = new Comparator<Material>() {
            @Override
            public int compare(Material mat1, Material mat2) {
                int x = mat1.getDescricao().compareTo(mat2.getDescricao());
                if (x != 0) {
                    return x;
                } else {
                    Integer x1 = ((Material) mat1).getQuantidade();
                    Integer x2 = ((Material) mat2).getQuantidade();
                    return x1.compareTo(x2);
                }
            }

        };
        Collections.sort(dado, cmp);

        this.tblBuscaMateriais.setItems(dado);

    }

    public void initialize() {

        new Thread() {
            @Override
            public void run() {
                List<Material> lista = NegociosEstaticos.getNegocioMaterial().buscarTodos();
                completarTabela(lista);

            }
        }.start();
        tblListaMateriais.setPlaceholder(new Label("Não há conteúdo a ser exibido na tabela"));
        tabListaMaterial.setDisable(true);
        dataObrigatorio.setVisible(false);
        finalidadeObrigatorio.setVisible(false);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tableLoading(true);
            }
        });

        String pattern = "dd/MM/yyyy";
        dtpDataEmprestimo.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            Date dt = new Date();
            LocalDate dtL = dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    if (!date.isBefore(dtL)) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        dtpDataEmprestimo.setDayCellFactory(new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                Date dt = new Date();
                LocalDate dtL = dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(dtL)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        });

    }

}
