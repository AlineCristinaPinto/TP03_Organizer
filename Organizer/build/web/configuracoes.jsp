<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="br.cefetmg.inf.organizer.model.domain.User"%>

<%-- 
Querido Professor,
Tentei de todas as formas usar as Expressions Language para realizar atribuições e pegar valores de objetos, porém não funcionou de jeito algum mesmo 
eu seguindo vários tutoriais online, gostaria de saber quando elas funcionam e quando não, pois tento fazer a atribuição acima por EL e não aceita.
Tento fazer um value="${user.userName}" e também não funciona. Por isso, infelizmente, tive que recorrer a scriplets em algumas partes do código.
--%>
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
                <ul class="x-navigation x-navigation-horizontal x-navigation-panel">
                    <li class="xn-icon-button">
                        <a href="#" class="x-navigation-minimize"><span class="fa fa-dedent"></span></a>
                    </li>

                    <!-- Pesquisa -->
                    <li class="xn-search">
                        <form role="form">
                            <input type="text" name="pesquisar" placeholder="Perquisar..."/>
                        </form>
                    </li>
                </ul>

                <div class="page-content-wrap">

                    <div class="row">
                        <div class="col-md-12">
                            <p></p>
                            <!-- Form -->
                            <form class="form-horizontal" id="formSettings" method="post">

                                <div class="panel panel-default">

                                    <div class="panel-body">

                                        <h1 style="text-align:center">Configurações</h1>

                                        <img id="profileIcon" name="profileIcon" class="perfil" src="imgs/icon.jpg"/>
                                        <p></p>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Nome: </label>
                                            <div class="col-md-6 col-xs-12">
                                                <div class="input-group">
                                                    <span class="input-group-addon"><span class="fa fa-pencil"></span></span>
                                                    <input id="userName" name="name" type="text" class="form-control" value="<%=userSessao.getUserName()%>"/>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Senha</label>
                                            <div class="col-md-6 col-xs-12">
                                                <div class="input-group">
                                                    <span class="input-group-addon"><span class="fa fa-lock"></span></span>
                                                    <input id="password" name="password" type="password" class="form-control" data-toggle="modal" data-target="#sennhaModal"/>
                                                </div>
                                            </div>
                                        </div>
                                        <input id="oldPassword" type="hidden" value="<%=userSessao.getUserPassword()%>"/>
                                        <!--<input id="oldPassword" type="hidden" value="${userSessao.userPassword}"/>-->
                                        <button type="button" class="btn btn-secondary" >Cancelar</button>
                                        <button type="button" class="btn btn-primary pull-right" onclick="validateSettingFields()" >Salvar</button>
                                    </div>
                                </div>
                            </form>
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <span class="fa fa-trash-o"></span><span id="excluiConta"> Excluir conta</span>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <!-- Modal de trocar senha-->
        <div class="modal fade" id="sennhaModal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Alterar Senha:</h4>
                    </div>
                    <div class="modal-body">
                        <form class="" action="" method="post">
                            <div class="form-group">
                                <label>Senha atual:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><span class="fa fa-lock"></span></span>
                                    <input id="currentPassword" type="password" class="form-control"/>
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <label>Nova senha:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><span class="fa fa-lock"></span></span>
                                    <input id="newPassword" type="password" class="form-control"/>
                                </div>
                            </div>
                            <hr>
                            <div class="form-group">
                                <label>Confirmar nova senha:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><span class="fa fa-lock"></span></span>
                                    <input id="confirmNewPassword" type="password" class="form-control"/>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" class="close" data-dismiss="modal">Cancelar</button>
                                <button type="button" class="btn btn-primary" class="close" data-dismiss="modal" onclick="validateFieldsChangePassword()">OK</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>

        <!-- Modal de exclusÃ£o de conta-->
        <div class="modal fade" id="excluirModal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Excluir conta:</h4>
                    </div>
                    <div class="modal-body">
                        <p>Você tem certeza que deseja excluir sua conta? </p>
                        <form  id="formDelete" method="post">
                            <div class="form-group">
                                <label>Senha :</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><span class="fa fa-lock"></span></span>
                                    <input id="deleteAccountPassword" name="password" type="password" class="form-control"/>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <a href="index.jsp"><button type="button" class="btn btn-secondary" >Cancelar</button></a>
                                <button type="button" class="btn btn-primary" onclick="validateFieldsDeleteAccount()">Excluir</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <<!-- Importação dos Scripts -->
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
        <script type="text/javascript" src="js/CryptoJSMD5/core-min.js"></script>
        <script type="text/javascript" src="js/CryptoJSMD5/md5.js"></script>
    </body>
</html>
