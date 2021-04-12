package com.jye.hiandroid.ioc;

import android.app.Application;

import com.jye.hiandroid.ioc.context.impl.AnnotationIocContext;
import com.jye.hiandroid.ioc.context.IocContext;

/**
 * @author jye
 * @since 1.0
 */
public class HiIoc {

    public static IocContext init(Application application) {
        return new AnnotationIocContext(application);
    }
}
