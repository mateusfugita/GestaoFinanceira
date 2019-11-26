import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sb
from sklearn.naive_bayes import GaussianNB
import mysql.connector
import DAO as dao

TxtId = open('Id_Usuario.txt', 'r')
id = TxtId.readlines()[0]
print(id)
id = int(id)
TxtId.close()
resultSelect = dao.BuscaIdade(id)
resultSelect = resultSelect.rename(columns={0: 'Idade'})
idade = int(resultSelect.Idade)
resultSelect = dao.BuscaDados()
resultSelect = resultSelect.rename(columns={0: 'Id_Usuario', 1: 'Idade', 2:'Id_Perfil_Usuario', 3:'Id_categoria_Empresa'})
print(resultSelect)

ListaCatEmp = list(resultSelect['Id_categoria_Empresa'])
ListaIdade = list(resultSelect['Idade'])

x = np.array(ListaIdade).reshape((-1, 1))
y = np.array(ListaCatEmp)

model = GaussianNB()
model.fit(x, y)
model = GaussianNB().fit(x, y)


valor = idade
valor1 = np.array([int(valor), 0]).reshape((-1,1))
y_pred = model.predict(valor1)[0]
print('Valor da predição:',y_pred)
textoPredicao = ""

if y_pred == 1:
    textoPredicao = ' com alimentos, tente preparar mais comida em casa.'
if y_pred == 2:
    textoPredicao = ' com empresas de transporte, experimente começar a andar de bicicleta ou utilizar mais o transporte publico'
if y_pred == 3:
    textoPredicao = ' com empresas de varejo, leve uma lista quando for fazer compras'
if y_pred == 4:
    textoPredicao = ' com empresas de educação, você está em um bom caminho mas procure por mais conteudo gratuito'
if y_pred == 5:
    textoPredicao = ' com games'

TextoGastoIdade = 'O gasto mais comum para a sua faixa de idade é' + textoPredicao
print(TextoGastoIdade)
print('--------------------------------------------------------------------------')
resultSelect = dao.BuscaMaiorFormaDePagamento(id)
try:
    resultSelect = resultSelect.rename(columns={0: 'NmrVezes', 1: 'Id_TipoDeGasto'})
    FormaPagamento = int(resultSelect.Id_TipoDeGasto)
except:
    FormaPagamento = 5

NmrVezes = int(resultSelect.NmrVezes)
if FormaPagamento == 1:
    textoFormadePag = 'Sua forma de pagamento mais utilizanda é o dinheiro, no total foram ' + str(NmrVezes) + ' vezes'
if FormaPagamento == 2:
    textoFormadePag = 'Sua forma de pagamento mais utilizanda é o cartão de débito, no total foram ' + str(NmrVezes) + ' vezes'
if FormaPagamento == 3:
    textoFormadePag = 'Sua forma de pagamento mais utilizanda é o cartão de crédito, no total foram ' + str(NmrVezes) + ' vezes'
if FormaPagamento == 4:
    textoFormadePag = 'Sua forma de pagamento mais utilizanda é o cheque, no total foram ' + str(NmrVezes) + ' vezes'
if FormaPagamento == 5:
    textoFormadePag = 'Não foram encontrados dados para poder dizer qual é a sua maior forma de pagamento'

print(textoFormadePag)

TxtSaida = open('Saida.txt', 'w', encoding='utf-8')
TxtSaida.writelines(TextoGastoIdade + '\n')
TxtSaida.writelines(textoFormadePag)
TxtSaida.close()


