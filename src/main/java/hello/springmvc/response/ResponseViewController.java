package hello.springmvc.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ResponseViewController {

    /**
     * model and view 사용
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        log.info("response-view-v1");
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }

    /**
     * model 사용
     */
    @ResponseBody
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        log.info("response-view-v2");
        model.addAttribute("data", "hello!");
        return "response/hello";
    }

    /**
     * 관례적으로 생략 -> 권장하지 않음
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}
