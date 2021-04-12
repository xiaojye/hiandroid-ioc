package com.jye.hiandroid.ioc.scanner.impl;

import android.content.Context;

import com.jye.hiandroid.ioc.scanner.DexFileScanner;
import com.jye.hiandroid.util.HiContextUtils;

import java.io.IOException;
import java.util.Set;

/**
 * Dex文件扫描类
 *
 * @author jye
 * @since 1.0
 */
public class DexFileScannerImpl implements DexFileScanner {

    private Context context;

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Set<String> doScan(String[] packages) {
        try {
            return HiContextUtils.getDexFileClasses(context, packages);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
