package com.baidu.wdyy.http;


import com.baidu.wdyy.core.ApiException;

/**
 * DataCall接口
 */
public interface DataCall<T> {

    void success(T data);

    void fail(ApiException e);

}
