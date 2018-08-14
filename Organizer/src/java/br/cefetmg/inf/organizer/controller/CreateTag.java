package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepTag;
import br.cefetmg.inf.util.ErrorObject;
import br.cefetmg.inf.util.exception.BusinessException;
import br.cefetmg.inf.util.exception.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateTag implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws PersistenceException, BusinessException {
        String pageJSP = "";

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String nameTag = req.getParameter("name");

        Tag tag = new Tag();
        tag.setTagName(nameTag);
        tag.setUser(user);
        tag.setSeqTag(null);

        IKeepTag keepTag = new KeepTag();
        boolean success = keepTag.createTag(tag);

        if (!success) {
            ErrorObject error = new ErrorObject();
            error.setErrorName("Tente novamente");
            error.setErrorDescription("Erro na criação da tag");
            error.setErrorSubtext("Verifique se a tag já existe ou se ocorreu um erro no preenchimento do campo");
            req.getSession().setAttribute("error", error);
            pageJSP = "/error.jsp";
        } else {
            pageJSP = "/index.jsp";
        }

        return pageJSP;
    }
}
