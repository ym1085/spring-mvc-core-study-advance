package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age = {}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody // @RestController와 같은 효과, Body에 HTTP Message를 반환
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ) {

        log.info("memberName = {}, memberAge = {}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ) {

        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {

        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) int age
    ) {

        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age
    ) {

        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object>paramMap) {

        log.info("username = {}, age = {}", paramMap.getOrDefault("username", ""), paramMap.getOrDefault("age", 0));
        return "ok";
    }

    /**
     * HTTP 파라미터 // Form(폼) Request Body -> multipart/form-data 형식 -> application/json 전송 불가
     * @ModelAttribute
     *      -> 클라이언트의 폼(form) HTTP 본문 | 파라미터 바인딩을 위해 사용
     *      -> 기본적으로 생성자를 통해 필드 값 셋팅, 나머지는 필드의 setter를 통해 셋팅
     *      -> 생성자 | setter 없으면 값 셋팅이 안됨
     *      -> 바인딩 타입이 맞지 않으면 자동 검증 -> BindingException 던질 듯
     *
     * @RequestBody
     *      -> 클라이언트의 application/json 요청을 바인딩이 아닌 변환하기 위해 사용
     *      -> ObjectMapper를 통해 JSON -> Java 객체로 변환하는 과정을 거침
     *      -> MappingJackson2HttpMessageConverter의 지원을 통해 변환 과정을 거침
     *      -> 기본 생성자를 통해 값을 셋팅하기 때문에, 생성자, setter 없이도 값 셋팅 가능
     *      -> 내부적으로 Reflaction을 사용
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);
        return "ok";
    }
}