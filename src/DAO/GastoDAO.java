package DAO;

import VO.GastoVO;
import VO.PadraoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class GastoDAO extends PadraoDAO {
    public GastoDAO(){
        setTabela("tbl_Gasto");
        setChave("IdGasto");
    }

    @Override
    protected String nomeProcedureInsert() {
        return "{CALL sp_inserir_gasto(?,?,?,?,?,?}";
    }

    @Override
    protected String nomeProcedureUpdate() {
        return "{CALL sp_atualizar_gasto(?,?,?,?,?,?)}";
    }

    @Override
    protected CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        GastoVO gastoVO = (GastoVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setInt("id_empresa", gastoVO.getIdEmpresa());
        stmt.setInt("id_usuario", gastoVO.getIdUsuario());
        stmt.setInt("id_pagamento", gastoVO.getIdPagamento());
        stmt.setDouble("valor", gastoVO.getValor());
        stmt.setDate("data_gasto", (Date)gastoVO.getDataGasto());
        stmt.setInt("id", gastoVO.getId());
        return stmt;
    }
}
