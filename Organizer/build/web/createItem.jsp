<%@page import="java.util.ArrayList"%>
<%@page import="br.cefetmg.inf.organizer.model.domain.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id='tagItem' class='java.util.ArrayList' scope="page"/>

<html>
    <head>
        <title>Organizer</title>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" type="text/css" href="css/theme-default.css" id="themeStyle"/>
        <link rel="stylesheet" href="css/styles.css">
    </head>
    <body>
        <div class="page-container">
            <!-- Menu lateral -->
            <%@include file="sideMenu.jsp"%>

            <div class="page-content">
                <!-- Menu Horizontal -->
                <%@include file="horizontalMenu.jsp"%>

                <div class="page-content-wrap">

                    <div class="row">
                        <div class="col-md-12">
                            <p></p>
                            <!-- Form -->
                            <form class="form-horizontal" action="/organizer/servletcontroller?process=CreateItem" method="post">

                                <div class="panel panel-default">

                                    <div class="panel-body">

                                        <h1 style="text-align:center">Criar Item</h1>

                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Tipo: </label>
                                            <div class="col-md-6 col-xs-12">
                                                <select class="form-control select" id="selectOfItemType" name="selectType">
                                                    <option value="SIM">Simples</option>
                                                    <option value="LEM">Lembrete</option>
                                                    <option value="TAR">Tarefa</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Nome: </label>
                                            <div class="col-md-6 col-xs-12">
                                                <div class="input-group">
                                                    <span class="input-group-addon"><span class="fa fa-pencil"></span></span>
                                                    <input type="text" class="form-control" name="nameItem" required/>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Descrição: </label>
                                            <div class="col-md-6 col-xs-12">
                                                <textarea class="form-control" rows="5" name="descriptionItem"></textarea>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Data: </label>
                                            <div class="col-md-6 col-xs-12">
                                                <div class="input-group">
                                                    <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                                                    <input type="date" class="form-control" name="dateItem" id="dateOfItem" readonly>
                                                </div>
                                                <span class="help-block" id="helpItem">Itens de tipo simples não possuem data.</span>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Tags: </label>
                                            <div class="col-md-6 col-xs-12">
                                                <div class="input-group">
                                                    <span class="input-group-addon"><span class="fa fa-tag"></span></span>
                                                    <input id="tags" type="text" class="form-control" data-toggle="modal" data-target="#tagsModal" name="inputTag" readonly/>
                                                </div>
                                            </div>
                                        </div>

                                        <button class="btn btn-primary pull-right" type="submit">Criar</button>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>

                </div>
            </div>
        </div>

        <!-- Modal de Inserir Tags durante a criação de um item-->
        <div class="modal fade" id="tagsModal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Adicionar Tags:</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Selecionados: </label>
                            <div class="input-group">
                                <span class="input-group-addon"><span class="fa fa-tag"></span></span>
                                <input id="tagSelected" type="text" class="form-control" disabled>
                            </div>
                        </div>
                        <hr>
                        <label>Existentes: </label>
                        <div class="container-fluid">
                            <div class="panel panel-default">
                                <div class="panel-body" id="scroll">
                                    <ul id="ulTags">

                                        <%
                                               // Lembrar de passar pra requisição
                                            tagItem = (ArrayList) request.getAttribute("tagList");

                                            pageContext.setAttribute("list", tagItem);
                                        %>

                                        <c:forEach	items='${list}' var='listTag' >
                                            <c:if test="${listTag.tagName != 'Concluidos'}">
                                                <li>&nbsp #${listTag.tagName}
                                                    <input type="checkbox" class="checkTags" value='${listTag.tagName}'>
                                                </li>
                                            </c:if>
                                        </c:forEach> 
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" class="close" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-primary" onclick="insertTagsOnInput()" class="close" data-dismiss="modal">OK</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <!-- Importação dos Scripts -->
        <script type="text/javascript" src="js/plugins/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="js/plugins/jquery/jquery-ui.min.js"></script>
        <script type="text/javascript" src="js/plugins/bootstrap/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/plugins.js"></script>
        <script type="text/javascript" src="js/actions.js"></script>
        <script type="text/javascript" src="js/script.js"></script>
        <script type="text/javascript" src="js/tagMenu.js"></script>
        <script type="text/javascript" src="js/tags.js"></script>
        <script type="text/javascript" src="js/configuracoes.js"></script>
        <script type="text/javascript" src="js/modalOptions.js"></script>
        <script type="text/javascript" src="js/filter.js"></script>
        <script type="text/javascript" src="js/theme.js"></script>
        <script type="text/javascript" src="js/settings.js"></script>

    </body>
</html>
