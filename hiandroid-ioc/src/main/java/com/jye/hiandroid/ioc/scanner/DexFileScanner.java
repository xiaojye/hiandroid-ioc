package com.jye.hiandroid.ioc.scanner;

import android.content.Context;

import java.util.Set;

/**
 * 安卓文件扫描器
 *
 * @author jye
 * @since 1.0
 */
public interface DexFileScanner {

    /**
     * 安卓上下文
     *
     * @param context 安卓上下文
     */
    void setContext(Context context);

    /**
     * 获得安卓上下文
     *
     * @return
     */
    Context getContext();

    /**
     * 扫描
     *
     * @return 类
     */
    Set<String> doScan(String[] packages);
}