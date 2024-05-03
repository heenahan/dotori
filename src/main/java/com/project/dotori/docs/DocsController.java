package com.project.dotori.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/docs")
@Controller
public class DocsController {

    @GetMapping
    public String getRestDocs() {
        return "/docs/index.html";
    }
}
