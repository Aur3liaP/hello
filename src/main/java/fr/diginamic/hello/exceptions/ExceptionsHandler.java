package fr.diginamic.hello.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(ExceptionFonctionnelle.class)
    public ResponseEntity<String> traiterErreur(ExceptionFonctionnelle e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
