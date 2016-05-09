===========================================================
BANCO DE DADOS 2 | APS: ESCALONAMENTO DE PROCESSOS | FASE 2
===========================================================
HENRIQUE SOUZA | SAVIO CAMACAM

Descrição

O problema consiste na simulação de um escalonador de transações de um SGBD.
O programa recebe do usuário os parâmetros:
* Número de itens de dados;
* Número de transações;
* Número de acessos;

Executando o programa no Windows usando o PowerShell ou no terminal do Linux, siga até o diretório onde se encontra o arquivo aps_bd2.jar e execute o comando:
```
java -jar aps_bd2.jar
```
Como opções de menu:
* (1) Gerar Transacoes: informados a quantidade de itens de dados, transações e acessos e nome do arquivo que deve ser gerado com as transacoes;
* (2) Gerar Schedule: informados nome nome do arquivo de entrada de transações (fonte) e nome do arquivo de saida do schedule (destino);
* (3) Escalonar Schedule: informado o nome do arquivo de schedule de entrada (fonte). 
* (4) Sair: programa é encerrado.

A qualquer tempo pode ser executada qualquer função do programa, desde que acessando arquivos que existam no diretório de execução do programa.
Apenas números inteiros devem ser inseridos para garantir sua execução.
Ao executar funções que geram novas saidas, ao criar o arquivo, se ele existir será sobrescrito.
Ao encontrar o deadlock o programa exibe as transações diretamente no console. As transações são apresentadas de forma desordenada logicamente, mas respeitando
a ordem de leitura do arquivo de schedule marcando quando as transações foram iniciadas.
