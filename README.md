minicredor
==========

Mini Credor é uma aplicação java, onde realizamos a simulação de ambiente entre uma loja e uma operadora de credito. Projeto de fins acadêmico, realizado na Faculdade Juazeiro do Norte - CE, na cadeira de Fundamentos de Sistemas de Informação do professor Isydório Alves Donato. 

Banco de dados;
 Postgres

SQL;

CREATE TABLE solicitacao_compras
(
  id serial NOT NULL,
  loja_id integer NOT NULL,
  cartao_id character(16) NOT NULL,
  nome_cliente character varying(70) NOT NULL,
  data_validade character varying(10) NOT NULL,
  num_seguranca integer NOT NULL,
  valor_total numeric NOT NULL,
  qtd_parcelas integer NOT NULL,
  data_compra character varying(10) NOT NULL,
  codigo_venda integer,
  CONSTRAINT solicitacao_compras_pkey PRIMARY KEY (id)
)
