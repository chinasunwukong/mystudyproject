
#include<studio.h>
#include<jni.h>

JNIEXPOET static jlong JNICALL initNative(JNIEnv *env,jclass type,jstring buffer_path_,
 jint capacity,jstring log_path_) {
     const char *buffer_path=env->GetStringUTFChars(buffer_path_,0);
     const char *log_path=env->GetStringUTFChars(log_path_,0);
     const size_t buffer_size = static_cast(capacity);

     //打开缓存文件
     int buffer_fd = open(buffer_path, O_RDWR|O_CREAT, S_IRUSR|S_IWUSR
        |S_IRGRP|S_IROTH);
     //打开日志文件
     int log_fd = open(log_path, O_RDWR|O_CREAT|O_APPEND, S_IRUSR|S_IWUSR
        |S_IRGRP|S_IROTH);

     //buffer的第一个字节会用于存储日志路径名称长度，后面紧跟日志路径，之后才是日志信息

     if(strlen(log_path) > CHAR_MAX / 2) {
        jclass je=env->FindClass("java/lang/IllegalArgumentException");
        std::ostringstream oss;
        oss<< "the length of log path must bu less than " << CHAR_MAX/2;
        env->ThrowNew(je,oss.str().c_str());
        return 0;
     }

     if(fileFlush == nullptr) {
        fileFlush = new AsyncFileFlush(log_fd);
     }

     char *buffer_ptr = openMMap(buffer_fd, buffer_size);
     bool map_buffer = true;
     //如果打开mmap失败，则降级使用内存缓存
     if(buffer_ptr == nullptr) {
        buffer_ptr = new  char[capacity];
        map_buffer = false;
     }

     env->ReleaseStringUTFChars(buffer_path_, buffer_path);
     env->ReleaseStringUTFChars(log_path_, log_path);

     LogBuffer* logBuffer = new LogBuffer(buffer_ptr, buffer_size);
     //将buffer内的数据清0， 并写入日志文件路径
     logBuffer->initData(log_path);
     logBuffer->map_buffer = map_buffer;
     return reinterpret_cast(logBuffer);
 }

 static char* openMMap(int buffer_fd, size_t buffer_size) {
    char* map_ptr = nullptr;
    if(buffer_fd != -1) {
       //写脏数据
       writeDirtyLogToFile(buffer_fd);
       //根据buffer size调整buffer文件大小
       ftruncate(buffer_fd, static_cast(buffer_size));
       lseek(buffer_fd, 0 ,SEEK_SET);
       map_ptr = (char *) mmap(0, buffer_size, PROT_WRITE|PROT_READ,
          MAP_SHARED, buffer_fd, 0);
       if(map_ptr == MAP_FAILED) {
          map_ptr = nullptr;
       }
    }
    return map_ptr;
 }

 static void writeDirtyLogToFile(int buffer_fd) {
     struct stat fileInfo;
     if(fstat(buffer_fd, &fileInfo) >= 0) {
         size_t buffered_size = static_cast(fileInfo.st_size);
         if(buffered_size > 0) {
             char *buffer_ptr_tmp = (char *) mmap(0, buffered_size, PROT_WRITE | PROT_READ, MAP_SHARED, buffer_fd, 0);
             if (buffer_ptr_tmp != MAP_FAILED) {
                 LogBuffer tmp(buffer_ptr_tmp, buffered_size);
                 size_t data_size = tmp.dataSize();
                 if (data_size > 0) {
                     char* log_path = tmp.getLogPath();
                     if (log_path != nullptr) {
                         int log_fd = open(log_path, O_RDWR|O_CREAT|O_APPEND, S_IRUSR|S_IWUSR|S_IRGRP|S_IROTH);
                         if(log_fd != -1) {
                             AsyncFileFlush tmpFlush(log_fd);
                             tmp.async_flush(&tmpFlush);
                         }
                         delete[] log_path;
                     }
                 }
             }
         }
     }
 }

 static void writeNative(JNIEnv *env, jobject instance, jlong ptr,
             jstring log_) {
     const char *log = env->GetStringUTFChars(log_, 0);
     LogBuffer* logBuffer = reinterpret_cast(ptr);
     size_t log_size = strlen(log);
     // 缓存写不下时异步刷新
     if (log_size >= logBuffer->emptySize()) {
         logBuffer->async_flush(fileFlush);
     }
     logBuffer->append(log);
     env->ReleaseStringUTFChars(log_, log);
 }