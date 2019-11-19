package DAO;

import VO.CategoriaEmpresaVO;
import VO.PadraoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CategoriaEmpresaDAO extends PadraoDAO {
    public CategoriaEmpresaDAO(){
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
        CategoriaEmpresaVO categoriaEmpresaVO = (CategoriaEmpresaVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setInt("", categoriaEmpresaVO.getId());
        stmt.setString("", categoriaEmpresaVO.getDescricao());
        return stmt;
    }
}
