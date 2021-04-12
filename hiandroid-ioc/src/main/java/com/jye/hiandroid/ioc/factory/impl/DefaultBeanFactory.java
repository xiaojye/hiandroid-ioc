package com.jye.hiandroid.ioc.factory.impl;

import android.util.Log;

import com.jye.hiandroid.ioc.factory.BeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jye
 * @since 1.0
 */
public class DefaultBeanFactory implements BeanFactory {

    private Map<String, Object> singletonMap = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String beanName) {
        return singletonMap.get(beanName);
    }

    @Override
    public <T> T getBean(Class<T> tClass) {
        for (Object key : singletonMap.keySet()) {
            Object value = singletonMap.get(key);
            if (value == null) {
                continue;
            }
            if (tClass.isAssignableFrom(value.getClass())) {
                return (T) value;
            }
        }

        return null;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> tClass) {
        Object obj = singletonMap.get(beanName);
        if (obj != null) {
            return (T) obj;
        }

        try {
            obj = tClass.newInstance();
            registerBean(beanName, obj);
            return (T) obj;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 首字母小写方法
     */
    protected String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }

    @Override
    public String getBeanName(Object src) {
        for (Map.Entry<String, Object> entry : singletonMap.entrySet()) {
            if (entry.getValue() == src) {
                return entry.getKey();
            }
        }

        return lowerFirst(src.getClass().getSimpleName());
    }

    @Override
    public void registerBean(String beanName, Object object) {
        if (beanName == null || beanName.isEmpty()) {
            registerBean(lowerFirst(object.getClass().getSimpleName()), object);
            return;
        }

        singletonMap.put(beanName, object);
        Log.i(TAG, "register bean: " + beanName + " ==> " + object.getClass());
    }

    @Override
    public void removeBean(String beanName) {
        singletonMap.remove(beanName);
        Log.i(TAG, "remove bean: " + beanName);
    }
}
