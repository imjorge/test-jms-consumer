package com.jorge.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.Destination;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

public class Main {
    
    private static final Log log = LogFactory.getLog(Main.class);

    public static void main(String[] args) throws Exception {
        final CommandLineOptions cmd = new CommandLineOptions();
        final JCommander commander = new JCommander(cmd, args);
        
        if (cmd.isHelp()) {
            commander.usage();
            return;
        }
        
        if (cmd.getConfigurationFile() != null) {
        	final Properties properties = new Properties();
        	final InputStream inputStream = new FileInputStream(cmd.getConfigurationFile());
        	try {
        		properties.load(inputStream);
        	} finally {
        		try {
					inputStream.close();
				} catch (IOException ignored) { }
        	}
        	ApplicationProperties.properties.putAll(properties);
        }
        
        if (!((cmd.getQueueJndiName() == null) || (cmd.getQueueJndiName().length() == 0))) {
        	ApplicationProperties.properties.put("test.queue.jndiName", cmd.getQueueJndiName());
        }
        
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        try {
            if (log.isInfoEnabled()) {
                final Destination destination = context.getBean("CoreQueue", Destination.class);
                log.info(destination);
            }
            
            Thread.sleep(Long.MAX_VALUE);
        } finally {
            context.close();
        }
    }
    
    @Parameters(separators = "= ")
    static class CommandLineOptions {
        
        @Parameter(names = { "-q", "--queue" }, description = "Queue JNDI name")
        private String queueJndiName;
        
        @Parameter(names = { "-c", "--configuration" }, description = "Configuration file")
        private String configurationFile;
        
        @Parameter(names = { "-h", "--help" }, help = true)
        private boolean help;
        
        public String getQueueJndiName() {
            return queueJndiName;
        }

        public void setQueueJndiName(String queueJndiName) {
            this.queueJndiName = queueJndiName;
        }

        public String getConfigurationFile() {
			return configurationFile;
		}

		public void setConfigurationFile(String configurationFile) {
			this.configurationFile = configurationFile;
		}

		public boolean isHelp() {
            return help;
        }

        public void setHelp(boolean help) {
            this.help = help;
        }
        
    }

}
