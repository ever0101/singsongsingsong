#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/ioctl.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<fcntl.h>
#include<string.h>
#include<linux/input.h>
#include <errno.h>
#include <jni.h>


#define EVENT_BUF_NUM 64

static int fd;

JNIEXPORT jint JNICALL Java_JNI_Keypad_Open( JNIEnv* env, jobject thiz )
{
	int ret;

	fd = open("/dev/input/event2",O_RDONLY);

	if(fd <= 0) return -errno;

	return fd;
}

JNIEXPORT jint JNICALL Java_JNI_Keypad_Close(JNIEnv* env, jobject thiz )
{
	if(fd > 0) {
		close(fd);
	}

	return 0;
}

JNIEXPORT jint JNICALL Java_JNI_Keypad_GetValue( JNIEnv* env,jobject thiz )
{
	int i;
	size_t read_bytes;
	struct input_event event_buf[64];

	read_bytes = read(fd, event_buf, (sizeof(struct input_event) *EVENT_BUF_NUM));
		if(read_bytes < sizeof(struct input_event))
		{
			return -1;
		}
		for ( i=0; i<(read_bytes/sizeof(struct input_event)); i++)
		{
			return event_buf[i].code;
		} 
}
