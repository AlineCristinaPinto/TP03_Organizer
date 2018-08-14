package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
import br.cefetmg.inf.organizer.model.service.impl.KeepUser;
import br.cefetmg.inf.util.ErrorObject;
import br.cefetmg.inf.util.PasswordCriptography;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUser implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String pageJSP = "";

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        User user = (User) req.getSession().getAttribute("user");
        User tempUser = new User();
        
        if (name.isEmpty() || name == null) {
            name = user.getUserName();
        }
        
        if (password.isEmpty() || password == null) {
            password = user.getUserPassword();
        } else {
            password = PasswordCriptography.generateMd5(password);
        }

        tempUser.setCodEmail(user.getCodEmail());
        tempUser.setUserName(name);
        tempUser.setUserPassword(password);
        tempUser.setCurrentTheme(user.getCurrentTheme());

        IKeepUser keepUser = new KeepUser();
        boolean success = keepUser.updateUser(tempUser);
        if (!success) {
            ErrorObject error = new ErrorObject();
            error.setErrorName("Tente novamente");
            error.setErrorDescription("Erro na alteração do usuário");
            error.setErrorSubtext("Verifique se os campos estão preenchidos corretamente ou se você está logado");
            req.getSession().setAttribute("error", error);
            pageJSP = "/error.jsp";
        } else {
            req.getSession().setAttribute("user",tempUser);
            pageJSP = "/configuracoes.jsp";
        }
        
        return pageJSP;
    }

}
