package top.shenluw.sldp;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.shenluw.sldp.annotation.SlSecurity;
import top.shenluw.sldp.annotation.Sldp;

import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 12:08
 */
@RestController
@SpringBootApplication
@ImportAutoConfiguration(SldpAutoConfiguration.class)
public class TestController {
    private final static Logger log = getLogger(TestController.class);

    public static class M {
        public Class clazz;
        public Object obj;

        public M(Object obj) {
            this.clazz = obj.getClass();
            this.obj = obj;
        }
    }

    @RequestMapping("/1")
    public M test1(Animal a) {
        log.info("test1: {}", a);
        return new M(a);
    }

    @RequestMapping("/2")
    public List<M> test2(@Sldp(name = "a") Animal a, @Sldp(name = "b") Animal b) {
        log.info("test2: {} {}", a, b);
        return Arrays.asList(new M(a), new M(b));
    }

    @RequestMapping("/3")
    public M test3(@Validated Animal a) {
        log.info("test3: {}", a);
        return new M(a);
    }

    @RequestMapping("/4")
    public M test4(Fly a) {
        log.info("test4: {}", a);
        return new M(a);
    }

    @RequestMapping("/5")
    public M test5(@Sldp(type = ModelType.Json) Animal a) {
        log.info("test5: {}", a);
        return new M(a);
    }

    @RequestMapping("/6")
    public M test6(@Sldp(type = ModelType.Json) @Validated Animal a) {
        log.info("test6: {}", a);
        return new M(a);
    }

    @RequestMapping("/7")
    public M test7(@Sldp(type = ModelType.Json) @SlSecurity Animal a) {
        log.info("test7: {}", a);
        return new M(a);
    }

}
