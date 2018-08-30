<%-- 
    Document   : max
    Created on : 30/07/2018, 11:08:45
    Author     : Ruan Bertuce
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script>
    var jsonItem = 0, jsonTag = 0, jsonTagsItems = 0, jsonItemsTags = 0;    
    jsonItem = JSON.parse('<%=request.getSession().getAttribute("jsonItem")%>');   
    jsonTag = JSON.parse('<%=request.getSession().getAttribute("jsonTag")%>');
    jsonTagsItems = JSON.parse('<%=request.getSession().getAttribute("jsonTagsItems")%>');
    jsonItemsTags = JSON.parse('<%=request.getSession().getAttribute("jsonItemsTags")%>');
</script>

<!DOCTYPE html>
<html lang="pt_BR">
<head>
    <meta charset="utf-8">
	<title>MAX</title>
    <link rel="stylesheet" type="text/css" href="css/max.css" />
    <script type="text/javascript" src="js/plugins/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="js/plugins/jquery/jquery-ui.min.js"></script>
    <script type="text/javascript" src="js/plugins/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/plugins.js"></script>
    <script type="text/javascript" src="js/actions.js"></script>
</head>
<body> 
	<div class="scroll">
        <input type="radio" name="group" id="rd_max">
        <input type="radio" name="group" id="rd_manual">
        <section class="sections">
            <section class="block" id="max">
                <a href="/organizer/servletcontroller?process=LoadItem">Voltar</a>
                <div>
                    <div class="division">
                        <span style="color: #1E90FF;">Você diz:</span> <br> 
                        <textarea id="transcription" class="box"></textarea> <p>
                    </div>
                    <div class="division">
                        <span style="font-size: 30px;"><h1>MAX</h1><span>
                        <img src="imgs/sphere.gif"> <p>
                        <button id="btn_interact"> <span>Interagir</span> </button> <p>
                        <button id="btn_manual"> <span>Manual de interações</span> </button>
                    </div>
                    <div class="division">
                        <span style="color: #1E90FF;">Max diz:</span> <br>
                        <textarea id="speech" class="box" disabled="true"></textarea>
                    </div>
                </div>
            </section>
            <section class="block" id="manual">
                <button id="btn_max">Voltar</button> <p>
                <div id="manual_text">        

<h2>Manual de interações do MAX</h2>
<ul>
    <li class="titles" style="list-style-type:none;">Informações iniciais</li><br>
        <ul>
            <li> Sobre a natureza do documento:
                <ul type="circle">
                    <li> Esse documento descreve os comandos com os quais o MAX interage
                </ul><br>        
            <li> Sobre as características do documento:
                <ol>
                    <li> As partes fixas dos comandos estão escritas em preto
                    <li> As partes variáveis, isto é, aquelas que variam conforme as características dos itens, estão escritas em azul 
                    <li> As respostas do MAX aos comandos estão escritas em branco
                    <li> As setinhas delimitam os momentos da interação em que você deve falar e os momentos em que o MAX reage
                </ol><br> 
            <li> Sobre as formas de interagir com o MAX:  
                <ul type="circle"> 
                    <li> Existem 2 maneiras de interagir com o MAX: 
                        <ol>
                            <li> Clicar no botão "Interagir" e dizer algum dos comandos nos formatos descritos neste documento
                            <li> Escrever um comando nos mesmos formatos dentro da caixa "Você diz:" e clicar no botão "Interagir"   
                        </ol>
                </ul><br>
            <li> Sobre as ações e os tipos de interações do MAX
                <ul type="circle">
                    <li> Ações são como o próprio nome sugere ações, neste caso realizadas pelo MAX de acordo com os comandos informados
                    <li> Os tipos de interações são conjuntos de ações que compartilham características semelhantes
                    <li> O MAX possui 20 ações e 10 tipos de interações  
                </ul><br>  
            <li> Sobre os padrões utilizados nas partes variáveis de um comando
                <ul type="circle">
                    <li> As partes variáveis que são nomes de itens, nomes de tags, descrições ou palavras-chave de pesquisa aceitam qualquer tipo de expressão
                    <li> As partes variáveis que são datas devem estar no padrão "dia-mês-ano" sendo separadas pela preposição "de". Ex: 28 de julho de 2018
                    <li> Para a data também é aceito o formato dia-mês, sendo que o ano será considerado o atual. Ex: 28 de julho (será interpretado como 28 de julho do ano atual) 
                    <li> A resposta para a pergunta do MAX "Há itens que utilizam essa tag. Ainda sim deseja excluí-la?" na interação "Excluir tarefa" só pode ser "sim" ou "não". Outras respostas são inválidas
                </ul>  
        </ul>
