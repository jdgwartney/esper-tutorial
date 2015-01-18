package org.myapp.event;

import java.util.Random;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class Application {

    public static void main(String [] args) {
    	Configuration config = new Configuration();
    	config.addEventTypeAutoName("org.myapp.event");
    	EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);

    	String expression = "select avg(price) from OrderEvent.win:time(30 sec)";
    	EPStatement statement = epService.getEPAdministrator().createEPL(expression);
    	
    	MyListener listener = new MyListener();
    	statement.addListener(listener);
    	
    	Random pricer = new Random();

    	for (int i = 100 ; i > 0 ; i--) {
    		Double price = pricer.nextDouble() * pricer.nextDouble() * 100.0;
    		OrderEvent event = new OrderEvent("shirt",price);
    		epService.getEPRuntime().sendEvent(event);
    	}
    }
}
