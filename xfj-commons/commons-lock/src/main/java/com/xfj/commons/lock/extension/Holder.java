package com.xfj.commons.lock.extension;

import lombok.Data;

@Data
public class Holder<T> {
    private volatile T value;
}
