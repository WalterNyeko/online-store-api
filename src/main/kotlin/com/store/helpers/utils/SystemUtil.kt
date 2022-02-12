package com.store.helpers.utils

import org.apache.commons.beanutils.BeanUtils

class SystemUtil {
    companion object {
        fun <R> copyProperties(source: Any?, target: R): R {
            BeanUtils.copyProperties(source, target)
            return target
        }
    }
}