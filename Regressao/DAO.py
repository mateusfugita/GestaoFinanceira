import mysql.connector
import pandas as pd
import mysql.connector


def BuscaDados():
    connection = mysql.connector.connect(host='localhost',database='Financeira1',user='root',password='1234')
    try:
        sql_select_Query = """select  usu.Id_Usuario, usu.Idade,  emp.Nome_Empresa, catEmp.Id_Categoria from tbl_Usuario as usu
                            inner join tbl_Gasto as gast on
                            gast.Id_Usuario = usu.Id_Usuario
                            inner join tbl_Empresa as emp on
                            emp.Id_Empresa = gast.Id_Empresa
                            inner join tbl_CatEmpresa as catEmp on
                            catEmp.Id_Categoria = emp.Id_Categoria order by idade """
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        resultSelect = pd.DataFrame(records)
        return resultSelect
    finally:
        cursor.close()
        connection.close()

def BuscaIdade(id_usuario):
    connection = mysql.connector.connect(host='localhost', database='Financeira1', user='root', password='1234')
    try:
        sql_select_Query = 'select Idade from tbl_Usuario where Id_Usuario = ' + str(id_usuario)
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        resultSelect = pd.DataFrame(records)
        return resultSelect
    finally:
        cursor.close()
        connection.close()

def BuscaMaiorFormaDePagamento(id_usuario):
    connection = mysql.connector.connect(host='localhost', database='Financeira1', user='root', password='1234')
    try:
        sql_select_Query = 'select  count(*) as NrVezes from tbl_Gasto where Id_Usuario = ' + str(id_usuario)
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        resultSelect = pd.DataFrame(records)
        #print("Select ")
        #print(resultSelect)
        resultSelectAux = resultSelect.rename(columns={0: 'counta'})
        count = int(resultSelectAux.counta)
        #print("count " + str(count))
        if count == 0:
            return resultSelect
        sql_select_Query = 'select  count(*) as NrVezes, Id_Pagamento  from tbl_Gasto where Id_Usuario = ' + str(id_usuario) + ' GROUP BY Id_Pagamento ORDER BY NrVezes DESC limit 1'
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        resultSelect = pd.DataFrame(records)
        return resultSelect
    finally:
        cursor.close()
        connection.close()


