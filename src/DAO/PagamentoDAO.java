package DAO;

import VO.PadraoVO;
import VO.PagamentoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PagamentoDAO extends PadraoDAO {
    public PagamentoDAO(){
        setTabela("");
        setChave("");
    }
    @Override
    protected String nomeProcedureInsert() {
        return null;
    }

    @Override
    protected String nomeProcedureUpdate() {
        return null;
    }

    @Override
    public CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        PagamentoVO pagamentoVO = (PagamentoVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setInt("", pagamentoVO.getId());
        stmt.setString("", pagamentoVO.getTipoPagamento());
        return stmt;
    }
}
