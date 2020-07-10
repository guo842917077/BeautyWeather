package com.crazyorange.beauty.comm.network;


import com.crazyorange.beauty.comm.network.call.Call;
import com.crazyorange.beauty.comm.network.call.RealCall;
import com.crazyorange.beauty.comm.network.executor.DefaultExecutor;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.SkipCallbackExecutor;

public class CallAdapterFactory extends CallAdapter.Factory {
    private static final String RETURN_TYPE = Call.class.getSimpleName();

    public CallAdapterFactory() {

    }

    /**
     * 使用 CallAdapter 将 Okhttp 的 call 对象做一层包装，转变成 LifeCycleCall
     *
     * @param returnType
     * @param annotations
     * @param retrofit
     * @return
     */
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Call.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalArgumentException(
                    String.format("%s return type must be parameterized as %s<Foo> or %s<? extends Foo>", RETURN_TYPE, RETURN_TYPE, RETURN_TYPE));
        }
        final Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
        //支持SkipCallbackExecutor
        final Executor executor = isAnnotationPresent(annotations, SkipCallbackExecutor.class)
                ? null
                : retrofit.callbackExecutor();
        return new CallAdapter<Object, Call<?>>() {
            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public Call<Object> adapt(retrofit2.Call<Object> call) {
                /**
                 * 1.如果 Retrofit 设定了 executor, 使用 Retrofit 的 Executor
                 * 2.如果没有设定,使用默认的 DefaultExecutor,不做任何线程处理
                 */
                if (executor != null) {
                    return new RealCall<>(executor, call);
                }
                return new RealCall<>(DefaultExecutor.get(), call);
            }
        };
    }

    public static Class<?> getRawType(Type type) {
        return CallAdapter.Factory.getRawType(type);
    }

    private boolean isAnnotationPresent(Annotation[] annotations,
                                        Class<? extends Annotation> cls) {
        for (Annotation annotation : annotations) {
            if (cls.isInstance(annotation)) {
                return true;
            }
        }
        return false;
    }
}
