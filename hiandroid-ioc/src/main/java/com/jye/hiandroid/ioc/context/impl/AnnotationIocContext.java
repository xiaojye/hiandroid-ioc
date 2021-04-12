package com.jye.hiandroid.ioc.context.impl;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.jye.hiandroid.ioc.annotation.Autowired;
import com.jye.hiandroid.ioc.annotation.Component;
import com.jye.hiandroid.ioc.annotation.Resource;
import com.jye.hiandroid.ioc.context.IocContext;
import com.jye.hiandroid.ioc.factory.impl.DefaultBeanFactory;
import com.jye.hiandroid.ioc.scanner.DexFileScanner;
import com.jye.hiandroid.ioc.scanner.impl.DexFileScannerImpl;
import com.jye.hiandroid.util.HiReflectUtils;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author jye
 * @since 1.0
 */
public class AnnotationIocContext extends DefaultBeanFactory implements IocContext {

    private Application application;

    public AnnotationIocContext(Application application) {
        this.application = application;
        init();
    }

    @Override
    public Application getApplication() {
        return application;
    }

    @Override
    public Context getApplicationContext() {
        return application.getApplicationContext();
    }

    private void init() {
        String[] packages = new String[]{application.getPackageName()};
        DexFileScanner dexFileScanner = new DexFileScannerImpl();
        dexFileScanner.setContext(application);
        Set<String> strings = dexFileScanner.doScan(packages);
        for (String s : strings) {
            try {
                Class scanClass = Class.forName(s);
                Object src = this.getSrc(scanClass);
                this.setBeanData(scanClass, src);
            } catch (Exception e) {
                continue;
            }
        }

        this.setBeanData(application.getClass(), application);
    }

    private Object getSrc(Class clazz) {
        if (clazz.isAnnotationPresent(Component.class)) {
            Component component = (Component) clazz.getAnnotation(Component.class);
            if (component.value() == null || "".equals(component.value().trim())) {
                return this.getBean(lowerFirst(clazz.getSimpleName()), clazz);
            } else {
                return this.getBean(component.value(), clazz);
            }
        }

        return null;
    }

    @Override
    public Object getRBean(Integer rId, Class tClass) {
        String resId = "R_" + rId.toString();

        //在注册表中查找看是否有这个BeanFactory的实例
        Object o = this.getBean(resId);
        if (o != null) {
            return o;
        }

        Object resource = null;
        if (String.class.isAssignableFrom(tClass)) {
            resource = application.getString(rId);
        }
        if (Drawable.class.isAssignableFrom(tClass)) {
            resource = ContextCompat.getDrawable(application.getApplicationContext(), rId);
        }
        if (Integer.class.isAssignableFrom(tClass) || int.class == tClass) {
            resource = ContextCompat.getColor(application, rId);
        }
        if (CharSequence.class.isAssignableFrom(tClass) && resource == null) {
            resource = application.getText(rId);
        }

        try {
            //将Bean注册到注册表
            this.registerBean(resId, tClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resource;
    }


    @Override
    public void setBeanData(Class clazz, Object src) {
        if (src == null) {
            return;
        }
        Set<Field> fields = HiReflectUtils.getAllClassSet(clazz);
        for (Field field : fields) {
            Class t = field.getType();
            if (field.isAnnotationPresent(Autowired.class)) {
                Object data = this.getBean(t);
                HiReflectUtils.setData(field, src, data);
            }

            if (field.isAnnotationPresent(Resource.class)) {
                int[] ids = field.getAnnotation(Resource.class).value();
                Object o = this.getRBean(ids[0], t);
                HiReflectUtils.setData(field, src, o);
            }
        }
    }

}
