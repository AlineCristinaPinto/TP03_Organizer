<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="br.cefetmg.inf.organizer.model.domain.User"%>
<%@page import="br.cefetmg.inf.util.ErrorObject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean class="java.lang.String" id="arrItemTag" scope="session" ></jsp:useBean>
<jsp:useBean id='tagItem' class='java.util.ArrayList' scope="page"/>

<jsp:useBean id="error" class="br.cefetmg.inf.util.ErrorObject" scope="page"></jsp:useBean>
<%error = (ErrorObject) session.getAttribute("error");%>

<!DOCTYPE html>
<html>
    <head>
        <title>Organizer</title>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" type="text/css" href="css/theme-default.css"/>
        <link rel="stylesheet" href="css/styles.css">
    </head>
    <body>
        <div class="page-container">
            <!-- Menu lateral -->
            <div class="page-content">
                <!-- Menu Horizontal -->
                <div class="page-content-wrap">

                    <div class="row">
                        <div class="error-container">
                            <div class="error-code"> <%=error.getErrorName()%> </div> 
                            <div class="error-text"> <%=error.getErrorDescription()%></div>
                            <div class="error-subtext"><%=error.getErrorSubtext()%></div>
                            <div class="error-actions">                                
                                <div class="row">
                                    <div class="col-md-6">
                                        <button class="btn btn-info btn-block btn-lg" onClick="document.location.href = 'login.jsp';">Volte a página inicial</button>
                                    </div>
                                    <div class="col-md-6">
                                        <button class="btn btn-primary btn-block btn-lg" onClick="history.back();">Página anterior</button>
                                    </div>
                                </div>                                
                            </div>
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
        <script type="text/javascript" src="js/configuracoes.js"></script>
        <script type="text/javascript" src="js/tags.js"></script>

    </body>
</html>

