package com.nhnacademy.day03frontcontroller.annotation;

import com.nhnacademy.day03frontcontroller.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class StopWatchControllerProxy implements Command {
    /**
     * 의존성 주입 방식
     */
    // 1.어노테이션으로 필드 주입 방식
    //@Autowired
    private Command command;

    // 2.컨트롤러를 생성자 주입 방식
    public StopWatchControllerProxy(final Command command) {
        this.command = command;
    }
    
    // 3.컨트롤러를 set 메서드 주입 방식
    //public void setCommand(Command command) {
    //   this.command = command;
    //}

    /**
     * 프록시 패턴을 사용하여 비즈니스 로직이 있는 Command 객체를 감싸고,
     * 비즈니스 로직에 연관없는 코드를 프록시 클래스에 넣어 관심사 분리를 통해
     * 비즈니스 로직과 부가적인 기능(실행 시간 측정 및 로깅)을 분리한 것
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // StopWatch 클래스
        StopWatch stopWatch = command.getClass().getDeclaredAnnotation(StopWatch.class);

        // StopWatch 어노테이션이 없는 컨트롤러인 경우 처리
        if (Objects.isNull(stopWatch)) {
            return command.execute(req, resp);
        }

        // StopWatch 어노테이션이 있는 컨트롤러인 경우
        long start = System.currentTimeMillis();

        String view = command.execute(req, resp);

        long end = System.currentTimeMillis();
        long result = end - start;
        log.info("result : {}", result);

        return view;
    }
}
