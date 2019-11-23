package DAO;

import VO.PadraoVO;
import VO.PerfilGastoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PerfilGastoDAO extends PadraoDAO {
    public PerfilGastoDAO(){
        setTabela("tbl_PerfilGasto");
        setChave("Id_PerfilGasto");
    }

    @Override
    protected String nomeProcedureInsert() {
        return "{CALL sp_inserir_perfilGasto(?)}";
    }

    @Override
    protected String nomeProcedureUpdate() {
        return "{CALL sp_atualizar_perfilGasto(?,?)}";
    }

    @Override
    protected CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        PerfilGastoVO perfilGastoVO = (PerfilGastoVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setString("Descricao", perfilGastoVO.getDescricao());
        stmt.setInt(getChave(), perfilGastoVO.getId());
        return stmt;
    }
}
