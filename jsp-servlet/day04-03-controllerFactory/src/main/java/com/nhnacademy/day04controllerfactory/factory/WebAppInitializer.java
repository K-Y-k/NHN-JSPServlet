package com.nhnacademy.day04controllerfactory.factory;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import static com.nhnacademy.day04controllerfactory.factory.ControllerFactory.CONTROLLER_FACTORY_CLASS;


/**
 * 서버가 실행될 때 초기에 WebAppInitializer 클래스가 실행되도록 하려면
 * 
 * resources/META-INF/services/jakarta.servlet.ServletContainerInitializer 파일을 생성하고
 * 이 파일에 WebAppInitializer 클래스 경로를 넣어줘야 한다.
 */
@Slf4j
// @HandlesTypes은 애플리케이션 초기화 시 특정 타입을 처리하기 위한 어노테이션
// 이 어노테이션은 Servlet 컨테이너에서 애플리케이션이 시작될 때 특정 클래스를 자동으로 스캔하도록 도와주는 역할을 한다.
@HandlesTypes(
        // value에 Command.class를 구현한 모든 클래스를 서블릿 컨테이너가 자동으로 찾아서 onStartup 메서드에 전달
        value = {
                com.nhnacademy.day04controllerfactory.controller.Command.class
        }
)
public class WebAppInitializer implements ServletContainerInitializer {
    /**
     * Application의 시작 시점에 Command interface를 구현한 Controller class를 onStartup Set<Class<?>> 타입으로 주입된다.
     * ControllerFactory에서는 Controller class 객체를 생성하고 Map<String,Object> 저장한다.
     */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        // 매개변수 Set<Class<?>> c에는 Command.class를 구현한 모든 클래스가 있다.

        ControllerFactory controllerFactory = new ControllerFactory();

        // ControllerFactory에서 Controller class 객체를 생성하고 Map<String,Object> 저장한다.
        controllerFactory.init(c);

        // CONTROLLER_FACTORY_CLASS 상수 변수를 활용해야 오타 문제를 해결할 수 있다.
        servletContext.setAttribute(CONTROLLER_FACTORY_CLASS, controllerFactory);
    }
}
