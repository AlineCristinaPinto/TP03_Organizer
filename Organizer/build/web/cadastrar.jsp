<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>

<html class="body-full-height">
    <head>
        <title>Organizer</title>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" type="text/css" href="css/theme-default.css"/>
        <link rel="stylesheet" href="css/styles.css">
        <script type="text/javascript" language="JavaScript" src="js/register.js"></script>
    </head>
    <body>

        <div class="login-container">

            <div class="login-box animated fadeInDown">
                <div class="login-logo"></div>
                <div class="login-body">
                    <div class="login-title"><strong>Junte-se ao Organizer!</strong></div>
                    <form class="form-horizontal" method="post" id="formRegister">
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="text" class="form-control" placeholder="Email" name="email" id="email"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="text" class="form-control" placeholder="Nome" name="name" id="name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="password" class="form-control" placeholder="Senha" name="password" id="password"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="password" class="form-control" placeholder="Confirmar senha" id="confirm-password"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-6">
                            <button  type="button" class="btn btn-info btn-block" onclick="validateFields()">Cadastrar</button>
                        </div>
                    </div>
                    </form>
                    <div class="login-subtitle">
                        Já possui uma conta? <a href="login.jsp">Logar</a>
                    </div>
                </div>
            </div>

        </div>

    </body>
</html>
