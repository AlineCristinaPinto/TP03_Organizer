package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepTag;
import br.cefetmg.inf.util.ErrorObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteTag implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String pageJSP = "";

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String nameTag = req.getParameter("tagMenu");

        IKeepTag keepTag = new KeepTag();

        Tag tag = new Tag();
        tag.setSeqTag(keepTag.searchTagByName(nameTag, user));
        tag.setTagName(nameTag);
        tag.setUser(user);

        boolean success = keepTag.deleteTag(tag);
        // ?? 
        if (!success) { 
            ErrorObject error = new ErrorObject();
            error.setErrorName("Tente novamente");
            error.setErrorDescription("Erro ao deletar a tag");
            //error.setErrorSubtext("Verifique se o usuário já existe ou se ocorreu um erro no preenchimento dos campos");
            req.getSession().setAttribute("error", error);
            pageJSP = "/error.jsp";
        } else {
            pageJSP = "/index.jsp";
        }

        return pageJSP;
    }
}