</ul>
<ul style="list-style-type: none;"> 
    <li class="titles"> Tipos de interações</li><br>
        <ol type="I">    
            <li class="titles">Interações básicas</li><br> 
                <ul>
                    <li> Olá? 
                    <li> Quem é você?
                    <li> Como você pode me ajudar? 
                    <li> Obrigado! 
                </ul><br>
            <li class="titles"> Criar item</li><br>  
                <ul>
                    <li> Criar item simples <i>"nome do item simples"</i> > <span class="m_s">Descrição</span> > <i>"descrição do item simples"</i>
                    <li> Criar tarefa <i>"nome da tarefa"</i> > <span class="m_s">Descrição</span> > <i>"descrição da tarefa"</i> > <span class="m_s">Data</span> > <i>"data da tarefa"</i>
                    <li> Criar lembrete <i>"nome do lembrete"</i> > <span class="m_s">Descrição</span> > <i>"descrição do lembrete"</i> > <span class="m_s">Data</span> > <i>"data do lembrete"</i> 
                </ul><br>    
            <li class="titles"> Alterar item</li><br> 
                <ul>
                    <li> Alterar o nome do item <i>"nome do item"</i> > <span class="m_s">Qual o novo nome?</span> > <i>"novo nome"</i>
                    <li> Alterar a descrição do item <i>"nome do item"</i> > <span class="m_s">Qual a nova descrição?</span> > <i>"nova descrição"</i>
                    <li> Alterar a data do item <i>"nome do item"</i> > <span class="m_s">Qual a nova data?</span> > <i>"nova data"</i>
                </ul><br>     
            <li class="titles"> Excluir item</li><br> 
                <ul>
                    <li> Excluir item <i>"nome do item"</i>
                    <li> Excluir itens com a tag <i>"nome da tag"</i>
                </ul><br> 
            <li class="titles"> Pesquisar item</li><br> 
                <ul>
                    <li> Pesquisar itens
                    <li> Pesquisar item usando <i>"palavra-chave"</i>
                    <li> Pesquisar item pelo tipo usando <i>"tipo-chave"</i>    
                    <li> Pesquisar item pelo nome usando <i>"palavra-chave"</i>
                    <li> Pesquisar item pela descrição usando <i>"palavra-chave"</i>
                    <li> pesquisar item pela data usando <i>"data-chave"</i>
                    <li> Pesquisar item pela tag usando <i>"tag-chave"</i>
                </ul><br> 
            <li class="titles"> Criar tag</li><br>
                <ul>
                    <li> Criar tag <i>"nome da tag"</i>
                </ul><br> 
            <li class="titles"> Alterar tag</li><br> 
                <ul>
                    <li> Alterar o nome da tag <i>"nome da tag"</i> > <span class="m_s">Qual o novo nome?</span> > <i>"novo nome"</i>
                </ul><br> 
            <li class="titles"> Excluir tag</li><br> 
                <ul>
                    <li> Excluir tag <i>"nome da tag"</i> > <span class="m_s">Há itens que utilizam essa tag. Ainda sim deseja excluí-la?</span> > <i>"sim"</i> ou <i>"não"</i>
                </ul><br> 
            <li class="titles"> Adicionar uma tag a um item</li><br>
                <ul>
                    <li> Adicionar tag ao item <i>"nome do item"</i> > <span class="m_s">Qual o nome da tag?</span> > <i>"nome da tag"</i>
                </ul><br> 
            <li class="titles"> Remover uma tag de um item</li><br>
                <ul>
                    <li> Remover tag do item <i>"nome do item"</i> > <span class="m_s">Qual o nome da tag?</span> > <i>"nome da tag"</i>
                </ul><br> 
        </ol>
</ul>

                </div>
            </section>
        </section>
    </div>
    <script type="text/javascript" src="js/max.js"></script>
</body>
</html>
