package threads.utils.ThreadFactoryCustom;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;


public final class MyThreadFactory  {
  
	private String nameFormat = null;
	private Boolean daemon = null;
	private Integer priority = null;
	private UncaughtExceptionHandler uncaughtExceptionHandler = null;
	private ThreadFactory backingThreadFactory = null;

	public MyThreadFactory() {}

	public MyThreadFactory setNameFormat(String nameFormat) {
		String.format(nameFormat, 0);
		this.nameFormat = nameFormat;
		return this;
	}

	public MyThreadFactory setDaemon(boolean daemon) {
		this.daemon = daemon;
		return this;
	}

	public MyThreadFactory setPriority(int priority) {
		this.priority = priority;
		return this;
	}

	public MyThreadFactory setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
		return this;
	}

	public MyThreadFactory setThreadFactory(ThreadFactory backingThreadFactory) {
		return this;
	}

	public ThreadFactory build() {
		return build(this);
	}

	private static ThreadFactory build(MyThreadFactory builder) {
		final String nameFormat = builder.nameFormat;
		final Boolean daemon = builder.daemon;
		final Integer priority = builder.priority;
		final UncaughtExceptionHandler uncaughtExceptionHandler = builder.uncaughtExceptionHandler;
		final ThreadFactory backingThreadFactory =
				(builder.backingThreadFactory != null) ? builder.backingThreadFactory : Executors.defaultThreadFactory();
		final AtomicLong count = (nameFormat != null) ? new AtomicLong(0) : null;
		
		return new ThreadFactory() {
			@Override public Thread newThread(Runnable runnable) {
				Thread thread = backingThreadFactory.newThread(runnable);
				if (nameFormat != null) {
					thread.setName(String.format(nameFormat, count.getAndIncrement()));
				}
				if (daemon != null) {
					thread.setDaemon(daemon);
				}
				if (priority != null) {
					thread.setPriority(priority);
				}
				if (uncaughtExceptionHandler != null) {
					thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
				}
				return thread;
			}
		};
	}
}
