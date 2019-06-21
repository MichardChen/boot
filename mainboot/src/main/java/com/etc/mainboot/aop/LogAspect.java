package com.etc.mainboot.aop;

import com.etc.mainboot.annotation.OperateLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * aop切面处理
 * 对模块、方法、参数的日志处理
 * @Aspect注解切面,@PointCut定义切点,标记方法
 * @author ChenDang
 * @date 2019/5/28 0028
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 此处定义切点是注解方法,这里使用注解的方式。
     * 也可以使用aop最原始的支撑包名的方式
     */
    @Pointcut("@annotation(com.etc.mainboot.annotation.OperateLog)")
    public void operateLog(){}

    /**
     * 环绕增强，在这里进行日志操作
     */
    @Around("operateLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{

        Object res = null;
        long time = System.currentTimeMillis();
        try {
            //方法继续执行
            res =  joinPoint.proceed();
            time = System.currentTimeMillis() - time;
            return res;
        } finally {
            try {
                //方法执行完成后增加日志
                addOperationLog(joinPoint,res,time);
            }catch (Exception e){
                System.out.println("LogAspect 操作失败：" + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    private void addOperationLog(JoinPoint joinPoint, Object res, long time){
        //joinPoint.getSignature()获取封装了署名的对象，在该对象中可以获取目标方法名，
        //所属类的class等信息
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String method = signature.getDeclaringTypeName() + "." + signature.getName();
        System.out.println("拦截方法名："+method);
        //获取注解对象，取到注解里定义的字段
        OperateLog annotation = signature.getMethod().getAnnotation(OperateLog.class);
        if(annotation != null) {

            String title = annotation.title();
            String bizType = annotation.bizType().name();
            String operateType = annotation.operateType().name();
            boolean isSaveData = annotation.isSaveData();
            System.out.println("title:"+title+",bizType:"+bizType+",operateType:"+operateType+",isSaveData:"+isSaveData);
            //获取参数
            HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
            Map<String, String[]> map =request.getParameterMap();
            if (map == null || map.isEmpty()) {
                return;
            }
            //输出参数
            for(String key : map.keySet()){
                System.out.println("key:"+key+",value="+map.get(key));
            }
        }
        //TODO 这里保存日志
        System.out.println("记录日志:");
    }

    @Before("operateLog()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        System.out.println("进入方法前执行.....");

    }

    /**
     * 处理完请求，返回内容
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "operateLog()")
    public void doAfterReturning(Object ret) {
        System.out.println("方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("operateLog()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }


    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("operateLog()")
    public void after(JoinPoint jp){
        System.out.println("方法最后执行.....");
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OperateLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(OperateLog.class);
        }
        return null;
    }

}
