package sec.project.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sec.project.domain.User;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private HttpSession session;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String validateLogin(@RequestParam String username, @RequestParam String password, Model model) throws SQLException, NoSuchAlgorithmException {
        HashMap<String, String> map = (HashMap) User.validateLogin(username, password);
        if (!(map.get("user") == null) && !map.get("user").isEmpty()) {
            session.setAttribute("user", map.get("user"));
            return "redirect:/home";
        } else {
            model.addAttribute("message", map.get("message"));
            return "form";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout() {
        session.setAttribute("user", null);
        return "redirect:/form";
    }

    @RequestMapping(value = "/change", method = RequestMethod.GET)
    public String change() {
        return "change";
    }
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public String updateCredentials(@RequestParam String username, @RequestParam String password) throws SQLException, NoSuchAlgorithmException {
        User.update(username, password, Integer.parseInt((String)session.getAttribute("user")));
        return "redirect:/home";
    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "createUser";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String submitForm(@RequestParam String username, @RequestParam String password, RedirectAttributes attributes) throws SQLException, NoSuchAlgorithmException {
        attributes.addFlashAttribute("message", "Account created!");
        User u = new User(username, password);
        u.saveUser();
        return "redirect:/form";
    }
}
