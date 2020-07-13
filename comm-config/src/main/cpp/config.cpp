//
// Created by Apple on 2020/7/13.
//

#ifndef BEAUTYWEATHER_CONFIG_H
#define BEAUTYWEATHER_CONFIG_H

#include <android/log.h>
#include <jni.h>
#include <string>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__)

#define TAG "CommConfig"
#define ERROR_CODE -1
#define SUCCESS_CODE 0


static const char *classPath = "com/crazyorange/beauty/comm/config/SignConfig";

jstring signApiKey(JNIEnv *env) {
    return env->NewStringUTF("439d997ab533caa2ba2fb99c610b3f39c");
}


static JNINativeMethod jniMethodTables[] = {
        {"getApiKey", "()Ljava/lang/String;", (jstring) signApiKey}
};


static int
jniRegisterNativeMethods(JNIEnv *env, const char *className, const JNINativeMethod *gMethods,
                         int numMethods) {
    jclass clazz;
    clazz = env->FindClass(className);
    if (clazz == NULL) {
        return ERROR_CODE;
    }
    if (env->RegisterNatives(clazz, jniMethodTables, numMethods) < 0) {
        LOGE("register native method error");
        return ERROR_CODE;
    }
    env->DeleteLocalRef(clazz);
    return SUCCESS_CODE;
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGE("Comm config start register native method");
    JNIEnv *env = nullptr;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return ERROR_CODE;
    }
    jniRegisterNativeMethods(env, classPath, jniMethodTables,
                             sizeof(jniMethodTables) / sizeof(JNINativeMethod));
    return JNI_VERSION_1_4;
}

#endif //BEAUTYWEATHER_CONFIG_H
