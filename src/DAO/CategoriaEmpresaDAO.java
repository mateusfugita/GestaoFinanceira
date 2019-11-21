package DAO;

import VO.CategoriaEmpresaVO;
import VO.PadraoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CategoriaEmpresaDAO extends PadraoDAO {
    public CategoriaEmpresaDAO(){
        setTabela("tbl_Categoria");
        setChave("Id_Categoria");
    }
    @Override
    protected String nomeProcedureInsert() {
        return "{CALL sp_inserir_categoriaEmpresa(?)}";
    }

    @Override
    protected String nomeProcedureUpdate() {
        return "{CALL sp_atualizar_categoriaEmpresa(?)}";
    }

    @Override
    protected CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        CategoriaEmpresaVO categoriaEmpresaVO = (CategoriaEmpresaVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setInt("", categoriaEmpresaVO.getId());
        stmt.setString("DescCategoria", categoriaEmpresaVO.getDescricao());
        return stmt;
    }
}
