package com.nhnacademy.day04controllerfactory.factory;

import com.nhnacademy.day04controllerfactory.annotation.StopWatchControllerProxy;
import com.nhnacademy.day04controllerfactory.controller.Command;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class ControllerFactory  {
    public static final String CONTROLLER_FACTORY_CLASS = "controllerFactory";

    private final ConcurrentMap<String, Object> beanMap = new ConcurrentHashMap<>();

    // Controller class 객체를 생성하고 Map<String,Object> 저장한다.
    public void init(Set<Class<?>> c) {
        // todo beanMap에 key = method + servletPath, value = Controller instance
        for (Class<?> clazz : c) {
            log.info("init class: {}", clazz.getName());

            // 각 컨트롤러의 RequestMapping 어노테이션 클래스를 가져와서
            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);

            // RequestMapping 어노테이션이 붙지 않는 클래스는 다음으로 넘어간다.
            if (Objects.isNull(requestMapping)) {
                continue;
            }
            
            // RequestMapping 어노테이션이 있는 클래스인 경우
            // 메소드명과 경로 값을 꺼내서
            String method = requestMapping.method().name();
            String servletPath = requestMapping.value();

            // 키를 "메소드_경로"로 가공
            String key = keyGenerator(method, servletPath);
            log.info("init method key: {}", key);

            try {
                // 현재 컨트롤러 클래스의 객체 생성
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                Command controller = (Command) constructor.newInstance();
                
                // 관심사 분리를 적용한 StopWatchControllerProxy를 적용
                /**
                 * 어노테이션과 프록시 패턴 적용 방식 = AOP 프로그래밍
                 * 1.관심사를 분리함
                 * 2.중복코드 해결
                 */
                Command stopWatchControllerProxy = new StopWatchControllerProxy(controller);

                // 키와 컨트롤러의 참조값 넣기
                beanMap.put(key, stopWatchControllerProxy);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String keyGenerator(String method, String servletPath) {
        return "%s-%s".formatted(method, servletPath);
    }

    public Object getBean(String method, String path){
        // todo beanMap 에서 method+servletPath을 key로 이용하여 Controller instance를 반환합니다.
        log.info("getBean method method: {}, path: {}", method, path);

        // http 메소드와 url 경로를 이어붙여 키로 가공
        String key = keyGenerator(method, path);

        // 해당 키의 객체를 꺼내옴
        Object findObject = beanMap.get(key);
        if (Objects.isNull(findObject)) {
            throw new ControllerNotFoundException(method, path);
        }

        // 키를 통해 값 꺼내옴
        return findObject;
    }
}