package com.jye.hiandroid.ioc.factory;

/**
 * @author jye
 * @since 1.0
 */
public interface BeanFactory {

    String TAG = "BeanFactory";

    Object getBean(String beanName);

    <T> T getBean(Class<T> tClass);

    <T> T getBean(String beanName, Class<T> tClass);

    String getBeanName(Object src);

    void registerBean(String beanName, Object object);

    void removeBean(String beanName);

}