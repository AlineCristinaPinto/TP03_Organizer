<%@page import="java.util.ArrayList"%>
<%@page import="br.cefetmg.inf.organizer.model.domain.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id='listTag' class='java.util.ArrayList' scope="page"/>
<jsp:useBean class="br.cefetmg.inf.organizer.model.domain.User" id="userSessao" scope="page" ></jsp:useBean>
<%userSessao = (User) request.getSession().getAttribute("user");%>

<!DOCTYPE html>

<form id="indexPageForm" action="/organizer/servletcontroller?process=LoadItem" method="post">
</form>

<div class="page-sidebar">

    <ul class="x-navigation">

        <li class="xn-logo">
            <a id="indexPage">Organizer</a>
            <a href="#" class="x-navigation-control"></a>
        </li>

        <li class="xn-profile">
            <a href="#" class="profile-mini">
                <img src="imgs/icon.jpg" />
            </a>
            <div class="profile">
                <div class="profile-image">
                    <img src="imgs/icon.jpg"/>
                </div>
                <div class="profile-data">
                    <div class="profile-data-name">${user.userName}</div>
                    <div class="profile-data-title">${user.codEmail}</div>
                </div>
            </div>
        </li>

        <li>
            <a href="/organizer/servletcontroller?process=LoadMax"><span class="fa fa-comments"></span> <span class="xn-text">Falar com o MAX</span></a>
        </li>

        <li class="xn-openable">
            <a href="#"><span class="fa fa-adjust"></span> <span class="xn-text">Temas</span></a>
            <ul>
                <li><a href="#">
                        <label class="container">Padrão
                            <input type="radio" id="padButton" name="tema">
                            <span class="checkmark"></span>
                        </label>
                    </a></li>
                <li><a href="#">
                        <label class="container">Elegante
                            <input type="radio" id="modButton" name="tema">
                            <span class="checkmark"></span>
                        </label>
                    </a></li>
                <li><a href="#">
                        <label class="container">Extravagante
                            <input type="radio" id="extButton" name="tema">
                            <span class="checkmark"></span>
                        </label>
                    </a></li>
            </ul>
        </li>

        <li class="xn-openable">
            <a href="#"><span class="fa fa-file-text-o"></span> <span class="xn-text">Tipos</span></a>

            <%
                String[] listTypes = new String[3];

                listTypes[0] = "Simples";
                listTypes[1] = "Lembrete";
                listTypes[2] = "Tarefa";

                //pegando os tipos marcados no checkbox antes de recarregar a pagina
                String[] usedTypes = request.getParameterValues("tipo");

                pageContext.setAttribute("usedTypes", usedTypes);
                pageContext.setAttribute("listTypes", listTypes);

            %>

            <ul id="ulTypes">
                <c:forEach items='${listTypes}' var='list'>
                    <li><a href="#">                                        
                            <label class="container">${list}
                               <input type="checkbox" name="tipo" value="${fn:substring(fn:toUpperCase(list), 0, 3)}"
                                       <c:forEach items='${usedTypes}' var='usedType'>
                                           <c:if test='${fn:substring(fn:toUpperCase(list), 0, 3) == usedType}'>
                                               checked="true"
                                           </c:if>
                                       </c:forEach>
                                       >
                                <span class="checkmarkTarefa"></span>
                            </label>
                        </a></li>
                    </c:forEach>
            </ul>
        </li>

        <li class="xn-openable">
            <a href="#"><span class="fa fa-tag"></span> <span class="xn-text">Tags</span></a>
            <% 
                listTag = (ArrayList) request.getSession().getAttribute("tagList");
                String[] usedTags = request.getParameterValues("tag");

                pageContext.setAttribute("listTagUser", listTag);
                pageContext.setAttribute("usedTags", usedTags);
            %>
            <ul id="ulTagMenu">
                <li><a href="#" id="novaTag">
                        <span class="fa fa-plus-square-o"></span> <span class="xn-text">Nova Tag</span>
                    </a></li>
                    <c:forEach items='${listTagUser}' var='list'>  
                    <li class="tagLine"><a href="#">
                            <label class="container">${list.tagName}
                                <input type="checkbox" id="${list.tagName}" name="tag" value="${list.tagName}"
                                       <c:forEach items='${usedTags}' var='usedTag'>
                                           <c:if test='${list.tagName == usedTag}'>
                                               checked="true"
                                           </c:if>
                                       </c:forEach>
                                       >
                                <span class="checkmarkTarefa"></span>
                                <c:if test='${list.tagName != "Concluidos"}'>
                                    <button class="buttonStyle delete" id="${list.tagName}" name="deleteButton"><i class="glyphicon glyphicon-trash"></i></button>
                                    <button class="buttonStyle editer" id="${list.tagName}" name="editButton"><i class="glyphicon glyphicon-cog"></i></button>
                                </c:if>
                            </label>
                        </a></li>
                    </c:forEach>
                <form method="post" id="formDel">
                    <input type="hidden" id="takeTag" name="tagMenu">
                </form>
            </ul>
        </li>

        <li>
            <a href="configuracoes.jsp"><span class="fa fa-cogs"></span> <span class="xn-text">Configurações</span></a>
        </li>

        <li>
            <a href="#" id="logout"><span class="fa fa-sign-out"></span> <span class="xn-text">Sair</span></a>
        </li>

    </ul>

</div>

<div class="modal fade" id="editTagModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Editar Tag:</h4>
            </div>
            <form method="post" id="formEditTag">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Nome: </label>
                        <div class="input-group">
                            <span class="input-group-addon"><span class="fa fa-tag"></span></span>
                            <input type="hidden" id="takeOldName" name="takeOldName">
                            <input id="nameEdited" name="nameEdited" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" class="close" data-dismiss="modal" >Cancelar</button>
                        <button type="button" class="btn btn-primary" id="buttonSaveModal" class="close" data-dismiss="modal" >Salvar</button>
                    </div>
                </div>
            </form>    
        </div>
    </div>
</div>

<!-- Modal de Inserir Tags no menu-->
<div class="modal fade" id="tagsMenu" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Adicionar Tags:</h4>
            </div>
            <form method="post" id="formCreateTag">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Nome: </label>
                        <div class="input-group">
                            <span class="input-group-addon"><span class="fa fa-tag"></span></span>
                            <input id="name" name="name" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" class="close" data-dismiss="modal" >Cancelar</button>
                        <button type="button" class="btn btn-primary" onclick="addTagMenu()" class="close" data-dismiss="modal" >OK</button>
                    </div>
                </div>
            </form>    
        </div>
    </div>
</div>

<!-- Modal de logout-->
<div class="modal fade" id="logoutModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Logout:</h4>
            </div>
            <div class="modal-body">
                <form method="post" action="/organizer/servletcontroller?process=UserLogout">
                    <p>Até logo! Deseja sair da sua conta? </p>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" >Cancelar</button>
                        <button class="btn btn-primary">Sair</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>