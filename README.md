IsiSpring — Mini Framework Web em Java 

Este projeto é um mini framework web desenvolvido do zero em Java, com o objetivo de entender como frameworks como o Spring Boot funcionam internamente.

A ideia não é competir com o Spring, mas aprender na prática conceitos avançados de backend, como:

servidores web embutidos

servlets

reflection

anotações customizadas

injeção de dependência

mapeamento de rotas HTTP

 Objetivo do Projeto

Entender como um framework web é construído

Compreender o fluxo completo:
HTTP Request → Dispatcher → Controller → Response

Aprender conceitos que normalmente ficam “escondidos” quando usamos Spring Boot

 Conceitos Aplicados

Programação Orientada a Objetos

Reflection API

Anotações customizadas (@IsiController, @IsiGetMethod, @IsiPostMethod, etc.)

Injeção de Dependência manual

Estruturas de dados (HashMap)

Servlets

Embedded Tomcat

Serialização JSON com Gson

Arquitetura inspirada em MVC

Arquitetura do Framework
Componentes principais:

IsiSpringWebApplication

Inicializa o framework

Sobe o Tomcat embutido

Faz o scan de classes e anotações

IsiDispatchServlet

Atua como Dispatcher central

Recebe todas as requisições HTTP

Resolve rota + método + controller

Executa o método correto via reflection

Annotations customizadas

@IsiController

@IsiGetMethod

@IsiPostMethod

@IsiBody

@IsiInjected

@IsiService

Data Structures

ControllerMaps → mapeia rotas HTTP

ControllersInstances → cache de controllers

DependencyInjectionMap → objetos injetados

ServiceImplementationMap → interface → implementação

 Fluxo de uma Requisição

Navegador faz uma requisição HTTP

Tomcat recebe e redireciona para o IsiDispatchServlet

O Dispatcher:

identifica URL e método HTTP

busca o controller correto

injeta dependências

executa o método via reflection

O retorno é convertido para JSON (Gson)

A resposta é enviada ao cliente

 Projeto de Teste

O repositório inclui uma aplicação de teste (webtestapp) que demonstra:

Controllers anotados

Métodos GET e POST

Request Body

Retorno de objetos em JSON

Injeção de serviços via interface
 
Tecnologias Utilizadas

Java

Embedded Tomcat

Servlet API

Gson

Maven

Reflection API

Observações Importantes

Este projeto é educacional

O foco é entendimento interno, não produção

Muitos conceitos aqui ajudam diretamente a:

entender Spring Boot

debugar problemas complexos

evoluir como desenvolvedor backend Java

 Autor

Este projeto foi desenvolvido com fins educacionais, baseado nas aulas e explicações do Prof. Isidro (IsiFLIX / PycodeBR), com o objetivo de compreender como frameworks web em Java funcionam internamente.

A implementação e os estudos foram realizados por Luciano Simão de Souza Junior, como parte do processo de aprendizado em desenvolvimento Back-end Java, explorando conceitos avançados como reflection, servlets, anotações e injeção de dependência.
