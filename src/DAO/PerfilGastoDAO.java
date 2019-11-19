package DAO;

import VO.PadraoVO;
import VO.PerfilGastoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PerfilGastoDAO extends PadraoDAO {
    public PerfilGastoDAO(){
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
        PerfilGastoVO perfilGastoVO = (PerfilGastoVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setInt("", perfilGastoVO.getId());
        stmt.setString("", perfilGastoVO.getDescricao());
        return stmt;
    }
}
