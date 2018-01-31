/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.cadastro.Cadastro;

import classesAuxiliares.NegociosEstaticos;
import classesAuxiliares.Validar;
import controller.cadastro.Consulta.ConsultarPessoaController;
import gui.SystemAutonet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import utilitarios.LerMessage;
import enumm.Atividade;
import enumm.PerfilUsuario;
import vo.Pessoa;
import enumm.Sexo;
import java.awt.Color;
import javafx.application.Platform;
import static javafx.scene.control.Pagination.INDETERMINATE;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import utilitarios.Alertas;
import utilitarios.Criptografia;

/**
 *
 * @author PET Autonet
 */
public class CadastroPessoaController {

    @FXML
    private ProgressBar barSenha;

    @FXML
    private TextField txtEndereco;

    @FXML
    private Label lblsecundario;

    @FXML
    private Label lblStatusSeguranca;

    @FXML
    private PasswordField txtRSenha;

    @FXML
    private Label foneObrigatorio;

    @FXML
    private DatePicker dtpDtNascimento;

    @FXML
    private TextField txtNome;

    @FXML
    private RadioButton rdbMasculino;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtNumMatricula;
    @FXML
    private Label Title;

    @FXML
    private Button btnSalvar;

    @FXML
    private Label funcaoObrigatorio;

    @FXML
    private RadioButton rdbAtivo;

    @FXML
    private Label nomeObrigatorio;

    @FXML
    private Label sexoObrigatorio;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<PerfilUsuario> cmbFuncao;

    @FXML
    private TextField txtRg;

    @FXML
    private Label usuarioObrigatorio;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Label cpfObrigatorio;

    @FXML
    private Tab tabDadoPessoais;

    @FXML
    private TextField txtSecundario;

    @FXML
    private Label matriculaObrigatorio;

    @FXML
    private Label emailObrigatorio;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label enderecoObrigatorio;

    @FXML
    private RadioButton rdbInativo;

    @FXML
    private TextField txtUsuario;

    @FXML
    private Label rgObrigatorio;

    @FXML
    private TextField txtPrincipal;

    @FXML
    private Label repSenhaObrigatorio;

    @FXML
    private Label dtNascimentoObrigatorio;

    @FXML
    private Label senhaObrigatorio;

    @FXML
    private RadioButton rdbFeminino;
    @FXML
    private CheckBox checkAlterarSenha;

    // private NegocioPessoa NegocioP;
    private static Pessoa alterar;

    private static boolean cadastrar;

    public static boolean isCadastrar() {
        return cadastrar;
    }

    public static void setCadastrar(boolean cadastra) {
        CadastroPessoaController.cadastrar = cadastra;
    }

    public static Pessoa getAlterar() {
        return alterar;
    }

    public static void setAlterar(Pessoa alterar) {
        CadastroPessoaController.alterar = alterar;
    }

    ObservableList<PerfilUsuario> perf = FXCollections.observableArrayList((PerfilUsuario.values()));

