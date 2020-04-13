package com.vwmin.coolq.function;

import java.io.IOException;

public interface Command<T> {
    T execute() throws IOException;
}
