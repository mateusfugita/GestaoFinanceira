package DAO;

import VO.EntradaVO;
import VO.PadraoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class EntradaDAO extends PadraoDAO {
    public EntradaDAO(){
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
        EntradaVO entradaVO = (EntradaVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setInt("", entradaVO.getId());
        stmt.setFloat("", entradaVO.getValor());
        stmt.setDate("", (Date)entradaVO.getDataEntrada());
        stmt.setInt("", entradaVO.getIdUsuario());
        return stmt;
    }
}
