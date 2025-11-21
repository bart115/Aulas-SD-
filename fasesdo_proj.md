# Objetivo
* Serviço de registo de eventos em séries temporais e de agregação de informação relativos à veda de produtos.
* A informação é mantida num servidor e acedida remotamente por um cliente. 
* Clientes interagem atravez de sockets TCP de forma a consultar e inserir informação. 
* O servidor deve atender os clientes concorrentemente e armazenar a informação
* 'D' é um parâmetro de inicialização do servidor e refere-se aos D dias anteriores. Todas as operações referem se aos D dias anteriores ?? 


# Funcionalidades
## Login e Registo de utilizadores
* Registo e autenticação de utilizadores, dado o seu nome e palavra passe.
* Sempre que um utilizador desejar interagir com o serviço, deverá estabelecer uma conexão e ser autenticado pelo servidor. O servidor não deverá processar qualquer pedido de um cliente que não esteja autenticado, exceto os próprios pedidos de autenticação/registo
## Registo de eventos 
* Operação de adicionar um eventos à serie temporal do dia corrente dando o nome do produto, quantidade e preço de venda ?