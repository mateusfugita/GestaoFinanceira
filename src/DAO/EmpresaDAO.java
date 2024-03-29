package DAO;

import VO.EmpresaVO;
import VO.PadraoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class EmpresaDAO extends PadraoDAO {
    public EmpresaDAO(){
        setTabela("tbl_Empresa");
        setChave("Id_Empresa");
    }
    @Override
    protected String nomeProcedureInsert() {
        return "{CALL sp_inserir_empresa(?,?,?)}";
    }

    @Override
    protected String nomeProcedureUpdate() {
        return "{CALL sp_atualizar_empresa(?,?,?)}";
    }

    @Override
    protected CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        EmpresaVO empresa = (EmpresaVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setString("nome", empresa.getNome());
        stmt.setInt("id_categoria", empresa.getIdCategoria());
        stmt.setInt("id", empresa.getId());
        return stmt;
    }
}
