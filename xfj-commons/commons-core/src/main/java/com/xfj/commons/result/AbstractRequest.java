package com.xfj.commons.result;

import java.io.Serializable;

/**
 * @Author ZQ
 * @Description //TODO
 * @Date 2019/11/24 19:19
 **/
public abstract class AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1717442845820713651L;

    public abstract void requestCheck();

    @Override
    public String toString() {
        return "AbstractRequest{}";
    }
}
