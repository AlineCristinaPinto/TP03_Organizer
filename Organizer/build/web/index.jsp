<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id='listItem' class='java.util.ArrayList' scope="page"/>

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
                
                <form id="createItemPageForm" action="/organizer/servletcontroller?process=LoadTagsToCreateItem" method="post"></form>
                
                <div class="page-title"></div>

                <div class="page-content-wrap">

                    <div class="row">
                        <div class="col-md-12">

                            <div class="panel panel-default">

                                <div class="panel-body accordion-menu">

                                    <ul id="ulItens">
                                        <li>
                                            <a id="indexCreateItem">Criar Item</a>
                                        </li>
                                        <%
                                            if (request.getAttribute("itemList") == null) {
                                            //    deve ter algum erro
                                            } else {
                                                listItem = (ArrayList) request.getAttribute("itemList");
                                            }

                                            pageContext.setAttribute("listItemUser", listItem);
                                        %>

                                        <c:forEach items='${listItemUser}' var='list'>
                                            <c:choose>
                                                <c:when test = "${list.identifierItem == 'TAR'}">
                                                    <c:choose>
                                                        <c:when test = "${list.identifierStatus == 'A'}">
                                                            <li id="${list.identifierItem}" class="open">
                                                                <label class="container" style="float:left">
                                                                    <input id="${list.seqItem}" class="checkTar" type="checkbox" name="tarefa" value="${list.nameItem}">
                                                                    <span class="checkmark"></span>
                                                                </label>
                                                                <button id="${list.seqItem}" class="opcoesItem btOption" value="${list.identifierItem}" data-toggle="modal" data-target="#btaoOpcaoModal"><i class="fa fa-ellipsis-v"></i></button>
                                                                <div class="dropdownlink">${list.nameItem}</div>
                                                                <ul class="submenuItems" style="display: none;">
                                                                    <c:if test = "${list.descriptionItem != ''}">
                                                                        <li id="subItem" class="liDescricao">${list.descriptionItem}</li>
                                                                        </c:if>                                                                
                                                                    <!-- tag <li class="liDescricao"></li>-->
                                                                    <c:if test = "${list.dateItem != null}">
                                                                        <li class="liDescricao" style="text-align: right">${list.dateItem}</li>
                                                                        </c:if>                                                                
                                                                </ul>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li id="${list.identifierItem}" class="open">
                                                                <label class="container" style="float:left">
                                                                    <input id="${list.seqItem}" class="checkTarC" checked="true" type="checkbox" name="tarefa" value="${list.nameItem}">
                                                                    <span class="checkmark"></span>
                                                                </label>
                                                                <button id="${list.seqItem}" class="opcoesItem btOption" value="${list.identifierItem}" data-toggle="modal" data-target="#btaoOpcaoModalTarefa"><i class="fa fa-ellipsis-v"></i></button>
                                                                <div class="dropdownlink">${list.nameItem}</div>
                                                                <ul class="submenuItems" style="display: none;">
                                                                    <c:if test = "${list.descriptionItem != ''}">
                                                                        <li id="subItem" class="liDescricao">${list.descriptionItem}</li>
                                                                        </c:if>                                                                
                                                                    <c:if test = "${list.dateItem != null}">
                                                                        <li class="liDescricao" style="text-align: right">${list.dateItem}</li>
                                                                        </c:if>                                                                
                                                                </ul>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <li id="${list.identifierItem}" class="open">
                                                        <button id="${list.seqItem}" value="${list.identifierItem}" class="opcoesItem btOption" data-toggle="modal" data-target="#btaoOpcaoModal"><i class="fa fa-ellipsis-v"></i></button>
                                                        <div class="dropdownlink">${list.nameItem}</div>
                                                        <ul class="submenuItems" style="display: none;">
                                                            <c:if test = "${list.descriptionItem != ''}">
                                                                <li id="subItem" class="liDescricao">${list.descriptionItem}</li>
                                                                </c:if>                                                                
                                                            <c:if test = "${list.dateItem != null}">
                                                                <li class="liDescricao" style="text-align: right">${list.dateItem}</li>
                                                                </c:if> 
                                                        </ul>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach> 
                                    </ul>

                                </div>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>


       

        <!-- Modal de botão de opção de item -->
        <div class="modal fade" id="btaoOpcaoModal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Opções de Item</h4>
                    </div>
                    <div class="modal-body">
                        <form id="updateItem" method="post">
                            <input type="hidden" id="takeIdU" name="takeIdU">
                            <input type="hidden" id="takeTypeU" name="takeTypeU">
                            <a class="opItemModal edit">
                                <span class="fa fa-edit"></span> Editar
                            </a>
                        </form>
                        <hr>
                        <form id="deleteItem" method="post">
                            <input type="hidden" id="takeId" name="takeId">
                            <a href="#" class="opItemModal delItem">                        
                                <span class="fa fa-trash-o"></span> Excluir
                            </a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Modal de botão de opção de tarefa -->
        <div class="modal fade" id="btaoOpcaoModalTarefa" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Opções de Tarefa Concluída</h4>
                    </div>
                    <div class="modal-body">
                        <form id="deleteItem" method="post">
                            <input type="hidden" id="takeId" name="takeId">
                            <a href="#" class="opItemModal delItem">                        
                                <span class="fa fa-trash-o"></span> Excluir
                            </a>
                        </form>
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

    </body>
</html>