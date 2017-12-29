/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.controller;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sec.project.domain.LoginCredential;

/**
 *
 * @author samuli
 */
@Controller
public class DefaultController {

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model, RedirectAttributes attributes) throws SQLException {
        if (session.getAttribute("user").equals("") || session.getAttribute("user") == null) {
            attributes.addFlashAttribute("message", "You must login first");
            return "redirect:/form";
        }
        
        List<LoginCredential> list = LoginCredential.getByUser(Integer.parseInt((String) session.getAttribute("user")));
        model.addAttribute("data", list);
        return "home";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam String url, @RequestParam String username, @RequestParam String password, @RequestParam String siteName, Model model) throws SQLException {
        LoginCredential lc = new LoginCredential(password, username, url, siteName);
        System.out.println(session.getAttribute("user"));
        lc.setUser_id(Integer.parseInt((String) session.getAttribute("user")));
        lc.save();
        return "redirect:/home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String search(@RequestParam String siteName, Model model) throws SQLException {
        List<LoginCredential> list = LoginCredential.getBySite(Integer.parseInt((String) session.getAttribute("user")), siteName);
        model.addAttribute("data", list);
        return "home";
    }
}
