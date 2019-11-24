package DAO;

import VO.PadraoVO;
import VO.PagamentoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class PagamentoDAO extends PadraoDAO {
    public PagamentoDAO(){
        setTabela("tbl_Pagamento");
        setChave("Id_Pagamento");
    }
    @Override
    protected String nomeProcedureInsert() {
        return "{CALL sp_inserir_pagamento(?,?)}";
    }

    @Override
    protected String nomeProcedureUpdate() {
        return "{CALL sp_atualizar_pagamento(?,?)}";
    }

    @Override
    protected CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        PagamentoVO pagamentoVO = (PagamentoVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setString("tipo_pagamento", pagamentoVO.getTipoPagamento());
        stmt.setInt("id", pagamentoVO.getId());
        return stmt;
    }
}
