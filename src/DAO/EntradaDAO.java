package DAO;

import VO.EntradaVO;
import VO.PadraoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class EntradaDAO extends PadraoDAO {
    public EntradaDAO(){
        setTabela("tbl_Entrada");
        setChave("Id_Entrada");
    }
    @Override
    protected String nomeProcedureInsert() {
        return "{CALL sp_inserir_entrada(?,?,?)}";
    }

    @Override
    protected String nomeProcedureUpdate() {
        return "{CALL sp_atualizar_entrada(?,?,?,?)}";
    }

    @Override
    protected CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        EntradaVO entradaVO = (EntradaVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setFloat("valor", entradaVO.getValor());
        stmt.setDate("data_entrada", (Date)entradaVO.getDataEntrada());
        stmt.setInt("id_usuario", entradaVO.getIdUsuario());
        stmt.setInt("id", entradaVO.getId());
        return stmt;
    }
}
