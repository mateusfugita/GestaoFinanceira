package VO;

import java.util.Date;

public class GastoVO extends PadraoVO {
    private int idEmpresa;
    private int idUsuario;
    private int idPagamento;
    private Double valor;
    private Date dataGasto;

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }

    public void setDataGasto(Date dataGasto) {
        this.dataGasto = dataGasto;
    }

    public Date getDataGasto() {
        return dataGasto;
    }
}
