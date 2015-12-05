package be.sandervl.admin.controllers.rest;

import be.sandervl.admin.business.upload.pdf.Pdf;
import be.sandervl.admin.business.upload.pdf.Program;
import be.sandervl.admin.repositories.upload.pdf.ProgramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramController.class);

    @Autowired
    private ProgramRepository programRepository;

    @RequestMapping(value = "/program/latest", method = RequestMethod.GET)
    public Program getLatestProgram() {
        List<Program> programs =
                programRepository.findByDateBetweenOnlineDateAndOfflineDateAndActiveIsTrue();
        if (programs.size() > 0) {
            return programs.get(0);
        }
        return null;
    }
}
