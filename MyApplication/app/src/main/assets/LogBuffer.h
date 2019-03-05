class LogBuffer {
public:
    LogBuffer(char* ptr, size_t capacity);
    ~LogBuffer();

    void initData(const char *log_path);
    char* dataCopy();
    size_t dataSize();
    size_t append(const char* log);
    void clear();
    void release();
    size_t emptySize();
    char *getLogPath();
    bool async_flush(AsyncFileFlush *fileFlush);

public:
    bool map_buffer = true;

private:
    char* const buffer_ptr = nullptr;
    char* data_ptr = nullptr;
    char* write_ptr = nullptr;

    size_t buffer_size = 0;
    std::recursive_mutex log_mtx;

};


LogBuffer::LogBuffer(char *ptr, size_t buffer_size):
        buffer_ptr(ptr),
        buffer_size(buffer_size) {
    data_ptr = buffer_ptr + 1 + buffer_ptr[0];
    write_ptr = data_ptr + strlen(data_ptr);
}
LogBuffer::~LogBuffer() {
    release();
}
void LogBuffer::initData(const char *log_path) {
    std::lock_guard lck_release(log_mtx);
    memset(buffer_ptr, '\0', buffer_size);
    size_t log_path_len = strlen(log_path);
    buffer_ptr[0] = static_cast(log_path_len);
    memcpy(buffer_ptr + 1, log_path, log_path_len);
    data_ptr = buffer_ptr + 1 + log_path_len;
    write_ptr = data_ptr;
}
void LogBuffer::release() {
    std::lock_guard lck_release(log_mtx);
    if(map_buffer) {
        munmap(buffer_ptr, buffer_size);
    } else {
        delete[] buffer_ptr;
    }
}

size_t LogBuffer::dataSize() {
    return write_ptr - data_ptr;
}
size_t LogBuffer::emptySize() {
    return buffer_size - (write_ptr - buffer_ptr);
}
char *LogBuffer::dataCopy() {
    size_t str_len = dataSize() + 1;  //'\0'
    char* data = new char[str_len];
    memcpy(data, data_ptr, str_len);
    data[str_len - 1] = '\0';
    return data;
}
void LogBuffer::clear() {
    std::lock_guard lck_clear(log_mtx);
    write_ptr = data_ptr;
    memset(write_ptr, '\0', emptySize());
}
char *LogBuffer::getLogPath() {
    size_t path_len = static_cast(buffer_ptr[0]);
    char* file_path = nullptr;
    if(path_len > 0) {
        file_path = new char[path_len + 1];
        memcpy(file_path, buffer_ptr + 1, path_len);
        file_path[path_len] = '\0';
    }
    return file_path;
}


size_t LogBuffer::append(const char *log) {
    std::lock_guard lck_append(log_mtx);
    size_t len = strlen(log);
    size_t freeSize = emptySize();
    size_t writeSize = len <= freeSize ? len : freeSize;
    memcpy(write_ptr, log, writeSize);
    write_ptr += writeSize;
    return writeSize;
}



bool LogBuffer::async_flush(AsyncFileFlush *fileFlush) {
    std::lock_guard lck_clear(log_mtx);
    if (dataSize() > 0) {
        char *data = dataCopy();
        if(fileFlush->async_flush(data)) {
            clear();
            return true;
        } else {
            delete[] data;
            return false;
        }
    }
    return false;
}