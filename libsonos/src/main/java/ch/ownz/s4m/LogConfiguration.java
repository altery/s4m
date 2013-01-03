package ch.ownz.s4m;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogConfiguration {
	private final LogManager logManager;
	private final Logger rootLogger;
	private final Handler defaultHandler = new ConsoleHandler();
	private final Formatter defaultFormatter = new LoggingFormatter();
	private final Logger appLogger;

	public LogConfiguration() {
		super();

		this.logManager = LogManager.getLogManager();
		this.rootLogger = Logger.getLogger("");
		this.appLogger = Logger.getLogger("ch.ownz.s4m");
		configure();
	}

	final void configure() {
		this.defaultHandler.setFormatter(this.defaultFormatter);
		this.defaultHandler.setLevel(Level.FINE);
		this.rootLogger.setLevel(Level.INFO);
		this.appLogger.setLevel(Level.FINE);
		this.rootLogger.addHandler(this.defaultHandler);
		this.logManager.addLogger(this.rootLogger);
	}
}