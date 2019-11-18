package VO;

import java.util.Date;

public class EntradaVO extends PadraoVO {
    private float valor;
    private Date dataEntrada;
    private int idUsuario;

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getValor() {
        return valor;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
}
