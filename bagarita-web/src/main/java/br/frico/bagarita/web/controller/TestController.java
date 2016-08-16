package br.frico.bagarita.web.controller;

import br.frico.bagarita.domain.Discipline;
import br.frico.bagarita.domain.repos.DisciplineRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Felipe Rico on 8/15/2016.
 */
@RestController
public class TestController {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String test() {
        return "Test Worked!";
    }

    @RequestMapping(path = "/discipline", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String testDiscipline(@RequestParam("name") String name,
                                 @RequestParam(value = "description", required = false) String description) {

        if (StringUtils.isNotBlank(name)) {
            Discipline discipline = new Discipline(name, description);
            disciplineRepository.save(discipline);
            return discipline.getId().toString();
        }

        return "Fail!";
    }

}
