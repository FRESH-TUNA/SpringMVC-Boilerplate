package com.boilerplate.core;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 *  톰캣은 웹 프로젝트를 시작할 때, WebApplicationInitializer 구현 클래스를 찾아서 기본 설정을 하게 만든다.
 */
public class WebApplication implements WebApplicationInitializer {
    /**
     * @param servletContext: (tomcat의 context)
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        /**
         * 프로젝트의 bean등을 관리해주는 ApplicationContext 생성
         */
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        /**
         * tomcat servlet의 정보를 가져오기 위해
         * 사용하는것으로 추정
         * ex
         * @Override
         * public String getApplicationName() {
         *     return (this.servletContext != null ? this.servletContext.getContextPath() : "");
         * }
         */
        context.setServletContext(servletContext);

        /**
         * context에 com.boilerplate.core.WebConfiguration 로드 -> 컴포넌트 스캔 진행
         */
        context.register(WebConfiguration.class);
        context.refresh();

        /**
         * 스프링의 DispatcherServlet를 servletContext의 서블렛에 등록한다.
         * DispatcherServlet에는 아까만든 AnnotationConfigWebApplicationContext 를 주입한다.
         */
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic app = servletContext.addServlet("app", dispatcherServlet);
        app.addMapping("/app/*");
    }
}
