class AsyncFileFlush {
public:
    AsyncFileFlush(int log_fd);
    ~AsyncFileFlush();
    bool async_flush(char *data);
    void stopFlush();
private:
    void async_log_thread();
    ssize_t flush(char *data);
    bool exit = false;
    int log_fd;
    std::vector async_buffer;
    std::thread async_thread;
    std::condition_variable async_condition;
    std::mutex async_mtx;
};

AsyncFileFlush::AsyncFileFlush(int log_fd):log_fd(log_fd) {
    async_thread = std::thread(&AsyncFileFlush::async_log_thread, this);
}

AsyncFileFlush::~AsyncFileFlush() {
    stopFlush();
}

void AsyncFileFlush::async_log_thread() {
    while (true) {
        std::unique_lock lck_async_log_thread(async_mtx);
        while (!async_buffer.empty()) {
            char* data = async_buffer.back();
            flush(data);
            async_buffer.pop_back();
            delete[] data;
        }
        if (exit) {
            return;
        }
        async_condition.wait(lck_async_log_thread);
    }
}

bool AsyncFileFlush::async_flush(char *data) {
    std::unique_lock lck_async_flush(async_mtx);
    if (exit) {
        return false;
    }
    async_buffer.push_back(data);
    async_condition.notify_all();
    return true;
}