    public void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                txtNome.requestFocus();
                lblStatusSeguranca.setVisible(false);
                barSenha.setStyle("-fx-accent: blue;");
            }
        });

        //NegocioP = new NegocioPessoa();
        setcamposObrigatorio();
        cmbFuncao.setItems(perf);
        rdbAtivo.setDisable(true);
        rdbInativo.setDisable(true);
        rdbAtivo.setSelected(true);
        if (!isCadastrar()) {
            completar();
            txtSenha.setDisable(true);
            txtRSenha.setDisable(true);
        } else {
            checkAlterarSenha.setVisible(false);
            alterar = null;
        }

    }

    @FXML
    void btnSalvarOnAction(ActionEvent event) {

        if (verificaCampoObrigatorio()) {

            try {
                if (alterar != null) {
                    salvar(alterar);
                } else {
                    Pessoa pessoa = new Pessoa();
                    salvar(pessoa);

                }
            } catch (Exception ex) {
                LerMessage ler = new LerMessage();
                Alertas aviso = new Alertas();
                try {

                    aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
                } catch (Exception ex1) {
                    System.out.println(ex1.getMessage());
                }
            }

        } else {
            try {
                LerMessage ler = new LerMessage();
                Alertas aviso = new Alertas();
                aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ler.getMessage("msg.cadastro.incompleto"));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());

            }
        }
    }

    @FXML
    void btnSalvarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (verificaCampoObrigatorio()) {

                try {
                    if (alterar != null) {
                        salvar(alterar);
                    } else {
                        Pessoa pessoa = new Pessoa();
                        salvar(pessoa);

                    }
                } catch (Exception ex) {
                    LerMessage ler = new LerMessage();
                    Alertas aviso = new Alertas();
                    try {

                        aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
                    } catch (Exception ex1) {
                        System.out.println(ex1.getMessage());
                    }
                }

            } else {
                try {
                    LerMessage ler = new LerMessage();
                    Alertas aviso = new Alertas();
                    aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ler.getMessage("msg.cadastro.incompleto"));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());

                }
            }
        }
    }

    @FXML
    void btnCancelarOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Parent root;
                // NegocioP = null;
                alterar = null;
                root = FXMLLoader.load(ConsultarPessoaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Pessoa.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
                SystemAutonet.SCENE.setRoot(root);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

    private void IncompatibilidadeNumero() throws Exception {
        LerMessage ler = new LerMessage();
        Alertas aviso = new Alertas();
        aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.dados.erro"), ler.getMessage("msg.incompatibilidade.numero"));

    }

    private void salvar(Pessoa pessoa) throws Exception {
        boolean trava = false;
        Date data = new Date();
        pessoa.setNome(txtNome.getText());

        pessoa.setEmail(txtEmail.getText());
        pessoa.setEndereco(txtEndereco.getText());

        pessoa.setUsuario(txtUsuario.getText());
        if (alterar == null) {
            Criptografia x = new Criptografia(txtSenha.getText());
            pessoa.setSenha(x.getSenha_criptografada());
        } else if (checkAlterarSenha.isSelected()) {
            Criptografia x = new Criptografia(txtSenha.getText());
            pessoa.setSenha(x.getSenha_criptografada());
        } else {
            pessoa.setSenha(alterar.getSenha());
        }

        pessoa.setUltimo_acesso(data);
        pessoa.setFuncao(cmbFuncao.getValue());

        if (Validar.isDigit(txtPrincipal.getText().toCharArray())) {
            pessoa.setFone_principal(txtPrincipal.getText());
        } else {
            this.foneObrigatorio.setVisible(true);
            trava = true;
        }

        if (Validar.isDigit(txtSecundario.getText().toCharArray())) {

            pessoa.setFone_secundario(txtSecundario.getText());
        } else {
            this.lblsecundario.setVisible(true);
            trava = true;
        }

        if (Validar.isDigit(txtCpf.getText().toCharArray())) {
            pessoa.setCpf(txtCpf.getText());
        } else {
            this.cpfObrigatorio.setVisible(true);
            trava = true;
        }

        if (Validar.isDigit(txtNumMatricula.getText().toCharArray())) {
            pessoa.setNum_matricula(txtNumMatricula.getText());
        } else {
            this.matriculaObrigatorio.setVisible(true);
            trava = true;
        }

        if (Validar.isDigit(txtRg.getText().toCharArray())) {
            pessoa.setRg(txtRg.getText());
        } else {
            this.rgObrigatorio.setVisible(true);
            trava = true;
        }

        if (trava) {
            IncompatibilidadeNumero();
            return;
        }
        if (rdbFeminino.isSelected()) {
            pessoa.setSexo(Sexo.F);
        }
        if (rdbMasculino.isSelected()) {
            pessoa.setSexo(Sexo.M);
        }
        if (rdbAtivo.isSelected()) {
            pessoa.setAtivo(Atividade.A);
        }
        if (rdbInativo.isSelected()) {
            pessoa.setAtivo(Atividade.I);
        }
        if(!txtSenha.getText().equals(txtRSenha.getText())){
            Alertas aviso = new Alertas();
            LerMessage ler = new LerMessage();
            aviso.alerta(Alert.AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ler.getMessage("msg.cadastro.senhaDiferente"));
            return;
        }

        Instant instant = dtpDtNascimento.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        pessoa.setDt_nascimento(Date.from(instant));

        try {
            //   NegocioP.salvar(pessoa);
            NegociosEstaticos.getNegocioPessoa().salvar(pessoa, alterar);
            Parent root;
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            // NegocioP = null;
            alterar = null;

            aviso.alerta(AlertType.INFORMATION, ler.getMessage("msg.cadastro.confirmacao"), ler.getMessage("msg.cadastro.sucesso"));
            root = FXMLLoader.load(ConsultarPessoaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Pessoa.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            LerMessage ler = new LerMessage();
            Alertas aviso = new Alertas();
            aviso.alerta(AlertType.ERROR, ler.getMessage("msg.cadastro.erro"), ex.getMessage());
        }
    }

    @FXML
    void btnCancelarOnAction(ActionEvent event) {

        try {
            Parent root;
            // NegocioP = null;
            alterar = null;
            root = FXMLLoader.load(ConsultarPessoaController.class.getClassLoader().getResource("fxml/cadastro/Consulta/Consultar_Pessoa.fxml"), ResourceBundle.getBundle("utilitarios/i18N_pt_BR"));
            SystemAutonet.SCENE.setRoot(root);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    void rdbFemininoOnKeyPressed(KeyEvent event) {
        rdbFeminino.setSelected(true);
    }

    @FXML
    void rdbMasculinoOnKeyPressed(KeyEvent event) {
        rdbMasculino.setSelected(true);

    }

    private void setcamposObrigatorio() {
        nomeObrigatorio.setVisible(false);
        sexoObrigatorio.setVisible(false);
        cpfObrigatorio.setVisible(false);
        rgObrigatorio.setVisible(false);
        dtNascimentoObrigatorio.setVisible(false);
        foneObrigatorio.setVisible(false);
        emailObrigatorio.setVisible(false);
        enderecoObrigatorio.setVisible(false);
        funcaoObrigatorio.setVisible(false);
        matriculaObrigatorio.setVisible(false);
        usuarioObrigatorio.setVisible(false);
        senhaObrigatorio.setVisible(false);
        repSenhaObrigatorio.setVisible(false);
        lblsecundario.setVisible(false);
    }

    private boolean verificaCampoObrigatorio() {
        setcamposObrigatorio();
        boolean verifica = true;
        if (txtNome.getText().isEmpty()) {
            nomeObrigatorio.setVisible(true);
            verifica = false;
        }
        if (!rdbFeminino.isSelected() && !rdbMasculino.isSelected()) {
            sexoObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtCpf.getText().isEmpty()) {
            cpfObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtRg.getText().isEmpty()) {
            rgObrigatorio.setVisible(true);
            verifica = false;
        }
        if (dtpDtNascimento.getValue() == null) {
            dtNascimentoObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtPrincipal.getText().isEmpty()) {
            foneObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtEmail.getText().isEmpty()) {
            emailObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtEndereco.getText().isEmpty()) {
            enderecoObrigatorio.setVisible(true);
            verifica = false;
        }
        if (cmbFuncao.getValue() == null) {
            funcaoObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtNumMatricula.getText().isEmpty()) {
            matriculaObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtUsuario.getText().isEmpty()) {
            usuarioObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtSenha.getText().isEmpty()) {
            senhaObrigatorio.setVisible(true);
            verifica = false;
        }
        if (txtRSenha.getText().isEmpty()) {
            repSenhaObrigatorio.setVisible(true);
            verifica = false;
        }
        return verifica;
    }

    @FXML
    void checkAlterarSenha_OnAction(ActionEvent event) {
        if (checkAlterarSenha.isSelected()) {
            txtSenha.setDisable(false);
            txtRSenha.setDisable(false);
            txtSenha.clear();
            txtRSenha.clear();
            LerMessage message = new LerMessage();
            checkAlterarSenha.setText(message.getMessage("lbl.alterarSenhaHabilitado"));

        } else {
            txtSenha.setDisable(true);
            txtRSenha.setDisable(true);
            txtSenha.setText("**********************");
            txtRSenha.setText("**********************");
            LerMessage message = new LerMessage();
            checkAlterarSenha.setText(message.getMessage("lbl.alterarSenha"));
        }
    }

    @FXML
    void checkAlterarSenha_OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (checkAlterarSenha.isSelected()) {
                txtSenha.setDisable(false);
                txtRSenha.setDisable(false);
                txtSenha.clear();
                txtRSenha.clear();
                LerMessage message = new LerMessage();
                checkAlterarSenha.setText(message.getMessage("lbl.alterarSenhaHabilitado"));

            } else {
                txtSenha.setDisable(true);
                txtRSenha.setDisable(true);
                txtSenha.setText("**********************");
                txtRSenha.setText("**********************");
                LerMessage message = new LerMessage();
                checkAlterarSenha.setText(message.getMessage("lbl.alterarSenha"));
            }
        }
    }

    void completar() {
        txtNome.setText(alterar.getNome());
        txtCpf.setText(alterar.getCpf());
        txtEmail.setText(alterar.getEmail());
        txtEndereco.setText(alterar.getEndereco());
        txtNumMatricula.setText(alterar.getNum_matricula());
        txtRSenha.setText(alterar.getSenha());
        txtSenha.setText(alterar.getSenha());
        txtRg.setText(alterar.getRg());
        txtSecundario.setText(alterar.getFone_secundario());
        txtPrincipal.setText(alterar.getFone_principal());
        txtUsuario.setText(alterar.getUsuario());
        cmbFuncao.setValue(alterar.getFuncao());
        LocalDate date = alterar.getDt_nascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dtpDtNascimento.setValue(date);
        if (alterar.getSexo().equals(Sexo.F)) {
            rdbFeminino.setSelected(true);
        } else {
            rdbMasculino.setSelected(true);
        }
        LerMessage ler = new LerMessage();
        try {

            Title.setText(ler.getMessage("title.cadastro.pessoa"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        rdbAtivo.setDisable(false);
        rdbInativo.setDisable(false);
        //alterar=null;
    }

    @FXML
    void txtSenha_OnKeyTyped(KeyEvent event) {
        progressBarSenha();
    }

    @FXML
    void txtSenha_OnKeyReleased(KeyEvent event) {
        progressBarSenha();
    }

    void progressBarSenha() {
        double nvlSeguranca = 0;

        LerMessage message = new LerMessage();
        if (txtSenha.getText().isEmpty()) {
            barSenha.setProgress(0);
            lblStatusSeguranca.setText(message.getMessage("lbl.StatusSenhaInsuficiente"));
            lblStatusSeguranca.setTextFill(Paint.valueOf("#dc143c"));
            barSenha.setProgress(0);
            return;
        }

        if (txtSenha.getText().length() < 4 && txtSenha.getText().length() != 0) {
            lblStatusSeguranca.setText(message.getMessage("lbl.StatusSenhaInsuficiente"));
            lblStatusSeguranca.setTextFill(Paint.valueOf("#dc143c"));
            //  barSenha.backgroundProperty().setValue(Background);
            lblStatusSeguranca.setVisible(true);
            barSenha.setProgress(0);
            return;
        }
        double tamanho;
        tamanho = txtSenha.getText().length() / 10.0;
        if (tamanho > 1) {
            tamanho = 1;
        }

        if (txtSenha.getText().matches(".*[a-z].*")) {
            nvlSeguranca = 0.1;
        }
        if (txtSenha.getText().matches(".*[0-9].*")) {
            nvlSeguranca = 0.1;
        }
        if (txtSenha.getText().matches(".*[A-Z].*")) {
            nvlSeguranca = 0.2;
        }
        if (txtSenha.getText().matches(".*[A-Z].*") && txtSenha.getText().matches(".*[a-z].*")) {
            nvlSeguranca = 0.3;
        }
        if (txtSenha.getText().matches(".*[a-z].*") && txtSenha.getText().matches(".*[0-9].*")) {
            nvlSeguranca = 0.4;
        }
        if (txtSenha.getText().matches(".*[A-Z].*") && txtSenha.getText().matches(".*[0-9].*")) {
            nvlSeguranca = 0.7;
        }
        if (txtSenha.getText().matches(".*[A-Z].*") && txtSenha.getText().matches(".*[a-z].*")
                && txtSenha.getText().matches(".*[0-9].*")) {
            nvlSeguranca = 1;
        }

        nvlSeguranca = (nvlSeguranca + tamanho) / 2;

        if (txtSenha.getText().length() >= 20 && nvlSeguranca == 0.55) {
            nvlSeguranca = nvlSeguranca + 0.2;
            barSenha.setStyle("-fx-accent: red;");
        }
        if (txtSenha.getText().length() >= 20 && nvlSeguranca == 0.6) {
            nvlSeguranca = nvlSeguranca + 0.2;
        }
        if (txtSenha.getText().length() >= 20 && nvlSeguranca == 0.65) {
            nvlSeguranca = nvlSeguranca + 0.2;
        }
        if (txtSenha.getText().length() >= 20 && nvlSeguranca == 0.7) {
            nvlSeguranca = nvlSeguranca + 0.2;
        }

        if (nvlSeguranca == 0) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lblStatusSeguranca.setText(message.getMessage("lbl.StatusSenhaInsuficiente"));
                    lblStatusSeguranca.setVisible(true);
                    barSenha.setStyle("-fx-accent: red;");
                }
            });
            return;
        }
        if (nvlSeguranca < 0.5) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lblStatusSeguranca.setVisible(false);
                    lblStatusSeguranca.setText(message.getMessage("lbl.StatusSenhaFraca"));
                    lblStatusSeguranca.setVisible(true);
                    barSenha.setStyle("-fx-accent: red;");
                }
            });
        }
        if (nvlSeguranca < 0.8 && nvlSeguranca >= 0.5) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lblStatusSeguranca.setText(message.getMessage("lbl.StatusSenhaMedia"));
                    lblStatusSeguranca.setTextFill(Paint.valueOf("#ffa500"));
                    lblStatusSeguranca.setVisible(true);
                    barSenha.setStyle("-fx-accent: #ffa500;");
                }
            });
        }
        if (nvlSeguranca >= 0.8) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lblStatusSeguranca.setText(message.getMessage("lbl.StatusSenhaForte"));
                    lblStatusSeguranca.setTextFill(Paint.valueOf("#32cd32"));
                    lblStatusSeguranca.setVisible(true);
                    barSenha.setStyle("-fx-accent: #32cd32;");
                }
            });
        }

        barSenha.setProgress(nvlSeguranca);
    }

}
