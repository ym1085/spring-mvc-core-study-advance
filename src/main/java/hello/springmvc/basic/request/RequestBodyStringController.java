package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * @since           :       2023-10-23
 * @author          :       youngmin
 * @version         :       1.0.0
 * @description     :       HTTP 메시지 - TEXT를 Body에 담아 Response로 읽는 방법
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-10-23       youngmin           최초 생성
 **/
@Slf4j
@Controller
public class RequestBodyStringController {

    /**
     * V1 : HTTP Message Body - HttpServletRequest, HttpServletResponse 사용
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody = {}", messageBody);
        
        response.getWriter().write("ok");
    }

    /**
     * V2 : HTTP Message Body - InputStream + Writer 객체를 사용
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer writer) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody = {}", messageBody);

        writer.write("ok");
    }

    /**
     * V3 : HTTP Message Body - HttpEntity를 사용하여 HTTP Body Request|Response
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody = {}", messageBody);

        return new HttpEntity<>("ok");
    }

    /**
     * V3 : HTTP Message Body - ResponseEntity 사용하여 HTTP Body Request|Response
     */
    @PostMapping("/request-body-string-other-v3")
    public ResponseEntity<String> requestBodyStringV3(RequestEntity<String> requestEntity) {
        String messageBody = requestEntity.getBody();
        log.info("messageBody = {}", messageBody);

        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    /**
     * V4 : HTTP Message Body - @RequestBody, @ResponseBody 사용
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody = {}", messageBody);
        return "ok";
    }
}
