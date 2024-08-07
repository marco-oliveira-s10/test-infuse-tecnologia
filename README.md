# test-infuse-tecnologia
Project in Spring Boot

Criar uma solução java em formato de API que atenda aos seguintes requisitos para a recepção de pedidos dos clientes:
	- Criar um serviço que receba pedidos no formato xml e json com 6 campos: - OK
	- Número controle - número aleatório informado pelo cliente. - OK
	- Data cadastro (opcional) - OK
	- Nome do produto - OK
	- Valor monetário unitário produto - OK
	- Quantidade (opcional) - quantidade de produtos. - OK
	- Codigo cliente - identificação numérica do cliente. - OK

Critérios aceitação e manipulação do arquivo:
	- O arquivo pode conter 1 ou mais pedidos, limitado a 10. - OK
	- Não poderá aceitar um número de controle já cadastrado. - OK
	- Caso a data de cadastro não seja enviada o sistema deve assumir a data atual. - OK
	- Caso a quantidade não seja enviada considerar 1. - OK
	- Caso a quantidade seja maior que 5 aplicar 5% de desconto no valor total, para quantidades a partir de 10 aplicar 10% de desconto no valor total. - OK
	- O sistema deve calcular e gravar o valor total do pedido. - OK
	- Assumir que já existe 10 clientes cadastrados, com códigos de 1 a 10. - OK

	- Criar um serviço onde possa consultar os pedidos enviados pelos clientes. - OK
	- Critérios aceitação: O retorno deve trazer todos os dados do pedido. - OK
	- filtros da consulta: número pedido, data cadastro, todos - OK

	Frameworks:
	Fica a critério do candidato utilizar ou não, sem restrições de escolha. (Spring Boot) - OK

	
	Desejável:
	
		- Injeção de dependência - OK
		- Padrões de projeto - OK
		- Testes unitários - OK

	Obrigatório:
		- Banco de dados mysql - OK
		- Utilizar framework ORM - OK
		- Utilizar a linguagem java 1.8 ou versão mais recente - OK
		- A solução deve ser publicada no github juntamente com o script de criação das tabelas. (ORM) - OK
		- Deve ser enviado o link do repositório da solução para este email.

	OBS RESTful:
		- Swagger - OK
		- Collection Postman - OK
		- hateoas - OK


Aguardo confirmação de recebimento do e-mail!
