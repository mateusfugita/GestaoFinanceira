package DAO;

import VO.GastoVO;
import VO.PadraoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class GastoDAO extends PadraoDAO {
    public GastoDAO(){
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
    protected CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        GastoVO gastoVO = (GastoVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setInt("", gastoVO.getId());
        stmt.setInt("", gastoVO.getIdEmpresa());
        stmt.setInt("", gastoVO.getIdUsuario());
        stmt.setInt("", gastoVO.getIdPagamento());
        stmt.setDouble("", gastoVO.getValor());
        stmt.setDate("", (Date)gastoVO.getDataGasto());
        return stmt;
    }
}
