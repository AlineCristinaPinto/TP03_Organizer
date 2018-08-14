package br.cefetmg.inf.organizer.controller;


import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
import br.cefetmg.inf.organizer.model.service.impl.KeepUser;
import br.cefetmg.inf.util.ErrorObject;
import br.cefetmg.inf.util.PasswordCriptography;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UserLogin implements GenericProcess{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String pageJSP = "";
        
        String email = req.getParameter("email");
        String password = PasswordCriptography.generateMd5(req.getParameter("password"));
        
        IKeepUser keepUser = new KeepUser();
        User user = keepUser.getUserLogin(email, password);
        
        if(user == null){
            ErrorObject error = new ErrorObject();
            error.setErrorName("Tente novamente");
            error.setErrorDescription("Erro no login");
            error.setErrorSubtext("Verifique se você já está cadastrado, senão crie uma conta");
            req.getSession().setAttribute("error", error);
            pageJSP = "/errorLogin.jsp";
        }else{
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            pageJSP = "/index.jsp";
        }
        
        return pageJSP;
    }
    
}
