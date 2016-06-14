LOCAL_PATH:=$(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE :=keytest
LOCAL_SRC_FILES :=keytest.c

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE :=textlcd
LOCAL_SRC_FILES :=textlcd.c

include $(BUILD_SHARED_LIBRARY)