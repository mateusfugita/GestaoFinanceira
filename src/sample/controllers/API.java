package sample.controllers;

import DAO.EmpresaDAO;
import DAO.EntradaDAO;
import DAO.GastoDAO;
import VO.EmpresaVO;
import VO.EntradaVO;
import VO.GastoVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class API {
    public void callAPI() {
        //----------------------------COMEÇA AQUI
        JSONParser parser = new JSONParser();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        try {
            JSONArray arr = (JSONArray) parser.parse(new FileReader("empresas.json"));
            arr.forEach(item -> {
                EmpresaVO empresa = new EmpresaVO();
                JSONObject jsonObject = (JSONObject) item;
                empresa.setId(((Long) jsonObject.get("id")).intValue());
                empresa.setNome((String) jsonObject.get("nome"));
                empresa.setIdCategoria(((Long) jsonObject.get("id_categoria")).intValue());
                try {
                    empresaDAO.inserir(empresa);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        GastoDAO gastoDAO = new GastoDAO();
        try {
            JSONArray arr = (JSONArray) parser.parse(new FileReader("gastos.json"));
            arr.forEach(item -> {
                GastoVO gasto = new GastoVO();
                JSONObject jsonObject = (JSONObject) item;
                gasto.setId(((Long) jsonObject.get("id")).intValue());
                gasto.setValor(((Long) jsonObject.get("valor")).doubleValue());
                gasto.setIdPagamento(((Long) jsonObject.get("id_pagamento")).intValue());
                gasto.setIdUsuario(((Long) jsonObject.get("id_usuario")).intValue());
                try {
                    gasto.setDataGasto(formatter.parse((String) jsonObject.get("data")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                gasto.setIdEmpresa(((Long) jsonObject.get("id_empresa")).intValue());
                try {
                    gastoDAO.inserir(gasto);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        EntradaDAO entradaDAO = new EntradaDAO();
        try{
            JSONArray arr = (JSONArray) parser.parse(new FileReader("entradas.json"));
            arr.forEach(item -> {
                EntradaVO entrada = new EntradaVO();
                JSONObject jsonObject = (JSONObject) item;
                entrada.setId(((Long) jsonObject.get("id")).intValue());
                entrada.setValor(((Long) jsonObject.get("valor")).floatValue());
                try {
                    entrada.setDataEntrada(formatter.parse((String) jsonObject.get("Data_Entrada")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                entrada.setIdUsuario(((Long) jsonObject.get("id_usuario")).intValue());
                try {
                    entradaDAO.inserir(entrada);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
