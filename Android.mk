LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_PACKAGE_NAME := BrokenSwitcher

LOCAL_SRC_FILES := $(call all-java-files-under, app/src/main)


LOCAL_STATIC_JAVA_LIBRARIES := \
    android-support-v7-appcompat \
    android-support-design

include $(BUILD_MULTI_PREBUILT)
