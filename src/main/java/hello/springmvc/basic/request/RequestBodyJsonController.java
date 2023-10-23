package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @since           :       2023-10-23
 * @author          :       youngmin
 * @version         :       1.0.0
 * @description     :       HTTP 메시지 - JSON 데이터를 Body에 담아 Response로 읽는 방법
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-10-23       youngmin           최초 생성
 **/
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * HttpServletRequest, HttpServletResponse를 사용하여 HTTP Request Body 내용 출력
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);
        HelloData helloData = mapper.readValue(messageBody, HelloData.class); // json to object -> 역직렬화
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    /**
     * @RequestBody를 사용하여 HTTP Request Body(JSON - String) 내용 출력
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody = {}", messageBody);
        HelloData helloData = mapper.readValue(messageBody, HelloData.class); // json to object -> 역직렬화
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * @RequestBody를 사용하여 HTTP Request Body(JSON - String) 내용 출력, 객체를 매개변수로 직접 입력
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * HttpEntity 사용하여 HTTP Request Body(JSON - String) 내용 출력
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username = {}, age = {}", data.getUsername(), data.getAge());
        return "ok";
    }

    /**
     * @RequestBody 사용하여 HTTP Request Body(JSON - String) 내용 출력
     * @RequestBody
     *  -> JSON -> HTTP Message Converter -> 객체 | 문자
     *
     * @ResponseBody
     *  -> 객체 | 문자 -> HTTP Message Converter -> JSON
     *
     * >>>> MappingJackson2HttpMessageConverter를 스프링이 실제로 사용한다
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username = {}, age = {}", data.getUsername(), data.getAge());
        return data;
    }

    @PostMapping("/request-body-test-v6")
    public @ResponseBody ResponseEntity<String> requestBodyTestV6(@RequestBody String data) throws JsonProcessingException {
        log.info("helloData = {}", data); // helloData -> @ToString 가지고 있음

        HelloData helloData = mapper.readValue(data, HelloData.class);
        log.info("helloData = {}", helloData);

        HelloData hd = new HelloData();
        String helloDataAsString = mapper.writeValueAsString(hd);
        log.info("helloDataAsString = {}", helloDataAsString);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
