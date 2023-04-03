/*
package br.edu.infnet.TechStore.controller;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class CustomErrorController implements ErrorController {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {

        if(ex.getMessage() != ""){
            model.addAttribute("exception", ex.getMessage());
        }
        return "error";
    }

    @RequestMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("exception", "Pagina nao encontrada");
        return "error";
    }
}
*/
