/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios.mock.auxiliar;

/**
 *
 * @author Henrique
 */
public class EntradaMaterial {

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getQtd_entrada() {
        return qtd_entrada;
    }

    public void setQtd_entrada(int qtd_entrada) {
        this.qtd_entrada = qtd_entrada;
    }

    public float getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(float valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public float getValor_total() {
        return valor_total;
    }

    public void setValor_total(float valor_total) {
        this.valor_total = valor_total;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
    
    private String material;
    private int qtd_entrada;
    private float valor_unitario;
    private float valor_total;
    private String local;
    
}
