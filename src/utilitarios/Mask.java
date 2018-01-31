/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

/**
 *
 * @author Henrique
 */
public class Mask {

    public String CpfCnpj(String txt) {
        txt = txt.replaceAll("[^0-9]", "");

        if (txt.length() <= 11) {
            txt = txt.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            txt = txt.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            txt = txt.replaceFirst("(\\d{3})(\\d)", "$1-$2");
        } else {
            txt = txt.replaceFirst("(\\d{2})(\\d)", "$1.$2");
            txt = txt.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            txt = txt.replaceFirst("(\\d{3})(\\d)", "$1/$2");
            txt = txt.replaceFirst("(\\d{4})(\\d)", "$1-$2");
        }

        txt = txt.trim();

        if (txt.length() > 18) {
            return txt.substring(0, 18);
        } else {
            return txt;
        }
    }

    public String TelefoneFixo(String txt) {

        txt = txt.replaceAll("[^0-9]", "");

        txt = txt.replaceFirst("(\\d{0})(\\d)", "$1($2");
        txt = txt.replaceFirst("(\\d{2})(\\d)", "$1) $2");
        txt = txt.replaceFirst("(\\d{4})(\\d)", "$1-$2");

        if (txt.length() > 14) {
            return txt.substring(0, 14);
        } else {
            return txt;
        }
    }

    public String TelefoneCelular(String txt) {

        txt = txt.replaceAll("[^0-9]", "");

        txt = txt.replaceFirst("(\\d{0})(\\d)", "$1($2");
        txt = txt.replaceFirst("(\\d{2})(\\d)", "$1) $2");
        txt = txt.replaceFirst("(\\d{5})(\\d)", "$1-$2");

        if (txt.length() > 15) {
            return txt.substring(0, 15);
        } else {
            return txt;
        }

    }

    public String Desmascarar(String txt) {
        txt = txt.replaceAll("[^a-zA-Z0-9]", "");
        return txt;
    }

    public double Monetaria_To_Double(String txt) {
        Double value = 0.0;

        txt = txt.replaceAll("[^0-9]", "");
        txt = txt.replaceFirst("(\\d{1})(\\d{1,2})$", "$1.$2");

        value = Double.parseDouble(txt);

        return value;
    }

    public String Monetaria(String txt) {

        if (txt == null) {
            return "";
        }
        if (txt.length() == 0) {
            return "";
        }
        txt = txt.replaceAll("[^0-9]", "");

        if (txt.length() > 14) {
            txt = txt.substring(0, 14);
        }

        try {

            txt = txt.replaceFirst("\\d{1}(\\d{14})$", "$1.$2");
            txt = txt.replaceFirst("(\\d{1})(\\d{11})$", "$1.$2");
            txt = txt.replaceFirst("(\\d{1})(\\d{8})$", "$1.$2");
            txt = txt.replaceFirst("(\\d{1})(\\d{5})$", "$1.$2");
            txt = txt.replaceFirst("(\\d{1})(\\d{1,2})$", "$1,$2");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return txt;
    }

    public String OnlyFloat(String txt) {

        if (txt == null) {
            return "";
        }
        if (txt.length() == 0) {
            return "";
        }

        txt = txt.replaceAll("[^0-9]", "");
        int pos = 0;
        if (txt.length() < 0) {
            pos = 0;
        } else if (txt.length() > 3) {
            pos = txt.length() - 3;
        }

        txt = txt.replaceFirst("(\\d{" + pos + "})(\\d)", "$1.$2");

        if (pos == 0) {
            txt = "0" + txt;
        }
        return txt;
    }

    public String OnlyInt(String txt) {
        if (txt == null) {
            return "";
        }
        if (txt.length() == 0) {
            return "";
        }

        txt = txt.replaceAll("[^0-9]", "");

        return txt;
    }
}
