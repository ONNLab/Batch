package com.cat.ic.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

public class BatchRetryListener extends RetryListenerSupport {
	private static final Logger LOG = LoggerFactory
			.getLogger(BatchRetryListener.class);

	@Override
	public <T, E extends Throwable> void onError(RetryContext context,
			RetryCallback<T, E> callback, Throwable throwable) {
		 LOG.info("retried operation :",throwable);
	}
}
