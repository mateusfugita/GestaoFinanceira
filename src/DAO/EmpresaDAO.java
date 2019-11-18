package DAO;

import VO.EmpresaVO;
import VO.PadraoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class EmpresaDAO extends PadraoDAO {
    public EmpresaDAO(){
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
        EmpresaVO empresa = (EmpresaVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setInt("id", empresa.getId());
        stmt.setString("", empresa.getNome());
        stmt.setInt("", empresa.getIdCategoria());
        return stmt;
    }
}
