package com.jye.hiandroid.ioc.context;

import android.app.Application;
import android.content.Context;

import com.jye.hiandroid.ioc.factory.BeanFactory;

/**
 * @author jye
 * @since 1.0
 */
public interface IocContext extends BeanFactory {

    Application getApplication();

    Context getApplicationContext();

    Object getRBean(Integer rId, Class tClass);

    void setBeanData(Class clazz, Object src);
}
